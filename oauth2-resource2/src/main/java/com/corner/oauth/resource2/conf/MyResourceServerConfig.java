package com.corner.oauth.resource2.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableResourceServer
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private ApplicationContext context;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            // 这里必须配置这个，不然报错
            .authorizeRequests().anyRequest().authenticated();
    }

    /**
     * 为什么配置这个请参见
     * https://stackoverflow.com/questions/29328124/no-bean-resolver-registered-in-the-context-to-resolve-access-to-bean
     * https://blog.csdn.net/liu_zhaoming/article/details/79411021
     * https://github.com/spring-projects/spring-security-oauth/issues/730
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.expressionHandler(getOAuth2MethodSecurityExpressionHandler());
    }

    @Bean
    public DefaultWebSecurityExpressionHandler getOAuth2MethodSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setApplicationContext(context);
        return defaultWebSecurityExpressionHandler;
    }
}
