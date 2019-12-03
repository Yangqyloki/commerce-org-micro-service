package sap.commerce.org.unitservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@Configuration
public class UnitServiceApplication {

	private static final String OCC_CLIENT_REGISTRATION_ID = "occ";

	@Value("${user.service.gateway}")
	private String userServiceGateway;

	public static void main(String[] args) {
		SpringApplication.run(UnitServiceApplication.class, args);
	}


	@Bean(name = {"userServiceWebClient"})
	public WebClient cloudCartWebClient()
	{
		return WebClient.builder().baseUrl(userServiceGateway)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
//	@Bean(name = {"userServiceWebClient"})
//	public WebClient accountServiceWebClient(final ReactiveClientRegistrationRepository clientRegistrations,
//											 final ServerOAuth2AuthorizedClientRepository authorizedClients)
//	{
//		final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
//				clientRegistrations, authorizedClients);
//
//
//		oauth.setDefaultClientRegistrationId(OCC_CLIENT_REGISTRATION_ID);
//		return WebClient.builder().baseUrl(userServiceGateway).filter(oauth)
//				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
//	}
//
//	@Bean
//	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//		http.authorizeExchange()
//				.anyExchange()
//				.authenticated()
//				.and()
//				.oauth2Login();
//		return http.build();
//	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http)
	{
		return http.authorizeExchange().anyExchange().permitAll().and().httpBasic().and().csrf().disable().build();
	}

}
