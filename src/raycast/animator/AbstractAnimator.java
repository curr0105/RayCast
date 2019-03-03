package raycast.animator;

import java.util.Objects;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import raycast.CanvasMap;
import raycast.entity.FpsCounter;
import utility.Point;

//this class must extend AnimationTimer. job of this class is to hold common functionality among animators.
//@author Shahriar (Shawn) Emami
//@version Jan 13, 2019
public abstract class AbstractAnimator extends AnimationTimer {

    //a protected class variable of type CanvasMap named map.
    protected CanvasMap map;

    //a protected class variable of type Point named mouse.
    protected Point mouse;

    //added for Lab3
    //initialize fpsCounter object. ??Add fill, stroke and width as desired.??
    protected FpsCounter fps;

    //a protected constructor that initializes the AbstractAnimator#mouse variable
    protected AbstractAnimator() {
        mouse = new Point();
        intersectResult = new double[4];
    }

    //a setter called setCanvas sets the CanvasMap to map - CanvasMap object
    public void setCanvas(CanvasMap map) {
        this.map = map;
    }

    //a method called mouseDragged that is called every time the position of mouse changes. 
    //@param e - MouseEvent object that holds the details of the mouse. using MouseEvent#getX and 
    //MouseEvent#getY
    public void mouseDragged(MouseEvent e) {
        mouse.set(e.getX(), e.getY());
    }

    //a method called mouseMoved that is called every time the position of mouse changes.
    //@param e - MouseEvent object that holds the details of the mouse. using MouseEvent#getX and
    //MouseEvent#getY
    public void mouseMoved(MouseEvent e) {
        mouse.set(e.getX(), e.getY());
    }

    //create a method called handle that is inherited from AnimationTimer#handle(long). 
    //this method is called by JavaFX application, it should not be called directly.
    //inside of this method call the abstract handle method AbstractAnimator#handle(GraphicsContext, long). 
    //GraphicsContext can be retrieved from CanvasMap#gc() @param now - current time in nanoseconds, 
    //represents the time that this function is called.
    //For Lab 3, this method needs to be modified so fps can be drawn. Fps drawing
    //will be placed in AbstractAnimator since it is shared.
    @Override
    public void handle(long now) {
        GraphicsContext gc = map.gc();
        
        //added for Lab3
        //if drawFPS is true in canvasMap
        if (map.getDrawFPS()) {
            //call calculateFPS on fps and pass now
            fps.calculateFPS(now);
        }
        //this line is from Lab2
        handle(gc, now);
        //if DrawShapeJoints orDrawBounds is true
        if (map.getDrawShapeJoints() || map.getDrawBounds()) {
            
         //loop through each shape in map.shapes()
//***TODO           
                //if DrawBounds is true
                if (map.getDrawBounds()) {
                    //call draw getBounds on shape then call draw on bounds and pass gc
                    shape.drawGetBounds();
                    bounds.draw(gc);
                   
                    //if DrawShapeJoints is true
                 } else if (map.getDrawShapeJoints()) {
                    //call drawCorners on shape and pass gc to it
                    shape.drawCorners(gc);
                 }
         }
            //if drawFPS is true in canvasMap
         if (map.getDrawFPS()) {
                //call draw on fps and pass gc
                fps.draw(gc);
         }   
    }
    //a protected abstract method called handle, it is overridden by subclasses.
    //@param gc - GraphicsContext object.  now (current time in nanoseconds) represents the time
    //that this function is called.
    protected abstract void handle(GraphicsContext gc, long now);

    //added for Lab3
    public void clearAndFill(GraphicsContext gc, Color background) {
        gc.setFill(background);
        gc.clearRect(0, 0, map.w(), map.h());
        gc.fillRect(0, 0, map.w(), map.h());
    }

    //All of this code is added from the pdf Junit Testing the Math
    // this array has in order x, y, scalar of intersect point with ray and scalar of intersect with line segment.
    protected double[] intersectResult;
    // return the result of getIntersection methods calculations
    // return intersectResult

    public double[] intersect() {
        Objects.requireNonNull(intersectResult, "intersectResult array must be initilized in the constructor.");
        if (intersectResult.length != 4) {
            throw new IllegalStateException("intersectResult must have length of 4");
        }
        return intersectResult;
    }

    // Determine if a light ray and a line segment intersect.
    public boolean getIntersection(double rsx, double rsy, double rex, double rey,
            double ssx, double ssy, double sex, double sey) {

        // given 2 line segments as vectors their intersect will be q + tr or p + us where
        // q and p are the starting point in from of (x, y),
        // r and s are the distance of end point to start point in form of ( x2x1,y2y1),
        // t and u are scaler values belonging to real numbers, such as 0.5, 1, 1.
        // by finding t and u the intersect can be found.
        // t and u can be found by equaling q + tr = p + us
        // this function can be refactored as below, look at the link in documentation for more details.
        // x is cross product
        // t = (q - p) x s / (r x s)
        // u = (q p) x r / (r x s)
        // (q - p) x s = ((qx - px) sy-sx (qy - py))
        // (q - p) x r = ((qx - px) ry - rx (qy - py))
        // (r x s) = (rxsy - sxry)
        double qpx = rsx - ssx;
        double qpy = rsy - ssy;

        double rx = rex - rsx;
        double ry = rey - rsy;
        double sx = sex - ssx;
        double sy = sey - ssy;

        double qps = qpx * sy - sx * qpy;
        double qpr = qpx * ry - rx * qpy;

        double rs = rx * sy - sx * ry;

        double rayScaler = -qps / rs;
        double segmentScaler = -qpr / rs;

        intersectResult[0] = rsx + rx * rayScaler;
        intersectResult[1] = rsy + ry * rayScaler;
        intersectResult[2] = rayScaler;
        intersectResult[3] = segmentScaler;
        return rs != 0 && rayScaler >= 0 && segmentScaler >= 0 && segmentScaler <= 1;

    }

}
