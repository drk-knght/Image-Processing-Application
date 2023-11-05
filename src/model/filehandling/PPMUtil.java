package model.filehandling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.enums.ColorMapping;
import model.RGBImage;

/**
 * This utility class signifies file handling operations on images which have extension as "ppm".
 * The class can read a file from a specified path and write down the information to another file.
 * The class can perform operation only on the files and images which are to be dealt with "ppm".
 * It checks for various exception that can occur during the process like IOException.
 */
public class PPMUtil {

  /**
   * The static methods performs the read operation to get the image from a specified file path.
   * The method also throws exception if an invalid file path is passed.
   * It also throws exception if an error occurred while reading the inputs stored in the file.
   *
   * @param filePathPPM String representing the file pah which contains the image data.
   * @return A 3-d matrix which contains the information about the individual rgb image pixels.
   * @throws FileNotFoundException Throws exception if an invalid file path input is provided.
   */
  public static int[][][] ppmFileReader(String filePathPPM) throws FileNotFoundException {
    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream(filePathPPM));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found at the specified path: "
              + filePathPPM + " for PPM Reading");
    }
    StringBuilder builder = getBuilderFromFile(sc);
    sc = new Scanner(builder.toString());
    checkPPMToken(sc);
    int width;
    int height;
    int maxValue;
    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();
    } catch (InputMismatchException ex) {
      throw new InputMismatchException("Found illegal in the stored file. The input is corrupted.");
    }
    if (maxValue != RGBImage.MAX) {
      throw new IllegalArgumentException("Max value for PPM file reading: " +
              maxValue + " does not match the expected value(255)");
    }
    int[][][] pixelMatrix = new int[height][width][ColorMapping.values().length];
    return helperImageMatrix(sc, height, width, pixelMatrix);
  }

  private static int[][][] helperImageMatrix(Scanner sc, int height, int width,
                                             int[][][] pixelMatrix) {

    for (int i = 0; i < height; i++) {
      if (pixelMatrix[i].length != width) {
        throw new InputMismatchException("The width of all the pixel arrays are not same");
      }
      for (int j = 0; j < width; j++) {
        pixelMatrix[i][j][ColorMapping.red.ordinal()] = sc.nextInt();
        pixelMatrix[i][j][ColorMapping.green.ordinal()] = sc.nextInt();
        pixelMatrix[i][j][ColorMapping.blue.ordinal()] = sc.nextInt();
      }
    }

    if (sc.hasNext()) {
      throw new InputMismatchException("Inputs are more than expected "
              + "for given width and height dimensions");
    }
    return pixelMatrix;
  }

  private static StringBuilder getBuilderFromFile(Scanner sc) {
    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    return builder;
  }

  private static void checkPPMToken(Scanner sc) {
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new InputMismatchException("Invalid PPM file: plain RAW "
              + "file should begin with P3 but stated with:" + token);
    }
  }

  /**
   * The static methods performs the write operation to get the image from a specified file path.
   * The method also throws exception if the info could not be written to the specified file path.
   *
   * @param height      Integer containing the data about the height of the image in memory.
   * @param width       Integer containing the data about the width of the image in memory.
   * @param imageMatrix 3-d matrix which contains the data about the individual rgb image pixels.
   * @param filePath    String representing the file pah where image data need to be saved.
   * @throws FileNotFoundException Throws exception if the data cannot be written to that file.
   */
  public static void savePPMImage(int height, int width,
                                  int[][][] imageMatrix, String filePath)
          throws FileNotFoundException {
    FileOutputStream fileStream = null;
    try {
      fileStream = new FileOutputStream(filePath);
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("File could not be created in the "
              + "file system at path: " + filePath);
    }
    StringBuilder ppmImageData = convertImageMatrixToString(height, width, imageMatrix);
    try {
      fileStream.write(new String(ppmImageData).getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * The method takes the image pixel matrix and returns a PPM file string format of the data.
   * The method appends different metadata like image height, width, maxvalue etc. to the string.
   *
   * @param imageHeight      Integer containing the data about the height of the image in memory.
   * @param imageWidth       Integer containing the data about the width of the image in memory.
   * @param pixelImageMatrix 3-d matrix containing the data about the individual rgb image pixels.
   * @return StringBuilder which contains PPM file image data as a string containing headers.
   */
  public static StringBuilder convertImageMatrixToString(int imageHeight, int imageWidth,
                                                         int[][][] pixelImageMatrix) {
    StringBuilder s = new StringBuilder();
    s.append("P3 ").append(imageWidth).append(" ").append(imageHeight).append("\n255\n");
    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        StringBuilder singlePixelColorCode = new StringBuilder();
        for (int k = 0; k < ColorMapping.values().length; k++) {
          singlePixelColorCode.append(" ").append(pixelImageMatrix[i][j][k]).append(" ");
        }
        s.append(singlePixelColorCode);
      }
    }
    return s;
  }
}
