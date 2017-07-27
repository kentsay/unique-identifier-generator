package poc.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Fileutil {

  public static String readTextFile(String fileName) throws IOException {
    String content = new String(Files.readAllBytes(Paths.get(fileName)));
    return content;
  }

  public static List<String> readTextFileByLines(String fileName) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get(fileName));
    return lines;
  }

  public static void writeToTextFile(String fileName, String content) throws IOException {
    Path path = Paths.get(fileName);
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
  }

}
