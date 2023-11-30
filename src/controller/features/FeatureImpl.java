package controller.features;


import controller.graphicalcontroller.GraphicalControllerInterface;
import enums.UpdateType;

/**
 * This class represents a set of features exposed by the controller to the view for the events.
 * These are high level requests made by view to the controller about the events occurring in UI.
 * It offers abstraction and hides the inner implementation of both view and controller.
 * The view don't have direct access to controller, so it cannot mutate the controller's object.
 */
public class FeatureImpl implements Features {

  private final GraphicalControllerInterface controller;

  /**
   * The constructor of the feature class takes the controller object and initializes its reference.
   * The constructor helps to favor composition over inheritance which prevents critical mutations.
   * The methods in the rest of the methods are just the call back to the main controller class.
   *
   * @param controller Object of the controller interface which has the response for the actions.
   */
  public FeatureImpl(GraphicalControllerInterface controller) {
    this.controller = controller;
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a greyscale op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void greyScale() {
    controller.applyGreyScale();
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a flip op.
   * The controller then performs the necessary actions to support the request made through UI.
   *
   * @param axisFlip Integer representing the axis along which the flip needs to occur.
   */
  @Override
  public void flip(int axisFlip) {
    controller.flipImage(axisFlip);
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When it is called the controller knows that the user wants to perform a color correction op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void colorCorrection() {
    controller.applyColorCorrection();
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When it is called the controller knows that the user wants to perform a sharpness change op.
   * The controller then performs the necessary actions to support the request made through UI.
   *
   * @param kernelMap Integer representing the map value for BLUR and SHARPEN operation using enums.
   */
  @Override
  public void changeSharpness(int kernelMap) {
    controller.changeSharpness(kernelMap);
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a sepia op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void sepia() {
    controller.applySepia();
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller knows that the user wants to perform a level adjust op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void levelAdjustment() {
    controller.levelAdjustImage();
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller knows that the user wants to perform a color-greyscale op.
   * The controller then performs the necessary actions to support the request made through UI.
   *
   * @param colorType Integer representing the map value of which color greyscale image user wants.
   */
  @Override
  public void getSingleComponent(int colorType) {
    controller.getSingleComponentImage(colorType);
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a compress op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void compressImage() {
    controller.compressImage();
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a load op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void load() {
    controller.loadImage();
  }


  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a save op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void save() {
    controller.saveImage();
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform a save img op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void applyOperation() {
    controller.setLiveImage(UpdateType.NEW.ordinal());
  }

  /**
   * This call back method to the controller which gives information about the user induced event.
   * When this is called the controller gets to know that the user wants to perform an undo op.
   * The controller then performs the necessary actions to support the request made through UI.
   */
  @Override
  public void cancelOperation() {
    controller.setLiveImage(UpdateType.OLD.ordinal());
  }
}
