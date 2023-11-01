package testModel.SingleIN;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import Model.Enums.ColorMapping;
import Model.Enums.GreyScaleType;
import Model.ImageOperations.SingleIN.GreyScaleImage;
import Model.ImageOperations.SingleIN.ImageOperation;
import Model.RGBImage;
import Model.RGBImageInterface;

import static Model.FileHandling.PPMUtil.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GreyscaleTest {
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
    ImageOperation imageOperation=new GreyScaleImage(1);
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
    ImageOperation imageOperation=new GreyScaleImage(0);
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
    ImageOperation imageOperation=new GreyScaleImage(2);
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
  public void testIllegalGreyscale() {
    try{
      ImageOperation imageOperation=new GreyScaleImage(GreyScaleType.values().length+100);
      fail("Positive illegal greyscale operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }

    try{
      ImageOperation imageOperation=new GreyScaleImage(-94);
      fail("Negative illegal greyscale operation test failed.");
    }
    catch (IllegalArgumentException ex){
      // test passes if exception is thrown.
    }
  }

  @Test
  public void testValueGreyScale() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new GreyScaleImage(GreyScaleType.value.ordinal());
    RGBImageInterface greyscaleValueImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= greyscaleValueImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {203, 203, 203}, {248, 248, 248}, {98, 98, 98}, {211, 211, 211} },
            { {216, 216, 216}, {243, 243, 243}, {97, 97, 97}, {224, 224, 224} },
            { {215, 215, 215}, {103, 103, 103}, {247, 247, 247}, {167, 167, 167} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
//          System.out.println("i:"+i +" j:"+j+" k:"+k);
//          System.out.println(actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greyscaleValueImage,expectedMat));
  }

  @Test
  public void testLumaGreyscale() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new GreyScaleImage(GreyScaleType.luma.ordinal());
    RGBImageInterface greyscaleLumaImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= greyscaleLumaImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {185, 185, 185}, {107, 107, 107}, {58, 58, 58}, {27, 27, 27} },
            { {187, 187, 187}, {141, 141, 141}, {36, 36, 36}, {194, 194, 194} },
            { {166, 166, 166}, {86, 86, 86}, {183, 183, 183}, {98, 98, 98} }
    };
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greyscaleLumaImage,expectedMat));
  }

  @Test
  public void testIntensityGreyscale() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ImageOperation imageOperation=new GreyScaleImage(GreyScaleType.intensity.ordinal());
    RGBImageInterface greyscaleIntensityImage=imageOperation.operation(rgbImage);
    int [][][] actualMat= greyscaleIntensityImage.getPixel();
    int [][][] expectedMat= new int [][][]{
            { {160, 160, 160}, {132, 132, 132}, {61, 61, 61}, {80, 80, 80} },
            { {164, 164, 164}, {174, 174, 174}, {68, 68, 68}, {197, 197, 197} },
            { {94, 94, 94}, {73, 73, 73}, {180, 180, 180}, {118, 118, 118} }
    };

    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
    assertTrue(checkImageStringFormat(greyscaleIntensityImage,expectedMat));
  }


  private boolean checkImageStringFormat(RGBImageInterface greyscaleImage, int [][][] mat) throws IOException {
    greyscaleImage.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-greyscale-Testing.ppm");
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
