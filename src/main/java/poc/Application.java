package poc;

import java.util.stream.LongStream;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import poc.model.User;
import poc.repository.UserRepo;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public CommandLineRunner demo(UserRepo repo) {
    return (args) -> {
      LongStream.range(0L, 1000000L).forEach(c -> repo.save(new User(Long.toString(c))));
    };
  }

}
