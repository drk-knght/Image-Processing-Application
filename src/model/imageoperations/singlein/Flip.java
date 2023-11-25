package model.imageoperations.singlein;

import enums.AxisName;
import enums.ColorMapping;
import model.RGBImage;
import model.RGBImageInterface;

/**
 * This class represents the flip operation on a single image currently present in the memory.
 * It takes the image and flip along any of the existing axis as required by the client using this.
 * It checks for validity of inputs passed to it and exception is thrown if invalid.
 */
public class Flip implements ImageOperation {

  private final int axisValue;

  /**
   * This constructor takes axis type which will be used for flipping action on an existing image.
   *
   * @param axisValue Integer signifying the Axis name mapping with the Enum.
   * @throws IllegalArgumentException Throws exception if an invalid axis type is passed as args.
   */
  public Flip(int axisValue) {
    if (axisValue < 0 || axisValue >= AxisName.values().length) {
      throw new IllegalArgumentException("Flipping operation value "
              + "passed is not defined in the system. Try again.\n");
    }
    this.axisValue = axisValue;

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
      throw new IllegalArgumentException("Image passed for flip is not as "
              + "expected, check again.\n");
    }
    int height = rgbImage.getImageHeight();
    int width = rgbImage.getImageWidth();
    int[][][] pixelMatrix = rgbImage.getPixel();
    if (this.axisValue == AxisName.horizontal.ordinal()) {
      flipHorizontal(height, width, pixelMatrix);
    }
    if (this.axisValue == AxisName.vertical.ordinal()) {
      flipVertical(height, width, pixelMatrix);
    }
    return new RGBImage(pixelMatrix);
  }

  private void flipHorizontal(int height, int width, int[][][] pixelMatrix) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width / 2; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          int currentValue = pixelMatrix[i][width - 1 - j][k];
          pixelMatrix[i][width - 1 - j][k] = pixelMatrix[i][j][k];
          pixelMatrix[i][j][k] = currentValue;
        }
      }
    }
  }

  private void flipVertical(int height, int width, int[][][] pixelMatrix) {
    for (int i = 0; i < height / 2; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          int currentValue = pixelMatrix[height - 1 - i][j][k];
          pixelMatrix[height - 1 - i][j][k] = pixelMatrix[i][j][k];
          pixelMatrix[i][j][k] = currentValue;
        }
      }
    }
  }

}
