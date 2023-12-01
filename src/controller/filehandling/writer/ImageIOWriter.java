package controller.filehandling.writer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.InputMismatchException;

import javax.imageio.ImageIO;

import enums.ColorMapping;
import model.RGBImageInterface;

/**
 * The class represents ImageIO write functionalities required while performing the I/O operations.
 * The methods for writing the document are static so no object creation is required for func calls.
 */
public class ImageIOWriter {

  /**
   * The static methods performs the write operation to save the image to a specified output stream.
   * The method also throws exception if the info could not be written to the specified file path.
   *
   * @param image         RGB image which needs to be written to the storage disk
   *                      of the local machine.
   * @param out           OutputStream where the data of the image is to be saved from the app.
   * @param fileExtension String representing the extension of the saved file (jpg, png, etc.).
   * @throws IOException Throws exception if the image data cannot be written to the output stream.
   */
  public static void writeToStorageDisk(RGBImageInterface image,
                                        OutputStream out,
                                        String fileExtension) throws IOException {
    checkDimensionMatrix(image.getPixel());
    BufferedImage imageSavingElement = getBufferedImage(image.getImageWidth(),
            image.getImageHeight(), image.getPixel());
    try {
      ImageIO.write(imageSavingElement, fileExtension, out);
    } catch (IOException e) {
      throw new IOException("Unable to save the image to the "
              + "particular outStream passed to the ImageIO writer.\n");
    }
  }

  private static void checkDimensionMatrix(int[][][] mat) {
    if (mat.length == 0 || mat[0].length == 0) {
      throw new InputMismatchException("The dimension of all the pixel arrays are not same\n");
    }
    int height = mat.length;
    int width = mat[0].length;
    for (int i = 0; i < height; i++) {
      if (mat[i].length != width) {
        throw new InputMismatchException("The dimension of all the pixel arrays are not same\n");
      }
      for (int j = 0; j < width; j++) {
        if (mat[i][j].length != ColorMapping.values().length) {
          throw new InputMismatchException("The dimension of all the pixel arrays are not same\n");
        }
      }
    }
  }

  /**
   * Method takes the image data and returns a new object of the image accessible by buffer class.
   * It takes the width, height and the pixel data of the image in form of matrix to convert it.
   * This is a helper method used at several places to create an awt image object when required.
   *
   * @param width       Integer signifying the width of the image passed to this method.
   * @param height      Integer signifying the height of the image passed to this method.
   * @param imageMatrix 3-D matrix signifying the image matrix data of an rgb image.
   * @return An object of Buffered Image class that can be accessed by different awt components.
   */
  public static BufferedImage getBufferedImage(int width, int height, int[][][] imageMatrix) {
    BufferedImage imageSavingElement = new BufferedImage(width,
            height, BufferedImage.TYPE_3BYTE_BGR);
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
