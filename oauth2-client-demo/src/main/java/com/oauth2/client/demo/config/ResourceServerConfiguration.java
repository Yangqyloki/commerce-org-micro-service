package com.oauth2.client.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled=true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
    public void configure(final HttpSecurity http) throws Exception {
        http.anonymous().disable().csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
				.httpBasic().disable();
    }

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) throws Exception
	{
		resources.tokenServices(tokenServices());
	}

	//	@Bean
	//	public RemoteTokenServices tokenService()
	//	{
	//		final RemoteTokenServices tokenServices = new RemoteTokenServices();
	//		tokenServices.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
	//		tokenServices.setClientId("hybris");
	//		tokenServices.setClientSecret("111111");
	//		return tokenServices;
	//	}
	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter()
	{
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices()
	{
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
}
