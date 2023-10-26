package folder.Controller;

public class Controller implements ControllerInterface{
  /*
  load image-path image-name: Load an image from the specified path and refer it to henceforth in the program by the given image name.

  save image-path image-name: Save the image with the given name to the specified path which should include the name of the file.

  red-component image-name dest-image-name: Create an image with the red-component of the image with the given name, and refer to it henceforth in the program by the given destination name. Similar commands for green, blue, value, luma, intensity components should be supported. Note that the images for value, luma and intensity will be greyscale images.

  horizontal-flip image-name dest-image-name: Flip an image horizontally to create a new image, referred to henceforth by the given destination name.

  vertical-flip image-name dest-image-name: Flip an image vertically to create a new image, referred to henceforth by the given destination name.

  brighten increment image-name dest-image-name: brighten the image by the given increment to create a new image, referred to henceforth by the given destination name. The increment may be positive (brightening) or negative (darkening).

  rgb-split image-name dest-image-name-red dest-image-name-green dest-image-name-blue: split the given image into three greyscale images containing its red, green and blue components respectively.

  rgb-combine image-name red-image green-image blue-image: Combine the three greyscale images into a single image that gets its red, green and blue components from the three images respectively.

  blur image-name dest-image-name: blur the given image and store the result in another image with the given name.

  sharpen image-name dest-image-name: sharpen the given image and store the result in another image with the given name.

  sepia image-name dest-image-name: produce a sepia-toned version of the given image and store the result in another image with the given name.

  run script-file: Load and run the script commands in the specified file.
   */
  @Override
  public void loadImage(String imageAddress, String imageName) {

  }

  @Override
  public void brightenImage(int brightnessValue, String existingImageName, String newImageName) {

  }

  @Override
  public void verticalFlipImage(String existingImageName, String newImageName) {

  }

  @Override
  public void greyScaleImage(String existingImageName, String newImageName) {

  }

  @Override
  public void saveImage(String imageAddress, String imageName) {

  }

  @Override
  public void rgbSplitImage(String originalImage, String redImage, String greenImage, String blueImage) {

  }

  @Override
  public void rgbCombineImage(String redImage, String greenImage, String blueImage, String originalImage) {

  }
}
