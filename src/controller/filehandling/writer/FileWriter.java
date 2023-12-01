package controller.filehandling.writer;

import java.io.FileOutputStream;
import java.io.IOException;

import model.RGBImageInterface;

/**
 * The class represents an output writer which writes the image data to a given specified file path.
 * The writer can write image data to different file extensions like ppm, jpg, png, etc. in local.
 * The writer supports the image data writing for ppm and any extension supported by imageIO class.
 */
public class FileWriter implements OutputWriterInterface {

  private final String saveFilePath;

  /**
   * The constructor takes the file path where data is to be stored in the local machine.
   *
   * @param saveFilePath String representing the path of file where the image data is to be stored.
   */
  public FileWriter(String saveFilePath) {
    this.saveFilePath = saveFilePath;
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
   * The methods represent the saving operation of the image.
   * An image can be saved to a specified file path in different formats like .ppm, .png, .jpg.
   * This can be done from controller as IO operations should be handled by the controller of MVC.
   * The old and new image both are retained in the memory.
   *
   * @param image RGB Image which needs to be saved to file storage system of the local machine.
   * @throws IOException Throws exception if the image data could not be written to that path.
   */
  @Override
  public void write(RGBImageInterface image) throws IOException {
    String extension = getFileExtension(saveFilePath);
    if (extension.equals("ppm")) {
      PPMWriter.writeToStorageDisk(image, new FileOutputStream(saveFilePath));
    } else if (extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg")) {
      ImageIOWriter.writeToStorageDisk(image, new FileOutputStream(saveFilePath), extension);
    } else {
      throw new IOException("Attempt to save in unknown file type extension");
    }
  }
}
