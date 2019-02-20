package com.corner.oauth.resource.conf;

import com.corner.oauth.resource.properties.Oauth2ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
@EnableConfigurationProperties({Oauth2ClientProperties.class})
public class MyClientConfig {

    @Autowired
    private Oauth2ClientProperties oauth2ClientProperties;

    /**
     * 其中设置的AccessTokenProvider，默认是提供支持四种方式，会根据resourceDetails的类型来
     * 使用对应的操作。
     * 如果是想只用一种授权方式的，就自己新建AccessTokenProvider然后设置进去就行了
     * @param clientContext
     * @return
     */
    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext clientContext) {
        // 这里使用默认提供的OAuth2ClientContext，用于将每个用户请求的认证信息隔离开。官方文档说如果我们不这么做，那么需要自己维护一个类似的数据结构用于隔离每个用户的请求。
        // 这里第一个参数配置使用的4种oauth2认证模式哪一种，默认授权码
        // 这里第二个参数必须使用默认的，就是注入进来的，什么new DefaultOAuth2ClientContext()是不对的。
        // 按照官方说法，默认的能够自动保存用户与token的映射关系，而自己new的需要自己维护，自己维护太麻烦了
        // 这里就用默认的最好。
        final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails(), clientContext);

        //=======只支持客户端模式配置=======
//        ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();

        // =============

        //=======只支持授权码模式配置=======
//        AuthorizationCodeAccessTokenProvider accessTokenProvider = new AuthorizationCodeAccessTokenProvider();
        // 关闭强制使用state参数（这个是防CSRF的参数，具体百度），正常开发应该必须携带
//        authCodeProvider.setStateMandatory(false);
        // =============

//        AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(accessTokenProvider));
//        oAuth2RestTemplate.setAccessTokenProvider(provider);
        // 这里可以配置ClientTokenServices来持久化token，避免服务重启token丢失需要重新授权。应该是必须配置的。
        return oAuth2RestTemplate;

    }

    /**
     * 配置使用的认证模式为客户端模式，即用户需要认证时直接我们这个应用认证了就算用户认证了
     * 一般内部系统使用这种方式来访问不需要用户登录的资源，资源服务器只需要认可是内部应用在调用就行了。
     * 但是如果需要用户认证，就需要使用授权码模式等方式，这个通过使用不同的OAuth2RestTemplate去调用资源服务器就行
     * （OAuth2RestTemplate是和resourceDetails绑定的）
     * @return
     */
/*    @Bean
    public ClientCredentialsResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId("test");
        details.setAccessTokenUri(oauth2ClientProperties.getAccessTokenUri());
        details.setClientId(oauth2ClientProperties.getClientId());
        details.setClientSecret(oauth2ClientProperties.getClientSecret());
        details.setAuthenticationScheme(AuthenticationScheme.query);
        return details;
    }*/


    @Bean
    public OAuth2ProtectedResourceDetails resourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setId("test");
        details.setAccessTokenUri(oauth2ClientProperties.getAccessTokenUri());
        details.setClientId(oauth2ClientProperties.getClientId());
        details.setClientSecret(oauth2ClientProperties.getClientSecret());
        details.setAuthenticationScheme(AuthenticationScheme.query);
        // 设置客户端的登录参数方式，这里使用SSO不使用客户端的登录
        //details.setClientAuthenticationScheme(AuthenticationScheme.form);
        //details.setScope(Arrays.asList("read", "write"));
        details.setUseCurrentUri(false);
        return details;
    }
}
