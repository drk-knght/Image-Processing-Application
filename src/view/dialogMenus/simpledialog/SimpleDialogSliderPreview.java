package view.dialogMenus.simpledialog;

import java.awt.*;

import javax.swing.*;



public class SimpleDialogSliderPreview extends JDialog implements SimpleDialogSliderInterface {

  private JLabel textLabel;

  private JSlider splitPreviewSlider;

  private int sliderPercentage;

  private String labelText;

  private boolean result;


  public SimpleDialogSliderPreview(JFrame parentFrame, String title,String labelText){
    super(parentFrame,title,true);
    this.result=false;
    this.labelText=labelText;

    JPanel displayPanel = new JPanel();
    displayPanel.setLayout(new BorderLayout(30,15));
    JScrollPane displayScrollPane=new JScrollPane(displayPanel);
    this.add(displayScrollPane);

    JPanel sliderPanel=getSliderPanel();
    JPanel applyButton=getApplyButton();
    displayPanel.add(sliderPanel,BorderLayout.CENTER);
    displayPanel.add(applyButton,BorderLayout.PAGE_END);

    this.setSize(new Dimension(500,200));
    this.setVisible(true);
  }

  private JPanel getSliderPanel(){
    JPanel sliderPanel=new JPanel();
    sliderPanel.setLayout(new BorderLayout(10,5));
    this.splitPreviewSlider=getJSlider();
    this.textLabel=new JLabel();
    setLabelText(splitPreviewSlider.getValue());
    sliderPanel.add(splitPreviewSlider,BorderLayout.CENTER);
    sliderPanel.add(textLabel,BorderLayout.PAGE_END);
    return sliderPanel;
  }

  private void setLabelText(int value){

    this.textLabel.setText(this.labelText+": "+value);
  }

  private JSlider getJSlider(){
    JSlider slider=new JSlider();
    slider.setMinimum(0);
    slider.setMaximum(100);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.addChangeListener(evt->{setLabelText(this.splitPreviewSlider.getValue());});
    return slider;
  }

  private JPanel getApplyButton(){
    JPanel buttonPanel=new JPanel();
    JButton applyButton = new JButton("Apply Operation");
    applyButton.addActionListener(evt->{
      setSliderPercentage();
      this.result=true;
      this.setVisible(false);
    });
    buttonPanel.add(applyButton);
    return buttonPanel;
  }

  private void setSliderPercentage(){
    this.sliderPercentage=this.splitPreviewSlider.getValue();
  }
  @Override
  public int getSliderPercentage() {
    return sliderPercentage;
  }

  @Override
  public boolean getResultOperationFlag(){
    return this.result;
  }

}