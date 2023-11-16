package controller.imagecommands.multioutcommand;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImageInterface;

/**
 * This class represents the splitting command operation on an existing image in memory.
 * The command utility class redirects the call to the proper method of the image class method.
 * Adds the new image to the existing set of collection of images in the memory.
 */
public class SplitChannelsCommand implements RGBImageCommandInterface {

  private final String existingImage;

  private final String[] channelImages;

  /**
   * Constructor takes the cmd args as an input and assign the file names to the fields.
   *
   * @param commandArguments Array of strings containing the information about the file names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public SplitChannelsCommand(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 4) {
      throw new IllegalArgumentException("The number of arguments expected for "
              + "RGB split operation does not match with the passed command.\n");
    }
    this.existingImage = commandArguments[0];
    channelImages = Arrays.copyOfRange(commandArguments, 1, commandArguments.length);
  }

  /**
   * Method represents the split channel image operations which needs to be performed by the app.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws NullPointerException Throws exception if the input is of null type.
   * @throws IOException          throws exception if there is an error when reading files for op.
   */
  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage)
          throws IllegalArgumentException, IOException {
    if (cachedImage == null) {
      throw new IllegalArgumentException("The lookup table passed for the "
              + "image processing app does not exists.\n");
    }
    RGBImageInterface rgbExistingImage = cachedImage.get(existingImage);
    if (rgbExistingImage == null) {
      throw new IllegalArgumentException("The image name: "
              + this.existingImage + " does not exists in the cached memory.\n");
    }
    List<RGBImageInterface> rgbImages = rgbExistingImage.splitImageComponents();
    for (int i = 0; i < rgbImages.size(); i++) {
      String imageName = channelImages[i];
      cachedImage.put(imageName, rgbImages.get(i));
    }
  }
}
