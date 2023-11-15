package controller.filehandling.reader;

import java.io.IOException;

/**
 * The interface represents the read operation performed on an input stream.
 * The interface handles the read part of the I/O operation from controller in the MVC architecture.
 * The method performs the read operation and returns back 3-d integer of data.
 */
public interface InputReaderInterface {

  /**
   * The method reads the input source and returns back the data in the form of 3-d integer matrix.
   * In the current application it returns back the image pixel data in the form of a matrix.
   *
   * @return A 3-d array representing the image pixel data for all the channels-red, green and blue.
   * @throws IOException Throws exception if an error occurred while reading the passed stream path.
   */
  int [][][] read() throws IOException;
}
