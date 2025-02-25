/**
 * This class contains class (static) methods
 * that will help you test the Picture class
 * methods. Uncomment the methods and the code
 * in the main to test.
 * 
 * @author Barbara Ericson
 */
public class PictureTester {
    /* Method to test zeroBlue */
    public static void testZeroBlue() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        beach.zeroBlue();
        beach.explore();
    }

    /* Method to test keepOnlyBlue */
    public static void testKeepOnlyBlue() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        beach.keepOnlyBlue();
        beach.explore();
    }

    /* Method to test negate */
    public static void testNegate() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        beach.negate();
        beach.explore();
    }

    /* Method to test grayscale */
    public static void testGrayscale() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        beach.grayscale();
        beach.explore();
    }

    /* Method to test pixelate */
    public static void testPixelate() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        beach.pixelate(15);
        beach.explore();
    }

    /* Method to test blur */
    public static void testBlur() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        Picture blurredImage = beach.blur(5);
        blurredImage.explore();
    }

    /* Method to test enhance */
    public static void testEnhance() {
        Picture water = new Picture("images/water.jpg");
        water.explore();
        Picture enhancedImage = water.enhance(10);
        enhancedImage.explore();
    }

    /* Method to test swapLeftRight */
    public static void testSwap() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        Picture swappedImage = beach.swapLeftRight();
        swappedImage.explore();
    }

    /* Method to test stairStep */
    public static void testStair() {
        Picture redMotorcycle = new Picture("images/arch.jpg");
        redMotorcycle.explore();
        Picture stairImage = redMotorcycle.stairStep(1, 280);
        stairImage.explore();
    }

    /* Method to test liquify */
    public static void testLiquify() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        Picture liquidImage = beach.liquify(100);
        liquidImage.explore();
    }

    /* Method to test wavy */
    public static void testWavy() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        Picture wavyImage = beach.wavy(20);
        wavyImage.explore();
    }

    /* Method to test edgeDetectionBelow */
    public static void testEdgeDetectionBelow() {
        Picture swan = new Picture("images/swan.jpg");
        swan.explore();
        Picture swanImage = swan.edgeDetectionBelow(10);
        swanImage.explore();
    }

    /* Method to test greenScreen */
    public static void testGreenScreen() {
        Picture beach = new Picture("images/beach.jpg");
        Picture gScreen = beach.greenScreen();
        gScreen.explore();
    }

    /* Method to test rotate */
    public static void testRotate() {
        Picture beach = new Picture("images/beach.jpg");
        beach.explore();
        Picture rotateImage = beach.rotate(Math.PI/6);
        rotateImage.explore();
    }

    /* Method to test mirrorVertical */
    public static void testMirrorVertical() {
        Picture caterpillar = new Picture("images/caterpillar.jpg");
        caterpillar.explore();
        caterpillar.mirrorVertical();
        caterpillar.explore();
    }

    /* Method to test mirrorTemple */
    public static void testMirrorTemple() {
        Picture temple = new Picture("images/temple.jpg");
        temple.explore();
        temple.mirrorTemple();
        temple.explore();
    }

    /* Method to test the collage method */
    public static void testCollage() {
        Picture canvas = new Picture("images/640x480.jpg");
        canvas.createCollage();
        canvas.explore();
    }

    /* Method to test edgeDetection */
    public static void testEdgeDetection() {
        Picture swan = new Picture("images/swan.jpg");
        swan.edgeDetection(10);
        swan.explore();
    }

    /* Main method for testing. Every class can have a main method in Java */
    public static void main(String[] args) {
        //testZeroBlue();
        //testKeepOnlyBlue();
        //testKeepOnlyRed();
        //testKeepOnlyGreen();
        //testNegate();
        //testGrayscale();
        //testFixUnderwater();
        //testMirrorVertical();
        //testMirrorTemple();
        //testMirrorArms();
        //testMirrorGull();
        //testMirrorDiagonal();
        //testCollage();
        //testCopy();
        //testEdgeDetection();
        //testEdgeDetection2();
        //testChromakey();
        //testEncodeAndDecode();
        //testGetCountRedOverValue(250);
        //testSetRedToHalfValueInTopHalf();
        //testClearBlueOverValue(200);
        //testGetAverageForColumn(0);
        //testPixelate();
        //testBlur();
        //testEnhance();
        //testSwap();
        //testStair();
        //testLiquify();
        //testWavy();
        //testEdgeDetectionBelow();
        //testGreenScreen();
        testRotate();
    }
}