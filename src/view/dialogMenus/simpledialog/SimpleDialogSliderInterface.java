package view.dialogMenus.simpledialog;

/**
 * The interface represents the functionalities offered by a simple dialog graphical user interface.
 * The interface has different methods to get different parameter values from the parent class.
 * It helps in creating a JDialog class component to get additional input like the JSlider value.
 * The object of it's class implementing this is created when ordered by the controller for the ip.
 * The controller attaches the present JDialog box to the main view class before creating it.
 */
public interface SimpleDialogSliderInterface {

  /**
   * The method gets the slider value when this JDialog box pops up to the user in the app.
   * The slider percentage here is needed by controller to enable the split preview functionality.
   * All the functionalities are performed keeping the main view intact and displayed.
   *  @return Integer value representing the selected slider value from the external environment.
   */
  int getSliderPercentage();

  /**
   * This helps to know the controller whether the user from the GUI has finalized the slider value.
   * Whenever this method return true it signifies that the user has selected the value it wanted.
   * Until the user selects the apply operation till then the method returns false.
   * @return Boolean value representing whether user has decided the slider value.
   */
  boolean getResultOperationFlag();

  /**
   * The method is used to just dispose the memory consumed by the JDialog box during its display.
   * After the slider value get finalized the controlled orders the JDialog to close itself.
   * This helps to release the resources consumed during the operation which could be use further.
   */
  void dispose();
}
