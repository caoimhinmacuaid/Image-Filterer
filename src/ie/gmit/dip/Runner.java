
package ie.gmit.dip;

import javax.imageio.ImageIO;
import java.util.Scanner;
import java.awt.Color;
import java.io.File;

import java.awt.image.BufferedImage;

public class Runner {
	
	public static void main(String[] args) throws Exception {

		Scanner scanner = new Scanner(System.in);
		String inputPath;
		String outputPath;
		double[][] kernel; // declaring variables

		System.out.println(ConsoleColour.BLUE_BRIGHT); // setting console colour to bright blue

		System.out.println("***************************************************");
		System.out.println("* GMIT - Dept. Computer Science & Applied Physics *");
		System.out.println("*                                                 *");
		System.out.println("*           Image Filtering System V0.1           *");
		System.out.println("*     H.Dip in Science (Software Development)     *");
		System.out.println("*                                                 *");
		System.out.println("***************************************************");
		
		//BufferedImage processedImage = ImageProcessing.Process(ImageIO.read(new File("src/ie/gmit/dip/bridge-rgb.png")), Kernel.EDGE_DETECTION_1 );
		//ImageIO.write(processedImage, "png", new File("src/ie/gmit/dip/out2.png"));

		System.out.println("Enter Image File Path:"); // Asking user to specify the file to process
		inputPath = scanner.nextLine();
		System.out.println();

		System.out.println("Would you like to save the image to a specific location? (Y/N)"); // Asking user if they would like to save the file
		String specific = scanner.nextLine();												  // to a specific or a default location
		System.out.println();
		if (specific.equals("Y")) {
			System.out.println("Please specify the file path of that location (e.g. src/ie/gmit/dip/out.png)");
			outputPath = scanner.nextLine(); // specific location option
		} else {
			outputPath = "src/ie/gmit/dip/out.png";			 // default output location option
		}

		
		
		kernel = KernelSelection(); // calling method which presents menu options to user
									// and prompts them to select a kernel
		
		
		BufferedImage processedImage = ImageProcessing.Process(ImageIO.read(new File(inputPath)), kernel ); // reading in image file, processing it with the chosen kernel
																											// and then converting it to a buffered image
		ImageIO.write(processedImage, "png", new File(outputPath));											// writing the image to the specified or default location
		

		System.out.println("Your image has been processed and saved to " + outputPath);  // message telling user the image has been processed
																					   // and where it has been saved to
																						// giving user the option of filtering the image again
		System.out.println("Would you like to filter the image again? (Y/N)");		  
		String again = scanner.nextLine();
		if (again.equals("Y")) {
			
			kernel = KernelSelection();
			processedImage = ImageProcessing.Process(ImageIO.read(new File(outputPath)), kernel );
			ImageIO.write(processedImage, "png", new File(outputPath));
			
			System.out.println("Your image has been processed and saved to " + outputPath + ".");
		} else {
			System.exit(0); 				// quit the program if user doesn't want to filter again
		}

	}
	
	public static double[][] KernelSelection() { // method which presents the user with menu options and prompts them to choose a kernel
		
		Scanner scanner = new Scanner(System.in);
		String filterCode;
		double[][] kernel = new double[3][3]; 
		
		while (true) {

			System.out.println("1) Select filter from a list"); 

			System.out.println();

			System.out.println("2) Create your own [3 x 3] convolution kernel"); 
		
			System.out.println();
			System.out.println("3) Quit"); // specifying the menu options

			System.out.println("\nSelect Option [1-3]>");

			String option = scanner.nextLine(); // selected menu option

			if (option.equals("1")) {

				while (true) {
					System.out.println();
					System.out.println("Please select a filter code from the list below"); // List the set of filters evalaible in the class Kernel.java
					System.out.println("   I = Identity"); // Each Kernel corresponds to a code
					System.out.println("   ED1 = Edge Detection 1"); 
					System.out.println("   ED2 = Edge Detection 2");
					System.out.println("   L = Laplacian");
					System.out.println("   S = Sharpen");
					System.out.println("   VL = Vertical Lines");
					System.out.println("   HL = Horizontal Lines");
					System.out.println("   D45L = Diagonal 45 lines");
					System.out.println("   BB = Box Blur");
					System.out.println("   SH = Sobel Horizontal");
					System.out.println("   SV = Sobel Vertical");
					filterCode = scanner.nextLine();		// takes in the code inputted by the user

					if (filterCode.equals("I")) { // if + else statements which select a kernel from the kernel class
						kernel = Kernel.IDENTITY; // based on the filter code value inputted by the user
						break;
					} else if (filterCode.equals("ED1")) {
						kernel = Kernel.EDGE_DETECTION_1;
						break;
					} else if (filterCode.equals("ED2")) {
						kernel = Kernel.EDGE_DETECTION_2;
						break;
					} else if (filterCode.equals("L")) {
						kernel = Kernel.LAPLACIAN;
						break;
					} else if (filterCode.equals("S")) {
						kernel = Kernel.SHARPEN;
						break;
					} else if (filterCode.equals("VL")) {
						kernel = Kernel.VERTICAL_LINES;
						break;
					} else if (filterCode.equals("HL")) {
						kernel = Kernel.HORIZONTAL_LINES;
						break;
					} else if (filterCode.equals("D45L")) {
						kernel = Kernel.DIAGONAL_45_LINES;
						break;
					} else if (filterCode.equals("BB")) {
						kernel = Kernel.BOX_BLUR;
						break;
					} else if (filterCode.equals("SH")) {
						kernel = Kernel.SOBEL_HORIZONTAL;
						break;
					} else if (filterCode.equals("SV")) {
						kernel = Kernel.SOBEL_VERTICAL;
						break;
					} else {
						System.out.println("Invlalid code. Please try again"); // all contained within a while loop. Program will
																			   // keep looping until valid code is entered
					}

				}

				break;

			} else if (option.equals("2")) { // option 2. Code below enables user to design a kernel

				System.out.println(
						"Please enter 9 convolution kernel values in seqeuntial order (Press enter key after typing each value):"); 
						// Read the matrix values

				
				
				// loop for row
				for (int i = 0; i < 3; i++)
					// inner for loop for column
					for (int j = 0; j < 3; j++)
						kernel[i][j] = scanner.nextDouble(); // filling 2d kernel array with inputted values

				System.out.println("You have entered the following convolution kernel: ");
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++)
						
						System.out.print(kernel[i][j] + " "); // prints the array elements
					
						System.out.println();
				}

				break;

			} else if (option.equals("3")) { // option to quit the program

				System.out.println("Program terminated!");
				System.exit(0);
				break;

			}

			// Default case statement. Loop won't exit until a number from 1-3 is entered
			else {

				System.out.println("Code invalid. Please try again");
				System.out.println();
			}
		}
		
		return kernel;
		
	}

	

}
