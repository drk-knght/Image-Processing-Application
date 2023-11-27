package view.dialogMenus.multiIPDialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import enums.GreyScaleType;

public class GreyScaleDialog extends JDialog implements MultiInputSliderDialogInterface {

  private boolean result;

  private JRadioButton lumaButton;

  private JRadioButton valueButton;

  private JRadioButton intensityButton;

  private ButtonGroup greyscaleGroup;

  private JLabel sliderLabel;

  private JSlider splitViewSlider;

  private int greyScaleValue;

  // 1. create a main panel using border layout.
  // 2. make line start panel using radio buttons for greyscale.
  // 3. Make center panel using slider and the label.
  // 4. make page end panel apply operation.


  public GreyScaleDialog(JFrame parentFrame, String title){
    super(parentFrame,title,true);
    this.result=false;
    initializeAndSetRadioButtons();
    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30,15));
    JScrollPane displayScrollPane=new JScrollPane(displayPanel);
    this.add(displayScrollPane);
    this.setSize(new Dimension(1000,1000));
    JPanel sliderPanel=getSliderPanel();
    JPanel greyscalePanel=getGreyScaleMenu();
    JPanel applyButtonPanel=getApplyButton();
    displayPanel.add(greyscalePanel,BorderLayout.LINE_START);
    displayPanel.add(sliderPanel,BorderLayout.CENTER);
    displayPanel.add(applyButtonPanel,BorderLayout.PAGE_END);
    this.setVisible(true);
  }



  private void initializeAndSetRadioButtons(){
    this.lumaButton=new JRadioButton("Luma Operation");
    this.lumaButton.addActionListener(evt->this.greyScaleValue= GreyScaleType.luma.ordinal());

    this.valueButton=new JRadioButton("Value Operation");
    this.valueButton.addActionListener(evt->this.greyScaleValue= GreyScaleType.value.ordinal());

    this.intensityButton=new JRadioButton("Intensity Operation");
    this.intensityButton.addActionListener(evt->this.greyScaleValue= GreyScaleType.intensity.ordinal());

    this.greyscaleGroup=new ButtonGroup();
    this.greyscaleGroup.add(this.lumaButton);
    this.greyscaleGroup.add(this.valueButton);
    this.greyscaleGroup.add(this.intensityButton);
  }

  private JPanel getSliderPanel(){
    JPanel sliderPanel=new JPanel();
    sliderPanel.setLayout(new BorderLayout(10,5));
    this.splitViewSlider=getJSlider();
    this.sliderLabel=new JLabel();
    setLabelText(splitViewSlider.getValue());
    sliderPanel.add(splitViewSlider,BorderLayout.CENTER);
    sliderPanel.add(sliderLabel,BorderLayout.PAGE_END);
    setTitleBorder(sliderPanel,"Select Split Percentage");
    return sliderPanel;
  }

  private void setLabelText(int value){
    this.sliderLabel.setText("The % of image on which operation is visible: "+value);
  }

  private JSlider getJSlider(){
    JSlider slider=new JSlider();
    slider.setMinimum(0);
    slider.setMaximum(100);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.addChangeListener(evt->{setLabelText(this.splitViewSlider.getValue());});
    return slider;
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

  private void setTitleBorder(JPanel panel, String headingLabel){
    TitledBorder sliderBorder=BorderFactory.createTitledBorder(headingLabel);
    sliderBorder.setTitleJustification(TitledBorder.CENTER);
    panel.setBorder(sliderBorder);
  }

  @Override
  public boolean getResultOperationFlag() {
    return this.result;
  }

  @Override
  public List<Integer> getListOfInputValues() {
    List<Integer> sliderInputValues=new ArrayList<>();
    sliderInputValues.add(this.splitViewSlider.getValue());
    sliderInputValues.add(this.greyScaleValue);
    return sliderInputValues;
  }

  private JPanel getGreyScaleMenu(){
    JPanel greyscaleMenu=new JPanel();
    greyscaleMenu.setLayout(new GridLayout(3,1,5,15));
    greyscaleMenu.add(this.lumaButton,0);
    greyscaleMenu.add(this.valueButton,1);
    greyscaleMenu.add(this.intensityButton,2);
    setTitleBorder(greyscaleMenu,"Grey Scale Types");
    return greyscaleMenu;
  }
}
