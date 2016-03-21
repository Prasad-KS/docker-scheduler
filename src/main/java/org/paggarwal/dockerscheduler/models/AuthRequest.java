package org.paggarwal.dockerscheduler.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.paggarwal.dockerscheduler.Validable;

/**
 * Created by paggarwal on 3/21/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest implements Validable {
    public static final TypeReference<AuthRequest> TYPE = new TypeReference<AuthRequest>() {};
    private String clientId;
    private String code;
    private String redirectUri;

    @JsonCreator
    public AuthRequest(@JsonProperty("clientId") String clientId, @JsonProperty("code") String code, @JsonProperty("redirectUri")  String redirectUri) {
        this.clientId = clientId;
        this.code = code;
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCode() {
        return code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthRequest)) return false;
        AuthRequest that = (AuthRequest) o;
        return Objects.equal(getClientId(), that.getClientId()) &&
                Objects.equal(getCode(), that.getCode()) &&
                Objects.equal(getRedirectUri(), that.getRedirectUri());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClientId(), getCode(), getRedirectUri());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("clientId", clientId)
                .add("code", code)
                .add("redirectUri", redirectUri)
                .toString();
    }


    public static class Builder {
        private String clientId;
        private String code;
        private String redirectUri;

        private Builder() {
        }

        public static Builder anAuthenticate() {
            return new Builder();
        }

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
            return this;
        }

        public Builder but() {
            return anAuthenticate().withClientId(clientId).withCode(code).withRedirectUri(redirectUri);
        }

        public AuthRequest build() {
            AuthRequest authenticate = new AuthRequest(clientId, code, redirectUri);
            return authenticate;
        }
    }
}
