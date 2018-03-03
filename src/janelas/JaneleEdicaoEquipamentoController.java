package janelas;

import classes.Equipamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JaneleEdicaoEquipamentoController implements Initializable {

    @FXML
    private TextField textFieldNome;

    @FXML
    private TextField textFieldKwh;

    @FXML
    private TextField textFieldMaxHoras;

    @FXML
    private TextField textFieldMinHoras;

    @FXML
    private Button botaoCadastrar;

    @FXML
    private CheckBox sempreLigadoCheckBox;

    private ArrayList<Equipamento> equipamentos;


    private int pos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void inicializaJanela(ArrayList<Equipamento> equipamentos, int pos) {
        this.equipamentos = equipamentos;
        this.pos = pos;
        this.textFieldNome.setText( this.equipamentos.get(pos).getNome());
        this.textFieldKwh.setText( "" + this.equipamentos.get(pos).getWatts());
        this.textFieldMaxHoras.setText( "" + this.equipamentos.get(pos).getMaxUtilzacaoDiaria());
        this.textFieldMinHoras.setText( "" + this.equipamentos.get(pos).getMinUtilzacaoDiaria());
    }

    @FXML
    private void editar() {
        try {
            if (this.sempreLigadoCheckBox.isSelected()){
                this.equipamentos.get(pos).setNome(this.textFieldNome.getText());
                this.equipamentos.get(pos).setWatts(Integer.parseInt(this.textFieldKwh.getText()));
                this.equipamentos.get(pos).setMaxUtilzacaoDiaria(1440);
                this.equipamentos.get(pos).setMinUtilzacaoDiaria(1440);
            }
            else{
                this.equipamentos.get(pos).setNome(this.textFieldNome.getText());
                this.equipamentos.get(pos).setWatts(Integer.parseInt(this.textFieldKwh.getText()));
                this.equipamentos.get(pos).setMaxUtilzacaoDiaria(Integer.parseInt(textFieldMaxHoras.getText()));
                this.equipamentos.get(pos).setMinUtilzacaoDiaria(Integer.parseInt(textFieldMinHoras.getText()));
            }
            System.out.println("Edicao completa!");
            Stage stage = (Stage) botaoCadastrar.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
    }


}
