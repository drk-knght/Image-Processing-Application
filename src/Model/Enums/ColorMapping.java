package Model.Enums;

public enum ColorMapping {
  red(0),
  green(1),
  blue(2);

  final int colorCoding;

  ColorMapping(int colorCoding) {
    this.colorCoding = colorCoding;
  }
}
