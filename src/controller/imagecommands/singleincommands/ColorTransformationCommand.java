package controller.imagecommands.singleincommands;

import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;

import model.RGBImageInterface;

/**
 * The class represents the color transformation controller command of the main controller.
 * The command takes an array of strings as input and sends the command to change the sepia of img.
 * It also takes the split % if the user want to compare the original and the updated image.
 */
public class ColorTransformationCommand implements RGBImageCommandInterface {

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  private final double splitPercentage;

  /**
   * Constructor takes the cmd args as an input and assign the file names to the fields.
   *
   * @param commandArguments Array of strings containing the information about the file names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public ColorTransformationCommand(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 2 && commandArguments.length != 4) {
      throw new IllegalArgumentException("The number of parameters does not match "
              + "with the expected number of parameters for the passed operation.\n");
    }
    this.rgbExistingImage = commandArguments[0];
    this.rgbModifiedImage = commandArguments[1];
    if (commandArguments.length == 2) {
      splitPercentage = 100;
    } else {
      splitPercentage = Double.parseDouble(commandArguments[3]);
    }
  }


  /**
   * The command calls the applying sepia method from the model class to get new image.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws IllegalArgumentException Throws exception if the input is of null type.
   */
  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IllegalArgumentException {
    if (cachedImage == null) {
      throw new IllegalArgumentException("The lookup table passed for "
              + "the image processing app does not exists.\n");
    }
    RGBImageInterface existingImage = cachedImage.get(rgbExistingImage);

    if (existingImage == null) {
      return;
    }
    RGBImageInterface rgbImage = existingImage.sepiaImage(this.splitPercentage);
    cachedImage.put(rgbModifiedImage, rgbImage);
  }
}
