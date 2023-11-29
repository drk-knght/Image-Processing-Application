package view.dialogMenus.multiipdialog;

import java.util.List;

public interface MultiInputSliderDialogInterface {

  boolean getResultOperationFlag();

  void dispose();

  List<Integer> getListOfInputValues();
}
