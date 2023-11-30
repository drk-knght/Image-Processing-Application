package controller.graphicalcontroller;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import controller.RGBImageControllerInterface;
import enums.LevelAdjustment;
import enums.UpdateType;
import controller.features.FeatureImpl;
import controller.features.Features;
import controller.filehandling.reader.FileReader;
import controller.filehandling.reader.InputReaderInterface;
import controller.filehandling.writer.FileWriter;
import controller.filehandling.writer.ImageIOWriter;
import controller.filehandling.writer.OutputWriterInterface;
import model.RGBImage;
import model.RGBImageInterface;
import view.IView;
import view.dialogMenus.multiipdialog.GreyScaleDialog;
import view.dialogMenus.multiipdialog.LevelAdjustDialog;


public class GraphicalController implements GraphicalControllerInterface, RGBImageControllerInterface {

  private RGBImageInterface liveImageModel;

  private RGBImageInterface currentPreviewImage;

  private final IView view;

  private final Features features;

  private boolean isSaved;

  public GraphicalController(IView view) {
    this.view = view;
    this.features = new FeatureImpl(this);
    isSaved = true;
    setFeaturesInView();
  }

  @Override
  public void loadImage() {
    try {
      String filePath = view.getInputFilePath();
      checkNullFilePath(filePath);
      InputReaderInterface fileReader = new FileReader(filePath);
      this.currentPreviewImage = new RGBImage(fileReader.read());
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

  @Override
  public void changeSharpness(int kernelMap) {
    if (checkNullImage()) {
      return;
    }
    try {
      int splitPercentage = view.displayDialogSingleSplitPreview("Split Preview Option",
              "The preview % of image on which change in sharpness operation is visible");
      RGBImageInterface displayImage = this.liveImageModel.changeSharpness(kernelMap, splitPercentage);
      currentPreviewImage = this.liveImageModel.changeSharpness(kernelMap, 100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Changing the sharpness of the image failed. \nReason: " + ex.getMessage());
    }
  }

  @Override
  public void applyGreyScale() {
    if (checkNullImage()) {
      return;
    }
    try {
      List<Integer> greyScaleValues = view.displayDialogMultiINPreview(new GreyScaleDialog((JFrame) view, "Greyscale Split"));
      int splitPercentage = greyScaleValues.get(0);
      int greyScaleValueMap = greyScaleValues.get(1);
      RGBImageInterface displayImage = this.liveImageModel.greyScaleImage(greyScaleValueMap, splitPercentage);
      currentPreviewImage = this.liveImageModel.greyScaleImage(greyScaleValueMap, 100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Grey scale operation failed. \nReason: " + ex.getMessage());
    }
  }

  @Override
  public void applySepia() {
    if (checkNullImage()) {
      return;
    }
    try {
      int splitPercentage = view.displayDialogSingleSplitPreview("Sepia Preview Option",
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

  @Override
  public void applyColorCorrection() {
    if (checkNullImage()) {
      return;
    }
    try {
      int splitPercentage = view.displayDialogSingleSplitPreview("Color Correction Split",
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

  @Override
  public void levelAdjustImage() {
    if (checkNullImage()) {
      return;
    }
    try {
      List<Integer> levelAdjustmentValues = view.displayDialogMultiINPreview(new LevelAdjustDialog((JFrame) view, "Level Adjustment Split"));
      int blackPoint = levelAdjustmentValues.get(LevelAdjustment.BLACK.levelValue);
      int midPoint = levelAdjustmentValues.get(LevelAdjustment.MID.levelValue);
      int highlightPoint = levelAdjustmentValues.get(LevelAdjustment.HIGHLIGHT.levelValue);
      int splitPercentage = levelAdjustmentValues.get(0);
      RGBImageInterface displayImage = this.liveImageModel.levelsAdjustment(blackPoint, midPoint, highlightPoint, splitPercentage);
      currentPreviewImage = this.liveImageModel.levelsAdjustment(blackPoint, midPoint, highlightPoint, 100);
      refreshImageOnScreen(displayImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Level adjustment operation failed. \nReason: " + ex.getMessage());
    }
  }

  @Override
  public void flipImage(int axisNameMap) {
    if (checkNullImage()) {
      return;
    }
    try {
      currentPreviewImage = this.currentPreviewImage.flipImage(axisNameMap);
    } catch (Exception ex) {
      view.setErrorMessage("Flip operation failed. \nReason: " + ex.getMessage());
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void getSingleComponentImage(int colorType) {
    if (checkNullImage()) {
      return;
    }
    try {
      currentPreviewImage = this.currentPreviewImage.getSingleComponentImage(colorType);
    } catch (Exception ex) {
      view.setErrorMessage("Single channel operation failed. \nReason: " + ex.getMessage());
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void compressImage() {
    if (checkNullImage()) {
      return;
    }
    try {
      int compressionPercentage = view.displayDialogSingleSplitPreview("Image Compression Factor",
              "The current compression factor of the image is");
      currentPreviewImage = this.currentPreviewImage.compressImage(compressionPercentage);
      refreshImageOnScreen(currentPreviewImage);
    } catch (NullPointerException ex) {
      getExceptionFromExternalEnv(ex);
    } catch (Exception ex) {
      view.setErrorMessage("Compression operation failed. \nReason: " + ex.getMessage());
    }
  }

  @Override
  public void setLiveImage(int updateType) {
    if (checkNullImage()) {
      return;
    }
    updateLiveImage(updateType);
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void getExceptionFromExternalEnv(Exception ex) {
    view.setPopupMessage("Looks like you have closed the pop up dialog.\nCurrent operation is being cancelled.");
  }

  private void setFeaturesInView() {
    this.view.setFeatures(features);
  }

  private boolean checkNullImage() {
    if (this.liveImageModel == null) {
      view.setErrorMessage("No image has been loaded but an action has been requested."
              + "\nLoad an image to use the operations.");
      return true;
    }
    return false;
  }

  private Image getLiveImage(RGBImageInterface liveImageModel) {
    return ImageIOWriter.getBufferedImage(liveImageModel.getImageWidth(), liveImageModel.getImageHeight(), liveImageModel.getPixel());
  }

  private void refreshImageOnScreen(RGBImageInterface resultImage) {
    Image liveImage = getLiveImage(resultImage);
    Image liveImageHistogram = getLiveImage(resultImage.getPixelHistogram());

    this.view.displayImage(liveImage);
    this.view.displayHistogram(liveImageHistogram);
  }

  private void messageForUnsavedImage() {
    if (!this.isSaved) {
      view.setPopupMessage("The image currently in preview is not saved. Applying load on new image");
    }
  }

  private void updateLiveImage(int updateType) {
    if (updateType == UpdateType.OLD.ordinal()) {
      this.currentPreviewImage = new RGBImage(liveImageModel.getPixel());
    } else {
      this.liveImageModel = new RGBImage(currentPreviewImage.getPixel());
    }
  }

  private void checkNullFilePath(String filePath) {
    if (filePath == null || filePath.isEmpty()) {
      throw new IllegalArgumentException("Operation cancelled.");
    }
  }

  @Override
  public void goCall() throws IOException {
    this.view.setDisplay();
  }
}
