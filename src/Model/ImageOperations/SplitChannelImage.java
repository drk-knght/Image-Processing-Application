package Model.ImageOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.Enums.ColorMapping;
import Model.RGBImageInterface;

public class SplitChannelImage implements MultipleImagesOPOperation {

  @Override
  public List<RGBImageInterface> operation(RGBImageInterface rgbImages) throws IOException {
    ArrayList<RGBImageInterface> channelImages=new ArrayList<RGBImageInterface>();
    for(int i=0;i<ColorMapping.values().length;i++){
      RGBImageInterface rgbImageChannel=rgbImages.getSingleComponentImage(i);
      channelImages.add(rgbImageChannel);
    }
    return channelImages;
  }
}
