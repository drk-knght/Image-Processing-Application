package Controller.ImageCommands.FileHandling;

import java.io.IOException;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;
import Model.RGBImage;
import Model.RGBImageInterface;

public class LoadController implements RGBImageCommandInterface {

  private final String imageName;

  private final String imageFilePath;

  public LoadController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=2){
      throw new IllegalArgumentException("Wrong number of parameters passed for load command.");
    }
    imageFilePath=commandArguments[0];
    imageName=commandArguments[1];
  }

  public LoadController(String imageName, String imageFilePath){
    this.imageName=imageName;
    this.imageFilePath=imageFilePath;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface rgbImage=cachedImage.get(imageName);
    if(rgbImage!=null){
      System.out.println("Changing the existing image present in Cache.");
    }
    rgbImage=new RGBImage(imageFilePath);
    cachedImage.put(imageName,rgbImage);
  }
}
