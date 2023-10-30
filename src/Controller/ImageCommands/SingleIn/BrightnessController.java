package Controller.ImageCommands.SingleIn;

import java.io.IOException;

import Model.RGBImageInterface;

public class BrightnessController extends AbstractCommandSingleIPController{


  public BrightnessController(String [] commandArguments) throws IllegalArgumentException{
    super(commandArguments);
  }

  public BrightnessController(int deltaChangePixelValue, String rgbExistingImage,String rgbModifiedImage){
    super(deltaChangePixelValue,rgbExistingImage,rgbModifiedImage);
  }

  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage, int imageOperationValueIndex) throws IOException {
    return existingImage.changeBrightness(imageOperationValueIndex);
  }


}
