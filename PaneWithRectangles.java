package num7;
// Assignment #: Arizona State University CSE205
//         Name: Jace Tjan
//    StudentID: 1216883785
//      Lecture: MWF 11:50am
//  Description: PaneWithRectangles class creates a pane where we can use
//               mouse key to drag on grids and there will be some color following
//               the mouse. We can also use combo boxes to change its colors and stroke widths

//import any classes necessary here
//----
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class PaneWithRectangles extends BorderPane
{
   private ComboBox<String> primaryColorCombo;
   private ComboBox<String> backColorCombo;
   private ComboBox<String> widthCombo;
    
   private Color primaryColor, secondaryColor, backgroundColor;
   private double selectWidth;

   private Rectangle[][] rectArray;


   public PaneWithRectangles()
   {
       primaryColor = Color.BLACK;
       secondaryColor = Color.GRAY;
       backgroundColor = Color.WHITE;
       selectWidth = 1.0;
      
       //Instantiate and initialize labels, combo boxes
       primaryColorCombo = new ComboBox<String>();
       backColorCombo = new ComboBox<String>();
       widthCombo = new ComboBox<String>();
       
       primaryColorCombo.getItems().addAll("Black", "Blue", "Red", "Green");
       backColorCombo.getItems().addAll("White", "Yellow", "Orange");
       widthCombo.getItems().addAll("1", "3", "5", "7");
       
       primaryColorCombo.setValue("Black");
       backColorCombo.setValue("White");
       widthCombo.setValue("1");
       
       Label pColor = new Label("PrimaryColor");
       Label bColor = new Label("BackgroundColor");
       Label sWidth = new Label("StrokeWidth");
       
       //Instantiate and initialize the two dimensional array rectArray
       //to contain 7 columns and 7 rows (49 rectangles total)
       //It is recommended to use nested loops
      
       rectArray = new Rectangle[7][7];
       for(int row = 0; row < 7; row++) {
    	   for(int col = 0; col < 7; col++) {
    		   rectArray[row][col] = new Rectangle(0, 0, 470/7, 390/7);
    		   rectArray[row][col].setFill(backgroundColor);
    		   rectArray[row][col].setStroke(Color.BLACK);
    	   }
       }
       
       //grid is a GridPane containing 49 rectangles.
       GridPane grid = new GridPane();
       //---- add 49 rectangles to the grid pane, it is recommended to use nested loops
      
       for(int row = 0; row < 7; row++) {
    	   for(int col = 0; col < 7; col++) {
    		   grid.add(rectArray[row][col], col, row);
    	   }
       }

       //leftPane is a VBox, it should contain labels and combo boxes
       VBox leftPane = new VBox();
       leftPane.setSpacing(20);
       leftPane.setPadding(new Insets(10, 0, 10, 0));
       leftPane.getChildren().addAll(pColor, primaryColorCombo, bColor, backColorCombo, sWidth, widthCombo);

       //add the left pane to the left of the pane
       //and the grid pane contains rectangles at the center
       this.setLeft(leftPane);
       this.setCenter(grid);

      //register/link the source nodes with its handler objects
      grid.setOnMouseDragged(new MouseHandler());
      primaryColorCombo.setOnAction(new PrimColorHandler());
      backColorCombo.setOnAction(new BackColorHandler());
      widthCombo.setOnAction(new WidthHandler());
   }

   //Step #2(A) - MouseHandler
   private class MouseHandler implements EventHandler<MouseEvent>
   {
      public void handle(MouseEvent event)
       {
            //handle MouseEvent here
            //Note: you can use if(event.getEventType()== MouseEvent.MOUSE_DRAGGED)
            //to check whether the mouse key is dragged
            //write your own codes here
            if(event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            	for(int row = 0; row < 7; row++) {
            		for(int col = 0; col < 7; col++) {
            			rectArray[row][col].setStroke(Color.BLACK);
            			
            			rectArray[row][col].setFill(backgroundColor);
            		}
            	}
            	//which rectangle is being pointed at
            	int xVal = (int)event.getX()/(470/7);
            	int yVal = (int)event.getY()/(390/7);
            	
            	//fill the pointed at rectangle and adjacent ones
            	//inside ones
            	if(xVal >= 1 && xVal <= 5 && yVal >=1 && yVal <=5) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		//right
            		rectArray[yVal][xVal+1].setFill(secondaryColor);
            		//left
            		rectArray[yVal][xVal-1].setFill(secondaryColor);
            		//top
            		rectArray[yVal-1][xVal].setFill(secondaryColor);
            		//bottom
            		rectArray[yVal+1][xVal].setFill(secondaryColor);
            	}
            	//the outside factor ones
            	//top/left
            	if(xVal == 0 && yVal == 0) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal][xVal+1].setFill(secondaryColor);
            		rectArray[yVal+1][xVal].setFill(secondaryColor);
            	}
            	//bot/right
            	if(xVal == 6 && yVal == 6) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal-1][xVal].setFill(secondaryColor);
            		rectArray[yVal][xVal-1].setFill(secondaryColor);
            	}
            	//bot/left
            	if(xVal == 0 && yVal == 6) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal-1][xVal].setFill(secondaryColor);
            		rectArray[yVal][xVal+1].setFill(secondaryColor);
            	}
            	//top/right
            	if(xVal == 6 && yVal == 0) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal][xVal-1].setFill(secondaryColor);
            		rectArray[yVal+1][xVal].setFill(secondaryColor);
            	}
            	//top
            	if(xVal >= 1 && xVal <= 5 && yVal == 0) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal][xVal+1].setFill(secondaryColor);
            		rectArray[yVal][xVal-1].setFill(secondaryColor);
            		rectArray[yVal+1][xVal].setFill(secondaryColor);
            	}
            	//bot
            	if(xVal >= 1 && xVal <= 5 && yVal == 6) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal-1][xVal].setFill(secondaryColor);
            		rectArray[yVal][xVal+1].setFill(secondaryColor);
            		rectArray[yVal][xVal-1].setFill(secondaryColor);
            	}
            	//right
            	if(yVal >= 1 && yVal <= 5 && xVal == 6) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal-1][xVal].setFill(secondaryColor);
            		rectArray[yVal][xVal-1].setFill(secondaryColor);
            		rectArray[yVal+1][xVal].setFill(secondaryColor);
            	}
            	//left
            	if(yVal >= 1 && yVal <= 5 && xVal == 0) {
            		rectArray[yVal][xVal].setFill(primaryColor);
            		rectArray[yVal-1][xVal].setFill(secondaryColor);
            		rectArray[yVal][xVal+1].setFill(secondaryColor);
            		rectArray[yVal+1][xVal].setFill(secondaryColor);
            	}
            }          
      }//end handle()
   }//end MouseHandler

   //A handler class used to handle primary and secondary colors
   private class PrimColorHandler implements EventHandler<ActionEvent>
   {
      public void handle(ActionEvent event)
      {
          //write your own codes here
          if(primaryColorCombo.getValue().equals("Black")) {
        	  primaryColor = Color.BLACK;
        	  secondaryColor = Color.GRAY;
          }
          else if(primaryColorCombo.getValue().equals("Blue")) {
        	  primaryColor = Color.BLUE; 
        	  secondaryColor = Color.POWDERBLUE;
          }
          else if(primaryColorCombo.getValue().equals("Red")) {
        	  primaryColor = Color.RED;
        	  secondaryColor = Color.PINK;
          }
          else if(primaryColorCombo.getValue().equals("Green")) {
        	  primaryColor = Color.GREEN;
        	  secondaryColor = Color.LIGHTGREEN;
          }
      }
   }//end PrimColorHandler
    
   //A handler class used to handle background color
    private class BackColorHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            //write your own codes here white yellow orange
            if(backColorCombo.getValue().equals("White"))
            	backgroundColor = Color.WHITE;
            else if(backColorCombo.getValue().equals("Yellow"))
            	backgroundColor = Color.YELLOW;
            else if(backColorCombo.getValue().equals("Orange"))
            	backgroundColor = Color.ORANGE;
            for(int row = 0; row < 7; row++) {
            	for(int col = 0; col < 7; col++) {
            		rectArray[row][col].setFill(backgroundColor);
            	}
            }
        }
    }//end BackColorHandler
    
    //A handler class used to handle stroke width
    private class WidthHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event) 
        {
            //write your own codes here
            if(widthCombo.getValue().equals("1"))
            	selectWidth = 1;
            else if(widthCombo.getValue().equals("3"))
            	selectWidth = 3;
            else if(widthCombo.getValue().equals("5"))
            	selectWidth = 5;
            else if(widthCombo.getValue().equals("7"))
            	selectWidth = 7;
            for(int row = 0; row < 7; row++) {
            	for(int col = 0; col < 7; col++) {
            		rectArray[row][col].setWidth((470/7)-(selectWidth-1));
            		rectArray[row][col].setHeight((390/7)-(selectWidth-1));
            		rectArray[row][col].setStrokeWidth(selectWidth);
            	}
            }
        }
    }//end WidthHandler
    //signature: Jace Tjan
} //end of PaneWithRectangles