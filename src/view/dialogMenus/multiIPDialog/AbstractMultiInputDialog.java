package view.dialogMenus.multiIPDialog;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public abstract class AbstractMultiInputDialog extends JDialog implements MultiInputSliderDialogInterface {

  protected boolean result;

  public AbstractMultiInputDialog(JFrame parentFrame, String title){
    super(parentFrame,title,true);
    this.result=false;
  }

  protected JPanel getApplyButton(){
    JPanel buttonPanel=new JPanel();
    JButton applyButton = new JButton("Apply Operation");
    applyButton.addActionListener(evt->{
      this.result=true;
      this.setVisible(false);
    });
    buttonPanel.add(applyButton);
    return buttonPanel;
  }

  protected JPanel getSingleJSliderPanel(String headingLabel, int max, JSlider presentSlider, JLabel actionChangeLabel){
    JPanel panelInUse=new JPanel();
    panelInUse.setLayout(new BorderLayout());
    setTitleBorder(panelInUse,headingLabel);
    setSliderProperties(presentSlider, max,actionChangeLabel);
    panelInUse.add(presentSlider,BorderLayout.CENTER);
    panelInUse.add(actionChangeLabel,BorderLayout.PAGE_END);
    return panelInUse;
  }

  protected void setTitleBorder(JPanel panel, String headingLabel){
    TitledBorder sliderBorder=BorderFactory.createTitledBorder(headingLabel);
    sliderBorder.setTitleJustification(TitledBorder.CENTER);
    panel.setBorder(sliderBorder);
  }

  private void setSliderProperties(JSlider slider, int max, JLabel actionChangeLabel){
    slider.setMinimum(0);
    slider.setMaximum(max);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(5);
    slider.addChangeListener(evt->{setLabelText(actionChangeLabel,slider);});
  }

  abstract protected void setLabelText(JLabel actionChangeLabel,JSlider slider);

  @Override
  public boolean getResultOperationFlag() {
    return this.result;
  }


}
