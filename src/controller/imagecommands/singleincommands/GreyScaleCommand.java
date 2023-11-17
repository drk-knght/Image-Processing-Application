package controller.imagecommands.singleincommands;

import model.RGBImageInterface;

/**
 * The class represents the greyscale image controller command of the main controller.
 * The command takes an array of strings as input and sends the command to grey method of the img.
 * It also takes the split % if the user want to compare the original and the updated image.
 */
public class GreyScaleCommand extends AbstractCommandSingleIP {

  private final double splitPercentage;

  /**
   * Constructor takes the cmd args as an input and assign the file names to the fields.
   *
   * @param commandArguments Array of strings containing the information about the file names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public GreyScaleCommand(String[] commandArguments) throws IllegalArgumentException {
    super(commandArguments);
    if (commandArguments.length == 3) {
      this.splitPercentage = 100;
    } else {
      this.splitPercentage = Double.parseDouble(commandArguments[4]);
    }
  }


  /**
   * The command calls the greyscale method from the model class to get new greyscale image.
   *
   * @param existingImage            Image presently in the memory of the application.
   * @param imageOperationValueIndex Operation index that needs to be performed for single input.
   * @return A new image from the model methods after the operation passed from the controller.
   */
  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage,
                                                   int imageOperationValueIndex) {
    return existingImage.greyScaleImage(imageOperationValueIndex, splitPercentage);
  }


}
