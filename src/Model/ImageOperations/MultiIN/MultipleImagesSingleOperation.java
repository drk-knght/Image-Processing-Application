package Model.ImageOperations.MultiIN;

import java.util.List;

import Model.RGBImageInterface;

/**
 * The interface represents image operation where the input is list of images.
 * It performs an operation on the list of images to return a new result image out of it.
 */
public interface MultipleImagesSingleOperation {

  /**
   * The method takes list of images as input and performs an operation on it to get a final result.
   * @param rgbImages Operands on which an image operation needs to be performed to get an image.
   * @return An image containing the result of the operation on the list of existing images.
   * @throws IllegalArgumentException When the arguments passed during the operations are invalid.
   */
  RGBImageInterface operation(List<RGBImageInterface> rgbImages) throws IllegalArgumentException;
}
