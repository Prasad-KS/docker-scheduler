package org.paggarwal.dockerscheduler.filters;

import com.auth0.jwt.JWTVerifier;
import com.google.common.base.Splitter;
import org.springframework.stereotype.Service;
import spark.Filter;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

import static org.apache.commons.lang.StringUtils.isEmpty;
import static spark.Spark.halt;

/**
 * Created by paggarwal on 3/21/16.
 */
@Service
public class AuthFilter implements Filter {
    @Inject
    private JWTVerifier jwtVerifier;

    @Override
    public void handle(Request request, Response response) throws Exception {
        String auth = request.headers("Authorization");
        if(isEmpty(auth)) {
            halt(401,"{\"message\":\"Missing Authentication Header\"}");
            return;
        }

        String token = Splitter.on(" ").splitToList(auth).get(1);
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            halt(401,"{\"message\":\"" + e.getMessage() + "\"}");
        }
    }
}
