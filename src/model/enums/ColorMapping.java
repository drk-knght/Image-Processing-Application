package model.enums;

import java.awt.*;

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

  ColorMapping(int colorCoding, Color color) {
    this.colorCoding = colorCoding;
    this.color = color;
  }
}
