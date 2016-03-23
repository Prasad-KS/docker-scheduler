package org.paggarwal.dockerscheduler.handlers;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.eclipse.egit.github.core.service.UserService;
import org.glassfish.jersey.filter.LoggingFilter;
import org.paggarwal.dockerscheduler.Answer;
import org.paggarwal.dockerscheduler.RequestHandlerWrapper;
import org.paggarwal.dockerscheduler.models.AuthRequest;
import org.paggarwal.dockerscheduler.util.TriFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import static com.google.common.collect.ImmutableMap.of;
import static org.paggarwal.dockerscheduler.models.User.Builder.anUser;

/**
 * Created by paggarwal on 3/21/16.
 */
@Service
public class AuthHandler extends RequestHandlerWrapper<AuthRequest> implements TriFunction<AuthRequest,Map<String,String>,Map<String,String>,Answer> {

    @Value("#{ systemEnvironment['GITHUB_ORGS'] ?: 'LiftOffLLC' }")
    private String githubOrgString;

    @Value("#{ systemEnvironment['GITHUB_CLIENT_SECRET'] }")
    private String githubSecret;

    @Inject
    private org.paggarwal.dockerscheduler.service.UserService userService;

    @Inject
    private JWTSigner jwtSigner;

    @Inject
    private JWTVerifier jwtVerifier;

    private List<String> githubOrgs;

    public AuthHandler() {
        super(AuthRequest.TYPE, null);
        setHandler(this);
    }

    @PostConstruct
    public void init() {
        githubOrgs = Splitter.on(",").splitToList(githubOrgString);
    }

    public Answer apply(AuthRequest authRequest, Map<String,String> params, Map<String,String> headers) {
        Map<String,String> requestParams = Maps.newHashMap();
        requestParams.put("code", authRequest.getCode());
        requestParams.put("client_id", authRequest.getClientId());
        requestParams.put("redirect_uri", authRequest.getRedirectUri());
        requestParams.put("client_secret",githubSecret);
        Client client = ClientBuilder.newClient();

        try {
            Entity<Map<String,String>> entity = Entity.json(requestParams);
            Map<String,String> response = client.target("https://github.com").path("/login/oauth/access_token").request(MediaType.APPLICATION_JSON).post(entity,Map.class);
            String accessToken = response.get("access_token");

            UserService userService = new UserService();
            userService.getClient().setOAuth2Token(accessToken);
            User githubUser = userService.getUser();

            OrganizationService organizationService = new OrganizationService();
            organizationService.getClient().setOAuth2Token(accessToken);

            List<User> orgs = organizationService.getOrganizations();
            if(orgs.stream().anyMatch(org -> githubOrgs.contains(org.getLogin()))) {
                if(headers.containsKey("Authorization")) {
                    Optional<org.paggarwal.dockerscheduler.models.User> userOpt = this.userService.findByGithubId(githubUser.getId());
                    if(userOpt.isPresent()) {
                        return new Answer(409, of("message","There is already a GitHub account that belongs to you"));
                    }

                    int id = Integer.parseInt(jwtVerifier.verify(headers.get("Authorization").split(" ")[0]).get("sub").toString());
                    userOpt = this.userService.findByGithubId(id);
                    if(!userOpt.isPresent()) {
                        return new Answer(400, of("message","User not found"));
                    }

                    return geTokenAnswer(this.userService.create(anUser().withGithubId(githubUser.getId())
                            .withName(githubUser.getName()).build()));
                }

                Optional<org.paggarwal.dockerscheduler.models.User> userOpt = this.userService.findByGithubId(githubUser.getId());
                if(userOpt.isPresent()) {
                    return geTokenAnswer(userOpt.get().getId());
                }

                org.paggarwal.dockerscheduler.models.User user = anUser().withGithubId(githubUser.getId())
                        .withName(githubUser.getName()).build();
                return geTokenAnswer(this.userService.create(user));
            } else {
                return new Answer(401);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Answer(500);
        }
    }

    private Answer geTokenAnswer(int id) {
        String token = jwtSigner.sign(of("sub", Integer.toString(id), "iat", System.currentTimeMillis(), "exp", System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000));
        return new Answer(200, of("token", token));
    }
}
