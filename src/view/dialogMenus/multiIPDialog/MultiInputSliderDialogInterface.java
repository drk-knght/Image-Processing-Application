package view.dialogMenus.multiIPDialog;

import java.util.List;

public interface MultiInputSliderDialogInterface {

  boolean getResultOperationFlag();

  void dispose();

  List<Integer> getListOfInputValues();
}
