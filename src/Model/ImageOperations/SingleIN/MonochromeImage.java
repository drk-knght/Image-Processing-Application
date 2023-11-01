package Model.ImageOperations.SingleIN;

import java.io.IOException;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class MonochromeImage implements ImageOperation {

  private final int colorComponent;


  public MonochromeImage(int colorComponent) throws IllegalArgumentException {
    if(colorComponent<0 || colorComponent>=ColorMapping.values().length){
      throw new IllegalArgumentException("Single channel operation value passed is not defined in the system. Try again.");
    }
    this.colorComponent = colorComponent;
  }

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
