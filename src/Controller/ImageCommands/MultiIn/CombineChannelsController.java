package Controller.ImageCommands.MultiIn;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import Controller.ImageCommands.RGBImageCommandInterface;
import Model.RGBImageInterface;

public class CombineChannelsController implements RGBImageCommandInterface {

  private final String [] channelImages;

  private final String modifiedImage;

  public CombineChannelsController(String [] commandArguments) throws IllegalArgumentException{
    if(commandArguments.length!=4){
      throw new IllegalArgumentException("The number of arguments expected for RGB combine operation does not match with the passed command.");
    }
    this.modifiedImage=commandArguments[0];
    channelImages= Arrays.copyOfRange(commandArguments,1,commandArguments.length);
  }


  @Override
  public void execute(Map<String, RGBImageInterface> cachedImage) throws NullPointerException {
    RGBImageInterface firstImage=cachedImage.get(this.channelImages[0]);

    if(firstImage==null){
      throw new NullPointerException("One of the file name passed: "+this.channelImages[0]+" does not exists in the cached Image");
    }

    ArrayList<RGBImageInterface> arrayList=new ArrayList<>();

    for(int i=1;i<channelImages.length;i++){
      arrayList.add(cachedImage.get(channelImages[i]));
    }

    RGBImageInterface combinedImage=firstImage.combineImageComponents(arrayList);

    cachedImage.put(modifiedImage,combinedImage);
  }
}
