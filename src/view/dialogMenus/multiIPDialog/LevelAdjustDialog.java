package view.dialogMenus.multiIPDialog;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class LevelAdjustDialog extends AbstractMultiInputDialog{

  private JLabel sliderSplitTextLabel;

  private JLabel blackPointLabel;

  private JLabel midPointLabel;

  private JLabel highlightPointLabel;

  private JSlider splitPreviewSlider;

  private JSlider blackPointSlider;

  private JSlider midPointSlider;

  private JSlider highlightPointSlider;

  public LevelAdjustDialog(JFrame parentFrame, String title){
    super(parentFrame,title);
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
    setLabelText(this.blackPointLabel,this.blackPointSlider);

    this.midPointLabel=new JLabel();
    setLabelText(this.midPointLabel,this.midPointSlider);

    this.highlightPointLabel=new JLabel();
    setLabelText(this.highlightPointLabel,this.highlightPointSlider);

    this.sliderSplitTextLabel=new JLabel();
    setLabelText(this.sliderSplitTextLabel,this.splitPreviewSlider);
  }

  private JPanel getCombinedSlidersPanel(){
    JPanel displayPanel=new JPanel();
    displayPanel.setLayout(new GridLayout(4,1,1,40));
    JPanel blackPanel=getSingleJSliderPanel("Black Point", 255,blackPointSlider,blackPointLabel);
    JPanel midPanel=getSingleJSliderPanel("Mid Point", 255,midPointSlider,midPointLabel);
    JPanel highlightPanel=getSingleJSliderPanel("Highlight Point", 255,highlightPointSlider,highlightPointLabel);
    JPanel splitPanel=getSingleJSliderPanel("Split Preview %", 100,splitPreviewSlider,sliderSplitTextLabel);
    displayPanel.add(blackPanel,0);
    displayPanel.add(midPanel,1);
    displayPanel.add(highlightPanel,2);
    displayPanel.add(splitPanel,3);
    return displayPanel;
  }


  @Override
  protected void setLabelText(JLabel actionChangeLabel, JSlider slider) {
    actionChangeLabel.setText("The number selected is: "+slider.getValue());
  }

  @Override
  public List<Integer> getListOfInputValues() {
    List<Integer> sliderInputValues=new ArrayList<>();
    sliderInputValues.add(splitPreviewSlider.getValue());
    sliderInputValues.add(blackPointSlider.getValue());
    sliderInputValues.add(midPointSlider.getValue());
    sliderInputValues.add(highlightPointSlider.getValue());
    return sliderInputValues;
  }
}
