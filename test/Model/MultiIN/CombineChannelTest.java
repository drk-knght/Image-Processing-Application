package Model.MultiIN;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import Model.Enums.ColorMapping;
import Model.ImageOperations.MultiIN.CombineChannelImage;
import Model.ImageOperations.MultiIN.MultipleImagesSingleOperation;

import Model.RGBImage;
import Model.RGBImageInterface;

import static Model.FileHandling.PPMUtil.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CombineChannelTest {

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
    MultipleImagesSingleOperation imageOperation=new CombineChannelImage();
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
    MultipleImagesSingleOperation imageOperation=new CombineChannelImage();
    try{
      int [][][] arZeroLength=new int [0][][];
      ArrayList<RGBImageInterface> arr=new ArrayList<RGBImageInterface>();
      arr.add(new RGBImage(arZeroLength));
      imageOperation.operation(arr);
      fail("Zero length test failed for split channels operation.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testInvalidImageWidth(){
    MultipleImagesSingleOperation imageOperation=new CombineChannelImage();
    try{
      int [][][] arZeroWidth=new int [40][0][];
      ArrayList<RGBImageInterface> arr=new ArrayList<RGBImageInterface>();
      arr.add(new RGBImage(arZeroWidth));
      imageOperation.operation(arr);

      fail("Zero width test failed for split channel operation.");
    }
    catch (IllegalArgumentException ex){
      // test passes if the exception is thrown.
    }
  }

  @Test
  public void testCombineChannelsImg() throws IOException {
    int [][][] smallResImage = new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    ArrayList<RGBImageInterface> arr=createChannelImages(smallResImage);
    MultipleImagesSingleOperation channelCombine=new CombineChannelImage();
    RGBImageInterface resultCombination=channelCombine.operation(arr);
    checkMatrix(smallResImage,resultCombination.getPixel());
    assertTrue(checkImageStringFormat(resultCombination,smallResImage));
  }

  private ArrayList<RGBImageInterface> createChannelImages(int [][][] smallResImage){
    RGBImageInterface rgbImage=new RGBImage(smallResImage);
    ArrayList<RGBImageInterface> arr=new ArrayList<RGBImageInterface>();
    for(int color=0;color<ColorMapping.values().length;color++){
      int [][][] channelComponent=rgbImage.getSingleComponentImage(color).getPixel();
      arr.add(new RGBImage(channelComponent));
    }
    return arr;
  }

  private void checkMatrix(int [][][]expectedMatrix, int [][][]actualMatrix){
    int height= expectedMatrix.length;
    int width=expectedMatrix[0].length;
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        for(int color=0;color<ColorMapping.values().length;color++){
          assertEquals(expectedMatrix[i][j][color],actualMatrix[i][j][color]);
        }
      }
    }
  }

  private boolean checkImageStringFormat(RGBImageInterface resultCombination, int [][][] mat) throws IOException {
    resultCombination.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-combineChannel-Testing.ppm");
    StringBuilder savedRes=convertImageMatrixToString(resultCombination.getImageHeight(), resultCombination.getImageWidth(), resultCombination.getPixel());
    String savedFormat=new String(savedRes);
    StringBuilder expectedFormat= new StringBuilder();
    expectedFormat.append("P3 ").append(4).append(" ").append(3).append("\n255\n");
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<ColorMapping.values().length;k++){
          expectedFormat.append(" ").append(mat[i][j][k]).append(" ");
        }
      }
    }

    assertEquals(expectedFormat.toString(),savedFormat);
    return true;
  }
}
