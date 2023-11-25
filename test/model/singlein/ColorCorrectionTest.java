package model.singlein;

import org.junit.Test;


import model.RGBImage;
import model.RGBImageInterface;
import enums.ColorMapping;
import model.imageoperations.singlein.ColorCorrection;
import model.imageoperations.singlein.ImageOperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Junit test class checks for the correctness of the color correction operation on an image model.
 * It checks for basics illegal parameters like null image, illegal width and height.
 * Along with that it also checks for the edge cases for color correction functionality.
 */
public class ColorCorrectionTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new ColorCorrection();
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
    ImageOperation imageOperation = new ColorCorrection();
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
    ImageOperation imageOperation = new ColorCorrection();
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to checks the normal color correction operation on an image.
   * The result matrix should be same as the expected matrix . If not the test fails.
   */
  @Test
  public void testColorCorrection() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{140, 207, 133}, {243, 73, 81}, {15, 69, 99}, {15, 15, 212}},
            {{90, 220, 182}, {238, 112, 174}, {91, 17, 97}, {167, 202, 225}},
            {{49, 219, 15}, {98, 91, 32}, {241, 175, 123}, {163, 81, 111}}
    };

    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));
  }

  /**
   * The test is used to check cascading color correction operation applied on the same image.
   * The result matrix should be same as the expected matrix . If not the test fails.
   */
  @Test
  public void testMultipleColorCorrection() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{140, 207, 133}, {243, 73, 81}, {15, 69, 99}, {15, 15, 212}},
            {{90, 220, 182}, {238, 112, 174}, {91, 17, 97}, {167, 202, 225}},
            {{49, 219, 15}, {98, 91, 32}, {241, 175, 123}, {163, 81, 111}}
    };

    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));

    ImageOperation imageOperation1 = new ColorCorrection();
    RGBImageInterface colorCorrectedImg1 = imageOperation1.operation(colorCorrectedImg);
    int[][][] resultMat1 = new int[][][]{
            {{140, 207, 133}, {243, 73, 81}, {15, 69, 99}, {15, 15, 212}},
            {{90, 220, 182}, {238, 112, 174}, {91, 17, 97}, {167, 202, 225}},
            {{49, 219, 15}, {98, 91, 32}, {241, 175, 123}, {163, 81, 111}}
    };

    assertTrue(checkColorCorrection(colorCorrectedImg1, resultMat1, 0));
  }

  /**
   * The test is used to check whether the color correction operation for pixel values <10 & >245.
   * The operation should not take into account for the peaks which is <10 & >245.
   * The result should be same as the original image. If not the test fails.
   */
  @Test
  public void testPeakColorCorrection() {
    int[][][] smallResImage = new int[][][]{
            {{1, 246, 2}, {248, 4, 0}, {0, 249, 8}, {0, 1, 251}},
            {{5, 250, 251}, {0, 1, 251}, {248, 4, 0}, {0, 8, 254}},
            {{0, 1, 251}, {248, 4, 0}, {1, 246, 2}, {248, 4, 0}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);

    assertTrue(checkColorCorrection(colorCorrectedImg, smallResImage, 0));
  }

  /**
   * The test is used to check whether the color correction operation for pixel values =10 & =245.
   * The operation should not take into account for the peaks which is =10 & =245.
   * The result should be same as the expected image. If not the test fails.
   */
  @Test
  public void testExtremeColorCorrection() {
    int[][][] smallResImage = new int[][][]{
            {{10, 245, 245}, {245, 10, 10}, {10, 245, 245}, {10, 10, 245}},
            {{10, 10, 10}, {245, 245, 245}, {245, 10, 10}, {245, 10, 245}},
            {{245, 245, 10}, {245, 10, 10}, {245, 10, 245}, {245, 245, 245}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{0, 255, 166}, {166, 166, 0}, {0, 255, 166}, {0, 166, 166}},
            {{0, 166, 0}, {166, 255, 166}, {166, 166, 0}, {166, 166, 166}},
            {{166, 255, 0}, {166, 166, 0}, {166, 166, 166}, {166, 255, 166}}
    };

    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));
  }

  /**
   * The test is used to check the color correction operation for red-greyscale image.
   * The operation should take the image and shift the rest of the channels pixel val by the avg.
   * The result should be same as the expected image. If not the test fails.
   */
  @Test
  public void testRedTint() {
    int[][][] smallResImage = new int[][][]{
            {{145, 0, 0}, {248, 0, 0}, {20, 0, 0}, {20, 0, 0}},
            {{95, 0, 0}, {243, 0, 0}, {96, 0, 0}, {172, 0, 0}},
            {{54, 0, 0}, {103, 0, 0}, {24, 0, 0}, {168, 0, 0}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{145, 20, 20}, {248, 20, 20}, {20, 20, 20}, {20, 20, 20}},
            {{95, 20, 20}, {243, 20, 20}, {96, 20, 20}, {172, 20, 20}},
            {{54, 20, 20}, {103, 20, 20}, {24, 20, 20}, {168, 20, 20}}
    };
    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));
  }

  /**
   * The test is used to check the color correction operation for green-greyscale image.
   * The operation should take the image and shift the rest of the channels pixel val by the avg.
   * The result should be same as the expected image. If not the test fails.
   */
  @Test
  public void testGreenTint() {
    int[][][] smallResImage = new int[][][]{
            {{0, 145, 0}, {0, 248, 0}, {0, 20, 0}, {0, 20, 0}},
            {{0, 95, 0}, {0, 243, 0}, {0, 96, 0}, {0, 172, 0}},
            {{0, 54, 0}, {0, 103, 0}, {0, 24, 0}, {0, 168, 0}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{20, 145, 20}, {20, 248, 20}, {20, 20, 20}, {20, 20, 20}},
            {{20, 95, 20}, {20, 243, 20}, {20, 96, 20}, {20, 172, 20}},
            {{20, 54, 20}, {20, 103, 20}, {20, 24, 20}, {20, 168, 20}}
    };

    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));
  }

  /**
   * The test is used to check the color correction operation for blue-greyscale image.
   * The operation should take the image and shift the rest of the channels pixel val by the avg.
   * The result should be same as the expected image. If not the test fails.
   */
  @Test
  public void testBlueTint() {
    int[][][] smallResImage = new int[][][]{
            {{0, 0, 145}, {0, 0, 248}, {0, 0, 20}, {0, 0, 20}},
            {{0, 0, 95}, {0, 0, 243}, {0, 0, 96}, {0, 0, 172}},
            {{0, 0, 54}, {0, 0, 103}, {0, 0, 24}, {0, 0, 168}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{20, 20, 145}, {20, 20, 248}, {20, 20, 20}, {20, 20, 20}},
            {{20, 20, 95}, {20, 20, 243}, {20, 20, 96}, {20, 20, 172}},
            {{20, 20, 54}, {20, 20, 103}, {20, 20, 24}, {20, 20, 168}}
    };

    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));
  }

  /**
   * The test is used to check the color correction operation for greyscale image.
   * The operation should take the image and shift the channels pixel val by the avg of the peaks.
   * The result should be same as the expected image. If not the test fails.
   */
  @Test
  public void testGreyscaleColorCorrection() {
    int[][][] smallResImage = new int[][][]{
            {{145, 145, 145}, {248, 248, 248}, {20, 20, 20}, {20, 20, 20}},
            {{95, 95, 95}, {243, 243, 243}, {96, 96, 96}, {172, 172, 172}},
            {{54, 54, 54}, {103, 103, 103}, {24, 24, 24}, {168, 168, 168}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{145, 145, 145}, {248, 248, 248}, {20, 20, 20}, {20, 20, 20}},
            {{95, 95, 95}, {243, 243, 243}, {96, 96, 96}, {172, 172, 172}},
            {{54, 54, 54}, {103, 103, 103}, {24, 24, 24}, {168, 168, 168}}
    };
    assertTrue(checkColorCorrection(colorCorrectedImg, resultMat, 0));
  }

  /**
   * The test is used to check the color correction operation for images pixel val<=10.
   * In this case as there is no peak >10 & <245 so no change in the image should take place.
   * The result should be same as the original image. If not the test fails.
   */
  @Test
  public void testPeakLessThanTen() {
    int[][][] smallResImage = new int[][][]{
            {{1, 2, 5}, {2, 8, 4}, {0, 0, 0}, {0, 1, 2}},
            {{5, 9, 9}, {3, 4, 3}, {0, 9, 6}, {7, 7, 7}},
            {{5, 10, 4}, {10, 0, 1}, {2, 2, 2}, {6, 1, 8}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    assertTrue(checkColorCorrection(colorCorrectedImg, smallResImage, 0));
  }

  /**
   * The test is used to check the color correction operation for images pixel val>=245.
   * In this case as there is no peak >10 & <245 so no change in the image should take place.
   * The result should be same as the original image. If not the test fails.
   */
  @Test
  public void testPeakGreaterThan245() {
    int[][][] smallResImage = new int[][][]{
            {{251, 252, 255}, {252, 248, 249}, {251, 246, 249}, {254, 255, 254}},
            {{245, 249, 249}, {253, 246, 253}, {250, 249, 246}, {253, 247, 247}},
            {{255, 250, 247}, {253, 250, 251}, {245, 250, 252}, {246, 251, 248}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    assertTrue(checkColorCorrection(colorCorrectedImg, smallResImage, 0));
  }

  /**
   * The test is used to check if the image pixel changes if it is already color corrected image.
   * The result should be same as the original image. If not the test fails.
   */
  @Test
  public void testColorCorrectedImg() {
    int[][][] smallResImage = new int[][][]{
            {{140, 207, 133}, {243, 73, 81}, {15, 69, 99}, {15, 15, 212}},
            {{90, 220, 182}, {238, 112, 174}, {91, 17, 97}, {167, 202, 225}},
            {{49, 219, 15}, {98, 91, 32}, {241, 175, 123}, {163, 81, 111}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new ColorCorrection();
    RGBImageInterface colorCorrectedImg = imageOperation.operation(rgbImage);
    assertTrue(checkColorCorrection(colorCorrectedImg, smallResImage, 0));
  }

  private boolean checkColorCorrection(RGBImageInterface imgMatrix, int[][][] smallMat, int delta) {
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
}
