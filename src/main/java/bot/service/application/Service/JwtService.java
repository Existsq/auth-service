package bot.service.application.Service;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.publicKeyPath}")
  private String publicKeyPath;

  private final Signer signer;
  private final Verifier verifier;

  public JwtService(Signer signer, Verifier verifier) {
    this.signer = signer;
    this.verifier = verifier;
    validateConfiguration();
  }

  private void validateConfiguration() {
    if (jwtSecret == null || jwtSecret.isEmpty()) {
      throw new IllegalArgumentException("JWT secret must not be null or empty");
    }
    if (publicKeyPath == null || publicKeyPath.isEmpty()) {
      throw new IllegalArgumentException("Public key path must not be null or empty");
    }
  }

  /**
   * Encrypts provided JWT token.
   *
   * @param jwt The JWT object to be encoded
   * @return Encoded JWT as a string
   * @throws IOException if an I/O error occurs while encoding
   */
  public String encrypt(JWT jwt) throws IOException {
    try {
      return JWT.getEncoder().encode(jwt, signer);
    } catch (Exception e) {
      logger.error("Error encoding JWT", e);
      throw new IOException("Failed to encode JWT", e);
    }
  }

  /**
   * Decodes a JWT and retrieves the UUID claim.
   *
   * @param encodedJWT The encoded JWT
   * @return The UUID claim from the JWT
   */
  public Optional<UUID> getUUID(String encodedJWT) {
    try {
      JWT jwt = JWT.getDecoder().decode(encodedJWT, verifier);
      String uuidString = jwt.getAllClaims().get("UUID").toString();
      return Optional.of(UUID.fromString(uuidString));
    } catch (Exception e) {
      logger.error("Error decoding JWT or retrieving UUID", e);
      return Optional.empty();
    }
  }

  /**
   * Creates a new JWT with default settings.
   *
   * @param subject The subject of the JWT
   * @param expirationDays Number of days until the JWT expires
   * @return The newly created JWT
   */
  public JWT createJwt(String subject, int expirationDays) {
    return new JWT()
        .setIssuer("www.acme.com")
        .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
        .setSubject(subject)
        .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusDays(expirationDays));
  }
}
