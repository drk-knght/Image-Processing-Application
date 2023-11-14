package controller.imagecommands.singleincommands;

import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImageInterface;

public class CompressCommand implements RGBImageCommandInterface {

  private final double compressPercentage;

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  /**
   * Constructor takes the cmd args as an input and assign the image names to the fields
   * It assigns the compress percentage of the image that is used for the compress operation.
   *
   * @param commandArguments Array of strings containing the information about the image names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public CompressCommand(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("The number of parameters does not match "
              + "with the expected number of parameters for the passed operation.");
    }
    this.compressPercentage=Double.parseDouble(commandArguments[0]);
    this.rgbExistingImage=commandArguments[1];
    this.rgbModifiedImage=commandArguments[2];
  }

  /**
   * The command calls the image compress method from the model class to get new pixelated image.
   * The new image contains the lossy compressed image data that was generated inside the model.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws IllegalArgumentException Throws exception if the input is of null type.
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
    RGBImageInterface rgbImage = existingImage.compressImage(compressPercentage);
    cachedImage.put(rgbModifiedImage, rgbImage);
  }
}
