package enums;


public enum LevelAdjustment {

  BLACK(1),
  MID(2),
  HIGHLIGHT(3);

  final public int levelValue;

  LevelAdjustment(int levelValue){
    this.levelValue=levelValue;
  }
}
