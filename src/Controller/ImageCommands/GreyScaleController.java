package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public class GreyScaleController implements RGBImageCommandInterface{

  private final int greyIndexValue;

  private final String existingImage;

  private final String modifiedImage;

  public GreyScaleController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("Wrong number of parameters passed to the grey filter command");
    }
    this.greyIndexValue=Integer.parseInt(commandArguments[0]);
    this.existingImage=commandArguments[1];
    this.modifiedImage=commandArguments[2];
  }

  public GreyScaleController(int greyIndexValue, String existingImage, String modifiedImage){
    this.existingImage=existingImage;
    this.modifiedImage=modifiedImage;
    this.greyIndexValue=greyIndexValue;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface rgbImage=cachedImage.get(existingImage);
    if(rgbImage==null){
      return;
    }
    RGBImageInterface greyScaleImage=rgbImage.greyScaleImage(greyIndexValue);
    cachedImage.put(modifiedImage,greyScaleImage);
  }
}
