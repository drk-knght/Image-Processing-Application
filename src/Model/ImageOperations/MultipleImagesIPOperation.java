package Model.ImageOperations;

import java.util.List;

import Model.RGBImageInterface;

public interface MultipleImagesIPOperation {
  RGBImageInterface operation(List<RGBImageInterface> rgbimages);
}
