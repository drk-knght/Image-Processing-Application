package Model.Enums;

public enum AxisName {
  horizontal(0),
  vertical(1);

  final int axisValue;

  AxisName(int axisValue){
    this.axisValue=axisValue;
  }
}
