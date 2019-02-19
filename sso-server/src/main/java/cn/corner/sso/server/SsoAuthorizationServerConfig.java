package cn.corner.sso.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class SsoAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("corner1")
                .secret("aaa1")
                .authorizedGrantTypes("authorization_code","refresh_token","password")
                // 这里是拿到code后302的url，默认拦截/login，拦截到后会自动处理
                .redirectUris("http://localhost:8082/client1/login")
                .scopes("all")
                .autoApprove(true)
                .and()
                .withClient("corner2")
                .secret("aaa2")
                .authorizedGrantTypes("authorization_code","refresh_token")
                .redirectUris("http://localhost:8083/client2/login")
                .scopes("all")
                .autoApprove(true);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
        /*设置可以将用户角色作为scope
        即请求中的scope可以是这种形式XXX,XXX是角色名,当然同时也需要在上面那个配置里进行配置
        注意配置的形式是"ROLE_XXX"
        */
//        DefaultOAuth2RequestFactory requestFactory = (DefaultOAuth2RequestFactory)endpoints.getOAuth2RequestFactory();
//        requestFactory.setCheckUserScopes(true);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // 配置jwt密钥的访问权限，这里是需要认证后才能访问
        security.tokenKeyAccess("isAuthenticated()");
        security.passwordEncoder(NoOpPasswordEncoder.getInstance());
        super.configure(security);
    }

    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("jkl");
        return accessTokenConverter;
    }
}
