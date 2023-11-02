package Model.ImageOperations.SingleIN;


import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

/**
 * This class represents the Monochrome operation on a single image currently present in the memory.
 * Takes the color component type and performs the action on it to get a new color-greyscale image.
 * It checks for validity of inputs passed to it and exception is thrown if invalid.
 */
public class MonochromeImage implements ImageOperation {

  private final int colorComponent;

  /**
   * This constructor takes color component type which will be used for color-greyscale action.
   * @param colorComponent Integer signifying the color component for which greyscale is done.
   * @throws IllegalArgumentException Throws exception if an invalid kernel type is passed as args.
   */
  public MonochromeImage(int colorComponent) throws IllegalArgumentException {
    if(colorComponent<0 || colorComponent>=ColorMapping.values().length){
      throw new IllegalArgumentException("Single channel operation value passed is not defined in the system. Try again.");
    }
    this.colorComponent = colorComponent;
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * Returns the images containing the data that can be accessed and operated by this interface.
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed n the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException{
    if(rgbImage==null || rgbImage.getImageWidth()<=0 || rgbImage.getImageHeight()<=0){
      throw new IllegalArgumentException("Image passed for monochrome images is not as expected, check again. Aborting!!");
    }
    int height = rgbImage.getImageHeight();
    int width = rgbImage.getImageWidth();
    int[][][] pixelMatrix = rgbImage.getPixel();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          if (k != colorComponent) {
            pixelMatrix[i][j][k] = 0;
          }
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }

}
