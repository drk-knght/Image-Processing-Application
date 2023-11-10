package controller.imagecommands.multiin;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImageInterface;

/**
 * This class represents the combine channels controller part of the image operation.
 * Takes the new image name and set of existing images and redirects the command to model combine.
 */
public class CombineChannelsCommand implements RGBImageCommandInterface {

  private final String[] channelImages;

  private final String modifiedImage;

  /**
   * Constructor takes cmd args needed for image names that are currently in use in the memory.
   * Then it assigns all the args to the respective fields of the present class objects.
   *
   * @param commandArguments Array of strings representing the image names of both new and existing.
   * @throws IllegalArgumentException Throws exception if the file is of not correct length.
   */
  public CombineChannelsCommand(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 4) {
      throw new IllegalArgumentException("The number of arguments expected for RGB combine "
              + "operation does not match with the passed command.");
    }
    this.modifiedImage = commandArguments[0];
    channelImages = Arrays.copyOfRange(commandArguments, 1, commandArguments.length);
  }


  /**
   * Method represents the combine images operation which needs to be performed by the application.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws NullPointerException Throws exception if the input is of null type.
   */
  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IllegalArgumentException {
    RGBImageInterface firstImage = cachedImage.get(this.channelImages[0]);

    if (firstImage == null) {
      throw new IllegalArgumentException("One of the file name passed: "
              + this.channelImages[0] + " does not exists in the cached Image");
    }

    ArrayList<RGBImageInterface> arrayList = new ArrayList<>();

    for (int i = 1; i < channelImages.length; i++) {
      arrayList.add(cachedImage.get(channelImages[i]));
    }

    RGBImageInterface combinedImage = firstImage.combineImageComponents(arrayList);

    cachedImage.put(modifiedImage, combinedImage);
  }
}
