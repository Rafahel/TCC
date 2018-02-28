package janelas;

import classes.Equipamento;
import classes.Escritor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.rmi.server.InactiveGroupException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class JaneleCadastroEquipamentoController implements Initializable {

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void inicializaJanela(ArrayList<Equipamento> equipamentos){
        this.equipamentos = equipamentos;
    }

    @FXML
    private void cadastrar(){
        try{
            if (this.sempreLigadoCheckBox.isSelected())
                this.equipamentos.add(new Equipamento(this.textFieldNome.getText(), Integer.parseInt(this.textFieldKwh.getText()), 1440, 1440));
            else
                this.equipamentos.add(new Equipamento(this.textFieldNome.getText(), Integer.parseInt(this.textFieldKwh.getText()), Integer.parseInt(this.textFieldMaxHoras.getText()), Integer.parseInt(this.textFieldMinHoras.getText())));
            System.out.println("Completo");
//            Escritor escritor = new Escritor(this.equipamentos, this.fi);
//            escritor.geraArquivo();
            Stage stage = (Stage) botaoCadastrar.getScene().getWindow();
            stage.close();
        }catch (NumberFormatException e){
            System.out.println(e);
        }
    }




}
