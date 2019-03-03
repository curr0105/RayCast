package raycast.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author kathy
 */
public class FpsCounter implements DrawableObject<FpsCounter> {

    public static double ONE_SECOND = 1000000000L;
    public static double HALF_SECOND = ONE_SECOND / 2F;
    private Font fpsFont;
    private String fpsDisplay;
    private int frameCount;
    private double lastTime;
    private double strokeWidth;
    private Color fill;
    private Color stroke;
    private double x;
    private double y;

    //constructor need to setPos and setFont for font use 
    public FpsCounter(double x, double y) {
    }

    //calculateFPS updates the frame count
    public void calculateFPS(long now) {
        //in an if condition, check if now - lastTime is bigger than HALF_SECOND
        if (now - lastTime > HALF_SECOND) {
            //assign frameCount*2 to fpsDisplay
            fpsDisplay = String.valueOf(frameCount * 2);
            //set frameCount to 0;
            frameCount = 0;
            //set lastTime to now;
            lastTime = now;
        }
        //increment frameCount
        frameCount++;
    }

    public FpsCounter setFont(Font font) {
        Font.font(Font.getDefault().getFamily(), FontWeight.BLACK, 24);
        return this;
    }

    public FpsCounter setPos(double x, double y) {
        return this;
    }
    
    //draws the current fps on canvas
    public void draw(GraphicsContext gc) {
        //get font from gc and save it in a temp variable to set it back later
        Font tempFont = gc.getFont();
        //call GraphicsContext#setFont and set fpsFont
        gc.setFont(fpsFont);
        //call GraphicsContext#setFill and pass fill
        gc.setFill(fill);
        //call GraphicsContext#fillText and pass fpsDisplay, x and y
        gc.fillText(fpsDisplay, x, y);
        //call GraphicsContext#setStroke and pass stroke
        gc.setStroke(stroke);
        //call GraphicsContext#setLineWidth and pass strokeWidth
        gc.setLineWidth(strokeWidth);
        //call GraphicsContext#strokeText and pass fpsDisplay, x and y
        gc.strokeText(fpsDisplay, x, y);
        //call GraphicsContext#setFont and pass to it the temp font created
        gc.setFont(tempFont);
    }

    @Override
    public FpsCounter setFill(Color color) {
        fill = color;
        return this;
    }   

    @Override
    public FpsCounter setStroke(Color color) {
        stroke = color;
        return this; 
    }

    @Override
    public FpsCounter setWidth(double width) {
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

}
