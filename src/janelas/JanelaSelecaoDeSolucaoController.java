package janelas;

import classes.Equipamento;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class JanelaSelecaoDeSolucaoController implements Initializable{
    @FXML private TextArea textAreaSolucao;
    private ArrayList<String> solucoes;
    private int indexSolucoes;
    @FXML private Button botaoProximo;
    @FXML private Button botaoAnterior;
    private boolean solucaoSelecionada;
    private String solucao;
    private TextArea textArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void inicializaJanela(ArrayList<String> solucoes, TextArea textArea){
        this.textArea = textArea;
        this.solucoes = solucoes;
        this.textAreaSolucao.setEditable(false);
        this.indexSolucoes = 0;
        if (solucoes.size() > 0){
            this.textAreaSolucao.setText(this.solucoes.get(this.indexSolucoes));
        }
        else {
            this.textAreaSolucao.setText("Não há soluções.");
        }
        this.solucaoSelecionada = false;
    }
    @FXML
    private void botaoProximoClicked(){
        if ((this.solucoes.size() - 1) > this.indexSolucoes){
            this.indexSolucoes ++;
            this.textAreaSolucao.setText(this.solucoes.get(this.indexSolucoes));
        }else {
            this.indexSolucoes = 0;
            this.textAreaSolucao.setText(this.solucoes.get(this.indexSolucoes));
        }
    }
    @FXML
    private void botaoAnteriorClicked(){
        if (this.indexSolucoes > 0){
            this.indexSolucoes --;
            this.textAreaSolucao.setText(this.solucoes.get(this.indexSolucoes));
        }else {
            this.indexSolucoes = this.solucoes.size() - 1;
            this.textAreaSolucao.setText(this.solucoes.get(this.indexSolucoes));
        }
    }

    @FXML
    private void selecionarSolucaoClicked(){
        this.solucao = this.solucoes.get(this.indexSolucoes);
        this.textArea.setText(this.solucoes.get(this.indexSolucoes));
        this.solucaoSelecionada = true;
    }

    public boolean isSolucaoSelecionada() {
        return solucaoSelecionada;
    }

    public String getSolucao() {
        return solucao;
    }
}
