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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void inicializaJanela(ArrayList<Equipamento> equipamentos){
        this.equipamentos = equipamentos;
        this.pos = 0;
        this.textFieldNome.setText(equipamentos.get(this.pos).getNome());
        this.textFieldKwh.setText(Integer.toString(equipamentos.get(this.pos).getWatts()));
        this.textFieldMaxHoras.setText(Integer.toString(equipamentos.get(this.pos).getMaxUtilzacaoDiaria()));
        this.textFieldMinHoras.setText(Integer.toString(equipamentos.get(this.pos).getMinUtilzacaoDiaria()));
    }


    @FXML
    private void botaoProximoClicked(){
        if (this.pos + 1 < this.equipamentos.size()){
            this.pos++;
        }else {
            this.pos = 0;
        }
        System.out.println(equipamentos.get(pos).getNome() + " " + equipamentos.get(pos).getWatts() + " " + equipamentos.get(pos).getMaxUtilzacaoDiaria() + " " + equipamentos.get(pos).getMinUtilzacaoDiaria());
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
