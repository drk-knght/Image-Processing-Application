package controller.features;


public interface Features {

  void greyScale();

  void flip(int axisFlip);

  void colorCorrection();

  void changeSharpness(int kernelMap);

  void sepia();

  void levelAdjustment();

  void getSingleComponent(int colorType);

  void compressImage();

  void load();

  void save();

  void applyOperation();

  void cancelOperation();

  void getExceptionFromView(Exception ex);
}
