import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import Controller.RGBImageController;
import Controller.RGBImageControllerInterface;

public class ImageProcessingApplication {
  public static void main(String[] args) throws FileNotFoundException, IOException {
    InputStream in;

    OutputStream out=System.out;

    RGBImageControllerInterface controller=null;

    if(args.length>0){
      String filePath=args[0];
      try{
        File commandFile=new File(filePath);
        in= new FileInputStream(commandFile);
      }
      catch (FileNotFoundException ex){
        throw new FileNotFoundException("File was not found at the passed location.");
      }
    }
    else {
      in=System.in;
    }
    controller=new RGBImageController(in,out);
    controller.go();
  }
}
