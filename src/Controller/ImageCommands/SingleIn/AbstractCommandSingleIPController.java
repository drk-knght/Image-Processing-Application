package Controller.ImageCommands.SingleIn;

import java.io.IOException;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;
import Model.RGBImageInterface;

public abstract class AbstractCommandSingleIPController implements RGBImageCommandInterface {

  private final int imageOperationValueIndex;
  private final String rgbExistingImage;

  private final String rgbModifiedImage;

  AbstractCommandSingleIPController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=3){
      throw new IllegalArgumentException("The number of parameters does not match " +
              "with the expected number of parameters for the passed operation.");
    }
    this.imageOperationValueIndex=Integer.parseInt(commandArguments[0]);
    this.rgbExistingImage=commandArguments[1];
    this.rgbModifiedImage=commandArguments[2];
//    this.imageOperationValueIndex=Integer.parseInt(commandArguments[2]);
  }

  AbstractCommandSingleIPController(int imageOperationValueIndex, String rgbExistingImage, String rgbModifiedImage){
    this.imageOperationValueIndex=imageOperationValueIndex;
    this.rgbExistingImage=rgbExistingImage;
    this.rgbModifiedImage=rgbModifiedImage;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws IOException {
    RGBImageInterface existingImage=cachedImage.get(rgbExistingImage);

    if(existingImage==null){
      return;
    }

    RGBImageInterface rgbImage=defineImageOperation(existingImage,imageOperationValueIndex);
    cachedImage.put(rgbModifiedImage,rgbImage);
  }

  abstract protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage,
                                                            int imageOperationValueIndex) throws IOException;
}
