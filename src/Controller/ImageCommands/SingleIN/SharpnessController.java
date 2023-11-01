package Controller.ImageCommands.SingleIN;

import java.io.IOException;

import Model.RGBImageInterface;

public class SharpnessController extends AbstractCommandSingleIPController {

  public SharpnessController(String[] commandArguments) {
    super(commandArguments);
  }

  public SharpnessController(int sharpnessValue, String existingImage, String modifiedImage) {
    super(sharpnessValue, existingImage, modifiedImage);
  }

  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage, int imageOperationValueIndex) throws IOException {
    return existingImage.changeSharpness(imageOperationValueIndex);
  }


}
