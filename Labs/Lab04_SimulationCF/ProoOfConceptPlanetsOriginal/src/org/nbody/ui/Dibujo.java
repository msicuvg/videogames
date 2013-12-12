package org.nbody.ui;

/*************************************************************************
 *  Compilation:  javac StdDraw.java
 *  Execution:    java StdDraw
 *
 *  Standard graphics library.
 *
 *  Todo
 *  ----
 *    -  Add support for gradient fill, etc.
 *
 *  Remarks
 *  -------
 *    -  don't use AffineTransform for rescaling since it inverts
 *       images and strings
 *    -  careful using setFont in inner loop within an animation -
 *       it can cause flicker
 *
 *************************************************************************/

import org.nbody.*;
import java.net.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;


/**
 *  <i>Standard draw</i>. This class provides a basic capability for 
 *  creating drawings with your programs. It uses a simple graphics model that
 *  allows you to create drawings consisting of points, lines, and curves
 *  in a window on your computer and to save the drawings to a file.
 *  <p>
 *  For additional documentation, see <a href="http://www.cs.princeton.edu/introcs/15inout">Section 1.5</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Dibujo extends JFrame{

    // pre-defined colors
    public static final Color BLACK      = Color.BLACK;
    public static final Color BLUE       = Color.BLUE;
    public static final Color CYAN       = Color.CYAN;
    public static final Color DARK_GRAY  = Color.DARK_GRAY;
    public static final Color GRAY       = Color.GRAY;
    public static final Color GREEN      = Color.GREEN;
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    public static final Color MAGENTA    = Color.MAGENTA;
    public static final Color ORANGE     = Color.ORANGE;
    public static final Color PINK       = Color.PINK;
    public static final Color RED        = Color.RED;
    public static final Color WHITE      = Color.WHITE;
    public static final Color YELLOW     = Color.YELLOW;

    // default colors
    private static final Color DEFAULT_PEN_COLOR   = BLACK;
    private static final Color DEFAULT_CLEAR_COLOR = WHITE;

    // current pen color
    private static Color penColor;
    // default canvas size is SIZE-by-SIZE
    private static final int DEFAULT_SIZE = 512;
    private static int width  = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;

    // default pen radius
    private static final double DEFAULT_PEN_RADIUS = 0.002;

    // current pen radius
    private static double penRadius;

    // show we draw immediately or wait until next show?
    private boolean defer = false;

    // boundary of drawing canvas, 5% border
    private static final double BORDER = 0.05;
    private static final double DEFAULT_XMIN = 0.0;
    private static final double DEFAULT_XMAX = 1.0;
    private static final double DEFAULT_YMIN = 0.0;
    private static final double DEFAULT_YMAX = 1.0;
    private static double xmin, ymin, xmax, ymax;
    private final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 16);  
    private BufferedImage offscreenImage, onscreenImage;
    private String title = "Universo";
    protected Graphics2D offscreen, onscreen;


    public Dibujo() {
        super();
        init();
    }
    public Dibujo(String title) {
        this.title = title;
        //this.universo = universo;
        init();
    }

    /**
     * Set the window size to w-by-h pixels
     *
     * @param w the width as a number of pixels
     * @param h the height as a number of pixels
     * @throws a RunTimeException if the width or height is 0 or negative.
     */
    public void setCanvasSize(int w, int h) {
        if (w < 1 || h < 1) throw new RuntimeException("width and height must be positive");
        width = w;
        height = h;
        init();
    }



    // init
    private void init() {

        //frame = new JFrame();
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        offscreen = offscreenImage.createGraphics();
        onscreen  = onscreenImage.createGraphics();
        setXscale();
        setYscale();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor();

        setPenRadius();
        setFont();
        clear();

        // add antialiasing
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        // frame stuff
        ImageIcon icon = new ImageIcon(onscreenImage);
        JLabel draw = new JLabel(icon);

        setContentPane(draw);
        setResizable(false);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);        
        this.setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);            // closes all windows
        setTitle(title);
        pack();
        requestFocusInWindow();
    
    }
    


   /*************************************************************************
    *  User and screen coordinate systems
    *************************************************************************/

    /**
     * Set the X scale to be the default
     */
    public void setXscale() { setXscale(DEFAULT_XMIN, DEFAULT_XMAX); }
    /**
     * Set the Y scale to be the default
     */
    public void setYscale() { setYscale(DEFAULT_YMIN, DEFAULT_YMAX); }
    /**
     * Set the X scale (a border is added to the values)
     * @param min the minimum value of the X scale
     * @param max the maximum value of the X scale
     */
    public void setXscale(double min, double max) {
        double size = max - min;
        xmin = min - BORDER * size;
        xmax = max + BORDER * size;
    }
    /**
     * Set the Y scale (a border is added to the values)
     * @param min the minimum value of the Y scale
     * @param max the maximum value of the Y scale
     */
    public void setYscale(double min, double max) {
        double size = max - min;
        ymin = min - BORDER * size;
        ymax = max + BORDER * size;
    }

    // helper functions that scale from user coordinates to screen coordinates and back
    private double  scaleX(double x) { return width  * (x - xmin) / (xmax - xmin); }
    private double  scaleY(double y) { return height * (ymax - y) / (ymax - ymin); }
    private double factorX(double w) { return w * width  / Math.abs(xmax - xmin);  }
    private double factorY(double h) { return h * height / Math.abs(ymax - ymin);  }
    


    /**
     * Clear the screen with the default color, white
     */
    public void clear() { clear(DEFAULT_CLEAR_COLOR); }
    /**
     * Clear the screen with the given color.
     * @param color the Color to make the background
     */
    public void clear(Color color) {
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        Show();
    }

    /**
     * Set the pen size to the default
     */
    public void setPenRadius() { setPenRadius(DEFAULT_PEN_RADIUS); }
    /**
     * Set the pen size to the given size 
     * @param r the radius of the pen
     * @throws RuntimeException if r is negative
     */
    public void setPenRadius(double r) {
        if (r < 0) throw new RuntimeException("pen radius must be positive");
        penRadius = r * DEFAULT_SIZE;
        // BasicStroke stroke = new BasicStroke((float) penRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        BasicStroke stroke = new BasicStroke((float) penRadius);
        offscreen.setStroke(stroke);
    }

    /**
     * Set the pen color to the default which is BLACK.
     */
    public void setPenColor() { setPenColor(DEFAULT_PEN_COLOR); }
    /**
     * Set the pen color to the given color. The available pen colors are 
       BLACK, BLUE, CYAN, DARK_GRAY, GRAY, GREEN, LIGHT_GRAY, MAGENTA, 
       ORANGE, PINK, RED, WHITE, and YELLOW.
     * @param color the Color to make the pen
     */
    public void setPenColor(Color color) {
        penColor = color;
        offscreen.setColor(penColor);
    }

    /**
     * Set the font to be the default for all string writing
     */
    public void setFont() { setFont(DEFAULT_FONT); }
    /**
     * Set the font as given for all string writing
     * @param f the font to make text
     */
  
    /**
     * Draw one pixel at (x, y)
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     */
    public void pixel(double x, double y) {
        offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    /**
     * Draw a point at (x, y)
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public void point(double x, double y) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = penRadius;
        // double ws = factorX(2*r);
        // double hs = factorY(2*r);
        // if (ws <= 1 && hs <= 1) pixel(x, y);
        if (r <= 1) pixel(x, y);
        else offscreen.fill(new Ellipse2D.Double(xs - r/2, ys - r/2, r, r));
        Show();
    }

  
  
   /*************************************************************************
    *  Drawing images.
    *************************************************************************/
   // get an image from the given filename
    public static Image getImage(URL url) {

        // to read from file
        //Object.getClass().getResource(filename);//)getClass().getResourceAsStream();
        ImageIcon icon = new ImageIcon(url);


        // in case file is inside a .jar
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            if (url == null || icon == null) throw new RuntimeException("image " + url.getFile() + " not found");
                //icon = new ImageIcon(url);
        }

        return icon.getImage();
    }

 
    // get an image from the given filename
    public static Image getImage(String filename) {

        // to read from file
        //Object.getClass().getResource(filename);//)getClass().getResourceAsStream();
        ImageIcon icon = new ImageIcon(filename);

        // try to read from URL
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            try {
                URL url = new URL(filename);
                icon = new ImageIcon(url);
            } catch (Exception e) { /* not a url */ }
        }

        // in case file is inside a .jar
        if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = Dibujo.class.getResource(filename);

            if (url == null) throw new RuntimeException("image " + filename + " not found");
            icon = new ImageIcon(url);
        }

        return icon.getImage();
    }

    public void picture(double x, double y, Image image) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        Show();
    }

    /**
     * Draw picture (gif, jpg, or png) centered on (x, y).
     * @param x the center x-coordinate of the image
     * @param y the center y-coordinate of the image
     * @param s the name of the image/picture, e.g., "ball.gif"
     * @throws RuntimeException if the image's width or height are negative
     */
    public void picture(double x, double y, String s) {
        Image image = getImage(s);
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) throw new RuntimeException("image " + s + " is corrupt");
        
        offscreen.drawImage(image, (int) Math.round(xs - ws/2.0), (int) Math.round(ys - hs/2.0), null);
        
        Show();
    }

    /**
     * Draw picture (gif, jpg, or png) centered on (x, y). 
     * Rescaled to w-by-h.
     * @param x the center x coordinate of the image
     * @param y the center y coordinate of the image
     * @param s the name of the image/picture, e.g., "ball.gif"
     * @param w the width of the image
     * @param h the height of the image
     */
    public void picture(double x, double y, String s, double w, double h) {
        Image image = getImage(s);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(w);
        double hs = factorY(h);
        if (ws < 0 || hs < 0) throw new RuntimeException("image " + s + " is corrupt");
        if (ws <= 1 && hs <= 1) pixel(x, y);
        else {
            offscreen.drawImage(image, (int) Math.round(xs - ws/2.0),
                                       (int) Math.round(ys - hs/2.0),
                                       (int) Math.round(ws),
                                       (int) Math.round(hs), null);
        }
        Show();
    }


  
   /*************************************************************************
    *  Drawing text.
    *************************************************************************/    
    /**
     * Display on screen and pause for t milliseconds.
     * Calling this method means that the screen will NOT be redrawn
     * after each line(), circle(), or square(). This is useful when there
     * are many methods to call to draw a complete picture.
     * @param t number of milliseconds
     */
    public void show(int t) {
        defer = true;
        onscreen.drawImage(offscreenImage, 0, 0, null);
        repaint();
        try { Thread.currentThread().sleep(t); }
        catch (InterruptedException e) { System.out.println("Error sleeping"); }
    }


    /**
     * Display on-screen;
     * calling this method means that the screen WILL be redrawn
     * after each line(), circle(), or square(). This is the default.
     */
    
    public void Show() {
        if (!defer) onscreen.drawImage(offscreenImage, 0, 0, null);
        if (!defer) repaint();
    }
}
