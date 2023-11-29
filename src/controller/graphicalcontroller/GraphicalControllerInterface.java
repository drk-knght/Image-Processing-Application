package controller.graphicalcontroller;


public interface GraphicalControllerInterface {

  void loadImage(String filePath);

  void saveImage(String filePath);

  void changeSharpness(int kernelMap,int splitPercentage);

  void applyGreyScale(int greyScaleType, int splitPercentage);

  void applySepia(int splitPercentage);

  void applyColorCorrection(int splitPercentage);

  void levelAdjustImage(int black, int mid, int highlight,int splitPercentage);

  void flipImage(int axisNameMap);

  void getSingleComponentImage(int colorType);

  void compressImage(int compressionPercentage);

  void setLiveImage(int updateType);

  void getExceptionFromExternalEnv(Exception ex);

}
