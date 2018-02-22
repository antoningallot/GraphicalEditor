package sample;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;


public class Controller {

    @FXML private Button deleteButton;
    @FXML private Button cloneButton;
    @FXML private RadioButton ellipseButton;
    @FXML private RadioButton rectangleButton;
    @FXML private RadioButton lineButton;
    @FXML private RadioButton selectButton;
    @FXML private ColorPicker colorPicker;
    @FXML private Canvas canvas;
    private List<Rectangle> rect;
    private EventHandler canvasListener;
    private EventHandler deleteButtonListener;
    private EventHandler cloneButtonListener;
    private EventHandler radioButtonListener;

    @FXML
    public void initialize() {

        rect = new ArrayList<Rectangle>();

        canvasListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!selectButton.isSelected()) {
                    double x = event.getX(), y = event.getY();
                    canvas.getGraphicsContext2D().setFill(colorPicker.getValue());
                    canvas.getGraphicsContext2D().setStroke(colorPicker.getValue());
                    if (ellipseButton.isSelected()) {
                        canvas.getGraphicsContext2D().fillOval(x, y, 75, 100);
                    } else if (rectangleButton.isSelected()) {
                        canvas.getGraphicsContext2D().fillRect(x, y, 75, 100);
                        rect.add(new Rectangle(x, y, 75, 100));
                    } else if (lineButton.isSelected()) {
                        canvas.getGraphicsContext2D().strokeLine(x, y, x + 50, y);
                    }
                }
                else {
                    double x = event.getX(), y = event.getY();
                    Rectangle r = select(x, y);
                    if(r!=null) {
                        if (deleteButton.isDefaultButton()) {
                            canvas.getGraphicsContext2D().clearRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                        }
                        else if (cloneButton.isDefaultButton()) {
                            canvas.getGraphicsContext2D().setFill(colorPicker.getValue());
                            canvas.getGraphicsContext2D().setStroke(colorPicker.getValue());
                            canvas.getGraphicsContext2D().fillRect(x + 1, y + 1, r.getWidth(), r.getHeight());
                        }
                        else if(event.getEventType() == MouseEvent.MOUSE_DRAGGED){
                            canvas.getGraphicsContext2D().clearRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                            r.setX(event.getX());
                            r.setY(event.getY());
                            canvas.getGraphicsContext2D().fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
                        }
                    }
                }
            }
        };

        radioButtonListener = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!selectButton.isSelected()){
                    deleteButton.setDisable(true);
                    cloneButton.setDisable(true);
                }
                else {
                    deleteButton.setDisable(false);
                    cloneButton.setDisable(false);
                }
            }
        };

        deleteButtonListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!deleteButton.isDefaultButton()) {
                    deleteButton.setDefaultButton(true);
                    cloneButton.setDefaultButton(false);
                }
                else {deleteButton.setDefaultButton(false);}
            }
        };

        cloneButtonListener = new EventHandler() {
            @Override
            public void handle(Event event) {
                if(!cloneButton.isDefaultButton()) {
                    deleteButton.setDefaultButton(false);
                    cloneButton.setDefaultButton(true);
                }
                else{cloneButton.setDefaultButton(false);}
            }
        };

        canvas.setOnMouseClicked(canvasListener);
        selectButton.setOnMouseClicked(radioButtonListener);
        ellipseButton.setOnMouseClicked(radioButtonListener);
        rectangleButton.setOnMouseClicked(radioButtonListener);
        lineButton.setOnMouseClicked(radioButtonListener);
        cloneButton.setOnMouseClicked(cloneButtonListener);
        deleteButton.setOnMouseClicked(deleteButtonListener);


    }

    private Rectangle select(double x, double y){
        for (int i = 0; i < rect.size(); i++) {
            if(x > rect.get(i).getX() && x < rect.get(i).getX()+ rect.get(i).getWidth() &&
                    y > rect.get(i).getY() && y < rect.get(i).getY()+ rect.get(i).getHeight()){
                return rect.get(i);
            }
        }
        return null;
    }


}


