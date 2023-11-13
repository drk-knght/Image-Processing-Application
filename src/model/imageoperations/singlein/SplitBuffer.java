package model.imageoperations.singlein;

import java.io.IOException;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

public class SplitBuffer implements ImageOperation{

  private final int  splitPercentage;

  private final ImageOperation imageOperation;

  public SplitBuffer(int splitPercentage,ImageOperation imageOperation){
    this.splitPercentage=splitPercentage;
    this.imageOperation=imageOperation;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    int [][][] rgbPixel= rgbImage.getPixel();
    double estimation=((double)splitPercentage*rgbImage.getImageWidth())/100.0;
    int splitWidth=(int)Math.round(estimation);
    try{
      int [][][] imageOperationMatrix=getPixelMatSubset(rgbPixel,rgbImage.getImageHeight(), splitWidth);
      RGBImageInterface operatedImage=imageOperation.operation(new RGBImage(imageOperationMatrix));
      int [][][] operatedPixelMatrix=operatedImage.getPixel();
      copyContentsMatrix(rgbPixel,operatedPixelMatrix, operatedImage.getImageHeight(), operatedImage.getImageWidth());
    }
    catch (IllegalArgumentException ex){
      return new RGBImage(rgbPixel);
    }
    return new RGBImage(rgbPixel);
  }

  private int [][][] getPixelMatSubset(int [][][] pixelMatOriginal, int height, int width){
    int [][][] resultMat=new int [height][width][ColorMapping.values().length];
    copyContentsMatrix(resultMat,pixelMatOriginal,height,width);
    return resultMat;
  }

  private void copyContentsMatrix(int [][][] NonUpdatedMatrix, int [][][] updatedMatrix, int height, int width){
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        for(int k=0;k<ColorMapping.values().length;k++){
          NonUpdatedMatrix[i][j][k]=updatedMatrix[i][j][k];
        }
      }
    }
  }

  public static void main(String [] args) throws IOException {
//    SplitBuffer(int splitPercentage, RGBImageInterface rgbImage,ImageOperation imageOperation)
    RGBImageInterface rgbImage=new RGBImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/Koala.ppm");
    ImageOperation operation=new BrightnessProfilerImage(100);
    SplitBuffer buff=new SplitBuffer(5,operation);
    RGBImageInterface result=buff.operation(rgbImage);
    result.saveImage("/Users/omagarwal/Desktop/Koala-split-brighter-10.ppm");
  }


}
