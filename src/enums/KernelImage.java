package enums;

/**
 * Enum which had the set of existing kernels for image operations like blur, sharpness.
 * The individual operations contain their kernel matrices which are applied during the filtering.
 * The names of the operations are also mapped to fixed numbers like Blur:0, Sharpen:1.
 * The fixed number can be accessed while applying the operation on any of the existing image.
 */
public enum KernelImage {

  Blur(0,
          new double[][]
          {
            {1.0 / 16, 1.0 / 8, 1.0 / 16},
            {1.0 / 8, 1.0 / 4, 1.0 / 8},
            {1.0 / 16, 1.0 / 8, 1.0 / 16}
          }
  ),

  Sharpen(1,
          new double[][]
          {
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, 1.0 / 4, 1.0 / 4, 1.0 / 4, -1.0 / 8},
            {-1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8, -1.0 / 8}
          }
  );

  final int kernelValue;

  /**
   * A double matrix for the initializing the kernel when the enum objects are created.
   */
  public final double[][] kernel;

  /**
   * The constructor assigns the Kernel mapping value mapping to each of the enum objects.
   * For this enum it assigns values to BLUR and SHARPEN enums the numerical maps.
   *
   * @param kernelValue   Integer representing the enum map for the diff Kernels: Sharpen & Blur.
   * @param kernel        A 2-D matrix having the kernel transformation values for different ops.
   */
  KernelImage(int kernelValue, double[][] kernel) {
    this.kernelValue = kernelValue;
    this.kernel = kernel;
  }
}
