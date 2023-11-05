package model.imageoperations.multiin;

import java.util.List;

import model.enums.ColorMapping;
import model.RGBImage;
import model.RGBImageInterface;

/**
 * This class represents the combine operation on the images of different channels of same depth.
 * In this operation the class takes lists of images of different color-greyscale.
 * It returns back the combination of all the different greyscale versions of Red, Green, Blue.
 */
public class CombineChannelImage implements MultipleImagesSingleOperation {

  /**
   * The method takes list of images as input and performs an operation on it to get a final result.
   *
   * @param rgbImages Operands on which an image operation needs to be performed to get an image.
   * @return An image containing the result of the operation on the list of existing images.
   * @throws IllegalArgumentException When the arguments passed during the operations are invalid.
   */
  @Override
  public RGBImageInterface operation(List<RGBImageInterface> rgbImages)
          throws IllegalArgumentException {
    if (rgbImages == null) {
      throw new IllegalArgumentException("Image passed for the combine operation "
              + "on image is not as expected, check again. Aborting!!");
    }
    checkValidDimImages(rgbImages);
    int[][][] pixelMatrix = rgbImages.get(0).getPixel();
    int height = rgbImages.get(0).getImageHeight();
    int width = rgbImages.get(0).getImageWidth();
    for (int color = 1; color < ColorMapping.values().length; color++) {
      int[][][] rgbImagePixelMatrix = rgbImages.get(color).getPixel();
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          pixelMatrix[i][j][color] = rgbImagePixelMatrix[i][j][color];
        }
      }
    }
    return new RGBImage(pixelMatrix);
  }

  private void checkValidDimImages(List<RGBImageInterface> rgbImages) {
    int height = rgbImages.get(0).getImageHeight();
    int width = rgbImages.get(0).getImageWidth();
    checkIllegalArguments(height, width);
    for (int i = 1; i < rgbImages.size(); i++) {
      RGBImageInterface rgbImage = rgbImages.get(i);
      checkIllegalArguments(rgbImage.getImageHeight(), rgbImage.getImageWidth());
      if (height != rgbImage.getImageHeight() || width != rgbImage.getImageWidth()) {
        throw new IllegalArgumentException("Images dimensions in the list doesn't match.");
      }
    }
  }

  private void checkIllegalArguments(int height, int width) {
    if (height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Image passed for channel combination operations "
              + "does not have valid dimensions, check again. Aborting!!");
    }
  }
}
