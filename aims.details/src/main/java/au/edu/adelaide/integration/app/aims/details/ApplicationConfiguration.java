package au.edu.adelaide.integration.app.aims.details;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.cxf.jaxrs.CxfRsEndpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.keycloak.OAuth2Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import au.edu.adelaide.integration.util.cxf.logging.UOALoggingFeature;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
public class ApplicationConfiguration {

	@Value("${app.name}")
	String appName;

	@Value("${rest.service.address}")
	private String serviceAddress;

	@Value("${peoplesoft.service.url}")
	private String peopleSoftServiceAddress;

	@Value("${secure.field.status:true}")
	private String secureFieldStatus;

	@Value("${keycloak.realm}")
	private String keycloakRealm;

	@Value("${keycloak.auth-server-url}")
	private String keycloakAuthUrl;

	@Value("${keycloak.resource}")
	private String keycloakResource;

	@Value("${keycloak.credentials.secret}")
	private String keycloakSecret;

	@Autowired
	private Bus bus;

	@Bean
	public Bus bus() {
		Bus bus = BusFactory.getDefaultBus();
		ArrayList<UOALoggingFeature> loggingFeatures = new ArrayList<UOALoggingFeature>();
		loggingFeatures.add(loggingFeature());
		bus.setFeatures(loggingFeatures);
		return bus;
	}

	@Bean
	public UOALoggingFeature loggingFeature() {
		UOALoggingFeature loggingFeature = new UOALoggingFeature(Boolean.parseBoolean(secureFieldStatus));
		loggingFeature.setPrettyLogging(true);
		return loggingFeature;
	}

	
	@Bean(name = "aimsDataServiceEndpoint")
	JAXRSServerFactoryBean aimsDataServiceEndpoint() {
	
		CxfRsEndpoint endpoint = new CxfRsEndpoint();
		JAXRSServerFactoryBean rsServer = endpoint.createJAXRSServerFactoryBean();
		rsServer.setBus(bus);
		rsServer.setServiceClass(RestService.class);
		rsServer.setAddress(serviceAddress);
		
		return rsServer;

	}
	// peoplesoft rest client

	@Bean
	public OAuth2RestTemplate oAuth2RestTemplate() {
		String url = keycloakAuthUrl + "/realms/" + keycloakRealm + "/protocol/openid-connect/token";

		ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
		resourceDetails.setId(keycloakResource);
		resourceDetails.setGrantType(OAuth2Constants.CLIENT_CREDENTIALS);
		resourceDetails.setAccessTokenUri(url);
		resourceDetails.setClientId(keycloakResource);
		resourceDetails.setClientSecret(keycloakSecret);

		return new OAuth2RestTemplate(resourceDetails);
	}

	@Bean
	JAXRSClientFactoryBean apiRestClient() {

		final Map<String, Object> properties = new HashMap<>();
		properties.put("inheritHeaders", true);
		properties.put("loggingFeatureEnabled", false);
		properties.put("loggingSizeLimit", 200);
		final JAXRSClientFactoryBean rsClient = new CxfRsEndpoint().createJAXRSClientFactoryBean();
		rsClient.setAddress(peopleSoftServiceAddress);
		rsClient.setProperties(properties);
		return rsClient;
	}

}
