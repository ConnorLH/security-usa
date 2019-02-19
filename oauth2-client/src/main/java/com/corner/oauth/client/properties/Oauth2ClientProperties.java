package com.corner.oauth.client.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "corner.security")
public class Oauth2ClientProperties {

    private String accessTokenUrl;

    private String clientId;

    private String clientSecret;

    private String clientAuthenticationScheme;
}
