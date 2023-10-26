package folder.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;


public class Image implements ImageInterface{

  int [][][]imagePixelMatrix;

  private final static double []lumaWeights={0.2126, 0.7152,0.0722};

  public enum colorMapping{
    red(0),
    green(1),
    blue(2);

    final int colorCoding;

    colorMapping(int colorCoding){
      this.colorCoding=colorCoding;
    }
  }

  public Image(String filePath) throws IOException {
    String fileExtension=getFileExtension(filePath);
    if(fileExtension.equals("ppm")){
      imagePixelMatrix=getImagePixelMatrixPPMImage(filePath);
    }
    else {
      imagePixelMatrix=getImagePixelMatrixBufferImage(filePath);
    }

  }

  private Image(int [][][] mat){
    int zLength=mat.length;
    int xLength=mat[0].length;
    int yLength=mat[0][1].length;
    this.imagePixelMatrix=new int [zLength][xLength][yLength];
    for(int z=0;z<zLength;z++){
      for(int x=0;x<xLength;x++){
        for(int y=0;y<yLength;y++){
          imagePixelMatrix[z][x][y]=mat[z][x][y];
        }
      }
    }
  }

  private String getFileExtension(String filePath){
    int index=filePath.lastIndexOf('.');
    if(index!=-1){
      return filePath.substring(index+1);
    }
    else return "";
  }

  private StringBuilder storeFileCharacters(Scanner sc){
    StringBuilder builder = new StringBuilder();

    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }
    return builder;
  }

  private int[][][] parseMetaDataGetEmptyPixelMatrix(Scanner sc){
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    return new int [colorMapping.values().length][height][width];
  }

  private int [][][] getImagePixelMatrixPPMImage(String filePath){
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(filePath));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filePath+ " not found!");
      return null;
    }
    StringBuilder builder = storeFileCharacters(sc);
    sc = new Scanner(builder.toString());
    int [][][] imagePixelMatrix=parseMetaDataGetEmptyPixelMatrix(sc);
    for (int i=0;i<imagePixelMatrix[0].length;i++) {
      for (int j=0;j<imagePixelMatrix[0][0].length;j++) {
        imagePixelMatrix[colorMapping.red.ordinal()][i][j] = sc.nextInt();
        imagePixelMatrix[colorMapping.green.ordinal()][i][j] = sc.nextInt();
        imagePixelMatrix[colorMapping.blue.ordinal()][i][j] = sc.nextInt();
      }
    }
    return imagePixelMatrix;
  }

  private int [][][] getImagePixelMatrixBufferImage(String filePath) throws FileNotFoundException, IOException {
    BufferedImage imageElement;
    FileInputStream fileName;
    try{
      fileName=new FileInputStream(filePath);
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
    return getPixelMatrixBuffer(imageElement);
  }

  private int [][][] getPixelMatrixBuffer(BufferedImage imageElement){
    int imageHeight=imageElement.getHeight();
    int imageWidth=imageElement.getWidth();
    int [][][] imagePixelMatrix=new int[colorMapping.values().length][imageHeight][imageWidth];
    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        int rgbCellValue=imageElement.getRGB(j,i);
        imagePixelMatrix[colorMapping.red.ordinal()][i][j]=(rgbCellValue>>16)&255;
        imagePixelMatrix[colorMapping.green.ordinal()][i][j]=(rgbCellValue>>8)&255;
        imagePixelMatrix[colorMapping.blue.ordinal()][i][j]=(rgbCellValue)&255;
      }
    }
    return imagePixelMatrix;
  }

  @Override
  public void save(String imagePath, String imageName) throws IOException {
    int height= imagePixelMatrix[0].length;
    int width=imagePixelMatrix[0][1].length;

    BufferedImage imageSavingElement = getBufferedImage(width, height);
    try{
      String imageFilePath=imagePath+imageName;
      File filePath= new File(imageFilePath);
      ImageIO.write(imageSavingElement,"png",filePath);
    }
    catch(IOException ex){
      throw new IOException("Unable to save the file to the path mentioned");
    }
  }

  private BufferedImage getBufferedImage(int width, int height) {
    BufferedImage imageSavingElement=new BufferedImage(width, height,BufferedImage.TYPE_3BYTE_BGR);
    for(int i = 0; i< height; i++){
      for(int j = 0; j< width; j++){
        int redPixelValue=imagePixelMatrix[colorMapping.red.ordinal()][i][j];
        int greenPixelValue=imagePixelMatrix[colorMapping.green.ordinal()][i][j];
        int bluePixelValue=imagePixelMatrix[colorMapping.blue.ordinal()][i][j];
        int rgbPixel=(redPixelValue<<16 | greenPixelValue<<8 | bluePixelValue);
        imageSavingElement.setRGB(j,i,rgbPixel);
      }
    }
    return imageSavingElement;
  }

  @Override
  public ImageInterface selectColorComponentImage(int colorIndex) {
    Image redImage=new Image(imagePixelMatrix);
    for(int i=0;i<colorMapping.values().length;i++){
      if(i!=colorIndex){
        redImage.removeColorComponent(redImage.imagePixelMatrix,i);
      }
    }
    return redImage;
  }

  private void removeColorComponent(int [][][]pixelMatrix, int colorComponent){
    int imageHeight=pixelMatrix[colorComponent].length;
    int imageWidth=pixelMatrix[colorComponent][0].length;
    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        pixelMatrix[colorComponent][i][j]=0;
      }
    }
  }

  @Override
  public ImageInterface horizontalFlipImage() {
    Image horizontalFlip=new Image(this.imagePixelMatrix);
    int [][][] pixelMat=horizontalFlip.imagePixelMatrix;
    int totalColors=colorMapping.values().length;
    int imageHeight=pixelMat[0].length;
    int imageWidth=pixelMat[0][0].length;
    for(int i=0;i<totalColors;i++){
      for(int j=0;j<imageHeight;j++){
        for(int k=0;k<imageWidth/2;k++){
          int val=pixelMat[i][j][k];
          pixelMat[i][j][k]=pixelMat[i][j][imageWidth-1-k];
          pixelMat[i][j][imageWidth-1-k]=val;
        }
      }
    }

    return horizontalFlip;
  }

  @Override
  public ImageInterface verticalFlipImage() {
    Image verticalFlip=new Image(this.imagePixelMatrix);
    int [][][] pixelMat=verticalFlip.imagePixelMatrix;
    int totalColors=colorMapping.values().length;
    int imageHeight=pixelMat[0].length;
    int imageWidth=pixelMat[0][0].length;
    for(int i=0;i<totalColors;i++){
      for(int j=0;j<imageHeight/2;j++){
        for(int k=0;k<imageWidth;k++){
          int val=pixelMat[i][j][k];
          pixelMat[i][j][k]=pixelMat[i][imageHeight-1-j][k];
          pixelMat[i][imageHeight-1-j][k]=val;
        }
      }
    }

    return verticalFlip;
  }

  @Override
  public ImageInterface changeImageBrightness(int deltaIncrement) {
    Image imageBrightness=new Image(this.imagePixelMatrix);
    int [][][] pixelMat=imageBrightness.imagePixelMatrix;
    int totalColors=colorMapping.values().length;
    int imageHeight=pixelMat[0].length;
    int imageWidth=pixelMat[0][0].length;
    for(int i=0;i<totalColors;i++){
      for(int j=0;j<imageHeight;j++){
        for(int k=0;k<imageWidth;k++){
          pixelMat[i][j][k]+=deltaIncrement;
          if(pixelMat[i][j][k]>255){
            pixelMat[i][j][k]=255;
          }
          if(pixelMat[i][j][k]<0){
            pixelMat[i][j][k]=0;
          }
        }
      }
    }
    return imageBrightness;
  }

  @Override
  public ImageInterface valueGreyScaleImage() {
    Image imageValue=new Image(this.imagePixelMatrix);
    int [][][] pixelMat=imageValue.imagePixelMatrix;
    int totalColors=colorMapping.values().length;
    int imageHeight=pixelMat[0].length;
    int imageWidth=pixelMat[0][0].length;
    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        int maxComponentValue=0;
        for(int k=0;k<totalColors;k++){
          maxComponentValue=Math.max(maxComponentValue,pixelMat[k][i][j]);
        }
        for(int k=0;k<totalColors;k++){
          pixelMat[k][i][j]=maxComponentValue;
        }
      }
    }
    return  imageValue;
  }

  @Override
  public ImageInterface lumaGreyScaleImage() {
    Image imageValue=new Image(this.imagePixelMatrix);
    int [][][] pixelMat=imageValue.imagePixelMatrix;
    int totalColors=colorMapping.values().length;
    int imageHeight=pixelMat[0].length;
    int imageWidth=pixelMat[0][0].length;
    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        double componentWeightedSum=0;
        for(int k=0;k<totalColors;k++){
          componentWeightedSum+=lumaWeights[k]*pixelMat[k][i][j];
        }
        for(int k=0;k<totalColors;k++){
          pixelMat[k][i][j]=(int)componentWeightedSum;
        }
      }
    }
    return  imageValue;
  }

  @Override
  public ImageInterface intensityGreyScaleImage() {
    Image imageValue=new Image(this.imagePixelMatrix);
    int [][][] pixelMat=imageValue.imagePixelMatrix;
    int totalColors=colorMapping.values().length;
    int imageHeight=pixelMat[0].length;
    int imageWidth=pixelMat[0][0].length;
    for(int i=0;i<imageHeight;i++){
      for(int j=0;j<imageWidth;j++){
        int maxComponentValue=0;
        for(int k=0;k<totalColors;k++){
          maxComponentValue+=pixelMat[k][i][j];
        }
        maxComponentValue/=totalColors;
        for(int k=0;k<totalColors;k++){
          pixelMat[k][i][j]=maxComponentValue;
        }
      }
    }
    return  imageValue;
  }



  public static void main(String []args) throws IOException {
//    String filePath="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/manhattan-small-blur.png";
//    Image obj=new Image(filePath);
//    String fileDir="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/";
//    String imageName="NEUMan.png";
//    obj.save(fileDir,imageName)
//    ;
    String filePath="/Users/omagarwal/Downloads/code-3 2/manhattan-small.png";
    Image obj=new Image(filePath);
    obj.selectColorComponentImage(1).save("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/Image Processing Application/src/folder/Model/","manhattan-small-blur.png");
  }
}
