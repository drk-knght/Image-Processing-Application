package controller.imagecommands.singleincommands;

import java.io.IOException;
import java.util.Map;

import controller.imagecommands.RGBImageCommandInterface;
import model.RGBImageInterface;

public class LevelAdjustmentCommand implements RGBImageCommandInterface {

  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  private final double shadowPoint;

  private final double highlightPoint;

  private final double midPoint;

  private final double splitPercentage;

  /**
   * Constructor takes the cmd args as an input and assign the image names to the fields
   * It assigns the split percentage of the image that is used for the split operation.
   * The constructor also checks and assigns the b, m and w points for the level adjustment curve.
   *
   * @param commandArguments Array of strings containing the information about the image names.
   * @throws IllegalArgumentException Throws exception if the string array is not of required len.
   */
  public LevelAdjustmentCommand(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=5 && commandArguments.length!=7){
      throw new IllegalArgumentException("The number of parameters does not match "
              + "with the expected number of parameters for the passed operation.");
    }
    this.shadowPoint=Double.parseDouble(commandArguments[0]);
    this.midPoint=Double.parseDouble(commandArguments[1]);
    this.highlightPoint=Double.parseDouble(commandArguments[2]);
    this.rgbExistingImage=commandArguments[3];
    this.rgbModifiedImage=commandArguments[4];
    if(commandArguments.length==5){
      this.splitPercentage=100;
    }
    else {
      this.splitPercentage=Double.parseDouble(commandArguments[6]);
    }
  }

  /**
   * The command calls the levels adjustment method from the model class to get new adjusted image.
   * The new image contains the levels modified for all the r,g,b pixels channels of present image.
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

    RGBImageInterface rgbImage = existingImage.levelsAdjustment(shadowPoint,midPoint,highlightPoint,splitPercentage);
    cachedImage.put(rgbModifiedImage, rgbImage);
  }
}
