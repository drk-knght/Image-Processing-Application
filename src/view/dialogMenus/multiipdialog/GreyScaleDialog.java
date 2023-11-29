package view.dialogMenus.multiipdialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import enums.GreyScaleType;

public class GreyScaleDialog extends  AbstractMultiInputDialog{

  private JRadioButton lumaButton;

  private JRadioButton valueButton;

  private JRadioButton intensityButton;

  private JLabel sliderLabel;

  private JSlider splitViewSlider;

  private int greyScaleValue;


  public GreyScaleDialog(JFrame parentFrame, String title){
    super(parentFrame,title);
    initializeAndSetRadioButtons();
    initializeSliderFields();

    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30,15));
    addScrollPaneToFrame(displayPanel);
    this.setSize(new Dimension(1000,1000));

    JPanel sliderPanel=getSingleJSliderPanel("Select Split Percentage",100,splitViewSlider,sliderLabel);
    JPanel greyscalePanel=getGreyScaleMenu();
    JPanel applyButtonPanel=getApplyButton();

    addPanelsToDisplay(displayPanel,greyscalePanel,sliderPanel,applyButtonPanel);

    this.setVisible(true);
  }

  private void addScrollPaneToFrame(JPanel displayPanel){
    JScrollPane displayScrollPane=new JScrollPane(displayPanel);
    this.add(displayScrollPane);
  }
  private void addPanelsToDisplay(JPanel displayPanel, JPanel greyscalePanel, JPanel sliderPanel, JPanel applyButtonPanel){
    displayPanel.add(greyscalePanel,BorderLayout.LINE_START);
    displayPanel.add(sliderPanel,BorderLayout.CENTER);
    displayPanel.add(applyButtonPanel,BorderLayout.PAGE_END);
  }

  private void initializeAndSetRadioButtons(){
    this.lumaButton=new JRadioButton("Luma Operation");
    this.lumaButton.setSelected(true);
    this.lumaButton.addActionListener(evt->this.greyScaleValue= GreyScaleType.luma.ordinal());

    this.valueButton=new JRadioButton("Value Operation");
    this.valueButton.addActionListener(evt->this.greyScaleValue= GreyScaleType.value.ordinal());

    this.intensityButton=new JRadioButton("Intensity Operation");
    this.intensityButton.addActionListener(evt->this.greyScaleValue= GreyScaleType.intensity.ordinal());

    ButtonGroup greyscaleGroup=new ButtonGroup();
    greyscaleGroup.add(this.lumaButton);
    greyscaleGroup.add(this.valueButton);
    greyscaleGroup.add(this.intensityButton);
  }

  private void initializeSliderFields(){
    this.sliderLabel=new JLabel();
    this.splitViewSlider=new JSlider();
    setLabelText(this.sliderLabel,this.splitViewSlider);
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

  @Override
  protected void setLabelText(JLabel actionChangeLabel, JSlider slider) {
    actionChangeLabel.setText("The preview % of image on which greyscale operation is visible: "
            + slider.getValue());
  }

  @Override
  public List<Integer> getListOfInputValues() {
    List<Integer> sliderInputValues=new ArrayList<>();
    sliderInputValues.add(this.splitViewSlider.getValue());
    sliderInputValues.add(this.greyScaleValue);
    return sliderInputValues;
  }
}
