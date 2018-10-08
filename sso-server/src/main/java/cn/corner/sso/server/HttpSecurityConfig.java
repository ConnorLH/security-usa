package cn.corner.sso.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 主要用于设置AuthorizationServer的EndPoint的权限拦截控制,特别是"/oauth/authorize"
 * 因为这个EndPoint需要登录后才能正常执行（需要获取给予授权的用户，即谁给第三方客户端授权的）
 * 所以必须在这里对其进行登录拦截
 *
 */
@Configuration
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SsoUserDetailsService ssoUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated().and()
            .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(ssoUserDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    /*    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/
}
