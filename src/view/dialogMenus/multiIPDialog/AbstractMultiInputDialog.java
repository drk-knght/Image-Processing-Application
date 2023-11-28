//package view.dialogMenus.multiIPDialog;
//
//import java.util.List;
//
//import javax.swing.*;
//import javax.swing.border.TitledBorder;
//
//public abstract class AbstractMultiInputDialog extends JDialog implements MultiInputSliderDialogInterface {
//
//
//  protected boolean result;
//
//  protected JPanel getApplyButton(){
//    JPanel buttonPanel=new JPanel();
//    JButton applyButton = new JButton("Apply Operation");
//    applyButton.addActionListener(evt->{
//      this.result=true;
//      this.setVisible(false);
//    });
//    buttonPanel.add(applyButton);
//    return buttonPanel;
//  }
//
//  protected void setTitleBorder(JPanel panel, String headingLabel){
//    TitledBorder sliderBorder=BorderFactory.createTitledBorder(headingLabel);
//    sliderBorder.setTitleJustification(TitledBorder.CENTER);
//    panel.setBorder(sliderBorder);
//  }
//
//  protected void setSliderProperties(JSlider slider, int max, JLabel actionChangeLabel){
//    slider.setMinimum(0);
//    slider.setMaximum(max);
//    slider.setPaintLabels(true);
//    slider.setPaintTicks(true);
//    slider.setMajorTickSpacing(10);
//    slider.setMinorTickSpacing(5);
//    slider.addChangeListener(evt->{setLabelText(actionChangeLabel,slider.getValue());});
//  }
//
//  abstract protected void setChangeListener(JLabel actionChangeLabel,JSlider slider);
//
//  @Override
//  public boolean getResultOperationFlag() {
//    return this.result;
//  }
//
//
//}
