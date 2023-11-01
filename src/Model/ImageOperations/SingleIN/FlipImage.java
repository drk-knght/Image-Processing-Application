package Model.ImageOperations.SingleIN;

import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class FlipImage implements ImageOperation {

  private final int axisValue;


  public FlipImage(int axisValue) {
    if(axisValue<0 || axisValue>=AxisName.values().length){
      throw new IllegalArgumentException("Flipping operation value passed is not defined in the system. Try again.");
    }
    this.axisValue = axisValue;

  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if(rgbImage==null || rgbImage.getImageWidth()<=0 || rgbImage.getImageHeight()<=0){
      throw new IllegalArgumentException("Image passed for flip is not as expected, check again. Aborting!!");
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
