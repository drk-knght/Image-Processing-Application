package model.singlein;

import org.junit.Before;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

import controller.filehandling.writer.PPMWriter;
import model.enums.ColorMapping;
import model.enums.GreyScaleType;
import model.imageoperations.singlein.GreyScale;
import model.imageoperations.singlein.ImageOperation;
import model.RGBImage;
import model.RGBImageInterface;

import static controller.filehandling.writer.PPMWriter.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class is used to check if the image is properly converted into intensity, value and luma
 * greyscale.
 */
public class GreyscaleTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new GreyScale(1);
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
    ImageOperation imageOperation = new GreyScale(0);
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
    ImageOperation imageOperation = new GreyScale(2);
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for brightness operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the greyscale values are invalid.
   */
  @Test
  public void testIllegalGreyscale() {
    try {
      ImageOperation imageOperation = new GreyScale(
              GreyScaleType.values().length + 100);
      fail("Positive illegal greyscale operation test failed.");
    } catch (IllegalArgumentException ex) {
      // test passes if exception is thrown.
    }

    try {
      ImageOperation imageOperation = new GreyScale(-94);
      fail("Negative illegal greyscale operation test failed.");
    } catch (IllegalArgumentException ex) {
      // test passes if exception is thrown.
    }
  }

  /**
   * The test is used to check if the greyscale value is calculated correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testValueGreyScale() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new GreyScale(GreyScaleType.value.ordinal());
    RGBImageInterface greyscaleValueImage = imageOperation.operation(rgbImage);
    int[][][] actualMat = greyscaleValueImage.getPixel();
    int[][][] expectedMat = new int[][][]{
            {{203, 203, 203}, {248, 248, 248}, {98, 98, 98}, {211, 211, 211}},
            {{216, 216, 216}, {243, 243, 243}, {97, 97, 97}, {224, 224, 224}},
            {{215, 215, 215}, {103, 103, 103}, {247, 247, 247}, {167, 167, 167}}
    };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greyscaleValueImage, expectedMat));
  }

  /**
   * The test is used to check if the luma value is calculated correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testLumaGreyscale() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new GreyScale(GreyScaleType.luma.ordinal());
    RGBImageInterface greyscaleLumaImage = imageOperation.operation(rgbImage);
    int[][][] actualMat = greyscaleLumaImage.getPixel();
    int[][][] expectedMat = new int[][][]{
            {{185, 185, 185}, {107, 107, 107}, {58, 58, 58}, {27, 27, 27}},
            {{187, 187, 187}, {141, 141, 141}, {36, 36, 36}, {194, 194, 194}},
            {{166, 166, 166}, {86, 86, 86}, {183, 183, 183}, {98, 98, 98}}
    };
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greyscaleLumaImage, expectedMat));
  }

  /**
   * The test is used to check if the intensity value is calculated correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testIntensityGreyscale() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new GreyScale(GreyScaleType.intensity.ordinal());
    RGBImageInterface greyscaleIntensityImage = imageOperation.operation(rgbImage);
    int[][][] actualMat = greyscaleIntensityImage.getPixel();
    int[][][] expectedMat = new int[][][]{
            {{160, 160, 160}, {132, 132, 132}, {61, 61, 61}, {80, 80, 80}},
            {{164, 164, 164}, {174, 174, 174}, {68, 68, 68}, {197, 197, 197}},
            {{94, 94, 94}, {73, 73, 73}, {180, 180, 180}, {118, 118, 118}}
    };

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greyscaleIntensityImage, expectedMat));
  }


  private boolean checkImageStringFormat(RGBImageInterface greyscaleImage, int[][][] mat)
          throws IOException {
    PPMWriter.writeToStorageDisk(greyscaleImage,new FileOutputStream("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/"
            + "CS 5010 PDP/"
            + "Labs/Image Processing/res/small-Res-greyscale-Testing.ppm"));

    StringBuilder savedRes = convertImageMatrixToString(greyscaleImage.getImageHeight(),
            greyscaleImage.getImageWidth(), greyscaleImage.getPixel());
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
