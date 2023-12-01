package view.dialogmenus.multiipdialog;

import java.util.List;

/**
 * The interface represents the functionalities offered by a multi dialog graphical user interface.
 * The interface has different methods to get different parameter values from the parent class.
 * It helps in creating a JDialog class component to get additional input like the JSlider value.
 * The object of it's class implementing this is created when ordered by the controller for the ip.
 * The controller attaches the present JDialog box to the main view class before creating it.
 */
public interface MultiInputSliderDialogInterface {

  /**
   * This helps to know controller whether the user from the GUI has finalized the slider values.
   * Whenever this method return true it signifies that the user has selected the value it wanted.
   * Until the user selects the apply operation till then the method returns false.
   * @return Boolean value representing whether user has decided about all the slider values.
   */
  boolean getResultOperationFlag();

  /**
   * The method is used to just dispose the memory consumed by the JDialog box during its display.
   * After the slider value get finalized the controlled orders the JDialog to close itself.
   * This helps to release the resources consumed during the operation which could be use further.
   */
  void dispose();

  /**
   * The method is responsible for putting all the values received from the GUI in a list.
   * When the user finalizes all the values present in the present JDialog frame,
   * this gets all the data from the GUI put it in a list and return it back to the controller.
   * The data from different components are fetched and then store it as required for parsing.
   * @return It returns a list of int of different info about the components that required data.
   */
  List<Integer> getListOfInputValues();
}
