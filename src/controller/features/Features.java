package controller.features;

/**
 * This interface represents the features and the functionality supported by controller for view.
 * This represents a high level communication between the controller and the view.
 * Whenever any event happens through the GUI the view give a call back to the controller using it.
 * The controller is the one who gets to know about all the events.
 * It then orders the view to perform the respective actions for each of the incident happening.
 */
public interface Features {

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a greyscale op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void greyScale();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a flip op.
   * The controller then performs the necessary actions to support the request made through UI.
   *
   * @param axisFlip Integer representing the axis along which the flip needs to occur.
   */
  void flip(int axisFlip);

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When it is called the controller knows that the user wants to perform a color correction op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void colorCorrection();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When it is called the controller knows that the user wants to perform a sharpness change op.
   * The controller then performs the necessary actions to support the request made through UI.
   *
   * @param kernelMap Integer representing the map value for BLUR and SHARPEN operation using enums.
   */
  void changeSharpness(int kernelMap);

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a sepia op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void sepia();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller knows that the user wants to perform a level adjust op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void levelAdjustment();


  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller knows that the user wants to perform a color-greyscale op.
   * The controller then performs the necessary actions to support the request made through UI.
   *
   * @param colorType Integer representing the map value of which color greyscale image user wants.
   */
  void getSingleComponent(int colorType);

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a compress op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void compressImage();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a load op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void load();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a save op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void save();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a save img op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void applyOperation();

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform an undo op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  void cancelOperation();
}
