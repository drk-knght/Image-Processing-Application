package Controller.ImageCommands.SingleIn;

import java.io.IOException;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;

import Model.RGBImageInterface;

public class ColorTransformationController implements RGBImageCommandInterface {

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  public ColorTransformationController(String [] commandArguments){
    if(commandArguments.length!=2){
      throw new IllegalArgumentException("The number of parameters does not match " +
              "with the expected number of parameters for the passed operation.");
    }
    this.rgbExistingImage=commandArguments[0];
    this.rgbModifiedImage=commandArguments[1];
  }

  public ColorTransformationController(String rgbExistingImage, String rgbModifiedImage){
    this.rgbExistingImage=rgbExistingImage;
    this.rgbModifiedImage=rgbModifiedImage;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface existingImage=cachedImage.get(rgbExistingImage);

    if(existingImage==null){
      return;
    }
    RGBImageInterface rgbImage=existingImage.sepiaImage();
    cachedImage.put(rgbModifiedImage,rgbImage);
  }
}
