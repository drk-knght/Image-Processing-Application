package Model.ImageOperations;

import java.util.List;

import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

public class CombineChannelImage implements MultipleImagesIPOperation {

  @Override
  public RGBImageInterface operation(List<RGBImageInterface> rgbImages) {

    int [][][]pixelMatrix=rgbImages.get(0).getPixel();
    int height=rgbImages.get(0).getImageHeight();
    int width=rgbImages.get(0).getImageWidth();
    for(int color=1;color<ColorMapping.values().length;color++){
      for(int i=0;i<height;i++){
        for(int j=0;j<width;j++){
          pixelMatrix[i][j][color]=rgbImages.get(color).getPixel()[i][j][color];
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }
}
