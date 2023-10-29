package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public class SharpnessController implements RGBImageCommandInterface{

  private String existingImage;

  private String modifiedImage;

  private int sharpnessValue;

  public SharpnessController(String [] commandArguments){
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("Number of parameters for Sharpness are not as expected");
    }
    sharpnessValue=Integer.parseInt(commandArguments[0]);
    existingImage=commandArguments[1];
    modifiedImage=commandArguments[2];
  }

  public SharpnessController(int sharpnessValue,String existingImage, String modifiedImage){
    this.sharpnessValue=sharpnessValue;
    this.existingImage=existingImage;
    this.modifiedImage=modifiedImage;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface rgbImage=cachedImage.get(this.existingImage);
    if(rgbImage==null){
      return;
    }
    // Pass the sharpness value mapping from this to rgbImage.sharpen() to get a new image object.
    RGBImageInterface sharpenedImage=rgbImage.changeSharpness(sharpnessValue);
    cachedImage.put(modifiedImage,sharpenedImage);
  }

}
