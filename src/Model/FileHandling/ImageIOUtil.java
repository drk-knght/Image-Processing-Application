package Model.FileHandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

import javax.imageio.ImageIO;

import Model.Enums.ColorMapping;

/**
 * This utility class signifies file handling operations on images which can be accessed by ImageIO.
 * The class can read a file from a specified path and write down the information to another file.
 * It checks for various exception that can occur during the process like IOException.
 */
public class ImageIOUtil {

  /**
   * The static methods performs the read operation to get the image from a specified file path.
   * The method also throws exception if an invalid file path is passed.
   * It also throws exception if an error occurred while reading the inputs stored in the file.
   * @param filePathImageIO String representing the file pah which contains the image data.
   * @return A 3-d matrix which contains the information about the individual rgb image pixels.
   * @throws IOException Throws exception if an invalid file path input is provided to the method.
   */
  public static int[][][] imageIOFileReader(String filePathImageIO) throws IOException {
    BufferedImage imageElement;
    FileInputStream fileName;
    try {
      fileName = new FileInputStream(filePathImageIO);
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("File not found at the specified path: " + filePathImageIO + " for ImageIO Reading");
    }
    try {
      imageElement = ImageIO.read(fileName);
    } catch (IOException ex) {
      throw new IOException("Unable to get image from the mentioned file path.");
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

  /**
   * The static methods performs the write operation to get the image from a specified file path.
   * The method also throws exception if the info could not be written to the specified file path.
   * @param height Integer containing the data about the height of the image in memory.
   * @param width Integer containing the data about the width of the image in memory.
   * @param imageMatrix 3-d matrix which contains the data about the individual rgb image pixels.
   * @param imageFilePath String representing the file pah where image data need to be saved.
   * @param imageExtension Extension of the image format for saving the image.
   * @throws IOException Throws exception if the image data cannot be written to the specified file.
   */
  public static void saveImageIOFile(int height, int width, int[][][] imageMatrix,
                                     String imageFilePath, String imageExtension) throws IOException {
    checkDimensionMatrix(imageMatrix);
    BufferedImage imageSavingElement = getBufferedImage(width, height, imageMatrix);
    try {
      File filePath = new File(imageFilePath);
      ImageIO.write(imageSavingElement, imageExtension, filePath);
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("File could not be created in the file system at path: "+imageFilePath);
    }
  }

  private static void checkDimensionMatrix(int[][][] mat){
    if(mat.length==0 || mat[0].length==0){
      throw new InputMismatchException("The dimension of all the pixel arrays are not same");
    }
    int height= mat.length;
    int width=mat[0].length;
    for(int i=0;i<height;i++){
      if(mat[i].length!=width){
        throw new InputMismatchException("The dimension of all the pixel arrays are not same");
      }
      for(int j=0;j<width;j++){
        if(mat[i][j].length!=ColorMapping.values().length){
          throw new InputMismatchException("The dimension of all the pixel arrays are not same");
        }
      }
    }
  }


  private static BufferedImage getBufferedImage(int width, int height, int[][][] imageMatrix) {
    BufferedImage imageSavingElement = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int redPixelValue = imageMatrix[i][j][ColorMapping.red.ordinal()];
        int greenPixelValue = imageMatrix[i][j][ColorMapping.green.ordinal()];
        int bluePixelValue = imageMatrix[i][j][ColorMapping.blue.ordinal()];
        int rgbPixel = (redPixelValue << 16 | greenPixelValue << 8 | bluePixelValue);
        imageSavingElement.setRGB(j, i, rgbPixel);
      }
    }
    return imageSavingElement;
  }
}
