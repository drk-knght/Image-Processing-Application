package model;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

import controller.filehandling.reader.ImageIOReader;
import controller.filehandling.writer.ImageIOWriter;
import model.enums.AxisName;
import model.enums.ColorMapping;
import model.enums.KernelImage;
import model.imageoperations.multiin.CombineChannelImage;
import model.imageoperations.multiin.MultipleImagesSingleOperation;
import model.imageoperations.multiout.MultipleOperationImages;
import model.imageoperations.multiout.SplitChannelImage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This is the main class from which the utility classes are called.
 */
public class RGBImageTest {

  int[][][] pixelMatrix;

  @Test
  public void testEmptyFilePath() throws IOException {
    try {
      RGBImageInterface rgbImage = new RGBImage(null);
      fail("Test failed");
    } catch (NullPointerException ex) {
      // catch the exception to pass the test.
    }

  }

  /**
   * SetUp() is used to load a test image which is used to for comparing test results.
   */
  @Before
  public void setUp() {
    pixelMatrix = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
  }

  /**
   * The test is used to check if the input matrix is invalid.
   */

  @Test
  public void testInvalidMatrixInput() {
    int[][][] newMat = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}, {243, 108, 173, 23}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}, {173}}
    };
    try {
      RGBImageInterface newImg = new RGBImage(newMat);
      fail("Test failed");
    } catch (IllegalArgumentException ex) {
      // catch to pass the test
    }
  }

  /**
   * The test is used to check if the input matrix in valid.
   */

  @Test
  public void testValidMatrixInput() {
    try {
      RGBImageInterface newImg = new RGBImage(pixelMatrix);
    } catch (InputMismatchException ex) {
      fail("Test failed");
    }
  }

  /**
   * The test is used to check if the image is saved correctly.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSaveImage() throws IOException {
    String imagePath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/"
            + "Image Processing/src/res/small-Res-combineChannel-Testing.png";
    String newImgPath = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/"
            + "Image Processing/src/res/small-MSave-Testing.png";
    int [][][] imagePathMat=ImageIOReader.readFileContent(new FileInputStream(imagePath));
    RGBImageInterface rgbImage = new RGBImage(imagePathMat);
    ImageIOWriter.writeToStorageDisk(rgbImage,new FileOutputStream(newImgPath),"png");
    rgbImage = new RGBImage(ImageIOReader.readFileContent(new FileInputStream(newImgPath)));
    int[][][] actualMatrix = rgbImage.getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMatrix[i][j][k], actualMatrix[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image horizontal flip is correct.
   */
  @Test
  public void testFlipImageHorizontal() {
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] expectedMat = new int[][][]{
            {{19, 11, 211}, {21, 65, 98}, {248, 69, 80}, {145, 203, 132}},
            {{171, 198, 224}, {97, 13, 96}, {243, 108, 173}, {95, 216, 181}},
            {{167, 77, 110}, {247, 171, 122}, {103, 87, 31}, {54, 215, 14}}
    };
    int[][][] actualMat = rgbImage.flipImage(AxisName.horizontal.ordinal()).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the vertical image flip is correct.
   */
  @Test
  public void testFlipImageVertical() {
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] expectedMat = new int[][][]{
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}}
    };
    int[][][] actualMat = rgbImage.flipImage(AxisName.vertical.ordinal()).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the brightness of the image is changed correctly.
   */
  @Test
  public void testFlipImageBrightness() {
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] actualMat = rgbImage.changeBrightness(30).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          int expectedNum = Math.min(255, Math.max(0, pixelMatrix[i][j][k] + 30));
          assertEquals(expectedNum, actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the monochrome image is generated correctly.
   */
  @Test
  public void testImageMonochrome() {
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] actualMat = rgbImage.getSingleComponentImage(ColorMapping.red.ordinal()).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          if (k == ColorMapping.red.ordinal()) {
            assertEquals(pixelMatrix[i][j][k], actualMat[i][j][k]);
          } else {
            assertEquals(0, actualMat[i][j][k]);
          }
        }
      }
    }
  }

  @Test
  public void testGetters() {
    RGBImageInterface rgbImg = new RGBImage(pixelMatrix);
    assertEquals(3, rgbImg.getImageHeight());
    assertEquals(4, rgbImg.getImageWidth());
    int[][][] actualMat = rgbImg.getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(pixelMatrix[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image has been blurred correctly.
   */
  @Test
  public void testImageBlur() {
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] expectedMat = new int[][][]{
            {{94, 93, 76}, {125, 78, 87}, {76, 47, 97}, {34, 36, 99}},
            {{100, 129, 92}, {157, 116, 114}, {143, 86, 128}, {94, 76, 121}},
            {{53, 98, 40}, {105, 97, 63}, {133, 84, 84}, {100, 66, 76}}
    };
    int[][][] actualMat = rgbImage.changeSharpness(KernelImage.Blur.ordinal(),100).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the image has been sharpened correctly.
   */
  @Test
  public void testImageSharp() {
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] expectedMat = new int[][][]{
            {{226, 232, 195}, {255, 125, 161}, {114, 43, 220}, {0, 16, 251}},
            {{247, 255, 249}, {255, 255, 255}, {255, 130, 255}, {234, 249, 255}},
            {{69, 252, 44}, {190, 189, 70}, {255, 194, 191}, {216, 130, 146}}
    };
    int[][][] actualMat = rgbImage.changeSharpness(KernelImage.Sharpen.ordinal(),100).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMat[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the sepia image is generated correctly.
   */
  @Test
  public void testSepiaImage() {
    int[][][] expectedMat = new int[][][]{
            {{238, 212, 165}, {165, 147, 114}, {76, 68, 53}, {55, 49, 38}},
            {{237, 211, 164}, {211, 187, 146}, {66, 58, 45}, {255, 233, 181}},
            {{189, 168, 131}, {113, 100, 78}, {251, 224, 174}, {145, 129, 100}}
    };
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] actualMatrix = rgbImage.sepiaImage(100).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMat[i][j][k], actualMatrix[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the value greyscale image is generated correctly.
   */
  @Test
  public void testGreyscaleValueImage() {
    int[][][] expectedMatValue = new int[][][]{
            {{203, 203, 203}, {248, 248, 248}, {98, 98, 98}, {211, 211, 211}},
            {{216, 216, 216}, {243, 243, 243}, {97, 97, 97}, {224, 224, 224}},
            {{215, 215, 215}, {103, 103, 103}, {247, 247, 247}, {167, 167, 167}}
    };
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] actualMatrixGrey = rgbImage.greyScaleImage(0,100).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMatValue[i][j][k], actualMatrixGrey[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the luma greyscale image is generated correctly.
   */
  @Test
  public void testGreyscaleLumaImage() {
    int[][][] expectedMatValue = new int[][][]{
            {{185, 185, 185}, {107, 107, 107}, {58, 58, 58}, {27, 27, 27}},
            {{187, 187, 187}, {141, 141, 141}, {36, 36, 36}, {194, 194, 194}},
            {{166, 166, 166}, {86, 86, 86}, {183, 183, 183}, {98, 98, 98}}
    };
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] actualMatrixGrey = rgbImage.greyScaleImage(1,100).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMatValue[i][j][k], actualMatrixGrey[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the intensity greyscale image is generated correctly.
   */
  @Test
  public void testGreyscaleIntensityImage() {
    int[][][] expectedMatValue = new int[][][]{
            {{160, 160, 160}, {132, 132, 132}, {61, 61, 61}, {80, 80, 80}},
            {{164, 164, 164}, {174, 174, 174}, {68, 68, 68}, {197, 197, 197}},
            {{94, 94, 94}, {73, 73, 73}, {180, 180, 180}, {118, 118, 118}}
    };
    RGBImageInterface rgbImage = new RGBImage(pixelMatrix);
    int[][][] actualMatrixGrey = rgbImage.greyScaleImage(2,100).getPixel();
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 3; k++) {
          assertEquals(expectedMatValue[i][j][k], actualMatrixGrey[i][j][k]);
        }
      }
    }
  }

  /**
   * The test is used to check if the combine image operation is working correctly.
   */
  @Test
  public void testCombineImage() {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    ArrayList<RGBImageInterface> arr = createChannelImages(smallResImage);
    MultipleImagesSingleOperation channelCombine = new CombineChannelImage();
    RGBImageInterface resultCombination = channelCombine.operation(arr);
    assertTrue(checkMatrix(smallResImage, resultCombination.getPixel()));
  }

  private ArrayList<RGBImageInterface> createChannelImages(int[][][] smallResImage) {
    RGBImageInterface rgbImage = new RGBImage(smallResImage);
    ArrayList<RGBImageInterface> arr = new ArrayList<RGBImageInterface>();
    for (int color = 0; color < ColorMapping.values().length; color++) {
      int[][][] channelComponent = rgbImage.getSingleComponentImage(color).getPixel();
      arr.add(new RGBImage(channelComponent));
    }
    return arr;
  }

  private boolean checkMatrix(int[][][] expectedMatrix, int[][][] actualMatrix) {
    int height = expectedMatrix.length;
    int width = expectedMatrix[0].length;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int color = 0; color < ColorMapping.values().length; color++) {
          assertEquals(expectedMatrix[i][j][color], actualMatrix[i][j][color]);
        }
      }
    }
    return true;
  }

  /**
   * The test is used to check if the split image operation is working correctly.
   */
  @Test
  public void testSplitImage() {
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
    }
  }
}
