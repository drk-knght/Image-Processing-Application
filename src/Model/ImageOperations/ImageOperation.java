package Model.ImageOperations;

import java.io.IOException;

import Model.RGBImageInterface;

public interface ImageOperation {

  RGBImageInterface operation(RGBImageInterface rgbImage) throws IOException;
}
