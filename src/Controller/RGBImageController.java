package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import Controller.ImageCommands.FileHandling.LoadController;
import Controller.ImageCommands.FileHandling.SaveController;
import Controller.ImageCommands.MultiIN.CombineChannelsController;
import Controller.ImageCommands.MultiOUT.SplitChannelsController;
import Controller.ImageCommands.RGBImageCommandInterface;
import Controller.ImageCommands.SingleIN.BrightnessController;
import Controller.ImageCommands.SingleIN.ColorTransformationController;
import Controller.ImageCommands.SingleIN.FlipImageController;
import Controller.ImageCommands.SingleIN.GreyScaleController;
import Controller.ImageCommands.SingleIN.RGBFilterController;
import Controller.ImageCommands.SingleIN.SharpnessController;
import Model.Enums.AxisName;
import Model.Enums.ColorMapping;
import Model.Enums.GreyScaleType;
import Model.Enums.KernelImage;
import Model.RGBImageInterface;

public class RGBImageController implements RGBImageControllerInterface {

  private final InputStream in;

  private final OutputStream out;

  private final Map<String, RGBImageInterface> cachedImages;

  private final Map<String, Function<String[], RGBImageCommandInterface>> knownCommands;

  public RGBImageController(InputStream in, OutputStream out) {
    this.in = in;
    this.out = out;
    cachedImages = new HashMap<>();
    knownCommands = getFamiliarCommands();
  }


  private String[] concatenateStringArrays(String[] args, int newValue) {
    int n = args.length;
    String[] newArgs = new String[n + 1];
    newArgs[0] = Integer.toString(newValue);
    for (int i = 1; i <= n; i++) {
      newArgs[i] = String.copyValueOf(args[i - 1].toCharArray());
    }
    return newArgs;
  }

  private Map<String, Function<String[], RGBImageCommandInterface>> getFamiliarCommands() {

    Map<String, Function<String[], RGBImageCommandInterface>> knownCommands = new HashMap<>();

    knownCommands.put("load", args -> new LoadController(args));
    knownCommands.put("save", args -> new SaveController(args));

    knownCommands.put("red-component",
            args ->
            {
              String[] newArgs = concatenateStringArrays(args, ColorMapping.red.ordinal());
              return new RGBFilterController(newArgs);
            });
    knownCommands.put("green-component",
            args ->
            {
              String[] newArgs = concatenateStringArrays(args, ColorMapping.green.ordinal());
              return new RGBFilterController(newArgs);
            });
    knownCommands.put("blue-component",
            args -> {
              String[] newArgs = concatenateStringArrays(args, ColorMapping.blue.ordinal());
              return new RGBFilterController(newArgs);
            });

    knownCommands.put("value-component",
            args -> {
              String[] newArgs = concatenateStringArrays(args, GreyScaleType.value.ordinal());
              return new GreyScaleController(newArgs);
            });
    knownCommands.put("luma-component",
            args -> {
              String[] newArgs = concatenateStringArrays(args, GreyScaleType.luma.ordinal());
              return new GreyScaleController(newArgs);
            });
    knownCommands.put("intensity-component",
            args -> {
              String[] newArgs = concatenateStringArrays(args, GreyScaleType.intensity.ordinal());
              return new GreyScaleController(newArgs);
            });

    knownCommands.put("horizontal-flip",
            args -> {
              String[] newArgs = concatenateStringArrays(args, AxisName.horizontal.ordinal());
              return new FlipImageController(newArgs);
            });
    knownCommands.put("vertical-flip",
            args -> {
              String[] newArgs = concatenateStringArrays(args, AxisName.vertical.ordinal());
              return new FlipImageController(newArgs);
            });

    knownCommands.put("brighten", args -> new BrightnessController(args));

    knownCommands.put("rgb-split", args -> new SplitChannelsController(args));
    knownCommands.put("rgb-combine", args -> new CombineChannelsController(args));

    knownCommands.put("blur",
            args -> {
              String[] newArgs = concatenateStringArrays(args, KernelImage.Blur.ordinal());
              return new SharpnessController(newArgs);
            });
    knownCommands.put("sharpen",
            args -> {
              String[] newArgs = concatenateStringArrays(args, KernelImage.Sharpen.ordinal());
              return new SharpnessController(newArgs);
            });

    knownCommands.put("sepia", args -> new ColorTransformationController(args));
    return knownCommands;
  }

  @Override
  public void go() throws IOException {

    Scanner sc = new Scanner(this.in);

    while (sc.hasNextLine()) {

      RGBImageCommandInterface c;

      String commandLine = sc.nextLine().trim();

      if (commandLine.equals("")) {
//        System.out.println("Please enter a valid command.");
        String op="You have entered a space. Please enter a valid command.";
        out.write(op.getBytes());
        continue;
      }

      if (commandLine.charAt(0) == '#') {
//        System.out.println("Reading comment now...");
        String op="Reading comment now....";
        out.write(op.getBytes());
        continue;
      }



      if (commandLine.equalsIgnoreCase("q") || commandLine.equalsIgnoreCase("quit")){
        String op="Quitting application.";
        out.write(op.getBytes());
        return;
      }

      String[] tokenizedCommandStrings = tokenizeInputWithoutQuoteDelimiters(commandLine);

      if (tokenizedCommandStrings[0].equalsIgnoreCase("run")) {
        sc = checkFileStreamExists(tokenizedCommandStrings);
        continue;
      }

      Function<String[], RGBImageCommandInterface> cmd = knownCommands.getOrDefault(tokenizedCommandStrings[0], null);

      if (cmd == null) {
        String op="Illegal arguments passed for the operations."+"Do you want to try again?";
        out.write(op.getBytes());
      } else {
        c = cmd.apply(Arrays.copyOfRange(tokenizedCommandStrings, 1, tokenizedCommandStrings.length));
        try {
          c.execute(cachedImages);
        } catch (IOException ex) {
          throw new IOException("Exception occurred while executing the command.");
        }
      }
    }
  }

  private Scanner checkFileStreamExists(String[] tokenizedCommandStrings) throws FileNotFoundException {
    if (tokenizedCommandStrings.length != 2) {
      throw new IllegalArgumentException(" Illegal arguments passed for the run file operation.");
    }
    try {
      File commandFile = new File(tokenizedCommandStrings[1]);
      return new Scanner(new FileInputStream(commandFile));
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("Run File does not exists. Wrong Input. Aborting...");
    }
  }

  private static String[] tokenizeInputWithoutQuoteDelimiters(String commandLine) {

    List<String> tokens = new ArrayList<String>();
    Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
    //\\s+(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)
    // [^\s"']+|"([^"]*)"|'([^']*)'
    // |"([^']*)"|'([^"]*)'
    Matcher regexMatcher = regex.matcher(commandLine);
    while (regexMatcher.find()) {
      if (regexMatcher.group(1) != null) {
        tokens.add(regexMatcher.group(1));
      } else if (regexMatcher.group(2) != null) {
        tokens.add(regexMatcher.group(2));
      } else {
        tokens.add(regexMatcher.group());
      }
    }

    return tokens.toArray(new String[0]);

  }

  public static void main(String[] args) throws IOException {

    Scanner sc = new Scanner(System.in);
    String res = sc.nextLine();
    String[] result = RGBImageController.tokenizeInputWithoutQuoteDelimiters(res);
    System.out.println("Size of result:" + result.length);
    for (String s : result) {
      System.out.println("S: " + s);
    }
//    String filePathBlue="/Users/omagarwal/Desktop/Koala.ppm";
//    RGBImageInterface imageInterface=new RGBImage(filePathBlue);
//    RGBImageInterface result=imageInterface.greyScaleImage(0);
//
//    RGBImageController controller=new RGBImageController(System.in,System.out);
//
//    controller.go();
  }
}
