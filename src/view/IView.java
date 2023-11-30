package view;

import java.awt.Image;
import java.util.List;

import controller.features.Features;
import view.dialogmenus.multiipdialog.MultiInputSliderDialogInterface;

/**
 * This interface represents the functionalities offered by a view.
 * A view can be given commands from a controller using the given methods.
 * A view can display the UI, update the images, show pop-ups for error and standard information.
 * Also, if given command can open different dialog boxes to get input from the user as required.
 */
public interface IView {

  /**
   * The method is responsible for populating the User Interface in order to show it to user.
   * It can load all the buttons, panels and images with the required listeners.
   * The display generated  is an interactive one where different components react to any events.
   */
  void setDisplay();

  /**
   * The method is used to show up a pop-up message to the user with the required information.
   * This is called from a controller in case the controller wants the user to know about something.
   *
   * @param message String having information that the user is made aware of for the event.
   */
  void setPopupMessage(String message);

  /**
   * The method is used to show up an error message to the user with the required information.
   * This is called from a controller when an unexpected event arises in the application.
   *
   * @param message String having error message that the user is made aware of for the event.
   */
  void setErrorMessage(String message);

  /**
   * This is used to populate the main image in the user interface on which the operation is done.
   * The method takes the image as an input and attach it to the current display panel seen by user.
   *
   * @param image Image object information about the image which needs to be shown in the UI.
   */
  void displayImage(Image image);

  /**
   * This is used to populate the histogram of the current image in display.
   * Using this the histogram is shown in the user interface on which the operation is done.
   * The method takes the histogram of the image as an input.
   * It then attaches it to the current display panel seen by user.
   *
   * @param image Image object information about the histogram which needs to be shown in the UI.
   */
  void displayHistogram(Image image);

  /**
   * This sets the features object which help to bridge communication between view and controller.
   * Using the features the view send the data to controller which follows a high level-abstraction.
   *
   * @param features An object which stores different requests that view can make to the controller.
   */
  void setFeatures(Features features);

  /**
   * These methods helps to get the input file path of the image when an image is loaded.
   * The function takes the file path from the external environment when ordered by controller.
   *
   * @return A String containing the information about the input file path where image is stored.
   */
  String getInputFilePath();

  /**
   * These methods helps to get the output file path of the image where the image needs to be saved.
   * The function takes the file path from the external environment when ordered by controller.
   *
   * @return A String containing the information about the input file path where image is stored.
   */
  String getOutputFilePath();

  /**
   * The method is used to show a dialog box for getting additional input needed by the controller.
   * Whenever required the controller commands the view to open the dialog box to get the input.
   * The dialog opened using this method is a simple box which takes only the info about the split.
   *
   * @param operationTitle The title of the opened dialog box which is displayed to the user.
   * @param labelText      The Text for the single slider present in the dialog box.
   * @return Integer object representing the selected numerical value from the slider by the user.
   */
  Integer displayDialogSingleSplitPreview(String operationTitle, String labelText);

  /**
   * The method is used to show a dialog box for getting additional input needed by the controller.
   * Whenever required the controller commands the view to open the dialog box to get the input.
   * The dialog box opened using this call takes multiple information from the JDialog box.
   * The method could take various inputs like multiple sliders value and other selected options.
   *
   * @param levelAdjustDialog Interface containing the information about the multiple data that
   *                          needs to be fetched from the user during the display of the pop-up.
   * @return List containing the data in sequential manner that were required by the callee method.
   */
  List<Integer> displayDialogMultiINPreview(MultiInputSliderDialogInterface levelAdjustDialog);

}
