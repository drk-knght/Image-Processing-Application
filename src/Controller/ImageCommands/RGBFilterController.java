package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public class RGBFilterController implements RGBImageCommandInterface{

  private int rgbIndexValue;

  private String existingImage;

  private String modifiedImage;

  public RGBFilterController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("Wrong number of parameters passed to the RGB filter command");
    }
    this.rgbIndexValue=Integer.parseInt(commandArguments[0]);
    this.existingImage=commandArguments[1];
    this.modifiedImage=commandArguments[2];
  }

  public RGBFilterController(int rgbIndexValue, String existingImage, String modifiedImage){
    this.existingImage=existingImage;
    this.modifiedImage=modifiedImage;
    this.rgbIndexValue=rgbIndexValue;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface rgbImage=cachedImage.get(existingImage);
    if(rgbImage==null){
      return;
    }
    RGBImageInterface monochromeImage=rgbImage.getSingleComponentImage(rgbIndexValue);
    cachedImage.put(modifiedImage,monochromeImage);
  }
}
