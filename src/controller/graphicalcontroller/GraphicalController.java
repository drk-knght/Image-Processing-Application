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

  public GraphicalController(IView view){
    this.view=view;
    this.features=new FeatureImpl(this);
    isSaved=true;
    setFeaturesInView();
  }

  @Override
  public void loadImage() {
    String filePath= null;
    try{
      filePath= view.getInputFilePath();
      checkNullFilePath(filePath);
    }
    catch(Exception ex){
      view.setPopupMessage(ex.getMessage());
      return;
    }
    try {

      InputReaderInterface fileReader=new FileReader(filePath);
      this.currentPreviewImage=new RGBImage(fileReader.read());
    }
    catch (Exception ex){
      view.setErrorMessage("Wrong or illegal value passed to the file load operation.");
      return;
    }
    messageForUnsavedImage();
    isSaved=false;
    updateLiveImage(UpdateType.NEW.ordinal());
    refreshImageOnScreen(this.currentPreviewImage);
    view.setPopupMessage("Image Preview Loaded");

  }

  @Override
  public void saveImage() {
    if(checkNullImage()){
      return;
    }
    String filePath=null;
    try {
      filePath=view.getOutputFilePath();
      checkNullFilePath(filePath);
    }
    catch (Exception ex){
      view.setPopupMessage(ex.getMessage());
      return;
    }

    try {
      OutputWriterInterface fileWriter=new FileWriter(filePath);
      fileWriter.write(this.liveImageModel);
      isSaved=true;
    }
    catch (Exception ex){
      view.setErrorMessage(ex.getMessage());
      return;
    }
    view.setPopupMessage("Image saved to Local disk");
  }

  @Override
  public void changeSharpness(int kernelMap) {
    if(checkNullImage()){
      return;
    }
    int splitPercentage;
    try {
      splitPercentage=view.displayDialogSingleSplitPreview("Split Preview Option",
              "The preview % of image on which change in sharpness operation is visible");
    }
    catch (Exception ex){
      getExceptionFromExternalEnv(ex);
      return;
    }
    RGBImageInterface displayImage=null;
    try{
      displayImage=this.liveImageModel.changeSharpness(kernelMap,splitPercentage);
      currentPreviewImage=this.liveImageModel.changeSharpness(kernelMap,100);
    }
    catch (Exception ex){
      view.setErrorMessage("Changing the sharpness of the image failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(displayImage);
  }

  @Override
  public void applyGreyScale() {
    if(checkNullImage()){
      return;
    }
    List<Integer> greyScaleValues=view.displayDialogMultiINPreview(new GreyScaleDialog((JFrame) view, "Greyscale Split"));
    int splitPercentage;
    int greyScaleValueMap;
    try{
      splitPercentage=greyScaleValues.get(0);
      greyScaleValueMap=greyScaleValues.get(1);
    }
    catch (Exception ex){
      getExceptionFromExternalEnv(ex);
      return;
    }
    RGBImageInterface displayImage=null;
    try{
      displayImage=this.liveImageModel.greyScaleImage(greyScaleValueMap,splitPercentage);
      currentPreviewImage=this.liveImageModel.greyScaleImage(greyScaleValueMap,100);
    }
    catch (Exception ex){
      view.setErrorMessage("Grey scale operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(displayImage);
  }

  @Override
  public void applySepia() {
    if(checkNullImage()){
      return;
    }
    int splitPercentage;
    try{
      splitPercentage=view.displayDialogSingleSplitPreview("Sepia Preview Option",
              "The preview % of image on which change in sepia operation is visible");
    }
    catch (Exception ex){
      getExceptionFromExternalEnv(ex);
      return;
    }
    RGBImageInterface displayImage=null;
    try {

      displayImage=this.liveImageModel.sepiaImage(splitPercentage);
      currentPreviewImage=this.liveImageModel.sepiaImage(100);
    }
    catch (Exception ex){
      view.setErrorMessage("Sepia Transformation operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(displayImage);
  }

  @Override
  public void applyColorCorrection() {
    if(checkNullImage()){
      return;
    }
    int splitPercentage;
    try {
      splitPercentage=view.displayDialogSingleSplitPreview("Color Correction Split",
              "The preview % of image on which Color Correction operation is visible");
    }
    catch (Exception ex){
      getExceptionFromExternalEnv(ex);
      return;
    }
    RGBImageInterface displayImage=null;
    try {
      displayImage=this.liveImageModel.colorCorrectionImage(splitPercentage);
      currentPreviewImage=this.liveImageModel.colorCorrectionImage(100);
    }
    catch (Exception ex){
      view.setErrorMessage("Color correction operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(displayImage);
  }

  @Override
  public void levelAdjustImage(){
    if(checkNullImage()){
      return;
    }
    RGBImageInterface displayImage=null;
    int blackPoint;
    int midPoint;
    int highlightPoint;
    int splitPercentage;
    try{
      List<Integer>levelAdjustmentValues=view.displayDialogMultiINPreview(new LevelAdjustDialog((JFrame)view, "Level Adjustment Split"));
      blackPoint=levelAdjustmentValues.get(LevelAdjustment.BLACK.levelValue);
      midPoint=levelAdjustmentValues.get(LevelAdjustment.MID.levelValue);
      highlightPoint=levelAdjustmentValues.get(LevelAdjustment.HIGHLIGHT.levelValue);
      splitPercentage=levelAdjustmentValues.get(0);
    }
    catch (Exception ex){
      getExceptionFromExternalEnv(ex);
      return;
    }
    try {
      displayImage=this.liveImageModel.levelsAdjustment(blackPoint,midPoint,highlightPoint,splitPercentage);
      currentPreviewImage=this.liveImageModel.levelsAdjustment(blackPoint,midPoint,highlightPoint,100);
    }
    catch (Exception ex){
      view.setErrorMessage("Level adjustment operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(displayImage);
  }

  @Override
  public void flipImage(int axisNameMap) {
    if(checkNullImage()){
      return;
    }
    try {
      currentPreviewImage=this.currentPreviewImage.flipImage(axisNameMap);
    }
    catch (Exception ex){
      view.setErrorMessage("Flip operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void getSingleComponentImage(int colorType) {
    if(checkNullImage()){
      return;
    }
    try {
      currentPreviewImage=this.currentPreviewImage.getSingleComponentImage(colorType);
    }
    catch (Exception ex){
      view.setErrorMessage("Single channel operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void compressImage() {
    if(checkNullImage()){
      return;
    }
    int compressionPercentage;
    try {
      compressionPercentage=view.displayDialogSingleSplitPreview("Image Compression Factor",
              "The current compression factor of the image is");
    }
    catch (Exception ex){
      getExceptionFromExternalEnv(ex);
      return;
    }
    try {
      currentPreviewImage=this.currentPreviewImage.compressImage(compressionPercentage);
    }
    catch (Exception ex){
      view.setErrorMessage("Compression operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void setLiveImage(int updateType) {
    if(checkNullImage()){
      return;
    }
    updateLiveImage(updateType);
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void getExceptionFromExternalEnv(Exception ex) {
    view.setPopupMessage("Looks like you have closed the pop up dialog.\nCurrent operation is being cancelled.");
  }

  private void setFeaturesInView(){
    this.view.setFeatures(features);
  }

  private boolean checkNullImage(){
    if(this.liveImageModel==null){
      view.setErrorMessage("No image has been loaded but an action has been requested."
              + "\nLoad an image to use the operations.");
      return true;
    }
    return false;
  }

  private Image getLiveImage(RGBImageInterface liveImageModel){
    return ImageIOWriter.getBufferedImage(liveImageModel.getImageWidth(),liveImageModel.getImageHeight(), liveImageModel.getPixel());
  }

  private void refreshImageOnScreen(RGBImageInterface resultImage){
    Image liveImage=getLiveImage(resultImage);
    Image liveImageHistogram=getLiveImage(resultImage.getPixelHistogram());

    this.view.displayImage(liveImage);
    this.view.displayHistogram(liveImageHistogram);
  }

  private void messageForUnsavedImage(){
    if(!this.isSaved){
      view.setPopupMessage("The image currently in preview is not saved. Applying load on new image");
    }
  }

  private void updateLiveImage(int updateType){
    if(updateType== UpdateType.OLD.ordinal()){
      this.currentPreviewImage=new RGBImage(liveImageModel.getPixel());
    }
    else{
      this.liveImageModel=new RGBImage(currentPreviewImage.getPixel());
    }
  }

  private void checkNullFilePath(String filePath){
    if(filePath==null || filePath.equals("")){
      throw new IllegalArgumentException("Operation cancelled.");
    }
  }

  @Override
  public void goCall() throws IOException {
    this.view.setDisplay();
  }
}
