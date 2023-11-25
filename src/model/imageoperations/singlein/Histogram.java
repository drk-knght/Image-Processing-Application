package model.imageoperations.singlein;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

import java.util.Arrays;


import model.RGBImage;
import model.RGBImageInterface;
import enums.ColorMapping;

/**
 * This class represents the histogram plotting operation on rbg pixel values of the image.
 * The classes use a graphics 2d object on buffer image and draws the line graph on it.
 * The graph represents the pixel intensity values Vs. frequency of its occurrences for r,b and b.
 */
public class Histogram implements ImageOperation {

  private final BufferedImage rgbHistogramGraph;

  private final Graphics2D g;

  private static final int histogramImageWidth = 256;

  private static final int histogramImageHeight = 256;

  private final int[][] colorDepthFreqMap;

  /**
   * The constructor creates the buffer image on which the graph is to be drawn.
   * It also creates graphics 2d object which draws on the buffer object for the individual pixels.
   * A new array is created to store the freq of occurrences of each of the channels' intensity.
   */
  public Histogram() {
    rgbHistogramGraph = new BufferedImage(histogramImageWidth,
            histogramImageHeight, BufferedImage.TYPE_3BYTE_BGR);
    g = rgbHistogramGraph.createGraphics();
    colorDepthFreqMap = new int[ColorMapping.values().length][256];
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * It plots the line-graph containing the freq and 256-greyscale values for all the channels.
   * Returns the images containing the data that can be accessed and operated by this interface.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed on the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if (rgbImage == null || rgbImage.getImageWidth() <= 0 || rgbImage.getImageHeight() <= 0) {
      throw new IllegalArgumentException("Image passed for histogram image "
              + "transformation is not as expected, check again.\n");
    }
    setBackground();
    int[][][] pixelMatrix = rgbImage.getPixel();
    int cumulativePeakMaxVal = 0;
    for (int i = 0; i < ColorMapping.values().length; i++) {
      colorDepthFreqMap[i] = singleChannelFreq(pixelMatrix, i);
      cumulativePeakMaxVal = Math.max(cumulativePeakMaxVal, getMaxFreq(colorDepthFreqMap[i]));
    }
    drawHistogram(colorDepthFreqMap, cumulativePeakMaxVal);

    return new RGBImage(convertBufferToPixelMat());
  }

  private void drawHistogram(int[][] channelPixelValues, int maxPeakFreq) {
    for (int i = 0; i < ColorMapping.values().length; i++) {
      Color c = getColor(i);
      drawSingleChannel(channelPixelValues[i], maxPeakFreq, c);
    }
  }

  private void drawSingleChannel(int[] channelFreq, int maxPeakFreq, Color penColor) {
    g.setColor(penColor);
    for (int i = 0; i + 1 < channelFreq.length; i++) {
      g.drawLine(i, 255 - (channelFreq[i] * 256) / maxPeakFreq,
              i + 1, 255 - (channelFreq[i + 1] * 256) / maxPeakFreq);
    }
  }

  private int getMaxFreq(int[] ar) {
    int max = 0;
    for (int num : ar) {
      max = Math.max(max, num);
    }
    return max;
  }

  private int[] singleChannelFreq(int[][][] pixelMatrix, int channelVal) {
    int[] freqMap = new int[256];
    Arrays.fill(freqMap, 0);
    for (int[][] matrix : pixelMatrix) {
      for (int[] ints : matrix) {
        int channelDepthValue = ints[channelVal];
        freqMap[channelDepthValue]++;
      }
    }
    return freqMap;
  }

  private Color getColor(int colorMapValue) {
    for (ColorMapping colormap : ColorMapping.values()) {
      if (colormap.ordinal() == colorMapValue) {
        return colormap.color;
      }
    }
    return null;
  }

  private void setBackground() {
    this.g.setBackground(Color.WHITE);
    this.g.fillRect(0, 0, histogramImageWidth, histogramImageHeight);
  }

  private int[][][] convertBufferToPixelMat() {
    int[][][] resultImgMat = new int
            [histogramImageHeight][histogramImageWidth][ColorMapping.values().length];
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

}
