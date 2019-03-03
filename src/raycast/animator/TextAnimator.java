package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//this class handles the job of drawing on the canvas. it must extend AbstractAnimator
//@author Shahriar (Shawn) Emami
//@version Jan 13, 2019
public class TextAnimator extends AbstractAnimator {

    //Inherit the abstract method AbstractAnimator#handle(GraphicsContext, long) as public.
   @Override
    public void handle(GraphicsContext gc, long now) {
        //call GraphicsContext#save() which saves the current state of GraphicsContext
        gc.save();
        //create a new larger Font using Font.font( gc.getFont().getFamily(), FontWeight.BLACK, 50)
        Font font = Font.font(gc.getFont().getFamily(), FontWeight.BLACK, 50);
        //call GraphicsContext#setFont and set the newly created font
        gc.setFont(font);
        //call GraphicsContext#setFill and set a Color of your choice
        gc.setFill(Color.BLACK);
        //call GraphicsContext#fillText and use "CST 8288 - Ray Cast" and mouse.x() and mouse.y()
        gc.fillText("CST 8288 - Ray Cast", mouse.x(), mouse.y());
        //call GraphicsContext#setStroke and set a Color of your choice
        gc.setStroke(Color.GREEN);
        //call GraphicsContext#strokeText and use "CST 8288 - Ray Cast" and mouse.x() and mouse.y()
        gc.strokeText("CST 8288 - Ray Cast", mouse.x(), mouse.y());
        //call GraphicsContext#restore which restores the state of GraphicsContext
        gc.restore();
    }
    //create a toString method and return "Text animator" as the name of this class 
    public String toString() {
        return "Text Animator";
    }
}
