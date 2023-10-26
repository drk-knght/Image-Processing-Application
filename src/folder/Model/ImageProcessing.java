package folder.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessing {
  public ImageProcessing() throws IOException {
    File obj=new File("/Users/omagarwal/Desktop/Grad@NEU/Acads/Sem-1/CS 5010 PDP/Labs/AssignmentsPDP/src/folder/Model/Koala.ppm");
    BufferedImage image= ImageIO.read(obj);

  }
}
