package tetris;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tetris.controller.GameLogic;
import tetris.controller.GuiController;

public class Main extends Application {

    private final static String TITLE = "Tetris";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/gui.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        GuiController guiController = loader.getController();
        GameLogic gameLogic = new GameLogic(guiController.getGraphicsContext());
        gameLogic.runTimer();

        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
