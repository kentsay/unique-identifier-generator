package poc;

import java.util.stream.LongStream;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import poc.model.PrefixedUser;
import poc.repository.PrefixedUserRepo;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public CommandLineRunner demo(PrefixedUserRepo repo) {
    return (args) -> {
      LongStream.rangeClosed(1L, 1000000L).forEach(c -> repo.save(new PrefixedUser(Long.toString(c))));
    };
  }

}
