package testModel.SingleIN;

import org.junit.Before;

import java.io.IOException;

import Model.RGBImage;
import Model.RGBImageInterface;

import static org.junit.Assert.fail;

public class SharpenImageTest {
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
}
