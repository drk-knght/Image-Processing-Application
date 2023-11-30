package controller.features;


import enums.UpdateType;
import controller.graphicalcontroller.GraphicalControllerInterface;

public class FeatureImpl implements Features{

  private final GraphicalControllerInterface controller;

  public FeatureImpl(GraphicalControllerInterface controller){
    this.controller=controller;
  }

  @Override
  public void greyScale() {
    controller.applyGreyScale();
  }

  @Override
  public void flip(int axisFlip) {
    controller.flipImage(axisFlip);
  }

  @Override
  public void colorCorrection() {
    controller.applyColorCorrection();
  }

  @Override
  public void changeSharpness(int kernelMap) {
    controller.changeSharpness(kernelMap);
  }

  @Override
  public void sepia(){
    controller.applySepia();
  }

  @Override
  public void levelAdjustment() {
    controller.levelAdjustImage();
  }

  @Override
  public void getSingleComponent(int colorType) {
    controller.getSingleComponentImage(colorType);
  }

//  @Override
//  public void compressImage(int compressionFactor) {
//    controller.compressImage(compressionFactor);
//  }

  @Override
  public void compressImage() {
    controller.compressImage();
  }

  @Override
  public void load() {
//    controller.loadImage(imagePath);
    controller.loadImage();
  }

//  @Override
//  public void save(String imagePath) {
//    controller.saveImage(imagePath);
//  }

  @Override
  public void save() {
    controller.saveImage();
  }

  @Override
  public void applyOperation() {
    controller.setLiveImage(UpdateType.NEW.ordinal());
  }

  @Override
  public void cancelOperation() {
    controller.setLiveImage(UpdateType.OLD.ordinal());
  }

  @Override
  public void getExceptionFromView(Exception ex) {
    controller.getExceptionFromExternalEnv(ex);
  }
}
