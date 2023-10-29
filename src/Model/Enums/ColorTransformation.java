package Model.Enums;

public enum ColorTransformation {

  Sepia(0, 
          new double [][]{
          { 0.393, 0.769, 0.189},
          {0.349, 0.686, 0.168},
          {0.272, 0.534, 0.131}
          }
        );

  final int transformationIndex;

  final double [][] matrixWeight;
  ColorTransformation(int transformationIndex, double[][] matrixWeight) {
    this.transformationIndex=transformationIndex;
    this.matrixWeight=matrixWeight;

  }
}
