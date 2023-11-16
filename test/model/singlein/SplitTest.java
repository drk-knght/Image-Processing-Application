package model.singlein;

import org.junit.Test;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SplitTest {

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {

    try {
      RGBImageInterface image=new RGBImage(null);
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
    try {
      int[][][] arZeroLength = new int[0][][];
      RGBImageInterface image=new RGBImage(arZeroLength);
      RGBImageInterface res=image.greyScaleImage(0,10);
      res=image.levelsAdjustment(10,20,30,0.53);
      res=image.sepiaImage(4.5);
      res=image.changeSharpness(0,2.78);
      res=image.colorCorrectionImage(31.1);

      fail("Zero length test failed for split operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the width of the input image is invalid.
   */
  @Test
  public void testInvalidImageWidth() {

    try {
      int[][][] arZeroLength = new int[0][][];
      RGBImageInterface image=new RGBImage(arZeroLength);
      RGBImageInterface res=image.greyScaleImage(0,10);
      res=image.levelsAdjustment(10,20,30,0.53);
      res=image.sepiaImage(4.5);
      res=image.changeSharpness(0,2.78);
      res=image.colorCorrectionImage(31.1);
      int[][][] arZeroWidth = new int[40][0][];
      fail("Zero width test failed for split operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testIllegalNegativeSplit(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    try{
      RGBImageInterface res=image.greyScaleImage(0,-30.65);
      res=image.levelsAdjustment(10,20,30,-35);
      res=image.sepiaImage(-0.56);
      res=image.changeSharpness(0,-7.08);
      res=image.colorCorrectionImage(-11);
      fail("Negative split operation failed for the image operations");
    }
    catch (IllegalArgumentException ex){
      // catch the exception to pass the test.
    }
  }

  @Test
  public void testIllegalPositiveSplit(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    try{
      RGBImageInterface res=image.greyScaleImage(0,130.123);
      res=image.levelsAdjustment(10,20,30,535.87);
      res=image.sepiaImage(456);
      res=image.changeSharpness(0,278);
      res=image.colorCorrectionImage(131.1);
      fail("More than 100% split operation failed for the image operations");
    }
    catch (IllegalArgumentException ex){
      // catch the exception to pass the test.
    }
  }

  @Test
  public void testSplitMidSplitSharpness(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.changeSharpness(0,40.69);
    int[][][] resultMat = new int[][][]{
            {{94, 93, 76}, {116, 69, 69}, {20, 65, 98}, {20, 11, 211}},
            {{100, 129, 92}, {128, 99, 88}, {96, 13, 96}, {172, 198, 224}},
            {{53, 98, 40}, {68, 75, 42}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));

    res=image.changeSharpness(0,0.69);
    resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitZeroSplitSharpness(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.changeSharpness(0,0.0);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitHundredSplitSharpness(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.changeSharpness(0,100.0);
    int[][][] resultMat = new int[][][]{
            {{94, 93, 76}, {124, 78, 87}, {76, 47, 97}, {35, 36, 99}},
            {{100, 129, 92}, {157, 116, 114}, {142, 86, 128}, {95, 76, 121}},
            {{53, 98, 40}, {105, 97, 63}, {133, 84, 84}, {100, 66, 76}}
    };
    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitMidSplitBlur(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.changeSharpness(1,4.034);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));

    res=image.changeSharpness(1,0.034);
    resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitZeroSplitBlur(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.changeSharpness(1,0.0);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitHundredSplitBlur(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.changeSharpness(1,100.0);
    int[][][] resultMat = new int[][][]{
            {{226, 232, 195}, {255, 125, 161}, {113, 43, 220}, {0, 16, 251}},
            {{248, 255, 249}, {255, 255, 255}, {255, 130, 255}, {235, 249, 255}},
            {{69, 252, 44}, {189, 189, 70}, {255, 194, 191}, {217, 130, 146}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitMidSplitSepia(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.sepiaImage(51.53);
    int[][][] resultMat = new int[][][]{
            {{238, 212, 165}, {165, 147, 114}, {20, 65, 98}, {20, 11, 211}},
            {{237, 211, 164}, {211, 187, 146}, {96, 13, 96}, {172, 198, 224}},
            {{189, 168, 131}, {113, 100, 78}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));

    res=image.sepiaImage(0.5153);
    resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitZeroSplitSepia(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.sepiaImage(0);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitHundredSplitSepia(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.sepiaImage(100);
    int[][][] resultMat = new int[][][]{
            {{238, 212, 165}, {165, 147, 114}, {76, 68, 52}, {56, 49, 38}},
            {{237, 211, 164}, {211, 187, 146}, {65, 58, 45}, {255, 233, 181}},
            {{189, 168, 131}, {113, 100, 78}, {251, 223, 174}, {146, 129, 101}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitMidSplitLuma(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.greyScaleImage(1,40);
    int[][][] resultMat = new int[][][]{
            {{185, 185, 185}, {107, 107, 107}, {20, 65, 98}, {20, 11, 211}},
            {{187, 187, 187}, {141, 141, 141}, {96, 13, 96}, {172, 198, 224}},
            {{166, 166, 166}, {86, 86, 86}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitZeroSplitLuma(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.greyScaleImage(1,0);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitHundredSplitLuma(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.greyScaleImage(1,100);
    int[][][] resultMat = new int[][][]{
            {{185, 185, 185}, {107, 107, 107}, {57, 57, 57}, {27, 27, 27}},
            {{187, 187, 187}, {141, 141, 141}, {36, 36, 36}, {194, 194, 194}},
            {{166, 166, 166}, {86, 86, 86}, {183, 183, 183}, {98, 98, 98}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitMidSplitColorCorrection(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.colorCorrectionImage(29.6);
    int[][][] resultMat = new int[][][]{
            {{181, 90, 208}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{131, 103, 255}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{90, 102, 90}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitZeroSplitColorCorrection(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.colorCorrectionImage(0);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitHundredSplitColorCorrection(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.colorCorrectionImage(100);
    int[][][] resultMat = new int[][][]{
            {{140, 207, 133}, {243, 73, 81}, {15, 69, 99}, {15, 15, 212}},
            {{90, 220, 182}, {238, 112, 174}, {91, 17, 97}, {167, 202, 225}},
            {{49, 219, 15}, {98, 91, 32}, {241, 175, 123}, {163, 81, 111}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitMidSplitLevelAdjustment(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.levelsAdjustment(2,5,8,69.6);
    int[][][] resultMat = new int[][][]{
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}, {20, 11, 211}},
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}, {172, 198, 224}},
            {{255, 255, 255}, {255, 255, 255}, {255, 255, 255}, {168, 77, 110}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitZeroSplitLevelAdjustment(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.levelsAdjustment(10.5, 43.6, 78.14,0);
    int[][][] resultMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    assertTrue(checkSplitPreview(res, resultMat, 0));
  }

  @Test
  public void testSplitHundredSplitLevelAdjustment(){
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {20, 65, 98}, {20, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {96, 13, 96}, {172, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {246, 171, 122}, {168, 77, 110}}
    };
    RGBImageInterface image=new RGBImage(smallResImage);
    RGBImageInterface res=image.levelsAdjustment(23.5,67.9,123.5,100);
    int[][][] resultMat = new int[][][]{
            {{255, 255, 2553}, {255, 130, 158}, {0, 120, 201}, {0, 0, 255}},
            {{194, 255, 255}, {255, 223, 255}, {196, 0, 196}, {255, 255, 255}},
            {{90, 255, 0}, {212, 175, 23}, {255, 255, 252}, {255, 151, 227}}
    };

    assertTrue(checkSplitPreview(res, resultMat, 0));
  }


  private boolean checkSplitPreview(RGBImageInterface imgMatrix, int[][][] smallMat, int delta) {
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
