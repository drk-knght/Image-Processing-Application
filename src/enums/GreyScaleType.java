package enums;

import java.util.Arrays;
import java.util.function.Function;

/**
 * Enum to store the information regarding existing greyscale operations present in the application.
 * Currently, it supports Value, luma and intensity greyscale.
 * Each of them have a respective numerical value of 0,1 and 2.
 * The Enum can during runtime calculate the grey scale value when an array of RGB pixels is passed.
 */
public enum GreyScaleType {
  value(0) {
    /**
     * The method takes an array of RGB pixel value for single cell and calculates value greyscale.
     * The value grey scale is the maximum of the r,g,b components of a single pixel cell of an img.
     * @param rgbPixelArray The array containing the R,G,B values of the single pixel of the image.
     * @return Integer signifying the Value greyscale for the RGB array passed after the operation.
     */
    @Override
    public int[] calculateReturnPixelValue(int[] rgbPixelArray) {
      return GreyScaleType.setPixelValue(rgbPixelArray,
        ar -> {
          int num = 0;
          for (int ele = 0; ele < ar.length; ele++) {
            num = Math.max(num, ar[ele]);
          }
          return num;
        }
      );

    }
  },

  luma(1) {
    /**
     * The method takes an array of RGB pixel value for single cell and calculates luma greyscale.
     * Luma greyscale is the weighted sum of the r,g,b components of a single pixel cell of an img.
     * @param rgbPixelArray The array containing the R,G,B values of the single pixel of the image.
     * @return Integer signifying the Luma greyscale for the RGB array passed after the operation.
     */
    @Override
    public int[] calculateReturnPixelValue(int[] rgbPixelArray) {
      return GreyScaleType.setPixelValue(rgbPixelArray,
        ar -> {
          double num = 0.2125 * rgbPixelArray[ColorMapping.red.ordinal()]
                  + 0.7152 * rgbPixelArray[ColorMapping.green.ordinal()]
                  + 0.0722 * rgbPixelArray[ColorMapping.blue.ordinal()];
          return (int) Math.min(255, Math.max(0, num));
        }
      );
    }
  },

  intensity(2) {
    /**
     * The method takes array of RGB pixel value for single cell and calculates intensity greyscale.
     * Intensity greyscale is the average of the r,g,b components of a single pixel cell of an img.
     * @param rgbPixelArray The array containing the R,G,B values of the single pixel of the image.
     * @return Integer signifying the Intensity greyscale for the RGB array passed after operation.
     */
    @Override
    public int[] calculateReturnPixelValue(int[] rgbPixelArray) {
      return GreyScaleType.setPixelValue(rgbPixelArray,
        ar -> {
          int num = 0;
          for (int ele = 0; ele < ar.length; ele++) {
            num += ar[ele];
          }
          return (num / ColorMapping.values().length);
        }
      );
    }

  };

  final int scaleValue;

  /**
   * The method takes an array of RGB pixel value for single cell and calculates greyscale for that.
   * The method is overridden by existing objects in enum that have logic implemented as required
   *
   * @param rgbPixelArray The array containing the R,G,B values of the single pixel of the image.
   * @return Integer signifying the grey scale value for the RGB array passed after the operation.
   */
  abstract public int[] calculateReturnPixelValue(int[] rgbPixelArray);

  private static int[] setPixelValue(int[] pixelArray, Function<int[], Integer> greyConverter) {
    int pixelValue = greyConverter.apply(pixelArray);
    Arrays.fill(pixelArray, pixelValue);
    return pixelArray;
  }

  /**
   * The constructor assigns the greyscale mapping value mapping to each of the enum objects.
   * For this enum it assigns values to luma, value and intensity enums the numerical maps.
   *
   * @param scaleValue Integer for the enum map for the diff greyscale: luma, value & intensity.
   */
  GreyScaleType(int scaleValue) {
    this.scaleValue = scaleValue;
  }
}
