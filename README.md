# Java Raytracer
A set of Java files written to run a simple raytracer. It is currently only capable of rendering any number of spheres with diffuse shading using point, directional, and ambient lighting.

<img width="997" alt="Screenshot 2024-10-10 at 1 33 14 AM" src="https://github.com/user-attachments/assets/887e0444-c60e-456a-824d-a909b6d724f1">

### History:
Made in 2024 as a way to explore my interest in computer graphics. I was having trouble conceptualizing advanced graphics software such as Metal and OpenGL, so I figured that coding the simplest form of rendering from the ground up would give me some insight into mechanics behind these modern pipelines. The code was written in Java as my Computer Science class at the time was based entirely in Java. I have had previous experience using java, but I figured writing a side project in the language would help refresh my memory. I also made the decision to write it entirely using vim editor, as my class was also insistent on teaching us command line and vim shortcuts. I am aware that this is not industry practice, and that it would generally be more effective to use an ide such as IntelliJ or Eclipse.

### Use: 
(Note: As far as I know this project only works on Mac)
This project is currently not very accessible in terms of modifying the scene information for the raytracer to process, but running it in its current state should be as simple as downloading the files, then compiling and running the MyImage.java file. The image should appear in a JFrame window.

If you have an interest in modifying the scene data, listed below are instructions for certain possible modifications.
##### Changing sphere data:
   Each sphere object comes with an int array to determine its color, a float array to determine its center position, and an int r to determine its radius. 
   Editing the radius should be as simple as changing the integer value in the sphere declaration in lines…
   Editing the color data requires editing or creating a new color object to feed into the sphere declaration.
   Editing the position requires editing the float array declarations in lines…, which the first index being x, the second index being y, and the third index being z.
##### Creating a new sphere:
   Creating a new sphere object involves copying or rewriting the declarations for the preexisting sphere objects, alongside their corresponding color object and center position. Then make sure  to feed the new sphere object into the declaration of the scene array so the raytracer will process it.
##### Removing a sphere:
   Simply remove the declaration for the sphere object alongside its corresponding color object and center position array. Then make sure to remove the sphere object from the declaration of the scene array.
##### Changing the lighting:
   The lighting objects are declared in the main class. There are three different types of light objects, defined by the first string in a light object’s declaration:
   Ambient, which takes in a float representing its intensity.
   Point, which takes in a float array representing the position of the light source, as well as a float representing its intensity.
   Directional, which takes in a float array representing the direction of the light source, as well as a float representing its intensity.
If you are removing or adding a light object to the scene, make sure to add or remove it to the declaration of the array of lights located in the main class.
##### Changing the size of the image.
   You can change the pixel size of the image by changing the integer imageWidth and imageHeight variables located at the beginning of the main class.
