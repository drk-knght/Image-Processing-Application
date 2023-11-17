package model.singlein;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;


import controller.filehandling.writer.PPMWriter;
import model.enums.ColorMapping;
import model.enums.KernelImage;

import model.imageoperations.singlein.ImageOperation;
import model.imageoperations.singlein.Sharpness;
import model.RGBImage;
import model.RGBImageInterface;

import static controller.filehandling.writer.PPMWriter.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class is used to test the operations performed while changing the sharpness/blur of an image.
 */
public class SharpenImageTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new Sharpness(0);
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
    ImageOperation imageOperation = new Sharpness(1);
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
    ImageOperation imageOperation = new Sharpness(0);
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for brightness operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is to check if the sharpness value is invalid.
   */

  @Test
  public void testIllegalSharpen() {

    try {
      ImageOperation imageOperation = new Sharpness(KernelImage.values().length + 570);
      fail("Positive illegal sharpen operation test failed.");
    } catch (IllegalArgumentException ex) {
      // test passes if exception is thrown.
    }

    try {
      ImageOperation imageOperation = new Sharpness(-69);
      fail("Negative illegal sharpen operation test failed.");
    } catch (IllegalArgumentException ex) {
      // test passes if exception is thrown.
    }
  }

  /**
   * The test is used to check if the image has been blurred correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testBlurImage() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Sharpness(KernelImage.Blur.ordinal());
    RGBImageInterface blurredImage = imageOperation.operation(rgbImage);
    int[][][] actualMat = blurredImage.getPixel();
    int[][][] expectedMat = new int[][][]{
            {{94, 93, 76}, {125, 78, 87}, {76, 47, 97}, {34, 36, 99}},
            {{100, 129, 92}, {157, 116, 114}, {143, 86, 128}, {94, 76, 121}},
            {{53, 98, 40}, {105, 97, 63}, {133, 84, 84}, {100, 66, 76}}
    };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(blurredImage, expectedMat));
  }


  /**
   * The test is used to check if the image has been sharpened correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSharpenImage() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Sharpness(KernelImage.Sharpen.ordinal());
    RGBImageInterface sharpenedImage = imageOperation.operation(rgbImage);
    int[][][] actualMat = sharpenedImage.getPixel();
    int[][][] expectedMat = new int[][][]{
            {{226, 232, 195}, {255, 125, 161}, {114, 43, 220}, {0, 16, 251}},
            {{247, 255, 249}, {255, 255, 255}, {255, 130, 255}, {234, 249, 255}},
            {{69, 252, 44}, {190, 189, 70}, {255, 194, 191}, {216, 130, 146}}
    };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(sharpenedImage, expectedMat));
  }

  private boolean checkImageStringFormat(RGBImageInterface sharpenedImage, int[][][] mat)
          throws IOException {
    PPMWriter.writeToStorageDisk(sharpenedImage,new FileOutputStream("/Users"
            + "/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/"
            + "CS 5010 PDP/"
            + "Labs/Image Processing/src/res/small-Res-Sharp-Testing.ppm"));
    StringBuilder savedRes = convertImageMatrixToString(sharpenedImage.getImageHeight(),
            sharpenedImage.getImageWidth(), sharpenedImage.getPixel());
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
