package Model.ImageOperations;

import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class FlipImage implements ImageOperation{

  private int axisValue;


  public FlipImage(int axisValue){
    this.axisValue=axisValue;

  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) {
    int height=rgbImage.getImageHeight();
    int width=rgbImage.getImageWidth();
    int [][][]pixelMatrix=rgbImage.getPixel();
    if(this.axisValue== AxisName.horizontal.ordinal()){
      flipHorizontal(height, width,pixelMatrix);
    }
    if(this.axisValue==AxisName.vertical.ordinal()){
      flipVertical(height, width,pixelMatrix);
    }
    return new RGBImage(pixelMatrix);
  }

  private void flipHorizontal(int height, int width, int [][][] pixelMatrix){
    for(int i=0;i<height;i++){
      for(int j=0;j<width/2;j++){
        for(int k = 0; k< ColorMapping.values().length; k++){
          int currentValue=pixelMatrix[i][width-1-j][k];
          pixelMatrix[i][width-1-j][k]=pixelMatrix[i][j][k];
          pixelMatrix[i][j][k]=currentValue;
        }
      }
    }
  }

  private void flipVertical(int height, int width, int [][][] pixelMatrix){
    for(int i=0;i<height/2;i++){
      for(int j=0;j<width;j++){
        for(int k=0;k<ColorMapping.values().length;k++){
          int currentValue=pixelMatrix[height-1-i][j][k];
          pixelMatrix[height-1-i][j][k]=pixelMatrix[i][j][k];
          pixelMatrix[i][j][k]=currentValue;
        }
      }
    }
  }

}