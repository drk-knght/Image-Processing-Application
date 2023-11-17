package controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.filehandling.reader.PPMReader;
import model.enums.ColorMapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The class represents the Junit testing for the integrated version of the model and controller.
 * The test for all the operations of the image processing application are done from end-to-end.
 * This ensures that controller and model when coupled together are working correctly.
 */
public class ControllerModelIntegratedTest {
  String imagePath;

  /**
   * SetUp() is used to load a test file path which is used to for comparing test results.
   */
  @Before
  public void setUp() {
    imagePath = "/Users/omagarwal/Desktop/Grad@NEU"
            + "/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/res/Koala.ppm";
  }

  /**
   * The test is used to check if the input command is invalid.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testUnknownCommand() throws IOException {
    String command = "unknown command";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface rgbController = new RGBImageController(in, out);
    rgbController.goCall();
    String actualOutput = out.toString();
    String expectedOp = "Illegal arguments passed for the operations.Do you want to try again?\n"
            + "\n\nLooks like we are at the end of program. See you soon.";
    assertEquals(expectedOp, actualOutput);
  }

  /**
   * The test is used to check if the application exits when the "q" or "quit" command is given.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testQuitApplication() throws IOException {
    String commandQ = "q";
    String commandQuit = "quit";
    InputStream inQ = new ByteArrayInputStream(commandQ.getBytes());
    InputStream inQuit = new ByteArrayInputStream(commandQuit.getBytes());
    OutputStream outQ = new ByteArrayOutputStream();
    OutputStream outQuit = new ByteArrayOutputStream();
    RGBImageControllerInterface rgbControllerQ = new RGBImageController(inQ, outQ);
    RGBImageControllerInterface rgbControllerQuit = new RGBImageController(inQuit, outQuit);
    rgbControllerQ.goCall();
    rgbControllerQuit.goCall();
    String actualOutputQ = outQ.toString();
    String actualOutputQuit = outQuit.toString();
    String expectedOp = "Quitting application.\n";
    assertEquals(expectedOp, actualOutputQ);
    assertEquals(expectedOp, actualOutputQuit);
  }

  /**
   * The test is used to check if the application reads the comment properly or error is thrown.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testCommentLineInput() throws IOException {
    String command = "### Comment command";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface rgbController = new RGBImageController(in, out);
    rgbController.goCall();
    String actualOutput = out.toString();
    String expectedOp = "Reading comment now....\n"
            + "\n\nLooks like we are at the end of program. See you soon.";
    assertEquals(expectedOp, actualOutput);
  }

  /**
   * The test is used to check if the application reads the incomplete commands properly.
   * The incorrect commands should display the message regarding the same.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testIncompleteCommands() {
    String[] cmd = new String[]{"load", "save", "red-component",
      "green-component", "blue-component", "value-component",
      "luma-component", "intensity-component", "horizontal-flip", "vertical-flip", "brighten",
      "rgb-split", "rgb-combine", "blur", "sharpen", "sepia"};

    for (int i = 0; i < cmd.length; i++) {
      try {
        InputStream in = new ByteArrayInputStream(cmd[i].getBytes());
        OutputStream out = new ByteArrayOutputStream();
        RGBImageControllerInterface rgbController = new RGBImageController(in, out);
        rgbController.goCall();
        fail("Test failed");
      } catch (IllegalArgumentException ex) {
        // catch to pass exception
      } catch (IOException e) {
        // catch to pass exception
      }
    }
  }

  /**
   * The test to check if the application throws an exception when excess arguments are given.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testExcessArgs() {
    String[] cmd = new String[]{"load", "save", "red-component",
      "green-component", "blue-component", "value-component",
      "luma-component", "intensity-component", "horizontal-flip", "vertical-flip", "brighten",
      "rgb-split", "rgb-combine", "blur", "sharpen", "sepia"};
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < cmd.length; i++) {
      s.append(cmd[i]);
    }
    for (int i = 0; i < cmd.length; i++) {
      try {
        String temp = cmd[i] + " " + s.toString();
        InputStream in = new ByteArrayInputStream(temp.getBytes());
        OutputStream out = new ByteArrayOutputStream();
        RGBImageControllerInterface rgbController = new RGBImageController(in, out);
        rgbController.goCall();
        fail("Test failed");
      } catch (IllegalArgumentException ex) {
        // catch to pass exception
      } catch (IOException e) {
        // catch to pass exception
      }
    }
  }

  private String checkImageStringFormat(int[][][] mat, int offset) {
    StringBuilder expectedFormat = new StringBuilder();
    expectedFormat.append("P3 ").append(mat[0].length).append(" ")
            .append(mat.length).append("\n255\n");
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[i].length; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          int sum = Math.min(255, Math.max(0, mat[i][j][k] + offset));
          expectedFormat.append(" ").append(sum).append(" ");
        }
      }
    }
    return expectedFormat.toString();
  }

  /**
   * The test to check if the application works correctly for the brightness operation on an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testBrightness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nbrighten 30 "
            + "image image-brighter-30\nsave " + saveImgPath + " image-brighter-30";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 30);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for the darkness operation on an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testDarkness() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nbrighten -30 "
            + "image image-darker-30\nsave " + saveImgPath + " image-darker-30";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, -30);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for to get single component red image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSingleChannelRed() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 0, 0}, {248, 0, 0}, {21, 0, 0}, {19, 0, 0}},
            {{95, 0, 0}, {243, 0, 0}, {97, 0, 0}, {171, 0, 0}},
            {{54, 0, 0}, {103, 0, 0}, {247, 0, 0}, {167, 0, 0}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nred-component "
            + "image image-red\nsave " + saveImgPath + " image-red";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for to get single component green image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSingleChannelGreen() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{0, 203, 0}, {0, 69, 0}, {0, 65, 0}, {0, 11, 0}},
            {{0, 216, 0}, {0, 108, 0}, {0, 13, 0}, {0, 198, 0}},
            {{0, 215, 0}, {0, 87, 0}, {0, 171, 0}, {0, 77, 0}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\ngreen-component "
            + "image image-green\nsave " + saveImgPath + " image-green";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for to get single component blue image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSingleChannelBlue() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{0, 0, 132}, {0, 0, 80}, {0, 0, 98}, {0, 0, 211}},
            {{0, 0, 181}, {0, 0, 173}, {0, 0, 96}, {0, 0, 224}},
            {{0, 0, 14}, {0, 0, 31}, {0, 0, 122}, {0, 0, 110}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nblue-component "
            + "image image-blue\nsave " + saveImgPath + " image-blue";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for flip horizontal op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testFlipHorizontal() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{19, 11, 211}, {21, 65, 98}, {248, 69, 80}, {145, 203, 132}},
            {{171, 198, 224}, {97, 13, 96}, {243, 108, 173}, {95, 216, 181}},
            {{167, 77, 110}, {247, 171, 122}, {103, 87, 31}, {54, 215, 14}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nhorizontal-flip "
            + "image image-horizon\nsave " + saveImgPath + " image-horizon";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for flip vertical op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testVerticalFlip() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nvertical-flip "
            + "image image-vertical\nsave " + saveImgPath + " image-vertical";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for sepia op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSepia() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{238, 212, 165}, {165, 147, 114}, {76, 68, 53}, {55, 49, 38}},
            {{237, 211, 164}, {211, 187, 146}, {66, 58, 45}, {255, 233, 181}},
            {{189, 168, 131}, {113, 100, 78}, {251, 224, 174}, {145, 129, 100}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nsepia "
            + "image image-sepia\nsave " + saveImgPath + " image-sepia";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for value greyscale op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testValueGreyscale() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{203, 203, 203}, {248, 248, 248}, {98, 98, 98}, {211, 211, 211}},
            {{216, 216, 216}, {243, 243, 243}, {97, 97, 97}, {224, 224, 224}},
            {{215, 215, 215}, {103, 103, 103}, {247, 247, 247}, {167, 167, 167}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nvalue-component "
            + "image image-value\nsave " + saveImgPath + " image-value";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for intensity greyscale op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testIntensityGreyscale() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{160, 160, 160}, {132, 132, 132}, {61, 61, 61}, {80, 80, 80}},
            {{164, 164, 164}, {174, 174, 174}, {68, 68, 68}, {197, 197, 197}},
            {{94, 94, 94}, {73, 73, 73}, {180, 180, 180}, {118, 118, 118}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nintensity-component "
            + "image image-intensity\nsave " + saveImgPath + " image-intensity";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for blur op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testBlurComponent() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{94, 93, 76}, {125, 78, 87}, {76, 47, 97}, {34, 36, 99}},
            {{100, 129, 92}, {157, 116, 114}, {143, 86, 128}, {94, 76, 121}},
            {{53, 98, 40}, {105, 97, 63}, {133, 84, 84}, {100, 66, 76}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nblur "
            + "image image-blur\nsave " + saveImgPath + " image-blur";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for sharpen op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSharpenComponent() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{226, 232, 195}, {255, 125, 161}, {114, 43, 220}, {0, 16, 251}},
            {{247, 255, 249}, {255, 255, 255}, {255, 130, 255}, {234, 249, 255}},
            {{69, 252, 44}, {190, 189, 70}, {255, 194, 191}, {216, 130, 146}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nsharpen "
            + "image image-sharpen\nsave " + saveImgPath + " image-sharpen";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for luma greyscale op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testLumaGreyscale() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{185, 185, 185}, {107, 107, 107}, {58, 58, 58}, {27, 27, 27}},
            {{187, 187, 187}, {141, 141, 141}, {36, 36, 36}, {194, 194, 194}},
            {{166, 166, 166}, {86, 86, 86}, {183, 183, 183}, {98, 98, 98}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingImg.ppm";
    String command = "load " + readImgPath + " image" + "\nluma-component"
            + " image image-luma\nsave " + saveImgPath + " image-luma";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  private int[][][] getColorMat(int[][][] mat, int colorIdx) {
    int[][][] res = new int[mat.length][mat[0].length][mat[0][0].length];
    for (int i = 0; i < mat.length; i++) {
      for (int j = 0; j < mat[0].length; j++) {
        for (int k = 0; k < mat[0][0].length; k++) {
          if (k == colorIdx) {
            res[i][j][k] = mat[i][j][k];
          } else {
            res[i][j][k] = 0;
          }
        }
      }
    }
    return res;
  }

  /**
   * The test to check if the application works correctly for RGB split op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testRGBSplit() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    int[][][] redMat = getColorMat(smallResImage, 0);
    int[][][] greenMat = getColorMat(smallResImage, 1);
    int[][][] blueMat = getColorMat(smallResImage, 2);
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/";
    String command = "load " + readImgPath + " image" + "\nrgb-split "
            + "image image-red image-green image-blue \nsave " + saveImgPath
            + "image-red.ppm image-red"
            + "\nsave " + saveImgPath + "image-green.ppm image-green"
            + "\nsave " + saveImgPath + "image-blue.ppm image-blue";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();

    String redPath = saveImgPath + "image-red.ppm";
    String contentRed = Files.readString(Paths.get(redPath));
    String expectedValueRed = checkImageStringFormat(redMat, 0);
    assertEquals(expectedValueRed, contentRed);

    String greenPath = saveImgPath + "image-green.ppm";
    String contentGreen = Files.readString(Paths.get(greenPath));
    String expectedValueGreen = checkImageStringFormat(greenMat, 0);
    assertEquals(expectedValueGreen, contentGreen);

    String bluePath = saveImgPath + "image-blue.ppm";
    String contentBlue = Files.readString(Paths.get(bluePath));
    String expectedValueBlue = checkImageStringFormat(blueMat, 0);
    assertEquals(expectedValueBlue, contentBlue);
  }

  /**
   * The test to check if the application works correctly for RGB combine op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testCombineRGB() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };

    String readImgPath = "/Users/omagarwal/Desktop/";
    String saveImgPath = "/Users/omagarwal/Desktop/";

    String command =
            "load " + readImgPath + "image-red.ppm" + " image-red"
                    + "\nload " + readImgPath + "image-green.ppm" + " image-green"
                    + "\nload " + readImgPath + "image-blue.ppm" + " image-blue"
                    + "\nrgb-combine " + "image" + " image-red" + " image-green" + " image-blue"
                    + "\nsave " + saveImgPath + "image-combined.ppm image";

    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath + "image-combined.ppm"));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for correct save op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testSaveImage() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/image-combined.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SaveTest.ppm";
    String command = "load " + readImgPath + " image"
            + "\nsave " + saveImgPath + " image";

    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for load op on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testLoadImage() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/image-combined.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SaveLoad.ppm";
    String command = "load " + readImgPath + " image"
            + "\nsave " + saveImgPath + " image";

    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for running a script file on image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testRunFile() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 203, 132}, {248, 69, 80}, {21, 65, 98}, {19, 11, 211}},
            {{95, 216, 181}, {243, 108, 173}, {97, 13, 96}, {171, 198, 224}},
            {{54, 215, 14}, {103, 87, 31}, {247, 171, 122}, {167, 77, 110}}
    };
    String readScriptFilePath = "/Users/omagarwal/Desktop/ErrorFile.txt";
    String saveImgPath = "/Users/omagarwal/Desktop/Image-saveFileTest.ppm";
    String command = "run " + readScriptFilePath;
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for level adjustment operation an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testLevelAdjustment() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{187, 255, 170}, {255, 85, 100}, {16, 79, 125}, {13, 1, 255}},
            {{121, 255, 232}, {255, 138, 222}, {123, 4, 122}, {219, 252, 255}},
            {{64, 255, 5}, {132, 110, 31}, {255, 219, 157}, {214, 96, 141}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingLevelAdjustmentImg.ppm";
    String command = "load " + readImgPath + " image" + "\nlevels-adjust 10 100 200"
            + " image image-adjust\nsave " + saveImgPath + " image-adjust";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for color correction operation an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testColorCorrection() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{140, 206, 132}, {243, 72, 80}, {16, 68, 98}, {14, 14, 211}},
            {{90, 219, 181}, {238, 111, 173}, {92, 16, 96}, {166, 201, 224}},
            {{49, 218, 14}, {98, 90, 31}, {242, 174, 122}, {162, 80, 110}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingColorCorrectedImg.ppm";
    String command = "load " + readImgPath + " image" + "\ncolor-correct"
            + " image image-correct\nsave " + saveImgPath + " image-correct";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();

    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for compression operation on an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testCompression() throws IOException {
    int[][][] smallResImage = new int[][][]{
            {{145, 196, 116}, {248, 76, 96}, {20, 70, 94}, {20, 6, 215}},
            {{95, 222, 187}, {243, 102, 167}, {96, 18, 100}, {172, 193, 220}},
            {{54, 208, 13}, {103, 80, 32}, {246, 182, 119}, {168, 79, 113}}
    };
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/SavingCompressedImg.ppm";
    String command = "load " + readImgPath + " image" + "\ncompress 20"
            + " image image-correct\nsave " + saveImgPath + " image-correct";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();

    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * Test to check if the application works correctly for histogram plotting operation of an image.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testHistogram() throws IOException {
    int[][][] smallResImage = PPMReader.readFileContent(new FileInputStream("/Users/"
            + "omagarwal/Desktop/Histogram-Test.ppm"));
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/Histogram.ppm";
    String command = "load " + readImgPath + " image" + "\nhistogram"
            + " image image-histogram\nsave " + saveImgPath + " image-histogram";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for cascading operation on an image.
   * For this test the image is operated on grey scale and sharpen operation one after another.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testCascadeGreySharpen() throws IOException {
    int[][][] smallResImage = PPMReader.readFileContent(new FileInputStream("/Users"
            + "/omagarwal/Desktop/CascadeGreySharpen.ppm"));
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/GreySharpen.ppm";
    String command = "load " + readImgPath + " image"
            + "\nluma-component image image-luma split 50"
            + "\n sharpen image-luma image-luma-sharpen split 20"
            + "\nsave " + saveImgPath + " image-luma-sharpen";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }

  /**
   * The test to check if the application works correctly for cascading operation on an image.
   * For this test the image is operated on color correction and level adjustment one after another.
   *
   * @throws IOException Throws exception if the input/output is invalid.
   */
  @Test
  public void testCascadeColorCorrectionLevelAdjust() throws IOException {
    int[][][] smallResImage = PPMReader.readFileContent(new FileInputStream("/Users/"
            + "omagarwal/Desktop/CascadeColorCorrectionLevelAdjust.ppm"));
    String readImgPath = "/Users/omagarwal/Desktop/Img.ppm";
    String saveImgPath = "/Users/omagarwal/Desktop/ColorCorrectionLevelAdjust.ppm";
    String command = "load " + readImgPath + " image"
            + "\ncolor-correct image image-color split 80"
            + "\nlevels-adjust 10 20 30 image-color image-color-levels split 40"
            + "\nsave " + saveImgPath + " image-color-levels";
    InputStream in = new ByteArrayInputStream(command.getBytes());
    OutputStream out = new ByteArrayOutputStream();
    RGBImageControllerInterface controller = new RGBImageController(in, out);
    controller.goCall();
    String content = Files.readString(Paths.get(saveImgPath));
    String expectedValue = checkImageStringFormat(smallResImage, 0);
    assertEquals(expectedValue, content);
  }


}
