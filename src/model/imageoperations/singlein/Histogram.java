package model.imageoperations.singlein;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.Arrays;



import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

public class Histogram implements ImageOperation{

  private final BufferedImage rgbHistogramGraph;

  private final Graphics2D g;

  private static final int histogramImageWidth=256;

  private static final int histogramImageHeight=256;

  private final int [][] colorDepthFreqMap;

  public Histogram(){
    rgbHistogramGraph=new BufferedImage(histogramImageWidth,histogramImageHeight,BufferedImage.TYPE_3BYTE_BGR);
    g=rgbHistogramGraph.createGraphics();
    colorDepthFreqMap=new int [ColorMapping.values().length][256];
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    setBackground();
    int [][][] pixelMatrix= rgbImage.getPixel();
    int cumulativePeakMaxVal=0;
    for(int i=0;i<ColorMapping.values().length;i++){
      colorDepthFreqMap[i]=singleChannelFreq(pixelMatrix,i);
      cumulativePeakMaxVal=Math.max(cumulativePeakMaxVal,getMaxFreq(colorDepthFreqMap[i]));
    }
    drawHistogram(colorDepthFreqMap,cumulativePeakMaxVal);

    return new RGBImage(convertBufferToPixelMat());
  }

  private void drawHistogram(int [][]channelPixelValues,int maxPeakFreq){
    for(int i=0;i<ColorMapping.values().length;i++){
      Color c=getColor(i);
      drawSingleChannel(channelPixelValues[i],maxPeakFreq,c);
    }
  }

  private void drawSingleChannel(int [] channelFreq, int maxPeakFreq, Color penColor){
    g.setColor(penColor);
    for(int i=0;i+1<channelFreq.length;i++){
      g.drawLine(i,255-(channelFreq[i]*256)/maxPeakFreq,i+1,255-(channelFreq[i+1]*256)/maxPeakFreq);
    }
  }

  private int getMaxFreq(int [] ar){
    int max=0;
    for(int num:ar){
      max=Math.max(max,num);
    }
    return max;
  }

  private int [] singleChannelFreq(int [][][] pixelMatrix, int channelVal){
    int [] freqMap=new int[256];
    Arrays.fill(freqMap,0);
    for (int[][] matrix : pixelMatrix) {
      for (int[] ints : matrix) {
        int channelDepthValue = ints[channelVal];
        freqMap[channelDepthValue]++;
      }
    }
    return freqMap;
  }

  private Color getColor(int colorMapValue){
    for(ColorMapping colormap: ColorMapping.values()){
      if(colormap.ordinal()==colorMapValue){
        return colormap.color;
      }
    }
    return null;
  }

  private void setBackground(){
    this.g.setBackground(Color.WHITE);
    this.g.fillRect(0,0,histogramImageWidth,histogramImageHeight);
  }

  private int [][][] convertBufferToPixelMat(){
    int[][][] resultImgMat=new int [histogramImageHeight][histogramImageWidth][ColorMapping.values().length];
    for (int i = 0; i < histogramImageHeight; i++) {
      for (int j = 0; j < histogramImageWidth; j++) {
        int rgbCellValue = rgbHistogramGraph.getRGB(j, i);
        resultImgMat[i][j][ColorMapping.red.ordinal()] = (rgbCellValue >> 16) & 255;
        resultImgMat[i][j][ColorMapping.green.ordinal()] = (rgbCellValue >> 8) & 255;
        resultImgMat[i][j][ColorMapping.blue.ordinal()] = (rgbCellValue) & 255;
      }
    }
    return resultImgMat;
  }

  public static void main(String[] args) throws IOException {
    RGBImageInterface imgColorPolluted=new RGBImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/Koala.ppm");
    Histogram hrColorPolluted=new Histogram();
    RGBImageInterface resColor=hrColorPolluted.operation(imgColorPolluted);
    String trying="/Users/omagarwal/Desktop/Img-Koala.ppm";
    resColor.saveImage(trying);
//
//    RGBImageInterface imgColorCorrected=new ColorCorrection().operation(imgColorPolluted);
//    Histogram hrColorCorrected=new Histogram();
//    RGBImageInterface resColorCorrected=hrColorCorrected.operation(imgColorCorrected);
//    String try2="/Users/omagarwal/Desktop/Img-galaxy-Corrected.ppm";
//    resColorCorrected.saveImage(try2);
//    File outputfile = new File("/Users/omagarwal/Desktop/Histogram-galaxy-new.jpg");
//    ImageIO.write(hr.rgbHistogramGraph, "jpg", outputfile);
  }
}
