/*
 * This object represents a multi-sided shape.  It keeps track of corners coordinates. These coordinates are
 * stored in a 2 dimensional array called points.  Each column is one point, meaning row zero is x and row 1 is y.
 * This is IMPORTANT, do not mix it up.  For example, third corner (x,y) is (point [0][2],points[1][2]).
 */
package raycast.entity.geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import raycast.entity.DrawableObject;

/**
 *
 * @author kathy
 */
public class PolyShape implements DrawableObject<PolyShape> {

    private int pointCount;
    //2 dimensional array that holds coordinates for the corners of a multi-sided shape.
    private double points[][];
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;
    //stroke width drawing
    private double strokeWidth;
    //fill and stroke color values
    private Color fill, stroke;
    private RectangleBounds bounds;

    public PolyShape() {
        strokeWidth = 1;
        fill = Color.PINK;
        stroke = Color.BLACK;
    }

    //setPoints can take many points. This method uses "..." which is called vararg. Nums is a one dimensional 
    //array, every 2 index is considered one point. This method will initialize rest of variables in PolyShape object. 
    //min and max variables are used in this method to determine the 4 corners of PolyShape so bounds 
    //can be initialized.
    public PolyShape setPoints(double... nums) {
        //set nums[0] to minX and maxX, First x
        minX = maxX = nums[0];
        //set nums[1] to minY and maxY, First y
        minY = maxY = nums[1];
        //set nums.length /2 to pointCount
        pointCount = nums.length / 2;
        //initialize points with 2 rows and pointCount of Columns
        double points[][] = new double[2][pointCount];
        //start a for loop with i and j at zero.  j increments by 1 and i increments by 2. 
        //continue looping as long as i is smaller than nums.length.
        //i is used to access every 2 point in nums array, while j is used to fill points array
        for (int i = 0, j = 0; i < nums.length; i+=2, j++) {
            //call updateMinMax and pass nums[i] and nums[i+1]. this method checks if min and max has changed
            updateMinMax(nums[i], nums[i + 1]);
            //assign nums[i] to points[0][j]
            points[0][j] = nums[i];
            //assign nums[i+1] to points[1][j]
            points[1][j] = nums[i + 1];
            //initialize bounds with minX, minY, maxX-minX and maxY-minY
            RectangleBounds bounds = new RectangleBounds(minX, minY, maxX - minX, maxY - minY);
        }
        return this;
    }

    //this is an internal helper method to update min and max values based on the x and y input.  Inside check if
    //minX and maxX need to be updated based on new x, then do the same for y.
    private void updateMinMax(double x, double y) {
        if (maxX < x) {
            maxX = x;
            
        }else if(minX > x) {
            minX = x;
        }
    
        if (maxY < y) {
            maxY = y;
               
        }else if(minY > y){
            minY = y;
        }
        
    }

    public int pointCount() {
        return pointCount;
    }

    public double[][] points() {
        return points;
    }

    //return x and y of specific corner. Remember how points is ordered, read PolyShape description again. 
    public double pX(int index) {
        return points[0][index];
    }

    //return x and y of specific corner. Remember how points is ordered, read PolyShape description again. 
    public double pY(int index) {
        return points[1][index];
    }

    //draws little circles on the corner os the shape, plus a little number
    public void drawCorners(GraphicsContext gc) {
        //start a for loop which should be smaller that pointCount
        for (int i = 0; i < pointCount;) {
            //this will make a little number counter near the corner
            gc.fillText(Integer.toString(i), points[0][i] - 5, points[1][i] - 5);
            gc.fillOval(points[0][i] - 5, points[1][i] - 5, 10, 10);
        }
    }

    public RectangleBounds getBounds() {
        return bounds;
    }

    @Override
    public PolyShape setFill(Color color) {
        fill = color;
        return this;
    }

    @Override
    public PolyShape setStroke(Color color) {
        stroke = color;
        return this;
    }

    @Override
    public PolyShape setWidth(double width) {
        strokeWidth = width;
        return this;
    }

    @Override
    public Color getFill() {
        return fill;
    }

    @Override
    public Color getStroke() {
        return stroke;
    }

    @Override
    public double getWidth() {
        return strokeWidth;
    }

    //draws the shape
    @Override
    public void draw(GraphicsContext gc) {
        //call setLineWidth on gc and pass StrokeWidth
        gc.setLineWidth(strokeWidth);
        //if stroke is not null
        if (stroke != null) {
            //call setStroke on gc and pass stroke
            gc.setStroke(stroke);
            //call strokePolygon on gc and pass points[0], points[1] and pointCount
            gc.strokePolygon(points[0], points[1], pointCount);
            //if fill is not null
        } else {
            //call setFill on gc and pass fill
            gc.setFill(fill);
            //call fillPolygon on gc and pass points[0], points[1] and pointCount
            gc.fillPolygon(points[0], points[1], pointCount);
        }
    }
}
