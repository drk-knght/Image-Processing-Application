package Model.FileHandling;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

import javax.imageio.ImageIO;

import Model.Enums.ColorMapping;

public class ImageIOUtil {

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
