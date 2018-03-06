package janelas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("JanelaPrincipal.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 1280, 720));
//        primaryStage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaPrincipal.fxml"));
        Parent root = (Parent) loader.load();
        JanelaPrincipalController newWindowController = loader.getController();
        primaryStage.setTitle("Janela Principal");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        System.out.println(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\HouseMon").mkdir());
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
