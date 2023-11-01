package Model.ImageOperations.SingleIN;

import Model.Enums.GreyScaleType;
import Model.RGBImage;
import Model.RGBImageInterface;

public class GreyScaleImage implements ImageOperation {

  int greyScaleOperationType;

  public GreyScaleImage(int greyScaleOperationType) throws IllegalArgumentException{
    if(greyScaleOperationType<0 || greyScaleOperationType>= GreyScaleType.values().length){
      throw new IllegalArgumentException("Grey scale operation is not defined. Please try again.");
    }
    this.greyScaleOperationType = greyScaleOperationType;
  }

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
