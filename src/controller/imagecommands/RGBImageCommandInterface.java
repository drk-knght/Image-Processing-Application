package controller.imagecommands;

import java.io.IOException;
import java.util.Map;

import model.RGBImageInterface;

/**
 * The interface represents the interface which wraps a single common method for all the operations.
 * The method can perform all the image processing application operation as required.
 */
public interface RGBImageCommandInterface {

  /**
   * Method represents the image operations which needs to be performed by the application.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws NullPointerException Throws exception if the input is of null type.
   * @throws IOException          throws exception if there is an error when reading files for op.
   */
  void execute(Map<String, RGBImageInterface> cachedImage) throws NullPointerException, IOException;
}
