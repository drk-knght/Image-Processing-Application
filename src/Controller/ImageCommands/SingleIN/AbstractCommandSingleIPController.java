package Controller.ImageCommands.SingleIN;

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
  }

  AbstractCommandSingleIPController(int imageOperationValueIndex, String rgbExistingImage, String rgbModifiedImage) throws IllegalArgumentException{
    if(imageOperationValueIndex<0 || rgbExistingImage==null || rgbModifiedImage==null){
      throw new IllegalArgumentException("Parameters passed for single input controller can not be null and negative.");
    }
    this.imageOperationValueIndex=imageOperationValueIndex;
    this.rgbExistingImage=rgbExistingImage;
    this.rgbModifiedImage=rgbModifiedImage;
  }

  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws NullPointerException,IOException {
    if(cachedImage==null){
      throw new NullPointerException("The lookup table passed for the image processing app does not exists.");
    }
    RGBImageInterface existingImage=cachedImage.get(rgbExistingImage);

    if(existingImage==null){
      return;
    }

    RGBImageInterface rgbImage=defineImageOperation(existingImage, imageOperationValueIndex);
    cachedImage.put(rgbModifiedImage,rgbImage);
  }

  abstract protected RGBImageInterface defineImageOperation(RGBImageInterface existingImage,
                                                            int imageOperationValueIndex) throws IOException;
}
