package janelas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JanelaSimuladorController implements Initializable {

    @FXML private TextArea textAreaResultadoOtimizado;
    @FXML private TextArea textAreaResultadoNaoOtimizado;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void inicializaJanela(double[] resultadoA, double[] resultadoB){

        for (int i = 0; i < resultadoA.length ; i++) {
            textAreaResultadoOtimizado.appendText("" + resultadoB[i] + "\n");
            textAreaResultadoNaoOtimizado.appendText("" + resultadoA[i] + "\n");
        }
    }
}
