package model.imageoperations.singlein;

import model.RGBImage;
import model.RGBImageInterface;
import enums.ColorMapping;

/**
 * This class represents the level adjustment operation on rbg pixel values of the image.
 * The class uses three points black point, midpoint and white point which are to be fit in the
 * quadratic equation to get the desired curve.
 * It implements the ImageOperation interface.
 */
public class LevelsAdjustment implements ImageOperation {

  private final double blackPoint;

  private final double midPoint;

  private final double highlightPoint;

  /**
   * This method is the constructor of the class which implements ImageOperation interface.
   *
   * @param blackPoint     Represents the dark regions which contribute to the value on the left
   *                       of the histogram.
   * @param midPoint       Represents the midpoint on a histogram which signifies
   *                       values closer to the average of a channel.
   * @param highlightPoint Represents the bright regions which contribute to the
   *                       value on the right of the histogram.
   */
  public LevelsAdjustment(double blackPoint, double midPoint, double highlightPoint) {
    if (blackPoint >= midPoint || midPoint >= highlightPoint || blackPoint < 0
            || highlightPoint > 255) {
      throw new IllegalArgumentException("Wrong values for levels "
              + "adjustment operation. Check values of B, M, W again.\n");
    }
    this.blackPoint = blackPoint;
    this.midPoint = midPoint;
    this.highlightPoint = highlightPoint;
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * It performs various mathematical calculations to return a level adjusted image.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return Returns an image which is level adjusted.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if (rgbImage == null || rgbImage.getImageWidth() <= 0 || rgbImage.getImageHeight() <= 0) {
      throw new IllegalArgumentException("Image passed for levels adjustment image "
              + "transformation is not as expected, check again.\n");
    }
    int[][][] pixelMatrix = rgbImage.getPixel();
    double denominator = computeDenominator();
    double a = computeA(denominator);
    double b = computeB(denominator);
    double c = computeC(denominator);
    adjustLevelImage(pixelMatrix, a, b, c);
    return new RGBImage(pixelMatrix);
  }

  private void adjustLevelImage(int[][][] pixelMatrix, double a, double b, double c) {
    for (int i = 0; i < pixelMatrix.length; i++) {
      for (int j = 0; j < pixelMatrix[i].length; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          pixelMatrix[i][j][k] = computePixelAdjustment(pixelMatrix[i][j][k], a, b, c);
          pixelMatrix[i][j][k] = Math.max(0, Math.min(255, pixelMatrix[i][j][k]));
        }
      }
    }
  }

  private int computePixelAdjustment(int oldValue, double a, double b, double c) {
    double res = (a * oldValue * oldValue) + (b * oldValue) + c;
    return (int) res;
  }

  private double computeDenominator() {
    double commonPart = midPoint - highlightPoint;
    double simplifiedPart = ((blackPoint * blackPoint) - (blackPoint * (midPoint + highlightPoint))
            + midPoint * highlightPoint);
    return commonPart * simplifiedPart;
  }

  private double computeA(double denominator) {
    double numerator = 127 * blackPoint + 128 * highlightPoint - 255 * midPoint;
    return numerator / denominator;
  }

  private double computeB(double denominator) {
    double numerator = (-127 * blackPoint * blackPoint) + (255 * midPoint * midPoint)
            - (128 * highlightPoint * highlightPoint);
    return numerator / denominator;
  }

  private double computeC(double denominator) {
    double numeratorPartA = (blackPoint * blackPoint) * (255 * midPoint - 128 * highlightPoint);
    double numeratorPartB = (-blackPoint) * ((255 * midPoint * midPoint)
            - (128 * highlightPoint * highlightPoint));
    double numerator = numeratorPartA + numeratorPartB;
    return numerator / denominator;
  }

}
