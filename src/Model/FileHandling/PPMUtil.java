package Model.FileHandling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import Model.Enums.ColorMapping;
import Model.RGBImage;

public class PPMUtil {

  public static int[][][] ppmFileReader(String filePathPPM) throws IOException {
    Scanner sc = null;
    try {
      sc = new Scanner(new FileInputStream(filePathPPM));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not found at the specified path: " +
              filePathPPM + " for PPM Reading");
    }
    StringBuilder builder = getBuilderFromFile(sc);
    sc = new Scanner(builder.toString());
    checkPPMToken(sc);
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    if (maxValue != RGBImage.maxValue) {
      throw new IllegalArgumentException("Max value for PPM file reading: " +
              maxValue + " does not match the expected value(255)");
    }
    int[][][] pixelMatrix = new int[height][width][ColorMapping.values().length];
    return helperImageMatrix(sc, height, width, pixelMatrix);
  }

  private static int[][][] helperImageMatrix(Scanner sc, int height, int width,
                                             int[][][] pixelMatrix) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelMatrix[i][j][ColorMapping.red.ordinal()] = sc.nextInt();
        pixelMatrix[i][j][ColorMapping.green.ordinal()] = sc.nextInt();
        pixelMatrix[i][j][ColorMapping.blue.ordinal()] = sc.nextInt();
      }
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
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
  }

  public static void savePPMImage(int height, int width,
                                  int[][][] imageMatrix, String filePath) throws IOException {
    FileOutputStream fileStream = null;
    try {
      fileStream = new FileOutputStream(filePath);
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("File could not be created in the file system at path: "+filePath);
    }
    StringBuilder ppmImageData = convertImageMatrixToString(height, width, imageMatrix);
    fileStream.write(new String(ppmImageData).getBytes());
  }

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
