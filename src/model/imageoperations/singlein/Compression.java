package model.imageoperations.singlein;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

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

public class Compression implements ImageOperation{

  private final int compressionPercentage;

  public Compression(int compressionPercentage){
    this.compressionPercentage=compressionPercentage;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    double [][][] operatedImageMatrix=getSquareMatrix(rgbImage);
    getCompressedSquareMatrix(operatedImageMatrix);
    int [][][] pixelMat=rgbImage.getPixel();
    getCompressedImgMatrix(operatedImageMatrix,pixelMat);
    return new RGBImage(pixelMat);
  }

  private double[][][] getSquareMatrix(RGBImageInterface rgbImage){
    int height= rgbImage.getImageHeight();
    int width=rgbImage.getImageWidth();
    int heightPad= Math.max(height,getNearest2Power(height));
    int widthPad=Math.max(width,getNearest2Power(width));
    int squareMatrixDimension=Math.max(heightPad,widthPad);
    double [][][] newSquarePaddedMat= new double [ColorMapping.values().length][squareMatrixDimension][squareMatrixDimension];
    copyDataToNewMatrix(newSquarePaddedMat,rgbImage.getPixel());
    return newSquarePaddedMat;
  }

  private void copyDataToNewMatrix(double [][][] newMatrix, int [][][]oldMatrix){
    for(int k=0;k<ColorMapping.values().length;k++){
      for(int i=0;i<oldMatrix.length;i++){
        for(int j=0;j<oldMatrix[i].length;j++){
          newMatrix[k][i][j]=oldMatrix[i][j][k];
        }
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

  private void getCompressedSquareMatrix(double [][][] pixelSquareMatrix){
    for(int i=0;i<ColorMapping.values().length;i++){
      performHaarTransformation(pixelSquareMatrix[i]);
    }
    eliminateSmallValues(pixelSquareMatrix);
    for(int i=0;i<ColorMapping.values().length;i++){
      performInvHaarTransformation(pixelSquareMatrix[i]);
    }
  }

  private void performHaarTransformation(double [][] originalImage){
    int subArrayLength= originalImage.length;
    while(subArrayLength>1) {
      for(int i=0;i<originalImage.length;i++){
        haarWaveletRowTransform(originalImage,i, subArrayLength, this::transform);
      }
      for(int j=0;j<originalImage.length;j++){
        haarWaveletColTransform(originalImage,j, subArrayLength, this::transform);
      }
      subArrayLength/=2;
    }
  }

  private void performInvHaarTransformation(double [][] originalImage){
    int subArrayLength= 2;
    while(subArrayLength<=originalImage.length) {
      for(int j=0;j<originalImage.length;j++){
        haarWaveletColTransform(originalImage,j,subArrayLength, this::inverseTransform);
      }
      for(int i=0;i<originalImage.length;i++){
        haarWaveletRowTransform(originalImage,i, subArrayLength, this::inverseTransform);
      }
      subArrayLength*=2;
    }
  }

  private void haarWaveletRowTransform(double [][] matrix, int row, int length, Function<double[], double[]> haarFunc){
    double [] rowSubArray= Arrays.copyOfRange(matrix[row],0,length);
    double [] transformResultSubArray=haarFunc.apply(rowSubArray);
    System.arraycopy(transformResultSubArray,0,matrix[row],0,length);
  }

  private void haarWaveletColTransform(double [][] matrix, int col, int length,Function<double[], double[]> haarFunc){
    double [] colSubArray=IntStream.range(0, length).mapToDouble(i -> matrix[i][col]).toArray();
    double [] transformResultSubArray=haarFunc.apply(colSubArray);
    for(int i=0;i<transformResultSubArray.length;i++){
      matrix[i][col]=transformResultSubArray[i];
    }
  }

  private double [] transform(double [] originalPixel){
    double [] avgPixelArray=getTransformationArray(originalPixel,
            (a,b)-> ((a + b) / Math.sqrt(2)));
    double [] diffPixelArray=getTransformationArray(originalPixel,
            (a,b)-> ((a - b) / Math.sqrt(2)));
    double [] resultArray=new double[avgPixelArray.length+diffPixelArray.length];
    System.arraycopy(avgPixelArray, 0, resultArray, 0, avgPixelArray.length);
    System.arraycopy(diffPixelArray, 0, resultArray, avgPixelArray.length, diffPixelArray.length);
    return resultArray;
  }

  private double[] getTransformationArray(double [] originalPixel, BiFunction<Double,Double,Double> functionObj){
    double [] resultArray=new double[originalPixel.length/2];
    for(int i=0;i+1<originalPixel.length;i+=2){
      double a=originalPixel[i];
      double b=originalPixel[i+1];
      double operatedValue=functionObj.apply(a,b);
      resultArray[i/2]=operatedValue;
    }
    return resultArray;
  }

  private Set<Double> getSortedAbsPixelValues(double [][][] transformedMatrix){
    Set<Double> set=new TreeSet<>();
    for(int k=0;k<ColorMapping.values().length;k++){
      for(int i=0;i<transformedMatrix[k].length;i++){
        for(int j=0;j<transformedMatrix[k][i].length;j++){
          double num=(double) Math.round(Math.abs(transformedMatrix[k][i][j]*100))/100;
          set.add(num);
        }
      }
    }
    return set;
  }

  private double calculateThreshold(Set<Double> set){
    double num = (double)set.size() * compressionPercentage;
    int cnt=(int)Math.round(num/100);
    double thresholdValue=Double.POSITIVE_INFINITY;

    Iterator<Double> iterator= set.iterator();

    if(set.size()>cnt){
      for(int i=0;i<cnt+1;i++){
        thresholdValue=iterator.next();
      }
    }
    return thresholdValue;
  }

  private void eliminateSmallValues(double [][][] transformedMatrix){
    Set<Double> set=getSortedAbsPixelValues(transformedMatrix);
    double thresholdValue = calculateThreshold(set);
    for(int k=0;k<ColorMapping.values().length;k++){
      for(int i=0;i<transformedMatrix[k].length;i++){
        for(int j=0;j<transformedMatrix[k][i].length;j++){
          if(Math.abs(transformedMatrix[k][i][j])< thresholdValue ){
            transformedMatrix[k][i][j]=0.0;
          }
        }
      }
    }
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

  private void getCompressedImgMatrix(double [][][] operatedImg, int [][][] pixelMat){
    for(int i=0;i<pixelMat.length;i++){
      for(int j=0;j<pixelMat[i].length;j++){
        for(int k=0;k<pixelMat[i][j].length;k++){
          int value=(int)Math.round(Math.abs(operatedImg[k][i][j]));
          pixelMat[i][j][k]=Math.max(0,Math.min(255,value));
        }
      }
    }
  }

//  public static void main(String [] args) throws IOException {
//
//    RGBImageInterface imgColorPolluted=new RGBImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/Koala.ppm");
//    Compression img=new Compression(90);
//    RGBImageInterface resColor=img.operation(imgColorPolluted);
//    String trying="/Users/omagarwal/Desktop/compressed-Image-Koala-new-Lambda.ppm";
//    resColor.saveImage(trying);
//    File outputfile = new File("/Users/omagarwal/Desktop/Histogram-galaxy-new.jpg");
//    ImageIO.write(hr.rgbHistogramGraph, "jpg", outputfile);
//    int [][][]testMat= new int [][][]{
//            {{5, 5, 5}, {3, 3, 3}},
//            {{2, 2, 2}, {4, 4, 4}}
//    };
//    RGBImageInterface image=new RGBImage(testMat);
//    image.saveImage("/Users/omagarwal/Desktop/TestingManualImage.png");
//    ImgCompression img=new ImgCompression(80);
//    RGBImageInterface resColor=img.operation(image);
//    int [][][] resultMat= resColor.getPixel();
//    for(int color=0;color<3;color++){
//      System.out.println("Color: "+color);
//      System.out.println("Printing matrix values: ");
//      for(int i=0;i<resultMat.length;i++){
//        for(int j=0;j<resultMat[i].length;j++){
//          System.out.println("I: "+i+" J: "+j+" VAL: "+resultMat[i][j][color]);
//        }
//      }
//    }

//  }

}
