package Model.SingleIN;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.ImageOperations.SingleIN.ImageOperation;
import Model.ImageOperations.SingleIN.MonochromeImage;
import Model.RGBImage;
import Model.RGBImageInterface;

import static Model.FileHandling.PPMUtil.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class is used to check if the red, green and blue channels are generated correctly
 * individually.
 */
public class MonochromeImageTest {
  String imagePathImageIO;

  String imagePathPPM;

  RGBImageInterface testingImageIO;

  RGBImageInterface testingImagePPM;

  /**
   * SetUp() is used to load a test image which is used to for comparing test results.
   */
  @Before
  public void setUp() {
    imagePathImageIO = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/Koala.png";
    imagePathPPM="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/Koala.ppm";
    try {
      testingImageIO = new RGBImage(imagePathImageIO);
      testingImagePPM=new RGBImage(imagePathPPM);
    } catch (IOException err) {
      fail("file for testing not found while environment set up for brightness ");
    }
  }

  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage(){
    ImageOperation imageOperation=new MonochromeImage(0);
    try{
      imageOperation.operation(null);
      fail("Test for null object passing failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown
    }
  }

  /**
   * The test is used to check if the height of the input image is invalid.
   */

  @Test
  public void testInvalidImageHeight(){
    ImageOperation imageOperation=new MonochromeImage(1);
    try{
      int [][][] arZeroLength=new int [0][][];
      imageOperation.operation(new RGBImage(arZeroLength));
      fail("Zero length test failed for brightness operation.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the width of the input image is invalid.
   */
  @Test
  public void testInvalidImageWidth(){
    ImageOperation imageOperation=new MonochromeImage(2);
    try{
      int [][][] arZeroWidth=new int [40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for brightness operation.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the values passed for any of the components is invalid.
   */
  @Test
  public void testIllegalSingleComponent() {

    try{
      ImageOperation imageOperation=new MonochromeImage(AxisName.values().length+55);
      fail("Positive illegal single channel image operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }

    try{
      ImageOperation imageOperation=new MonochromeImage(-744);
      fail("Negative illegal single channel image operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }
  }

  /**
   * The test is sued to check if the red component generated is correct.
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testRedComponentImage() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new MonochromeImage(ColorMapping.red.ordinal());
    RGBImageInterface redChannelImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= redChannelImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {145, 0, 0}, {248, 0, 0}, {21, 0, 0}, {19, 0, 0} },
            { {95, 0, 0}, {243, 0, 0}, {97, 0, 0}, {171, 0, 0} },
            { {54, 0, 0}, {103, 0, 0}, {247, 0, 0}, {167, 0, 0} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(redChannelImage,expectedMat));
  }

  /**
   * The test is used check if the green component generated is correct.
   * @throws IOException Throws exception if th input/output is invalid.
   */
  @Test
  public void testGreenComponentImage() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new MonochromeImage(ColorMapping.green.ordinal());
    RGBImageInterface greenChannelImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= greenChannelImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {0, 203, 0}, {0, 69, 0}, {0, 65, 0}, {0, 11, 0} },
            { {0, 216, 0}, {0, 108, 0}, {0, 13, 0}, {0, 198, 0} },
            { {0, 215, 0}, {0, 87, 0}, {0, 171, 0}, {0, 77, 0} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greenChannelImage,expectedMat));
  }

  /**
   * The test is used to check if the blue component generated is correct.
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testBlueComponentImage() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new MonochromeImage(ColorMapping.blue.ordinal());
    RGBImageInterface blueChannelImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= blueChannelImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {0, 0, 132}, {0, 0, 80}, {0, 0, 98}, {0, 0, 211} },
            { {0, 0, 181}, {0, 0, 173}, {0, 0, 96}, {0, 0, 224} },
            { {0, 0, 14}, {0, 0, 31}, {0, 0, 122}, {0, 0, 110} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(blueChannelImage,expectedMat));
  }


  private boolean checkImageStringFormat(RGBImageInterface greyscaleImage, int [][][] mat) throws IOException {
    greyscaleImage.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-Channel-Testing.ppm");
    StringBuilder savedRes = convertImageMatrixToString(greyscaleImage.getImageHeight(), greyscaleImage.getImageWidth(), greyscaleImage.getPixel());
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
    assertEquals(expectedFormat.toString(),savedFormat);
    return true;
  }
}
