package view.dialogMenus.multiipdialog;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;

import enums.GreyScaleType;

/**
 * The class represent a JDialog box to get the input data from the user for Greyscale operations.
 * The class has a single slider for the split preview option along with multiple radio buttons.
 * The radio buttons offers different types of greyscale that can be offered to the UI for an img.
 * Each of the components present in the dialog have valid title which represents their use case.
 */
public class GreyScaleDialog extends AbstractMultiInputDialog {

  private JRadioButton lumaButton;

  private JRadioButton valueButton;

  private JRadioButton intensityButton;

  private JLabel sliderLabel;

  private JSlider splitViewSlider;

  private int greyScaleValue;

  /**
   * The constructor takes the parent frame for this present dialog and title as parameters.
   * It attached itself to the parent JFrame and sets the title of the present JDialog for the GUI.
   * The constructor initializes all its components for the GUI like labels,radio buttons & sliders.
   *
   * @param parentFrame JFrame to which the present JDialog box is attached to for its existence.
   * @param title       The title of the Dialog box which makes the purpose of it clear to the user.
   */
  public GreyScaleDialog(JFrame parentFrame, String title) {
    super(parentFrame, title);
    initializeAndSetRadioButtons();
    initializeSliderFields();

    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30, 15));
    addScrollPaneToFrame(displayPanel);

    JPanel sliderPanel = getSingleJSliderPanel("Select Split Percentage",
            100, splitViewSlider, sliderLabel);
    JPanel greyscalePanel = getGreyScaleMenu();
    JPanel applyButtonPanel = getApplyButton();

    addPanelsToDisplay(displayPanel, greyscalePanel, sliderPanel, applyButtonPanel);
    this.pack();
    this.setVisible(true);
  }

  private void addScrollPaneToFrame(JPanel displayPanel) {
    JScrollPane displayScrollPane = new JScrollPane(displayPanel);
    this.add(displayScrollPane);
  }

  private void addPanelsToDisplay(JPanel displayPanel, JPanel greyscalePanel,
                                  JPanel sliderPanel, JPanel applyButtonPanel) {
    displayPanel.add(greyscalePanel, BorderLayout.LINE_START);
    displayPanel.add(sliderPanel, BorderLayout.CENTER);
    displayPanel.add(applyButtonPanel, BorderLayout.PAGE_END);
  }

  private void initializeAndSetRadioButtons() {
    this.lumaButton = new JRadioButton("Luma Operation");
    this.lumaButton.setSelected(true);
    this.lumaButton.addActionListener(evt -> this.greyScaleValue = GreyScaleType.luma.ordinal());

    this.valueButton = new JRadioButton("Value Operation");
    this.valueButton.addActionListener(evt ->
            this.greyScaleValue = GreyScaleType.value.ordinal());

    this.intensityButton = new JRadioButton("Intensity Operation");
    this.intensityButton.addActionListener(evt ->
            this.greyScaleValue = GreyScaleType.intensity.ordinal());

    ButtonGroup greyscaleGroup = new ButtonGroup();
    greyscaleGroup.add(this.lumaButton);
    greyscaleGroup.add(this.valueButton);
    greyscaleGroup.add(this.intensityButton);
  }

  private void initializeSliderFields() {
    this.sliderLabel = new JLabel();
    this.splitViewSlider = new JSlider();
    setLabelText(this.sliderLabel, this.splitViewSlider);
  }

  private JPanel getGreyScaleMenu() {
    JPanel greyscaleMenu = new JPanel();
    greyscaleMenu.setLayout(new GridLayout(3, 1, 5, 15));
    greyscaleMenu.add(this.lumaButton, 0);
    greyscaleMenu.add(this.valueButton, 1);
    greyscaleMenu.add(this.intensityButton, 2);
    setTitleBorder(greyscaleMenu, "Grey Scale Types");
    return greyscaleMenu;
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
  @Override
  protected void setLabelText(JLabel actionChangeLabel, JSlider slider) {
    actionChangeLabel.setText("The preview % of image on which greyscale operation is visible: "
            + slider.getValue());
  }

  /**
   * The method is responsible for putting all the values received from the GUI in a list.
   * When the user finalizes all the values present in the present JDialog frame,
   * this gets all the data from the GUI put it in a list and return it back to the controller.
   * The data from different components are fetched and then store it as required for parsing.
   *
   * @return It returns a list of integer values containing different information
   * about the additional components that required data.
   */
  @Override
  public List<Integer> getListOfInputValues() {
    List<Integer> sliderInputValues = new ArrayList<>();
    sliderInputValues.add(this.splitViewSlider.getValue());
    sliderInputValues.add(this.greyScaleValue);
    return sliderInputValues;
  }
}
