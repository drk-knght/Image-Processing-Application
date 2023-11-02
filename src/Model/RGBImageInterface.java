package Model;

import java.io.IOException;
import java.util.List;

/**
 * This interface represents the actions that can be performed on a given single image.
 * The image can carry out saving, flipping, split into different components etc.
 * It also throws an exception if the parameters passed for the operations are invalid or corrupted.
 */

public interface RGBImageInterface {

  /**
   * The methods represent the saving operation of the image.
   * An image can save itself to a specified file path in different formats like .ppm, .png, .jpg.
   * The old and new image both are retained in the memory.
   * @param imagePath The file path where the image needs to be stored in the present file system.
   * @throws IOException Throws exception if the image data could not be written to that path.
   */
  void saveImage(String imagePath) throws IOException;

  /**
   * The method represents the flipping action carried out by an image on itself.
   * Image can flip along different axis like horizontal or vertical depending on the requirement.
   * The old and new image both are retained in the memory.
   * @param axisDirection Integer representing the axis direction mapping with the Enum data.
   * @return Modified image which contains the data of the flipped version of the previous image.
   * @throws IllegalArgumentException Throws exception if the data could not be written to the path.
   */
  RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException;

  /**
   * The method represents the changing brightness option on the present image.
   * It increases or decreases the brightness as needed for this image.
   * It ensures that after the operation the pixel value are within the range of 0 to 255.
   * @param deltaChangeValue Amount to change for the individual pixels of the image.
   * @return Modified image which contains the data of the brighter version of the previous image.
   */
  RGBImageInterface changeBrightness(int deltaChangeValue);

  /**
   * The method represents the changing sharpness option on the present image.
   * The operation can blur or sharpen the image depending on the input type provided to the method.
   * @param kernelType Containing the mapping to an Enum which store data for changing sharpness.
   * @return Modified img which contains the data of the changed sharpness version of the prev img.
   * @throws IllegalArgumentException Throws exception if kernel mapping is invalid or not exists.
   */
  RGBImageInterface changeSharpness(int kernelType) throws IllegalArgumentException;

  /**
   * The method represents the combining different channeled images into a single image.
   * The present image is also added to the list and passed to the operation for combining RGB vals.
   * @param imageComponents List of images having different color-greyscale images.
   * @return An image containing the data of combinations of all the images that are passed to it.
   * @throws IllegalArgumentException Throws exception if param is invalid or not exists.
   */
  RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents) throws IllegalArgumentException;

  /**
   * The method represents the split image operation on this img into different color-greyscale img.
   * It divides it into different channel images of same depth.
   * @return List of images which are generated after applying image processing operation.
   * @throws IOException Exception is thrown If the image passed is not a valid input.
   */
  List<RGBImageInterface> splitImageComponents() throws IOException;

  /**
   * This represents the Monochrome operation on the present image that calls this method.
   * @param colorValue Takes the color component type to get a new color-greyscale image.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the colorValue is not a valid input.
   */
  RGBImageInterface getSingleComponentImage(int colorValue) throws IllegalArgumentException;

  /**
   * This method represents the greyscale operation on the present single image currently in use.
   * @param greyScaleType Takes the type of action to perform on this to get a new black-white img.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the greyScaleType is not a valid input.
   */
  RGBImageInterface greyScaleImage(int greyScaleType) throws IllegalArgumentException;

  /**
   * Method represents the sepia color transformation on the present single image currently in use.
   * @return An image as the result of the action performed on the present image.
   */
  RGBImageInterface sepiaImage();

  /**
   * Getter method to get the height of the image currently in use.
   * @return Integer signifying the height of the present image in use.
   */
  int getImageHeight();

  /**
   * Getter method to get the width of the image currently in use.
   * @return Integer signifying the width of the present image in use.
   */
  int getImageWidth();

  /**
   * Getter method to get the deep copy of the image pixel matrix currently in use.
   * @return 3-d Matrix signifying the pixels of the present image in use.
   */
  int[][][] getPixel();
}
