package controller.mocks;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import controller.RGBImageControllerInterface;
import controller.features.Features;
import controller.graphicalcontroller.GraphicalController;
import controller.graphicalcontroller.GraphicalControllerInterface;
import view.IView;
import view.dialogMenus.multiipdialog.MultiInputSliderDialogInterface;

import static org.junit.Assert.assertEquals;

public class GraphicControllerView {

  static class ViewMock implements IView {
    StringBuilder viewlogData;

    public ViewMock(StringBuilder logData){
      this.viewlogData=logData;
    }

    @Override
    public void setDisplay() {
      viewlogData.append("Setting up the display for the mock view model.");
    }

    @Override
    public void setPopupMessage(String message) {
      viewlogData.append("Pop up message for the mock view model.");
    }

    @Override
    public void setErrorMessage(String message) {
      viewlogData.append("Error message for the mock view model.");
    }

    @Override
    public void displayImage(Image image) {
      viewlogData.append("Display image from the mock view model.");
    }

    @Override
    public void displayHistogram(Image image) {
      viewlogData.append("Display histogram from the mock view model.");
    }

    @Override
    public void setFeatures(Features features) {
      viewlogData.append("Features from the mock view model.");
    }

    @Override
    public String getInputFilePath() {
      viewlogData.append("Input file Path.");
      return null;
    }

    @Override
    public String getOutputFilePath() {
      viewlogData.append("Output file Path.");
      return null;
    }

    @Override
    public Integer displayDialogSingleSplitPreview(String operationTitle, String labelText) {
      viewlogData.append("Split Preview");
      return null;
    }

    @Override
    public List<Integer> displayDialogMultiINPreview(MultiInputSliderDialogInterface levelAdjustDialog) {
      return null;
    }
  }

//  static class ModelMock implements RGBImageInterface {
//    StringBuilder modelLogData;
//
//    RGBImageInterface imageObj;
//
//    /**
//     * Constructor which assigns a new objects to the fields.
//     *
//     * @param logData  String builder which stores the log data.
//     * @param imageObj An image object which is used for returning from method calls.
//     */
//    public ModelMock(StringBuilder logData, RGBImageInterface imageObj) {
//      this.modelLogData = logData;
//      this.imageObj = imageObj;
//      this.modelLogData.append("loading the image.");
//    }
//
//
//    /**
//     * Flip image operation on an image.
//     *
//     * @param axisDirection Integer representing the axis direction mapping with the Enum data.
//     * @return Default image objected created in the constructor of the class.
//     * @throws IllegalArgumentException If the arguments passed are not valid.
//     */
//    @Override
//    public RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException {
//      modelLogData.append("Flip image operation: " + axisDirection);
//      return this.imageObj;
//    }
//
//    /**
//     * Brightness image operation on an image.
//     *
//     * @param deltaChangeValue Amount to change for the individual pixels of the image.
//     * @return Default image objected created in the constructor of the class.
//     */
//    @Override
//    public RGBImageInterface changeBrightness(int deltaChangeValue) {
//      modelLogData.append("Brightness image operation: " + deltaChangeValue);
//      return this.imageObj;
//    }
//
//    /**
//     * Sharpness image operation on an image.
//     *
//     * @param kernelType Containing the mapping to an Enum which store data for changing sharpness.
//     * @return Default image objected created in the constructor of the class.
//     * @throws IllegalArgumentException If the arguments passed are not valid.
//     */
//    @Override
//    public RGBImageInterface changeSharpness(int kernelType, double splitPercentage)
//            throws IllegalArgumentException {
//      modelLogData.append("Sharpness image operation KernelType: " + kernelType
//              + " split percent:" + splitPercentage);
//      return this.imageObj;
//    }
//
//    /**
//     * Combine image operation on an image.
//     *
//     * @param imageComponents List of images having different color-greyscale images.
//     * @return Default image objected created in the constructor of the class.
//     * @throws IllegalArgumentException If the arguments passed are not valid.
//     */
//    @Override
//    public RGBImageInterface combineImageComponents(java.util.List<RGBImageInterface> imageComponents)
//            throws IllegalArgumentException {
//      modelLogData.append("Combine image operation on a "
//              + "list of images having size:" + imageComponents.size());
//      return this.imageObj;
//    }
//
//    /**
//     * Split image operation on an image.
//     *
//     * @return Default image objected created in the constructor of the class.
//     * @throws IOException If the arguments passed are not valid.
//     */
//    @Override
//    public List<RGBImageInterface> splitImageComponents() {
//      modelLogData.append("Split operation on an image.");
//      ArrayList<RGBImageInterface> arr = new ArrayList<>();
//      arr.add(imageObj);
//
//      return arr;
//    }
//
//    /**
//     * Get single component image operation on an image.
//     *
//     * @param colorValue Takes the color component type to get a new color-greyscale image.
//     * @return Default image objected created in the constructor of the class.
//     * @throws IllegalArgumentException If the arguments passed are not valid.
//     */
//    @Override
//    public RGBImageInterface getSingleComponentImage(int colorValue)
//            throws IllegalArgumentException {
//      modelLogData.append("Get single component on an image: " + colorValue);
//      return this.imageObj;
//    }
//
//    /**
//     * Greyscale image operation on an image.
//     *
//     * @param greyScaleType Takes the type of action to perform on this to get a new black-white.
//     * @return Default image objected created in the constructor of the class.
//     * @throws IllegalArgumentException If the arguments passed are not valid.
//     */
//    @Override
//    public RGBImageInterface greyScaleImage(int greyScaleType,
//                                            double splitPercentage)
//            throws IllegalArgumentException {
//      modelLogData.append("Get greyscale component on an image: "
//              + greyScaleType + " split percent:" + splitPercentage);
//      return this.imageObj;
//    }
//
//    /**
//     * Sepia image operation on an image.
//     *
//     * @return Default image objected created in the constructor of the class.
//     */
//    @Override
//    public RGBImageInterface sepiaImage(double splitPercentage) {
//      modelLogData.append("Apply sepia color transformation on an image."
//              + " split percent:" + splitPercentage);
//      return this.imageObj;
//    }
//
//    /**
//     * Method represents the level adjustment operation on an image currently present in use.
//     * This method is used to log the data for the mock model.
//     *
//     * @param b               Shadow-point where the intensity of the pixel decreases around that.
//     * @param m               Mid-point where the intensity of the pixel
//     *                        changes non-linearly as per the curve eq.
//     * @param w               Highlight-point where the intensity of the pixel
//     *                        is increased which satisfy the curve.
//     * @param splitPercentage Double value representing the split ratio
//     *                        of original and modified img.
//     * @return An image as the result of the action performed on the present image.
//     * @throws IllegalArgumentException Exception is thrown If the b,m,w are not in ascending order.
//     */
//    @Override
//    public RGBImageInterface levelsAdjustment(double b, double m,
//                                              double w, double splitPercentage)
//            throws IllegalArgumentException {
//      modelLogData.append("Level adjustment of an image. B:" + b + " M:" + m
//              + " W:" + w + " split percent:" + splitPercentage);
//      return this.imageObj;
//    }
//
//    /**
//     * Method represents the histogram plotting of the r,g,b pixel operation on an image.
//     * The histogram represents the intensity values Vs frequency of those values on the axes.
//     *
//     * @return An image as the result of the action performed on the present image.
//     */
//    @Override
//    public RGBImageInterface getPixelHistogram() {
//      modelLogData.append("Histogram operation on an image.");
//      return this.imageObj;
//    }
//
//    /**
//     * Method represents the compression operation part on the image, and it is a lossy one.
//     * The data of the pixels is lost when it is reverted back to its original size.
//     *
//     * @param compressionPercentage Percent of the image that needs to be thrown on the operation.
//     * @return An image as the result of the action performed on the present image.
//     * @throws IllegalArgumentException Exception is thrown if the % of compression is non-positive.
//     */
//    @Override
//    public RGBImageInterface compressImage(double compressionPercentage)
//            throws IllegalArgumentException {
//      modelLogData.append("Image compression Operation. "
//              + "Compression Percentage: " + compressionPercentage);
//      return this.imageObj;
//    }
//
//    /**
//     * Method represents the color correction part on an existing image.
//     * The intensity values for different channels are aligned long the common global max peak.
//     *
//     * @param splitPercentage Double value representing the split ratio of original & modified img.
//     * @return An image as the result of the action performed on the present image.
//     */
//    @Override
//    public RGBImageInterface colorCorrectionImage(double splitPercentage) {
//      modelLogData.append("Color correction on an image. Split percent: " + splitPercentage);
//      return this.imageObj;
//    }
//
//    /**
//     * Get height of the image currently in use.
//     *
//     * @return Zero default value.
//     */
//    @Override
//    public int getImageHeight() {
//      modelLogData.append("Get height of an image.");
//      return 0;
//    }
//
//    /**
//     * Get width of the image currently in use.
//     *
//     * @return Zero default value.
//     */
//    @Override
//    public int getImageWidth() {
//      modelLogData.append("Get width of an image.");
//      return 0;
//    }
//
//    /**
//     * Get pixel matrix of the image currently in use.
//     *
//     * @return Zero default value.
//     */
//    @Override
//    public int[][][] getPixel() {
//      modelLogData.append("Get pixel matrix of an image.");
//      return new int[0][][];
//    }
//  }

  private GraphicalControllerInterface controllerInterface;

  private StringBuilder log;

  private IView view;

  private String expResult;
  @Before
  public void setUp(){
    log=new StringBuilder();
    view =new ViewMock(log);
    controllerInterface=new GraphicalController(view);
    controllerInterface.loadImage();
    expResult="Features from the mock view model.Input file Path."
            + "Pop up message for the mock view model."
            + "Error message for the mock view model.";
  }

  @Test
  public void testSetDisplay() throws IOException {
    StringBuilder log=new StringBuilder();
    IView view=new ViewMock(log);
    RGBImageControllerInterface controllerInterface=new GraphicalController(view);
    controllerInterface.goCall();
    assertEquals("Features from the mock view model.Setting up the display for the mock view model.",log.toString());
  }

  @Test
  public void testFeatures(){
    StringBuilder log=new StringBuilder();
    IView view=new ViewMock(log);
    RGBImageControllerInterface controllerInterface=new GraphicalController(view);
    assertEquals("Features from the mock view model.",log.toString());
  }

  @Test
  public void testSharpness() throws IOException {
    controllerInterface.changeSharpness(1);
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testGreyScale() throws IOException {
    controllerInterface.applyGreyScale();
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testSepia(){
    controllerInterface.applySepia();
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testColorCorrection(){
    controllerInterface.applyColorCorrection();
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testLevelAdjustment(){
    controllerInterface.levelAdjustImage();
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testFlipImage(){
    controllerInterface.flipImage(1);
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testSingleComponent(){
    controllerInterface.getSingleComponentImage(2);
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testCompressImage(){
    controllerInterface.compressImage();
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testLiveImage(){
    controllerInterface.setLiveImage(1);
    assertEquals(expResult,log.toString());
  }

  @Test
  public void testExceptionFromView(){
    controllerInterface.getExceptionFromExternalEnv(new Exception("Test Exception"));
    expResult="Features from the mock view model."
            + "Input file Path."
            + "Pop up message for the mock view model."
            + "Pop up message for the mock view model.";
    assertEquals(expResult,log.toString());
  }

}
