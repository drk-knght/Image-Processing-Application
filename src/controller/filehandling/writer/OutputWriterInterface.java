package controller.filehandling.writer;

import java.io.IOException;

import model.RGBImageInterface;

/**
 * The interface represents the write operation performed on an output stream.
 * It handles the write part of the I/O operation from controller in the MVC architecture.
 * The method performs the write operation to write contents of the image object to the stream.
 */
public interface OutputWriterInterface {

  /**
   * The methods represent the saving operation of the image.
   * An image can be saved to a specified file path in different formats like .ppm, .png, .jpg.
   * This can be done from controller as IO operations should be handled by the controller of MVC.
   * The old and new image both are retained in the memory.
   *
   * @param image RGB Image which needs to be saved to file storage system of the local machine.
   * @throws IOException Throws exception if the image data could not be written to that path.
   */
  void write(RGBImageInterface image) throws IOException;
}
