package bot.service.application.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private RSAPrivateKey jwtSecret;

  @Value("${jwt.public}")
  private RSAPublicKey jwtPublic;

  public void createJWT() {
    try {
      Algorithm algorithm = Algorithm.RSA256(jwtPublic, jwtSecret);
      String token = JWT.create().withIssuer("auth0").sign(algorithm);
    } catch (JWTCreationException exception) {
      // Invalid Signing configuration / Couldn't convert Claims.
    }
  }

  public void verifyJWT() {
    String token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
    DecodedJWT decodedJWT;
    try {
      Algorithm algorithm = Algorithm.RSA256(jwtPublic, jwtSecret);
      JWTVerifier verifier =
          JWT.require(algorithm)
              // specify any specific claim validations
              .withIssuer("auth0")
              // reusable verifier instance
              .build();

      decodedJWT = verifier.verify(token);
    } catch (JWTVerificationException exception) {
      // Invalid signature/claims
    }
  }

}
