package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImage;
import Model.RGBImageInterface;

public class LoadController implements RGBImageCommandInterface{

  private String imageName;

  private String imageFilePath;

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
    if(imageName!=null){
      System.out.println("Changing the existing image present in Cache.");
    }
    rgbImage=new RGBImage(imageFilePath);
    cachedImage.put(imageName,rgbImage);
  }
}
