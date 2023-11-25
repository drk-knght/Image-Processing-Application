package model.multiout;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import controller.filehandling.writer.PPMWriter;
import enums.ColorMapping;

import model.imageoperations.multiout.MultipleOperationImages;
import model.imageoperations.multiout.SplitChannelImage;

import model.RGBImage;
import model.RGBImageInterface;

import static controller.filehandling.writer.PPMWriter.convertImageMatrixToString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * The class is used to test the operations performed while splitting an image into channels.
 */
public class SplitChannelTest {


  /**
   * The test is used to check if the input passed is null.
   */
  @Test
  public void testNullImage() {
    MultipleOperationImages imageOperation = new SplitChannelImage();
    try {
      imageOperation.operation(null);
      fail("Test for null object passing failed.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown
    }
  }

  /**
   * The test is used to check if the height of the input image is invalid.
   */
  @Test
  public void testInvalidImageHeight() {
    MultipleOperationImages imageOperation = new SplitChannelImage();
    try {
      int[][][] arZeroLength = new int[0][][];
      imageOperation.operation(new RGBImage(arZeroLength));
      fail("Zero length test failed for split channels operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the width of the input image is invalid.
   */
  @Test
  public void testInvalidImageWidth() {
    MultipleOperationImages imageOperation = new SplitChannelImage();
    try {
      int[][][] arZeroWidth = new int[40][0][];
      imageOperation.operation(new RGBImage(arZeroWidth));
      fail("Zero width test failed for split channel operation.");
    } catch (IllegalArgumentException ex) {
      // test passes if the exception is thrown.
    }
  }

  /**
   * The test is used to check if the images are split correctly into channels.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSplitImgOp() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    MultipleOperationImages imageOperation = new SplitChannelImage();
    List<RGBImageInterface> splitChannelImages = imageOperation.operation(rgbImage);

    for (int i = 0; i < splitChannelImages.size(); i++) {
      int[][][] actualMat = splitChannelImages.get(i).getPixel();
      for (int row = 0; row < actualMat.length; row++) {
        for (int col = 0; col < actualMat[0].length; col++) {
          for (int color = 0; color < ColorMapping.values().length; color++) {
            if (color == i) {
              assertEquals(smallResImage[row][col][color], actualMat[row][col][color]);
            } else {
              assertEquals(0, actualMat[row][col][color]);
            }
          }
        }
      }
      assertTrue(checkImageStringFormat(splitChannelImages.get(i), smallResImage, i));
    }
  }

  private boolean checkImageStringFormat(RGBImageInterface singleChannelImage,
                                         int[][][] mat, int color) throws IOException {
    PPMWriter.writeToStorageDisk(singleChannelImage,new FileOutputStream("/Users/omagarwal/Desktop/"
            + "Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/src/"
            + "res/small-Res-singleChannel-Testing.ppm"));
    StringBuilder savedRes = convertImageMatrixToString(singleChannelImage.getImageHeight(),
            singleChannelImage.getImageWidth(), singleChannelImage.getPixel());
    String savedFormat = new String(savedRes);
    StringBuilder expectedFormat = new StringBuilder();
    expectedFormat.append("P3 ").append(4).append(" ").append(3).append("\n255\n");
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          if (k == color) {
            expectedFormat.append(" ").append(mat[i][j][k]).append(" ");
          } else {
            expectedFormat.append(" ").append(0).append(" ");
          }
        }
      }
    }
    assertEquals(expectedFormat.toString(), savedFormat);
    return true;
  }

}
