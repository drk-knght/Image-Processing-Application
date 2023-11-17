package controller.imagecommands.iocommands;

import java.io.IOException;
import java.util.Map;

import controller.filehandling.reader.FileReader;
import controller.filehandling.reader.InputReaderInterface;
import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImage;
import model.RGBImageInterface;

/**
 * LoadController is a class implementing RGBImageCommandInterface.
 * It facilitates the loading of RGB images into a processing app.
 * It features two constructors for initialization.
 * It also has an execute method to handle the image loading process.
 * The execute method checks for null lookup tables, updates existing images in the cache.
 * It also adds new RGB images based on provided parameters.
 */

public class LoadCommand implements RGBImageCommandInterface {

  private final String imageName;

  private final String imageFilePath;

  /**
   * Constructor takes the cmd args as an input and assign the file path and names to the fields.
   *
   * @param commandArguments Array of strings containing the information about path and file name.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public LoadCommand(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 2) {
      throw new IllegalArgumentException("Wrong number of parameters passed for load command.\n");
    }
    imageFilePath = commandArguments[0];
    imageName = commandArguments[1];
  }


  /**
   * Method represents the load image operations which needs to be performed by the application.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws IllegalArgumentException Throws exception if the input is of null type.
   * @throws IOException              throws exception if there is an error when reading files
   *                                  during operation.
   */
  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage)
          throws IllegalArgumentException, IOException {
    if (cachedImage == null) {
      throw new IllegalArgumentException("The lookup table passed for "
              + "the image processing app does not exists.");
    }
    RGBImageInterface rgbImage = cachedImage.get(imageName);
    if (rgbImage != null) {
      System.out.println("Changing the existing image present in Cache.");
    }
    InputReaderInterface reader = new FileReader(imageFilePath);
    rgbImage = new RGBImage(reader.read());
    cachedImage.put(imageName, rgbImage);
  }
}
