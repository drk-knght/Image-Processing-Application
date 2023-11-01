package Controller.ImageCommands.FileHandling;

import java.io.IOException;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;
import Model.RGBImageInterface;

public class SaveController implements RGBImageCommandInterface {

  private final String imageName;

  private final String imageFilePath;

  public SaveController(String[] commandArguments) {
    if (commandArguments.length != 2) {
      throw new IllegalArgumentException("Number of parameters passed for the save command " +
              "does not match as per the syntax.");
    }
    imageFilePath = commandArguments[0];
    imageName = commandArguments[1];
  }

  public SaveController(String imageName, String imageFilePath) {
    this.imageName = imageName;
    this.imageFilePath = imageFilePath;
  }


  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws NullPointerException, IOException {
    if(cachedImage==null){
      throw new NullPointerException("The lookup table passed for the image processing app does not exists.");
    }
    RGBImageInterface rgbCachedImage = cachedImage.get(this.imageName);
    if (rgbCachedImage == null) {
      System.out.println("No such images named: " + this.imageName + " exists in the memory");
      return;
    }
    rgbCachedImage.saveImage(imageFilePath);
  }
}
