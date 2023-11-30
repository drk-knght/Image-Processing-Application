package enums;

/**
 * Enum to store the axis names and their mapping to fixed value for flip operation.
 * The enum can be extended to more flipping operations as required in future version.
 * Horizontal is assigned a numerical value of 0 and vertical is assigned a value of 1.
 */
public enum AxisName {
  horizontal(0),
  vertical(1);

  final int axisValue;

  /**
   * The constructor assigns the axis value mapping to each of the enum objects.
   * For this enum it assigns values to horizontal and vertical enums the numerical maps.
   *
   * @param axisValue Integer representing the enum map for the diff axis: horizontal & vertical.
   */
  AxisName(int axisValue) {
    this.axisValue = axisValue;
  }
}
