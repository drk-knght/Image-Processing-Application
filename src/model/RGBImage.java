package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import controller.filehandling.reader.ImageIOReader;
import controller.filehandling.reader.PPMReader;
import controller.filehandling.writer.ImageIOWriter;
import controller.filehandling.writer.PPMWriter;
import model.enums.AxisName;
import model.enums.ColorMapping;
import model.enums.GreyScaleType;
import model.enums.KernelImage;
import model.imageoperations.singlein.Brightness;
import model.imageoperations.singlein.ColorTransformation;
import model.imageoperations.multiin.CombineChannelImage;
import model.imageoperations.singlein.Flip;
import model.imageoperations.singlein.GreyScale;
import model.imageoperations.singlein.ImageOperation;
import model.imageoperations.singlein.Monochrome;
import model.imageoperations.multiin.MultipleImagesSingleOperation;
import model.imageoperations.multiout.MultipleOperationImages;
import model.imageoperations.singlein.Sharpness;
import model.imageoperations.multiout.SplitChannelImage;

/**
 * This class represents an image. The image class can contain different fields like height, width.
 * The image can also perform various image transformation operation on itself and get new objects.
 * This it main model class for the MVC architecture of image processing application.
 */
public class RGBImage implements RGBImageInterface {

  private int[][][] pixelMatrix;

  private int height;

  private int width;

  public static final int MAX = 255;

  /**
   * Constructor takes the file path of image as arguments and loads the image to the app memory.
   *
   * @param filePath String containing the file path where the img is present in the disk.
   * @throws IOException              Throws exception if the file path is invalid or no access.
   * @throws IllegalArgumentException Throws exception if the file path is empty.
   */
  public RGBImage(String filePath) throws IOException, NullPointerException {
    if (filePath.isEmpty()) {
      throw new IllegalArgumentException("No file path passed. Aborting!!!");
    }
    int[][][] pixelMatrix = null;
    String extension = getFileExtension(filePath);
    if (extension.equals("ppm")) {
      pixelMatrix = PPMReader.readFileContent(new FileInputStream(filePath));
    } else {
      pixelMatrix = ImageIOReader.readFileContent(new FileInputStream(filePath));
    }
    checkAndAssignValues(pixelMatrix);
  }

  /**
   * Constructor to create an image object using the pixel image matrix passed as args to it.
   *
   * @param pixelMatrix 3-d Matrix which signifies the img data in matrix format.
   */
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
    if (pixelMatrix == null) {
      throw new IllegalArgumentException("The image matrix passed is a null reference. "
              + "Aborting the program.");
    }

    checkInitialDim(pixelMatrix);
    int height = pixelMatrix.length;
    int width = pixelMatrix[0].length;
    for (int i = 0; i < pixelMatrix.length; i++) {
      for (int j = 0; j < pixelMatrix[i].length; j++) {
        if (pixelMatrix[i].length != width
                || pixelMatrix[i][j].length != ColorMapping.values().length) {
          throw new IllegalArgumentException("Input values of the "
                  + "array does not match as expected.");
        }
      }
    }
  }

  private void checkInitialDim(int[][][] pixelMatrix) {
    int height = pixelMatrix.length;
    if (height == 0) {
      throw new IllegalArgumentException("Input values of the array does not match as expected.");
    }
    int width = 0;
    if (pixelMatrix[0] != null && pixelMatrix[0].length > 0) {
      width = pixelMatrix[0].length;
    } else {
      throw new IllegalArgumentException("Input values of the array does not match as expected.");
    }
  }

  private String getFileExtension(String filePath) {

    int index = filePath.lastIndexOf('.');
    if (index != -1) {
      return filePath.substring(index + 1);
    } else {
      return "";
    }
  }

  /**
   * The methods represent the saving operation of the image.
   * An image can save itself to a specified file path in different formats like .ppm, .png, .jpg.
   * The old and new image both are retained in the memory.
   *
   * @param imagePath The file path where the image needs to be stored in the present file system.
   * @throws IOException Throws exception if the image data could not be written to that path.
   */
  @Override
  public void saveImage(String imagePath) throws IOException {
    String imageExtension = getFileExtension(imagePath);
    if (imageExtension.equals("ppm")) {
//      PPMUtil.savePPMImage(this.height, this.width, this.pixelMatrix, imagePath);
      PPMWriter.writeToStorageDisk(this,new FileOutputStream(imagePath));
    } else {
//      ImageIOUtil.saveImageIOFile(this.height, this.width,
//              this.pixelMatrix, imagePath, imageExtension);
      ImageIOWriter.writeToStorageDisk(this,new FileOutputStream(imagePath),imageExtension);
    }
  }

  /**
   * The method represents the flipping action carried out by an image on itself.
   * Image can flip along different axis like horizontal or vertical depending on the requirement.
   * The old and new image both are retained in the memory.
   *
   * @param axisDirection Integer representing the axis direction mapping with the Enum data.
   * @return Modified image which contains the data of the flipped version of the previous image.
   * @throws IllegalArgumentException Throws exception if the data could not be written to the path.
   */
  @Override
  public RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException {
    if (axisDirection >= AxisName.values().length) {
      throw new IllegalArgumentException("Wrong axis value passed to the model "
              + "for flipping the image. Aborting!!");
    }
    ImageOperation imageOperation = new Flip(axisDirection);
    return imageOperation.operation(this);
  }

  /**
   * The method represents the changing brightness option on the present image.
   * It increases or decreases the brightness as needed for this image.
   * It ensures that after the operation the pixel value are within the range of 0 to 255.
   *
   * @param deltaChangeValue Amount to change for the individual pixels of the image.
   * @return Modified image which contains the data of the brighter version of the previous image.
   */
  @Override
  public RGBImageInterface changeBrightness(int deltaChangeValue) {
    ImageOperation imageOperation = new Brightness(deltaChangeValue);
    return imageOperation.operation(this);
  }

  /**
   * The method represents the changing sharpness option on the present image.
   * The operation can blur or sharpen the image depending on the input type provided to the method.
   *
   * @param kernelType Containing the mapping to an Enum which store data for changing sharpness.
   * @return Modified img which contains the data of the changed sharpness version of the prev img.
   * @throws IllegalArgumentException Throws exception if kernel mapping is invalid or not exists.
   */
  @Override
  public RGBImageInterface changeSharpness(int kernelType) throws IllegalArgumentException {
    if (kernelType >= KernelImage.values().length) {
      throw new IllegalArgumentException("Wrong kernel value passed to model for "
              + "changing the sharpness operation on the image. Aborting!!");
    }
    ImageOperation imageOperation = new Sharpness(kernelType);
    return imageOperation.operation(this);
  }

  /**
   * The method represents the combining different channeled images into a single image.
   * The present image is also added to the list and passed to the operation for combining RGB vals.
   *
   * @param imageComponents List of images having different color-greyscale images.
   * @return An image containing the data of combinations of all the images that are passed to it.
   * @throws IllegalArgumentException Throws exception if param is invalid or not exists.
   */
  @Override
  public RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents)
          throws IllegalArgumentException {
    if (imageComponents == null) {
      throw new IllegalArgumentException("Image set for channel combination "
              + "cannot be null. aborting!!");
    }
    MultipleImagesSingleOperation imageOperation = new CombineChannelImage();
    imageComponents.add(0, this);
    return imageOperation.operation(imageComponents);
  }

  /**
   * The method represents the split image operation on this img into different color-greyscale img.
   * It divides it into different channel images of same depth.
   *
   * @return List of images which are generated after applying image processing operation.
   */
  @Override
  public List<RGBImageInterface> splitImageComponents() {
    MultipleOperationImages multiOP = new SplitChannelImage();
    return multiOP.operation(this);
  }

  /**
   * This represents the Monochrome operation on the present image that calls this method.
   *
   * @param colorValue Takes the color component type to get a new color-greyscale image.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the colorValue is not a valid input.
   */
  @Override
  public RGBImageInterface getSingleComponentImage(int colorValue)
          throws IllegalArgumentException {
    if (colorValue >= ColorMapping.values().length) {
      throw new IllegalArgumentException("Wrong color value passed to model for "
              + "performing the monochrome operation on the image. Aborting!!");
    }
    ImageOperation imageOperation = new Monochrome(colorValue);
    return imageOperation.operation(this);
  }

  /**
   * This method represents the greyscale operation on the present single image currently in use.
   *
   * @param greyScaleType Takes the type of action to perform on this to get a new black-white img.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the greyScaleType is not a valid input.
   */
  @Override
  public RGBImageInterface greyScaleImage(int greyScaleType) throws IllegalArgumentException {
    if (greyScaleType >= GreyScaleType.values().length) {
      throw new IllegalArgumentException("Wrong greyscale value passed to model for "
              + "greyscale operation on image. Aborting!!");
    }
    ImageOperation imageOperation = new GreyScale(greyScaleType);
    return imageOperation.operation(this);
  }

  /**
   * Method represents the sepia color transformation on the present single image currently in use.
   *
   * @return An image as the result of the action performed on the present image.
   */
  @Override
  public RGBImageInterface sepiaImage() {
    ImageOperation imageOperation = new ColorTransformation();
    return imageOperation.operation(this);
  }

  /**
   * Getter method to get the height of the image currently in use.
   *
   * @return Integer signifying the height of the present image in use.
   */
  @Override
  public int getImageHeight() {
    return this.height;
  }

  /**
   * Getter method to get the width of the image currently in use.
   *
   * @return Integer signifying the width of the present image in use.
   */
  @Override
  public int getImageWidth() {
    return this.width;
  }

  /**
   * Getter method to get the deep copy of the image pixel matrix currently in use.
   *
   * @return 3-d Matrix signifying the pixels of the present image in use.
   */
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

}
