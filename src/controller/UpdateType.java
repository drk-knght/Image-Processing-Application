package controller;

public enum UpdateType {
  OLD(0),
  NEW(1);

  final int updateValue;

  UpdateType(int updateValue){
    this.updateValue=updateValue;
  }
}
