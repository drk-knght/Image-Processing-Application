package model.imageoperations.singlein;

import java.util.Arrays;

import model.RGBImageInterface;
import model.enums.ColorMapping;

public class Compression implements ImageOperation{

  private final int compressionPercentage;

  public Compression(int compressionPercentage){
    this.compressionPercentage=compressionPercentage;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    int [][][] operatedImageMatrix=getSquareMatrix(rgbImage);
    // 1. Perform haar transformation
    // 2. Perform invhaarTransform

    return null;
  }


  private int[][][] getSquareMatrix(RGBImageInterface rgbImage){
    int height= rgbImage.getImageHeight();
    int width=rgbImage.getImageWidth();
    int heightPad= Math.max(height,getNearest2Power(height));
    int widthPad=Math.max(width,getNearest2Power(width));
    int squareMatrixDimension=Math.max(heightPad,widthPad);
    int [][][] newSquarePaddedMat= new int [squareMatrixDimension][squareMatrixDimension][ColorMapping.values().length];
    copyDataToNewMatrix(newSquarePaddedMat,rgbImage.getPixel());
    return newSquarePaddedMat;
  }

  private void copyDataToNewMatrix(int [][][] newMatrix, int [][][]oldMatrix){
    for(int i=0;i<oldMatrix.length;i++){
      for(int j=0;j<oldMatrix[i].length;j++){
        System.arraycopy(oldMatrix[i][j], 0, newMatrix[i][j], 0, oldMatrix[i][j].length);
      }
    }
  }

  int getNearest2Power(int targetNum){
    int res=1;
    while(res<targetNum){
      res=res*2;
    }
    return res;
  }
}
