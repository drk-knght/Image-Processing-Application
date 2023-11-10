package controller.filehandling.reader;

import java.io.IOException;
import java.io.InputStream;

public interface FileReader {
  int [][][] read() throws IOException;
}
