package sap.commerce.org.unitservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
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
	public WebClient accountServiceWebClient(final ReactiveClientRegistrationRepository clientRegistrations,
											 final ServerOAuth2AuthorizedClientRepository authorizedClients)
	{
		final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrations, authorizedClients);


		oauth.setDefaultClientRegistrationId(OCC_CLIENT_REGISTRATION_ID);
		return WebClient.builder().baseUrl(userServiceGateway).filter(oauth)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
}
