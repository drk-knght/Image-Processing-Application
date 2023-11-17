package controller.filehandling.writer;


import java.io.IOException;
import java.io.OutputStream;

import model.RGBImageInterface;
import model.enums.ColorMapping;

/**
 * The class represents PPM write functionalities required while performing the I/O operations.
 * The methods for writing the document are static so no object creation is required for func calls.
 */
public class PPMWriter {


  /**
   * The static methods performs the write operation to write the image to a specified OutputStream.
   * The method also throws exception if the info could not be written to the specified file path.
   *
   * @param image RGB Image whose data needs to be saved to a particular Output stream path.
   * @param out   Output stream where the data of the image is transferred in the PPM format.
   * @throws IOException Throws exception if the image data cannot be written to the output stream.
   */
  public static void writeToStorageDisk(RGBImageInterface image,
                                        OutputStream out) throws IOException {
    StringBuilder ppmImageData = convertImageMatrixToString(image.getImageHeight(),
            image.getImageWidth(), image.getPixel());
    try {
      out.write(new String(ppmImageData).getBytes());
    } catch (IOException e) {
      throw new IOException("Unable to save the image to the particular "
              + "outStream passed to the PPM writer.\n");
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
