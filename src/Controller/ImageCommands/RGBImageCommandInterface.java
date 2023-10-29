package Controller.ImageCommands;

import java.io.IOException;
import java.util.Map;

import Model.RGBImageInterface;

public interface RGBImageCommandInterface {

  void execute(Map<String, RGBImageInterface> cachedImage) throws IOException;
}
