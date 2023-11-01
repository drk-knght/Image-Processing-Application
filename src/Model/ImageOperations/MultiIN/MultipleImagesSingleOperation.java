package Model.ImageOperations.MultiIN;

import java.util.List;

import Model.RGBImageInterface;

public interface MultipleImagesSingleOperation {
  RGBImageInterface operation(List<RGBImageInterface> rgbImages) throws IllegalArgumentException;
}
