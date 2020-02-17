package org.paggarwal.dockerscheduler.config;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;

import com.amazonaws.services.ecr.AmazonECR;
import com.amazonaws.services.ecr.AmazonECRClientBuilder;
import com.amazonaws.services.ecr.model.AuthorizationData;
import com.amazonaws.services.ecr.model.GetAuthorizationTokenRequest;
import com.amazonaws.services.ecr.model.GetAuthorizationTokenResult;
import com.github.dockerjava.api.model.AuthConfig;

public class AuthConfigFactory extends AbstractFactoryBean<AuthConfig> {

	@Value("#{ systemEnvironment['REGISTRY_URL'] }")
	private String registryUrl;

	@Value("#{ systemEnvironment['REGISTRY_USERNAME']}")
	private String registryUsername;

	@Value("#{ systemEnvironment['REGISTRY_PASSWORD'] }")
	private String registryPassword;

	@Value("#{ systemEnvironment['REGISTRY_EMAIL'] }")
	private String registryEmail;

	@Value("#{ systemEnvironment['AWS_REGION'] ?: 'us-west-1' }")
	private String awsRegion;

	private AuthorizationData authorizationData;

	/**
	 * 
	 */
	public AuthConfigFactory() {
		setSingleton(false);
	}

	@Override
	public Class<?> getObjectType() {
		return AuthConfig.class;
	}

	@Override
	protected AuthConfig createInstance() throws Exception {
		if (StringUtils.isNotBlank(registryUrl)) {
			return new AuthConfig().withRegistryAddress(registryUrl).withUsername(registryUsername)
					.withPassword(registryPassword).withEmail(registryEmail);
		}
		
		Date time = getOneHourFromNow();
		
		if (authorizationData == null || authorizationData.getExpiresAt().before(time)) {
			AuthorizationData authData = getECRAuthorization();
			this.authorizationData = authData;

		}
		
		String userPassword = new String(Base64.getDecoder().decode(this.authorizationData.getAuthorizationToken()));
		String user = userPassword.substring(0, userPassword.indexOf(":"));
		String password = userPassword.substring(userPassword.indexOf(":") + 1);

		return new AuthConfig().withUsername(user).withPassword(password)
				.withRegistryAddress(this.authorizationData.getProxyEndpoint());
	}

	private AuthorizationData getECRAuthorization() {
		AmazonECR amazonECR = AmazonECRClientBuilder.standard().withRegion(awsRegion).build();

		GetAuthorizationTokenResult authorizationToken = amazonECR
				.getAuthorizationToken(new GetAuthorizationTokenRequest());

		return authorizationToken.getAuthorizationData().get(0);
	}

	private Date getOneHourFromNow() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		return calendar.getTime();
	}

}
