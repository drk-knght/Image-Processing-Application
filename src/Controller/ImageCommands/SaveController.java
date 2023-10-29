package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public class SaveController implements RGBImageCommandInterface{

  String imageName;

  String imageFilePath;

  public SaveController(String [] commandArguments){
    imageFilePath=commandArguments[0];
    imageName=commandArguments[1];
  }

  public SaveController(String imageName, String imageFilePath){
    this.imageName=imageName;
    this.imageFilePath=imageFilePath;
  }


  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface rgbCachedImage=cachedImage.get(this.imageName);
    if(rgbCachedImage==null){
      System.out.println("No such images named: "+this.imageName+" exists in the memory");
    }
    rgbCachedImage.saveImage(imageFilePath,imageName);
  }
}
