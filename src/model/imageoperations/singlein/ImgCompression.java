package model.imageoperations.singlein;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

// Overall Process
// 1. Get the image matrix.
// 2. Pad the image matrix to make it a square matrix of dimension to the nearest power of 2.
// 3. Perform Haar Transformation.
// 4. Perform compression by removing the lower values.
// 5. Perform invHaar Transformation.

// Haar Transformation
// 1. Set initial length=2^n
// 2. for length>1
//    Perform row transformation.
//    Perform col transformation.
// 3. Length/=2

// Row Transformation
// 1. Get the row index.
// 2. Get the row sub array and perform transformation.
// 3. Copy back the result to the original array at that position.
// 4. return back to calling point.

// Col Transformation
// 1. Get the col index.
// 2. Get the col sub array and perform the transformation.
// 3. copy back the result to the original array to that position.
// 4. return back to calling point.

public class ImgCompression implements ImageOperation{

  private final int compressionPercentage;

  public ImgCompression(int compressionPercentage){
    this.compressionPercentage=compressionPercentage;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    int [][][] operatedImageMatrix=getSquareMatrix(rgbImage);
    for(int i=0;i<ColorMapping.values().length;i++){
      double [][] resultMat=compressionSingleChannel(operatedImageMatrix,i);
      updateChannelCompressionValue(i,operatedImageMatrix,resultMat);
    }
    int [][][] pixelMat=rgbImage.getPixel();
    removePadding(operatedImageMatrix,pixelMat);
    return new RGBImage(pixelMat);
  }

  private void updateChannelCompressionValue(int channel, int [][][] imgMat, double [][] modifiedImg){
    for(int i=0;i< imgMat.length;i++){
      for(int j=0;j<imgMat[i].length;j++){
        int value=(int)Math.round(modifiedImg[i][j]);
        imgMat[i][j][channel]=Math.max(0,Math.min(255,value));
      }
    }
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

  private int getNearest2Power(int targetNum){
    int res=1;
    while(res<targetNum){
      res=res*2;
    }
    return res;
  }

  private double [][] compressionSingleChannel(int [][][] pixelSquareMatrix, int channelValue){
    double [][] chanelPixelMatrix=getSingleChannelMatrix(pixelSquareMatrix,channelValue);
    performHaarTransformation(chanelPixelMatrix);
    eliminateSmallValues(chanelPixelMatrix);
    performInvHaarTransformation(chanelPixelMatrix);
    return chanelPixelMatrix;
  }

  private void performInvHaarTransformation(double [][] originalImage){
    int subArrayLength= 2;
    while(subArrayLength<=originalImage.length) {
      performSingleInvHaarTransformationIteration(originalImage,subArrayLength);
      subArrayLength*=2;
    }
  }

  private void performSingleInvHaarTransformationIteration(double [][] originalImage, int subArrayLength){
    for(int j=0;j<originalImage.length;j++){
      invHaarTransformColVector(originalImage,j, subArrayLength);
    }

    for(int i=0;i<originalImage.length;i++){
      invHaarTransformRowVector(originalImage,i, subArrayLength);
    }

  }

  private void invHaarTransformRowVector(double [][] matrix, int row, int length){
    double [] rowSubArray= Arrays.copyOfRange(matrix[row],0,length);
    double [] transformResultSubArray=inverseTransform(rowSubArray);
    System.arraycopy(transformResultSubArray,0,matrix[row],0,length);
  }

  private void invHaarTransformColVector(double [][] matrix, int col, int length){
    double [] colSubArray=getColSubArray(matrix,col,length);
    double [] transformResultSubArray=inverseTransform(colSubArray);
    updateColData(matrix,col,transformResultSubArray);
  }

  private double [][] getSingleChannelMatrix(int [][][] pixelSquareMatrix, int channelValue){
    int matrixDim= pixelSquareMatrix.length;
    double [][] resultMat=new double[matrixDim][matrixDim];
    for(int i=0;i<matrixDim;i++){
      for(int j=0;j<matrixDim;j++){
        resultMat[i][j]=pixelSquareMatrix[i][j][channelValue];
      }
    }
    return resultMat;
  }

  private void performHaarTransformation(double [][] originalImage){
    int subArrayLength= originalImage.length;
    while(subArrayLength>1) {
      performSingleHaarTransformationIteration(originalImage,subArrayLength);
      subArrayLength/=2;
    }
  }

  private void performSingleHaarTransformationIteration(double [][] originalImage, int subArrayLength){
    for(int i=0;i<originalImage.length;i++){
      haarTransformRowVector(originalImage,i, subArrayLength);
    }
    for(int j=0;j<originalImage.length;j++){
      haarTransformColVector(originalImage,j, subArrayLength);
    }
  }

  private void haarTransformRowVector(double [][] matrix, int row, int length){
    double [] rowSubArray= Arrays.copyOfRange(matrix[row],0,length);
    double [] transformResultSubArray=transform(rowSubArray);
    System.arraycopy(transformResultSubArray,0,matrix[row],0,length);
  }

  private void haarTransformColVector(double [][] matrix, int col,int length){
    // 1. Get the col index.
    // 2. Get the col sub array and perform the transformation.
    // 3. copy back the result to the original array to that position.
    // 4. return back to calling point.
    double [] colSubArray=getColSubArray(matrix,col,length);
    double [] transformResultSubArray=transform(colSubArray);
    updateColData(matrix,col,transformResultSubArray);
  }

  private double [] getColSubArray(double [][] matrix, int col, int length){
    double [] result=new double[length];
    for(int i=0;i<length;i++){
      result[i]=matrix[i][col];
    }
    return result;
//    IntStream.range(0, length).mapToDouble(i -> matrix[i][col]).toArray();
  }

  private void updateColData(double [][] matrix, int col,double [] newData){
    for(int i=0;i<newData.length;i++){
      matrix[i][col]=newData[i];
    }
  }

  private double [] transform(double [] originalPixel){
    double [] avgPixelArray=new double[originalPixel.length/2];
    double [] diffPixelArray=new double[originalPixel.length/2];
    for(int i=0;i+1<originalPixel.length;i+=2){
      double a=originalPixel[i];
      double b=originalPixel[i+1];
      double avg=(a+b)/Math.sqrt(2);
      double diff=(a-b)/Math.sqrt(2);
      avgPixelArray[i/2]=avg;
      diffPixelArray[i/2]=diff;
    }
    return concatenateArrays(avgPixelArray,diffPixelArray);
  }

  private double [] concatenateArrays(double [] firstArray, double [] secondArray){
    int finalLength= firstArray.length+secondArray.length;
    double [] resultArray=new double[finalLength];
    System.arraycopy(firstArray, 0, resultArray, 0, firstArray.length);
    System.arraycopy(secondArray, 0, resultArray, firstArray.length, secondArray.length);
    return resultArray;
  }

  private void eliminateSmallValues(double [][] transformedMatrix){

    // 1. Get the unique values in the matrix
    // 2. Calculate the max of eliminated value using percentage.
    // 3. Update the matrix

    Set<Double> set=new TreeSet<>();
    for(int i=0;i<transformedMatrix.length;i++){
      for(int j=0;j<transformedMatrix.length;j++){
        set.add(Math.abs(transformedMatrix[i][j]));
      }
    }
    double num = (double)set.size() * compressionPercentage;
    int cnt=(int)Math.round(num/100);
    double firstEle=Double.POSITIVE_INFINITY;
//    for(double d:set){
//
//      cnt--;
//      if(cnt==0){
//        break;
//      }
//    }
    Iterator<Double> iterator= set.iterator();
    if(set.size()>cnt){
      for(int i=0;i<cnt+1;i++){
        firstEle=iterator.next();
      }
    }
    int zeroCalc=0;
    for(int i=0;i<transformedMatrix.length;i++){
      for(int j=0;j<transformedMatrix.length;j++){
        if(Math.abs(transformedMatrix[i][j])< firstEle ){
          transformedMatrix[i][j]=0.0;
          zeroCalc++;
        }
      }
    }
    System.out.println("ZERO: "+zeroCalc);
  }

  private double [] inverseTransform(double [] transformedPixel){
    double [] interleavePixels=new double[transformedPixel.length];
    for(int i=0;i<transformedPixel.length/2;i++){
      double a=transformedPixel[i];
      double b=transformedPixel[i+(transformedPixel.length/2)];
      double avg=(a+b)/Math.sqrt(2);
      double diff=(a-b)/Math.sqrt(2);
      interleavePixels[2*i]=avg;
      interleavePixels[2*i+1]=diff;
    }
    return interleavePixels;
  }

  private void removePadding(int [][][] operatedImg, int [][][] pixelMat){
    for(int i=0;i<pixelMat.length;i++){
      for(int j=0;j<pixelMat[i].length;j++){
        for(int k=0;k<pixelMat[i][j].length;k++){
          pixelMat[i][j][k]=operatedImg[i][j][k];
        }
      }
    }
  }

  public static void main(String [] args) throws IOException {

    RGBImageInterface imgColorPolluted=new RGBImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/Koala.ppm");
    ImgCompression img=new ImgCompression(65);
    RGBImageInterface resColor=img.operation(imgColorPolluted);
    String trying="/Users/omagarwal/Desktop/compressed-Image-Koala.ppm";
    resColor.saveImage(trying);
//    File outputfile = new File("/Users/omagarwal/Desktop/Histogram-galaxy-new.jpg");
//    ImageIO.write(hr.rgbHistogramGraph, "jpg", outputfile);
  }

}
