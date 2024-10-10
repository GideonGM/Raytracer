//imports, find a way around this, or maybe just switch to an actual ide.
//import ij.*;
import java.lang.Math;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyImage{

	public static int TraceRay(float[] O,float[] D,float t_min,float t_max, Sphere[] scene, Light[] lights){
		//System.out.print("ray traced");
		int objNum = scene.length;
		int closest_sphere = -1;
		float closest_t = 999999;
		Color backColor = new Color(0, 255, 255);
		float[] sphereRads = new float[objNum];
		float[][] sphereCents = new float[objNum][3];
		Color[] sphereCols = new Color[objNum];
		for (int i = 0; i < objNum; i++){
			sphereCols[i] = scene[i].color;
			sphereRads[i] = scene[i].radius;
			for (int j = 0; j < 3; j++){
			sphereCents[i][j] = scene[i].center[j];
			}
		}
		for (int i = 0; i < objNum; i++){
			float[] t12 = IntersectRaySphere(O, D, sphereRads[i], sphereCents[i], sphereCols[i]);
			//System.out.print(t12[0] + " " + t12[1] + " ");
			if (t12[0] > t_min && t12[0] < t_max && t12[0] < closest_t){
				closest_t = t12[0];
				closest_sphere = i;
			}
			if (t12[1] > t_min && t12[1] < t_max && t12[1] < closest_t){
                                closest_t = t12[1];
                                closest_sphere = i;
                        }
		}
		float[] P = new float[3];
		float[] N = new float[3];
		if (closest_sphere == -1){
			return backColor.getRGB();
		}
		else{
			P[0] = O[0] + closest_t * D[0];P[1] = O[1] + closest_t * D[1];P[2] = O[2] + closest_t * D[2];
			N[0] = P[0] - scene[closest_sphere].center[0]; N[1] = P[1] - scene[closest_sphere].center[1]; N[2] = P[2] - scene[closest_sphere].center[2];
			float NLength = ThreeVectorLength(N);
			N[0] = N[0]/NLength;N[1] = N[1]/NLength;N[2] = N[2]/NLength;
			float lightEffect = ComputeLighting(P, N, lights);
			//float lightEffect = (float)0.5;
			int outRed = (int)(sphereCols[closest_sphere].getRed() * lightEffect);
			int outBlue =	(int)(sphereCols[closest_sphere].getBlue() * lightEffect);
			int outGreen = (int)(sphereCols[closest_sphere].getGreen() * lightEffect);
			if (outRed > 255){
				outRed = 255;
			}
			if (outGreen > 255) {
				outGreen = 255;
			}
			if (outBlue > 255){
				outBlue = 255;
			}
			Color outputColor = new Color(outRed, outGreen, outBlue);
			//Color outputColor = new Color(69, 69, 69);
			//System.out.println(sphereCols[closest_sphere].getRed());
			return outputColor.getRGB();
		}
	}

	public static float ThreeVectorLength(float[] N){
		return (float)(Math.sqrt((N[0]*N[0]) + (N[1]*N[1] + (N[2]*N[2]))));
	}

	public static float[] IntersectRaySphere(float[] O, float[] D, float sphereRad, float[] sphereCent, Color sphereCol){
		float[] CO = {O[0] - sphereCent[0], O[1] - sphereCent[1], O[2] - sphereCent[2]};
		//System.out.println(CO[0] + " " + CO[1] + " " + CO[2]);
                float a = 0;
                for(int i = 0; i < 3; i++){
                        a = a + (D[i] * D[i]);
                }      
		float b = 0;
                for(int i = 0; i < 3; i++){
                        b = b + (CO[i] * D[i]);
                }      
		b = b*2;
		float c = 0;
                for(int i = 0; i < 3; i++){
                        c = c + (CO[i] * CO[i]);
			c = c - sphereRad * sphereRad;
                }             			

		float discriminant = b*b - (float)4*a*c;
		//System.out.print(discriminant);
		float[] re = new float[2];
		if (discriminant < 0){
			re[0] = 999999;
			re[1] = 999999;
			//System.out.print("O");
			return re;
		}
		else{
			float t1 = (((b * (float)-1) + (float)Math.sqrt(discriminant)) / ((float)2*a));
			float t2 = (((b * (float)-1) - (float)Math.sqrt(discriminant)) / ((float)2*a));
			re[0] = t1;
			re[1] = t2;
			//System.out.print("X");
			return re;
		}
	}

	public static float ComputeLighting(float[] P, float[] N, Light[] lights){
		float lightI = 0;
		float[] L = new float[3];
		float nDotL = 0;
		for (int i = 0; i < lights.length; i++){
			if (lights[i].type == "ambient"){
				lightI = lightI + lights[i].intensity;
			}
			else{
				if (lights[i].type == "point"){
					for (int j = 0; j < 3; j++){
						L[j] = lights[i].position[j] - (float)P[j];
					}
				}
				else{
					for (int j = 0; j < 3; j++){
                                                L[j] = lights[i].direction[j];
                                        }
				}
			 	//N dot product L
				for (int j = 0; j < 3; j++){
					nDotL = nDotL + (N[j] * L[j]);
				}
				if (nDotL > 0){
					lightI = lightI + lights[i].intensity * nDotL/(ThreeVectorLength(N) * ThreeVectorLength(L));
				}
			}
		}
		return lightI;
	}

	//setup for display method
	private static JFrame frame;
        private static JLabel label;
	//dispaly method, displays images using JFrame, don't ask me how it works.
        public static void display(BufferedImage image){
             if(frame==null){
                frame=new JFrame();
             	frame.setTitle("stained_image");
                frame.setSize(image.getWidth(), image.getHeight());
		//frame.pack();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                label=new JLabel();
                label.setIcon(new ImageIcon(image));
                frame.getContentPane().add(label,BorderLayout.CENTER);
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
             }else label.setIcon(new ImageIcon(image));
        }   

	public static void main(String[] args){
		//String imagePath = "/Users/gideon/Downloads/scouting_cover-1500x1000.jpeg";
		int imageWidth = 1000;
		int imageHeight = 1000;
		Color myWhite = new Color(255, 255, 255);
		float[] sCent1 = {0, 2, 8};
		float[] sCent2 = {4, -1, 6};
		float[] sCent3 = {-4, 0, 8};
		float[] sCent4 = {0, -88, 7};
		Color myGreen = new Color(0, 255, 0);
                Color myRed = new Color(255, 0, 0);
                Color myBlue = new Color(0, 0, 255);
		Color myYellow = new Color(255, 255, 0);
		Sphere sphere1 = new Sphere(1, sCent1, myRed);
		Sphere sphere2 = new Sphere(1, sCent2, myGreen);
		Sphere sphere3 = new Sphere(1, sCent3, myBlue);
		Sphere sphere4 = new Sphere(50, sCent4, myYellow);

		float[] lightPos = {2, 1, 0};
		float[] lightDir = {1, 4, 4};
		Light light1 = new Light("ambient", (float)0.2);
		Light light2 = new Light("point", (float)0.6, lightPos);
		Light light3 = new Light("directional", (float)0.2, lightDir);
		//System.out.println(light1.type + " " + light2.intensity + " " + light3.direction[2]);

		Sphere[] scene = {sphere1, sphere2, sphere3, sphere4};
		Light[] lights = {light1, light2, light3};


		//BufferedImage myPicture = ImageIO.read(new File(imagePath));
		BufferedImage myPicture = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) myPicture.getGraphics();
		//myPicture.setColor(myWhite);
		//define math values;
		float FOV = 1;
                float[] O = {0, 0, 0}; //Origin position
                float ViewX;
                float ViewY;
                float ViewZ;
		float[] D = new float[3];
		int finalCol;

		for (int i = 0; i < imageWidth; i++){
			int CanvX =  i - imageWidth/2;
			//System.out.println("");
			for(int j = 0; j < imageHeight; j++){
				int CanvY = (j - imageHeight/2)*-1;
				//the actual math
				ViewX = CanvX*(FOV/imageWidth);
				//System.out.println("X" + ViewX);
				ViewY = CanvY*(FOV/imageHeight);
				//System.out.println("Y" + ViewY);
				ViewZ = 1;
				D[0] = ViewX;
				//System.out.println("X" + ViewX);
				D[1] = ViewY;
				//System.out.println("Y" + ViewY);
				D[2] = ViewZ;
				//System.out.println("Z" + ViewZ);
				finalCol = TraceRay(O, D, 1, 99999, scene, lights);
				myPicture.setRGB(i, j, finalCol);

				//System.out.println(i + " " + j + "Before: "  + myPicture.getRGB(i, j));
				//myPicture.setRGB(i, j, myWhite.getRGB());
				}
			}
		display(myPicture);

	}
}
