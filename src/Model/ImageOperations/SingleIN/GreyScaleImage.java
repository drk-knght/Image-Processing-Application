package Model.ImageOperations.SingleIN;

import Model.Enums.GreyScaleType;
import Model.RGBImage;
import Model.RGBImageInterface;

/**
 * This class represents the greyscale operation on a single image currently present in the memory.
 * Takes the greyscale type and performs the action on it to get a new black-white image.
 * It checks for validity of inputs passed to it and exception is thrown if invalid.
 */
public class GreyScaleImage implements ImageOperation {

  private final int greyScaleOperationType;

  /**
   * This constructor takes greyscale type which will be used for greyscale action on an image.
   * @param greyScaleOperationType Integer signifying the greyscale mapping with the Enum.
   * @throws IllegalArgumentException Throws exception if an invalid greyscale is passed as args.
   */
  public GreyScaleImage(int greyScaleOperationType) throws IllegalArgumentException{
    if(greyScaleOperationType<0 || greyScaleOperationType>= GreyScaleType.values().length){
      throw new IllegalArgumentException("Grey scale operation is not defined. Please try again.");
    }
    this.greyScaleOperationType = greyScaleOperationType;
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
      throw new IllegalArgumentException("Image passed for grey scale images conversion is not as expected, check again. Aborting!!");
    }
    int height = rgbImage.getImageHeight();
    int width = rgbImage.getImageWidth();
    int[][][] pixelMatrix = rgbImage.getPixel();
    GreyScaleType typeofGreyFilter = findOrdinalName(greyScaleOperationType);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (typeofGreyFilter != null) {
          pixelMatrix[i][j] = typeofGreyFilter.calculateReturnPixelValue(pixelMatrix[i][j]);
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }

  private static GreyScaleType findOrdinalName(int greyScaleOperationType) {
    for (GreyScaleType g : GreyScaleType.values()) {
      if (greyScaleOperationType == g.ordinal()) {
        return g;
      }
    }
    return null;
  }

}
