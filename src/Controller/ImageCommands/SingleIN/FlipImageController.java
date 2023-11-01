package Controller.ImageCommands.SingleIN;

import java.io.IOException;

import Model.RGBImageInterface;

public class FlipImageController extends AbstractCommandSingleIPController {

  public FlipImageController(String[] commandArguments) throws IllegalArgumentException {
    super(commandArguments);
  }

  public FlipImageController(int axisValue, String rgbExistingImage, String rgbModifiedImage) {
    super(axisValue, rgbExistingImage, rgbModifiedImage);
  }


  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage, int imageOperationValueIndex) throws IOException {
    return existingImage.flipImage(imageOperationValueIndex);
  }


}
