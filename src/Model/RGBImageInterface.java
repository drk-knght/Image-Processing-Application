package Model;

import java.io.IOException;
import java.util.List;

public interface RGBImageInterface {

  void saveImage(String imagePath) throws IOException;

  RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException;

  RGBImageInterface changeBrightness(int deltaChangeValue);

  RGBImageInterface changeSharpness(int kernelType) throws IllegalArgumentException;

  RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents) throws IllegalArgumentException;

  List<RGBImageInterface> splitImageComponents() throws IOException;

  RGBImageInterface getSingleComponentImage(int colorValue) throws IllegalArgumentException;

  RGBImageInterface greyScaleImage(int greyScaleType) throws IllegalArgumentException;

  RGBImageInterface sepiaImage();

  int getImageHeight();

  int getImageWidth();

  int[][][] getPixel();
}
