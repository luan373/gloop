package br.com.gloop.rest.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.osgi.service.component.annotations.Component;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;

@Component(service = JwtUtil.class)
public class JwtUtil {

	private static final String SECRET_JWT = "deadMouseArrombado";

	private static final Verifier VERIFIER = HMACVerifier.newVerifier(SECRET_JWT);

	private static final Signer SIGNER = HMACSigner.newSHA256Signer(SECRET_JWT);

	public void validateToken(String token) throws Exception {
		JWT jwt = decodeToken(token);

		if (jwt.isExpired()) {
			throw new Exception();
		}
	}

	public JWT decodeToken(String token) {
		return JWT.getDecoder().decode(token, VERIFIER);
	}

	public String issueToken(String username) {
		JWT jwt = new JWT().setIssuer("www.gloop.com").setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
				.setSubject(username).setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusHours(5));

		String encodedJWT = JWT.getEncoder().encode(jwt, SIGNER);

		return encodedJWT;
	}
}
