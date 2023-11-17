package controller.filehandling;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import controller.filehandling.reader.FileReader;
import controller.filehandling.reader.InputReaderInterface;
import controller.filehandling.writer.FileWriter;
import controller.filehandling.writer.ImageIOWriter;
import controller.filehandling.writer.OutputWriterInterface;
import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This class is used to test if the file input and output are correct.
 */
public class ImageIOTest {

  private String imageSavedPath;

  RGBImageInterface rgbImage;

  int[][][] pixelMatrix;

  /**
   * SetUp() is used to initialise an image into a 3x4 matrix of pixels.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Before
  public void setUp() throws IOException {
    imageSavedPath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/"
            + "Sem-1/CS 5010 PDP/Labs/Image Processing/"
            + "res/small-Res-combineChannel-Testing.png";
    pixelMatrix = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    rgbImage = new RGBImage(pixelMatrix);
    ImageIOWriter.writeToStorageDisk(rgbImage, new FileOutputStream(imageSavedPath),
            "png");
  }

  /**
   * The test is used to check if the input file is read correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testValidReadOperation() throws IOException {
    InputReaderInterface reader = new FileReader(imageSavedPath);
    int[][][] actualPixelMat = reader.read();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int color = 0; color < ColorMapping.values().length; color++) {
          assertEquals(pixelMatrix[i][j][color], actualPixelMat[i][j][color]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image file path is correct.
   */
  @Test
  public void testIncorrectFilePath() {
    imageSavedPath = "/Users/omagarwal/Desktop/Grad@NEU/"
            + "Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/res/small-Res-abc-Testing.jpg";
    try {
      InputReaderInterface reader = new FileReader(imageSavedPath);
      int[][][] res = reader.read();
      fail("Illegal file path test failed.");
    } catch (FileNotFoundException ex) {
      // to catch the illegal file path.
    } catch (IOException e) {
      // to catch the illegal file path.
    }
  }

  /**
   * The test is used to check if the write operation is performed correctly.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testValidWriteOperation() throws IOException {
    String imagePath = "/Users/omagarwal/Desktop/Grad@NEU/"
            + "Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/SavingImg.png";
    OutputWriterInterface writer = new FileWriter(imagePath);
    writer.write(new RGBImage(pixelMatrix));
    InputReaderInterface reader = new FileReader(imageSavedPath);
    int[][][] actualMat = reader.read();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMatrix[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image write operation is performed correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testIllegalImageWrite() throws IOException {
    int[][][] newMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}, {13, 34, 56}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String imagePath = "/Users/omagarwal/Desktop/Grad@NEU/"
            + "Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/IllegalImageIOImg.png";
    try {
      OutputWriterInterface writer = new FileWriter(imagePath);
      writer.write(new RGBImage(newMat));
      fail("Test failed");
    } catch (IllegalArgumentException ex) {
      // to catch mismatched input image matrix.
    }
  }


}
