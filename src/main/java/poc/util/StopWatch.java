package poc.util;

public class StopWatch {
  long before;
  long time;

  public void on() {
    before = System.currentTimeMillis();
  }

  public long off() {
    time = System.currentTimeMillis() - before;
    return time;
  }
}
