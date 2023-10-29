package Model.ImageOperations;

import java.io.IOException;
import java.util.List;

import Model.RGBImageInterface;

public interface MultipleImagesOPOperation {

  List<RGBImageInterface> operation(RGBImageInterface rgbImages) throws IOException;
}
