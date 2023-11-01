package testModel.SingleIN;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.ImageOperations.SingleIN.BrightnessProfilerImage;
import Model.ImageOperations.SingleIN.FlipImage;
import Model.ImageOperations.SingleIN.ImageOperation;
import Model.RGBImage;
import Model.RGBImageInterface;

import static Model.FileHandling.PPMUtil.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FlipImageTest {

  String imagePathImageIO;

  String imagePathPPM;

  RGBImageInterface testingImageIO;

  RGBImageInterface testingImagePPM;

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

  @Test
  public void testNullImage(){
    ImageOperation imageOperation=new FlipImage(100);
    try{
      imageOperation.operation(null);
      fail("Test for null object passing failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown
    }
  }

  @Test
  public void testInvalidImageHeight(){
    ImageOperation imageOperation=new FlipImage(340);
    try{
      int [][][] arZeroLength=new int [0][][];
      imageOperation.operation(new RGBImage(arZeroLength));
      fail("Zero length test failed for brightness operation.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testInvalidImageWidth(){
    ImageOperation imageOperation=new FlipImage(-90);
    try{
      int [][][] arZeroWidth=new int [40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for brightness operation.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testHorizontalFlip() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new FlipImage(AxisName.horizontal.ordinal());
    RGBImageInterface horizontalFlippedImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= horizontalFlippedImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {19,11,211}, {21, 65, 98}, {248, 69, 80}, {145, 203, 132} },
            { {171, 198, 224}, {97, 13, 96}, {243, 108, 173}, {95, 216, 181} },
            { {167, 77, 110}, {247, 171, 122}, {103, 87, 31}, {54, 215, 14} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(horizontalFlippedImage,expectedMat));
  }

  private boolean checkImageStringFormat(RGBImageInterface flippedImage, int [][][] mat) throws IOException {
    flippedImage.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-flip-Testing.ppm");
    StringBuilder savedRes = convertImageMatrixToString(flippedImage.getImageHeight(), flippedImage.getImageWidth(), flippedImage.getPixel());
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

  @Test
  public void testVerticalFlip() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new FlipImage(AxisName.vertical.ordinal());
    RGBImageInterface verticalFlippedImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= verticalFlippedImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(verticalFlippedImage,expectedMat));
  }

  @Test
  public void testIllegalFlip() {

    try{
      ImageOperation imageOperation=new FlipImage(AxisName.values().length+100);
      fail("Positive illegal flipping operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }

    try{
      ImageOperation imageOperation=new FlipImage(-54);
      fail("Negative illegal flipping operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }
  }

}
