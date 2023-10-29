package Model.ImageOperations;

import java.io.IOException;

import Model.Enums.ColorMapping;
import Model.Enums.KernelImage;
import Model.RGBImage;
import Model.RGBImageInterface;

public class SharpenImage implements ImageOperation{

  private final int kernelOperation;
  public SharpenImage(int kernelOperation){
    this.kernelOperation=kernelOperation;
  }

  private static KernelImage getKernelType(int kernelOrdinal){
    for(KernelImage g:KernelImage.values()){
      if(kernelOrdinal==g.ordinal()){
        return g;
      }
    }
    return null;
  }

  @Override
  public RGBImageInterface operation(RGBImageInterface rgbImage) throws IOException {
    int [][][] pixelMatrix= rgbImage.getPixel();
    KernelImage kernelType=getKernelType(kernelOperation);
    int [][][] resultMat=null;
    if(kernelType!=null){
      resultMat=applyFilter(kernelType.kernel,pixelMatrix);
    }
    return new RGBImage(resultMat);
  }

  private int [][][] applyFilter(double [][] kernel, int [][][]imageMatrix){
    int height=imageMatrix.length;
    int width=imageMatrix[0].length;
    int [][][] imagePixelSharper=new int [height][width][ColorMapping.values().length];

    for(int color=0;color< ColorMapping.values().length;color++){

      for(int i=0;i<height;i++){

        for(int j=0;j<width;j++){

          int rowMidPoint= (kernel.length/2);
          int colMidPoint=(kernel[0].length/2);

          double sum=0.0;

          for(int xOffset=-rowMidPoint;xOffset<=rowMidPoint;xOffset++){
            for(int yOffset=-colMidPoint;yOffset<=colMidPoint;yOffset++){

              if(i+xOffset>=0 && i+xOffset<height && j+yOffset>=0 && j+yOffset<width){
                sum+=(double)imageMatrix[i+xOffset][j+yOffset][color]*kernel[rowMidPoint+xOffset][colMidPoint+yOffset];
              }
            }
          }
          imagePixelSharper[i][j][color]=(int)Math.min(255, Math.max(0, sum));
        }
      }
    }
    return imagePixelSharper;
  }

}
