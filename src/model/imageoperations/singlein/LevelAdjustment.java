package model.imageoperations.singlein;

import java.io.IOException;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

public class LevelAdjustment implements ImageOperation{

  private final int blackPoint;

  private final int midPoint;

  private final int highlightPoint;

  public LevelAdjustment(int blackPoint,int midPoint,int highlightPoint){
    this.blackPoint=blackPoint;
    this.midPoint=midPoint;
    this.highlightPoint=highlightPoint;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    int [][][] pixelMatrix= rgbImage.getPixel();
    double denominator=computeDenominator();
    double a=computeA(denominator);
    double b=computeB(denominator);
    double c=computeC(denominator);
    adjustLevelImage(pixelMatrix,a,b,c);
    return new RGBImage(pixelMatrix);
  }

  private void adjustLevelImage(int [][][] pixelMatrix, double a, double b, double c){
    for(int i=0;i<pixelMatrix.length;i++){
      for(int j=0;j<pixelMatrix[i].length;j++){
        for(int k=0;k< ColorMapping.values().length;k++){
          pixelMatrix[i][j][k]=computePixelAdjustment(pixelMatrix[i][j][k],a,b,c);
          pixelMatrix[i][j][k]=Math.max(0,Math.min(255,pixelMatrix[i][j][k]));
        }
      }
    }
  }

  private int computePixelAdjustment(int oldValue, double a, double b,double c){
    double res=(a*oldValue*oldValue)+(b*oldValue)+c;
    return (int)res;
  }

  private double computeDenominator(){
    double commonPart=midPoint-highlightPoint;
    double simplifiedPart=((blackPoint*blackPoint)-(blackPoint*(midPoint+highlightPoint))+midPoint*highlightPoint);
    return commonPart*simplifiedPart;
  }

  private double computeA(double denominator){
    double numerator=127*blackPoint+128*highlightPoint-255*midPoint;
    return numerator/denominator;
  }

  private double computeB(double denominator){
    double numerator=(-127*blackPoint*blackPoint)+(255*midPoint*midPoint)-(128*highlightPoint*highlightPoint);
    return numerator/denominator;
  }

  private double computeC(double denominator){
    double numeratorPartA=(blackPoint*blackPoint)*(255*midPoint-128*highlightPoint);
    double numeratorPartB=(-blackPoint)*((255*midPoint*midPoint)-(128*highlightPoint*highlightPoint));
    double numerator= numeratorPartA+numeratorPartB;
    return numerator/denominator;
  }

  public static void main(String[] args) throws IOException {
    RGBImageInterface img=new RGBImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/galaxy.png");
    LevelAdjustment hr=new LevelAdjustment(20,100,255);
    RGBImageInterface res=hr.operation(img);
    Histogram h=new Histogram();
    RGBImageInterface graph=h.operation(res);
    String trying="/Users/omagarwal/Desktop/Level-Adjustment.png";
    res.saveImage(trying);
    String histogram="/Users/omagarwal/Desktop/Level-Adjustment-Histogram.png";
    graph.saveImage(histogram);
//    File outputfile = new File("/Users/omagarwal/Desktop/Histogram-galaxy-new.jpg");
//    ImageIO.write(hr.rgbHistogramGraph, "jpg", outputfile);
  }
}
