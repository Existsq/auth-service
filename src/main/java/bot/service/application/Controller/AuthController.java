package bot.service.application.Controller;

import bot.service.application.Service.AuthService;
import bot.service.application.Service.JwtService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {

  private final JwtService jwtService;

  @GetMapping("/auth/{value}")
  @ResponseBody
  public String auth(@PathVariable String value) {
    return "";
  }
}
