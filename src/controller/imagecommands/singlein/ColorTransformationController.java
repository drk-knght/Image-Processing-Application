package controller.imagecommands.singlein;

import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;

import model.RGBImageInterface;

/**
 * The class represents the color transformation controller command of the main controller.
 * The command takes an array of strings as input and sends the command to change the sepia of img.
 */
public class ColorTransformationController implements RGBImageCommandInterface {

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  /**
   * Constructor takes the cmd args as an input and assign the file names to the fields.
   *
   * @param commandArguments Array of strings containing the information about the file names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public ColorTransformationController(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 2) {
      throw new IllegalArgumentException("The number of parameters does not match "
              + "with the expected number of parameters for the passed operation.");
    }
    this.rgbExistingImage = commandArguments[0];
    this.rgbModifiedImage = commandArguments[1];
  }


  /**
   * The command calls the applying sepia method from the model class to get new image.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws NullPointerException Throws exception if the input is of null type.
   */
  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IllegalArgumentException {
    if (cachedImage == null) {
      throw new IllegalArgumentException("The lookup table passed for "
              + "the image processing app does not exists.");
    }
    RGBImageInterface existingImage = cachedImage.get(rgbExistingImage);

    if (existingImage == null) {
      return;
    }
    RGBImageInterface rgbImage = existingImage.sepiaImage();
    cachedImage.put(rgbModifiedImage, rgbImage);
  }
}
