/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raycast.animator;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author kathy
 */
public class StaticShapes extends AbstractAnimator {
    
    private static Color BACKGROUND = Color.AQUA;

    @Override
    protected void handle(GraphicsContext gc, long now) {
        //call clearAndFill and pass gc and color of your choice
        clearAndFill(gc, Color.GREEN);
        //for each shape in c.shapes(), call draw and pass gc
//***TODO       
        
    }
     //create a toString method and return "Static Shapes" as the name of this class 
    public String toString() {
        return "Static Shapes";
    }
}
