package Model.ImageOperations.SingleIN;

import java.io.IOException;

import Model.Enums.ColorMapping;
import Model.Enums.KernelImage;
import Model.ImageOperations.SingleIN.ImageOperation;
import Model.RGBImage;
import Model.RGBImageInterface;

public class SharpenImage implements ImageOperation {

  private final int kernelOperation;

  public SharpenImage(int kernelOperation) {
    if(kernelOperation<0 || kernelOperation>=KernelImage.values().length){
      throw new IllegalArgumentException("Sharpening operation value passed is not defined in the system. Try again.");
    }
    this.kernelOperation = kernelOperation;
  }

  private static KernelImage getKernelType(int kernelOrdinal) {
    for (KernelImage g : KernelImage.values()) {
      if (kernelOrdinal == g.ordinal()) {
        return g;
      }
    }
    return null;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IllegalArgumentException{
    if(rgbImage==null || rgbImage.getImageWidth()<=0 || rgbImage.getImageHeight()<=0) {
      throw new IllegalArgumentException("Image passed for changing the sharpness is not as expected, check again. Aborting!!");
    }
    int[][][] pixelMatrix = rgbImage.getPixel();
    KernelImage kernelType = getKernelType(kernelOperation);
    int[][][] resultMat = null;
    if (kernelType == null) {
      throw new IllegalArgumentException("Wrong parameter passed for kernel type.");
    }
    resultMat = applyFilter(kernelType.kernel, pixelMatrix);
    return new RGBImage(resultMat);
  }

  private int[][][] applyFilter(double[][] kernel, int[][][] imageMatrix) {
    int height = getHeightPixelMatrix(imageMatrix);
    int width = getWidthPixelMatrix(imageMatrix);
    int[][][] imagePixelSharper = new int[height][width][ColorMapping.values().length];

    for (int color = 0; color < ColorMapping.values().length; color++) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
//          int rowMidPoint= (kernel.length/2);
//          int colMidPoint=(kernel[0].length/2);
//          double sum=0.0;
//          for(int xOffset=-rowMidPoint;xOffset<=rowMidPoint;xOffset++){
//            for(int yOffset=-colMidPoint;yOffset<=colMidPoint;yOffset++){
//
//              if(i+xOffset>=0 && i+xOffset<height && j+yOffset>=0 && j+yOffset<width){
//                sum+=(double)imageMatrix[i+xOffset][j+yOffset][color]*kernel[rowMidPoint+xOffset][colMidPoint+yOffset];
//              }
//            }
//          }
          double sum = calculateKernelFilterValue(imageMatrix, kernel, i, j, color);
          imagePixelSharper[i][j][color] = (int) Math.min(255, Math.max(0, sum));
        }
      }
    }
    return imagePixelSharper;
  }

  private int getHeightPixelMatrix(int[][][] pixelMatrix) {
    return pixelMatrix.length;
  }

  private int getWidthPixelMatrix(int[][][] pixelMatrix) {
    if (pixelMatrix != null) {
      return pixelMatrix[0].length;
    }
    return 0;
  }


  private double calculateKernelFilterValue(int[][][] imageMatrix, double[][] kernel,
                                            int i, int j, int color) {
    int height = getHeightPixelMatrix(imageMatrix);
    int width = getWidthPixelMatrix(imageMatrix);
    int rowMidPoint = (kernel.length / 2);
    int colMidPoint = (kernel[0].length / 2);
    double sum = 0.0;

    for (int xOffset = -rowMidPoint; xOffset <= rowMidPoint; xOffset++) {
      for (int yOffset = -colMidPoint; yOffset <= colMidPoint; yOffset++) {
        if (i + xOffset >= 0 && i + xOffset < height && j + yOffset >= 0 && j + yOffset < width) {
          sum += (double) imageMatrix[i + xOffset][j + yOffset][color] * kernel[rowMidPoint + xOffset][colMidPoint + yOffset];
        }
      }
    }
    return sum;
  }

}
