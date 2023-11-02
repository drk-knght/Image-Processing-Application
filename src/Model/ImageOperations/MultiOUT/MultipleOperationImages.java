package Model.ImageOperations.MultiOUT;

import java.util.List;

import Model.RGBImageInterface;

/**
 * The interface represents an image operation which returns a list of images from a single image.
 * The method operate on a single image and divide the images in a way to list of different images.
 */
public interface MultipleOperationImages {

  /**
   * It takes an image as an input and apply some operation on it to divide it into several types.
   * @param rgbImages An image accessible by RGBImageInterface on which the operation is to be done.
   * @return List of images which are generated after applying image processing operation.
   * @throws IllegalArgumentException Exception is thrown If the image passed is not a valid input.
   */
  List<RGBImageInterface> operation(RGBImageInterface rgbImages) throws IllegalArgumentException;
}
