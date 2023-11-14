package model.imageoperations.singlein;

import java.io.IOException;
import java.util.Arrays;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

public class ColorCorrection implements ImageOperation{

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    int [] peaksValue=new int[ColorMapping.values().length];
    Arrays.fill(peaksValue,0);
    int [][][] imgMatrix=rgbImage.getPixel();
    for(int color=0;color<ColorMapping.values().length;color++){
      int peakValue=getPeakFreqForSingleChannel(imgMatrix,color);
      peaksValue[color]=peakValue;
    }
    int avg=calculateAvgPeakForImage(peaksValue);
    for(int color=0;color<ColorMapping.values().length;color++){
      int deltaShift=avg-peaksValue[color];
      colorCorrectedImageMatrix(imgMatrix,color,deltaShift);
    }
    return new RGBImage(imgMatrix);
  }

  private void colorCorrectedImageMatrix(int [][][] pixelMat,int channelMap,int deltaPixelShift){
    for(int i=0;i<pixelMat.length;i++){
      for(int j=0;j<pixelMat[i].length;j++){
        pixelMat[i][j][channelMap]+=deltaPixelShift;
        pixelMat[i][j][channelMap]=Math.max(0,Math.min(255,pixelMat[i][j][channelMap]));
      }
    }
  }

  private int calculateAvgPeakForImage(int [] colorPeaks){
    int cntMeaningfulPeaks=0;
    int peaksSum=0;
    for (int colorPeak : colorPeaks) {
      if (colorPeak > 10 && colorPeak < 245) {
        peaksSum += colorPeak;
        cntMeaningfulPeaks++;
      }
    }
    if(cntMeaningfulPeaks!=0){
      return (peaksSum/cntMeaningfulPeaks);
    }
    else return 0;
  }

  private int getPeakFreqForSingleChannel(int [][][] pixelMatrix, int channelIndex){
    int [] channelDepthAr=new int [256];
    Arrays.fill(channelDepthAr,0);
    int maxPeakFreq=0;
    int peakChannelDepth=0;
    for (int[][] matrix : pixelMatrix) {
      for (int[] ints : matrix) {
        int channelDepthValue = ints[channelIndex];
        channelDepthAr[channelDepthValue]++;
      }
    }
    for(int i=0;i<channelDepthAr.length;i++){
      if(channelDepthAr[i]>maxPeakFreq){
        maxPeakFreq=channelDepthAr[i];
        peakChannelDepth=i;
      }
    }
    return peakChannelDepth;
  }

//  public static void main(String [] args) throws IOException {
//    RGBImageInterface img=new RGBImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/Tiger-red-tint.png");
//    ColorCorrection cr=new ColorCorrection();
//    RGBImageInterface res=cr.operation(img);
//    res.saveImage("/Users/omagarwal/Desktop/Koala-Color-Corrected.png");
//  }
}
