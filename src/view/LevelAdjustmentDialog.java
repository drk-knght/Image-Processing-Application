//package view;
//
//import java.awt.*;
//import java.util.List;
//
//import javax.swing.*;
//
//public class LevelAdjustmentDialog extends JDialog implements InputSliderDialogInterface {
//
//  private JLabel sliderSplitTextLabel;
//
//  private JLabel blackPointLabel;
//
//  private JLabel midPointLabel;
//
//  private JLabel highlightPointLabel;
//
//  private JSlider splitPreviewSlider;
//
//  private JSlider blackPointSlider;
//
//  private JSlider midPointSlider;
//
//  private JSlider highlightPointSlider;
//
//  private int sliderPercentage;
//
//  private boolean result;
//
//  public LevelAdjustmentDialog(JFrame parentFrame, String title){
//    super(parentFrame,title,true);
//    this.result=false;
//    JPanel displayPanel = new JPanel();
//    displayPanel.setLayout(new BorderLayout(30,15));
//    JScrollPane displayScrollPane=new JScrollPane(displayPanel);
//    this.add(displayScrollPane);
//    JPanel sliderPanel=getSliderPanel();
//    JPanel applyButton=getApplyButton();
//    displayPanel.add(sliderPanel,BorderLayout.CENTER);
//    displayPanel.add(applyButton,BorderLayout.PAGE_END);
//    this.setSize(new Dimension(500,200));
//    this.setVisible(true);
//  }
//
//  @Override
//  public boolean getResultOperationFlag() {
//    return false;
//  }
//
//  @Override
//  public List<Integer> getListOfInputValues() {
//    return null;
//  }
//
//  // 1. Main panel will have a border layout structure with all sliders and their labels in the center panel
//  // 2. create a grid layout for all the sliders.
//  // 3. for each slider create a border layout-
//    // 3.1. label to show the heading of the slider like black, mid, highlight etc.
//    // 3.2. with min, max value in center panel.
//    // 3.3. label to show the current slider value in the page end panel.
//
//  private JPanel getSingleSliderPanel(int min, int max, String headingLabel){
//    JPanel sliderPanel=new JPanel();
//    sliderPanel.setLayout(new BorderLayout());
//    JLabel heading=new JLabel(headingLabel);
//
//    JSlider slider=getSlider(min,max,)
//  }
//
//  private JSlider getSlider(int min, int max, JLabel fieldLabel){
//    JSlider slider=new JSlider();
//    slider.setMinimum(min);
//    slider.setMaximum(max);
//    slider.setPaintLabels(true);
//    slider.setPaintTicks(true);
//    slider.setMajorTickSpacing(5);
//    slider.setMinorTickSpacing(1);
//    slider.addChangeListener(evt->{setLabelText(fieldLabel,slider.getValue());});
//    return slider;
//  }
//
////  private void addChangeListener(JSlider slider, JLabel fieldLabel, String displayText){
////    slider.
////  }
//
//  private void setLabelText(JLabel label, int sliderValue){
//    label.setText("Number Selected for the "+sliderValue);
//  }
//}
