package br.com.gloop.rest.whiteboard;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsApplicationSelect;
import org.osgi.service.jaxrs.whiteboard.propertytypes.JaxrsResource;

import br.com.gloop.jpa.model.Credentials;
import br.com.gloop.jpa.service.CredentialsService;
import br.com.gloop.rest.api.model.RefreshToken;
import br.com.gloop.rest.api.model.User;
import br.com.gloop.rest.security.Secured;
import br.com.gloop.rest.util.JwtUtil;
import io.fusionauth.jwt.domain.JWT;

@Path("/authentication")
@JaxrsApplicationSelect("(osgi.jaxrs.name=gloop)")
@Component(service = AuthenticationRest.class, scope = ServiceScope.PROTOTYPE)
@JaxrsResource
public class AuthenticationRest {

	@Reference
	private CredentialsService service;
	
	@Reference
    private JwtUtil jwtUtil;
	
	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response authenticateUser(Credentials credentials) {

		try {

			String username = credentials.getUsername();
			String password = credentials.getPassword();

			// Authenticate the user using the credentials provided
			authenticate(username, password);

			// Issue a token for the user
			String token = jwtUtil.issueToken(username);

			// Return the token on the response
			return Response.ok(token).build();

		} catch (Exception e) {
			return Response.status(Response.Status.FORBIDDEN).build();
		}
	}
	
	@POST
	@Path("refresh")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response refreshToken(RefreshToken rt) {
    	// Verify and decode the encoded string JWT to a rich object
    	JWT jwt = jwtUtil.decodeToken(rt.getRefresh_token());
    	
    	String token = rt.getRefresh_token();
    	
    	if(jwt.isExpired()) {
    		 token = jwtUtil.issueToken(jwt.subject);
    	}
		
		return Response.ok(token).build();
	}
	
	@GET
	@Path("user")
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response user(@Context HttpHeaders httpheaders) {
		String authorizationHeader = httpheaders.getHeaderString(HttpHeaders.AUTHORIZATION);
		String token = authorizationHeader
                .substring("Bearer".length()).trim();
		
    	// Verify and decode the encoded string JWT to a rich object
    	JWT jwt = jwtUtil.decodeToken(token);
    	
    	Credentials credentials = service.get(jwt.subject);
		
		return Response.ok(new User(credentials.getUsername())).build();
	}

	private void authenticate(String username, String password) throws Exception {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		
		Credentials credentials = service.get(username);
		
		if(credentials != null) {
			if(!passwordEncryptor.checkPassword(password, credentials.getPassword())) {
				throw new Exception();
			}
		} else {
			throw new Exception();
		}
	}

}