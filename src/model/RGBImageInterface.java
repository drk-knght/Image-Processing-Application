package model;

import java.io.IOException;
import java.util.List;

/**
 * This interface represents the actions that can be performed on a given single image.
 * The image can carry out saving, flipping, split into different components etc.
 * It also throws an exception if the parameters passed for the operations are invalid or corrupted.
 */

public interface RGBImageInterface {

  /**
   * The method represents the flipping action carried out by an image on itself.
   * Image can flip along different axis like horizontal or vertical depending on the requirement.
   * The old and new image both are retained in the memory.
   *
   * @param axisDirection Integer representing the axis direction mapping with the Enum data.
   * @return Modified image which contains the data of the flipped version of the previous image.
   * @throws IllegalArgumentException Throws exception if the data could not be written to the path.
   */
  RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException;

  /**
   * The method represents the changing brightness option on the present image.
   * It increases or decreases the brightness as needed for this image.
   * It ensures that after the operation the pixel value are within the range of 0 to 255.
   *
   * @param deltaChangeValue Amount to change for the individual pixels of the image.
   * @return Modified image which contains the data of the brighter version of the previous image.
   */
  RGBImageInterface changeBrightness(int deltaChangeValue);

  /**
   * The method represents the changing sharpness option on the present image.
   * The operation can blur or sharpen the image depending on the input type provided to the method.
   *
   * @param kernelType Containing the mapping to an Enum which store data for changing sharpness.
   * @param splitPercentage Double value representing the split ratio of original and modified img.
   * @return Modified img which contains the data of the changed sharpness version of the prev img.
   * @throws IllegalArgumentException Throws exception if kernel mapping is invalid or not exists.
   */
  RGBImageInterface changeSharpness(int kernelType, double splitPercentage) throws IllegalArgumentException;

  /**
   * The method represents the combining different channeled images into a single image.
   * The present image is also added to the list and passed to the operation for combining RGB val.
   *
   * @param imageComponents List of images having different color-greyscale images.
   * @return An image containing the data of combinations of all the images that are passed to it.
   * @throws IllegalArgumentException Throws exception if param is invalid or not exists.
   */
  RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents)
          throws IllegalArgumentException;

  /**
   * The method represents the split image operation on this img into different color-greyscale img.
   * It divides it into different channel images of same depth.
   *
   * @return List of images which are generated after applying image processing operation.
   * @throws IOException Exception is thrown If the image passed is not a valid input.
   */
  List<RGBImageInterface> splitImageComponents() throws IOException;

  /**
   * This represents the Monochrome operation on the present image that calls this method.
   *
   * @param colorValue Takes the color component type to get a new color-greyscale image.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the colorValue is not a valid input.
   */
  RGBImageInterface getSingleComponentImage(int colorValue) throws IllegalArgumentException;

  /**
   * This method represents the greyscale operation on the present single image currently in use.
   *
   * @param greyScaleType Takes the type of action to perform on this to get a new black-white img.
   * @param splitPercentage Double value representing the split ratio of original and modified img.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the greyScaleType is not a valid input.
   */
  RGBImageInterface greyScaleImage(int greyScaleType, double splitPercentage) throws IllegalArgumentException;

  /**
   * Method represents the sepia color transformation on the present single image currently in use.
   *
   * @param splitPercentage Double value representing the split ratio of original and modified img.
   * @return An image as the result of the action performed on the present image.
   */
  RGBImageInterface sepiaImage(double splitPercentage);

  /**
   * Method represents the level adjustment operation on an existing image currently present in use.
   * The values of the individual pixels is changed as per the quadratic equation.
   *
   * @param b Shadow-point where the intensity of the pixel decreases around that range.
   * @param m Mid-point where the intensity of the pixel changes non-linearly as per the curve eq.
   * @param w Highlight-point where the intensity of the pixel is increased which satisfy the curve.
   * @param splitPercentage Double value representing the split ratio of original and modified img.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown If the b,m,w are not in ascending order.
   */
  RGBImageInterface levelsAdjustment(double b, double m, double w, double splitPercentage) throws IllegalArgumentException;

  /**
   * Method represents the histogram plotting of the r,g,b pixel operation on an image.
   * The histogram represents the intensity values Vs frequency of those values on the axes.
   * @return An image as the result of the action performed on the present image.
   */
  RGBImageInterface getPixelHistogram();

  /**
   * Method represents the compression operation part on the image, and it is a lossy one.
   * The data of the pixels is lost when it is reverted back to its original size.
   * @param compressionPercentage Percent of the image that needs to be thrown on the operation.
   * @return An image as the result of the action performed on the present image.
   * @throws IllegalArgumentException Exception is thrown if the % of compression is non-positive.
   */
  RGBImageInterface compressImage(double compressionPercentage) throws IllegalArgumentException;

  /**
   * Method represents the color correction part on an existing image.
   * The intensity values for different channels are aligned long the common global max peak.
   * @param splitPercentage Double value representing the split ratio of original and modified img.
   * @return An image as the result of the action performed on the present image.
   */
  RGBImageInterface colorCorrectionImage(double splitPercentage);

  /**
   * Getter method to get the height of the image currently in use.
   *
   * @return Integer signifying the height of the present image in use.
   */
  int getImageHeight();

  /**
   * Getter method to get the width of the image currently in use.
   *
   * @return Integer signifying the width of the present image in use.
   */
  int getImageWidth();

  /**
   * Getter method to get the deep copy of the image pixel matrix currently in use.
   *
   * @return 3-d Matrix signifying the pixels of the present image in use.
   */
  int[][][] getPixel();
}
