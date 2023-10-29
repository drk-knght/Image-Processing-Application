package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public class FlipImageController implements RGBImageCommandInterface{

  private int axisValue;

  private String rgbExistingImage;

  private String rgbModifiedImage;


  FlipImageController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("Wrong number of parameters passed to the flip command");
    }
    this.axisValue=Integer.parseInt(commandArguments[0]);
    this.rgbExistingImage=commandArguments[1];
    this.rgbModifiedImage=commandArguments[2];
  }

  FlipImageController(int axisValue, String rgbExistingImage, String rgbModifiedImage){
    this.axisValue=axisValue;
    this.rgbExistingImage=rgbExistingImage;
    this.rgbModifiedImage=rgbModifiedImage;
  }


  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface existingImage=cachedImage.get(rgbExistingImage);
    if(existingImage==null){
      return;
    }
    RGBImageInterface modifiedImage=existingImage.flipImage(axisValue);
    cachedImage.put(rgbModifiedImage,modifiedImage);
  }
}
