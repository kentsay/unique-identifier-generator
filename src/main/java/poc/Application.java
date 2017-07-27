package poc;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
import poc.util.Fileutil;
import poc.util.StopWatch;

@SpringBootApplication
public class Application {

  private ExecutorService pool;
  private CyclicBarrier barrier;
  private int numThreads;
  private long numIterations;
  private final String OUTPUT_FILE = "output.csv";

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
      StopWatch sw = new StopWatch();
      Future<Void> future = null;

      this.pool = Executors.newFixedThreadPool(numThreads);
      this.barrier = new CyclicBarrier(numThreads + 1);

      switch (strategy) {
        case "baseline":
          for (int i = 0; i < numThreads; i++) {
            future = pool.submit(new BaselineStrategy(baselineUserRepo));
          }
          break;
        case "custPrefixed":
          for (int i = 0; i < numThreads; i++) {
            future = pool.submit(new CustPrefixedStrategy(prefixedUserRepo));
          }
          break;
        case "seqPrefixed":
          for (int i = 0; i < numThreads; i++) {
            future = pool.submit(new SeqPrefixedStrategy(seqPrefixedUserRepo));
          }
          break;
      }
      barrier.await();
      sw.on();
      future.get();
      Fileutil.writeToTextFile(OUTPUT_FILE, numThreads + "," + numIterations + "," + strategy + "," + sw.off() + "\n");
      pool.shutdown();
    };
  }

  class BaselineStrategy implements Callable<Void> {

    BaselineUserRepo repo;

    BaselineStrategy(BaselineUserRepo repo) {
      this.repo = repo;
    }

    @Override
    public Void call() throws Exception {
      try {
        barrier.await();
        LongStream
            .rangeClosed(1L, numIterations)
            .forEach(c -> repo.save(new BaselineUser(Long.toString(c))));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }

  class CustPrefixedStrategy implements Callable<Void> {

    PrefixedUserRepo repo;

    CustPrefixedStrategy(PrefixedUserRepo repo) {
      this.repo = repo;
    }

    @Override
    public Void call() throws Exception {
      try {
        barrier.await();
        LongStream
            .rangeClosed(1L, numIterations)
            .forEach(c -> repo.save(new PrefixedUser(Long.toString(c))));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }

  class SeqPrefixedStrategy implements Callable<Void> {

    SeqPrefixedUserRepo repo;

    SeqPrefixedStrategy(SeqPrefixedUserRepo repo) {
      this.repo = repo;
    }

    @Override
    public Void call() throws Exception {
      try {
        barrier.await();
        LongStream
            .rangeClosed(1L, numIterations)
            .forEach(c -> repo.save(new SeqPrefixedUser(Long.toString(c))));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return null;
    }
  }
}
