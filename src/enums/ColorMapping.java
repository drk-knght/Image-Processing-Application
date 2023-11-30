package enums;

import java.awt.Color;

/**
 * Enum to store the color names and their mapping to fixed value for any color related operation.
 * The enum can be extended to more  color codes like transparency etc. as required in the system.
 * Red color is assigned a numerical value of 0.
 * Green color is assigned a numerical value of 1.
 * Blue color is assigned a numerical value of 2 for any color related uses in the application.
 */
public enum ColorMapping {
  red(0, Color.RED),
  green(1, Color.GREEN),
  blue(2, Color.BLUE);

  final int colorCoding;

  public final Color color;

  /**
   * The constructor assigns the color mapping value mapping to each of the enum objects.
   * For this enum it assigns values to red, green and blue enums the numerical maps.
   *
   * @param colorCoding Integer representing the enum map for the diff colors: red, green & blue.
   * @param color       Color object representing the enum map for the diff colors: red, green & blue.
   */
  ColorMapping(int colorCoding, Color color) {
    this.colorCoding = colorCoding;
    this.color = color;
  }
}
