package folder.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method 
 *  as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file. 
   */
  StringBuilder matrix=new StringBuilder();
  public  void readPPM(String filename) {
    Scanner sc;
    
    try {
        sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
        System.out.println("File "+filename+ " not found!");
        return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0)!='#') {
            builder.append(s+System.lineSeparator());
        }
    }
    
    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token; 

    token = sc.next();
    if (!token.equals("P3")) {
        System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);
    int [][] redImage=new int[height][width];
    for (int i=0;i<height;i++) {
        for (int j=0;j<width;j++) {
            int r = sc.nextInt();
            redImage[i][j]=r;
            int g = sc.nextInt();
            int b = sc.nextInt();
            System.out.println("Color of pixel ("+j+","+i+"): "+ r+","+g+","+b);
        }
    }

  }

  public  void createImage(){
    Scanner sc;

    String filename="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/Koala.ppm";
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    matrix .append("P3 " + width + " " +  height+ "\n255\n");
    ArrayList pic = new ArrayList();
    int[] rgb = new int[3];

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
//        rgb[0]=r;
        matrix.append( " "+ r + " " + 0 + " " + 0 + "  " );
        System.out.println("Height: "+i+" width: "+j+" rValue:"+r);
      }
    }
    matrix.append( "\n");

  }

   void createColorRepresentation(){
    Scanner sc;

    String filename="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/Koala.ppm";
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    matrix .append("P3 " + width + " " +  height+ "\n255\n");
    ArrayList pic = new ArrayList();
    int[] rgb = new int[3];

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
//        if(r==Math.max(r,Math.max(g,b))){
//          matrix.append( " "+ r + " " + r + " " + r + "  " );
//        }
//        else if(g==Math.max(r,Math.max(g,b))){
//          matrix.append( " "+ g + " " + g + " " + g + "  " );
//        }
//        else {
//          matrix.append( " "+ b + " " + b + " " + b + "  " );
//        }
        matrix.append(lumaImageTransformation(r,g,b));
        System.out.println("Height: "+i+" width: "+j+" rValue:"+r);
      }
    }
    matrix.append( "\n");

  }

  private String valueImageTransformation(int r, int g, int b){
    String res;
    if(r==Math.max(r,Math.max(g,b))){
      res=" "+ r + " " + r + " " + r + "  " ;
    }
    else if(g==Math.max(r,Math.max(g,b))){
      res=" "+ g + " " + g + " " + g + "  " ;
    }
    else {
      res= " "+ b + " " + b + " " + b + "  " ;
    }
    return  res;
  }

  private String IntensityImageTransformation(int r, int g,int b){
    int avg=(r+g+b)/3;
    return " "+avg+" "+avg+" "+avg+" ";
  }

  private String lumaImageTransformation(int r,int g,int b){
//    float pixelValue=0.2126*r+0.7152*g+0.0722*b;
    int pixelValue=(int)(0.2126*r+0.7152*g+0.0722*b);
    return " "+pixelValue+" "+pixelValue+" "+pixelValue;
  }

  private void flipVertical(){
    Scanner sc;

    String filename="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/Koala.ppm";
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    matrix.append("P3 " + width + " " +  height+ "\n255\n");

    int matrixVerical[][][]=new int [height][width][3];
    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        matrixVerical[height-1-i][j][0]=r;
        matrixVerical[height-1-i][j][1]=g;
        matrixVerical[height-1-i][j][2]=b;
//        if(r==Math.max(r,Math.max(g,b))){
//          matrix.append( " "+ r + " " + r + " " + r + "  " );
//        }
//        else if(g==Math.max(r,Math.max(g,b))){
//          matrix.append( " "+ g + " " + g + " " + g + "  " );
//        }
//        else {
//          matrix.append( " "+ b + " " + b + " " + b + "  " );
//        }

//        matrix.append(lumaImageTransformation(r,g,b));
        System.out.println("Height: "+i+" width: "+j+" rValue:"+r);
      }
    }
    for(int i=0;i<height;i++){
      for(int j=0;j<width;j++){

        matrix.append(" "+matrixVerical[i][j][0]+" "+matrixVerical[i][j][1]+" "+matrixVerical[i][j][2]+" ");
      }
    }
    matrix.append( "\n");
  }

  private void brightenImage(int brightFactor){
    Scanner sc;

    String filename="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/Koala.ppm";
    try {
      sc = new Scanner(new FileInputStream(filename));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename+ " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0)!='#') {
        builder.append(s+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);

    matrix .append("P3 " + width + " " +  height+ "\n255\n");

    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r = Math.min(sc.nextInt()+brightFactor,255);
        int g = Math.min(sc.nextInt()+brightFactor,255);
        int b = Math.min(sc.nextInt()+brightFactor,255);

        matrix.append( " "+ r + " " + g + " " + b + "  " );
        System.out.println("Height: "+i+" width: "+j+" rValue:"+r);
      }
    }

  }

  private void imageComparison(){
    Scanner sc1,sc2;

    String filename1="/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/assignment1_q1_Luma.ppm";
    String filename2="/Users/omagarwal/Downloads/code-10/koala-luma-greyscale.png";
    try {
      sc1 = new Scanner(new FileInputStream(filename1));
      sc2 = new Scanner(new FileInputStream(filename2));
    }
    catch (FileNotFoundException e) {
      System.out.println("File "+filename1+ " not found!");
      return;
    }
    StringBuilder builder1 = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc1.hasNextLine()) {
      String s = sc1.nextLine();
      if (s.charAt(0)!='#') {
        builder1.append(s+System.lineSeparator());
      }
    }

    StringBuilder builder2 = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc2.hasNextLine()) {
      String s2 = sc2.nextLine();
      if (s2.charAt(0)!='#') {
        builder2.append(s2+System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc1 = new Scanner(builder1.toString());
    sc2 = new Scanner(builder2.toString());

    String token1,token2;

    token1 = sc1.next();
    token2=sc2.next();
    if (!token1.equals("P3") || !token2.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc1.nextInt();
    width=sc2.nextInt();
    System.out.println("Width of image: "+width);
    int height = sc1.nextInt();
    height = sc2.nextInt();
    System.out.println("Height of image: "+height);
    int maxValue = sc1.nextInt();
    maxValue = sc2.nextInt();
    System.out.println("Maximum value of a color in this file (usually 255): "+maxValue);


    for (int i=0;i<height;i++) {
      for (int j=0;j<width;j++) {
        int r1 = sc1.nextInt();
        int g1 = sc1.nextInt();
        int b1 = sc1.nextInt();

        int r2 = sc2.nextInt();
        int g2 = sc2.nextInt();
        int b2 = sc2.nextInt();

        System.out.println("Printing for cell...: "+i+" "+j);
        System.out.println("Difference in R: "+(r1-r2));
        System.out.println("Difference in B: "+(b1-b2));
        System.out.println("Difference in G: "+(g1-g2));
      }
    }
  }


  public  void writeImage(String fn) throws FileNotFoundException, IOException {

    //if (pic != null) {

    FileOutputStream fos = new FileOutputStream(fn);

    System.out.println("Line number: 177");
    fos.write(new String(matrix).getBytes());

    //fos.write(data.length);
    //System.out.println(data.length);
    fos.close();
    // }
  }

  //demo main
//  public static void main(String []args) {
//      String filename;
//
//      if (args.length>0) {
//          filename = args[0];
//      }
//      else {
//          filename = "/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/Koala.ppm";
//      }
//
//      ImageUtil.readPPM(filename);
//  }
  public static void main(String[] args) throws IOException {
//    createImage();
    ImageUtil util=new ImageUtil();
//    util.imageComparison();
    util.brightenImage(100);
    String filename="assignment1_q1_Brightness.ppm";

    util.writeImage(filename);
    System.out.println("Filename: "+filename);
  }
}

