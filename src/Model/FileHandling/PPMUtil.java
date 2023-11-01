package Model.FileHandling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import Model.Enums.ColorMapping;
import Model.RGBImage;

public class PPMUtil {

  public static int[][][] ppmFileReader(String filePathPPM) throws FileNotFoundException {
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
    int width;
    int height;
    int maxValue;
    try{
      width = sc.nextInt();
      height = sc.nextInt();
      maxValue = sc.nextInt();
    }
    catch (InputMismatchException ex){
      throw new InputMismatchException("Found illegal in the stored file. The input is corrupted.");
    }
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
    if(sc.hasNext()){
      throw new InputMismatchException("Inputs are more than expected for given width and height dimensions");
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
      throw new InputMismatchException("Invalid PPM file: plain RAW file should begin with P3 but stated with:"+token);
    }
  }

  public static void savePPMImage(int height, int width,
                                  int[][][] imageMatrix, String filePath) throws FileNotFoundException {
    FileOutputStream fileStream = null;
    try {
      fileStream = new FileOutputStream(filePath);
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("File could not be created in the file system at path: "+filePath);
    }
    StringBuilder ppmImageData = convertImageMatrixToString(height, width, imageMatrix);
    try {
      fileStream.write(new String(ppmImageData).getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
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
