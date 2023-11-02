package Controller;


import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.ImageCommands.MultiIN.CombineChannelsController;
import Controller.ImageCommands.MultiOUT.SplitChannelsController;
import Controller.ImageCommands.RGBImageCommandInterface;
import Controller.ImageCommands.SingleIN.BrightnessController;
import Controller.ImageCommands.SingleIN.ColorTransformationController;
import Controller.ImageCommands.SingleIN.FlipImageController;
import Controller.ImageCommands.SingleIN.GreyScaleController;
import Controller.ImageCommands.SingleIN.RGBFilterController;
import Controller.ImageCommands.SingleIN.SharpnessController;
import Model.RGBImageInterface;

import static org.junit.Assert.assertEquals;

public class ControllerModelMock {

  static class MockModel implements RGBImageInterface{

    StringBuilder logData;

    RGBImageInterface imageObj;

    public MockModel(StringBuilder logData,RGBImageInterface imageObj){
      this.logData=logData;
      this.imageObj=imageObj;
      this.logData.append("loading the image.");
    }

    @Override
    public void saveImage(String imagePath) throws IOException {
      logData.append("Save path:"+imagePath);
    }

    @Override
    public RGBImageInterface flipImage(int axisDirection) throws IllegalArgumentException {
      logData.append("Flip image operation: "+axisDirection);
      return this.imageObj;
    }

    @Override
    public RGBImageInterface changeBrightness(int deltaChangeValue) {
      logData.append("Brightness image operation: "+deltaChangeValue);
      return this.imageObj;
    }

    @Override
    public RGBImageInterface changeSharpness(int kernelType) throws IllegalArgumentException {
      logData.append("Sharpness image operation: "+kernelType);
      return this.imageObj;
    }

    @Override
    public RGBImageInterface combineImageComponents(List<RGBImageInterface> imageComponents) throws IllegalArgumentException {
      logData.append("Combine image operation on a list of images having size:"+imageComponents.size());
      return this.imageObj;
    }

    @Override
    public List<RGBImageInterface> splitImageComponents() throws IOException {
      logData.append("Split operation on an image.");
      ArrayList<RGBImageInterface> arr=new ArrayList<>();
      arr.add(imageObj);
      arr.add(imageObj);
      arr.add(imageObj);
      return arr;
    }

    @Override
    public RGBImageInterface getSingleComponentImage(int colorValue) throws IllegalArgumentException {
      logData.append("Get single component on an image: "+colorValue);
      return this.imageObj;
    }

    @Override
    public RGBImageInterface greyScaleImage(int greyScaleType) throws IllegalArgumentException {
      logData.append("Get greyscale component on an image: "+greyScaleType);
      return this.imageObj;
    }

    @Override
    public RGBImageInterface sepiaImage() {
      logData.append("Apply sepia color transformation on an image.");
      return this.imageObj;
    }

    @Override
    public int getImageHeight() {
      logData.append("Get height of an image.");
      return 0;
    }

    @Override
    public int getImageWidth() {
      logData.append("Get width of an image.");
      return 0;
    }

    @Override
    public int[][][] getPixel() {
      logData.append("Get pixel matrix of an image.");
      return new int[0][][];
    }
  }

  @Test
  public void testBrightnessController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"0","Koala","Koala-Bright"};

    RGBImageCommandInterface controller = new BrightnessController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Brightness image operation: 0",mockModel.logData.toString());
  }

  @Test
  public void testFlipController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"0","Koala","Koala-Flip"};

    RGBImageCommandInterface controller = new FlipImageController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Flip image operation: 0",mockModel.logData.toString());
  }

  @Test
  public void testSharpnessController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"0","Koala","Koala-Sharp"};

    RGBImageCommandInterface controller = new SharpnessController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Sharpness image operation: 0",mockModel.logData.toString());
  }

  @Test
  public void testGreyscaleController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"0","Koala","Koala-grey"};

    RGBImageCommandInterface controller = new GreyScaleController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Get greyscale component on an image: 0",mockModel.logData.toString());
  }

  @Test
  public void testRGBFilterController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"0","Koala","Koala-grey"};

    RGBImageCommandInterface controller = new RGBFilterController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Get single component on an image: 0",mockModel.logData.toString());
  }

  @Test
  public void testColorTransformationController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"Koala","Koala-grey"};

    RGBImageCommandInterface controller = new ColorTransformationController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Apply sepia color transformation on an image.",mockModel.logData.toString());
  }

  @Test
  public void testCombineChannelsController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"Koala","Koala-Red","Koala-Green","Koala-Blue"};

    RGBImageCommandInterface controller = new CombineChannelsController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala-Red",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Combine image operation on a list of images having size:2",mockModel.logData.toString());
  }

  @Test
  public void testSplitChannelsController() throws IOException {
    StringBuilder log = new StringBuilder();
    MockModel mockModel = new MockModel(log,null);
    String [] ar=new String[]{"Koala","Koala-Red","Koala-Green","Koala-Blue"};

    RGBImageCommandInterface controller = new SplitChannelsController(ar);
    Map<String, RGBImageInterface> mp=new HashMap<>();
    mp.put("Koala",mockModel);
    controller.execute(mp);
    assertEquals("loading the image.Split operation on an image.",mockModel.logData.toString());
  }
}
