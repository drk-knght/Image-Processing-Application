package view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.graphicalcontroller.Features;
import controller.graphicalcontroller.GraphicalController;
import controller.graphicalcontroller.GraphicalControllerInterface;
import enums.AxisName;
import enums.ColorMapping;
import enums.KernelImage;
import enums.LevelAdjustment;

public class GraphicalView extends JFrame implements IView {

  private JPanel lineStartPanel;

  private  JPanel centerPanel;

  private JPanel pageEndPanel;

  private JPanel mainPanel;

  private JPanel histogramPanel;

  private Features features;

  private final Map<String, ActionListener>buttonActions;

  private final String [] ioOperations={"Load Image","Exit App","Save Image"};

  private final String [] imgOp= {"Visualize Red Component", "Visualize Green Component",
          "Visualize Blue Component", "Flip Horizontal", "Flip Vertical",
          "Blur", "Sharpen","Greyscale","Sepia","Compression",
          "Color Correction","Level Adjustment"};

  private final String [] changeImage={"Apply Operation", "Cancel Operation"};

  private void setButtonActions(){
    // I/O operations
    buttonActions.put("Load Image",evt->{
      String filePath=this.getUploadedFilePath();
      this.features.load(filePath);
    });
    buttonActions.put("Save Image",evt->{
      String filePath=this.getSavingFilePath();
      this.features.save(filePath);
    });
    buttonActions.put("Exit App",evt->System.exit(0));

    // Non-split operations:
    buttonActions.put("Visualize Red Component",evt->{this.features.getSingleComponent(ColorMapping.red.ordinal());});
    buttonActions.put("Visualize Green Component",evt->{this.features.getSingleComponent(ColorMapping.green.ordinal());});
    buttonActions.put("Visualize Blue Component",evt->{this.features.getSingleComponent(ColorMapping.blue.ordinal());});
    buttonActions.put("Flip Horizontal", evt->{this.features.flip(AxisName.horizontal.ordinal());});
    buttonActions.put("Flip Vertical", evt->{this.features.flip(AxisName.vertical.ordinal());});

    // Apply and cancel
    buttonActions.put("Apply Operation",evt->{this.features.applyOperation();});
    buttonActions.put("Cancel Operation",evt->{this.features.cancelOperation();});

    // Popup/Split operations
    buttonActions.put("Blur",evt->
    {
      int splitPercentage=getSplitPercentage("Blur Split");
      this.features.changeSharpness(KernelImage.Blur.ordinal(),splitPercentage);
    });
    buttonActions.put("Sharpen",evt->
    {
      int splitPercentage=getSplitPercentage("Sharpen Split");
      this.features.changeSharpness(KernelImage.Sharpen.ordinal(),splitPercentage);
    });
    buttonActions.put("Sepia",evt->
    {
      int splitPercentage=getSplitPercentage("Sepia Split");
      this.features.sepia(splitPercentage);
    });
    buttonActions.put("Color Correction",evt->
    {
      int splitPercentage=getSplitPercentage("Color Correction Split");
      this.features.colorCorrection(splitPercentage);
    });
    buttonActions.put("Compression",evt->
    {
      int compressionFactor=getSplitPercentage("Image Compression Factor");
      this.features.compressImage(compressionFactor);
    });
    buttonActions.put("Level Adjustment", evt->
    {
      List<Integer>levelAdjustmentValues=getLevelAdjustmentValues("Level Adjustment Split");
      int blackPoint=levelAdjustmentValues.get(LevelAdjustment.BLACK.levelValue);
      int midPoint=levelAdjustmentValues.get(LevelAdjustment.MID.levelValue);
      int highlightPoint=levelAdjustmentValues.get(LevelAdjustment.HIGHLIGHT.levelValue);
      int splitPercentage=levelAdjustmentValues.get(0);
      this.features.levelAdjustment(blackPoint,midPoint,highlightPoint,splitPercentage);
    });
  }

  private int getSplitPercentage(String operationTitle){
    SimpleDialogSliderInterface simpleJDialog=new SimpleDialogSliderPreview(this,operationTitle);
    int splitPercentage=100;
    if(simpleJDialog.getResultOperationFlag()){
      splitPercentage= simpleJDialog.getSliderPercentage();
    }
    simpleJDialog.dispose();
    return splitPercentage;
  }

  private List<Integer> getLevelAdjustmentValues(String operationTitle){
    InputSliderDialogInterface levelAdjustDialog=new LevelAdjust(this,operationTitle);
    List<Integer> resultList=null;
    if(levelAdjustDialog.getResultOperationFlag()){
      resultList=levelAdjustDialog.getListOfInputValues();
    }
    levelAdjustDialog.dispose();
    return resultList;
  }
  private String getUploadedFilePath(){
    JFileChooser selectFile=new JFileChooser(".");
    FileNameExtensionFilter fileExtensions=new FileNameExtensionFilter("PPM, JPG, JPEG, PNG Images",
            "jpg","jpeg","png","ppm");
    selectFile.setFileFilter(fileExtensions);
    int retrievalResult=selectFile.showOpenDialog(GraphicalView.this);
    if(retrievalResult==JFileChooser.APPROVE_OPTION){
      File f=selectFile.getSelectedFile();
      return f.getPath();
    }
    return null;
  }

  private String getSavingFilePath(){
    JFileChooser selectFile=new JFileChooser(".");
    FileNameExtensionFilter fileExtensions=new FileNameExtensionFilter("PPM, JPG, JPEG, PNG Images",
            "jpg","jpeg","png","ppm");
    selectFile.setFileFilter(fileExtensions);
    int retrievalResult=selectFile.showSaveDialog(GraphicalView.this);
    if(retrievalResult==JFileChooser.APPROVE_OPTION){
      File f=selectFile.getSelectedFile();
      return f.getPath();
    }
    return null;
  }

  public GraphicalView(){
    super();
    setTitle("Image Processing Application");
    this.setSize(new Dimension(1000,1000));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.buttonActions=new HashMap<>();
    setButtonActions();
  }

  @Override
  public void setDisplay() {
    this.mainPanel=new JPanel();
    this.mainPanel.setLayout(new BorderLayout(30,15));
    JScrollPane mainScrollPane=new JScrollPane(mainPanel);
    this.add(mainScrollPane);
    Image image=new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
    this.lineStartPanel=getLineStartPanel(image);
    this.centerPanel=getLiveImagePanel(image,"Image Preview",1000,800);
    this.pageEndPanel=getPageEndPanel();
    this.mainPanel.add(lineStartPanel,BorderLayout.LINE_START);
    this.mainPanel.add(centerPanel,BorderLayout.CENTER);
    this.mainPanel.add(pageEndPanel,BorderLayout.PAGE_END);
    setVisibility(true,this);
  }

  @Override
  public void setPopupMessage(String message) {
    JOptionPane.showMessageDialog(this,message,"Popup Message",JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void setErrorMessage(String message) {
    JOptionPane.showMessageDialog(this,message,"Error Message",JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void displayImage(Image image) {
    setVisibility(false,this.centerPanel);
    this.centerPanel=getLiveImagePanel(image,"Image Preview",1000,800);
    this.mainPanel.add(this.centerPanel,BorderLayout.CENTER);
    setVisibility(true,this);
  }

  @Override
  public void displayHistogram(Image image) {
    setVisibility(false,this.histogramPanel);
    this.histogramPanel=getLiveImagePanel(image,"Histogram of the image",256,256);
    lineStartPanel.add(this.histogramPanel,BorderLayout.PAGE_END);
    setVisibility(true,this);
  }

  @Override
  public void setFeatures(Features features) {
    this.features=features;
  }


  private JPanel getLiveImagePanel(Image image, String title,int x,int y){
    JPanel imagePanel = new JPanel();
    TitledBorder imageBorder=BorderFactory.createTitledBorder(title);
    imageBorder.setTitleJustification(TitledBorder.CENTER);
    imagePanel.setBorder(imageBorder);
    JLabel imageLabel=new JLabel();
    JScrollPane imageScrollPane=new JScrollPane(imageLabel);
    imageLabel.setIcon(new ImageIcon(image));
    imageScrollPane.setPreferredSize(new Dimension(x,y));
    imagePanel.add(imageScrollPane);
    return imagePanel;
  }

  private JPanel getLineStartPanel(Image imageData){
    JPanel lineStart=new JPanel();
    lineStart.setLayout(new BorderLayout(5,20));
    JPanel ioPanel=setPanel(ioOperations,"I/O Operations",new FlowLayout(FlowLayout.CENTER,10,10));
    JPanel opPanel=setPanel(imgOp,"Image Operations",new GridLayout(imgOp.length,1));
    this.histogramPanel=getLiveImagePanel(imageData,"Histogram of the image",256,256);
    lineStart.add(ioPanel,BorderLayout.PAGE_START);
    lineStart.add(opPanel,BorderLayout.CENTER);
    lineStart.add(histogramPanel,BorderLayout.PAGE_END);
    return lineStart;
  }

  private JPanel setPanel(String [] buttonList, String panelTitle,LayoutManager manager){
    JPanel resPanel=new JPanel();
    resPanel.setLayout(manager);
    addButtons(buttonList,resPanel);
    TitledBorder panelBorder=BorderFactory.createTitledBorder(panelTitle);
    panelBorder.setTitleJustification(TitledBorder.CENTER);
    resPanel.setBorder(panelBorder);
    return resPanel;
  }

  private JPanel getPageEndPanel(){
    JPanel lowerPanel=new JPanel();
    lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
    addButtons(changeImage,lowerPanel);
    return lowerPanel;
  }

  private void addButtons(String[] buttonList, JPanel panel){
    for(String s: buttonList){
      JButton button=new JButton(s);
      button.setActionCommand(s);
      ActionListener event=this.buttonActions.getOrDefault(button.getActionCommand(),null);
      if(event!=null){
        button.addActionListener(event);
      }
      panel.add(button);
    }
  }

  private void setVisibility(boolean isVisible, Component component){
    component.setVisible(isVisible);
  }

  public static void main(String[] args){
    IView view=new GraphicalView();
    GraphicalControllerInterface controller=new GraphicalController(view);

  }
}
