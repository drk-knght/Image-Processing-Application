package Model.ImageOperations.MultiOUT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Enums.ColorMapping;
import Model.RGBImageInterface;

public class SplitChannelImage implements MultipleOperationImages {

  @Override
  public List<RGBImageInterface> operation(RGBImageInterface rgbImages) throws IllegalArgumentException {
    if(rgbImages==null){
      throw new IllegalArgumentException("Image passed for the split operation on image is not as expected, check again. Aborting!!");
    }
    ArrayList<RGBImageInterface> channelImages = new ArrayList<RGBImageInterface>();
    for (int i = 0; i < ColorMapping.values().length; i++) {
      RGBImageInterface rgbImageChannel = rgbImages.getSingleComponentImage(i);
      channelImages.add(rgbImageChannel);
    }
    return channelImages;
  }
}
