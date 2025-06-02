import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.stage.Stage; 
import javafx.scene.Group; 
import javafx.scene.control.Button; 
import javafx.scene.control.Label; 
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.Pane; 
import javafx.application.Platform; 
import java.util.Random; 
import javafx.event.ActionEvent; 
 
public class DessertGame extends Application {  
    private int score = 0; 
    @Override 
    public void start(final Stage stage) { 
        Label label = new Label("Score: " + score); 
        Button button = new Button("Exit"); 
        button.addEventHandler(ActionEvent.ACTION, (event) -> { 
                Platform.exit(); 
        }); 
 
        Button button1 = new Button("Dessert"); 
        Button button2 = new Button("Desert"); 
        Button button3 = new Button("Desert"); 
        Button button4 = new Button("Desert"); 
        Button button5 = new Button("Desert"); 
        Button button6 = new Button("Desert"); 
        Button button7 = new Button("Desert"); 
        Button button8 = new Button("Desert"); 
        Button[] buttonsList = new Button[]{button1, button2, button3, button4, button5, button6, button7, button8}; 
        Random rand = new Random();

        button1.addEventHandler(ActionEvent.ACTION, (event) -> {
                button.requestFocus();
                score++;
                label.setText("Score: " + score);
                scrambleButtons(rand, buttonsList);
        });

        button2.addEventHandler(ActionEvent.ACTION, (event) -> {
                button.requestFocus();
                score--;
                label.setText("Score: " + score);
                scrambleButtons(rand, buttonsList);
        });

        button3.addEventHandler(ActionEvent.ACTION, (event) -> {
                button.requestFocus();
                score--;
                label.setText("Score: " + score);
                scrambleButtons(rand, buttonsList);
        });

        button4.addEventHandler(ActionEvent.ACTION, (event) -> {
                button.requestFocus();
                score--;
                label.setText("Score: " + score);
                scrambleButtons(rand, buttonsList);
        });

        button5.addEventHandler(ActionEvent.ACTION, (event) -> {
                button.requestFocus();
                score--;
                label.setText("Score: " + score);
                scrambleButtons(rand, buttonsList);
        });

 
        button6.addEventHandler(ActionEvent.ACTION, (event) -> {
          button.requestFocus();
          score--;
          label.setText("Score: " + score);
          scrambleButtons(rand, buttonsList);
  });

  button7.addEventHandler(ActionEvent.ACTION, (event) -> {
          button.requestFocus();
          score--;
          label.setText("Score: " + score);
          scrambleButtons(rand, buttonsList);
  });

  button8.addEventHandler(ActionEvent.ACTION, (event) -> {
          button.requestFocus();
          score--;
          label.setText("Score: " + score);
          scrambleButtons(rand, buttonsList);
  });


        BorderPane root = new BorderPane();
        Pane pane = new Pane();
        pane.getChildren().add(button1);
        pane.getChildren().add(button2);
        pane.getChildren().add(button3);
        pane.getChildren().add(button4);
        pane.getChildren().add(button5);
        pane.getChildren().add(button6);
        pane.getChildren().add(button7);
        pane.getChildren().add(button8);

        root.setCenter(pane);
        root.setTop(label);
        root.setBottom(button);
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("DessertGame");
        stage.setScene(scene);
        button.requestFocus();
        scrambleButtons(rand, buttonsList);
        stage.show();
    }

    private void scrambleButtons(Random random, Button[] buttons) {
        for(int i = 0; i < buttons.length; i++){
                buttons[i].setLayoutX(random.nextInt(601));
                buttons[i].setLayoutY(random.nextInt(401));
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
