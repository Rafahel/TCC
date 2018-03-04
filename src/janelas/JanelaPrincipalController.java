package janelas;


import classes.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class JanelaPrincipalController implements Initializable {

    @FXML private Button botaoCadastroEquipamento;

    private ArrayList<Equipamento> equipamentos;

    @FXML private Button botaoCalcula;

    @FXML private TextField textFieldTarifa;

    @FXML private Button DEBUG_EQUIPAMENTOS;

    @FXML private ListView<String> listView;

    @FXML private ListView<String> listView2;

    @FXML private Button botaoArquivo;

    @FXML private Button buttonSalvar;

    private File file;

    private ArrayList<Equipamento> listaEquipamentosSelecionados;

    private ArrayList<Integer> portas;

    @FXML private Button otimizaButton;

    @FXML private TextField objetivoField;

    @FXML private TextArea resultadoTextArea;

    @FXML private Label maximaLabel;

    @FXML private Label mediaLabel;

    @FXML private Label minimaLabel;

    private double max, med, min;

    @FXML private Button buttonAtualizaLabels;

    private Boolean cancela;

    @FXML private Button botaoEditarEquipamento;

    @FXML private Button janelaStatusButton;

    boolean janelaStatusAberta;

    @FXML private Button botaoMostraEquipamentos;

    @FXML private Button botaoLimpar;

    @FXML
    private void botaoArquivoClicked() {
        listaEquipamentosSelecionados = new ArrayList<>();
        String caminho = "C:\\Users\\Rafahel\\Desktop";
        try {
            FileReader fr = new FileReader("Settings.txt");
            BufferedReader br = new BufferedReader(fr);
            caminho = br.readLine();
            br.close();
        } catch (NullPointerException | IOException n) {
            // não faz nada
        }

        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Abrir Arquivo");
            chooser.setInitialDirectory(new File(caminho));
            this.file = chooser.showOpenDialog(new Stage());
            //System.out.println(file.toString());
            this.equipamentos = Loader.load(file);
        } catch (Exception e) {
//            //System.out.println(e);
        } finally {
            this.addToList();
            this.refreshListClicked();
            this.clearList2();
        }
    }

    @FXML
    private void buttonSalvarClicked() {
        try {
            if (!this.file.equals(null)) {
                //System.out.println("Salvando");
                Escritor escritor = new Escritor(this.equipamentos, this.file);
                escritor.geraArquivo();
            }
        } catch (Exception e) {
            if (this.equipamentos.size() > 0) {
                try {
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Abrir Arquivo");
                    chooser.setInitialDirectory(new File("C:\\Users\\"));
                    this.file = chooser.showOpenDialog(new Stage());
                    //System.out.println(file.toString());
                    Escritor escritor = new Escritor(this.equipamentos, this.file);
                    escritor.geraArquivo();
                    this.addToList();
                } catch (Exception equipamentoNaoSelecionado) {
                    //System.out.println("Arquivo não selecionado.");
                }
            }
        }
    }

    @FXML
    private void debugEquipamentos() {
        try {
            File file = new File("C:\\Users\\Rafahel\\IdeaProjects\\Projeto\\Trabalho\\log.txt");
            //System.out.println(file.toString());
            this.equipamentos = Loader.load(file);
        } catch (Exception e) {
//            //System.out.println(e);
        } finally {
            this.addToList();
            this.refreshListClicked();
            this.clearList2();
        }
    }

    @FXML
    private void botaoCadastroEquipamento() { // Chama a janela de cadastro e passa argumentos para ela.

        try {
            //System.out.println("Chamando janela");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaCadastroEquipamento.fxml"));
            Parent root = (Parent) loader.load();
            JaneleCadastroEquipamentoController newWindowController = loader.getController();
            newWindowController.inicializaJanela(equipamentos);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro de Equipamentos");
            stage.show();


        } catch (IOException e) {
            //System.out.println(e);
//            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.equipamentos = new ArrayList<>();
        this.file = null;
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listView2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listaEquipamentosSelecionados = new ArrayList<>();
//        this.addListaEquipamentosDEBUG();
        this.addToList();
        this.cancela = false;
        this.otimizaButton.setDisable(true);
        this.janelaStatusAberta = false;

    }

    private void addToList() {
        for (Equipamento e : this.equipamentos) {
            //System.out.println(e.getNome() + " adicionado a lista 1");

            this.listView.getItems().add(e.getNome());
        }
    }

    @FXML
    private void clickLista() {
        if (this.listView.getItems().size() > 0) {
            ObservableList<String> lista;
            lista = this.listView.getSelectionModel().getSelectedItems();
//            //System.out.println(this.listView.getSelectionModel().getSelectedItems());
//            //System.out.println(equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()));
            Equipamento e = new Equipamento(equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()).getNome(),
                    equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()).getWatts(),
                    equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()).getMinUtilzacaoDiaria(),
                    equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()).getMaxUtilzacaoDiaria());
            listaEquipamentosSelecionados.add(new Equipamento(e.getNome(), e.getWatts(), e.getMinUtilzacaoDiaria(), e.getMaxUtilzacaoDiaria()));
            this.listView2.getItems().add(lista.get(0));
            chamaCalculadora();
        }
    }

    @FXML
    private void clickLista2() {
        if (this.listView2.getSelectionModel().getSelectedIndex() > -1){
            listaEquipamentosSelecionados.remove(listaEquipamentosSelecionados.get(listView2.getSelectionModel().getSelectedIndex()));
            this.listView2.getItems().remove(this.listView2.getSelectionModel().getSelectedIndex());
        }
        chamaCalculadora();
    }

    private void clearList2() {
        try {
            this.listView2.getItems().remove(0, this.listView2.getItems().size());
        } catch (Exception e) {
            //System.out.println(e);
        }
    }

    @FXML
    private void refreshListClicked() {
        this.listView.getItems().remove(0, this.listView.getItems().size());
        this.addToList();
    }

    @FXML
    private void configurarEquipamentos() {
        if (this.listaEquipamentosSelecionados.size() > 0) {
            this.portas = new ArrayList<>();
            for (Equipamento e : this.listaEquipamentosSelecionados) {
                //System.out.println("Na lista : " + e.getNome());

            }
            try {
                //System.out.println("Chamando janela");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaConfigEquipamento.fxml"));
                Parent root = (Parent) loader.load();
                JanelaConfigEquipamentos newWindowController = loader.getController();
                newWindowController.inicializaJanela(this.listaEquipamentosSelecionados, this.portas);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Cadastro de Equipamentos");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void otimizaButtonClicked() {
        if(objetivoField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING, "O campo objetivo esta vazio!", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }
        if (listaEquipamentosSelecionados.size() > 0) {
            Calculadora calculadora = new Calculadora(listaEquipamentosSelecionados, Double.parseDouble(textFieldTarifa.getText()));
            calculadora.calculaGastosTotais();
            double minimo = Double.parseDouble(this.formatDbl(min));
            double maximo = Double.parseDouble(this.formatDbl(max));
            double objetivo = Double.parseDouble(this.objetivoField.getText());
            if (objetivo < maximo) {
                if (objetivo > minimo) {
                    OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(listaEquipamentosSelecionados, objetivo, resultadoTextArea, cancela);
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            otimizacaoGenetica.otimiza();
                        }
                    };
                    thread.start();

                } else {
                    resultadoTextArea.setText("O objetivo precisa ser maior ou igual ao MINIMO");
                }
            } else {
                resultadoTextArea.setText("O objetivo precisa ser menor ou igual ao MAXIMO");
            }
        }
    }

    @FXML
    private void buttonJanelaStatusButtonClicked(){
        if(!janelaStatusAberta){
            try {
                this.janelaStatusAberta = true;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaStatus.fxml"));
                Parent root = (Parent) loader.load();
                JanelaStatusController newWindowController = loader.getController();
                newWindowController.inicializaJanela(this.listaEquipamentosSelecionados, this.portas, Double.parseDouble(this.textFieldTarifa.getText()));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Monitor de equipamentos");
                stage.setOnHidden(e -> {newWindowController.shutdown(); this.janelaStatusAberta = false;});
                stage.show();
            } catch (IOException e) {
                //System.out.println(e);
            }
        }else {
            //System.out.println("A janela já esta aberta.");
        }

    }

    private void chamaCalculadora(){

        double valores[] = new double[3];
        double tarifa = Double.parseDouble(textFieldTarifa.getText());
        Calculadora calculadora = new Calculadora(listaEquipamentosSelecionados, tarifa);
        calculadora.calculaGastosTotais();
        max = calculadora.getResults()[0];
        med = calculadora.getResults()[1];
        min = calculadora.getResults()[2];
        atualizaLabels();
    }

    private void atualizaLabels(){

        if (listaEquipamentosSelecionados.size() == 0){
            minimaLabel.setText("0.00");
            maximaLabel.setText("0.00");
            mediaLabel.setText("0.00");
        }
        else if(listaEquipamentosSelecionados.size() > 4){
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            maximaLabel.setText(df.format(max));
            mediaLabel.setText(df.format(med));
            minimaLabel.setText(df.format(min));
            this.otimizaButton.setDisable(false);
        }
    }

    @FXML
    private void printEquips(){
        for (Equipamento e: listaEquipamentosSelecionados) {
            System.out.println(e);
        }
    }

    @FXML
    private void botaoLimparClicked(){
        listView2.getItems().remove(0, listView2.getItems().size());
        listaEquipamentosSelecionados = new ArrayList<>();
        chamaCalculadora();
    }

    public String formatDbl(double dbl){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(dbl);

    }

}
