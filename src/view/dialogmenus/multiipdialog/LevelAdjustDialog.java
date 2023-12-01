package view.dialogmenus.multiipdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * This class represent a JDialog box to get the input data from the user for Level Adjustment.
 * The class has several sliders to get the values of black, mid, highlight point.
 * It also supports other functionalities like split preview sliders for user to see preview of op.
 * Each of the components present in the dialog have valid title which represents their use case.
 */
public class LevelAdjustDialog extends AbstractMultiInputDialog {

  private JLabel sliderSplitTextLabel;

  private JLabel blackPointLabel;

  private JLabel midPointLabel;

  private JLabel highlightPointLabel;

  private JSlider splitPreviewSlider;

  private JSlider blackPointSlider;

  private JSlider midPointSlider;

  private JSlider highlightPointSlider;

  /**
   * The constructor takes the parent frame for this present dialog and title as parameters.
   * It attached itself to the parent JFrame and sets the title of the present JDialog for the GUI.
   * The constructor initializes all its components for the GUI which includes labels and sliders.
   *
   * @param parentFrame JFrame to which the present JDialog box is attached to for its existence.
   * @param title       The title of the Dialog box which makes the purpose of it clear to the user.
   */
  public LevelAdjustDialog(JFrame parentFrame, String title) {
    super(parentFrame, title);
    initializeSliders();
    initializeLabels();

    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30, 15));
    JScrollPane displayScrollPane = new JScrollPane(displayPanel);
    this.add(displayScrollPane);

    JPanel sliderPanel = getCombinedSlidersPanel();
    JPanel applyButton = getApplyButton();
    addPanelsToDisplay(displayPanel, sliderPanel, applyButton);

    this.setSize(new Dimension(900, 500));
    this.setVisible(true);
  }

  private void addPanelsToDisplay(JPanel displayPanel, JPanel sliderPanel, JPanel applyButton) {
    displayPanel.add(sliderPanel, BorderLayout.CENTER);
    displayPanel.add(applyButton, BorderLayout.PAGE_END);
  }

  private void initializeSliders() {
    this.blackPointSlider = new JSlider();
    this.midPointSlider = new JSlider();
    this.highlightPointSlider = new JSlider();
    this.splitPreviewSlider = new JSlider();
  }

  private void initializeLabels() {
    this.blackPointLabel = new JLabel();
    setLabelText(this.blackPointLabel, this.blackPointSlider);

    this.midPointLabel = new JLabel();
    setLabelText(this.midPointLabel, this.midPointSlider);

    this.highlightPointLabel = new JLabel();
    setLabelText(this.highlightPointLabel, this.highlightPointSlider);

    this.sliderSplitTextLabel = new JLabel();
    setLabelText(this.sliderSplitTextLabel, this.splitPreviewSlider);
  }

  private JPanel getCombinedSlidersPanel() {
    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new GridLayout(4, 1, 1, 40));
    JPanel blackPanel = getSingleJSliderPanel("Black Point",
            255, blackPointSlider, blackPointLabel);
    JPanel midPanel = getSingleJSliderPanel("Mid Point",
            255, midPointSlider, midPointLabel);
    JPanel highlightPanel = getSingleJSliderPanel("Highlight Point",
            255, highlightPointSlider, highlightPointLabel);
    JPanel splitPanel = getSingleJSliderPanel("Split Preview %",
            100, splitPreviewSlider, sliderSplitTextLabel);
    displayPanel.add(blackPanel, 0);
    displayPanel.add(midPanel, 1);
    displayPanel.add(highlightPanel, 2);
    displayPanel.add(splitPanel, 3);
    return displayPanel;
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
    actionChangeLabel.setText("The number selected is: " + slider.getValue());
  }

  /**
   * The method is responsible for putting all the values received from the GUI in a list.
   * When the user finalizes all the values present in the present JDialog frame,
   * this gets all the data from the GUI put it in a list and return it back to the controller.
   * The data from different components are fetched and then store it as required for parsing.
   *
   * @return It returns a list of int of different info about the components that required data.
   */
  @Override
  public List<Integer> getListOfInputValues() {
    List<Integer> sliderInputValues = new ArrayList<>();
    sliderInputValues.add(splitPreviewSlider.getValue());
    sliderInputValues.add(blackPointSlider.getValue());
    sliderInputValues.add(midPointSlider.getValue());
    sliderInputValues.add(highlightPointSlider.getValue());
    return sliderInputValues;
  }
}
