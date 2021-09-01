package com.kaz.passm.config

import com.kaz.passm.auth.AuthTokenFilter
import com.kaz.passm.auth.JwtAuthenticationEntryPoint
import com.kaz.passm.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var authenticationEntryPoint: JwtAuthenticationEntryPoint

    @Autowired
    lateinit var userService: UserService

    //    @Autowired
    //    private AuthenticationProvider authenticationProvider;
    override fun configure(http: HttpSecurity) {
        // Disable CSRF (cross site request forgery)
        http.csrf().disable()
        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // Entry points
        http.authorizeRequests() //
            .antMatchers("/api/auth/**").permitAll()
//            .antMatchers("/h2-console/**/**").permitAll() // Disallow everything else..
            .anyRequest().authenticated()

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity?) {
        super.configure(web)
        // Allow swagger to be accessed without authentication
        web!!.ignoring().antMatchers("/v2/api-docs") //
            .antMatchers("/swagger-resources/**") //
            .antMatchers("/swagger-ui.html") //
            .antMatchers("/swagger-ui/**") //
            .antMatchers("/swagger-ui/index.html") //
            .antMatchers("/configuration/**") //
            .antMatchers("/webjars/**") //
            .antMatchers("/public") // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
            .and()
            .ignoring()
            .antMatchers("/h2-console/**/**")

    }

    override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(12)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Bean
    fun authenticationJwtTokenFilter(): AuthTokenFilter {
        return AuthTokenFilter()
    }
}
