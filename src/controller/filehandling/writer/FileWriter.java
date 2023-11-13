package controller.filehandling.writer;

import java.io.FileOutputStream;
import java.io.IOException;

import model.RGBImage;
import model.RGBImageInterface;

public class FileWriter implements FileWriterInterface{

  private final String saveFilePath;

  public FileWriter(String saveFilePath){
    this.saveFilePath=saveFilePath;
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
  public void write(RGBImageInterface image) throws IOException {
    String extension=getFileExtension(saveFilePath);
    if(extension.equals("ppm")){
      PPMWriter.writeToStorageDisk(image,new FileOutputStream(saveFilePath));
    }
    else{
      ImageIOWriter.writeToStorageDisk(image,new FileOutputStream(saveFilePath),extension);
    }
  }
}
