package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by admin on 2019/2/26.
 */
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private DataSource dataSource ;

    @Autowired
    private UserDetailsService userDetailsService ;

    String pwdQuery = "select user_name,pwd,status from t_user where user_name = ?";

    String roleQuery = "select u.user_name,r.role_name from t_user u,t_user_role ru,t_role r" +
            "where u.id = ur.user_id and r.id = ur.role_id" +
            "and u.user_name = ?";

    String secret = "qwer";

//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        //密码编码器
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        //使用内存存储
//        authenticationManagerBuilder.inMemoryAuthentication()
//                .passwordEncoder(bCryptPasswordEncoder)
//                .withUser("user")
//                //使用密码编码器的话，可以通过bCryptPasswordEncoder.encode（”123456“）来得到加密后的密码
//                .password(bCryptPasswordEncoder.encode("123123"))
//                .roles("user","admin");
//
//    }

//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        //密码编码器
//        Pbkdf2PasswordEncoder bCryptPasswordEncoder = new Pbkdf2PasswordEncoder(this.secret);
//        authenticationManagerBuilder.jdbcAuthentication()
//                //密码编辑器
//                .passwordEncoder(bCryptPasswordEncoder)
//                //数据源
//                .dataSource(dataSource)
//                .usersByUsernameQuery(pwdQuery)
//                .authoritiesByUsernameQuery(roleQuery);
//
//    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        Pbkdf2PasswordEncoder bCryptPasswordEncoder = new Pbkdf2PasswordEncoder(this.secret);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
//                .passwordEncoder(bCryptPasswordEncoder);
    }


    protected  void configure(HttpSecurity http) throws Exception{
        http        //requiresSecure()      需要https
                    //requiresInsecure()    不需要https
                .requiresChannel().antMatchers("/z/**").requiresInsecure()

            .and().authorizeRequests()
                .antMatchers("/testThymeleaf/helloHtml").hasAuthority("ADMIN")
                .antMatchers("/test/**").hasAuthority("USER")
                //其他路径允许签名后访问
                .anyRequest().permitAll()
                //对于没有配置权限的其他请求允许匿名访问
                .and().anonymous()
                //使用默认的登录页面
                .and().formLogin().loginPage("/login/page").defaultSuccessUrl("/admin/welcom1")
                .and().logout().logoutUrl("/logout/page").logoutSuccessUrl("/login/page")

                //启用http基础验证
                .and().httpBasic()
                .and().rememberMe().tokenValiditySeconds(86400).key("remember-key");


    }

}
