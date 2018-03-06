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
    @FXML private VBox nomesVbox;
    @FXML private VBox textFieldVbox;

    @FXML private Button oKbutton;

    @FXML private Button botaoProximo;

    @FXML private Button botaoAnterior;

    @FXML private Button botaoSalvar;

    @FXML private TextField textFieldNome;

    @FXML private TextField textFieldKwh;

    @FXML private TextField textFieldMaxHoras;

    @FXML private TextField textFieldMinHoras;

    @FXML private CheckBox sempreLigadoCheckBox;

    private int pos;
    private TextField t[];
    private Label label[];
    private ArrayList<Integer> portas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void inicializaJanela(ArrayList<Equipamento> equipamentos, ArrayList<Integer> portas){
        this.equipamentos = equipamentos;
        t = new TextField[equipamentos.size()];
        label = new Label[equipamentos.size()];
        this.portas = portas;
        this.pos = 0;
        this.textFieldNome.setText(equipamentos.get(pos).getNome());
        this.textFieldKwh.setText(Integer.toString(equipamentos.get(pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(equipamentos.get(pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(equipamentos.get(pos).getMinUtilzacaoDiaria()));

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
            t[i].setText("" + (i + 1));
            nomesVbox.getChildren().add(label[i]);
            textFieldVbox.getChildren().add(t[i]);
        }
    }

    @FXML
    private void botaoOkClicked(){
        try {
            for (int i = 0; i < equipamentos.size() ; i++) {
                System.out.println("adicionando porta " + t[i].getText());
                portas.add(Integer.parseInt(t[i].getText()));
            }
        }catch (Exception e){

        }

    }

    @FXML
    private void botaoProximoClicked(){
        if (pos + 1 < equipamentos.size()){
            pos++;
        }else {
            pos = 0;
        }
        System.out.println(equipamentos.get(pos));
        this.textFieldNome.setText(equipamentos.get(pos).getNome());
        this.textFieldKwh.setText(Integer.toString(equipamentos.get(pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(equipamentos.get(pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(equipamentos.get(pos).getMinUtilzacaoDiaria()));
    }

    @FXML
    private void botaoAnteriorClicked(){
        if (pos - 1 >= 0){
            pos--;
        }else {
            pos = equipamentos.size() - 1;
        }
        System.out.println(equipamentos.get(pos));
        this.textFieldNome.setText(equipamentos.get(pos).getNome());
        this.textFieldKwh.setText(Integer.toString(equipamentos.get(pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(equipamentos.get(pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(equipamentos.get(pos).getMinUtilzacaoDiaria()));
    }

    @FXML
    private void botaoSalvarClicked(){
       try {
           equipamentos.get(pos).setNome(textFieldNome.getText());
           equipamentos.get(pos).setWatts(Integer.parseInt(textFieldKwh.getText()));
           if(sempreLigadoCheckBox.isSelected()){
               equipamentos.get(pos).setMaxUtilzacaoDiaria(1440);
               equipamentos.get(pos).setMinUtilzacaoDiaria(1440);
           }else {
               equipamentos.get(pos).setMaxUtilzacaoDiaria(Integer.parseInt(textFieldMaxHoras.getText()));
               equipamentos.get(pos).setMinUtilzacaoDiaria(Integer.parseInt(textFieldMinHoras.getText()));
           }
       }catch (NumberFormatException e){

       }
    }


}
