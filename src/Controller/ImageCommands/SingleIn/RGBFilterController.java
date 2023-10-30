package Controller.ImageCommands.SingleIn;

import java.io.IOException;

import Model.RGBImageInterface;

public class RGBFilterController extends AbstractCommandSingleIPController{

  public RGBFilterController(String [] commandArguments) throws IllegalArgumentException{
    super(commandArguments);
  }

  public RGBFilterController(int rgbIndexValue, String existingImage, String modifiedImage){
    super(rgbIndexValue,existingImage,modifiedImage);
  }

  @Override
  protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage, int imageOperationValueIndex) throws IOException {
    return existingImage.getSingleComponentImage(imageOperationValueIndex);
  }


}
