package model.singlein;

import org.junit.Test;

import model.RGBImage;
import model.RGBImageInterface;
import enums.ColorMapping;

import model.imageoperations.singlein.Compression;
import model.imageoperations.singlein.ImageOperation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Junit test class checks for the correctness of the functionality of the compression operation.
 * It checks for basics illegal parameters along with the edge cases for compression functionality.
 */
public class CompressionTest {


  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new Compression(5.067);
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
    ImageOperation imageOperation = new Compression(34);
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
    ImageOperation imageOperation = new Compression(90.56);
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check zero compression functionality on an image.
   * The result should be same as the original image. If not the test fails.
   */
  @Test
  public void testZeroCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(0);
    RGBImageInterface compressedImg = imageOperation.operation(rgbImage);
    assertTrue(checkCompression(compressedImg, smallResImage, 0));
  }

  /**
   * The test is used to check fifty percent compression functionality on an image.
   * The result matrix should be same as the expected matrix . If not the test fails.
   */
  @Test
  public void testFiftyCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(50);
    RGBImageInterface compressedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{145, 172, 149}, {255, 79, 149}, {52, 80, 89}, {52, 17, 210}},
            {{74, 219, 149}, {200, 126, 149}, {123, 7, 89}, {123, 183, 210}},
            {{56, 222, 46}, {80, 66, 46}, {217, 159, 96}, {217, 103, 90}}
    };
    assertTrue(checkCompression(compressedImg, resultMat, 0));
  }

  /**
   * The test is used to check hundred percent compression functionality on an image.
   * The result matrix should have zeros at every pixel cells . If not the test fails.
   */
  @Test
  public void testHundredCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(100);
    RGBImageInterface compressedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}},
            {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}, {0, 0, 0}}
    };
    assertTrue(checkCompression(compressedImg, resultMat, 0));
  }

  /**
   * The test is used to check negative percent compression functionality on an image.
   * The test passes if an Illegal argument exception is thrown else the test fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(-40);
  }

  /**
   * The test is used to check more than 100 percent compression functionality on an image.
   * The test passes if an Illegal argument exception is thrown else the test fails.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAboveHundredCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(140);
  }

  /**
   * The test is used to check fractional(double val) percent compression functionality on an image.
   * The test passes if the value of the compressed matrix is same as expected matrix else fails.
   */
  @Test
  public void testFractionalCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(40.789);
    RGBImageInterface compressedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat = new int[][][]{
            {{123, 172, 115}, {249, 79, 115}, {11, 80, 86}, {50, 17, 206}},
            {{96, 219, 153}, {221, 126, 153}, {125, 7, 124}, {164, 183, 244}},
            {{56, 222, 30}, {80, 66, 30}, {237, 159, 111}, {198, 103, 105}}
    };
    assertTrue(checkCompression(compressedImg, resultMat, 0));
  }

  /**
   * The test is used to check whether multiple compression method calls work correctly on an img.
   * The test passes if the value of the compressed matrix is same as expected matrix else fails.
   */
  @Test
  public void testReCompression() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ImageOperation imageOperation = new Compression(40.789);
    RGBImageInterface compressedImg = imageOperation.operation(rgbImage);
    int[][][] resultMat1 = new int[][][]{
            {{140, 90, 149}, {140, 90, 149}, {61, 90, 149}, {61, 90, 149}},
            {{140, 90, 149}, {140, 90, 149}, {61, 90, 149}, {61, 90, 149}},
            {{132, 158, 34}, {132, 158, 34}, {212, 158, 34}, {212, 158, 34}}
    };

    ImageOperation imageOperation1 = new Compression(79.54);
    RGBImageInterface compressedImg1 = imageOperation1.operation(compressedImg);
    assertTrue(checkCompression(compressedImg1, resultMat1, 0));
  }

  private boolean checkCompression(RGBImageInterface imgMatrix, int[][][] smallMat, int delta) {
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
