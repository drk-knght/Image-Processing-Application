package controller.filehandling.writer;


import java.io.IOException;
import java.io.OutputStream;

import model.RGBImageInterface;
import model.enums.ColorMapping;

public class PPMWriter implements FileWriter{

  private final OutputStream out;

  public PPMWriter(OutputStream out){
    this.out=out;
  }

  @Override
  public void write(RGBImageInterface image) throws IOException {
    StringBuilder ppmImageData = convertImageMatrixToString(image.getImageHeight(), image.getImageWidth(), image.getPixel());
    try {
      out.write(new String(ppmImageData).getBytes());
    } catch (IOException e) {
      throw new IOException("Unable to save the image to the particular outStream passed to the PPM writer.");
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
  public StringBuilder convertImageMatrixToString(int imageHeight, int imageWidth,
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
