package testModel.SingleIN;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


import Model.Enums.ColorMapping;
import Model.Enums.KernelImage;

import Model.ImageOperations.SingleIN.ImageOperation;
import Model.ImageOperations.SingleIN.SharpenImage;
import Model.RGBImage;
import Model.RGBImageInterface;

import static Model.FileHandling.PPMUtil.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SharpenImageTest {
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
    ImageOperation imageOperation=new SharpenImage(100);
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
    ImageOperation imageOperation=new SharpenImage(340);
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
    ImageOperation imageOperation=new SharpenImage(-90);
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
  public void testIllegalSharpen() {

    try{
      ImageOperation imageOperation=new SharpenImage(KernelImage.values().length+570);
      fail("Positive illegal sharpen operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }

    try{
      ImageOperation imageOperation=new SharpenImage(-69);
      fail("Negative illegal sharpen operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }
  }

  @Test
  public void testBlurImage() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new SharpenImage(KernelImage.Blur.ordinal());
    RGBImageInterface blurredImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= blurredImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {94,93,76}, {125, 78, 87}, {76, 47, 97}, {34, 36, 99} },
            { {100, 129, 92}, {157,116, 114}, {143,86, 128}, {94,76, 121} },
            { {53,98, 40}, {105,97, 63}, {133,84, 84}, {100,66, 76} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
//          System.out.println("i: "+i+" j:"+j+" k:"+k);
//          System.out.println(actualMat[i][j][k]);
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(blurredImage,expectedMat));
  }

  @Test
  public void testSharpenImage() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new SharpenImage(KernelImage.Sharpen.ordinal());
    RGBImageInterface sharpenedImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= sharpenedImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {226,232,195}, {255, 125, 161}, {114, 43, 220}, {0, 16, 251} },
            { {247, 255, 249}, {255,255, 255}, {255,130, 255}, {234,249, 255} },
            { {69,252, 44}, {190,189, 70}, {255,194, 191}, {216,130, 146} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(sharpenedImage,expectedMat));
  }

  private boolean checkImageStringFormat(RGBImageInterface sharpenedImage, int [][][] mat) throws IOException {
    sharpenedImage.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-Sharp-Testing.ppm");
    StringBuilder savedRes = convertImageMatrixToString(sharpenedImage.getImageHeight(), sharpenedImage.getImageWidth(), sharpenedImage.getPixel());
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
