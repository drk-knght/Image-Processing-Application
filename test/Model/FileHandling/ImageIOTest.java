package Model.FileHandling;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;

/**
 * This class is used to test if the file input and output are correct.
 */
public class ImageIOTest {

  private String imageSavedPath;

  RGBImageInterface rgbImage;

  int [][][] pixelMatrix;

  /**
   * SetUp() is used to initialise an image into a 3x4 matrix of pixels.
   * @throws IOException Throws exception if the input is invalid.
   */
  @Before
  public void setUp() throws IOException {
    imageSavedPath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-combineChannel-Testing.png";
    pixelMatrix=new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    rgbImage=new RGBImage(pixelMatrix);
    rgbImage.saveImage(imageSavedPath);
  }

  /**
   * The test is used to check if the input file is read correctly.
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testValidReadOperation() throws IOException {
    int [][][]actualPixelMat=ImageIOUtil.imageIOFileReader(imageSavedPath);
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int color = 0; color< ColorMapping.values().length; color++){
          assertEquals(pixelMatrix[i][j][color],actualPixelMat[i][j][color]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image file path is correct.
   */
  @Test
  public void testIncorrectFilePath(){
    imageSavedPath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-abc-Testing.jpg";
    try{
      int [][][]res=ImageIOUtil.imageIOFileReader(imageSavedPath);
      fail("Illegal file path test failed.");
    }
    catch (FileNotFoundException ex){
      // to catch the illegal file path.
    } catch (IOException e) {
      // to catch the illegal file path.
    }
  }

  /**
   * The test is used to check if the write operation is performed correctly.
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testValidWriteOperation() throws IOException {
    String imagePath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/SavingImg.png";
    ImageIOUtil.saveImageIOFile(3,4,pixelMatrix,imagePath,"png");
    int [][][]actualMat=ImageIOUtil.imageIOFileReader(imagePath);
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int k=0;k<3;k++){
          assertEquals(pixelMatrix[i][j][k],actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image write operation is performed correctly.
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testIllegalImageWrite() throws IOException {
    int [][][] newMat=new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}, {13, 34,56} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
    String imagePath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/IllegalImageIOImg.png";
    try{
      ImageIOUtil.saveImageIOFile(3,4,newMat,imagePath,"png");
      fail("Test failed");
    }
    catch (InputMismatchException ex){
      // to catch mismatched input image matrix.
    }
  }


}
