package model.imageoperations.singlein;

import model.RGBImage;
import model.RGBImageInterface;
import model.enums.ColorMapping;

/**
 * This class represents a buffer/ intermediate between main model class and image operations.
 * The class performs the split preview operation on the existing image in the cache.
 * It takes the split percentage and the image operation object to perform the action.
 * The preview function works with any image operation that can be accessed using the img interface.
 */
public class SplitBuffer implements ImageOperation {

  private final double splitPercentage;

  private final ImageOperation imageOperation;

  /**
   * Constructor of the class assigns itself the split percentage and the image operation object.
   *
   * @param splitPercentage Double representing the % on which the img operation needs to be done.
   * @param imageOperation  The image operation which needs to be performed to display in preview.
   */
  public SplitBuffer(double splitPercentage, ImageOperation imageOperation)
          throws IllegalArgumentException {
    if (splitPercentage < 0 || splitPercentage > 100 || imageOperation == null) {
      throw new IllegalArgumentException("Illegal parameters passed for the "
              + "split preview operation on an image.\n");
    }
    this.splitPercentage = splitPercentage;
    this.imageOperation = imageOperation;
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * It provides a subset of pixel values to the imageOperation object for applying changes.
   * The result is merged with the original image's leftover part to display it in the preview mode.
   * The combined image is stored in the cache memory of the application.
   * Returns the images containing the data that can be accessed and operated by this interface.
   *
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed on the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if (rgbImage == null || rgbImage.getImageWidth() <= 0 || rgbImage.getImageHeight() <= 0) {
      throw new IllegalArgumentException("Image passed for to the "
              + "buffer is not as expected, check again.\n");
    }
    int[][][] rgbPixel = rgbImage.getPixel();
    double estimation = (splitPercentage * rgbImage.getImageWidth()) / 100.0;
    int splitWidth = (int) Math.round(estimation);
    try {
      int[][][] imageOperationMatrix = getPixelMatSubset(rgbPixel,
              rgbImage.getImageHeight(), splitWidth);
      RGBImageInterface operatedImage = imageOperation.operation(
              new RGBImage(imageOperationMatrix));
      int[][][] operatedPixelMatrix = operatedImage.getPixel();
      copyContentsMatrix(rgbPixel, operatedPixelMatrix,
              operatedImage.getImageHeight(), operatedImage.getImageWidth());
    } catch (IllegalArgumentException ex) {
      return new RGBImage(rgbPixel);
    }
    return new RGBImage(rgbPixel);
  }

  private int[][][] getPixelMatSubset(int[][][] pixelMatOriginal, int height, int width) {
    int[][][] resultMat = new int[height][width][ColorMapping.values().length];
    copyContentsMatrix(resultMat, pixelMatOriginal, height, width);
    return resultMat;
  }

  private void copyContentsMatrix(int[][][] NonUpdatedMatrix,
                                  int[][][] updatedMatrix, int height, int width) {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        System.arraycopy(updatedMatrix[i][j], 0, NonUpdatedMatrix[i][j],
                0, ColorMapping.values().length);
      }
    }
  }


}
