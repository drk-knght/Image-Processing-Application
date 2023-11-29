package controller.scriptcontroller;

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


import controller.RGBImageControllerInterface;
import controller.imagecommands.iocommands.LoadCommand;
import controller.imagecommands.iocommands.SaveCommand;
import controller.imagecommands.multiincommand.CombineChannelsCommand;
import controller.imagecommands.multioutcommand.SplitChannelsCommand;
import controller.imagecommands.RGBImageCommandInterface;
import controller.imagecommands.singleincommands.BrightnessCommand;
import controller.imagecommands.singleincommands.ColorCorrectionCommand;
import controller.imagecommands.singleincommands.ColorTransformationCommand;
import controller.imagecommands.singleincommands.CompressCommand;
import controller.imagecommands.singleincommands.FlipImageCommand;
import controller.imagecommands.singleincommands.GreyScaleCommand;
import controller.imagecommands.singleincommands.HistogramCommand;
import controller.imagecommands.singleincommands.LevelAdjustmentCommand;
import controller.imagecommands.singleincommands.RGBFilterCommand;
import controller.imagecommands.singleincommands.SharpnessCommand;
import enums.AxisName;
import enums.ColorMapping;
import enums.GreyScaleType;
import enums.KernelImage;
import model.RGBImageInterface;

/**
 * The class represents the controller part of the MVC  which handles the bifurcation of the work.
 * The controller has an InputStream and an OutputStream where the inputs and outputs are fetched.
 * The design is based on the command builder design pattern.
 */
public class RGBImageController implements RGBImageControllerInterface {

  private final InputStream in;

  private final OutputStream out;

  private final Map<String, RGBImageInterface> cachedImages;

  private final Map<String, Function<String[], RGBImageCommandInterface>> knownCommands;

  /**
   * Constructor takes the input and output stream from where the interaction is to be made.
   * The data is fetched from in and the result if any are posted to out streams.
   *
   * @param in  The inputStream from where the controller gets the data.
   * @param out The outputStream where the controller send the result of the operation.
   */
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

    knownCommands.put("load", args -> new LoadCommand(args));
    knownCommands.put("save", args -> new SaveCommand(args));

    knownCommands.put("red-component",
        args -> {
        String[] newArgs = concatenateStringArrays(args, ColorMapping.red.ordinal());
        return new RGBFilterCommand(newArgs);
      });
    knownCommands.put("green-component",
        args -> {
        String[] newArgs = concatenateStringArrays(args, ColorMapping.green.ordinal());
        return new RGBFilterCommand(newArgs);
      });
    knownCommands.put("blue-component",
        args -> {
        String[] newArgs = concatenateStringArrays(args, ColorMapping.blue.ordinal());
        return new RGBFilterCommand(newArgs);
      });

    knownCommands.put("value-component",
        args -> {
        String[] newArgs = concatenateStringArrays(args, GreyScaleType.value.ordinal());
        return new GreyScaleCommand(newArgs);
      });
    knownCommands.put("luma-component",
        args -> {
        String[] newArgs = concatenateStringArrays(args, GreyScaleType.luma.ordinal());
        return new GreyScaleCommand(newArgs);
      });
    knownCommands.put("intensity-component",
        args -> {
        String[] newArgs = concatenateStringArrays(args, GreyScaleType.intensity.ordinal());
        return new GreyScaleCommand(newArgs);
      });

    knownCommands.put("horizontal-flip",
        args -> {
        String[] newArgs = concatenateStringArrays(args, AxisName.horizontal.ordinal());
        return new FlipImageCommand(newArgs);
      });
    knownCommands.put("vertical-flip",
        args -> {
        String[] newArgs = concatenateStringArrays(args, AxisName.vertical.ordinal());
        return new FlipImageCommand(newArgs);
      });

    knownCommands.put("brighten", args -> new BrightnessCommand(args));

    knownCommands.put("rgb-split", args -> new SplitChannelsCommand(args));
    knownCommands.put("rgb-combine", args -> new CombineChannelsCommand(args));

    knownCommands.put("blur",
        args -> {
        String[] newArgs = concatenateStringArrays(args, KernelImage.Blur.ordinal());
        return new SharpnessCommand(newArgs);
      });
    knownCommands.put("sharpen",
        args -> {
        String[] newArgs = concatenateStringArrays(args, KernelImage.Sharpen.ordinal());
        return new SharpnessCommand(newArgs);
      });

    knownCommands.put("sepia", args -> new ColorTransformationCommand(args));
    knownCommands.put("compress", args -> new CompressCommand(args));
    knownCommands.put("histogram", args -> new HistogramCommand(args));
    knownCommands.put("color-correct", args -> new ColorCorrectionCommand(args));
    knownCommands.put("levels-adjust", args -> new LevelAdjustmentCommand(args));
    return knownCommands;
  }

  /**
   * The method which gives command for all the image processing application operations.
   * It encapsulates all the helper command classes objects under the method.
   *
   * @throws IOException Throws exception if invalid data is used in the method.
   */
  @Override
  public void goCall() throws IOException {

    Scanner sc = new Scanner(this.in);

    while (sc.hasNextLine()) {

      RGBImageCommandInterface c;

      String commandLine = sc.nextLine().trim();

      if (commandLine.equals("")) {
        String op = "\nEncountered a new line or space token.\n";
        out.write(op.getBytes());
        continue;
      }

      if (commandLine.charAt(0) == '#') {
        String op = "Reading comment now....\n";
        out.write(op.getBytes());
        continue;
      }


      if (commandLine.equalsIgnoreCase("q") || commandLine.equalsIgnoreCase(
              "quit")) {
        String op = "Quitting application.\n";
        out.write(op.getBytes());
        return;
      }

      String[] tokenizedCommandStrings = tokenizeInputWithoutQuoteDelimiters(commandLine);

      if (tokenizedCommandStrings[0].equalsIgnoreCase("run")) {
        try {
          sc = checkFileStreamExists(tokenizedCommandStrings);
        } catch (Exception ex) {
          String msg = ex.getMessage();
          out.write(msg.getBytes());
        }

        continue;
      }

      Function<String[], RGBImageCommandInterface> cmd = knownCommands.getOrDefault(
              tokenizedCommandStrings[0], null);

      if (cmd == null) {
        String op = "Illegal arguments passed for the operations." + "Do you want to try again?\n";
        out.write(op.getBytes());
      } else {

        try {
          c = cmd.apply(Arrays.copyOfRange(tokenizedCommandStrings, 1,
                  tokenizedCommandStrings.length));
          c.execute(cachedImages);
        } catch (Exception ex) {
          String msg = ex.getMessage();
          out.write(msg.getBytes());
        }
      }
    }
    String endMessage = "\n\nLooks like we are at the end of program. See you soon.\n\n";
    out.write(endMessage.getBytes());
  }

  private Scanner checkFileStreamExists(String[] tokenizedCommandStrings)
          throws FileNotFoundException {
    if (tokenizedCommandStrings.length != 2) {
      throw new IllegalArgumentException(" Illegal arguments passed for the run file operation.\n");
    }
    try {
      File commandFile = new File(tokenizedCommandStrings[1]);
      return new Scanner(new FileInputStream(commandFile));
    } catch (FileNotFoundException ex) {
      throw new FileNotFoundException("Run File does not exists. Wrong Input. Aborting...\n");
    }
  }

  private static String[] tokenizeInputWithoutQuoteDelimiters(String commandLine) {

    List<String> tokens = new ArrayList<String>();
    Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
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

}
