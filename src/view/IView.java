package view;

import java.awt.*;
import java.util.List;

import controller.features.Features;
import view.dialogMenus.multiipdialog.MultiInputSliderDialogInterface;

public interface IView {

  void setDisplay();

  void setPopupMessage(String message);

  void setErrorMessage(String message);

  void displayImage(Image image);

  void displayHistogram(Image image);

  void setFeatures(Features features);

  String getInputFilePath();

  String getOutputFilePath();

  Integer displayDialogSingleSplitPreview(String operationTitle,String labelText);

  List<Integer> displayDialogMultiINPreview(MultiInputSliderDialogInterface levelAdjustDialog);

}
