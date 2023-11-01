package Controller.ImageCommands.SingleIN;

import java.io.IOException;

import Model.RGBImageInterface;

public class GreyScaleController extends AbstractCommandSingleIPController {

  public GreyScaleController(String[] commandArguments) throws IllegalArgumentException {
    super(commandArguments);
  }

  public GreyScaleController(int greyIndexValue, String existingImage, String modifiedImage) {
    super(greyIndexValue, existingImage, modifiedImage);
  }

  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage, int imageOperationValueIndex) throws IOException {
    return existingImage.greyScaleImage(imageOperationValueIndex);
  }


}
