package controller.mocks;


import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import controller.imagecommands.multiincommand.CombineChannelsCommand;
import controller.imagecommands.multioutcommand.SplitChannelsCommand;
import controller.imagecommands.singleincommands.BrightnessCommand;
import controller.imagecommands.singleincommands.ColorCorrectionCommand;
import controller.imagecommands.singleincommands.ColorTransformationCommand;
import controller.imagecommands.singleincommands.CompressCommand;
import controller.imagecommands.singleincommands.FlipImageCommand;
import controller.imagecommands.singleincommands.GreyScaleCommand;
import controller.imagecommands.singleincommands.HistogramCommand;
import controller.imagecommands.singleincommands.LevelAdjustmentCommand;
import controller.imagecommands.singleincommands.RGBFilterCommand;
import controller.imagecommands.singleincommands.SharpnessCommand;
import model.RGBImageInterface;

import static org.junit.Assert.assertEquals;

/**
 * This class represents the mock testing of controller.
 * The mock model is created and data passed to the mock model from controller is logged to test.
 * The log is asserted against the expected value to ensure that data is going correctly to model.
 */
public class ScriptControllerModelMock {

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
    public RGBImageInterface changeSharpness(int kernelType, double splitPercentage)
            throws IllegalArgumentException {
      logData.append("Sharpness image operation KernelType: " + kernelType
              + " split percent:" + splitPercentage);
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
    public List<RGBImageInterface> splitImageComponents() {
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
    public RGBImageInterface greyScaleImage(int greyScaleType,
                                            double splitPercentage)
            throws IllegalArgumentException {
      logData.append("Get greyscale component on an image: "
              + greyScaleType + " split percent:" + splitPercentage);
      return this.imageObj;
    }

    /**
     * Sepia image operation on an image.
     *
     * @return Default image objected created in the constructor of the class.
     */
    @Override
    public RGBImageInterface sepiaImage(double splitPercentage) {
      logData.append("Apply sepia color transformation on an image."
              + " split percent:" + splitPercentage);
      return this.imageObj;
    }

    /**
     * Method represents the level adjustment operation on an image currently present in use.
     * This method is used to log the data for the mock model.
     *
     * @param b               Shadow-point where the intensity of the pixel decreases around that.
     * @param m               Mid-point where the intensity of the pixel
     *                        changes non-linearly as per the curve eq.
     * @param w               Highlight-point where the intensity of the pixel
     *                        is increased which satisfy the curve.
     * @param splitPercentage Double value representing the split ratio
     *                        of original and modified img.
     * @return An image as the result of the action performed on the present image.
     * @throws IllegalArgumentException Exception is thrown If the b,m,w are not in ascending order.
     */
    @Override
    public RGBImageInterface levelsAdjustment(double b, double m,
                                              double w, double splitPercentage)
            throws IllegalArgumentException {
      logData.append("Level adjustment of an image. B:" + b + " M:" + m
              + " W:" + w + " split percent:" + splitPercentage);
      return this.imageObj;
    }

    /**
     * Method represents the histogram plotting of the r,g,b pixel operation on an image.
     * The histogram represents the intensity values Vs frequency of those values on the axes.
     *
     * @return An image as the result of the action performed on the present image.
     */
    @Override
    public RGBImageInterface getPixelHistogram() {
      logData.append("Histogram operation on an image.");
      return this.imageObj;
    }

    /**
     * Method represents the compression operation part on the image, and it is a lossy one.
     * The data of the pixels is lost when it is reverted back to its original size.
     *
     * @param compressionPercentage Percent of the image that needs to be thrown on the operation.
     * @return An image as the result of the action performed on the present image.
     * @throws IllegalArgumentException Exception is thrown if the % of compression is non-positive.
     */
    @Override
    public RGBImageInterface compressImage(double compressionPercentage)
            throws IllegalArgumentException {
      logData.append("Image compression Operation. "
              + "Compression Percentage: " + compressionPercentage);
      return this.imageObj;
    }

    /**
     * Method represents the color correction part on an existing image.
     * The intensity values for different channels are aligned long the common global max peak.
     *
     * @param splitPercentage Double value representing the split ratio of original & modified img.
     * @return An image as the result of the action performed on the present image.
     */
    @Override
    public RGBImageInterface colorCorrectionImage(double splitPercentage) {
      logData.append("Color correction on an image. Split percent: " + splitPercentage);
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

    @Override
    public void checkAndAssignValues(int[][][] pixelMatrix) {
      logData.append("Getting pixel values from the caller");
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
    assertEquals("loading the image.Sharpness image operation KernelType: "
            + "0 split percent:100.0", mockModel.logData.toString());

    ar = new String[]{"1", "Koala", "Koala-sharp", "split", "30"};
    log = new StringBuilder();
    mockModel = new MockModel(log, null);
    mp.put("Koala", mockModel);
    controller = new SharpnessCommand(ar);
    controller.execute(mp);
    assertEquals("loading the image.Sharpness image operation KernelType: "
            + "1 split percent:30.0", mockModel.logData.toString());
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
    assertEquals("loading the image.Get greyscale component on an image: "
            + "0 split percent:100.0", mockModel.logData.toString());

    ar = new String[]{"1", "Koala", "Koala-grey", "split", "50"};
    log = new StringBuilder();
    mockModel = new MockModel(log, null);
    mp.put("Koala", mockModel);
    controller = new GreyScaleCommand(ar);
    controller.execute(mp);
    assertEquals("loading the image.Get greyscale component on an image: "
            + "1 split percent:50.0", mockModel.logData.toString());
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
  public void testSepiaController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"Koala", "Koala-sepia"};
    RGBImageCommandInterface controller = new ColorTransformationCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Apply sepia color transformation on an image. "
            + "split percent:100.0", mockModel.logData.toString());

    ar = new String[]{"Koala", "Koala-sepia", "split", "50"};
    log = new StringBuilder();
    mockModel = new MockModel(log, null);
    mp.put("Koala", mockModel);
    controller = new ColorTransformationCommand(ar);
    controller.execute(mp);
    assertEquals("loading the image.Apply sepia color transformation on an image. "
            + "split percent:50.0", mockModel.logData.toString());
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

  /**
   * The test method is used to adjust the levels of the images by following the quad equation.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testLevelsAdjustment() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"10", "100", "200", "Koala", "Koala-level-adjusted"};
    RGBImageCommandInterface controller = new LevelAdjustmentCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Level adjustment of an image. B:10.0 M:100.0 W:200.0 "
            + "split percent:100.0", mockModel.logData.toString());


    ar = new String[]{"10", "100", "200", "Koala", "Koala-level-adjusted", "split", "20"};
    log = new StringBuilder();
    mockModel = new MockModel(log, null);
    mp.put("Koala", mockModel);
    controller = new LevelAdjustmentCommand(ar);
    controller.execute(mp);
    assertEquals("loading the image.Level adjustment of an image. "
            + "B:10.0 M:100.0 W:200.0 split percent:20.0", mockModel.logData.toString());
  }

  /**
   * The test method is used to check the color correction operation of the image.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testColorCorrection() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"Koala", "Koala-level-adjusted"};
    RGBImageCommandInterface controller = new ColorCorrectionCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Color correction on an image. "
            + "Split percent: 100.0", mockModel.logData.toString());

    ar = new String[]{"Koala", "Koala-level-adjusted", "split", "20"};
    log = new StringBuilder();
    mockModel = new MockModel(log, null);
    mp.put("Koala", mockModel);
    controller = new ColorCorrectionCommand(ar);
    controller.execute(mp);
    assertEquals("loading the image.Color correction on an image. "
            + "Split percent: 20.0", mockModel.logData.toString());
  }

  /**
   * The test method is used to check the histogram operation of the image.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testHistogram() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"Koala", "Koala-histogram"};
    RGBImageCommandInterface controller = new HistogramCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Histogram operation on an image.",
            mockModel.logData.toString());
  }

  /**
   * The test method is used to check the compression operation of the image.
   *
   * @throws IOException Throws exception if the input is invalid.
   */
  @Test
  public void testCompression() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log, null);
    String[] ar = new String[]{"40", "Koala", "Koala-compressed"};
    RGBImageCommandInterface controller = new CompressCommand(ar);
    Map<String, RGBImageInterface> mp = new HashMap<>();
    mp.put("Koala", mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Image compression Operation. "
            + "Compression Percentage: 40.0", mockModel.logData.toString());
  }

}
