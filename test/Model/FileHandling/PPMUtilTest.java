package Model.FileHandling;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

import static Model.FileHandling.PPMUtil.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PPMUtilTest {

  private String imageSavedPath;

  RGBImageInterface rgbImage;

  int [][][] pixelMatrix;

  @Before
  public void setUp() throws IOException {
    imageSavedPath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-combineChannel-Testing.ppm";
    rgbImage=new RGBImage(imageSavedPath);
    pixelMatrix=new int[][][]{
            { {145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211} },
            { {95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224} },
            { {54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110} }
    };
  }

  @Test
  public void testValidRead() throws IOException {
    int [][][]actualPixelMat=PPMUtil.ppmFileReader(imageSavedPath);
    for(int i=0;i<3;i++){
      for(int j=0;j<4;j++){
        for(int color=0;color< ColorMapping.values().length;color++){
          assertEquals(pixelMatrix[i][j][color],actualPixelMat[i][j][color]);
        }
      }
    }
    String actualFormat=PPMUtil.convertImageMatrixToString(actualPixelMat.length,actualPixelMat[0].length,actualPixelMat).toString();
    assertTrue(checkImageStringFormat(actualFormat,pixelMatrix));
  }

  private boolean checkImageStringFormat(String actualFormat, int [][][] mat) throws IOException {

    StringBuilder expectedFormat = generate(mat);
    assertEquals(expectedFormat.toString(),actualFormat);
    return true;
  }

  private StringBuilder generate(int [][][]mat){
    StringBuilder expectedFormat = new StringBuilder();
    expectedFormat.append("P3 ").append(4).append(" ").append(3).append("\n255\n");
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[0].length; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          expectedFormat.append(" ").append(mat[i][j][k]).append(" ");
        }
      }
    }
    return expectedFormat;
  }

  @Test
  public void testIncorrectFilePath(){
    imageSavedPath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-abc-Testing.ppm";
    try{
      int [][][]res=PPMUtil.ppmFileReader(imageSavedPath);
      fail("Illegal file path test failed.");
    }
    catch (FileNotFoundException ex){
      // to catch the illegal file path.
    }
  }

  @Test
  public void testIllegalFileInput(){
    imageSavedPath="/Users/omagarwal/Desktop/ErrorFile.ppm";
    try {
      int [][][] res=PPMUtil.ppmFileReader(imageSavedPath);
      fail("Corrupted file test failed.");
    } catch (FileNotFoundException e) {
      // to catch the corrupted file
    }
  }

  @Test
  public void testValidWrite(){

  }

}
