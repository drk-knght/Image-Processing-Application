package model.imageoperations.singlein;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import model.RGBImage;
import model.RGBImageInterface;
import enums.ColorMapping;

/**
 * This class represents the compression operation on an image.
 * The class takes the percentage of values to be removed for the compression as a field.
 * The new image has lower definition compared to its original one.
 */
public class Compression implements ImageOperation {

  private final double compressionPercentage;

  /**
   * The constructor of the class takes the percentage by which image needs to be compressed.
   * Assigns back the value to its private field for the operation
   *
   * @param compressionPercentage Double representing the ratio by which img needs to be compressed.
   */
  public Compression(double compressionPercentage) throws IllegalArgumentException {
    if (compressionPercentage < 0 || compressionPercentage > 100) {
      throw new IllegalArgumentException("Compression percentage is invalid\n");
    }
    this.compressionPercentage = compressionPercentage;
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * It performs the compression part of the image where the image becomes more pixelated.
   * Returns the images containing the data that can be accessed and operated by this interface.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed on the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if (rgbImage == null || rgbImage.getImageWidth() <= 0 || rgbImage.getImageHeight() <= 0) {
      throw new IllegalArgumentException("Image passed for compression image "
              + "transformation is not as expected, check again.\n");
    }
    double[][][] operatedImageMatrix = getSquareMatrix(rgbImage);
    getCompressedSquareMatrix(operatedImageMatrix);
    int[][][] pixelMat = rgbImage.getPixel();
    getCompressedImgMatrix(operatedImageMatrix, pixelMat);
    return new RGBImage(pixelMat);
  }

  private double[][][] getSquareMatrix(RGBImageInterface rgbImage) {
    int height = rgbImage.getImageHeight();
    int width = rgbImage.getImageWidth();
    int heightPad = Math.max(height, getNearest2Power(height));
    int widthPad = Math.max(width, getNearest2Power(width));
    int squareMatrixDimension = Math.max(heightPad, widthPad);
    double[][][] newSquarePaddedMat = new double
            [ColorMapping.values().length][squareMatrixDimension][squareMatrixDimension];
    copyDataToNewMatrix(newSquarePaddedMat, rgbImage.getPixel());
    return newSquarePaddedMat;
  }

  private void copyDataToNewMatrix(double[][][] newMatrix, int[][][] oldMatrix) {
    for (int k = 0; k < ColorMapping.values().length; k++) {
      for (int i = 0; i < oldMatrix.length; i++) {
        for (int j = 0; j < oldMatrix[i].length; j++) {
          newMatrix[k][i][j] = oldMatrix[i][j][k];
        }
      }
    }
  }

  private int getNearest2Power(int targetNum) {
    int res = 1;
    while (res < targetNum) {
      res = res * 2;
    }
    return res;
  }

  private void getCompressedSquareMatrix(double[][][] pixelSquareMatrix) {
    for (int i = 0; i < ColorMapping.values().length; i++) {
      performHaarTransformation(pixelSquareMatrix[i]);
    }
    eliminateSmallValues(pixelSquareMatrix);
    for (int i = 0; i < ColorMapping.values().length; i++) {
      performInvHaarTransformation(pixelSquareMatrix[i]);
    }
  }

  private void performHaarTransformation(double[][] originalImage) {
    int subArrayLength = originalImage.length;
    while (subArrayLength > 1) {
      for (int i = 0; i < originalImage.length; i++) {
        haarWaveletRowTransform(originalImage, i, subArrayLength, this::transform);
      }
      for (int j = 0; j < originalImage.length; j++) {
        haarWaveletColTransform(originalImage, j, subArrayLength, this::transform);
      }
      subArrayLength /= 2;
    }
  }

  private void performInvHaarTransformation(double[][] originalImage) {
    int subArrayLength = 2;
    while (subArrayLength <= originalImage.length) {
      for (int j = 0; j < originalImage.length; j++) {
        haarWaveletColTransform(originalImage, j, subArrayLength, this::inverseTransform);
      }
      for (int i = 0; i < originalImage.length; i++) {
        haarWaveletRowTransform(originalImage, i, subArrayLength, this::inverseTransform);
      }
      subArrayLength *= 2;
    }
  }

  private void haarWaveletRowTransform(double[][] matrix, int row,
                                       int length, Function<double[], double[]> haarFunc) {
    double[] rowSubArray = Arrays.copyOfRange(matrix[row], 0, length);
    double[] transformResultSubArray = haarFunc.apply(rowSubArray);
    System.arraycopy(transformResultSubArray, 0, matrix[row], 0, length);
  }

  private void haarWaveletColTransform(double[][] matrix, int col,
                                       int length, Function<double[], double[]> haarFunc) {
    double[] colSubArray = IntStream.range(0, length).mapToDouble(i -> matrix[i][col]).toArray();
    double[] transformResultSubArray = haarFunc.apply(colSubArray);
    for (int i = 0; i < transformResultSubArray.length; i++) {
      matrix[i][col] = transformResultSubArray[i];
    }
  }

  private double[] transform(double[] originalPixel) {
    double[] avgPixelArray = getTransformationArray(originalPixel,
      (a, b) -> ((a + b) / Math.sqrt(2)));
    double[] diffPixelArray = getTransformationArray(originalPixel,
      (a, b) -> ((a - b) / Math.sqrt(2)));
    double[] resultArray = new double[avgPixelArray.length + diffPixelArray.length];
    System.arraycopy(avgPixelArray, 0, resultArray, 0, avgPixelArray.length);
    System.arraycopy(diffPixelArray, 0, resultArray,
            avgPixelArray.length, diffPixelArray.length);
    return resultArray;
  }

  private double[] getTransformationArray(double[] originalPixel,
                                          BiFunction<Double, Double, Double> functionObj) {
    double[] resultArray = new double[originalPixel.length / 2];
    for (int i = 0; i + 1 < originalPixel.length; i += 2) {
      double a = originalPixel[i];
      double b = originalPixel[i + 1];
      double operatedValue = functionObj.apply(a, b);
      resultArray[i / 2] = operatedValue;
    }
    return resultArray;
  }

  private Set<Double> getSortedAbsPixelValues(double[][][] transformedMatrix) {
    Set<Double> set = new TreeSet<>();
    for (int k = 0; k < ColorMapping.values().length; k++) {
      for (int i = 0; i < transformedMatrix[k].length; i++) {
        for (int j = 0; j < transformedMatrix[k][i].length; j++) {
          double num = (double) Math.round(Math.abs(transformedMatrix[k][i][j] * 100)) / 100;
          set.add(num);
        }
      }
    }
    return set;
  }

  private double calculateThreshold(Set<Double> set) {
    double num = (double) set.size() * compressionPercentage;
    int cnt = (int) Math.round(num / 100);
    double thresholdValue = Double.POSITIVE_INFINITY;

    Iterator<Double> iterator = set.iterator();

    if (set.size() > cnt) {
      for (int i = 0; i < cnt + 1; i++) {
        thresholdValue = iterator.next();
      }
    }
    return thresholdValue;
  }

  private void eliminateSmallValues(double[][][] transformedMatrix) {
    Set<Double> set = getSortedAbsPixelValues(transformedMatrix);
    double thresholdValue = calculateThreshold(set);
    for (int k = 0; k < ColorMapping.values().length; k++) {
      for (int i = 0; i < transformedMatrix[k].length; i++) {
        for (int j = 0; j < transformedMatrix[k][i].length; j++) {
          if (Math.abs(transformedMatrix[k][i][j]) < thresholdValue) {
            transformedMatrix[k][i][j] = 0.0;
          }
        }
      }
    }
  }

  private double[] inverseTransform(double[] transformedPixel) {
    double[] interleavePixels = new double[transformedPixel.length];
    for (int i = 0; i < transformedPixel.length / 2; i++) {
      double a = transformedPixel[i];
      double b = transformedPixel[i + (transformedPixel.length / 2)];
      double avg = (a + b) / Math.sqrt(2);
      double diff = (a - b) / Math.sqrt(2);
      interleavePixels[2 * i] = avg;
      interleavePixels[2 * i + 1] = diff;
    }
    return interleavePixels;
  }

  private void getCompressedImgMatrix(double[][][] operatedImg, int[][][] pixelMat) {
    for (int i = 0; i < pixelMat.length; i++) {
      for (int j = 0; j < pixelMat[i].length; j++) {
        for (int k = 0; k < pixelMat[i][j].length; k++) {
          int value = (int) Math.round(Math.abs(operatedImg[k][i][j]));
          pixelMat[i][j][k] = Math.max(0, Math.min(255, value));
        }
      }
    }
  }

}
