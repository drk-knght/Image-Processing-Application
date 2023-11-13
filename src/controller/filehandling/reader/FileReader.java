package controller.filehandling.reader;

import java.io.FileInputStream;
import java.io.IOException;

public class FileReader implements FileReaderInterface{

  private final String filePath;

  public FileReader(String filePath){
    this.filePath=filePath;
  }

  private String getFileExtension(String filePath){
    int index = filePath.lastIndexOf('.');
    if (index != -1) {
      return filePath.substring(index + 1);
    } else {
      return "";
    }
  }

  @Override
  public int[][][] read() throws IOException {
    String fileExtension=getFileExtension(this.filePath);
    if(fileExtension.equals("ppm")){
      return PPMReader.readFileContent(new FileInputStream(filePath));
    }
    else{
      return ImageIOReader.readFileContent(new FileInputStream(filePath));
    }
  }
}
