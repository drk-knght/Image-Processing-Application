package folder.Controller;

/**
 * The interface represents the controller of the mvc model.
 * A controller can take inputs from the user and provide instructions to model to perform particular operations.
 * The controller calls the model object's methods as per the input given by the user.
 */
public interface ControllerInterface {

  void loadImage(String imageAddress, String imageName);

  void brightenImage(int brightnessValue, String existingImageName, String newImageName);

  void verticalFlipImage(String existingImageName,String newImageName);

  void greyScaleImage(String existingImageName,String newImageName);

  void saveImage(String imageAddress, String imageName);

  void rgbSplitImage(String originalImage, String redImage, String greenImage, String blueImage);

  void rgbCombineImage(String redImage, String greenImage, String blueImage, String originalImage);
}
