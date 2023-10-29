package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Model.Enums.ColorMapping;
import Model.ImageOperations.BrightnessProfilerImage;
import Model.ImageOperations.CombineChannelImage;
import Model.ImageOperations.FlipImage;
import Model.ImageOperations.GreyScaleImage;
import Model.ImageOperations.ImageOperation;
import Model.ImageOperations.MonochromeImage;
import Model.ImageOperations.MultipleImagesIPOperation;
import Model.ImageOperations.MultipleImagesOPOperation;
import Model.ImageOperations.SharpenImage;
import Model.ImageOperations.SplitChannelImage;

public class RGBImage implements RGBImageInterface{

  private int [][][] pixelMatrix;

  private int height;

  private int width;

  private final int maxValue=255;

  public RGBImage(String filePath) throws IOException {
    String extension=getFileExtension(filePath);
    if(extension.equals("ppm")){
      ppmFileReader(filePath);
    }
    else {
      imageIOFileReader(filePath);
    }
  }

  public RGBImage(int [][][] pixelMatrix){
    this.pixelMatrix=pixelMatrix;
    this.height=pixelMatrix.length;
    this.width=pixelMatrix[0].length;
  }

  private String getFileExtension(String filePath){
    int index=filePath.lastIndexOf('.');
    if(index!=-1){
      return filePath.substring(index+1);
    }
    else return "";
  }

  private void ppmFileReader(String filePathPPM) throws RuntimeException{
    Scanner sc=null;
    try{
      sc=new Scanner(new FileInputStream(filePathPPM));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    StringBuilder builder= new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s).append(System.lineSeparator());
      }
    }
    sc=new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    this.width = sc.nextInt();

    this.height = sc.nextInt();

    int tempMaxValue = sc.nextInt();


    this.pixelMatrix=new int [height][width][ColorMapping.values().length];

    helperImageMatrix(sc, height, width, pixelMatrix);
  }

  private void helperImageMatrix(Scanner sc, int height, int width, int[][][] pixelMatrix) {
    for (int i = 0; i< height; i++) {
      for (int j = 0; j< width; j++) {
        pixelMatrix[i][j][ColorMapping.red.ordinal()]=sc.nextInt();
        pixelMatrix[i][j][ColorMapping.green.ordinal()]=sc.nextInt();
        pixelMatrix[i][j][ColorMapping.blue.ordinal()]=sc.nextInt();
      }
    }
  }

  private void imageIOFileReader(String filePathImageIO) throws IOException {
    BufferedImage imageElement;
    FileInputStream fileName;
    try{
      fileName=new FileInputStream(filePathImageIO);
    }
    catch (FileNotFoundException ex){
      throw new FileNotFoundException("Error in file reading");
    }
    try{
      imageElement= ImageIO.read(fileName);
    }
    catch (IOException ex){
      throw new IOException("Unable to get image from the mentioned file path.");
    }
    this.height=imageElement.getHeight();
    this.width=imageElement.getWidth();
    this.pixelMatrix=getPixelMatrixBuffer(imageElement);

  }

  private int [][][] getPixelMatrixBuffer(BufferedImage imageElement){
    int imageHeight=imageElement.getHeight();
    int imageWidth=imageElement.getWidth();
    int [][][] imagePixelMatrix=new int[imageHeight][imageWidth][ColorMapping.values().length];
    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        int rgbCellValue=imageElement.getRGB(j,i);
        imagePixelMatrix[i][j][ColorMapping.red.ordinal()]=(rgbCellValue>>16)&255;
        imagePixelMatrix[i][j][ColorMapping.green.ordinal()]=(rgbCellValue>>8)&255;
        imagePixelMatrix[i][j][ColorMapping.blue.ordinal()]=(rgbCellValue)&255;
      }
    }
    return imagePixelMatrix;
  }

  private void savePPMImage(String filePath) throws IOException {
    FileOutputStream fileStream=null;
    try{
      fileStream=new FileOutputStream(filePath);
    }
    catch (FileNotFoundException ex){
      throw new FileNotFoundException("File does not exists in the system.");
    }
    StringBuilder ppmImageData=convertImageMatrixToString(height,width,pixelMatrix);
    fileStream.write(new String(ppmImageData).getBytes());
  }

  private StringBuilder convertImageMatrixToString(int imageHeight, int imageWidth, int[][][] pixelImageMatrix){
    StringBuilder s=new StringBuilder();

    s.append("P3 ").append(imageWidth).append(" ").append(imageHeight).append("\n255\n");

    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        StringBuilder singlePixelColorCode=new StringBuilder();
        for(int k=0;k<ColorMapping.values().length;k++){
          singlePixelColorCode.append(" ").append(pixelImageMatrix[i][j][k]).append(" ");
        }
        s.append(singlePixelColorCode);
      }
    }
    return s;
  }

  private void saveBufferImage(String imageFilePath,String imageExtension) throws IOException {
    BufferedImage imageSavingElement = getBufferedImage(width, height);
    try{
      File filePath= new File(imageFilePath);
      ImageIO.write(imageSavingElement,imageExtension,filePath);
    }
    catch(IOException ex){
      throw new IOException("Unable to save the file to the path mentioned");
    }
  }

  private BufferedImage getBufferedImage(int width, int height) {
    BufferedImage imageSavingElement=new BufferedImage(width, height,BufferedImage.TYPE_3BYTE_BGR);
    for(int i = 0; i< height; i++){
      for(int j = 0; j< width; j++){
        int redPixelValue=pixelMatrix[i][j][ColorMapping.red.ordinal()];
        int greenPixelValue=pixelMatrix[i][j][ColorMapping.green.ordinal()];
        int bluePixelValue=pixelMatrix[i][j][ColorMapping.blue.ordinal()];
        int rgbPixel=(redPixelValue<<16 | greenPixelValue<<8 | bluePixelValue);
        imageSavingElement.setRGB(j,i,rgbPixel);
      }
    }
    return imageSavingElement;
  }

  @Override
  public void saveImage(String imagePath, String ImageName) throws IOException {
    String imageExtension=getFileExtension(imagePath);
    if(imageExtension.equals("ppm")){
      savePPMImage(imagePath);
    }
    else{
      saveBufferImage(imagePath,imageExtension);
    }
  }

  @Override
  public RGBImageInterface flipImage(int axisDirection) throws IOException {
    ImageOperation imageOperation=new FlipImage(axisDirection);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface changeBrightness(int deltaChangeValue) throws IOException {
    ImageOperation imageOperation=new BrightnessProfilerImage(deltaChangeValue);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface changeSharpness(int kernelType) throws IOException {
    ImageOperation imageOperation=new SharpenImage(kernelType);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents) {

    MultipleImagesIPOperation imageOperation=new CombineChannelImage();
    imageComponents.add(0,this);
    return imageOperation.operation(imageComponents);
  }

  @Override
  public List<RGBImageInterface> splitImageComponents() throws IOException {
    MultipleImagesOPOperation multiOP=new SplitChannelImage();
    return multiOP.operation(this);
  }

  @Override
  public RGBImageInterface getSingleComponentImage(int colorValue) throws IOException {
    ImageOperation imageOperation=new MonochromeImage(colorValue);
    return imageOperation.operation(this);
  }

  @Override
  public RGBImageInterface greyScaleImage(int greyScaleType) throws IOException {
    ImageOperation imageOperation=new GreyScaleImage(0);
    return imageOperation.operation(this);
  }

  @Override
  public int getImageHeight(){
    return this.height;
  }

  @Override
  public int getImageWidth(){
    return this.width;
  }

  @Override
  public int [][][] getPixel(){
    int [][][] copyPixelMatrix=new int [height][width][ColorMapping.values().length];
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){
        for(int k=0;k<ColorMapping.values().length;k++){
          copyPixelMatrix[i][j][k]=pixelMatrix[i][j][k];
        }
      }
    }
    return copyPixelMatrix;
  }

  @Override
  public int getMaxValue(){
    return this.maxValue;
  }

  public static void main(String [] args) throws IOException {
//    String filePathRed="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/ManRed.png";
//    RGBImageInterface imageInterfaceRed=new RGBImage(filePathRed);
//
//    String filePathGreen="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/ManGreen.png";
//    RGBImageInterface imageInterfaceGreen=new RGBImage(filePathGreen);
//
//    String filePathBlue="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/ManBlue.png";
//    RGBImageInterface imageInterfaceBlue=new RGBImage(filePathBlue);
//
//    ArrayList<RGBImageInterface> arrayList=new ArrayList<>();
//    arrayList.add(imageInterfaceGreen);
//    arrayList.add(imageInterfaceBlue);

//    RGBImageInterface result=imageInterfaceRed.combineImageComponents(arrayList);
//    RGBImageInterface red=imageInterface.getSingleComponentImage(0);
//    RGBImageInterface green=imageInterface.getSingleComponentImage(1);
//    RGBImageInterface blue=imageInterface.getSingleComponentImage(2);
//    RGBImageInterface brighter=imageInterface.changeBrightness(-500);
//    RGBImageInterface flip=imageInterface.flipImage(1);
//    flip=flip.flipImage(0);
//    result.saveImage("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/ManCombi.png","Manhattan");

//    String filePathRed="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/Koala.png";
//    RGBImageInterface imageInterface=new RGBImage(filePathRed);
//    ArrayList<RGBImageInterface>result= (ArrayList<RGBImageInterface>) imageInterface.splitImageComponents();
//    for(int i=0;i<result.size();i++){
//      String filedir="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/Koala"+i+".png";
//      result.get(i).saveImage(filedir,"abc");
//    }
    String filePathBlue="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/Manhattan.png";
    RGBImageInterface imageInterfaceBlue=new RGBImage(filePathBlue);
    RGBImageInterface result=imageInterfaceBlue.changeSharpness(1);
    result=result.changeSharpness(1);
//    result=result.changeSharpness(1);
//    result=result.changeSharpness(1);
//    result=result.changeSharpness(1);
//    result=result.changeSharpness(1);
    String filedir="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing/ManhattanSharp.jpg";
    result.saveImage(filedir,"abc");
  }
}
