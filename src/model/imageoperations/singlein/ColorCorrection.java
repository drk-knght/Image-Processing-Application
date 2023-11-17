package model.imageoperations.singlein;

import java.util.Arrays;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

/**
 * This class represents the color correction operation on a single image currently in the memory.
 * It takes the image and realigns the pixel intensity peaks of the image to a common value.
 * It checks for validity of inputs passed to it and exception is thrown if invalid.
 */
public class ColorCorrection implements ImageOperation {

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * It performs the color correction part of the image presently in the cached collection.
   * Returns the images containing the data that can be accessed and operated by this interface.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed on the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if (rgbImage == null || rgbImage.getImageWidth() <= 0 || rgbImage.getImageHeight() <= 0) {
      throw new IllegalArgumentException("Image passed for Color Correction image "
              + "transformation is not as expected, check again.\n");
    }
    int[] peaksValue = new int[ColorMapping.values().length];
    Arrays.fill(peaksValue, 0);
    int[][][] imgMatrix = rgbImage.getPixel();
    for (int color = 0; color < ColorMapping.values().length; color++) {
      int peakValue = getPeakFreqForSingleChannel(imgMatrix, color);
      peaksValue[color] = peakValue;
    }
    int avg = calculateAvgPeakForImage(peaksValue);
    for (int color = 0; color < ColorMapping.values().length; color++) {
      int deltaShift = 0;
      if (avg != Integer.MAX_VALUE) {
        deltaShift = avg - peaksValue[color];
      }
      colorCorrectedImageMatrix(imgMatrix, color, deltaShift);
    }
    return new RGBImage(imgMatrix);
  }

  private void colorCorrectedImageMatrix(int[][][] pixelMat, int channelMap, int deltaPixelShift) {
    for (int i = 0; i < pixelMat.length; i++) {
      for (int j = 0; j < pixelMat[i].length; j++) {
        pixelMat[i][j][channelMap] += deltaPixelShift;
        pixelMat[i][j][channelMap] = Math.max(0, Math.min(255, pixelMat[i][j][channelMap]));
      }
    }
  }

  private int calculateAvgPeakForImage(int[] colorPeaks) {
    int cntMeaningfulPeaks = 0;
    int peaksSum = 0;
    for (int colorPeak : colorPeaks) {
      if (colorPeak >= 10 && colorPeak <= 245) {
        peaksSum += colorPeak;
        cntMeaningfulPeaks++;
      }
    }
    if (cntMeaningfulPeaks != 0) {
      return (peaksSum / cntMeaningfulPeaks);
    } else return Integer.MAX_VALUE;
  }

  private int getPeakFreqForSingleChannel(int[][][] pixelMatrix, int channelIndex) {
    int[] channelDepthAr = new int[256];
    Arrays.fill(channelDepthAr, 0);
    int maxPeakFreq = 0;
    int peakChannelDepth = 0;
    for (int[][] matrix : pixelMatrix) {
      for (int[] ints : matrix) {
        int channelDepthValue = ints[channelIndex];
        channelDepthAr[channelDepthValue]++;
      }
    }
    for (int i = 0; i < channelDepthAr.length; i++) {
      if (channelDepthAr[i] > maxPeakFreq) {
        maxPeakFreq = channelDepthAr[i];
        peakChannelDepth = i;
      }
    }
    return peakChannelDepth;
  }

}
