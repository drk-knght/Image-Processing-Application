package controller.mocks;

import org.junit.Before;
import org.junit.Test;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.RGBImageControllerInterface;
import controller.features.Features;
import controller.graphicalcontroller.GraphicalController;
import controller.graphicalcontroller.GraphicalControllerInterface;
import model.RGBImageInterface;
import view.IView;
import view.dialogmenus.multiipdialog.MultiInputSliderDialogInterface;

import static org.junit.Assert.assertEquals;

/**
 * This class represents the mock testing of graphical controller.
 * The mock model is created. Data passed to the mock model & view from controller is log to test.
 * The log is asserted against the expected value to ensure that data is going correctly to model.
 * It also checks assertion against if the data is perfect for the view as well.
 */
public class GraphicsControllerViewModel {

  /**
   * Static class representing the mock version of the view.
   * The class implements the same interface and the methods only log the action of calling.
   * The callings are checked against the assertions.
   */
  static class ViewMock implements IView {
    StringBuilder viewLogData;

    /**
     * Constructor which assigns a new objects to the fields.
     *
     * @param logData String builder which stores the log data.
     */
    public ViewMock(StringBuilder logData) {
      this.viewLogData = logData;
    }

    /**
     * To check the log information for the setting up display operation of the view.
     */
    @Override
    public void setDisplay() {
      viewLogData.append("Setting up the display for the mock view model.");
    }

    /**
     * To check the log information for the pop up message operation of the view.
     *
     * @param message String having information that the user is made aware of for the event.
     */
    @Override
    public void setPopupMessage(String message) {
      viewLogData.append("Pop up message for the mock view model.");
    }

    /**
     * To check the log information for the error message operation of the view.
     *
     * @param message String having error message that the user is made aware of for the event.
     */
    @Override
    public void setErrorMessage(String message) {
      viewLogData.append("Error message for the mock view model.");
    }

    /**
     * To check the log information for the display image operation of the view.
     *
     * @param image Image object information about the image which needs to be shown in the UI.
     */
    @Override
    public void displayImage(Image image) {
      viewLogData.append("Display image from the mock view model.");
    }

    /**
     * To check the log information for the display histogram of image operation of the view.
     *
     * @param image Image object information about the histogram which needs to be shown in the UI.
     */
    @Override
    public void displayHistogram(Image image) {
      viewLogData.append("Display histogram from the mock view model.");
    }

    /**
     * To check the log information for the setting up the features of the view.
     *
     * @param features An object which stores different requests that view can make to controller.
     */
    @Override
    public void setFeatures(Features features) {
      viewLogData.append("Features from the mock view model.");
    }

    /**
     * To check the log information for the getting the input file path from the view to controller.
     *
     * @return Constant string representing the path of the image.
     */
    @Override
    public String getInputFilePath() {
      viewLogData.append("Input file Path.");
      return "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs"
              + "/Image Processing/res/Tiger.png";
    }

    /**
     * To check the log information for the saving the data to output file path from the view.
     *
     * @return Constant string representing the local path of the image.
     */
    @Override
    public String getOutputFilePath() {
      viewLogData.append("Output file Path.");
      return "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/"
              + "Image Processing/res/Tiger2.png";
    }

    /**
     * To check the log information for the single ip dialog from the view.
     *
     * @param operationTitle The title of the opened dialog box which is displayed to the user.
     * @param labelText      The Text for the single slider present in the dialog box.
     * @return Constant integer 0 for checking log.
     */
    @Override
    public Integer displayDialogSingleSplitPreview(String operationTitle, String labelText) {
      viewLogData.append("Single Split Preview");
      return 0;
    }

    /**
     * To check the log information for the multiple ip dialog from the view.
     *
     * @param levelAdjustDialog Interface containing the information about the multiple data that
     *                          needs to be fetched from the user during the display of the pop-up.
     * @return Constant list of integers {1,2,3,4} for checking log.
     */
    @Override
    public List<Integer> displayDialogMultiINPreview(
            MultiInputSliderDialogInterface levelAdjustDialog) {
      viewLogData.append("Multiple Split Preview");
      return new ArrayList<>(List.of(1, 2, 3, 4));
    }
  }

  /**
   * Static class representing the mock version of the model.
   * The class implements the same interface and the methods only log the action of calling.
   * The callings are checked against the assertions.
   */
  static class ModelMock implements RGBImageInterface {
    StringBuilder modelLogData;

    RGBImageInterface imageObj;

    int[][][] pseudoMat;

    /**
     * Constructor which assigns a new objects to the fields.
     *
     * @param logData  String builder which stores the log data.
     * @param imageObj An image object which is used for returning from method calls.
     */
    public ModelMock(StringBuilder logData, RGBImageInterface imageObj) {
      this.modelLogData = logData;
      this.imageObj = imageObj;
      this.modelLogData.append("loading the image.");
      pseudoMat = new int[5][5][3];
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
      modelLogData.append("Flip image operation: " + axisDirection);
      return this;
    }

    /**
     * Brightness image operation on an image.
     *
     * @param deltaChangeValue Amount to change for the individual pixels of the image.
     * @return Default image objected created in the constructor of the class.
     */
    @Override
    public RGBImageInterface changeBrightness(int deltaChangeValue) {
      modelLogData.append("Brightness image operation: " + deltaChangeValue);
      return this;
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
      modelLogData.append("Sharpness image operation KernelType: " + kernelType
              + " split percent:" + splitPercentage);
      return this;
    }

    /**
     * Combine image operation on an image.
     *
     * @param imageComponents List of images having different color-greyscale images.
     * @return Default image objected created in the constructor of the class.
     * @throws IllegalArgumentException If the arguments passed are not valid.
     */
    @Override
    public RGBImageInterface combineImageComponents(java.util.List<RGBImageInterface> imageComponents)
            throws IllegalArgumentException {
      modelLogData.append("Combine image operation on a "
              + "list of images having size:" + imageComponents.size());
      return this;
    }

    /**
     * Split image operation on an image.
     *
     * @return Default image objected created in the constructor of the class.
     * @throws IOException If the arguments passed are not valid.
     */
    @Override
    public List<RGBImageInterface> splitImageComponents() {
      modelLogData.append("Split operation on an image.");
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
      modelLogData.append("Get single component on an image: " + colorValue);
      return this;
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
      modelLogData.append("Get greyscale component on an image: "
              + greyScaleType + " split percent:" + splitPercentage);
      return this;
    }

    /**
     * Sepia image operation on an image.
     *
     * @return Default image objected created in the constructor of the class.
     */
    @Override
    public RGBImageInterface sepiaImage(double splitPercentage) {
      modelLogData.append("Apply sepia color transformation on an image."
              + " split percent:" + splitPercentage);
      return this;
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
      modelLogData.append("Level adjustment of an image. B:" + b + " M:" + m
              + " W:" + w + " split percent:" + splitPercentage);
      return this;
    }

    /**
     * Method represents the histogram plotting of the r,g,b pixel operation on an image.
     * The histogram represents the intensity values Vs frequency of those values on the axes.
     *
     * @return An image as the result of the action performed on the present image.
     */
    @Override
    public RGBImageInterface getPixelHistogram() {
      modelLogData.append("Histogram operation on an image.");
      return this;
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
      modelLogData.append("Image compression Operation. "
              + "Compression Percentage: " + compressionPercentage);
      return this;
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
      modelLogData.append("Color correction on an image. Split percent: " + splitPercentage);
      return this;
    }

    /**
     * Get height of the image currently in use.
     *
     * @return Zero default value.
     */
    @Override
    public int getImageHeight() {
      modelLogData.append("Get height of an image.");
      return 4;
    }

    /**
     * Get width of the image currently in use.
     *
     * @return Zero default value.
     */
    @Override
    public int getImageWidth() {
      modelLogData.append("Get width of an image.");
      return 4;
    }

    /**
     * Get pixel matrix of the image currently in use.
     *
     * @return Zero default value.
     */
    @Override
    public int[][][] getPixel() {
      modelLogData.append("Get pixel matrix of an image.");
      return new int[4][4][3];
    }

    /**
     * The method is used to check and assign new image matrix value to the model currently in use.
     *
     * @param pixelMatrix 3-d Matrix signifying the pixels of the present image in use.
     */
    @Override
    public void checkAndAssignValues(int[][][] pixelMatrix) {
      modelLogData.append("Getting pixel values from the caller");
      this.pseudoMat = pixelMatrix;
    }
  }

  private GraphicalControllerInterface controllerInterface;

  private StringBuilder viewLog;

  private StringBuilder modelLog;

  /**
   * The method sets up the initial values for every testing process.
   * It creates an object of mock model and view. Assigns it to controller before every test.
   * Also create two different log variables to store the log data from view and model mocks.
   */
  @Before
  public void setUp() {
    viewLog = new StringBuilder();
    modelLog = new StringBuilder();
    IView view = new ViewMock(viewLog);
    RGBImageInterface model = new ModelMock(modelLog, null);
    controllerInterface = new GraphicalController(model, view);
  }

  /**
   * The test tests whether the controller properly sends display data to the view.
   * The test passes if assertions are true else it fails.
   *
   * @throws IOException Throws exception if there is an error in I/O operations.
   */
  @Test
  public void testSetDisplay() throws IOException {
    StringBuilder viewLog = new StringBuilder();
    StringBuilder modelLog = new StringBuilder();
    IView view = new ViewMock(viewLog);
    RGBImageInterface model = new ModelMock(modelLog, null);
    RGBImageControllerInterface controllerInterface = new GraphicalController(model, view);
    controllerInterface.goCall();
    assertEquals("Features from the mock view model."
            + "Setting up the display for the mock view model.", viewLog.toString());
  }

  /**
   * This method tests whether the feature value gets assigned properly to the view from controller.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testFeatures() {
    StringBuilder viewLog = new StringBuilder();
    StringBuilder modelLog = new StringBuilder();
    IView view = new ViewMock(viewLog);
    RGBImageInterface model = new ModelMock(modelLog, null);
    RGBImageControllerInterface controllerInterface = new GraphicalController(model, view);
    assertEquals("Features from the mock view model.", viewLog.toString());
  }

  /**
   * This method tests whether the sharpness functionality sends correct data to model and view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   *
   * @throws IOException Throws exception if there is an error in I/O operations.
   */
  @Test
  public void testSharpness() throws IOException {
    controllerInterface.changeSharpness(1);
    String expResultView = "Features from the mock view model."
            + "Single Split Preview"
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image."
            + "Sharpness image operation KernelType: 1 split percent:0.0"
            + "Sharpness image operation KernelType: 1 split percent:100.0";
    assertEquals(expResultView, viewLog.toString());
    assertEquals(expResultModel, modelLog.toString());

  }

  /**
   * This method tests whether the greyscale functionality sends correct data to model and view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   *
   * @throws IOException Throws exception if there is an error in I/O operations.
   */
  @Test
  public void testGreyScale() throws IOException {
    controllerInterface.applyGreyScale();
    String expResultView = "Features from the mock view model."
            + "Multiple Split Preview"
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image."
            + "Get greyscale component on an image: 2"
            + " split percent:1";
    assertEquals(expResultView, viewLog.toString());
    assertEquals(expResultModel, modelLog.toString());
  }

  /**
   * This method tests whether the sepia functionality sends correct data to model and view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testSepia() {
    controllerInterface.applySepia();
    String expResultView = "Features from the mock view model."
            + "Single Split Preview"
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image."
            + "Apply sepia color transformation on an image. "
            + "split percent:0.0Apply sepia color transformation on an image. "
            + "split percent:100.0";
    assertEquals(expResultView, viewLog.toString());
    assertEquals(expResultModel, modelLog.toString());
  }

  /**
   * This tests whether the color correction functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testColorCorrection() {
    controllerInterface.applyColorCorrection();
    String expResultView = "Features from the mock view model."
            + "Single Split Preview"
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image.Color correction on an image. "
            + "Split percent: 0.0Color correction on an image. "
            + "Split percent: 100.0";
    assertEquals(expResultView, viewLog.toString());
    assertEquals(expResultModel, modelLog.toString());
  }

  /**
   * This tests whether the level adjustment functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testLevelAdjustment() {
    controllerInterface.levelAdjustImage();
    String expResultView = "Features from the mock view model."
            + "Multiple Split Preview"
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image.Color correction on an image. "
            + "Level adjustment of an image. B:2 M:3"
            + " W:" + 4 + " split percent:1";
    assertEquals(expResultView, viewLog.toString());
    assertEquals(expResultModel, modelLog.toString());
  }

  /**
   * This method tests whether the flip functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testFlipImage() {
    controllerInterface.flipImage(1);
    String expResultView = "Features from the mock view model."
            + "Display image from the mock view model."
            + "Display histogram from the mock view model.";
    String expResultModel = "loading the image.Flip image operation: 1";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());

  }

  /**
   * This tests whether the single-greyscale functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testSingleComponent() {
    controllerInterface.getSingleComponentImage(2);
    String expResultView = "Features from the mock view model."
            + "Display image from the mock view model."
            + "Display histogram from the mock view model.";
    String expResultModel = "loading the image.Get single component on an image: 2";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());
  }

  /**
   * This tests whether the compression functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testCompressImage() {
    controllerInterface.compressImage();
    String expResultView = "Features from the mock view model."
            + "Single Split Preview"
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image.Image compression Operation. "
            + "Compression Percentage: 0.0";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());
  }

  /**
   * This tests whether the setting up of live img functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testLiveImage() {
    controllerInterface.setLiveImage(1);
    String expResultView = "Features from the mock view model."
            + "Display image from the mock view model."
            + "Display histogram from the mock view model.";
    String expResultModel = "loading the image.Getting pixel values from the caller";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());
  }

  /**
   * This tests whether the exception functionality from view is reacted correctly by model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testExceptionFromView() {
    controllerInterface.getExceptionFromExternalEnv(new Exception("Test Exception"));
    String expResultView = "Features from the mock view model."
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image.";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());
  }

  /**
   * This tests whether the load functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testLoadImage() {
    controllerInterface.loadImage();
    String expResultView = "Features from the mock view model."
            + "Input file Path.Display image from the mock view model."
            + "Display histogram from the mock view model."
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image."
            + "Getting pixel values from the caller"
            + "Get pixel matrix of an image."
            + "Getting pixel values from the caller"
            + "Get width of an image.Get height of an image."
            + "Get pixel matrix of an image."
            + "Histogram operation on an image."
            + "Get width of an image."
            + "Get height of an image."
            + "Get pixel matrix of an image.";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());
  }

  /**
   * This tests whether the save functionality sends correct data to model & view.
   * It checks the data received to model and view against the expected log data.
   * The test passes if assertions are true else it fails.
   */
  @Test
  public void testSaveImage() {
    controllerInterface.saveImage();
    String expResultView = "Features from the mock view model."
            + "Output file Path."
            + "Pop up message for the mock view model.";
    String expResultModel = "loading the image."
            + "Get pixel matrix of an image."
            + "Get width of an image.Get height of an image."
            + "Get pixel matrix of an image.";
    assertEquals(expResultModel, modelLog.toString());
    assertEquals(expResultView, viewLog.toString());
  }

}
