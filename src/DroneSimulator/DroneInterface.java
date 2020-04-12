/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DroneSimulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author RORY JACKSON
 */
public class DroneInterface extends Application implements Serializable {
     
    private AnimationTimer timer ; // timer for animation
    private VBox rtPane ; //vertical box for displaying info
    private DroneArena arena ; 
    private Stage stage ; //stage for file handling
    private GraphicsContext gc ; //graphics context for drawing
    private static final long serialVersionUID = 1L ; 
    /**
     * shows the information about the program
     */
    private void showAbout(){
        Alert alert = new Alert(AlertType.INFORMATION) ; 
        alert.setTitle("About") ; 
        alert.setHeaderText(null);
        alert.setContentText("RJ's Drone Demo") ; 
        alert.showAndWait();
    }
    
    /**
     * function for inputting a value to alter x size of the arena
     */
    private void showXInput(){
        TextInputDialog dialog = new TextInputDialog("X Size") ; 
        dialog.setTitle("Input X Size") ; 
        dialog.setHeaderText(null) ;
        dialog.setContentText("Enter a value for X Size: ") ; 
        
        Optional<String> result = dialog.showAndWait() ; 
        if (result.isPresent()){
            System.out.println("X Value: " + result.get()) ; 
            String xBuffer = result.get() ; 
            int xBufferParsed = Integer.parseInt(xBuffer) ; 
            arena.setXSize(xBufferParsed) ; 
        }
    }
    
    /**
     * Function for inputting a value to alter y size of the arena
     */
    private void showYInput(){
        TextInputDialog dialog = new TextInputDialog("Y Size") ;
        dialog.setTitle("Input Y Size") ; 
        dialog.setHeaderText(null) ; 
        dialog.setContentText("Enter a value for Y Size") ; 
        
        Optional<String> result = dialog.showAndWait() ; 
        if (result.isPresent()){
            System.out.println("Y Value: " + result.get());
            String yBuffer = result.get();
            int yBufferParsed = Integer.parseInt(yBuffer) ; 
            arena.setYSize(yBufferParsed) ; 
        }
    }
    
    /**
     * Input the max value to alter the max number of drones
     */
    private void showMaxInput(){
        TextInputDialog dialog = new TextInputDialog("Max Drones") ; 
        dialog.setTitle("Input a max number of drones") ; 
        dialog.setHeaderText(null) ;
        dialog.setContentText("Please enter a value: ") ; 
        
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            System.out.println("Max Drones: " + result.get()) ; 
            String maxDronesBuffer = result.get();
            int maxDronesBufferParsed = Integer.parseInt(maxDronesBuffer);
            arena.setMaxDrones(maxDronesBufferParsed);
        }
    }
    /**
     * Function to convert char c to colour
     * @param c character used for colour
     * @return color
     */
    Color colFromChar(char c){
        Color ans = Color.BLACK ; 
        switch(c){
            case'y':
                ans = Color.YELLOW ; 
                break ; 
            case 'w':
                ans = Color.WHITE ;
                break ;
            case 'r':
                ans = Color.RED ;
                break ;
            case 'g':
                ans = Color.GREEN ;
                break ;
            case 'b':
                ans = Color.BLUE;
                break;
            case 'o':
                ans = Color.ORANGE;
                break ; 
        }
        return ans ; 
    }
    
    /**
     * Graphics context for drawing
     * @return gc
     */
    public GraphicsContext getGC(){
        return gc ; 
    }
    /**
     * This function allows a user to save the arena to a file.
     * By using the FileChooser, the user is able to specify the location
     * that they would like to save their file.
     * If the location and file type is correct, then it will save the arena
     */
    public void save(){
        FileChooser fileChooser = new FileChooser() ; 
        File file = fileChooser.showSaveDialog(stage) ; //show save file
        
        FileOutputStream fileOS = null ; 
        ObjectOutputStream objectOS = null ; 
        try {
            fileOS = new FileOutputStream(file) ; 
            objectOS = new ObjectOutputStream(fileOS) ; 
            objectOS.writeObject(fileOS) ; 
            objectOS.flush() ; 
            objectOS.close() ;
            fileOS.flush();
            fileOS.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException err){
            err.printStackTrace();
        }
        
        if (file != null){
            System.out.println("File Written: " + file) ; 
        }       
    }
    
    /**
     * this function allows a user to load a certain file.
     * By using the fileChooser, it allows the user to specify what file.
     * If the file name or type is invalid, then an error message will be displayed
     */
    public void load(){
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage); 
        try{
            FileInputStream inFile = new FileInputStream(file);
            ObjectInputStream inObj = new ObjectInputStream(inFile);
            this.arena = (DroneArena) inObj.readObject();
            inFile.close();
            inObj.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException err){
            err.printStackTrace();
        } catch (ClassNotFoundException ex){
            ex.printStackTrace();
            System.out.println("File Type invalid") ; 
        }
        
        if (file != null){
            System.out.println("File chosen: " + file) ;
        }
        drawWorld();
    }
    
    /**
     * set up the mouse events - when mouse is pressed, move drone here
     * @param canvas Canvas 
     */
    void setMouseEvents (Canvas canvas){
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent e){
                        drawWorld();
                        drawStatus();
                    }
                });
    }
    
    /**
     * create menu of commands 
     * @return MainMenu
     */
    private MenuBar setMenu(){
        MenuBar menuBar = new MenuBar(); //create main menu
        Menu mFile = new Menu("File") ; //create file menu
        
        MenuItem mExit = new MenuItem("Exit") ; 
        mExit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                timer.stop() ;
                System.exit(0);
            }
        });
        
        MenuItem mSave = new MenuItem("Save") ;
        mSave.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                save();
            }
        });
        
        MenuItem mLoad = new MenuItem("Load") ; 
        mLoad.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                load();
            }
        });
        
        mFile.getItems().addAll(mSave,mLoad,mExit) ; 
        
        Menu mHelp = new Menu("Help") ; 
        MenuItem mAbout = new MenuItem("About") ; 
        mAbout.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent){
                showAbout();
            }
        });
        mHelp.getItems().addAll(mAbout);
        
        Menu mConfig = new Menu("Configure") ; //create configure menu
        MenuItem mSetX = new MenuItem("Set X Size") ; //set size
        mSetX.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0){
                showXInput() ; 
            }
        });
        
        MenuItem mSetY = new MenuItem("Set Y Size of the arena") ; 
        mSetY.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg1){
                showYInput() ;
            }
        });
        
        MenuItem mSetMax = new MenuItem("Set Max Drones") ;
        mSetMax.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg1){
                showMaxInput();
            }
        });
        mConfig.getItems().addAll(mSetX, mSetY, mSetMax) ;
        
        menuBar.getMenus().addAll(mFile, mHelp, mConfig);
        return menuBar;
    }
    
    /**
     * set up the horizontal box for the bottom with relevant buttons
     * @return hBox
     */
    private HBox setButtons(){
        Button startBtn = new Button("Start");
        startBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                timer.start();
            }
        });
        
        Button stopBtn = new Button("Stop");
        stopBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle (ActionEvent event){
                timer.stop();
            }
        });
        
        Button normalDrone = new Button("Add Normal Drone");
        normalDrone.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                arena.addNormalDrone();
                drawWorld();
                drawStatus();
            }
        });
        
        Button clearBtn = new Button("Clear") ; 
        clearBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                arena = new DroneArena(400,500);
                drawWorld();
                drawStatus();
            }
        });
        
        Button lightDrone = new Button ("Add Light Drone" ); 
        lightDrone.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                arena.addLightDrone();
                drawWorld();
                drawStatus();
            }
        });
        
        Button lightSensorDrone = new Button("Add Light Sensor Drone" );
        lightSensorDrone.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                arena.addLightSensorDrone();
                drawWorld();
                drawStatus();
            }
        });
        return new HBox(new Label("Run: "), startBtn, stopBtn, clearBtn,
            new Label("Add: "), normalDrone, lightDrone, lightSensorDrone);
    }
    
    
    
    /**
     * Draw the world with drone in it
     */
    public void drawWorld(){
        gc.setFill(Color.WHITE) ; 
        gc.fillRect(0,0, arena.getXSize(), arena.getYSize());
        arena.drawArena(this) ; 
    }
    
    /**
     * show where the drone is, in pane on the right
     */
    public void drawStatus(){
        rtPane.getChildren().clear();
        ArrayList<String>allDs = arena.describeAll();
        for (String s : allDs){
            Label l = new Label(s);
            rtPane.getChildren().add(l); 
        }
    }
    
    /**
     * 
     * @param primaryStage primaryStage
     * @throws Exception E
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("RJ's Drone Simulator") ; 
        BorderPane bp = new BorderPane() ; 
        bp.setPadding(new Insets(10,20,10,20));
        
        bp.setTop(setMenu()); //put the menu at the top
        
        Group root = new Group() ; //create group with canvas
        Canvas canvas = new Canvas(400, 500);
        root.getChildren().add(canvas);
        bp.setLeft(root) ; //load canvas to the left
        
        gc= canvas.getGraphicsContext2D() ; //context for drawing
        
        setMouseEvents(canvas);
        
        arena = new DroneArena(400, 500) ; 
        drawWorld() ; 
        
        timer = new AnimationTimer(){ //set up the timer
            public void handle (long currentNanoTime) { //and its action when on
                arena.adjustDrones() ; //move drones
                drawWorld(); // draw arena
                drawStatus() ; // indicate where the drones are
            }
        } ; 
        
        rtPane = new VBox() ; //set up VBox on right to list drones
        rtPane.setAlignment(Pos.TOP_LEFT) ; //set alignment
        rtPane.setPadding(new Insets(5, 50, 50, 5)) ; //padding
        bp.setRight(rtPane) ; //add rtPane to border pane right
        
        bp.setBottom(setButtons()) ; //set bottom pane with buttons
        
        Scene scene = new Scene(bp, 700, 600) ; //set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty()) ;
        
        primaryStage.setScene(scene);
        primaryStage.show() ;
    }
    
    
    public static void main(String[] args){
        Application.launch(args);
    }
}

