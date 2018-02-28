package janelas;

import classes.Equipamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JanelaConfigEquipamentos implements Initializable {

    private ArrayList<Equipamento> equipamentos;
    @FXML
    private VBox nomesVbox;
    @FXML
    private VBox textFieldVbox;
    private TextField t[];
    private Label label[];
    private ArrayList<Integer> portas;
    @FXML
    private Button oKbutton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void inicializaJanela(ArrayList<Equipamento> equipamentos, ArrayList<Integer> portas){
        this.equipamentos = equipamentos;
        t = new TextField[equipamentos.size()];
        label = new Label[equipamentos.size()];
        this.portas = portas;
        populateList();
    }


    private void populateList(){
        nomesVbox.setSpacing(15);
        textFieldVbox.setSpacing(5);
        for (int i = 0; i < equipamentos.size() ; i++) {
            t[i] = new TextField();
            label[i] = new Label(equipamentos.get(i).getNome() + ":");
            label[i].setTextFill(Color.web("#f6ff00"));
            label[i].setAlignment(Pos.BASELINE_LEFT);
            label[i].prefHeight(5);
            t[i].prefHeight(5);
            nomesVbox.getChildren().add(label[i]);
            textFieldVbox.getChildren().add(t[i]);
        }
    }

    @FXML
    private void botaoOkClicked(){
        for (int i = 0; i < equipamentos.size() ; i++) {
            System.out.println("adicionando porta " + t[i].getText());
            portas.add(Integer.parseInt(t[i].getText()));
        }
        Stage stage = (Stage) this.oKbutton.getScene().getWindow();
        stage.close();
    }


}
