//package model.imageoperations.singlein;
//
//
//import java.util.Arrays;
//import java.util.stream.IntStream;
//
//import model.RGBImageInterface;
//import model.enums.ColorMapping;
//
//public class Compression implements ImageOperation{
//
//  private final int compressionPercentage;
//
//  public Compression(int compressionPercentage){
//    this.compressionPercentage=compressionPercentage;
//  }
//
//  @Override
//  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
//    int [][][] operatedImageMatrix=getSquareMatrix(rgbImage);
//    // 1. Perform haar transformation
//    // 2. Perform invhaarTransform
//
//    return null;
//  }
//
//  private int[][][] getSquareMatrix(RGBImageInterface rgbImage){
//    int height= rgbImage.getImageHeight();
//    int width=rgbImage.getImageWidth();
//    int heightPad= Math.max(height,getNearest2Power(height));
//    int widthPad=Math.max(width,getNearest2Power(width));
//    int squareMatrixDimension=Math.max(heightPad,widthPad);
//    int [][][] newSquarePaddedMat= new int [squareMatrixDimension][squareMatrixDimension][ColorMapping.values().length];
//    copyDataToNewMatrix(newSquarePaddedMat,rgbImage.getPixel());
//    return newSquarePaddedMat;
//  }
//
//  private void copyDataToNewMatrix(int [][][] newMatrix, int [][][]oldMatrix){
//    for(int i=0;i<oldMatrix.length;i++){
//      for(int j=0;j<oldMatrix[i].length;j++){
//        System.arraycopy(oldMatrix[i][j], 0, newMatrix[i][j], 0, oldMatrix[i][j].length);
//      }
//    }
//  }
//
//  int getNearest2Power(int targetNum){
//    int res=1;
//    while(res<targetNum){
//      res=res*2;
//    }
//    return res;
//  }
//
//  private double [] transform(double [] originalPixel){
//    double [] avgPixelArray=new double[originalPixel.length/2];
//    double [] diffPixelArray=new double[originalPixel.length/2];
//    for(int i=0;i+1<originalPixel.length;i+=2){
//      double a=originalPixel[i];
//      double b=originalPixel[i+1];
//      double avg=(a+b)/Math.sqrt(2);
//      double diff=(a-b)/Math.sqrt(2);
//      avgPixelArray[i/2]=avg;
//      diffPixelArray[i/2]=diff;
//    }
//    return concatenateArrays(avgPixelArray,diffPixelArray);
//  }
//
//  double [] concatenateArrays(double [] firstArray, double [] secondArray){
//    int finalLength= firstArray.length+secondArray.length;
//    double [] resultArray=new double[finalLength];
//    System.arraycopy(firstArray, 0, resultArray, 0, firstArray.length);
//    System.arraycopy(secondArray, 0, resultArray, firstArray.length, secondArray.length);
//    return resultArray;
//  }
//
//  private double [] inverseTransform(double [] transformedPixel){
//    double [] interleavePixels=new double[transformedPixel.length];
//    for(int i=0;i<transformedPixel.length/2;i++){
//      double a=transformedPixel[i];
//      double b=transformedPixel[i+(transformedPixel.length/2)];
//      double avg=(a+b)/Math.sqrt(2);
//      double diff=(a-b)/Math.sqrt(2);
//      interleavePixels[2*i]=avg;
//      interleavePixels[2*i+1]=diff;
//    }
//    return interleavePixels;
//  }
//
//  private double [][] haarOperation(double [][] originalImage){
//    int subArrayLength= originalImage.length;
//    while(subArrayLength>1){
//      // row Transformation
//      for(int i=0;i<originalImage.length;i++){
//        // X[i, 1....c]= T(X[i, 1....c])
//        double [] subX=Arrays.copyOfRange(originalImage[i],0,subArrayLength-1);
//        double [] rowTransformResult=transform(subX);
//        System.arraycopy(rowTransformResult, 0, originalImage[i], 0, rowTransformResult.length);
//      }
//
//      // col Transformation
//      for(int j=0;j<originalImage.length;j++){
//        // X[1....c, j]= T(X[1....c, j])
//        double []subY=getColumnArray(originalImage,j);
//        double [] colTransformResult=transform(subY);
//
//      }
//    }
//  }
//
//  double [] getColumnArray(double [][] matrix, int colIndex){
//      return IntStream.range(0, matrix.length) .mapToDouble(i -> matrix[i][colIndex]).toArray();
//  }
//
//  private void transferData(double [] oldData, double [] newData){
//    System.arraycopy(newData, 0, oldData, 0, oldData.length);
//  }
//  public static void main(String [] args){
//    double [] ar={5,3,2,4,2,1,0,3};
//    Compression cp=new Compression(40);
//    double [] trans=cp.transform(ar);
//    System.out.println(Arrays.toString(trans));
//
//    double [] invTrans=cp.inverseTransform(trans);
//    System.out.println(Arrays.toString(invTrans));
//  }
//}
