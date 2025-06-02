import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class FirstJavaFX extends Application {

    public void start(Stage stage) {
        Label label = new Label("The key to making programs fast\nis to make them do practically nothing.\n-- Mike Haertel");
        Circle circle = new Circle(160, 120, 30);
        Polygon polygon = new Polygon(160, 120, 200, 220, 120, 220);
        Group group = new Group(label,circle, polygon);
        Scene scene = new Scene(group,480,320);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("CS400");
    }

    public static void main(String[] args) {
        Application.launch(args);
        System.out.println("Goodbye.");
    }

}
~                                                                                                                                               
~              