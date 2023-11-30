package controller.graphicalcontroller;

/**
 * This interface represents the controller which handles the graphical user interface of the view.
 * The interface supports several methods which when requested by view are invoked.
 * The controller can command different actions like it can perform IO, get result of img op, etc.
 * It is the one who gets to know first whether any action has happened for any event.
 * First it gets the input and command the respective units as per the requirements of it.
 */

public interface GraphicalControllerInterface {

  /**
   * The method does load operation for the present MVC architecture application (I/O Part).
   * It loads an image from the passed file path from the user & gives ip to model about the data.
   * The load method reads the file reader as per the extension converts all of them to same scale.
   */
  void loadImage();

  /**
   * The method does save operation for the present MVC architecture application (I/O Part).
   * It saves an image to the selected file path from the user with required valid file extension.
   * The save method opens the file writer as per the required extension & saves the data to it.
   */
  void saveImage();

  /**
   * The method handles the request to change in sharpness (both BLUR & SHARPEN) op on the img.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   *
   * @param kernelMap Integer representing the map value for BLUR and SHARPEN operation using enums.
   */
  void changeSharpness(int kernelMap);

  /**
   * The method handles the request to apply greyscale operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  void applyGreyScale();

  /**
   * The method handles the request to apply sepia operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  void applySepia();

  /**
   * The method handles the request to apply color correction operation on the img, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  void applyColorCorrection();

  /**
   * The method handles the request to apply level adjust operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  void levelAdjustImage();

  /**
   * The method handles the request to apply flip (HORIZONTAL & VERTICAL) op on the img.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   *
   * @param axisNameMap Integer representing the map value for HORIZONTAL & VERTICAL op using enums.
   */
  void flipImage(int axisNameMap);

  /**
   * The method handles the request to get Color-greyscale (RED,GREEN & BLUE) op on the img.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   *
   * @param colorType Integer representing the map value for RED,GREEN & BLUE operation using enums.
   */
  void getSingleComponentImage(int colorType);

  /**
   * The method handles the request to apply compression operation on the image, request by user.
   * The controller commands the model to get a new image with the passed operation.
   * It then orders the view to display the new image as requested by the user for the application.
   */
  void compressImage();

  /**
   * The method sets up the live image by putting a command call to view for the display of the img.
   * The image on display can either be rolled back to the prev version or it can be updated to new.
   * The controller puts the display based on the request user makes through diff UI components.
   *
   * @param updateType Integer value representing the map value for OLD & NEW operation using enums.
   */
  void setLiveImage(int updateType);

  /**
   * The method gets any exception if occurred while view is interacting with the external env.
   * The exception generated is sent back to the controller & it decides what it has to do for it.
   *
   * @param ex The exception object generated from any unexpected event that occurred from ext env.
   */
  void getExceptionFromExternalEnv(Exception ex);

}
