package Model.ImageOperations.MultiIN;

import java.util.List;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class CombineChannelImage implements MultipleImagesSingleOperation {

  @Override
  public RGBImageInterface operation(List<RGBImageInterface> rgbImages) throws IllegalArgumentException{
    if(rgbImages==null){
      throw new IllegalArgumentException("Image passed for the combine operation on image is not as expected, check again. Aborting!!");
    }
    checkValidDimImages(rgbImages);
    int[][][] pixelMatrix = rgbImages.get(0).getPixel();
    int height = rgbImages.get(0).getImageHeight();
    int width = rgbImages.get(0).getImageWidth();
    for (int color = 1; color < ColorMapping.values().length; color++) {
      int[][][] rgbImagePixelMatrix = rgbImages.get(color).getPixel();
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
//          pixelMatrix[i][j][color]=rgbImages.get(color).getPixel()[i][j][color];
          pixelMatrix[i][j][color] = rgbImagePixelMatrix[i][j][color];
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }

  private void checkValidDimImages(List<RGBImageInterface> rgbImages){
    int height=rgbImages.get(0).getImageHeight();
    int width=rgbImages.get(0).getImageWidth();
    checkIllegalArguments(height, width);
    for(int i=1;i< rgbImages.size();i++){
      RGBImageInterface rgbImage=rgbImages.get(i);
      checkIllegalArguments(rgbImage.getImageHeight(), rgbImage.getImageWidth());
      if(height!=rgbImage.getImageHeight() || width!=rgbImage.getImageWidth()){
        throw new IllegalArgumentException("Images dimensions in the list doesn't match.");
      }
    }
  }

  private void checkIllegalArguments(int height, int width){
    if(height<=0 || width<=0){
      throw new IllegalArgumentException("Image passed for channel combination operations does not have valid dimensions, check again. Aborting!!");
    }
  }
}
