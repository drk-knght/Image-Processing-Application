package Model;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.InputMismatchException;

import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.Enums.KernelImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RGBImageTest {

  int [][][] pixelMatrix;

  @Test
  public void testEmptyFilePath() throws IOException {
    try{
      RGBImageInterface rgbImage=new RGBImage("");
      fail("Test failed");
    }
    catch (NullPointerException ex){
      // catch the exception to pass the test.
    }

  }

  @Before
  public void setUp() {
    pixelMatrix=new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
  }

  @Test
  public void testInvalidMatrixInput(){
    int [][][] newMat=new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211},{243, 108, 173,23} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110},{173} }
    };
    try {
      RGBImageInterface newImg=new RGBImage(newMat);
      fail("Test failed");
    }
    catch (InputMismatchException ex){
      // catch to pass the test
    }
  }

  @Test
  public void testValidMatrixInput(){
    try {
      RGBImageInterface newImg=new RGBImage(pixelMatrix);
    }
    catch (InputMismatchException ex){
      fail("Test failed");
    }
  }

  @Test
  public void testSaveImage() throws IOException {
    String imagePath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-combineChannel-Testing.png";
    String newImgPath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-MSave-Testing.png";
    RGBImageInterface rgbImage=new RGBImage(imagePath);
    rgbImage.saveImage(newImgPath);
    rgbImage=new RGBImage(newImgPath);
    int [][][] actualMatrix=rgbImage.getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(pixelMatrix[i][j][k],actualMatrix[i][j][k]);
        }
      }
    }

  }

  @Test
  public void testFlipImageHorizontal(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][] expectedMat= new int [][][]{
            { {19,11,211}, {21, 65, 98}, {248, 69, 80}, {145, 203, 132} },
            { {171, 198, 224}, {97, 13, 96}, {243, 108, 173}, {95, 216, 181} },
            { {167, 77, 110}, {247, 171, 122}, {103, 87, 31}, {54, 215, 14} }
    };
    int [][][]actualMat=rgbImage.flipImage(AxisName.horizontal.ordinal()).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testFlipImageVertical(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][] expectedMat= new int [][][]{
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} }
    };
    int [][][]actualMat=rgbImage.flipImage(AxisName.vertical.ordinal()).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testFlipImageBrightness(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][]actualMat=rgbImage.changeBrightness(30).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          int expectedNum=Math.min(255,Math.max(0,pixelMatrix[i][j][k]+30));
          assertEquals(expectedNum,actualMat[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testFlipImageMonochrome(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][]actualMat=rgbImage.getSingleComponentImage(ColorMapping.red.ordinal()).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          if(k==ColorMapping.red.ordinal()){
            assertEquals(pixelMatrix[i][j][k],actualMat[i][j][k]);
          }
          else{
            assertEquals(0,actualMat[i][j][k]);
          }
        }
      }
    }
  }

  @Test
  public void testGetters(){
    RGBImageInterface rgbImg=new RGBImage(pixelMatrix);
    assertEquals(3,rgbImg.getImageHeight());
    assertEquals(4,rgbImg.getImageWidth());
    int [][][]actualMat= rgbImg.getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(pixelMatrix[i][j][k],actualMat[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testImageBlur(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][] expectedMat= new int [][][]{
            { {94,93,76}, {125, 78, 87}, {76, 47, 97}, {34, 36, 99} },
            { {100, 129, 92}, {157,116, 114}, {143,86, 128}, {94,76, 121} },
            { {53,98, 40}, {105,97, 63}, {133,84, 84}, {100,66, 76} }
    };
    int [][][]actualMat=rgbImage.changeSharpness(KernelImage.Blur.ordinal()).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testImageSharp(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][] expectedMat= new int [][][]{
            { {226,232,195}, {255, 125, 161}, {114, 43, 220}, {0, 16, 251} },
            { {247, 255, 249}, {255,255, 255}, {255,130, 255}, {234,249, 255} },
            { {69,252, 44}, {190,189, 70}, {255,194, 191}, {216,130, 146} }
    };
    int [][][]actualMat=rgbImage.changeSharpness(KernelImage.Sharpen.ordinal()).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(expectedMat[i][j][k],actualMat[i][j][k]);
        }
      }
    }
  }

  @Test
  public void testMonochromeImage(){
    RGBImageInterface rgbImage=new RGBImage(pixelMatrix);
    int [][][] actualMatrix=rgbImage.getSingleComponentImage(ColorMapping.red.ordinal()).getPixel();
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          if(k==ColorMapping.red.ordinal()){
            assertEquals(pixelMatrix[i][j][k],actualMatrix[i][j][k]);
          }
          else {
            assertEquals(0,actualMatrix[i][j][k]);
          }
        }
      }
    }
  }



}
