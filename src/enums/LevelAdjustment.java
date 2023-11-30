package enums;

/**
 * Enum which shows the information about the level adjustment name-value mappings.
 * This individual name are used to fetch the data and use it as per different containers need.
 * The names of the operations are also mapped to fixed numbers like BLACK:1, MID:2,HIGHLIGHT:3
 * The fixed number can be accessed while applying the operation on any of the existing image.
 */
public enum LevelAdjustment {

  BLACK(1),
  MID(2),
  HIGHLIGHT(3);

  final public int levelValue;

  /**
   * The constructor assigns the Level Adjustment mapping value mapping to each of the enum objects.
   * For this enum it assigns values to BLACK, MID and HIGHLIGHT enums the numerical maps.
   *
   * @param levelValue Integer representing enum map for the diff levels: BLACK, MID & HIGHLIGHT.
   */
  LevelAdjustment(int levelValue) {
    this.levelValue = levelValue;
  }
}
