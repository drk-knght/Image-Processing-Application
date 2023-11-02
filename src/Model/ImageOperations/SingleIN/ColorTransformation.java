package Model.ImageOperations.SingleIN;


import Model.Enums.ColorMapping;
import Model.RGBImage;
import Model.RGBImageInterface;

/**
 * This class represents the sepia color transformation on a single image presently in the memory.
 * It takes the image and converts to sepia image as required by the client using this.
 * It checks for validity of inputs passed to it and exception is thrown if invalid.
 */
public class ColorTransformation implements ImageOperation{

  private final double [][] SEPIA_WEIGHTS={
          {0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534,0.131}
  };

  private int [] matrixMultiplication(int []rgbPixels){
    int [] transformedResult=new int[rgbPixels.length];
    for(int i=0;i<rgbPixels.length;i++){
      double weightedSum=0;
      for(int k=0;k<rgbPixels.length;k++){
        weightedSum+=SEPIA_WEIGHTS[i][k]*rgbPixels[k];
      }
      transformedResult[i]=Math.max(0,Math.min(255,(int)weightedSum));
    }
    return transformedResult;
  }

  /**
   * The method performs an action on the existing image in memory of the image processing app.
   * Returns the images containing the data that can be accessed and operated by this interface.
   * @param rgbImage Image currently in memory on which the working is to be done.
   * @return An image as the result of the action performed n the former image.
   * @throws IllegalArgumentException Throws exception if the parameter passed is invalid.
   */
  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException {
    if(rgbImage==null || rgbImage.getImageWidth()<=0 || rgbImage.getImageHeight()<=0){
      throw new IllegalArgumentException("Image passed for sepia image transformation is not as expected, check again. Aborting!!");
    }
    int height=rgbImage.getImageHeight();
    int width=rgbImage.getImageWidth();
    int [][][]pixelMatrix=rgbImage.getPixel();
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        int [] imagePixels=new int[ColorMapping.values().length];
        for(int k=0;k<ColorMapping.values().length;k++){
          imagePixels[k]=pixelMatrix[i][j][k];
        }
        pixelMatrix[i][j]= matrixMultiplication(imagePixels);
      }
    }
    return new RGBImage(pixelMatrix);
  }
}
