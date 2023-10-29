//package Model;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//import java.util.Scanner;
//
//public class PPMUtil {
//
////  public static int [][][] readPPMFilePath(String filePathPPM){
////    Scanner sc;
////    try {
////      sc = new Scanner(new FileInputStream(filePathPPM));
////    }
////    catch (FileNotFoundException e) {
////      System.out.println("File "+filePathPPM+ " not found!");
////      return null;
////    }
////    StringBuilder builder = storeFileCharactersFromFile(sc);
////    sc = new Scanner(builder.toString());
////    int [][][] imagePixelMatrix=parseMetaDataGetEmptyPixelMatrix(sc);
////    for (int i=0;i<imagePixelMatrix[0].length;i++) {
////      for (int j=0;j<imagePixelMatrix[0][0].length;j++) {
////        imagePixelMatrix[RGBImage.colorMapping.red.ordinal()][i][j] = sc.nextInt();
////        imagePixelMatrix[RGBImage.colorMapping.green.ordinal()][i][j] = sc.nextInt();
////        imagePixelMatrix[RGBImage.colorMapping.blue.ordinal()][i][j] = sc.nextInt();
////      }
////    }
////    return imagePixelMatrix;
////  }
//
//  public static RGBImageInterface ppmFileReader(String filePathPPM) throws RuntimeException{
//    Scanner sc=null;
//    try{
//      sc=new Scanner(new FileInputStream(filePathPPM));
//    } catch (FileNotFoundException e) {
//      throw new RuntimeException(e);
//    }
//    StringBuilder builder= new StringBuilder();
//
//    while (sc.hasNextLine()) {
//      String s = sc.nextLine();
//      if (s.charAt(0)!='#') {
//        builder.append(s).append(System.lineSeparator());
//      }
//    }
//    sc=new Scanner(builder.toString());
//
//    String token;
//
//    token = sc.next();
//    if (!token.equals("P3")) {
//      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
//    }
//    int width = sc.nextInt();
//    System.out.println("Width of image: "+width);
//    int height = sc.nextInt();
//    System.out.println("Height of image: "+height);
//    int maxValue = sc.nextInt();
//    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);
//
//    int [][][] pixelImageMatrix=new int [RGBImage.colorMapping.values().length][height][width];
//
//    RGBImage.helperImageMatrix(sc, height, width, pixelImageMatrix);
//
//    return new RGBImage(height,width,pixelImageMatrix);
//  }
//
//  private static StringBuilder convertImageMatrixToString(int imageHeight, int imageWidth, int[][][] pixelImageMatrix){
//    StringBuilder s=new StringBuilder();
//
//    s.append("P3 ").append(imageWidth).append(" ").append(imageHeight).append("\n255\n");
//
//    for(int i=0;i<imageHeight;i++){
//      for(int j=0;j<imageWidth;j++){
//        StringBuilder singlePixelColorCode=new StringBuilder();
//        for(int k=0;k<RGBImage.colorMapping.values().length;k++){
//          singlePixelColorCode.append(" ").append(pixelImageMatrix[i][j][k]).append(" ");
//        }
//        s.append(singlePixelColorCode);
//      }
//    }
//    return s;
//  }
//
//  public static void ppmFileWriter(String filePath, int imageHeight, int imageWidth, int[][][] pixelImageMatrix) throws FileNotFoundException {
//    OutputStream fileStream=null;
//    try{
//      fileStream=new FileOutputStream(filePath);
//    }
//    catch (FileNotFoundException ex){
//      new FileOutputStream("File does not exists in the system.");
//    }
//    StringBuilder ppmImageData=convertImageMatrixToString(imageHeight,imageWidth,pixelImageMatrix);
//  }
//}
