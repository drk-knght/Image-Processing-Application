package controller.filehandling.reader;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.RGBImage;
import model.enums.ColorMapping;

/**
 * The class represents PPM read functionalities required while performing the I/O operations.
 * The methods for reading the document are static so no object creation is required for func calls.
 */
public class PPMReader {

  /**
   * The static methods performs the read operation to get the image from a specified inputStream.
   * The method also throws exception if an invalid file path is passed.
   * It also throws exception if an error occurred while reading the inputs stored in the file.
   *
   * @param in InputStream from where the data of the image is read to load it in the application.
   * @return A 3-d matrix which contains the information about the individual rgb image pixels.
   */
  public static int[][][] readFileContent(InputStream in) {
    Scanner sc = new Scanner(in);
    StringBuilder builder = getBuilderFromFile(sc);
    sc = new Scanner(builder.toString());
    checkPPMToken(sc);
    return checkMetaDataGetImgData(sc);
  }

  private static int[][][] checkMetaDataGetImgData(Scanner sc) {
    int width;
    int height;
    int maxValue;
    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();
    } catch (InputMismatchException ex) {
      throw new InputMismatchException("Found illegal format in the "
              + "passed inputStream. The input is corrupted.\n");
    }
    if (maxValue != RGBImage.MAX) {
      throw new IllegalArgumentException("Max value for PPM file reading: "
              + maxValue + " does not match the expected value(255)\n");
    }
    int[][][] pixelMatrix = new int[height][width][ColorMapping.values().length];
    return helperImageMatrix(sc, height, width, pixelMatrix);
  }

  private static int[][][] helperImageMatrix(Scanner sc, int height, int width,
                                             int[][][] pixelMatrix) {

    for (int i = 0; i < height; i++) {
      if (pixelMatrix[i].length != width) {
        throw new InputMismatchException("The width of all the pixel arrays are not same\n");
      }
      for (int j = 0; j < width; j++) {
        pixelMatrix[i][j][ColorMapping.red.ordinal()] = sc.nextInt();
        pixelMatrix[i][j][ColorMapping.green.ordinal()] = sc.nextInt();
        pixelMatrix[i][j][ColorMapping.blue.ordinal()] = sc.nextInt();
      }
    }

    if (sc.hasNext()) {
      throw new InputMismatchException("Inputs are more than expected "
              + "for given width and height dimensions\n");
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
              + "file should begin with P3 but stated with:" + token + "\n");
    }
  }

}
