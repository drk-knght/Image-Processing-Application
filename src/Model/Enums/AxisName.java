package Model.Enums;

/**
 * Enum to store the axis names and their mapping to fixed value for flip operation.
 * The enum can be extended to more flipping operations as required in future version.
 * Horizontal is assigned a numerical value of 0 and vertical is assigned a value of 1.
 */
public enum AxisName {
  horizontal(0),
  vertical(1);

  final int axisValue;

  AxisName(int axisValue){
    this.axisValue=axisValue;
  }
}
