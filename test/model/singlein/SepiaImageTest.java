package model.singlein;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import controller.filehandling.writer.PPMWriter;
import model.enums.ColorMapping;
import model.imageoperations.singlein.ColorTransformation;
import model.imageoperations.singlein.ImageOperation;

import model.RGBImage;
import model.RGBImageInterface;

import static controller.filehandling.writer.PPMWriter.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class is used to test the operations performed while converting an image into sepia tone.
 */
public class SepiaImageTest {


  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new ColorTransformation();
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
    ImageOperation imageOperation = new ColorTransformation();
    try {
      int[][][] arZeroLength = new int[0][][];
      imageOperation.operation(new RGBImage(arZeroLength));
      fail("Zero length test failed for brightness operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the width of the input image is invalid.
   */
  @Test
  public void testInvalidImageWidth() {
    ImageOperation imageOperation = new ColorTransformation();
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for brightness operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the image is correctly converted into sepia tone.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */

  @Test
  public void testSepiaImage() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorTransformation();
    RGBImageInterface sepiaImage = imageOperation.operation(rgbImage);
    int[][][] actualMat = sepiaImage.getPixel();
    int[][][] expectedMat = new int[][][]{
            {{238, 212, 165}, {165, 147, 114}, {76, 68, 53}, {55, 49, 38}},
            {{237, 211, 164}, {211, 187, 146}, {66, 58, 45}, {255, 233, 181}},
            {{189, 168, 131}, {113, 100, 78}, {251, 224, 174}, {145, 129, 100}}
    };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(sepiaImage, expectedMat));
  }

  private boolean checkImageStringFormat(RGBImageInterface sepiaImage, int[][][] mat)
          throws IOException {
    PPMWriter.writeToStorageDisk(sepiaImage,new FileOutputStream("/Users/"
            + "omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/"
            + "Labs/Image Processing/src/res/small-Res-Sepia-Testing.ppm"));
    StringBuilder savedRes = convertImageMatrixToString(sepiaImage.getImageHeight(),
            sepiaImage.getImageWidth(), sepiaImage.getPixel());
    String savedFormat = new String(savedRes);
    StringBuilder expectedFormat = new StringBuilder();
    expectedFormat.append("P3 ").append(4).append(" ").append(3).append("\n255\n");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          expectedFormat.append(" ").append(mat[i][j][k]).append(" ");
        }
      }
    }
    assertEquals(expectedFormat.toString(), savedFormat);
    return true;
  }
}
