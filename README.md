# Image Processing Application

Welcome to our GitHub repository for a Java-based image processing application! This repository contains the code for a straightforward and efficient image processing tool designed to perform many image manipulations like blur, sharpen, greyscale images etc. Whether you're a beginner looking to explore image processing or a developer seeking an easy-to-understand Java solution for your projects, you're in the right place. Dive into the code, experiment with image enhancements, and simplify your image processing tasks.
This image processing stand-alone desktop application developed using the MVC architecture. 

<img width="1726" alt="Screenshot 2023-12-11 at 6 42 54 PM" src="https://github.com/drk-knght/Image-Processing-Application/assets/71601478/d86db0f4-a064-4690-a707-2ef5361c16f4">

The image processing software at present support different types of image operations- 

1. Changing the brightness of the images (both +ve and -ve).
2. Perform horizontal and vertical flips of the images.
3. Splitting the image into multiple images based on different channels like red, green, blue etc.
4. Blur the image by fixed discrete step.
5. Apply different greyscale operation like Luma, Value, Intensity on an existing image.
6. Getting red, green and blue channel images separately from an image.
7. Sharpen the image by fixed discrete step.
8. Carry out sepia tone on an image.
9. Combine different channel images like red, green and blue into a single image.
10. Fetching histogram table consisting of (value, frequency) for 256 different greyscale values( uses 8-bit per pixel).
11. Color correct an image and meaningfully align the frequency peaks to remove pollution from multiple channels.
12. Use Levels-Adjustment to correct the tonal range and color balance of an image by adjusting intensity values of shadow, midtones and highlight points.
13. Perform lossy image compression using haar-transformation.
14. Support functionality to preview the image operations applied on a part of the image which gives user a sense of what the image will look like after the operation is perfomed. This is done during the action is performed on the image. For user using GUI then can directly see the preview and then choose to either save the operation or cancel it. For rest of the user interfaces the user has to type out the command manually to see the preview operation.

Here are examples how the image would look for some operations-

--Getting only the green-componenet of the image-
<img width="1726" alt="Screenshot 2023-12-11 at 6 44 06 PM" src="https://github.com/drk-knght/Image-Processing-Application/assets/71601478/dc4d5150-b049-4680-b611-bb4314ce051a">


--Selecting level-adjustment values and split preview % before applying operation on the image
<img width="1726" alt="Screenshot 2023-12-11 at 6 48 58 PM" src="https://github.com/drk-knght/Image-Processing-Application/assets/71601478/014ddc3a-efd9-4f08-bbaa-2d18f79d9e68">

--Getting the level-adjustment feature operation on a split preview of newly operated image and actual image before image transformation-
<img width="1726" alt="Screenshot 2023-12-11 at 6 50 42 PM" src="https://github.com/drk-knght/Image-Processing-Application/assets/71601478/cf525337-cbbf-419c-bbdc-a0dc55f6f8ec">



***

## Design Overview
[`ImageProcessingApplication`](https://github.com/drk-knght/Image-Processing-Application/blob/new-Patch/src/ImageProcessingApplication.java): The driver code which starts the Image Proocesssing Program.
- When run without any command line arguments the program will use the Graphical User Interface to interact with the client of the code.
- When **-file filePath.txt**  is passed as an input through terminal then the program connect itself to that particular file and parses all the commands dumped in that particular file path.
- when **-text** is passed as an input, the the application uses an interactive text based console to get the commands to manipulate the images.

All the implementation details are present in the src folder. The usage of each of the package is described below-

### [Enums](https://github.com/drk-knght/Image-Processing-Application/tree/new-Patch/src/enums)

This stores all the enum data that are used in the model, view and controller implementation of the MVC architecture. It stores the name and value mapping along with some enums consisting of lambda methods which are used dynamically as when required by all the three component multiple classes.
The enums also maintains the semantic menaing mapping of numbers to the repective strings, colors, matrices wtc. which are used as and when required by any of the existing components of model, view and controller.

### [Model](https://github.com/drk-knght/Image-Processing-Application/tree/new-Patch/src/model)

In the present image processing application, a model represents a single image which can be loaded from local disk. The extensions of image supported for the current versions are *ppm, png, jpg & jpeg*. However, many new possible extensions if required can be added easily to make support other file formats.
The model package of our MVC architecture contains the one main model class named *RGBImage* which implements the *RGBImageInterface* interface. This class contains all the commands as public methods which can be called from the controller class as per the required user input.
The model contains several classes for each individual operation on the image. Depending on the type of image editing required, the operations are classified into different packages where all the classes in that package implement a particular interface.
The model contains the following packages:

1. `imageoperations`: This package represents all the image enhancement that can be done on a single image. It contains multiple packages for handling different commands of the image operations like single input single output, single input multiple output, and multiple input single output. All the files in the packages deal with the image operations that are handled for the present image processing application.
2. `RGBImage` : It is the main model file which interacts with the controller, gets the command from controller and redirects the given command to above image operation utility classes.
3. `RGBImageInterface`: Interface which has the overview of all the methods that are expected to work for the given MVC architecture and user requirements.


### [Controller](https://github.com/drk-knght/Image-Processing-Application/tree/new-Patch/src/controller)

The controller is the brain of the image processing application. The design for this based on the command builder design pattern. This package has the ability to handle diiferent streams of input like graphical user interface or any other streams that fall under the [inputstream](https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html) like FileInputStream, AudioInputStream, etc. and similarly display the outputs to any streams supported by [outputstream](https://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html).
There are different controllers to handle different types of interfaces like graphical and script/file based interfaces. The controller contains the following packages:

1. `imagecommads`: This is similar to model class. It has all the utility commands classes divided into hierarchical packages and files. The main controller redirects the commands to these utility classes for passing the input to the model.
2. `filehandling`: The package deals with read and write operations on the files stored on the hard disk of the local machine containing the images. The package contains the cases for both imageIO and ppm file handling functionality.
3. `graphicalcontroller`: This package contains implementation details related to the controller part which handles the GUI aspect of the image processing application. It contains an interface which represents the basic functionalities offered by the controller and its implementation details are present in the *GraphicalController*.
4. `features`: This package contains details and implementation related to command callbacks  methods. Using the object of the feature class view can put a request to the controller if any external event happens to the MVC environment. It has one interface which is implemented by a class. This class exposes only limited functionalities of the controller showcasing the benefit of composition over inheritance.
5. `scriptcontroller`: This package has all the implementation details of the controller which handles the interactive text based console and file scripting aspect of the Image Processing Application.


### [View](https://github.com/drk-knght/Image-Processing-Application/tree/new-Patch/src/view)

The application currently supports various methods of interacting with the user- 

- Text based interactive stream- The application has the potential to interact with a user through a text driven terminal where the user has to enter the image commands as per the syntax made for this particular application. The application if it has the knowledge about that operation, performs the respective operation requested by the user.

  
- File based stream- The program also supports the files based input functionality where if the user wants to stack up all the commands of the text based console in a valid file and want to pass that script file to the application for reading then they can do so by passing the appropriate file flag to the main application or the JAR file.

  
- Graphical User Interface- The java program also supports the graphical interaction of the user with the application where the user can interactively load image, perform different operations, preview the operations and if required save it to the local disk.

This package contains code and implementation details related to the graphical user interface part of the image processing application. The view allows the user to interact with the application via the Graphical User Interface(GUI). It allows the user to load an image, perform operations on it and save the new image. The GUI has been built using Java Swing.The view contains the following packages:
dialogmenus: It contains the implementation details about the JDialog boxes that pop-up when additional information like split-preview %, level-adjustment values etc are needed to perform an operation. The package has two sub packages- 

1. `Multiindialog`- This handles the dialog pop-up which needs to get multiple data from the dialog box.
   
2. `Singleindialog`: This handles the dialog pop-up which needs to get only the split preview data from the dialog box.
   
3. `GraphicalView`: This is the main view class which implements the IView interface. The controller sends all the commands to this class. It has the responsibility to display all the functionalities like buttons, headings, labels, etc. Whenever an event occurs this view sends the request about the happening to the controller.


---

## Running the [`JAR`](https://github.com/drk-knght/Image-Processing-Application/blob/new-Patch/res/Image%20Processing.jar) File

The JAR file present in the resources folder can be directly run instead of compiling the program and running the main program again.

### Run the text-based user interactive console-

To run the application in the interactive text based mode:

```
java -jar <path-to-jar-file> -text
```

example for running our text-based interactive console :

```
cd res/
java -jar "Image Processing.jar" -text
```


## Run the file-based user interface stream-

To run a script file:

```
java -jar <path-to-jar-file> -file <path-to-script-file>
```

example for running our sample script :

```
cd res/
java -jar "Image Processing.jar" -file Script-Tiger.txt
```
**Note: we recommend running the sample script from the `res` directory to avoid relative path issues for images.**

---

## Running the `main`


You can also run the application from within intellij by running the main function of `ImageProcessingApplication.java`.


### Program Compilation

To first compile the complete application do:

```
javac pathToMainClass.java
```

E.g. For compiling our main program :

```
cd src/
javac ImageProcessingApplication.java
```

### Program execution

#### Run the text-based user interactive console-

To run the application in the interactive text based mode:

```
java  <path-to-main-file> -text
```

E.g. For running our text-based interactive console :

```
cd src/
java ImageProcessingApplication -text
```

#### Run the file-based user interface stream-

To run the application using a script file:

```
java  <path-to-main-file> -file <path-to-script-file>
```

E.g. For running our script file based console :

```
cd src/
java ImageProcessingApplication -file Script-Tiger.txt
```

#### Run the GUI based user interface-

To run the application in the interactive GUI based mode:

```
java  <path-to-main-file>
```

E.g. For running our GUI interactive console :

```
cd src/
java ImageProcessingApplication
```

---

## Image Command Syntax & GUI Functionality

All the image processing image commands are present in the [`USEME-SCRIPT`](https://github.com/drk-knght/Image-Processing-Application/blob/new-Patch/res/USEME.pdf) for terminal and file based input streams. Please refer to that if you want to enter any valid commands that can be accomodated by the program.

For the GUI functionality and what the actions are expected to perform when an event occurs is mentioned in the [`GUI-SCRIPT`](https://github.com/drk-knght/Image-Processing-Application/blob/new-Patch/res/USEME%20-%20GUI.pdf). Please refer to this incase you have any doubt.

---

## Credits

The images used are taken from multiple sources for demonstartion purpose. 
1. [Tiger](https://github.com/drk-knght/Image-Processing-Application/blob/new-Patch/res/Tiger.png): The image is taken from [**FlowingData**](https://flowingdata.com/2019/09/30/pixelation-to-represent-endangered-species-counts/). Some of the possible image operations has been perfomed on this image for demonstration and has been stored in the res folder for references.
2. Koala: The image is taken from [Nossa_I.L.](https://www.flickr.com/photos/79719151@N02/8007267074) for demonstration purpose of our image processing application.
