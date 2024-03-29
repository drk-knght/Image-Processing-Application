package view;

import java.awt.Image;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.features.Features;
import enums.AxisName;
import enums.ColorMapping;
import enums.KernelImage;
import view.dialogmenus.multiipdialog.MultiInputSliderDialogInterface;
import view.dialogmenus.simpledialog.SimpleDialogSliderInterface;
import view.dialogmenus.simpledialog.SimpleDialogSliderPreview;

/**
 * This class represents the Graphical User Interface of the MVC architecture.
 * This class is a child of JFrame and the central class which show all the UI to the user.
 * Whenever invoked the class sets up all the swing components in order to display a valid response.
 * The class also requests information to the controller and perform actions ordered by controller.
 */
public class GraphicalView extends JFrame implements IView {

  private JPanel lineStartPanel;

  private JPanel centerPanel;

  private JPanel mainPanel;

  private JPanel histogramPanel;

  private Features features;

  private final Map<String, ActionListener> buttonActions;

  private final String[] ioOperations = {"Load Image", "Exit App", "Save Image"};

  private final String[] imgOp = {"Visualize Red Component", "Visualize Green Component",
      "Visualize Blue Component", "Flip Horizontal", "Flip Vertical",
      "Blur", "Sharpen", "Greyscale", "Sepia", "Compression",
      "Color Correction", "Level Adjustment"};

  private final String[] changeImage = {"Save Operation", "Cancel Operation"};

  private void setButtonActions() {

    addIOButtonListeners();

    addNonPreviewListeners();

    modifyImageListeners();

    addPreviewListeners();
    buttonActions.put("Compression", evt -> this.features.compressImage());
  }

  private void addPreviewListeners() {
    addSingleSplitPreview();
    addMultiInSplitPreview();
  }

  private void addSingleSplitPreview() {
    buttonActions.put("Blur", evt ->
            this.features.changeSharpness(KernelImage.Blur.ordinal()));
    buttonActions.put("Sharpen", evt ->
            this.features.changeSharpness(KernelImage.Sharpen.ordinal()));
    buttonActions.put("Sepia", evt -> this.features.sepia());
    buttonActions.put("Color Correction", evt -> this.features.colorCorrection());
  }

  private void addMultiInSplitPreview() {
    buttonActions.put("Level Adjustment", evt -> this.features.levelAdjustment());
    buttonActions.put("Greyscale", evt -> this.features.greyScale());
  }

  private void addNonPreviewListeners() {
    buttonActions.put("Visualize Red Component", evt ->
            this.features.getSingleComponent(ColorMapping.red.ordinal()));
    buttonActions.put("Visualize Green Component", evt ->
            this.features.getSingleComponent(ColorMapping.green.ordinal()));
    buttonActions.put("Visualize Blue Component", evt ->
            this.features.getSingleComponent(ColorMapping.blue.ordinal()));
    buttonActions.put("Flip Horizontal", evt ->
            this.features.flip(AxisName.horizontal.ordinal()));
    buttonActions.put("Flip Vertical", evt -> this.features.flip(AxisName.vertical.ordinal()));
  }

  private void addIOButtonListeners() {
    buttonActions.put("Load Image", evt -> this.features.load());
    buttonActions.put("Save Image", evt -> this.features.save());
    buttonActions.put("Exit App", evt -> System.exit(0));
  }

  private void modifyImageListeners() {
    buttonActions.put("Save Operation", evt -> this.features.applyOperation());
    buttonActions.put("Cancel Operation", evt -> this.features.cancelOperation());
  }

  /**
   * The method is used to show a dialog box for getting additional input needed by the controller.
   * Whenever required the controller commands the view to open the dialog box to get the input.
   * The dialog opened using this method is a simple box which takes only the info about the split.
   *
   * @param operationTitle The title of the opened dialog box which is displayed to the user.
   * @param labelText      The Text for the single slider present in the dialog box.
   * @return Integer object representing the selected numerical value from the slider by the user.
   */
  @Override
  public Integer displayDialogSingleSplitPreview(String operationTitle, String labelText) {
    SimpleDialogSliderInterface simpleJDialog = new SimpleDialogSliderPreview(this,
            operationTitle, labelText);
    Integer splitPercentage = null;

    if (simpleJDialog.getResultOperationFlag()) {
      splitPercentage = simpleJDialog.getSliderPercentage();
    }
    simpleJDialog.dispose();
    return splitPercentage;
  }

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
  @Override
  public List<Integer> displayDialogMultiINPreview(
          MultiInputSliderDialogInterface levelAdjustDialog) {
    List<Integer> resultList = null;
    if (levelAdjustDialog.getResultOperationFlag()) {
      resultList = levelAdjustDialog.getListOfInputValues();
    }
    levelAdjustDialog.dispose();
    return resultList;
  }

  /**
   * These methods helps to get the input file path of the image when an image is loaded.
   * The function takes the file path from the external environment when ordered by controller.
   *
   * @return A String containing the information about the input file path where image is stored.
   */
  @Override
  public String getInputFilePath() {
    JFileChooser selectFile = new JFileChooser(".");
    FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("PPM, JPG,"
            + " JPEG, PNG Images",
            "jpg", "jpeg", "png", "ppm");
    selectFile.setFileFilter(fileExtensions);
    int retrievalResult = selectFile.showOpenDialog(GraphicalView.this);
    if (retrievalResult == JFileChooser.APPROVE_OPTION) {
      File f = selectFile.getSelectedFile();
      return f.getPath();
    }
    return null;
  }

  /**
   * These methods helps to get the output file path of the image where the image needs to be saved.
   * The function takes the file path from the external environment when ordered by controller.
   *
   * @return A String containing the information about the input file path where image is stored.
   */
  @Override
  public String getOutputFilePath() {
    JFileChooser selectFile = new JFileChooser(".");
    FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("PPM, JPG, "
            + "JPEG, PNG Images",
            "jpg", "jpeg", "png", "ppm");
    selectFile.setFileFilter(fileExtensions);
    int retrievalResult = selectFile.showSaveDialog(GraphicalView.this);
    if (retrievalResult == JFileChooser.APPROVE_OPTION) {
      File f = selectFile.getSelectedFile();
      return f.getPath();
    }
    return null;
  }

  /**
   * The constructor is responsible for setting up the environment variables for the view.
   * As this is a GUI, the constructor initializes all the swing components with valid objects.
   * It sets ups the title of the JFrame which contains all the image processing application UI.
   * The constructor also add the action listeners for any events on the buttons before
   * showing the UI to the user.
   */
  public GraphicalView() {
    super();
    setTitle("Image Processing Application");
    this.setSize(new Dimension(700, 500));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.buttonActions = new HashMap<>();
    setButtonActions();
  }

  /**
   * The method is responsible for populating the User Interface in order to show it to user.
   * It can load all the buttons, panels and images with the required listeners.
   * The display generated  is an interactive one where different components react to any events.
   */
  @Override
  public void setDisplay() {
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BorderLayout(30, 15));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);
    Image image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    this.lineStartPanel = getLineStartPanel(image);
    this.centerPanel = getLiveImagePanel(image, "Image Preview", 1000, 800);
    JPanel pageEndPanel = getPageEndPanel();
    this.mainPanel.add(lineStartPanel, BorderLayout.LINE_START);
    this.mainPanel.add(centerPanel, BorderLayout.CENTER);
    this.mainPanel.add(pageEndPanel, BorderLayout.PAGE_END);
    setVisibility(true, this);
  }

  /**
   * The method is used to show up a pop-up message to the user with the required information.
   * This is called from a controller in case the controller wants the user to know about something.
   *
   * @param message String having information that the user is made aware of for the event.
   */
  @Override
  public void setPopupMessage(String message) {
    JOptionPane.showMessageDialog(this, message,
            "Popup Message", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * The method is used to show up an error message to the user with the required information.
   * This is called from a controller when an unexpected event arises in the application.
   *
   * @param message String having error message that the user is made aware of for the event.
   */
  @Override
  public void setErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message,
            "Error Message", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This is used to populate the main image in the user interface on which the operation is done.
   * The method takes the image as an input and attach it to the current display panel seen by user.
   *
   * @param image Image object information about the image which needs to be shown in the UI.
   */
  @Override
  public void displayImage(Image image) {
    setVisibility(false, this.centerPanel);
    this.centerPanel = getLiveImagePanel(image, "Image Preview", 800, 800);
    this.mainPanel.add(this.centerPanel, BorderLayout.CENTER);
    setVisibility(true, this);
  }

  /**
   * This is used to populate the histogram of the current image in display.
   * Using this the histogram is shown in the user interface on which the operation is done.
   * The method takes the histogram of the image as an input.
   * It then attaches it to the current display panel seen by user.
   *
   * @param image Image object information about the histogram which needs to be shown in the UI.
   */
  @Override
  public void displayHistogram(Image image) {
    setVisibility(false, this.histogramPanel);
    this.histogramPanel = getLiveImagePanel(image, "Histogram of the image", 256, 256);
    lineStartPanel.add(this.histogramPanel, BorderLayout.PAGE_END);
    setVisibility(true, this);
  }

  /**
   * This sets the features object which help to bridge communication between view and controller.
   * Using the features the view send the data to controller which follows a high level-abstraction.
   *
   * @param features An object which stores different requests that view can make to the controller.
   */
  @Override
  public void setFeatures(Features features) {
    this.features = features;
  }

  private JPanel getLiveImagePanel(Image image, String title, int x, int y) {
    JPanel imagePanel = new JPanel();
    TitledBorder imageBorder = BorderFactory.createTitledBorder(title);
    imageBorder.setTitleJustification(TitledBorder.CENTER);
    imagePanel.setBorder(imageBorder);
    JLabel imageLabel = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(image));
    imageScrollPane.setPreferredSize(new Dimension(x, y));
    imagePanel.add(imageScrollPane);
    return imagePanel;
  }

  private JPanel getLineStartPanel(Image imageData) {
    JPanel lineStart = new JPanel();
    lineStart.setLayout(new BorderLayout(5, 20));
    JPanel ioPanel = setPanel(ioOperations, "I/O Operations",
            new FlowLayout(FlowLayout.CENTER, 10, 10));
    JPanel opPanel = setPanel(imgOp, "Image Operations", new GridLayout(imgOp.length, 1));
    this.histogramPanel = getLiveImagePanel(imageData, "Histogram of the image", 256, 256);
    lineStart.add(ioPanel, BorderLayout.PAGE_START);
    lineStart.add(opPanel, BorderLayout.CENTER);
    lineStart.add(histogramPanel, BorderLayout.PAGE_END);
    return lineStart;
  }

  private JPanel setPanel(String[] buttonList, String panelTitle, LayoutManager manager) {
    JPanel resPanel = new JPanel();
    resPanel.setLayout(manager);
    addButtons(buttonList, resPanel);
    TitledBorder panelBorder = BorderFactory.createTitledBorder(panelTitle);
    panelBorder.setTitleJustification(TitledBorder.CENTER);
    resPanel.setBorder(panelBorder);
    return resPanel;
  }

  private JPanel getPageEndPanel() {
    JPanel lowerPanel = new JPanel();
    lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    addButtons(changeImage, lowerPanel);
    return lowerPanel;
  }

  private void addButtons(String[] buttonList, JPanel panel) {
    for (String s : buttonList) {
      JButton button = new JButton(s);
      button.setActionCommand(s);
      ActionListener event = this.buttonActions.getOrDefault(button.getActionCommand(), null);
      if (event != null) {
        button.addActionListener(event);
      }
      panel.add(button);
    }
  }

  private void setVisibility(boolean isVisible, Component component) {
    component.setVisible(isVisible);
  }

}
