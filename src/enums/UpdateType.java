package enums;

/**
 * Enum which shows the information about the update type name-value mappings.
 * This individual name are used to fetch the data and use it as per different containers need.
 * The names of the operations are also mapped to fixed numbers like OLD:0, NEW:1.
 * The fixed number can be accessed while applying the operation on any of the existing image.
 */
public enum UpdateType {
  OLD(0),
  NEW(1);

  final int updateValue;

  /**
   * The constructor assigns the update type mapping value mapping to each of the enum objects.
   * For this enum it assigns values to OLD & NEW enums the numerical maps.
   *
   * @param updateValue Integer representing enum map for the diff updates: OLD & NEW.
   */
  UpdateType(int updateValue) {
    this.updateValue = updateValue;
  }
}
