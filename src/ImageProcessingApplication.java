import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import controller.RGBImageController;
import controller.RGBImageControllerInterface;

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
  public static void main(String[] args) throws IOException {
    InputStream in=System.in;

    OutputStream out = System.out;

    RGBImageControllerInterface controller = null;

    if (args.length > 0) {
      String fileFlag=args[0];
      String filePath = args[1];
      if(!fileFlag.equals("-file")){
        String wrongFlag="Wrong flag parameter passed as input through the input source."
                + " Try again.\n";
        out.write(wrongFlag.getBytes());
        return;
      }
      try {
        File commandFile = new File(filePath);
        in = new FileInputStream(commandFile);
      } catch (FileNotFoundException ex) {
        String msg="File was not found at the passed location.\n";
        out.write(msg.getBytes());
      }
    }
    controller = new RGBImageController(in, out);
    controller.goCall();
  }
}
