package Controller.ImageCommands.FileHandling;

import java.io.IOException;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;
import Model.RGBImageInterface;

/**
 * This class represents the saving image part of the controller.
 * The class takes the image name and saves it to that particular file location.
 * It throws exception if file path is not accessible or null.
 */
public class SaveController implements RGBImageCommandInterface {

  private final String imageName;

  private final String imageFilePath;

  /**
   * Constructor takes the cmd args as an input and assign the file path and names to the fields.
   * @param commandArguments Array of strings containing the information about path and file name.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
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


  /**
   * Method represents the save image operations which needs to be performed by the application.
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws NullPointerException Throws exception if the input is of null type.
   * @throws IOException throws exception if there is an error when reading files during operation.
   */
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
