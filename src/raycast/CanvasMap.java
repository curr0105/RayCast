package raycast;

import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import raycast.animator.AbstractAnimator;
import raycast.entity.geometry.PolyShape;

//this class represents the drawing area. it is backed by Canvas class. this
//class itself does not handle any of the drawing. this task is accomplished by
//the AnimationTimer.
//@author Shahriar (Shawn) Emami
//@version Jan 13, 2019
public class CanvasMap {

    //create a Canvas object call board. it provides the tools to draw in
    //JavaFX. this is also a Node which means can be added to our JavaFX
    //application. the object needed to draw on a Canvas is GraphicsContext
    //which is retrieved using Canvas#getGraphicsContext2D()method.
    private Canvas board;
    
    //create a AbstractAnimator called animator. AnimationTimer provides most
    //common functionally needed to draw animations of ray casting.
    private AbstractAnimator animator;
    
    //create an IntegerProperty called rayCount to keep track of ray count
    //changes. this variable can be initialized with SimpleIntegerProperty 
    private IntegerProperty rayCount = new SimpleIntegerProperty();
    
    //added for Lab3
    private List<PolyShape> shapes;

    /**
     * <pre>
     * IntegerProperty i1 = new SimpleIntegerProperty( 1);
     * IntegerProperty i2 = new SimpleIntegerProperty();
     * i1.bind( i2);
     * i2.set( 100);
     * System.out.println( i1.get()); // prints 100
     */
    //create a getter that returns IntegerProperty
    public IntegerProperty rayCountProperty() {
        return rayCount;
    }
    //and a method that returns IntegerProperty-get()
    public int getRayCount(){
        return rayCount.get();
    }

    //create a set of BooleanProperty's to track some drawing options.
    //(drawLightSource, drawIntersectPoint, drawShapeJoints, drawSectors, drawBounds, drawFPS)
    //these variables can be initialized with SimpleBooleanProperty
    private final BooleanProperty drawLightSource = new SimpleBooleanProperty();
    private final BooleanProperty drawIntersectPoint = new SimpleBooleanProperty();
    private final BooleanProperty drawShapeJoints = new SimpleBooleanProperty();
    private final BooleanProperty drawSectors = new SimpleBooleanProperty();
    private final BooleanProperty drawBounds = new SimpleBooleanProperty();
    private final BooleanProperty drawFPS = new SimpleBooleanProperty();

    /**
     * < pre>
     * BooleanProperty b1 = new SimpleBooleanProperty( false); BooleanProperty
     * b2 = new SimpleBooleanProperty(); b1.bind( b2); b2.set( true);
     * System.out.println( b1.get()); // prints true
     * </pre>
     * @return 
     */
    //create a getter that returns BooleanProperty and a method that returns
    //BooleanProperty#get() for each BooleanProperty.
    public BooleanProperty drawFPSProperty(){
        return drawFPS;
    }
    public boolean getDrawFPS() {
        return drawFPS.get();
    }
    
    public BooleanProperty drawLightSource(){
        return drawLightSource;
    }
    public boolean getDrawLightSource() {
        return drawLightSource.get();
    }
   
    public BooleanProperty drawDrawIntersectPoint(){
        return drawIntersectPoint;
    }
    public boolean getDrawIntersectPoint() {
        return drawIntersectPoint.get();
    }
    
    public BooleanProperty drawDrawShapeJoints(){
        return drawShapeJoints;
    }
    public boolean getDrawShapeJoints() {
        return drawShapeJoints.get();
    }

    public BooleanProperty drawDrawSectors(){
        return drawSectors;
    }
    public boolean getDrawSectors() {
        return drawSectors.get();
    }

   public BooleanProperty drawDrawBounds(){
       return drawBounds;
   }
   public boolean getDrawBounds() {
        return drawBounds.get();
    }

    //create a constructor and initialize all class variables.
    public CanvasMap() {
        board = new Canvas();
//***TODO        
    //constructor initialize shapes with ArrayList of initial size 20    
    }
    
    
    //the function setAnimator below sets the class variable "animator" and returns an AbstrastAnimator
    //create a method called setAnimator. set an AbstractAnimator. if an animator exists CanvasMap#stop()it 
    //and call CanvasMap#removeMouseEvents(). then set the new animator and call CanvasMap#start() and
    //CanvasMap#registerMouseEvents().
    //@param newAnimator - new AbstractAnimator object
    //@return the current instance of this object
    public CanvasMap setAnimator(AbstractAnimator newAnimator) {

        if (animator != null) {
            stop();
            removeMouseEvents();
        }

        animator = newAnimator;
        start();
        registerMouseEvents();

        return this;
    }

    //create a method called registerMouseEvents. register the mouse events for
    //when the mouse is MouseEvent#MOUSE_MOVED or MouseEvent#MOUSE_DRAGGED.
    //call CanvasMap#addEventHandler twice and pass to it
    //MouseEvent#MOUSE_DRAGGED, animator#mouseDragged and
    //MouseEvent#MOUSE_MOVED, animator#mouseMoved.
    //a method can be passed directly as an argument if the method signature matches the functional interface. 
    //in this example you will pass the animator method using object::method syntax.
    public void registerMouseEvents() {
        addEventHandler(MouseEvent.MOUSE_DRAGGED, animator::mouseDragged);
        addEventHandler(MouseEvent.MOUSE_MOVED, animator::mouseMoved);
    }

    //create a method called removeMouseEvents. remove the mouse events for
    //when the mouse is MouseEvent#MOUSE_MOVED or MouseEvent#MOUSE_DRAGGED.<br>
    //call CanvasMap#addEventHandler twice and pass to it
    //MouseEvent#MOUSE_DRAGGED, animator#mouseDragged and
    //MouseEvent#MOUSE_MOVED, animator#mouseMoved.
    //a method can be passed directly as an argument if the method signature matches the functional interface.
    //in this example you will pass the animator method using object::method syntax
    public void removeMouseEvents() {
        removeEventHandler(MouseEvent.MOUSE_DRAGGED, animator::mouseDragged);
        removeEventHandler(MouseEvent.MOUSE_MOVED, animator::mouseMoved);
    }
    
    //register the given EventType and EventHandler
    //@param event - an event such as MouseEvent#MOUSE_MOVED.
    //@param handler - a lambda to be used when registered event is triggered.
    public <E extends Event> void addEventHandler(EventType< E> event, EventHandler< E> handler) {
        board.addEventHandler(event, handler);
    }

    //remove the given EventType registered with EventHandler
    //@param event - an event such as MouseEvent#MOUSE_MOVED.
    //@param handler - a lambda to be used when registered event is triggered.
    public < E extends Event> void removeEventHandler(EventType< E> event, EventHandler< E> handler) {
        board.removeEventHandler(event, handler);
    }

    //create a method called start. start the animator. AnimationTimer#start()
    public void start() {
        animator.start();
    }

    //create a method called stop. stop the animator. AnimationTimer#stop()
    public void stop() {
        animator.stop();
    }

    //a method called getCanvas. get the JavaFX Canvas node, return Canvas node
    public Canvas getCanvas() {
        return board;
    }

    //a method called gc. get the GraphicsContext of Canvas that allows direct drawing.
    //return GraphicsContext of Canvas
    public GraphicsContext gc() {
        return board.getGraphicsContext2D();
    }

    //a method called h. gets the height of the map, Canvas#getHeight() return the height of canvas
    public double h() {
        return board.getHeight();
    }

    //create a method called w. gets the width of the map, Canvas#getWidth() return width of canvas
    public double w() {
        return board.getWidth();
    }
    
    //added for Lab3
//***TODO
    public List<PolyShape> shapes(){
        //constructor initialize shapes with ArrayList of initial size 20
        //shapes returns shapes list
    }
    
    public void addSampleShapes(){
//***TODO
    //create a bunch of sample shapes as you like. at least 3. make sure to customize your fill, width and stroke.
    
   }
    
}
