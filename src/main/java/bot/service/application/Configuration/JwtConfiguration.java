package bot.service.application.Configuration;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.rsa.RSASigner;
import io.fusionauth.jwt.rsa.RSAVerifier;
import java.io.IOException;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JwtConfiguration {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.public}")
  private String publicKeyPath;

  @Bean
  public Signer rsaSigner() {
    return RSASigner.newSHA256Signer(jwtSecret);
  }

  @Bean
  public Verifier rsaVerifier() throws IOException {
    return RSAVerifier.newVerifier(Path.of(publicKeyPath));
  }
}