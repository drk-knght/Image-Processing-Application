package controller.imagecommands.singleincommands;


import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImageInterface;

/**
 * The class represents the image command operation for plotting line-graph of r,g,b values.
 * The r,g,b values for an image is plotted by model from this command class.
 * The command takes an array of strings as input and sends the command to plot gram of the image.
 */
public class HistogramCommand implements RGBImageCommandInterface {

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  /**
   * Constructor takes the cmd args as an input and assign the file names to the fields.
   *
   * @param commandArguments Array of strings containing the information about the image names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public HistogramCommand(String[] commandArguments) throws IllegalArgumentException{
    if (commandArguments.length != 2) {
      throw new IllegalArgumentException("The number of parameters does not match "
              + "with the expected number of parameters for the passed operation.\n");
    }
    this.rgbExistingImage = commandArguments[0];
    this.rgbModifiedImage = commandArguments[1];
  }

  /**
   * The command calls the histogram method from the model class to get new image.
   * The new image contains the line graph for all the pixels and r,g,b channels of present image.
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
    RGBImageInterface rgbImage = existingImage.getPixelHistogram();
    cachedImage.put(rgbModifiedImage, rgbImage);
  }
}
