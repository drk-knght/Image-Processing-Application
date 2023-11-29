import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import controller.graphicalcontroller.GraphicalController;
import controller.scriptcontroller.RGBImageController;
import controller.RGBImageControllerInterface;
import view.GraphicalView;
import view.IView;

/**
 * The class represents the starting point of the image processing application.
 * The main method here initializes the states of model with inputStream and outputStream.
 * Then it provides the responsibilities to the controller where all the work is bifurcated.
 */
public class ImageProcessingApplication {

  /**
   * Main method where the controller is initialized and provided the access to start the app.
   *
   * @param args The command line arguments if any passed to the main method.
   * @throws IOException Throws exception if the file path in args in invalid or not exists.
   */

  public static void main(String [] args) throws IOException {
    RGBImageControllerInterface controller=null;
    if(args.length>0){
      OutputStream out=System.out;
      if(args[0].equals("-file")){
        String filePath = args[1];
        File commandFile = new File(filePath);
        InputStream in = new FileInputStream(commandFile);
        controller=new RGBImageController(in,out);
      }
      else if(args[0].equals("-text")){
        InputStream in=System.in;
        controller=new RGBImageController(in,out);
      }
      else{
        System.out.println("Illegal flags passed for the script controller.Try again.");
        return;
      }
    }
    else {
      IView view=new GraphicalView();
      controller=new GraphicalController(view);

    }
    controller.goCall();
  }
}
