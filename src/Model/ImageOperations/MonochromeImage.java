package Model.ImageOperations;

import java.io.IOException;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class MonochromeImage implements  ImageOperation{

  private int colorComponent;


  public MonochromeImage(int colorComponent){
    this.colorComponent=colorComponent;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IOException {
    int height=rgbImage.getImageHeight();
    int width=rgbImage.getImageWidth();
    int [][][]pixelMatrix=rgbImage.getPixel();
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        for(int k = 0; k< ColorMapping.values().length; k++){
          if(k!=colorComponent){
            pixelMatrix[i][j][k]=0;
          }
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }

}
