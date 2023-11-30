package controller.graphicalcontroller;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import controller.RGBImageControllerInterface;
import controller.features.FeatureImpl;
import controller.features.Features;
import controller.filehandling.reader.FileReader;
import controller.filehandling.reader.InputReaderInterface;
import controller.filehandling.writer.FileWriter;
import controller.filehandling.writer.ImageIOWriter;
import controller.filehandling.writer.OutputWriterInterface;
import enums.LevelAdjustment;
import enums.UpdateType;
import model.RGBImageInterface;
import view.IView;
import view.dialogmenus.multiipdialog.GreyScaleDialog;
import view.dialogmenus.multiipdialog.LevelAdjustDialog;


/**
 * The class represents a controller which manages the graphical user interface for the MVC app.
 * The controller gets the model and view object from the constructor in the parameter.
 * The GUI controller can perform several actions like display image and histogram on the display.
 * It can handle several image operations like blur, sepia, etc. along with split preview options.
 * The controller is the one which interacts with the external env so it performs load and save op.
 */
public class GraphicalController implements GraphicalControllerInterface,
        RGBImageControllerInterface {

  private RGBImageInterface liveImageModel;

  private RGBImageInterface currentPreviewImage;

  private final IView view;

  private final Features features;

  private boolean isSaved;

  /**
   * The constructor of the GUI controller initializes the view and the model parameter for MVC.
   * It also passes itself to feature class to get a features object for call backs implementations.
   * Sets the all the features for the passed the view interface and complete the load operations.
   *
   * @param liveImageModel The object of the model on which the image operation would be applied.
   * @param view           The object of the view on which the GUI is displayed for the present app.
   */
  public GraphicalController(RGBImageInterface liveImageModel, IView view) {
    this.view = view;
    this.liveImageModel = liveImageModel;
    this.currentPreviewImage = liveImageModel;
    this.features = new FeatureImpl(this);
    isSaved = true;
    setFeaturesInView();
  }

  /**
   * The method does load operation for the present MVC architecture application (I/O Part).
   * It loads an image from the passed file path from the user & gives ip to model about the data.
   * The load method reads the file reader as per the extension converts all of them to same scale.
   */
  @Override
  public void loadImage() {
    try {
      String filePath = view.getInputFilePath();
      checkNullFilePath(filePath);
      InputReaderInterface fileReader = new FileReader(filePath);
      this.currentPreviewImage.checkAndAssignValues(fileReader.read());
    } catch (IllegalArgumentException ex) {
      view.setPopupMessage(ex.getMessage());
      return;
    } catch (IOException ex) {
      view.setErrorMessage("Wrong or illegal value passed to the file load operation.");
      return;
    }
    messageForUnsavedImage();
    isSaved = false;
    updateLiveImage(UpdateType.NEW.ordinal());
    refreshImageOnScreen(this.currentPreviewImage);
    view.setPopupMessage("Image Preview Loaded");
  }

  /**
   * The method does save operation for the present MVC architecture application (I/O Part).
   * It saves an image to the selected file path from the user with required valid file extension.
   * The save method opens the file writer as per the required extension & saves the data to it.
   */
  @Override
  public void saveImage() {
    if (checkNullImage()) {
      return;
    }
    try {
      String filePath = view.getOutputFilePath();
      checkNullFilePath(filePath);
      OutputWriterInterface fileWriter = new FileWriter(filePath);
      fileWriter.write(this.liveImageModel);
      isSaved = true;
    } catch (IllegalArgumentException ex) {
      view.setPopupMessage(ex.getMessage());
      return;
    } catch (IOException ex) {
      view.setErrorMessage(ex.getMessage());
      return;
    }
    view.setPopupMessage("Image saved to Local disk");
  }

  /**
   * The method handles the request to change in sharpness (both BLUR & SHARPEN) op on the img.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   *
   * @param kernelMap Integer representing the map value for BLUR and SHARPEN operation using enums.
   */
  @Override
  public void changeSharpness(int kernelMap) {
    if (checkNullImage()) {
      return;
    }
    try {
      int splitPercentage = view.displayDialogSingleSplitPreview("Split Preview "
                      + "Option",
              "The preview % of image on which change in sharpness operation is visible");
      RGBImageInterface displayImage = this.liveImageModel.changeSharpness(kernelMap,
              splitPercentage);
      currentPreviewImage = this.liveImageModel.changeSharpness(kernelMap, 100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Changing the sharpness of the image failed. \nReason: "
              + ex.getMessage());
    }
  }

  /**
   * The method handles the request to apply greyscale operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  @Override
  public void applyGreyScale() {
    if (checkNullImage()) {
      return;
    }
    try {
      List<Integer> greyScaleValues = view.displayDialogMultiINPreview(
              new GreyScaleDialog((JFrame) view, "Greyscale Split"));
      int splitPercentage = greyScaleValues.get(0);
      int greyScaleValueMap = greyScaleValues.get(1);
      RGBImageInterface displayImage = this.liveImageModel.greyScaleImage(greyScaleValueMap,
              splitPercentage);
      currentPreviewImage = this.liveImageModel.greyScaleImage(greyScaleValueMap,
              100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Grey scale operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method handles the request to apply sepia operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  @Override
  public void applySepia() {
    if (checkNullImage()) {
      return;
    }
    try {
      int splitPercentage = view.displayDialogSingleSplitPreview("Sepia "
                      + "Preview Option",
              "The preview % of image on which change in sepia operation is visible");
      RGBImageInterface displayImage = this.liveImageModel.sepiaImage(splitPercentage);
      currentPreviewImage = this.liveImageModel.sepiaImage(100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Sepia Transformation operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method handles the request to apply color correction operation on the img, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  @Override
  public void applyColorCorrection() {
    if (checkNullImage()) {
      return;
    }
    try {
      int splitPercentage = view.displayDialogSingleSplitPreview("Color "
                      + "Correction Split",
              "The preview % of image on which Color Correction operation is visible");
      RGBImageInterface displayImage = this.liveImageModel.colorCorrectionImage(splitPercentage);
      currentPreviewImage = this.liveImageModel.colorCorrectionImage(100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Color correction operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method handles the request to apply level adjust operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  @Override
  public void levelAdjustImage() {
    if (checkNullImage()) {
      return;
    }
    try {
      List<Integer> levelAdjustmentValues = view.displayDialogMultiINPreview(
              new LevelAdjustDialog((JFrame) view, "Level Adjustment Split"));
      int blackPoint = levelAdjustmentValues.get(LevelAdjustment.BLACK.levelValue);
      int midPoint = levelAdjustmentValues.get(LevelAdjustment.MID.levelValue);
      int highlightPoint = levelAdjustmentValues.get(LevelAdjustment.HIGHLIGHT.levelValue);
      int splitPercentage = levelAdjustmentValues.get(0);
      RGBImageInterface displayImage = this.liveImageModel.levelsAdjustment(blackPoint,
              midPoint, highlightPoint, splitPercentage);
      currentPreviewImage = this.liveImageModel.levelsAdjustment(blackPoint,
              midPoint, highlightPoint, 100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Level adjustment operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method handles the request to apply flip (HORIZONTAL & VERTICAL) op on the img.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   *
   * @param axisNameMap Integer representing the map value for HORIZONTAL & VERTICAL op using enums.
   */
  @Override
  public void flipImage(int axisNameMap) {
    if (checkNullImage()) {
      return;
    }
    try {
      currentPreviewImage = this.currentPreviewImage.flipImage(axisNameMap);
      refreshImageOnScreen(currentPreviewImage);
    } catch (Exception ex) {
      view.setErrorMessage("Flip operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method handles the request to get Color-greyscale (RED,GREEN & BLUE) op on the img.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   *
   * @param colorType Integer representing the map value for RED,GREEN & BLUE operation using enums.
   */
  @Override
  public void getSingleComponentImage(int colorType) {
    if (checkNullImage()) {
      return;
    }
    try {
      currentPreviewImage = this.currentPreviewImage.getSingleComponentImage(colorType);
      refreshImageOnScreen(currentPreviewImage);
    } catch (Exception ex) {
      view.setErrorMessage("Single channel operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method handles the request to apply compression operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  @Override
  public void compressImage() {
    if (checkNullImage()) {
      return;
    }
    try {
      int compressionPercentage = view.displayDialogSingleSplitPreview("Image "
                      + "Compression Factor",
              "The current compression factor of the image is");
      currentPreviewImage = this.currentPreviewImage.compressImage(compressionPercentage);
      refreshImageOnScreen(currentPreviewImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Compression operation failed. \nReason: " + ex.getMessage());
    }
  }

  /**
   * The method sets up the live image by putting a command call to view for the display of the img.
   * The image on display can either be rolled back to the prev version or it can be updated to new.
   * The controller puts the display based on the request user makes through diff UI components.
   *
   * @param updateType Integer value representing the map value for OLD & NEW operation using enums.
   */
  @Override
  public void setLiveImage(int updateType) {
    if (checkNullImage()) {
      return;
    }
    updateLiveImage(updateType);
    refreshImageOnScreen(currentPreviewImage);
  }

  /**
   * The method gets any exception if occurred while view is interacting with the external env.
   * The exception generated is sent back to the controller & it decides what it has to do for it.
   *
   * @param ex The exception object generated from any unexpected event that occurred from ext env.
   */
  @Override
  public void getExceptionFromExternalEnv(Exception ex) {
    view.setPopupMessage("Looks like you have closed the pop up dialog."
            + "\nCurrent operation is being cancelled.");
  }

  private void setFeaturesInView() {
    this.view.setFeatures(features);
  }

  private boolean checkNullImage() {
    if (this.liveImageModel.getImageWidth() == 0 || this.liveImageModel.getImageHeight() == 0
            || this.liveImageModel == null || this.liveImageModel.getPixel() == null) {
      view.setErrorMessage("No image has been loaded but an action has been requested."
              + "\nLoad an image to use the operations.");
      return true;
    }
    return false;
  }

  private Image getLiveImage(RGBImageInterface liveImageModel) {
    return ImageIOWriter.getBufferedImage(liveImageModel.getImageWidth(),
            liveImageModel.getImageHeight(), liveImageModel.getPixel());
  }

  private void refreshImageOnScreen(RGBImageInterface resultImage) {
    Image liveImage = getLiveImage(resultImage);
    Image liveImageHistogram = getLiveImage(resultImage.getPixelHistogram());

    this.view.displayImage(liveImage);
    this.view.displayHistogram(liveImageHistogram);
  }

  private void messageForUnsavedImage() {
    if (!this.isSaved) {
      view.setPopupMessage("The image currently in "
              + "preview is not saved. Applying load on new image");
    }
  }

  private void updateLiveImage(int updateType) {
    if (updateType == UpdateType.OLD.ordinal()) {
      this.currentPreviewImage.checkAndAssignValues(this.liveImageModel.getPixel());
    } else {
      this.liveImageModel.checkAndAssignValues(currentPreviewImage.getPixel());
    }
  }

  private void checkNullFilePath(String filePath) {
    if (filePath == null || filePath.isEmpty()) {
      throw new IllegalArgumentException("Operation cancelled.");
    }
  }

  /**
   * The method which gives command for all the image processing application operations.
   * It encapsulates all the helper command classes objects under the method.
   *
   * @throws IOException Throws exception if invalid data is used in the method.
   */
  @Override
  public void goCall() throws IOException {
    this.view.setDisplay();
  }
}
