package Model;

import java.io.IOException;
import java.util.List;

public interface RGBImageInterface {
  void saveImage(String imagePath, String ImageName) throws IOException;

  RGBImageInterface flipImage(int axisDirection) throws IOException;

  RGBImageInterface changeBrightness(int deltaChangeValue) throws IOException;

  RGBImageInterface changeSharpness(int kernelType) throws IOException;

  RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents);

  List<RGBImageInterface> splitImageComponents() throws IOException;

  RGBImageInterface getSingleComponentImage(int colorValue) throws IOException;

  RGBImageInterface greyScaleImage(int greyScaleType) throws IOException;

  int getImageHeight();

  int getImageWidth();

  int [][][] getPixel();

  int getMaxValue();

}
