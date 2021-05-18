package org.apache.karaf.rest.whiteboard;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.karaf.jpa.model.Credentials;
import org.apache.karaf.jpa.service.CredentialsService;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;

@Path("/authentication")
@JaxrsApplicationSelect("(osgi.jaxrs.name=gloop)")
@Component(service = AuthenticationRest.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
public class AuthenticationRest {

	@Reference
	private CredentialsService service;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(Credentials credentials) {

		try {

			String username = credentials.getUsername();
			String password = credentials.getPassword();

			// Authenticate the user using the credentials provided
			authenticate(username, password);

			// Issue a token for the user
			String token = issueToken(username);

			// Return the token on the response
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}

	private void authenticate(String username, String password) throws Exception {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(password);
		
		if(passwordEncryptor.checkPassword(password, encryptedPassword)) {
			System.out.println("valido");
		}
		
		Credentials credentials = service.get(username, password);
		
		if(credentials == null) {
			throw new Exception();
		}
	}

	private String issueToken(String username) {
		// Build an HMAC signer using a SHA-256 hash
		Signer signer = HMACSigner.newSHA256Signer("too many secrets");

		// Build a new JWT with an issuer(iss), issued at(iat), subject(sub) and
		// expiration(exp)
		JWT jwt = new JWT().setIssuer("www.acme.com").setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
				.setSubject(username).setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60));

		// Sign and encode the JWT to a JSON string representation
		String encodedJWT = JWT.getEncoder().encode(jwt, signer);

		return encodedJWT;
	}
}