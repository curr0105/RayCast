package raycast;

import java.util.List;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import raycast.animator.AbstractAnimator;
import raycast.animator.TextAnimator;

//this is the start of JavaFX application. this class must extend Application.
//@author Shahriar (Shawn) Emami
//@version Jan 13, 2019
public class RayCast extends Application {

    //size of the scene
    private double width = 650, height = 650;

    //title of application
    private String title = "RayCast";

    //background color of application
    private Color background = Color.LIGHTPINK;

    private BorderPane root;

    //this object represents the canvas (drawing area)
    private CanvasMap board;

    //this is a ChoiceBox that holds all different animators available
    private ChoiceBox< AbstractAnimator> animatorsBox;

    /**
     * a list of all animators available. ObservableList is a wrapper of a
     * normal List data structure. The difference being, ObservableList is
     * capable of notifying any observer that the list has been changed if
     * elements have been added, removed or the list is changed in any manner.
     * The best way to initialize any ObservableList is to use the utility class
     * FXCollections.
     */
    /**
     * ObservableList< Integer> nums = FXCollections.observableArrayList();
     *
     * nums.addListener( ( Change< ? extends Integer> c) -> { while( c.next()){
     * List< ? extends Integer> added = c.getAddedSubList();
     * System.out.println(added);
     *
     * List< ? extends Integer> removed = c.getRemoved();
     * System.out.println(removed); } } nums.addAll( 1, 2, 3, 4, 5, 6, 7, 8, 9);
     * nums.removeAll( 1, 3, 5, 7, 9);
     *
     * every time values are added or removed they will be printed. however, in
     * this application we are not using this feature of list.
     */
    private ObservableList< AbstractAnimator> animators;

    //this method is called at the very beginning of the JavaFX application and can be used to initialize all 
    //components in the application. however, Scene and Stage must not be created in this method.
    //this method does not run JavaFX thread.
    @Override
    public void init() throws Exception {

        //Initialize the animators with FXCollections.observableArrayList and pass to it a new TextAnimator
//***TODO
//add the new StaticShapes to end of animators list.  don't remove the old TextAnimator.
        animators = FXCollections.observableArrayList(new TextAnimator());

        //initialize the board object
        board = new CanvasMap();

        //create two ToolBar objects and store createStatusBar() and createOptionsBar() in each
        ToolBar optionsBar = createOptionsBar();
        ToolBar statusBar = createStatusBar();

        //initialize root
        root = new BorderPane();

        //call setTop on it and pass to it the options bar
        root.setTop(optionsBar);

        //call setCenter on it and pass to it board.getCanvas()
        root.setCenter(board.getCanvas());

        //call setBottom on it and pass to it the status bar
        root.setBottom(statusBar);

        //we need to bind the height and width of of canvas to root so if screen is resized board is resized as well.
        //to bind the width get the canvas from board first then call widthProperty on it and then bind
        //root.widthProperty to it
        board.getCanvas()
                .widthProperty()
                .bind(root.widthProperty());

        //to bind the height it is almost the same process however the height of options and status bar
        //need to be subtracted from  root height. subtract can be done you also need to subtract 
        //optionsBar.heightProperty as well.
        board.getCanvas()
                .heightProperty()
                .bind(root.heightProperty()
                        .subtract(statusBar.heightProperty())
                        .subtract(optionsBar.heightProperty()));

        //loop through all animators and setCanvas as board
        for (AbstractAnimator a : animators) {
            a.setCanvas(board);
        }
        
        //at the end of the function call addSampleShapes on board to add the sampel shapes
        //board.addSampleShapes();

    }

    //this method is called when JavaFX application is started and it is running on JavaFX thread. 
    //this method must at least create Scene and finish customizing Stage. these two objects must be on 
    //JavaFX thread when created.
    //
    //Stage represents the frame of your application, such as minimize, maximize and close buttons.
    //Scene represents the holder of all your JavaFX Nodes. Node is the super class of every javaFX class.
    //
    //primaryStage - primary stage of your application that will be rendered
    @Override
    public void start(Stage primaryStage) throws Exception {
        //scene holds all JavaFX components that need to be displayed in Stage
        Scene scene = new Scene(root, width, height, background);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);

        //when escape key is pressed close the application
        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                primaryStage.hide();
            }
        });

        //display the JavaFX application
        primaryStage.show();

        //select first index of animatorsBox as start,
        //this will also sets the new animator as the lambda we setup will be triggered
//***TODO for Lab 3 - chage select(0) to select(1), this will start the application with StaticShapes instead
        //of TextAnimator.
        animatorsBox.getSelectionModel().select(0);
    }

    //this method is called at the very end when the application is about to exit. this method is used
    //to stop or release any resources used during the application.
    @Override
    public void stop() throws Exception {
        board.stop();
    }

    //create a ToolBar that represent the menu bar at the top of the application. return customized ToolBar
    public ToolBar createOptionsBar() {
        //use the createButton method and create a start button with lambda that calls board.start()
        Button start = createButton("start", e -> board.start());

        //use the createButton method and create a start button with lambda that calls board.stop()
        Button stop = createButton("stop", e -> board.stop());

        //create 2 Pane objects called filler1 and filler2
        Pane filler1 = new Pane();
        Pane filler2 = new Pane();

        //Pane class is a super class of all layout managers. by itself it has no rules.
        //call the static method setHgrow from Hbox with filler1, Priority.ALWAYS
        HBox.setHgrow(filler1, Priority.ALWAYS);

        //call the static method setHgrow from Hbox with Filler2, Priority.ALWAYS
        //this will allow the filler to expand and fill the space between nodes
        HBox.setHgrow(filler2, Priority.ALWAYS);

        //create a Spinner object called rayCount with generic type of Integer
        //in the constructor pass to it 1 as min, Integer.MAX_VALUE as max and 360*3 as current
        Spinner<Integer> rayCount = new Spinner<Integer>(1, Integer.MAX_VALUE, 360 * 3);

        //call setEditable on it and set to true so the counter can be changed by typing in it.
        rayCount.setEditable(true);

        //call setMaxWidth on it and set 100, as default size if too big
        rayCount.setMaxWidth(100);

        //get rayCount property from CanvasMap and bind it to rayCount.valueProperty().
        //rayCount.valueProperty() should be passed as argument.
        board.rayCountProperty().bind(rayCount.valueProperty());

        //call createCheckBox 5 times and use following names: FPS, Intersects. Lights, Joints, Bounds, Sectors
        //only FPS is selected the rest are false.
        CheckMenuItem f = createCheckMenuItem("FPS", true, board.drawFPSProperty());
        CheckMenuItem i = createCheckMenuItem("Intersects", false, board.drawDrawIntersectPoint());
        CheckMenuItem l = createCheckMenuItem("Lights", false, board.drawLightSource());
        CheckMenuItem j = createCheckMenuItem("Joints", false, board.drawDrawShapeJoints());
        CheckMenuItem b = createCheckMenuItem("Bounds", false, board.drawDrawBounds());
        CheckMenuItem s = createCheckMenuItem("Sectors", false, board.drawDrawSectors());

        //create a MenuButton with argument "Options", null and all of created CheckMenuItem.
        MenuButton mb = new MenuButton("Options", null, f, i, l, j, b, s);

        //as last argument get the equivalent property from CanvasMap
        //Initialize animatorsBox with the animators list
        animatorsBox = new ChoiceBox(animators);

        //call getSelectionModel on animatorsBox then call selectedItemProperty and then call addListener.
        //finally as argument for addListener pass a lambda that sets the new animator for CanvasMap.
        //this Lambda is called ChangeListener and it takes 3 arguments:
        //ObservableValue<? extends T> observable, it is an object that represent the observable data 
        //T oldValue, it is the old value before the change
        //T newValue, it is the new value after the change
        //this lambda will only use the newValue argument which passed to setAnimator of CanvasMap
        //because of the lambda syntax the "ObservableValue<? extends T>" or "T" is not required and is implied
        animatorsBox.getSelectionModel().selectedItemProperty().addListener((observable,
                oldValue, newValue) -> board.setAnimator(newValue));

        //create a new ToolBar and as arguments of its constructor pass the create nodes. there should be 8:
        //startButton, stopButton, filler1, rayCount, options, filler2, new Label( "Animators"), animatorsBox
        ToolBar optionsBar = new ToolBar(start, stop, filler1, rayCount, mb, filler2,
                new Label("Animators"), animatorsBox);

        //return the created ToolBar
        return optionsBar;
    }

    //create a ToolBar that will represent the status bar of the application. return customized ToolBar
    public ToolBar createStatusBar() {
        //create two Label objects each with value of "(0,0)".
        //one label keeps track of mouse movement and other mouse drag.
        Label moveCoordLabel = new Label("(0,0)");
        Label dragCoordLabel = new Label("(0,0)");

        //call addEventHandler on canvas for MouseEvent.MOUSE_MOVED event and pass a lambda to it that will
        //update the text in one of the labels with the new coordinate of the mouse. the lambda will take one 
        //argument of type MouseEvent and two methods getX and getY from MouseEvent can be used to get 
        //position of the mouse
        board.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            moveCoordLabel.setText("(" + e.getX() + "," + e.getY() + ")");
        });

        //call addEventHandler on canvas for MouseEvent.MOUSE_DRAGGED event and pass a lambda to
        //it that will update the text in one of the labels with the new coordinate of the mouse.
        //the lambda will take one argument of type MouseEvent and two methods getX and getY from MouseEvent 
        //can be used to get position of the mouse
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            dragCoordLabel.setText("(" + e.getX() + "," + e.getY() + ")");
        });

        //create a new ToolBar and as arguments of its constructor pass the create labels to it.
        //there should be 4 labels: 
        //new Label( "Mouse: "), moveCoordLabel, new Label( "Drag: "), dragCoordLabel
        ToolBar statusBar = new ToolBar(new Label("Mouse: "), moveCoordLabel, new Label("Drag: "),
                dragCoordLabel);

        //return the created ToolBar
        return statusBar;
    }

    //create a CheckMenuItem with given text, initial state and BooleanProperty binding. 
    //BooleanProperty is a special class that can be binded to another BooleanProperty. this means every time
    //one changes the other BooleanProperty changes as well. for example:
    /**
     * <pre>
     * BooleanProperty b1 = new SimpleBooleanProperty( false);
     * BooleanProperty b2 = new SimpleBooleanProperty();
     * b1.bind( b2);
     * b2.set( true);
     * System.out.println( b1.get()); // prints true
     * IntegerProperty i1 = new SimpleIntegerProperty( 1);
     * IntegerProperty i2 = new SimpleIntegerProperty();
     * i1.bind( i2);
     * i2.set( 100);
     * System.out.println( i1.get()); // prints 100
     * </pre>
     *
     * if p2 changes p1 changes as well. this relationship is NOT bidirectional.
     */
    
    //text - String to be displayed
    //isSelected - initial state, true id selected
    //binding - BooleanProperty that will be notified if state of this
    //CheckMenuItem is changed
    //return customized CheckMenuItem
    public CheckMenuItem createCheckMenuItem(String text, boolean isSelected, BooleanProperty binding) {
        
        //create a new CheckMenuItem object with given text.
        CheckMenuItem check = new CheckMenuItem(text);

        //call bind on binding and pass to it check.selectedProperty(), this will notify the binding object every time
        //check.selectedProperty() is changed.
        binding.bind(check.selectedProperty());

        //call setSelected and pass to it isSelected
        check.setSelected(isSelected);

        //return the created CheckMenuItem.
        return check;
    }

    //create a Button with given text and lambda for when it is clicked 
    //text - String to be displayed
    //action - lambda for when the button is clicked, the lambda will take one argument of type ActionEvent
    //return customized Button
    //this function is used with a lambda when calling createButton (ie: Button start = createButton("start", e -> board.start());)
    public Button createButton(String text, EventHandler< ActionEvent> action) {
        //create a new Button object with given text
        Button button = new Button(text);

        //call setOnAction on created button and give it action
        button.setOnAction(action);

        //return the created button
        return button;
    }

    public static void main(String[] args) {
        //launch( args); method starts the javaFX application.
        //some IDEs are capable of starting JavaFX without this method.
        launch(args);
    }
}
