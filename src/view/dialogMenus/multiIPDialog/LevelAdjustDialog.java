package view.dialogMenus.multiIPDialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LevelAdjustDialog extends JDialog implements MultiInputSliderDialogInterface {

  private JLabel sliderSplitTextLabel;

  private JLabel blackPointLabel;

  private JLabel midPointLabel;

  private JLabel highlightPointLabel;

  private JSlider splitPreviewSlider;

  private JSlider blackPointSlider;

  private JSlider midPointSlider;

  private JSlider highlightPointSlider;

  private boolean result;


  public LevelAdjustDialog(JFrame parentFrame, String title){
    super(parentFrame,title,true);
    this.result=false;
    initializeSliders();
    initializeLabels();
    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30,15));
    JScrollPane displayScrollPane=new JScrollPane(displayPanel);
    this.add(displayScrollPane);
    JPanel sliderPanel=getCombinedSlidersPanel();
    JPanel applyButton=getApplyButton();
    displayPanel.add(sliderPanel,BorderLayout.CENTER);
    displayPanel.add(applyButton,BorderLayout.PAGE_END);
    this.setSize(new Dimension(1000,1000));
    this.setVisible(true);
  }

  private void initializeSliders(){
    this.blackPointSlider=new JSlider();
    this.midPointSlider=new JSlider();
    this.highlightPointSlider=new JSlider();
    this.splitPreviewSlider=new JSlider();
  }

  private void initializeLabels(){
    this.blackPointLabel=new JLabel();
    setLabelText(blackPointLabel,this.blackPointSlider.getValue());

    this.midPointLabel=new JLabel();
    setLabelText(midPointLabel,this.midPointSlider.getValue());

    this.highlightPointLabel=new JLabel();
    setLabelText(highlightPointLabel,this.highlightPointSlider.getValue());

    this.sliderSplitTextLabel=new JLabel();
    setLabelText(sliderSplitTextLabel,this.splitPreviewSlider.getValue());
  }

  // 1. Main panel will have a border layout structure with all sliders and their labels in the center panel
  // 2. create a grid layout for all the sliders.
  // 3. for each slider create a border layout-
  // 3.1. label to show the heading of the slider like black, mid, highlight etc.
  // 3.2. with min, max value in center panel.
  // 3.3. label to show the current slider value in the page end panel.

  private JPanel getCombinedSlidersPanel(){
    JPanel displayPanel=new JPanel();
    displayPanel.setLayout(new GridLayout(4,1,1,40));

//    JPanel blackPanel=new JPanel();
//    blackPanel.setLayout(new BorderLayout());
//    blackPanel.add(new JLabel("Black Point"),BorderLayout.PAGE_START);
//    this.blackPointSlider=new JSlider();
//    this.blackPointSlider.setMinimum(0);
//    this.blackPointSlider.setMaximum(255);
//    this.blackPointSlider.setPaintLabels(true);
//    this.blackPointSlider.setPaintTicks(true);
//    this.blackPointSlider.setMajorTickSpacing(5);
//    this.blackPointSlider.setMinorTickSpacing(1);
//    this.blackPointLabel=new JLabel();
//    this.blackPointSlider.addChangeListener(evt->{setLabelText(this.blackPointLabel,this.blackPointSlider.getValue());});
//    blackPanel.add(this.blackPointSlider,BorderLayout.CENTER);
//    blackPanel.add(this.blackPointLabel,BorderLayout.PAGE_END);
    JPanel blackPanel=getSingleJSliderPanel("Black Point",0,255,blackPointSlider,blackPointLabel);
    JPanel midPanel=getSingleJSliderPanel("Mid Point",0,255,midPointSlider,midPointLabel);
    JPanel highlightPanel=getSingleJSliderPanel("Highlight Point",0,255,highlightPointSlider,highlightPointLabel);
    JPanel splitPanel=getSingleJSliderPanel("Split Preview %",0,100,splitPreviewSlider,sliderSplitTextLabel);
    displayPanel.add(blackPanel,0);
    displayPanel.add(midPanel,1);
    displayPanel.add(highlightPanel,2);
    displayPanel.add(splitPanel,3);
    return displayPanel;
  }

  private JPanel getSingleJSliderPanel(String headingLabel,int min, int max,JSlider presentSlider, JLabel actionChangeLabel){
    JPanel panelInUse=new JPanel();
    panelInUse.setLayout(new BorderLayout());
    setTitleBorder(panelInUse,headingLabel);
    setSliderProperties(presentSlider,min,max,actionChangeLabel);
    panelInUse.add(presentSlider,BorderLayout.CENTER);
    panelInUse.add(actionChangeLabel,BorderLayout.PAGE_END);
    return panelInUse;
  }

  private void setSliderProperties(JSlider slider, int min, int max, JLabel actionChangeLabel){
    slider.setMinimum(min);
    slider.setMaximum(max);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(5);
    slider.addChangeListener(evt->{setLabelText(actionChangeLabel,slider.getValue());});
  }

  private void setTitleBorder(JPanel panel, String headingLabel){
    TitledBorder sliderBorder=BorderFactory.createTitledBorder(headingLabel);
    sliderBorder.setTitleJustification(TitledBorder.CENTER);
    panel.setBorder(sliderBorder);
  }

  private void setLabelText(JLabel label, int value){
    label.setText("The selected number is:"+value);
  }

  private JPanel getApplyButton(){
    JPanel buttonPanel=new JPanel();
    JButton applyButton = new JButton("Apply Operation");
    applyButton.addActionListener(evt->{
      this.result=true;
      this.setVisible(false);
    });
    buttonPanel.add(applyButton);
    return buttonPanel;
  }

  @Override
  public boolean getResultOperationFlag() {
    return this.result;
  }

  @Override
  public List<Integer> getListOfInputValues() {
    List<Integer> sliderInputValues=new ArrayList<>();
    sliderInputValues.add(splitPreviewSlider.getValue());
    sliderInputValues.add(blackPointSlider.getValue());
    sliderInputValues.add(midPointSlider.getValue());
    sliderInputValues.add(highlightPointSlider.getValue());
//    sliderInputValues.set(LevelAdjustment.BLACK.ordinal(),blackPointSlider.getValue());
//    sliderInputValues.set(LevelAdjustment.MID.ordinal(),midPointSlider.getValue());
//    sliderInputValues.set(LevelAdjustment.HIGHLIGHT.ordinal(),highlightPointSlider.getValue());
    return sliderInputValues;
  }
}
