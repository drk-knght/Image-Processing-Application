package model.filehandling;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

import model.enums.ColorMapping;
import model.RGBImage;
import model.RGBImageInterface;


import static controller.filehandling.writer.PPMWriter.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Class to test the operations on PPM images.
 */
public class PPMUtilTest {

  private String imageSavedPath;

  RGBImageInterface rgbImage;

  int[][][] pixelMatrix;

  /**
   * SetUp() is used to initialise a 3x4 pixel matrix representing an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Before
  public void setUp() throws IOException {
    imageSavedPath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/"
            + "Sem-1/CS 5010 PDP/Labs/Image Processing/"
            + "src/res/small-Res-combineChannel-Testing.ppm";
    rgbImage = new RGBImage(imageSavedPath);
    pixelMatrix = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
  }

  /**
   * The test is used to check if the read operation is performed correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testValidReadOperation() throws IOException {
    int[][][] actualPixelMat = PPMUtil.ppmFileReader(imageSavedPath);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int color = 0; color < ColorMapping.values().length; color++) {
          assertEquals(pixelMatrix[i][j][color], actualPixelMat[i][j][color]);
        }
      }
    }
    String actualFormat = convertImageMatrixToString(actualPixelMat.length,
            actualPixelMat[0].length, actualPixelMat).toString();
    assertTrue(checkImageStringFormat(actualFormat, pixelMatrix));
  }

  private boolean checkImageStringFormat(String actualFormat, int[][][] mat) throws IOException {

    StringBuilder expectedFormat = generate(mat, false);
    assertEquals(expectedFormat.toString(), actualFormat);
    return true;
  }

  private StringBuilder generate(int[][][] mat, boolean addComment) {
    StringBuilder expectedFormat = new StringBuilder();
    int height = mat.length;
    int width = mat[0].length;
    if (addComment) {
      expectedFormat.append("############### Represent a ppm image of dim 3X4\n");
    }
    expectedFormat.append("P3 ").append(4).append(" ").append(3).append("\n255\n");
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[i].length; j++) {
        for (int k = 0; k < mat[i][j].length; k++) {
          expectedFormat.append(" ").append(mat[i][j][k]).append(" ");

        }
      }
    }
    return expectedFormat;
  }

  /**
   * The test is used to validate the file path..
   */
  @Test
  public void testIncorrectFilePath() {
    imageSavedPath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs"
            + "/Image Processing/src/res/small-Res-abc-Testing.ppm";
    try {
      int[][][] res = PPMUtil.ppmFileReader(imageSavedPath);
      fail("Illegal file path test failed.");
    } catch (FileNotFoundException ex) {
      // to catch the illegal file path.
    }
  }

  /**
   * The test is used to validate the input file with the help of a 3x4 pixel matrix.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testIllegalFileInput() throws IOException {
    int[][][] newMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}, {13, 34, 56}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String content = generate(newMat, false).toString();
    imageSavedPath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP"
            + "/Labs/Image Processing/res/ErrorImg.ppm";
    generateWriteContentsFile(content, imageSavedPath);

    try {
      int[][][] res = PPMUtil.ppmFileReader(imageSavedPath);
      fail("Corrupted file test failed.");
    } catch (InputMismatchException e) {
      // to catch the corrupted file
    }
  }

  /**
   * The test is used to check in the given input is a comment.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testCommentFile() throws IOException {
    String fileContentComment = generate(pixelMatrix, true).toString();
    String imageCommentPath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1"
            + "/CS 5010 PDP/Labs/Image Processing/res/CommentImg.ppm";
    generateWriteContentsFile(fileContentComment, imageCommentPath);
    int[][][] mat = PPMUtil.ppmFileReader(imageCommentPath);
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[i].length; j++) {
        for (int k = 0; k < mat[i][j].length; k++) {
          assertEquals(pixelMatrix[i][j][k], mat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the height and width of the image is valid.
   *
   * @throws FileNotFoundException Throws an exception if the file doesn't exist.
   */
  @Test
  public void testValidImgDimensions() throws FileNotFoundException {
    int[][][] actualMatrix = PPMUtil.ppmFileReader(imageSavedPath);
    RGBImageInterface rgbImage = new RGBImage(actualMatrix);
    int height = 3;
    int width = 4;
    assertEquals(height, rgbImage.getImageHeight());
    assertEquals(width, rgbImage.getImageWidth());
  }

  private void generateWriteContentsFile(String content, String filePath) throws IOException {
    File path = new File(filePath);
    FileWriter wr = new FileWriter(path);
    wr.write(content);
    wr.flush();
    wr.close();
  }

  /**
   * The test is to check if the write operation performed is correct.
   *
   * @throws IOException Throws exception if the input/output is valid.
   */
  @Test
  public void testValidWriteOperation() throws IOException {
    String imagePath = "/Users/omagarwal/Desktop/Grad@NEU"
            + "/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/SavingImg.ppm";
    PPMUtil.savePPMImage(3, 4, pixelMatrix, imagePath);
    int[][][] actualMat = PPMUtil.ppmFileReader(imagePath);
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMatrix[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

}
