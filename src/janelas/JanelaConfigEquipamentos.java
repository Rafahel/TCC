package janelas;

import classes.Equipamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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

    @FXML private AnchorPane insidePane;

    private int pos;
    private TextField t[];
    private Label label[];
    private ArrayList<Integer> portas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void inicializaJanela(ArrayList<Equipamento> equipamentos, ArrayList<Integer> portas){
        this.equipamentos = equipamentos;
        this.t = new TextField[equipamentos.size()];
        this.label = new Label[equipamentos.size()];
        this.portas = portas;
        this.pos = 0;
        this.textFieldNome.setText(equipamentos.get(this.pos).getNome());
        this.textFieldKwh.setText(Integer.toString(equipamentos.get(this.pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(equipamentos.get(this.pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(equipamentos.get(this.pos).getMinUtilzacaoDiaria()));
        if (equipamentos.size() > 22)
            this.insidePane.setPrefHeight(this.insidePane.getPrefHeight() + ((this.equipamentos.size() - 22) * 30));


        populateList();

    }


    private void populateList(){
        this.nomesVbox.setSpacing(15);
        this.textFieldVbox.setSpacing(5);
        for (int i = 0; i < this.equipamentos.size() ; i++) {
            this.t[i] = new TextField();
            this.label[i] = new Label(this.equipamentos.get(i).getNome() + ":");
            this.label[i].setTextFill(Color.web("#f6ff00"));
            this.label[i].setAlignment(Pos.BASELINE_LEFT);
            this.label[i].prefHeight(5);
            this.t[i].prefHeight(5);
            this.t[i].setText("" + (i + 1));
            this.nomesVbox.getChildren().add(this.label[i]);
            this.textFieldVbox.getChildren().add(this.t[i]);
        }
    }

    @FXML
    private void botaoOkClicked(){
        try {
            for (int i = 0; i < equipamentos.size() ; i++) {
                System.out.println("adicionando porta " + t[i].getText());
                this.portas.add(Integer.parseInt(t[i].getText()));
            }
        }catch (Exception e){

        }

    }

    @FXML
    private void botaoProximoClicked(){
        if (this.pos + 1 < this.equipamentos.size()){
            this.pos++;
        }else {
            this.pos = 0;
        }
        System.out.println(equipamentos.get(pos));
        this.textFieldNome.setText(this.equipamentos.get(this.pos).getNome());
        this.textFieldKwh.setText(Integer.toString(this.equipamentos.get(this.pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(this.equipamentos.get(this.pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(this.equipamentos.get(this.pos).getMinUtilzacaoDiaria()));
    }

    @FXML
    private void botaoAnteriorClicked(){
        if (this.pos - 1 >= 0){
            this.pos--;
        }else {
            this.pos = this.equipamentos.size() - 1;
        }
        System.out.println(equipamentos.get(pos));
        this.textFieldNome.setText(this.equipamentos.get(this.pos).getNome());
        this.textFieldKwh.setText(Integer.toString(this.equipamentos.get(this.pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(this.equipamentos.get(this.pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(this.equipamentos.get(this.pos).getMinUtilzacaoDiaria()));
    }

    @FXML
    private void botaoSalvarClicked(){
       try {
           this.equipamentos.get(this.pos).setNome(this.textFieldNome.getText());
           this.equipamentos.get(this.pos).setWatts(Integer.parseInt(this.textFieldKwh.getText()));
           if(this.sempreLigadoCheckBox.isSelected()){
               this.equipamentos.get(this.pos).setMaxUtilzacaoDiaria(1440);
               this.equipamentos.get(this.pos).setMinUtilzacaoDiaria(1440);
           }else {
               this.equipamentos.get(this.pos).setMaxUtilzacaoDiaria(Integer.parseInt(this.textFieldMaxHoras.getText()));
               this.equipamentos.get(this.pos).setMinUtilzacaoDiaria(Integer.parseInt(this.textFieldMinHoras.getText()));
           }
       }catch (NumberFormatException e){

       }
    }


}
