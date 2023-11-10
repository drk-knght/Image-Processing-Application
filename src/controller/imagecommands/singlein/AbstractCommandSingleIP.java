package controller.imagecommands.singlein;

import java.io.IOException;
import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImageInterface;

/**
 * This abstract class represents an abstract controller which handles single input related query.
 * The class takes the operation index, existing and new image name to perform the operation.
 */
public abstract class AbstractCommandSingleIP implements RGBImageCommandInterface {

  private final int imageOperationValueIndex;
  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  AbstractCommandSingleIP(String[] commandArguments) throws IllegalArgumentException {
    if (commandArguments.length != 3) {
      throw new IllegalArgumentException("The number of parameters does not match "
              + "with the expected number of parameters for the passed operation.");
    }
    this.imageOperationValueIndex = Integer.parseInt(commandArguments[0]);
    this.rgbExistingImage = commandArguments[1];
    this.rgbModifiedImage = commandArguments[2];
  }


  /**
   * Method represents the single input commands on image operations to be performed by the app.
   *
   * @param cachedImage The set of images presently in use in the memory of this application.
   * @throws NullPointerException Throws exception if the input is of null type.
   * @throws IOException          throws exception if there is an error when reading files for op.
   */
  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage)
          throws IllegalArgumentException, IOException {
    if (cachedImage == null) {
      throw new IllegalArgumentException("The lookup table passed "
              + "for the image processing app does not exists.");
    }
    RGBImageInterface existingImage = cachedImage.get(rgbExistingImage);

    if (existingImage == null) {
      return;
    }

    RGBImageInterface rgbImage = defineImageOperation(existingImage, imageOperationValueIndex);
    cachedImage.put(rgbModifiedImage, rgbImage);
  }

  /**
   * The method represent the image controller operation for the single input param.
   * This is override by the respective controller utilities as per the required functionality.
   *
   * @param existingImage            Image presently in the memory of the application.
   * @param imageOperationValueIndex Operation index that needs to be performed for single input.
   * @return A new image from the model methods after the operation passed from the controller.
   * @throws IOException Throws exception if it's not able to perform the read and write operation.
   */
  abstract protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage,
                                                            int imageOperationValueIndex)
          throws IOException;
}
