package controller.graphicalcontroller;

import java.awt.*;
import java.io.IOException;

import controller.RGBImageControllerInterface;
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
  public void loadImage(String filePath) {
//    messageForUnsavedImage();
    try {
      InputReaderInterface fileReader=new FileReader(filePath);
      this.currentPreviewImage=new RGBImage(fileReader.read());
//      isSaved=false;
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
  public void saveImage(String filePath) {
    if(checkNullImage()){
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
  public void changeSharpness(int kernelMap,int splitPercentage) {
    if(checkNullImage()){
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
  public void applyGreyScale(int greyScaleType, int splitPercentage) {
    if(checkNullImage()){
      return;
    }
    RGBImageInterface displayImage=null;
    try{
      displayImage=this.liveImageModel.greyScaleImage(greyScaleType,splitPercentage);
      currentPreviewImage=this.liveImageModel.greyScaleImage(greyScaleType,100);
    }
    catch (Exception ex){
      view.setErrorMessage("Grey scale operation failed. \nReason: "+ex.getMessage());
      return;
    }
    refreshImageOnScreen(displayImage);
  }

  @Override
  public void applySepia(int splitPercentage) {
    if(checkNullImage()){
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
  public void applyColorCorrection(int splitPercentage) {
    if(checkNullImage()){
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
  public void levelAdjustImage(int black, int mid, int highlight,int splitPercentage) {
    if(checkNullImage()){
      return;
    }
    RGBImageInterface displayImage=null;
    try {
      displayImage=this.liveImageModel.levelsAdjustment(black,mid,highlight,splitPercentage);
      currentPreviewImage=this.liveImageModel.levelsAdjustment(black,mid,highlight,100);
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
  public void compressImage(int compressionPercentage) {
    if(checkNullImage()){
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

  @Override
  public void goCall() throws IOException {
    this.view.setDisplay();
  }
}
