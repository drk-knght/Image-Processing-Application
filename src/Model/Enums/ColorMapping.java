package Model.Enums;

/**
 * Enum to store the color names and their mapping to fixed value for any color related operation.
 * The enum can be extended to more  color codes like transparency etc. as required in the system.
 * Red color is assigned a numerical value of 0.
 * Green color is assigned a numerical value of 1.
 * Blue color is assigned a numerical value of 2 for any color related uses in the application.
 */
public enum ColorMapping {
  red(0),
  green(1),
  blue(2);

  final int colorCoding;

  ColorMapping(int colorCoding) {
    this.colorCoding = colorCoding;
  }
}
