package controller.features;


public interface Features {

  void greyScale(int greyScaleType, int splitPercent);

  void flip(int axisFlip);

  void colorCorrection(int splitPercent);

  void changeSharpness(int sharpen, int splitPercent);

  void sepia(int splitPercent);

  void levelAdjustment(int b, int m, int w, int splitPercent);

  void getSingleComponent(int colorType);

  void compressImage(int compressionFactor);

  void load(String imagePath);

  void save(String imagePath);

  void applyOperation();

  void cancelOperation();

  void getExceptionFromView(Exception ex);
}
