package controller.filehandling.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

import model.RGBImage;
import model.enums.ColorMapping;

public class PPMReader implements FileReader{

  private final InputStream in;

  public PPMReader(InputStream in){
    this.in=in;
  }

  @Override
  public int[][][] read() throws IOException {
    Scanner sc = new Scanner(this.in);
    StringBuilder builder = getBuilderFromFile(sc);
    sc = new Scanner(builder.toString());
    checkPPMToken(sc);
    return checkMetaDataGetImgData(sc);
  }

  private int [][][] checkMetaDataGetImgData(Scanner sc) {
    int width;
    int height;
    int maxValue;
    try {
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();
    } catch (InputMismatchException ex) {
      throw new InputMismatchException("Found illegal format in the passed inputStream. The input is corrupted.");
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

}
