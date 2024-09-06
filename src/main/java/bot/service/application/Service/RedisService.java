package bot.service.application.Service;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;

  @Autowired
  public RedisService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void saveRefreshToken(
      String uuid, String refreshToken, long expirationTime, TimeUnit timeUnit) {
    redisTemplate.opsForValue().set(uuid, refreshToken, expirationTime, timeUnit);
  }

  public String getRefreshToken(String uuid) {
    return redisTemplate.opsForValue().get(uuid);
  }

  public void deleteRefreshToken(String uuid) {
    redisTemplate.delete(uuid);
  }
}
