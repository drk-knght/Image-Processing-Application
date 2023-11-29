package controller.filehandling.reader;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * The class represents an input reader which reads the image data from a given specified file path.
 * The reader can read image data for different file extensions like ppm, jpg, png, etc.
 * The reader supports the image data reading for ppm and any extension supported by imageIO class.
 */
public class FileReader implements InputReaderInterface {

  private final String filePath;

  /**
   * The constructor takes the file path where data is stored in the local machine for reading data.
   *
   * @param filePath String representing the path of the file where the image data is stored.
   */
  public FileReader(String filePath) {
    this.filePath = filePath;
  }

  private String getFileExtension(String filePath) {
    int index = filePath.lastIndexOf('.');
    if (index != -1) {
      return filePath.substring(index + 1);
    } else {
      return "";
    }
  }

  /**
   * The method reads the input source and returns back the data in the form of 3-d integer matrix.
   * In the current application it returns back the image pixel data in the form of a matrix.
   * Based on the extension the PPM or the ImageIO util classes are called to read from the path.
   *
   * @return A 3-d array representing the image pixel data for all the channels-red, green and blue.
   * @throws IOException Throws exception if an error occurred while reading the passed stream path.
   */
  @Override
  public int[][][] read() throws IOException {
    String fileExtension = getFileExtension(this.filePath);
    if (fileExtension.equals("ppm")) {
      return PPMReader.readFileContent(new FileInputStream(filePath));
    } else if(fileExtension.equals("png") || fileExtension.equals("jpg") || fileExtension.equals("jpeg")){
      return ImageIOReader.readFileContent(new FileInputStream(filePath));
    }
    else {
      throw new IOException("Attempt to load in unknown file type extension");
    }
  }
}
