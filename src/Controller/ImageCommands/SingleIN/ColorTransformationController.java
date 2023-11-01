package Controller.ImageCommands.SingleIN;

import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;

import Model.RGBImageInterface;

public class ColorTransformationController implements RGBImageCommandInterface {

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  public ColorTransformationController(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 2) {
      throw new IllegalArgumentException("The number of parameters does not match " +
              "with the expected number of parameters for the passed operation.");
    }
    this.rgbExistingImage = commandArguments[0];
    this.rgbModifiedImage = commandArguments[1];
  }

  public ColorTransformationController(String rgbExistingImage, String rgbModifiedImage) throws IllegalArgumentException {
    if(rgbExistingImage==null || rgbModifiedImage==null){
      throw new IllegalArgumentException("Parameters passed for the color transformation are not as expected.");
    }
    this.rgbExistingImage = rgbExistingImage;
    this.rgbModifiedImage = rgbModifiedImage;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws NullPointerException {
    if(cachedImage==null){
      throw new NullPointerException("The lookup table passed for the image processing app does not exists.");
    }
    RGBImageInterface existingImage = cachedImage.get(rgbExistingImage);

    if (existingImage == null) {
      return;
    }
    RGBImageInterface rgbImage = existingImage.sepiaImage();
    cachedImage.put(rgbModifiedImage, rgbImage);
  }
}
