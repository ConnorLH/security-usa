package com.corner.oauth.resource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "corner.security")
public class Oauth2ClientProperties {

    private String accessTokenUri;

    private String clientId;

    private String clientSecret;

    private String clientAuthenticationScheme;
}
