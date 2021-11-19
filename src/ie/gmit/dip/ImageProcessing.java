package ie.gmit.dip;

import javax.imageio.ImageIO;
import java.util.Scanner;
import java.awt.Color;
import java.io.File;

import java.awt.image.BufferedImage;


public class ImageProcessing {
	public static BufferedImage Process(BufferedImage image, double[][] kernel) throws Exception {

		
		int[][] imageArray = new int[image.getHeight()][image.getWidth()]; // create a 2d integer array of the same width and height as the buffered image
		
		for (int y = 0; y < image.getHeight(); y++) { // Loop over the 2D image pixel-by-pixel and
			for (int x = 0; x < image.getWidth(); x++) { // copy values to 2d integer array

				int pixel = image.getRGB(x, y);
				imageArray[y][x] = pixel;

			}
		}

		int redAccumulator; // declaring variables
		int greenAccumulator;
		int blueAccumulator;
		int red;
		int green;
		int blue;

		for (int y = 0; y < imageArray.length - 2; y++) { //looping through the 2d integer array pixel by pixel
			for (int x = 0; x < imageArray[y].length - 2; x++) { //.length=-2 so that the processing doesn't throw an out of bounds exception

				redAccumulator = 0; //will contain the value of the accumulated red*kernel values
				greenAccumulator = 0; //will contain the value of the accumulated green*kernel values
				blueAccumulator = 0; //will contain the value of the accumulated blue*kernel values

				red = 0; // will contain a value between 0 and 255 for the red channel of a particular pixel
				green = 0; // will contain a value between 0 and 255 for the green channel of a particular pixel
				blue = 0; // will contain a value between 0 and 255 for the blue channel of a particular pixel

				for (int row = 0; row < kernel.length; row++) { //looping through the kernel
					for (int col = 0; col < kernel[row].length; col++) {

						int pixel = imageArray[y + row][x + col];  //accessing pixel from relevant imageArray position
						red = (pixel >> 16) & 0xff;	// extracting values for the red, green and blue channels of that pixel
						green = (pixel >> 8) & 0xff;
						blue = pixel & 0xff;

						redAccumulator += (int) (red * kernel[row][col]); // performing (kernel)*(pixel RGB values) calculations
						greenAccumulator += (int) (green * kernel[row][col]); // casting each calculated value to an int
						blueAccumulator += (int) (blue * kernel[row][col]); // adding each casted calculated value to relevant accumulator

					}

				}

				int rgb = 0;
				int alpha = 255;

				if (redAccumulator < 0) {   // bunch of if else statements which clamp values to 0 if they are negative
					redAccumulator = 0;		// or 255 if they are greater than 255
				} else if (redAccumulator > 255) {
					redAccumulator = 255;
				}

				if (greenAccumulator < 0) {
					greenAccumulator = 0;
				} else if (greenAccumulator > 255) {
					greenAccumulator = 255;
				}

				if (blueAccumulator < 0) {
					blueAccumulator = 0;
				} else if (blueAccumulator > 255) {
					blueAccumulator = 255;
				}

				rgb = rgb | (alpha << 24);  // recreating a 32 bit pixel using newly calculated RGB values
				rgb = rgb | (redAccumulator << 16);
				rgb = rgb | (greenAccumulator << 8);
				rgb = rgb | blueAccumulator;

				image.setRGB(x + 1, y + 1, rgb);  // inputting recreated pixel to relevant position in image

			}

		}

		 // writing file to output directory path
		
		return image;
		
		

		

	}
}
