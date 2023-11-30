package view.dialogMenus.multiipdialog;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * The abstract class represents a dialog box supporting receiving multiple inputs from the UI.
 * The class has a boolean value which is set to true when the user has finalized all the values.
 * The class sets up the JDialog box, attaches itself to parent frame and sets up the title of it.
 * As it takes multiple inputs from the user it returns back a list of input data values to user.
 */
public abstract class AbstractMultiInputDialog extends JDialog
        implements MultiInputSliderDialogInterface {

  protected boolean result;

  /**
   * The constructor takes the parent JFrame as an input and attaches itself to the parent.
   * It sets up the boolean flag to false which basically signifies that the inputs are not final.
   * It sets up the title of the JDialog box which represents the purpose of its creation to user.
   *
   * @param parentFrame JFrame representing the parent frame to which the dialog box is attached.
   * @param title       The title of the JDialog box which represents the purpose of its creation.
   */
  public AbstractMultiInputDialog(JFrame parentFrame, String title) {
    super(parentFrame, title, true);
    this.result = false;
  }

  /**
   * This method creates an "apply" button which is pressed by the user when the ip are finalized.
   * The button is created and is added to a panel with the required name and listeners.
   * The boolean flag is set to true in the listener when the button is clicked.
   *
   * @return JPanel containing the button with all the mentioned properties.
   */
  protected JPanel getApplyButton() {
    JPanel buttonPanel = new JPanel();
    JButton applyButton = new JButton("Apply Operation");
    applyButton.addActionListener(evt -> {
      this.result = true;
      this.setVisible(false);
    });
    buttonPanel.add(applyButton);
    return buttonPanel;
  }

  /**
   * The method creates a panel having JSlider and its label in it/
   * The panel created has a border layout with the slider in the center and label in the page end.
   * The panel is attached with a heading label which represents the purpose of using this slider.
   *
   * @param headingLabel      Representing the heading of the slider and info gathered through this.
   * @param max               As the controller restricts the max value permissible in the slider, it sets that.
   * @param presentSlider     The Slider which needs to be added to the panel created here.
   * @param actionChangeLabel The label which shows the presently selected value in the slider.
   * @return The newly created panel having slider, its label and the title of it for the purpose.
   */
  protected JPanel getSingleJSliderPanel(String headingLabel,
                                         int max, JSlider presentSlider, JLabel actionChangeLabel) {
    JPanel panelInUse = new JPanel();
    panelInUse.setLayout(new BorderLayout());
    setTitleBorder(panelInUse, headingLabel);
    setSliderProperties(presentSlider, max, actionChangeLabel);
    panelInUse.add(presentSlider, BorderLayout.CENTER);
    panelInUse.add(actionChangeLabel, BorderLayout.PAGE_END);
    return panelInUse;
  }

  /**
   * The methods set the title border with the heading label for the panel passed as a parameter.
   * It takes the panel creates a Border Factory layout structure.
   * Titled border then attach the label in the center which describes the use case of the panel.
   *
   * @param panel        The panel on which the border needs to be applied for the presentation.
   * @param headingLabel The label which represents the purpose of the panel.
   */
  protected void setTitleBorder(JPanel panel, String headingLabel) {
    TitledBorder sliderBorder = BorderFactory.createTitledBorder(headingLabel);
    sliderBorder.setTitleJustification(TitledBorder.CENTER);
    panel.setBorder(sliderBorder);
  }

  private void setSliderProperties(JSlider slider, int max, JLabel actionChangeLabel) {
    slider.setMinimum(0);
    slider.setMaximum(max);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(5);
    slider.addChangeListener(evt -> {
      setLabelText(actionChangeLabel, slider);
    });
  }

  /**
   * The method takes the slider and attach a particular label to the slider.
   * Whenever the slider's listener is invoked an operation is done using the label to display.
   * The event listener for the present use case displays a message along
   * with the present value selected by the user using the present slider.
   *
   * @param actionChangeLabel Label containing the message that is displayed in the GUI of dialog.
   * @param slider            The slider which is shown to get additional information from
   *                          the user by controller.
   */
  abstract protected void setLabelText(JLabel actionChangeLabel, JSlider slider);

  /**
   * This helps to know controller whether the user from the GUI has finalized the slider values.
   * Whenever this method return true it signifies that the user has selected the value it wanted.
   * Until the user selects the apply operation till then the method returns false.
   *
   * @return Boolean value representing whether user has decided about all the slider values.
   */
  @Override
  public boolean getResultOperationFlag() {
    return this.result;
  }


}
