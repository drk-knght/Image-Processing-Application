package model.singlein;

import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import controller.filehandling.writer.PPMWriter;
import model.enums.ColorMapping;
import model.imageoperations.singlein.Brightness;
import model.imageoperations.singlein.ImageOperation;
import model.RGBImage;
import model.RGBImageInterface;


import static controller.filehandling.writer.PPMWriter.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class to test the operations performed while increasing/decreasing the brightness of an image.
 */
public class BrightnessProfilerTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new Brightness(100);
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
    ImageOperation imageOperation = new Brightness(340);
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
    ImageOperation imageOperation = new Brightness(-90);
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for brightness operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the brightness of the image is increased/decreased correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testMaxBrightnessImage() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Brightness(900);
    RGBImageInterface brighterImg = imageOperation.operation(rgbImage);
    assertTrue(checkBrightness(brighterImg, smallResImage, 900));
    assertTrue(checkImageStringFormat(brighterImg, 900, smallResImage));
  }

  private boolean checkImageStringFormat(RGBImageInterface brighterImg, int num, int[][][] mat)
          throws IOException {
    PPMWriter.writeToStorageDisk(brighterImg,new FileOutputStream("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/"
            + "Labs/Image Processing/src/res/small-Res-Brighter-" + num + "-Testing.ppm"));
//    brighterImg.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/"
//            + "Labs/Image Processing/src/res/small-Res-Brighter-" + num + "-Testing.ppm");
    StringBuilder savedRes = convertImageMatrixToString(brighterImg.getImageHeight(),
            brighterImg.getImageWidth(), brighterImg.getPixel());
    String savedFormat = new String(savedRes);
    StringBuilder expectedFormat = new StringBuilder();
    expectedFormat.append("P3 ").append(4).append(" ").append(3).append("\n255\n");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          int sum = Math.min(255, Math.max(mat[i][j][k] + num, 0));
          expectedFormat.append(" ").append(sum).append(" ");
        }
      }
    }

    assertEquals(expectedFormat.toString(), savedFormat);
    return true;
  }

  private boolean checkBrightness(RGBImageInterface imgMatrix, int[][][] smallMat, int delta) {
    int[][][] resultMatrixBright = imgMatrix.getPixel();
    for (int i = 0; i < resultMatrixBright.length; i++) {
      for (int j = 0; j < resultMatrixBright[i].length; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          int sum = Math.min(255, Math.max(smallMat[i][j][k] + delta, 0));
          assertEquals(sum, resultMatrixBright[i][j][k]);
        }
      }
    }
    return true;
  }

  /**
   * The test is used to check minimum darkness value of the image.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testMinDarkness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Brightness(-300);
    RGBImageInterface brighterImg = imageOperation.operation(rgbImage);
    assertTrue(checkBrightness(brighterImg, smallResImage, -300));
    assertTrue(checkImageStringFormat(brighterImg, -300, smallResImage));
  }

  /**
   * The test is used to check if the brightness of the image is correctly adjusted to the
   * given value.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testStandardBrightness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Brightness(30);
    RGBImageInterface brighterImg = imageOperation.operation(rgbImage);
    assertTrue(checkBrightness(brighterImg, smallResImage, 30));
    assertTrue(checkImageStringFormat(brighterImg, 30, smallResImage));
  }

  /**
   * The test is used to check if the brightness of the image is correctly adjusted to the
   * given value.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testStandardDarkness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Brightness(-5);
    RGBImageInterface brighterImg = imageOperation.operation(rgbImage);
    assertTrue(checkBrightness(brighterImg, smallResImage, -5));
    assertTrue(checkImageStringFormat(brighterImg, -5, smallResImage));
  }

  /**
   * The test is used to check the validity if the brightness is changed by 0.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testZeroChangeBrightness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Brightness(+0);
    RGBImageInterface brighterImg = imageOperation.operation(rgbImage);
    assertTrue(checkBrightness(brighterImg, smallResImage, +0));
    assertTrue(checkImageStringFormat(brighterImg, +0, smallResImage));
  }

  /**
   * The test is used to check the validity if the darkness is changed by 0.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testZeroChangeDarkness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Brightness(-0);
    RGBImageInterface brighterImg = imageOperation.operation(rgbImage);
    assertTrue(checkBrightness(brighterImg, smallResImage, -0));
    assertTrue(checkImageStringFormat(brighterImg, -0, smallResImage));
  }

}
