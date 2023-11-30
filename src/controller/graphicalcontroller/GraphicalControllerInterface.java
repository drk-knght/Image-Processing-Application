package controller.graphicalcontroller;

/**
 * This interface
 */

public interface GraphicalControllerInterface {

  void loadImage();

  void saveImage();

  void changeSharpness(int kernelMap);

  void applyGreyScale();

  void applySepia();

  void applyColorCorrection();

  void levelAdjustImage();

  void flipImage(int axisNameMap);

  void getSingleComponentImage(int colorType);

  void compressImage();

  void setLiveImage(int updateType);

  void getExceptionFromExternalEnv(Exception ex);

}
