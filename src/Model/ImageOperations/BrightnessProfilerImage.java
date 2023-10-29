package Model.ImageOperations;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class BrightnessProfilerImage implements ImageOperation{

  int deltaChangeValue;


  public BrightnessProfilerImage(int deltaChangeValue){
    this.deltaChangeValue=deltaChangeValue;
  }
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) {
    int height=rgbImage.getImageHeight();
    int width=rgbImage.getImageWidth();
    int [][][]pixelMatrix=rgbImage.getPixel();
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        for(int k = 0; k< ColorMapping.values().length; k++){
          pixelMatrix[i][j][k]+=deltaChangeValue;
          if(pixelMatrix[i][j][k]>255){
            pixelMatrix[i][j][k]=255;
          }
          if(pixelMatrix[i][j][k]<0){
            pixelMatrix[i][j][k]=0;
          }
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }
}
