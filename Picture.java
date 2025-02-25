import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * @author Barbara Ericson ericson@cc.gatech.edu
 * @author Ani Kumar
 */
public class Picture extends SimplePicture {
  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture () {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
  /* Method to set the blue to 0 */
  public void zeroBlue() {
    this.setTitle("zeroBlue");
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels) {
      for (Pixel pixelObj : rowArray) {
        pixelObj.setBlue(0);
      }
    }
  }

  /* Method to set the red and green to 0 */
  public void keepOnlyBlue() {
    this.setTitle("keepOnlyBlue");
	  Pixel[][] pixels = this.getPixels2D();
	  for (Pixel[] rowArray : pixels) {
		  for (Pixel pixelObj : rowArray) {
			  pixelObj.setRed(0);
			  pixelObj.setGreen(0);
		  }
	  }
  }

  /* Method to invert all 3 RGB values */
  public void negate() {
    this.setTitle("negate");
	  Pixel[][] pixels = this.getPixels2D();
	  for (Pixel[] rowArray : pixels) {
		  for (Pixel pixelObj : rowArray) {
			  pixelObj.setRed(255 - pixelObj.getRed());
			  pixelObj.setGreen(255 - pixelObj.getGreen());
			  pixelObj.setBlue(255 - pixelObj.getBlue());
		  }
	  }
  }

  /* Method to average all 3 RGB values to turn picture into shades of gray */
  public void grayscale() {
    this.setTitle("grayscale");
    Pixel[][] pixels = this.getPixels2D();
	  for (Pixel[] rowArray : pixels) {
		  for (Pixel pixelObj : rowArray) {
			  int val = (pixelObj.getRed() + pixelObj.getGreen() + pixelObj.getBlue()) / 3;
			  pixelObj.setRed(val);
			  pixelObj.setGreen(val);
			  pixelObj.setBlue(val);
		  }
	  }
  }
  
  /** To pixelate by dividing area into size x size.
   * @param size    Side length of square area to pixelate */
  public void pixelate(int size) {
    this.setTitle("pixelate " + size);
    Pixel[][] pixels = this.getPixels2D();
    int height = this.getHeight();
    int width = this.getWidth();
    for (int row = 0; row < height; row += size) {
      for (int col = 0; col < width; col += size) {
        int sumRed, sumGreen, sumBlue, count;
        sumRed = sumGreen = sumBlue = count = 0;
        // get averages of 3 colors
        for (int i = row; i < row + size && i < height; i++) {
          for (int j = col; j < col + size && j < width; j++) {
            Pixel pixel = pixels[i][j];
            sumRed += pixel.getRed();
            sumGreen += pixel.getGreen();
            sumBlue += pixel.getBlue();
            count++;
          }
        }
        // set square to average color
        int avgRed = sumRed / count;
        int avgGreen = sumGreen / count;
        int avgBlue = sumBlue / count;
        Color averageColor = new Color(avgRed, avgGreen, avgBlue);
        for (int i = row; i < row + size && i < height; i++) {
          for (int j = col; j < col + size && j < width; j++) {
            Pixel pixel = pixels[i][j];
            pixel.setColor(averageColor);
          }
        }
      }
    }
  }

  /** Method that blurs the picture
   * @param size    Blur size, greater is more blur
   * @return        Blurred picture
   */
  public Picture blur(int size) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    result.setTitle("blur " + size);
    Pixel[][] resultPixels = result.getPixels2D();
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        int redTotal, greenTotal, blueTotal, count;
        redTotal = greenTotal = blueTotal = count = 0;
        for (int i = row - size; i <= row + size; i++) {
          for (int j = col - size; j <= col + size; j++) {
            if (i >= 0 && i < pixels.length &&
                    j >= 0 && j < pixels[0].length) {
              redTotal += pixels[i][j].getRed();
              greenTotal += pixels[i][j].getGreen();
              blueTotal += pixels[i][j].getBlue();
              count++;
            }
          }
        }
        // set RGB values to average of pixels around it
        resultPixels[row][col].setRed(redTotal / count);
        resultPixels[row][col].setGreen(greenTotal / count);
        resultPixels[row][col].setBlue(blueTotal / count);
      }
    }
    return result;
  }

  /** Method that enhances a picture by getting average Color around
   *  a pixel then applies the following formula:
   *    pixelColor <- 2 * currentValue - averageValue
   *  size is the area to sample for blur.
   *
   * @param size    Larger means more area to average around pixel
   *                      and longer compute time
   * @return        Enhanced picture
   */
  public Picture enhance(int size) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    result.setTitle("enhance " + size);
    Pixel[][] resultPixels = result.getPixels2D();
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < pixels[0].length; col++) {
        int avgRed, avgGreen, avgBlue, count;
        avgRed = avgGreen = avgBlue = count = 0;
        // get average RGB values of surrounding pixels
        for (int i = -size / 2; i <= size / 2; i++) {
          for (int j = -size / 2; j <= size / 2; j++) {
            if (row + i >= 0 && row + i < pixels.length &&
                    col + j >= 0 && col + j < pixels[0].length) {
              avgRed += pixels[row + i][col + j].getRed();
              avgGreen += pixels[row + i][col + j].getGreen();
              avgBlue += pixels[row + i][col + j].getBlue();
              count++;
            }
          }
        }
        avgRed /= count;
        avgGreen /= count;
        avgBlue /= count;
        // use formula to enhance pixel
        int enhancedRed = 2 * pixels[row][col].getRed() - avgRed;
        int enhancedGreen = 2 * pixels[row][col].getGreen() - avgGreen;
        int enhancedBlue = 2 * pixels[row][col].getBlue() - avgBlue;
        // set enhanced values to new Picture while ensuring RGB values stay within valid range
        resultPixels[row][col].setRed(Math.min(Math.max(enhancedRed, 0), 255));
        resultPixels[row][col].setGreen(Math.min(Math.max(enhancedGreen, 0), 255));
        resultPixels[row][col].setBlue(Math.min(Math.max(enhancedBlue, 0), 255));
      }
    }
    return result;
  }

  /**
   * Swap the left and right sides of a picture by shifting pixels
   * @return        Swapped picture
   */
  public Picture swapLeftRight() {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(this);
    result.setTitle("swapLeftRight");
    Pixel[][] resultPixels = result.getPixels2D();
    int height = this.getHeight();
    int width = this.getWidth();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel p = pixels[row][col];
        int newColumn = (col + width / 2) % width;
        while (newColumn >= width)
          newColumn -= width;
        resultPixels[row][newColumn].setColor(new Color(p.getRed(),
                p.getGreen(), p.getBlue()));
      }
    }
    return result;
  }

  /** Create a jagged picture using stair steps of shifted pixels
   * @param shiftCount The number of pixels to shift to the right
   * @param steps      The number of steps
   * @return           The picture with pixels shifted in stair steps
   */
  public Picture stairStep(int shiftCount, int steps) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(pixels.length, pixels[0].length);
    result.setTitle("stairStep " + shiftCount + " " + steps);
    Pixel[][] resultPixels = result.getPixels2D();
    int height = this.getHeight();
    int width = this.getWidth();
    int count = 0, step = 1;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (height / steps == count) {
          step++;
          count = 0;
        }
        Pixel p = pixels[row][col];
        int newColumn = col + shiftCount * step;
        while (newColumn >= width)
          newColumn -= width;
        resultPixels[row][newColumn].setColor(new Color(p.getRed(),
                p.getGreen(), p.getBlue()));
      }
      count++;
    }
    return result;
  }

  /**
   * Distort horizontal center by shifting pixels horizontally
   * @param maxHeight   Max height (shift) of curve in pixels
   * @return 		    Liquified picture
   */
  public Picture liquify(int maxHeight) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(this);
    result.setTitle("liquify " + maxHeight);
    Pixel[][] resultPixels = result.getPixels2D();
    int height = this.getHeight();
    int width = this.getWidth();
    int bellWidth = 50;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel p = pixels[row][col];
        double exponent = Math.pow(row - this.getHeight() / 2.0, 2) / (2.0 * Math.pow(bellWidth, 2));
        int rightShift = (int)(maxHeight * Math.exp(-exponent));
        int newCol = (col + rightShift) % width;
        resultPixels[row][newCol].setColor(new Color(p.getRed(),
                p.getGreen(), p.getBlue()));
      }
    }
    return result;
  }

  /**
   * Distort left and right in sinusoidal pattern
   * @param amplitude	The maximum shift of pixels
   * @return		    Wavy picture
   */
  public Picture wavy(int amplitude) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(this);
    result.setTitle("wavy " + amplitude);
    Pixel[][] resultPixels = result.getPixels2D();
    int height = this.getHeight();
    int width = this.getWidth();
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel p = pixels[row][col];
        double frequency = 1.0 / (240);
        int phaseShift = 0;
        int shift = (int)(amplitude * Math.sin(2 * Math.PI * frequency * row + phaseShift));
        int newCol = col + shift;
        while (newCol >= width)
          newCol -= width;
        while (newCol < 0)
          newCol += width;
        resultPixels[row][newCol].setColor(new Color(p.getRed(),
                p.getGreen(), p.getBlue()));
      }
    }
    return result;
  }

  /** Method that creates an edge detected black/white picture
    * @param threshold		Threshold as determined by Pixel’s colorDistance method
    * @return 				Edge detected picture
	*/
  public Picture edgeDetectionBelow(int threshold) {
	  Pixel[][] pixels = this.getPixels2D();
	  Picture result = new Picture(pixels.length, pixels[0].length);
	  result.setTitle("edgeDetectionBelow " + threshold);
	  Pixel[][] resultPixels = result.getPixels2D();
	  for (int row = 0; row < pixels.length - 1; row++) {
		  for (int col = 0; col < pixels[0].length; col++) {
			  Pixel currPixel = pixels[row][col];
			  Pixel belowPixel = pixels[row + 1][col];
			  if (currPixel.colorDistance(belowPixel.getColor()) > threshold)
				  resultPixels[row][col].setColor(Color.BLACK);
			  else resultPixels[row][col].setColor(Color.WHITE);
		  }
	  }
	  return result;
  }

  /**
    * Method that creates a green screen picture
    * @return         	    green screen picture
    */
  public Picture greenScreen() {
    Picture bkgnd = new Picture("greenScreenImages/IndoorHouseLibraryBackground.jpg");
    Pixel[][] bkgndPixels = bkgnd.getPixels2D();
    bkgnd.setTitle("greenScreen");
    Picture kitten = new Picture("greenScreenImages/kitten1GreenScreen.jpg");
    Picture mouse = new Picture("greenScreenImages/mouse1GreenScreen.jpg");
    Pixel[][] kittenPixels = kitten.getPixels2D();
    Pixel[][] mousePixels = mouse.getPixels2D();
    for (int i = 0; i < kittenPixels.length; i++) {
      for (int j = 0; j < kittenPixels[0].length; j++) {
        // subtracts 25 so slightly greenish pixels are considered and halos are mitigated
        // if red or blue values are greater the pixel is NOT green and is added
        if (kittenPixels[i][j].getGreen() - 25 < kittenPixels[i][j].getRed() ||
                kittenPixels[i][j].getGreen() - 25 < kittenPixels[i][j].getBlue()) {
          // new coordinates for image to be overlayed
          int newRow = i / 2 + 365;
          int newCol = j / 2 + 520;
          // checks bounds and sets background pixel colors to corresponding kitten pixel
          if (newRow < bkgndPixels.length && newCol < bkgndPixels[0].length) {
            bkgndPixels[newRow][newCol].setColor(kittenPixels[i][j].getColor());
          }
        }
      }
    }
    for (int k = 0; k < mousePixels.length; k++) {
      for (int l = 0; l < mousePixels[0].length; l++) {
        // identical check for green pixels as done with kitten image
        if (mousePixels[k][l].getGreen() - 25 < mousePixels[k][l].getRed() ||
                mousePixels[k][l].getGreen() - 25 < mousePixels[k][l].getBlue()) {
          // new coordinates for image to be overlayed
          int newRow = k / 2 + 350;
          int newCol = l / 2 + 300;
          // checks bounds and sets background pixel colors to corresponding mouse pixel
          if (newRow < bkgndPixels.length && newCol < bkgndPixels[0].length) {
            bkgndPixels[newRow][newCol].setColor(mousePixels[k][l].getColor());
          }
        }
      }
    }
    return bkgnd;
  }

  /**
    * Rotate image in radians, clean up "drop-out" pixels
    * @param angle        Angle of rotation in radians
    * @return             Picture that is rotated
   */
  public Picture rotate(double angle) {
    Pixel[][] pixels = this.getPixels2D();
    Picture result = new Picture(this);
    result.setTitle("rotate");
    Pixel[][] resultPixels = result.getPixels2D();
    int height = this.getHeight();
    int width = this.getWidth();
    // center coordinates
    int centerX = width / 2;
    int centerY = height / 2;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int x = col - centerX;
        int y = row - centerY;
        int x1 = (int) (x * Math.cos(-angle) - y * Math.sin(-angle)) + centerX;
        int y1 = (int) (x * Math.sin(-angle) + y * Math.cos(-angle)) + centerY;
        if (x1 >= 0 && x1 < width && y1 >= 0 && y1 < height)
          resultPixels[row][col].setColor(pixels[y1][x1].getColor());
        else
          resultPixels[row][col].setColor(Color.WHITE);
      }
    }
    return result;
  }

  /** Method that mirrors the picture around a
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical() {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; col < width / 2; col++) {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /* Mirror just part of a picture of a temple */
  public void mirrorTemple() {
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();
    // loop through the rows
    for (int row = 27; row < 97; row++) {
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++) {
        leftPixel = pixels[row][col];      
        rightPixel = pixels[row]                       
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, 
                 int startRow, int startCol) {
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; 
         fromRow < fromPixels.length &&
         toRow < toPixels.length; 
         fromRow++, toRow++) {
      for (int fromCol = 0, toCol = startCol; 
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;  
           fromCol++, toCol++) {
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }   
  }

  /* Method to create a collage of several pictures */
  public void createCollage() {
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }

  /** Method to show large changes in color 
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist) {
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++) {
      for (int col = 0; 
           col < pixels[0].length-1; col++) {
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > 
            edgeDist)
          leftPixel.setColor(Color.BLACK);
        else
          leftPixel.setColor(Color.WHITE);
      }
    }
  }

  /* Main method for testing - each class in Java can have a main method */
  public static void main(String[] args) {
    Picture beach = new Picture("images/beach.jpg");
    beach.explore();
    beach.zeroBlue();
    beach.explore();
  }
}