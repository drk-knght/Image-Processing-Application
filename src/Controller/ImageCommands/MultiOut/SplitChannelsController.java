package Controller.ImageCommands.MultiOut;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;
import Model.RGBImageInterface;

public class SplitChannelsController implements RGBImageCommandInterface {

  private final String existingImage;

  private final String[] channelImages;

  public SplitChannelsController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=4){
      throw new IllegalArgumentException("The number of arguments expected for RGB split operation does not match with the passed command.");
    }
    this.existingImage=commandArguments[0];
    channelImages= Arrays.copyOfRange(commandArguments,1,commandArguments.length);
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface rgbExistingImage=cachedImage.get(existingImage);
    if(rgbExistingImage==null){
      System.out.println("The image name: "+this.existingImage+" does not exists in the cached memory.");
    }
    List<RGBImageInterface> rgbImages=rgbExistingImage.splitImageComponents();
    for(int i=0;i<rgbImages.size();i++){
      String imageName=channelImages[i];
      cachedImage.put(imageName, rgbImages.get(i));
    }
  }
}
