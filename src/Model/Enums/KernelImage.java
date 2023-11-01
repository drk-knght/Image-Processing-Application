package Model.Enums;

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

  public final double[][] kernel;

  KernelImage(int kernelValue, double[][] kernel) {
    this.kernelValue = kernelValue;
    this.kernel = kernel;
  }
}
