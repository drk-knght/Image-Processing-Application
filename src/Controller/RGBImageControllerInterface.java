package Controller;

import java.io.IOException;

/**
 * This interface represents the central part of controller.
 * The methods are based on command builder design patter.
 */
public interface RGBImageControllerInterface {

  /**
   * The method which gives command for all the image processing application operations.
   * It encapsulates all the helper command classes objects under the method.
   * @throws IOException Throws exception if invalid data is used in the method.
   */
  void go() throws IOException;
}
