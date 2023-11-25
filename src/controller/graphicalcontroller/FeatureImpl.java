package controller.graphicalcontroller;


import controller.UpdateType;

public class FeatureImpl implements Features{

  private final GraphicalControllerInterface controller;

  public FeatureImpl(GraphicalControllerInterface controller){
    this.controller=controller;
  }

  @Override
  public void greyScale(int greyScaleType, int splitPercent) {
    controller.applyGreyScale(greyScaleType,splitPercent);
  }

  @Override
  public void flip(int axisFlip) {
    controller.flipImage(axisFlip);
  }

  @Override
  public void colorCorrection(int splitPercent) {
    controller.applyColorCorrection(splitPercent);
  }

  @Override
  public void changeSharpness(int sharpen, int splitPercent) {
    controller.changeSharpness(sharpen,splitPercent);
  }

  @Override
  public void sepia(int splitPercent) {
    controller.applySepia(splitPercent);
  }

  @Override
  public void levelAdjustment(int b, int m, int w, int splitPercent) {
    controller.levelAdjustImage(b,m,w,splitPercent);
  }

  @Override
  public void getSingleComponent(int colorType) {
    controller.getSingleComponentImage(colorType);
  }

  @Override
  public void compressImage(int compressionFactor) {
    controller.compressImage(compressionFactor);
  }

  @Override
  public void load(String imagePath) {
    controller.loadImage(imagePath);
  }

  @Override
  public void save(String imagePath) {
    controller.saveImage(imagePath);
  }

  @Override
  public void applyOperation() {
    controller.setLiveImage(UpdateType.NEW.ordinal());
  }

  @Override
  public void cancelOperation() {
    controller.setLiveImage(UpdateType.OLD.ordinal());
  }
}
