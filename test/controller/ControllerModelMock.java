package controller;


import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.imagecommands.multiin.CombineChannelsCommand;
import controller.imagecommands.multiout.SplitChannelsCommand;
import controller.imagecommands.RGBImageCommandInterface;
import controller.imagecommands.singlein.BrightnessCommand;
import controller.imagecommands.singlein.ColorTransformationCommand;
import controller.imagecommands.singlein.FlipImageCommand;
import controller.imagecommands.singlein.GreyScaleCommand;
import controller.imagecommands.singlein.RGBFilterCommand;
import controller.imagecommands.singlein.SharpnessCommand;
import model.RGBImageInterface;

import static org.junit.Assert.assertEquals;

/**
 * This class represents the mock testing of controller.
 * The mock model is created and data passed to the mock model from controller is logged to test.
 * The log is asserted against the expected value to ensure that data is going correctly to model.
 */
public class ControllerModelMock {

  /**
   * Static class representing the mock version of the model.
   * The class implements the same interface and the methods only log the action of calling.
   * The callings are checked against the assertions.
   */
  static class MockModel implements RGBImageInterface {

    StringBuilder logData;

    RGBImageInterface imageObj;

    /**
     * Constructor which assigns a new objects to the fields.
     *
     * @param logData  String builder which stores the log data.
     * @param imageObj An image object which is used for returning from method calls.
     */
    public MockModel(StringBuilder logData, RGBImageInterface imageObj) {
      this.logData = logData;
      this.imageObj = imageObj;
      this.logData.append("loading the image.");
    }

    /**
     * Save image operation on an image.
     *
     * @param imagePath The file path where the image needs to be stored in the present file system.
     * @throws IOException If error occurs while saving the path.
     */
    @Override
    public void saveImage(String imagePath) throws IOException {
      logData.append("Save path:" + imagePath);
    }

    /**
     * Flip image operation on an image.
     *
     * @param axisDirection Integer representing the axis direction mapping with the Enum data.
     * @return Default image objected created in the constructor of the class.
     * @throws IllegalArgumentException If the arguments passed are not valid.
     */
    @Override
    public RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException {
      logData.append("Flip image operation: " + axisDirection);
      return this.imageObj;
    }

    /**
     * Brightness image operation on an image.
     *
     * @param deltaChangeValue Amount to change for the individual pixels of the image.
     * @return Default image objected created in the constructor of the class.
     */
    @Override
    public RGBImageInterface changeBrightness(int deltaChangeValue) {
      logData.append("Brightness image operation: " + deltaChangeValue);
      return this.imageObj;
    }

    /**
     * Sharpness image operation on an image.
     *
     * @param kernelType Containing the mapping to an Enum which store data for changing sharpness.
     * @return Default image objected created in the constructor of the class.
     * @throws IllegalArgumentException If the arguments passed are not valid.
     */
    @Override
    public RGBImageInterface changeSharpness(int kernelType) throws IllegalArgumentException {
      logData.append("Sharpness image operation: " + kernelType);
      return this.imageObj;
    }

    /**
     * Combine image operation on an image.
     *
     * @param imageComponents List of images having different color-greyscale images.
     * @return Default image objected created in the constructor of the class.
     * @throws IllegalArgumentException If the arguments passed are not valid.
     */
    @Override
    public RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents)
            throws IllegalArgumentException {
      logData.append("Combine image operation on a "
              + "list of images having size:" + imageComponents.size());
      return this.imageObj;
    }

    /**
     * Split image operation on an image.
     *
     * @return Default image objected created in the constructor of the class.
     * @throws IOException If the arguments passed are not valid.
     */
    @Override
    public List<RGBImageInterface> splitImageComponents() throws IOException {
      logData.append("Split operation on an image.");
      ArrayList<RGBImageInterface> arr = new ArrayList<>();
      arr.add(imageObj);

      return arr;
    }

    /**
     * Get single component image operation on an image.
     *
     * @param colorValue Takes the color component type to get a new color-greyscale image.
     * @return Default image objected created in the constructor of the class.
     * @throws IllegalArgumentException If the arguments passed are not valid.
     */
    @Override
    public RGBImageInterface getSingleComponentImage(int colorValue)
            throws IllegalArgumentException {
      logData.append("Get single component on an image: " + colorValue);
      return this.imageObj;
    }

    /**
     * Greyscale image operation on an image.
     *
     * @param greyScaleType Takes the type of action to perform on this to get a new black-white.
     * @return Default image objected created in the constructor of the class.
     * @throws IllegalArgumentException If the arguments passed are not valid.
     */
    @Override
    public RGBImageInterface greyScaleImage(int greyScaleType) throws IllegalArgumentException {
      logData.append("Get greyscale component on an image: " + greyScaleType);
      return this.imageObj;
    }

    /**
     * Sepia image operation on an image.
     *
     * @return Default image objected created in the constructor of the class.
     */
    @Override
    public RGBImageInterface sepiaImage() {
      logData.append("Apply sepia color transformation on an image.");
      return this.imageObj;
    }

    /**
     * Get height of the image currently in use.
     *
     * @return Zero default value.
     */
    @Override
    public int getImageHeight() {
      logData.append("Get height of an image.");
      return 0;
    }

    /**
     * Get width of the image currently in use.
     *
     * @return Zero default value.
     */
    @Override
    public int getImageWidth() {
      logData.append("Get width of an image.");
      return 0;
    }

    /**
     * Get pixel matrix of the image currently in use.
     *
     * @return Zero default value.
     */
    @Override
    public int[][][] getPixel() {
      logData.append("Get pixel matrix of an image.");
      return new int[0][][];
    }
  }

  /**
   * The test method to check the brightness method correctly.
   *
   * @throws IOException Throws exception for invalid input.
   */
  @Test
  public void testBrightnessController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"0", "Koala", "Koala-Bright"};

    RGBImageCommandInterface controller = new BrightnessCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Brightness "
            + "image operation: 0", mockModel.logData.toString());
  }

  /**
   * The test method to flip the image horizontally or vertically.
   *
   * @throws IOException Throws exception for invalid input.
   */
  @Test
  public void testFlipController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"0", "Koala", "Koala-Flip"};

    RGBImageCommandInterface controller = new FlipImageCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Flip image operation: "
            + "0", mockModel.logData.toString());
  }

  /**
   * The test method is used to check the sharpness of the image.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testSharpnessController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"0", "Koala", "Koala-Sharp"};

    RGBImageCommandInterface controller = new SharpnessCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Sharpness "
            + "image operation: 0", mockModel.logData.toString());
  }

  /**
   * This test method is used to check if the image is correctly converted into greyscale.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testGreyscaleController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"0", "Koala", "Koala-grey"};

    RGBImageCommandInterface controller = new GreyScaleCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Get greyscale "
            + "component on an image: 0", mockModel.logData.toString());
  }

  /**
   * The test method is used to convert an image into red, green or blue images individually.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testRGBFilterController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"0", "Koala", "Koala-grey"};

    RGBImageCommandInterface controller = new RGBFilterCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Get single component "
            + "on an image: 0", mockModel.logData.toString());
  }

  /**
   * The test method is used to check if the image is converted into sepia image correctly.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testColorTransformationController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"Koala", "Koala-grey"};

    RGBImageCommandInterface controller = new ColorTransformationCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Apply sepia color "
            + "transformation on an image.", mockModel.logData.toString());
  }

  /**
   * The test method is used to combine red, green and blue images into a single greyscale image.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testCombineChannelsController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"Koala", "Koala-Red", "Koala-Green", "Koala-Blue"};

    RGBImageCommandInterface controller = new CombineChannelsCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala-Red", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Combine image operation "
            + "on a list of images having size:2", mockModel.logData.toString());
  }

  /**
   * The test method is used to split an image into red, green and blue components at once.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testSplitChannelsController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"Koala", "Koala-Red", "Koala-Green", "Koala-Blue"};

    RGBImageCommandInterface controller = new SplitChannelsCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Split operation "
            + "on an image.", mockModel.logData.toString());
  }
}
