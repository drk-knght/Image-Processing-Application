package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public class BrightnessController implements RGBImageCommandInterface{

  private int deltaChangePixelValue;

  private String rgbExistingImage;

  private String rgbModifiedImage;

  public BrightnessController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("Wrong number of parameters passed for command array.");
    }
    deltaChangePixelValue=Integer.parseInt(commandArguments[0]);
    rgbExistingImage=commandArguments[1];
    rgbModifiedImage=commandArguments[2];
  }

  public BrightnessController(int deltaChangePixelValue, String rgbExistingImage,String rgbModifiedImage){
    this.deltaChangePixelValue=deltaChangePixelValue;
    this.rgbExistingImage=rgbExistingImage;
    this.rgbModifiedImage=rgbModifiedImage;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface existingImage=cachedImage.get(rgbExistingImage);
    if(existingImage==null){
      return;
    }
    RGBImageInterface modifiedImage=existingImage.changeBrightness(deltaChangePixelValue);
    cachedImage.put(rgbModifiedImage,modifiedImage);
  }
}
