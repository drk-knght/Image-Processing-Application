package Model;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;

import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.Enums.GreyScaleType;
import Model.Enums.KernelImage;
import Model.FileHandling.ImageIOUtil;
import Model.FileHandling.PPMUtil;
import Model.ImageOperations.SingleIN.BrightnessProfilerImage;
import Model.ImageOperations.SingleIN.ColorTransformation;
import Model.ImageOperations.MultiIN.CombineChannelImage;
import Model.ImageOperations.SingleIN.FlipImage;
import Model.ImageOperations.SingleIN.GreyScaleImage;
import Model.ImageOperations.SingleIN.ImageOperation;
import Model.ImageOperations.SingleIN.MonochromeImage;
import Model.ImageOperations.MultiIN.MultipleImagesSingleOperation;
import Model.ImageOperations.MultiOUT.MultipleOperationImages;
import Model.ImageOperations.SingleIN.SharpenImage;
import Model.ImageOperations.MultiOUT.SplitChannelImage;

public class RGBImage implements RGBImageInterface {

  private int[][][] pixelMatrix;

  private int height;

  private int width;

  public static final int maxValue = 255;

  public RGBImage(String filePath) throws IOException, NullPointerException {
    if(filePath.isEmpty()){
      throw new NullPointerException("No file path passed. Aborting!!!");
    }
    int[][][] pixelMatrix = null;
    String extension = getFileExtension(filePath);
    if (extension.equals("ppm")) {
      pixelMatrix = PPMUtil.ppmFileReader(filePath);
    } else {
      pixelMatrix = ImageIOUtil.imageIOFileReader(filePath);
    }
    checkAndAssignValues(pixelMatrix);
  }

  public RGBImage(int[][][] pixelMatrix) {
    checkAndAssignValues(pixelMatrix);
  }

  private void checkAndAssignValues(int[][][] pixelMatrix) {
    checkValidDimensionImage(pixelMatrix);
    this.pixelMatrix = pixelMatrix;
    this.height = pixelMatrix.length;
    this.width = pixelMatrix[0].length;
  }

  private void checkValidDimensionImage(int[][][] pixelMatrix) {
    if(pixelMatrix==null){
      throw new NullPointerException("The image matrix passed is a null reference. Aborting the program.");
    }
    int height=pixelMatrix.length;
    int width=pixelMatrix[0].length;
    for(int i=0;i< pixelMatrix.length;i++){
      for(int j=0;j<pixelMatrix[i].length;j++){
        if(pixelMatrix[i].length!=width || pixelMatrix[i][j].length!=ColorMapping.values().length){
          throw new InputMismatchException("Input values of the array does not match as expected.");
        }
      }
    }
  }

  private String getFileExtension(String filePath) {

    int index = filePath.lastIndexOf('.');
    if (index != -1) {
      return filePath.substring(index + 1);
    } else return "";
  }

  @Override
  public void saveImage(String imagePath) throws IOException {
    String imageExtension = getFileExtension(imagePath);
    if (imageExtension.equals("ppm")) {
      PPMUtil.savePPMImage(this.height, this.width, this.pixelMatrix, imagePath);
    } else {
      ImageIOUtil.saveImageIOFile(this.height, this.width, this.pixelMatrix, imagePath, imageExtension);
    }
  }

  @Override
  public RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException {
    if(axisDirection>= AxisName.values().length){
      throw new IllegalArgumentException("Wrong axis value passed to the model for flipping the image. Aborting!!");
    }
    ImageOperation imageOperation = new FlipImage(axisDirection);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface changeBrightness(int deltaChangeValue){
    ImageOperation imageOperation = new BrightnessProfilerImage(deltaChangeValue);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface changeSharpness(int kernelType) throws IllegalArgumentException {
    if(kernelType>= KernelImage.values().length){
      throw new IllegalArgumentException("Wrong kernel value passed to model for changing the sharpness operation on the image. Aborting!!");
    }
    ImageOperation imageOperation = new SharpenImage(kernelType);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents) throws IllegalArgumentException {
    if(imageComponents==null){
      throw new IllegalArgumentException("Image set for channel combination cannot be null. aborting!!");
    }
    MultipleImagesSingleOperation imageOperation = new CombineChannelImage();
    imageComponents.add(0, this);
    return imageOperation.operation(imageComponents);
  }

  @Override
  public List<RGBImageInterface> splitImageComponents() {
    MultipleOperationImages multiOP = new SplitChannelImage();
    return multiOP.operation(this);
  }

  @Override
  public RGBImageInterface getSingleComponentImage(int colorValue) throws IllegalArgumentException {
    if(colorValue>= ColorMapping.values().length){
      throw new IllegalArgumentException("Wrong color value passed to model for performing the monochrome operation on the image. Aborting!!");
    }
    ImageOperation imageOperation = new MonochromeImage(colorValue);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface greyScaleImage(int greyScaleType) throws IllegalArgumentException{
    if(greyScaleType>= GreyScaleType.values().length){
      throw new IllegalArgumentException("Wrong greyscale value passed to model for greyscale operation on image. Aborting!!");
    }
    ImageOperation imageOperation = new GreyScaleImage(greyScaleType);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface sepiaImage() {
    ImageOperation imageOperation = new ColorTransformation();
    return imageOperation.operation(this);
  }

  @Override
  public int getImageHeight() {
    return this.height;
  }

  @Override
  public int getImageWidth() {
    return this.width;
  }

  @Override
  public int[][][] getPixel() {
    int[][][] copyPixelMatrix = new int[height][width][ColorMapping.values().length];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        for (int k = 0; k < ColorMapping.values().length; k++) {
          copyPixelMatrix[i][j][k] = pixelMatrix[i][j][k];
        }
      }
    }
    return copyPixelMatrix;
  }


//  public static void main(String[] args) throws IOException {
//
//    String filePathBlue = "/Users/omagarwal/Desktop/LowRes.jpeg";
//
//    RGBImageInterface imageInterface = new RGBImage(filePathBlue);
//    RGBImageInterface result = imageInterface.greyScaleImage(2);
//
//    String filedir = "/Users/omagarwal/Desktop/LowRes.ppm";
//    result.saveImage(filedir);
//  }
}
