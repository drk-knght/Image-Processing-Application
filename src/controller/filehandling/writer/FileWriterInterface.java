package controller.filehandling.writer;

import java.io.IOException;

import model.RGBImageInterface;

public interface FileWriterInterface {

  void write(RGBImageInterface image) throws IOException;
}
