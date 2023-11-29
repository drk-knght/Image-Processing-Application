package view;

import java.awt.*;

import controller.features.Features;

public interface IView {

  void setDisplay();

  void setPopupMessage(String message);

  void setErrorMessage(String message);

  void displayImage(Image image);

  void displayHistogram(Image image);

  void setFeatures(Features features);

}
