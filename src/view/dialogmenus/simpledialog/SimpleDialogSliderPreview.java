package view.dialogmenus.simpledialog;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * This class represents a JDialog box which takes only the slider input val;ue from the user.
 * Whenever additional information like about split preview is required the controller creates it.
 * The class has a label which shows the current selected split value offered to the user.
 * As soon as the operation is done the class can dispose itself to free out the resources it had.
 */

public class SimpleDialogSliderPreview extends JDialog implements SimpleDialogSliderInterface {

  private JLabel textLabel;

  private JSlider splitPreviewSlider;

  private int sliderPercentage;

  private final String labelText;

  private boolean result;

  /**
   * The constructor helps to create and initialize the variable values required for the JDialog.
   * It attaches itself first as a child of the main view class and initializes the display frame.
   * It adds listeners to the components and the attached panels before displaying it to the user.
   *
   * @param parentFrame Parent JFrame to which the current JDialog box is attached to for display.
   * @param title       The title of the JDialog box which contains the heading of
   *                    this GUI component.
   * @param labelText   The text to be displayed for the listener of the slider in the present GUI.
   */
  public SimpleDialogSliderPreview(JFrame parentFrame, String title, String labelText) {
    super(parentFrame, title, true);
    this.result = false;
    this.labelText = labelText;

    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30, 15));
    JScrollPane displayScrollPane = new JScrollPane(displayPanel);
    this.add(displayScrollPane);

    JPanel sliderPanel = getSliderPanel();
    JPanel applyButton = getApplyButton();
    displayPanel.add(sliderPanel, BorderLayout.CENTER);
    displayPanel.add(applyButton, BorderLayout.PAGE_END);

    this.setSize(new Dimension(500, 200));
    this.setVisible(true);
  }

  private JPanel getSliderPanel() {
    JPanel sliderPanel = new JPanel();
    sliderPanel.setLayout(new BorderLayout(10, 5));
    this.splitPreviewSlider = getJSlider();
    this.textLabel = new JLabel();
    setLabelText(splitPreviewSlider.getValue());
    sliderPanel.add(splitPreviewSlider, BorderLayout.CENTER);
    sliderPanel.add(textLabel, BorderLayout.PAGE_END);
    return sliderPanel;
  }

  private void setLabelText(int value) {
    this.textLabel.setText(this.labelText + ": " + value);
  }

  private JSlider getJSlider() {
    JSlider slider = new JSlider();
    slider.setMinimum(0);
    slider.setMaximum(100);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.addChangeListener(evt -> {
      setLabelText(this.splitPreviewSlider.getValue());
    });
    return slider;
  }

  private JPanel getApplyButton() {
    JPanel buttonPanel = new JPanel();
    JButton applyButton = new JButton("Apply Operation");
    applyButton.addActionListener(evt -> {
      setSliderPercentage();
      this.result = true;
      this.setVisible(false);
    });
    buttonPanel.add(applyButton);
    return buttonPanel;
  }

  private void setSliderPercentage() {
    this.sliderPercentage = this.splitPreviewSlider.getValue();
  }

  /**
   * The method gets the slider value when this JDialog box pops up to the user in the app.
   * The slider percentage here is needed by controller to enable the split preview functionality.
   * All the functionalities are performed keeping the main view intact and displayed.
   *
   * @return Integer value representing the selected slider value from the external environment.
   */
  @Override
  public int getSliderPercentage() {
    return sliderPercentage;
  }

  /**
   * This helps to know the controller whether the user from the GUI has finalized the slider value.
   * Whenever this method return true it signifies that the user has selected the value it wanted.
   * Until the user selects the apply operation till then the method returns false.
   *
   * @return Boolean value representing whether user has decided the slider value.
   */
  @Override
  public boolean getResultOperationFlag() {
    return this.result;
  }

}
