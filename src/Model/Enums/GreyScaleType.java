package Model.Enums;

import java.util.Arrays;
import java.util.function.Function;

public enum GreyScaleType {
  value(0){
    @Override
    public int[] calculateReturnPixelValue(int[] rgbPixelArray) {
      return GreyScaleType.setPixelValue( rgbPixelArray,
              ar->
              {
                int num=0;
                for(int ele=0;ele<ar.length;ele++){
                  num=Math.max(num,ar[ele]);
                }
                System.out.println("num: "+num);
                return num;
              }
      );

    }
  },

  luma(1){
    @Override
    public int[] calculateReturnPixelValue(int[] rgbPixelArray) {
      return GreyScaleType.setPixelValue( rgbPixelArray,
              ar->
              {
                double num=0.2125*rgbPixelArray[ColorMapping.red.ordinal()]+
                        0.7152*rgbPixelArray[ColorMapping.green.ordinal()]+
                        0.0722*rgbPixelArray[ColorMapping.blue.ordinal()];

                System.out.println("num: "+num);
                return (int)num;
              }
      );
    }
  },

  intensity(2){
    @Override
    public int[] calculateReturnPixelValue(int[] rgbPixelArray) {
      return GreyScaleType.setPixelValue( rgbPixelArray,
              ar->
              {
                int num=0;
                for(int ele=0;ele<ar.length;ele++){
                  num+=ar[ele];
                }
                System.out.println("num: "+num);
                return num/3;
              }
      );
    }

  };

  final int scaleValue;

  abstract public int[] calculateReturnPixelValue(int [] rgbPixelArray);

  private static int [] setPixelValue(int [] pixelArray, Function<int [],Integer> greyConverter){
    int pixelValue=greyConverter.apply(pixelArray);
    Arrays.fill(pixelArray, pixelValue);
    return pixelArray;
  }

  GreyScaleType(int scaleValue){
    this.scaleValue=scaleValue;
  }
}
