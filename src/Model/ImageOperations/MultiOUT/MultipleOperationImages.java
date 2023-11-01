package Model.ImageOperations.MultiOUT;

import java.io.IOException;
import java.util.List;

import Model.RGBImageInterface;

public interface MultipleOperationImages {

  List<RGBImageInterface> operation(RGBImageInterface rgbImages) throws IllegalArgumentException;
}
