package poc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import poc.model.User;
import poc.repository.UserRepo;

@SpringBootApplication
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public CommandLineRunner demo(UserRepo repo) {
    return (args) -> {
      // save a couple of users
      repo.save(new User("Jack"));
      repo.save(new User("Chloe"));

//      // fetch all customers
//      log.info("Users found with findAll():");
//      log.info("-------------------------------");
//      for (User user : repo.findAll()) {
//        log.info(user.toString());
//      }
//      log.info("");
//
//      // fetch an individual user by ID
//      User user = repo.findOne(1L);
//      log.info("User found with findOne(1L):");
//      log.info("--------------------------------");
//      log.info(user.toString());
//      log.info("");
    };
  }

}
