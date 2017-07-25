package poc;

import java.util.stream.LongStream;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import poc.model.BaselineUser;
import poc.model.PrefixedUser;
import poc.model.SeqPrefixedUser;
import poc.repository.BaselineUserRepo;
import poc.repository.PrefixedUserRepo;
import poc.repository.SeqPrefixedUserRepo;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public CommandLineRunner demo(BaselineUserRepo baselineUserRepo, PrefixedUserRepo prefixedUserRepo, SeqPrefixedUserRepo seqPrefixedUserRepo) {
        return (args) -> {
            if (args.length < 1 || args.length > 2) {
                System.out.println("Usage: java -jar poc-id-generator-<version>.jar baseline|prefixed|table [number of iterations]");
                return;
            }

            long maxIterations = 1000L;
            if (args.length == 2) {
                maxIterations = Long.parseLong(args[1]);
            }

            switch (args[0]) {
                case "baseline":
                    LongStream.rangeClosed(1L, maxIterations).forEach(c -> baselineUserRepo.save(new BaselineUser(Long.toString(c))));
                    break;
                case "prefixed":
                    LongStream.rangeClosed(1L, maxIterations).forEach(c -> prefixedUserRepo.save(new PrefixedUser(Long.toString(c))));
                    break;
                case "table":
                    LongStream.rangeClosed(1L, maxIterations).forEach(c -> seqPrefixedUserRepo.save(new SeqPrefixedUser(Long.toString(c))));
                    break;
            }
        };
    }

}
