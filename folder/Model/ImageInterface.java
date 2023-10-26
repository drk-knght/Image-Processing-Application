package folder.Model;

import java.io.IOException;

/**
 * The interface represents operations corresponding to an image.
 * An image can perform operations on itself like increasing brightness, blurring etc.
 */
public interface ImageInterface {

  void save(String imagePath, String ImageName) throws IOException;

  ImageInterface selectColorComponentImage(int colorIndex);

  ImageInterface horizontalFlipImage();

  ImageInterface verticalFlipImage();

  ImageInterface changeImageBrightness(int deltaIncrement);

  ImageInterface valueGreyScaleImage();

  ImageInterface lumaGreyScaleImage();

  ImageInterface intensityGreyScaleImage();

}

