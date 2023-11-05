package model.imageoperations.singlein;


import model.RGBImageInterface;

/**
 * The interface represents an image operation which returns a modified version of the existing one.
 * It operates on an existing image and return back a newly modified image of same depth.
 */
public interface ImageOperation {

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * Returns the images containing the data that can be accessed and operated by this interface.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed n the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException;
}
