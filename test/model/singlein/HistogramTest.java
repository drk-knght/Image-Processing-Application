package model.singlein;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;


import controller.filehandling.reader.PPMReader;
import model.RGBImage;
import model.RGBImageInterface;
import enums.ColorMapping;
import model.imageoperations.singlein.Histogram;
import model.imageoperations.singlein.ImageOperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Junit test class checks for the working of the histogram plotting operation on an image model.
 * It checks for basics legal parameters along with the pixel values of the image matrix.
 */
public class HistogramTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new Histogram();
    try {
      imageOperation.operation(null);
      fail("Test for null object passing failed.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown
    }
  }

  /**
   * The test is used to check if the height of the input image is invalid.
   */
  @Test
  public void testInvalidImageHeight() {
    ImageOperation imageOperation = new Histogram();
    try {
      int[][][] arZeroLength = new int[0][][];
      imageOperation.operation(new RGBImage(arZeroLength));
      fail("Zero length test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the width of the input image is invalid.
   */
  @Test
  public void testInvalidImageWidth() {
    ImageOperation imageOperation = new Histogram();
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check the dimension of the image.
   * If the image generated has both height and width of dimension length 256.
   * Values are checked against the assertion. If the assertion passes test is success else fails.
   */
  @Test
  public void testDimensionHistogram() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Histogram();
    RGBImageInterface histogramImg = imageOperation.operation(rgbImage);
    assertEquals(256, histogramImg.getImageHeight());
    assertEquals(256, histogramImg.getImageWidth());
  }

  /**
   * Test checks the individual pixel values of the histogram generated against the expected one.
   * Values are checked against the assertion. If the assertion passes test is success else fails.
   *
   * @throws IOException If an error occurs while reading the input from a file in local storage.
   */
  @Test
  public void testNormalHistogram() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    ImageOperation op = new Histogram();
    RGBImageInterface img = op.operation(new RGBImage(smallResImage));
    String storagePath = "/Users/omagarwal/Desktop/gram.ppm";
    int[][][] resultMat = PPMReader.readFileContent(new FileInputStream(storagePath));
    assertTrue(checkHistogram(img, resultMat));
  }

  private boolean checkHistogram(RGBImageInterface imgMatrix, int[][][] smallMat) {
    int[][][] resultMatrixBright = imgMatrix.getPixel();
    for (int i = 0; i < resultMatrixBright.length; i++) {
      for (int j = 0; j < resultMatrixBright[i].length; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          int sum = Math.min(255, Math.max(smallMat[i][j][k], 0));
          assertEquals(sum, resultMatrixBright[i][j][k]);
        }
      }
    }
    return true;
  }
}
