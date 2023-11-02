package Model.ImageOperations.MultiOUT;

import java.util.ArrayList;
import java.util.List;

import Model.Enums.ColorMapping;
import Model.RGBImageInterface;

/**
 * This class represents the split operation in an image processing application.
 * It takes an image object as an input and divides it into different channel images of same depth.
 * The single image is retained in the memory along with the newly generated channeled images.
 */
public class SplitChannelImage implements MultipleOperationImages {

  /**
   * It takes an image as an input and apply some operation on it to divide it into several types.
   * @param rgbImages An image accessible by RGBImageInterface on which the operation is to be done.
   * @return List of images which are generated after applying image processing operation.
   * @throws IllegalArgumentException Exception is thrown If the image passed is not a valid input.
   */
  @Override
  public List<RGBImageInterface> operation(RGBImageInterface rgbImages) throws IllegalArgumentException {
    if(rgbImages==null || rgbImages.getImageHeight()<=0 || rgbImages.getImageWidth()<=0){
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
