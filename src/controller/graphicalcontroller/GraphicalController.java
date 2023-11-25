package controller.graphicalcontroller;

import java.awt.*;

import controller.UpdateType;
import controller.filehandling.reader.FileReader;
import controller.filehandling.reader.InputReaderInterface;
import controller.filehandling.writer.FileWriter;
import controller.filehandling.writer.ImageIOWriter;
import controller.filehandling.writer.OutputWriterInterface;
import model.RGBImage;
import model.RGBImageInterface;
import view.IView;

public class GraphicalController implements GraphicalControllerInterface{

  private RGBImageInterface liveImageModel;

  private RGBImageInterface currentPreviewImage;

  private final IView view;

  private final Features features;

  private boolean isSaved;

  public GraphicalController(IView view){
    this.view=view;
    this.features=new FeatureImpl(this);
    isSaved=true;
    this.view.setDisplay();
    setFeaturesInView();
  }

  @Override
  public void loadImage(String filePath) {
    messageForUnsavedImage();
    try {
      InputReaderInterface fileReader=new FileReader(filePath);
      this.currentPreviewImage=new RGBImage(fileReader.read());
      isSaved=false;
    }
    catch (Exception ex){
      view.setErrorMessage(ex.getMessage());
      return;
    }
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
    try{
      currentPreviewImage=this.currentPreviewImage.changeSharpness(kernelMap,splitPercentage);
    }
    catch (Exception ex){
      view.setErrorMessage("Error occurred while changing the sharpness of the image");
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
//    if(this.liveImageModel!=null){
//      refreshImageOnScreen();
//    }
  }

  @Override
  public void applyGreyScale(int greyScaleType, int splitPercentage) {
    if(checkNullImage()){
      return;
    }
    try{
      currentPreviewImage=this.currentPreviewImage.greyScaleImage(greyScaleType,splitPercentage);
    }
    catch (Exception ex){
      view.setErrorMessage("Grey scale operation failed while applying on the image");
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void applySepia(int splitPercentage) {
    if(checkNullImage()){
      return;
    }
    try {
      currentPreviewImage=this.currentPreviewImage.sepiaImage(splitPercentage);
    }
    catch (Exception ex){
      view.setErrorMessage("Grey scale operation failed while applying on the image");
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void applyColorCorrection(int splitPercentage) {
    if(checkNullImage()){
      return;
    }
    try {
      currentPreviewImage=this.currentPreviewImage.colorCorrectionImage(splitPercentage);
    }
    catch (Exception ex){
      view.setErrorMessage("Color correction operation failed while applying on the image");
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void levelAdjustImage(int black, int mid, int highlight,int splitPercentage) {
    if(checkNullImage()){
      return;
    }
    try {
      currentPreviewImage=this.currentPreviewImage.levelsAdjustment(black,mid,highlight,splitPercentage);
    }
    catch (Exception ex){
      view.setErrorMessage("Level adjustment operation failed while applying on the image");
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
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
      view.setErrorMessage("Flip operation failed while applying on the image");
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
      view.setErrorMessage("Single channel operation failed while applying on the image");
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
      view.setErrorMessage("Compression operation failed while applying on the image");
      return;
    }
    refreshImageOnScreen(currentPreviewImage);
  }

  @Override
  public void setLiveImage(int updateType) {
    updateLiveImage(updateType);
    refreshImageOnScreen(currentPreviewImage);
  }

  private void setFeaturesInView(){
    this.view.setFeatures(features);
  }

  private boolean checkNullImage(){
    if(this.liveImageModel==null){
      view.setErrorMessage("No image has been loaded but an operation has been attempted");
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
}
