package controller.filehandling.reader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

import javax.imageio.ImageIO;

import model.enums.ColorMapping;

public class ImageIOReader{


  /**
   * The method performs the read operation to get the image from a specified inputStream path.
   * The method also throws exception if an invalid inputStream path is passed.
   * It also throws exception if an error occurred while reading the inputs from the passed stream.
   * @return A 3-d matrix which contains the information about the individual rgb image pixels.
   * @throws IOException Throws exception if an error occurs while reading the stream.
   */

  public static int[][][] readFileContent(InputStream in) throws IOException {
    BufferedImage imageElement;
    try {
      imageElement = ImageIO.read(in);
    } catch (IOException ex) {
      throw new IOException("Unable to get image from the mentioned inputStream path for ImageIO Reader.");
    }
    return getPixelMatrixBuffer(imageElement);
  }

  private static int[][][] getPixelMatrixBuffer(BufferedImage imageElement) {
    int imageHeight = imageElement.getHeight();
    int imageWidth = imageElement.getWidth();
    int[][][] imagePixelMatrix = new int[imageHeight][imageWidth][ColorMapping.values().length];
    checkDimensionMatrix(imagePixelMatrix);
    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        int rgbCellValue = imageElement.getRGB(j, i);
        imagePixelMatrix[i][j][ColorMapping.red.ordinal()] = (rgbCellValue >> 16) & 255;
        imagePixelMatrix[i][j][ColorMapping.green.ordinal()] = (rgbCellValue >> 8) & 255;
        imagePixelMatrix[i][j][ColorMapping.blue.ordinal()] = (rgbCellValue) & 255;
      }
    }
    return imagePixelMatrix;
  }

  private static void checkDimensionMatrix(int[][][] mat) {
    if (mat.length == 0 || mat[0].length == 0) {
      throw new InputMismatchException("The dimension of all the pixel arrays are not same");
    }
    int height = mat.length;
    int width = mat[0].length;
    for (int i = 0; i < height; i++) {
      if (mat[i].length != width) {
        throw new InputMismatchException("The dimension of all the pixel arrays are not same");
      }
      for (int j = 0; j < width; j++) {
        if (mat[i][j].length != ColorMapping.values().length) {
          throw new InputMismatchException("The dimension of all the pixel arrays are not same");
        }
      }
    }
  }
}
