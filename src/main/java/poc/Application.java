package poc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

  private ExecutorService pool;
  private CyclicBarrier barrier;
  private int numThreads;
  private long numIterations;

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setBannerMode(Banner.Mode.OFF);
    app.run(args);
  }

  @Bean
  public CommandLineRunner demo(BaselineUserRepo baselineUserRepo,
      PrefixedUserRepo prefixedUserRepo,
      SeqPrefixedUserRepo seqPrefixedUserRepo) {
    return (args) -> {
      if (args.length < 1 || args.length > 3) {
        System.out.println(
            "Usage: java -jar poc-id-generator-<version>.jar baseline|custPrefixed|seqPrefixed [number of concurrent threads] [number of iterations]");
        return;
      }

      numIterations = 1000L;
      numThreads = 50;
      if (args.length == 3) {
        numIterations = Long.parseLong(args[2]);
        numThreads = Integer.parseInt(args[1]);
      } else if (args.length == 2) {
        numThreads = Integer.parseInt(args[1]);
      }
      String strategy = args[0];

      this.pool = Executors.newFixedThreadPool(numThreads);
      this.barrier = new CyclicBarrier(numThreads + 1);

      switch (strategy) {
        case "baseline":
          for (int i = 0; i < numThreads; i++) {
            pool.execute(new BaselineStrategy(baselineUserRepo));
          }
          break;
        case "custPrefixed":
          for (int i = 0; i < numThreads; i++) {
            pool.execute(new CustPrefixedStrategy(prefixedUserRepo));
          }
          break;
        case "seqPrefixed":
          for (int i = 0; i < numThreads; i++) {
            pool.execute(new SeqPrefixedStrategy(seqPrefixedUserRepo));
          }
          break;
      }
      barrier.await();
      pool.shutdown();
    };
  }

  class BaselineStrategy implements Runnable {

    BaselineUserRepo repo;

    BaselineStrategy(BaselineUserRepo theRepo) {
      repo = theRepo;
    }

    @Override
    public void run() {
      try {
        barrier.await();
        LongStream
            .rangeClosed(1L, numIterations)
            .forEach(c -> repo.save(new BaselineUser(Long.toString(c))));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  class CustPrefixedStrategy implements Runnable {

    PrefixedUserRepo repo;

    CustPrefixedStrategy(PrefixedUserRepo theRepo) {
      repo = theRepo;
    }

    @Override
    public void run() {
      try {
        barrier.await();
        LongStream
            .rangeClosed(1L, numIterations)
            .forEach(c -> repo.save(new PrefixedUser(Long.toString(c))));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  class SeqPrefixedStrategy implements Runnable {

    SeqPrefixedUserRepo repo;

    SeqPrefixedStrategy(SeqPrefixedUserRepo repo) {
      this.repo = repo;
    }

    @Override
    public void run() {
      try {
        barrier.await();
        LongStream
            .rangeClosed(1L, numIterations)
            .forEach(c -> repo.save(new SeqPrefixedUser(Long.toString(c))));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }
}
