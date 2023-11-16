package model.singlein;

import org.junit.Test;

import java.util.Arrays;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;
import model.imageoperations.singlein.ColorCorrection;
import model.imageoperations.singlein.ImageOperation;
import model.imageoperations.singlein.LevelsAdjustment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LevelAdjustmentTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    ImageOperation imageOperation = new LevelsAdjustment(19.65,26.76,69.6);
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
    ImageOperation imageOperation = new LevelsAdjustment(10,34.5,56.78);
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
    ImageOperation imageOperation = new LevelsAdjustment(5,7,9);
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testNonAscValues(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    try{
      ImageOperation imageOperation =new LevelsAdjustment(70,50,100);
      RGBImageInterface resultImg=imageOperation.operation(image);
      fail("Test failed for non-ascending order b,m values");
    }
    catch (IllegalArgumentException ex){
      // catch to pass the test.
    }
    try{
      ImageOperation imageOperation =new LevelsAdjustment(55,70,60);
      RGBImageInterface resultImg=imageOperation.operation(image);
      fail("Test failed for non-ascending order m,w values");
    }
    catch (IllegalArgumentException ex){
      // catch to pass the test.
    }


  }

  @Test
  public void testEqualValues(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    try{
      ImageOperation imageOperation =new LevelsAdjustment(70,70,100);
      RGBImageInterface resultImg=imageOperation.operation(image);
      fail("Test failed for equal order b,m values");
    }
    catch (IllegalArgumentException ex){
      // catch to pass the test.
    }
    try{
      ImageOperation imageOperation =new LevelsAdjustment(55,70,70);
      RGBImageInterface resultImg=imageOperation.operation(image);
      fail("Test failed for equal order m,w values");
    }
    catch (IllegalArgumentException ex){
      // catch to pass the test.
    }
    try{
      ImageOperation imageOperation =new LevelsAdjustment(100,100,100);
      RGBImageInterface resultImg=imageOperation.operation(image);
      fail("Test failed for equal order m,w values");
    }
    catch (IllegalArgumentException ex){
      // catch to pass the test.
    }
  }

  @Test
  public void testNormalAdjustment(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    ImageOperation imageOperation =new LevelsAdjustment(20,40,100);
    RGBImageInterface resultImg=imageOperation.operation(image);
    int[][][] resultMat = new int[][][]{
            {{97, 0, 165}, {0, 237, 255}, {0, 227, 255}, {0, 0, 0}},
            {{255, 0, 0}, {0, 242, 0}, {255, 0, 255}, {0, 0, 0}},
            {{192, 0, 0}, {251, 255, 75}, {0, 0, 204}, {0, 251, 238}}
    };

    assertTrue(checkCompression(resultImg, resultMat, 0));
  }

  @Test
  public void testNegativeBlackPoint(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    try {
      ImageOperation imageOperation = new LevelsAdjustment(-10,34.5,56.78);
      imageOperation.operation(new RGBImage(smallResImage));
      fail("Zero length test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testNegativeMidPoint(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    try {
      ImageOperation imageOperation = new LevelsAdjustment(10,-34.5,56.78);
      imageOperation.operation(new RGBImage(smallResImage));
      fail("Zero length test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testNegativeHighlightPoint(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    try {
      ImageOperation imageOperation = new LevelsAdjustment(10,34.5,-56.78);
      imageOperation.operation(new RGBImage(smallResImage));
      fail("Zero length test failed for compression operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testLinearAdjustment(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    ImageOperation imageOperation = new LevelsAdjustment(0,34.5,56.78);
    RGBImageInterface resultImg=imageOperation.operation(new RGBImage(smallResImage));
    int [][][] resultMat= new int [][][]{
            {{255, 255, 255}, {255, 255, 255}, {64, 255, 255}, {64, 31, 255}},
            {{255, 255, 255}, {255, 255, 255}, {255, 38, 255}, {255, 255, 255}},
            {{237, 255, 41}, {255, 255, 111}, {255, 255, 255}, {255, 255, 255}}
    };

    assertTrue(checkCompression(resultImg, resultMat, 0));
  }

  @Test
  public void testLevelAdjustOriginalImage(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    ImageOperation imageOperation = new LevelsAdjustment(0,128,255);
    RGBImageInterface resultImg=imageOperation.operation(new RGBImage(smallResImage));

    assertTrue(checkCompression(resultImg, smallResImage, 0));
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
