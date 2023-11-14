package model.imageoperations.singlein;

import model.enums.ColorMapping;
import model.RGBImage;
import model.RGBImageInterface;

/**
 * This class represents the brightness operation on a single image currently present in the memory.
 * It takes the image and change the brightness of the image as required by the client using this.
 * It checks for validity of inputs passed to it and exception is thrown if invalid.
 */
public class Brightness implements ImageOperation {

  private final int deltaChangeValue;

  /**
   * This constructor takes numerical change required for each pixel of the img as part of this op.
   *
   * @param deltaChangeValue Integer signifying the change in the pixel value that needs to be done.
   * @throws IllegalArgumentException Throws exception if an invalid axis type is passed as args.
   */
  public Brightness(int deltaChangeValue) {
    this.deltaChangeValue = deltaChangeValue;
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * Returns the images containing the data that can be accessed and operated by this interface.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed n the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if (rgbImage == null || rgbImage.getImageWidth() <= 0 || rgbImage.getImageHeight() <= 0) {
      throw new IllegalArgumentException("Image passed for changing the brightness of image "
             + "transformation is not as expected, check again. Aborting!!");
    }
    int height = rgbImage.getImageHeight();
    int width = rgbImage.getImageWidth();
    int[][][] pixelMatrix = rgbImage.getPixel();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          pixelMatrix[i][j][k] += deltaChangeValue;
          pixelMatrix[i][j][k] = Math.max(0, Math.min(255, pixelMatrix[i][j][k]));
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }
}
