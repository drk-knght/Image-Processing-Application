package controller.imagecommands.singlein;

import java.io.IOException;

import model.RGBImageInterface;

/**
 * The class represents the greyscale image controller command of the main controller.
 * The command takes an array of strings as input and sends the command to flip method of the img.
 */
public class GreyScaleCommand extends AbstractCommandSingleIP {

  /**
   * Constructor takes the cmd args as an input and assign the file names to the fields.
   *
   * @param commandArguments Array of strings containing the information about the file names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public GreyScaleCommand(String[] commandArguments) throws IllegalArgumentException {
    super(commandArguments);
  }


  /**
   * The command calls the greyscale method from the model class to get new greyscale image.
   *
   * @param existingImage            Image presently in the memory of the application.
   * @param imageOperationValueIndex Operation index that needs to be performed for single input.
   * @return A new image from the model methods after the operation passed from the controller.
   * @throws IOException Throws exception if it's not able to perform the read and write operation.
   */
  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage,
                                                 int imageOperationValueIndex) throws IOException {
    return existingImage.greyScaleImage(imageOperationValueIndex);
  }


}
