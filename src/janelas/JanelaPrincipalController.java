package janelas;


import classes.*;
import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class JanelaPrincipalController implements Initializable {

    // Componentes FXML do JavaFx
    @FXML
    private TextField textFieldTarifa;

    @FXML
    private Button DEBUG_EQUIPAMENTOS;

    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<String> listView2;

    @FXML
    private Button otimizaButton;

    @FXML
    private TextField objetivoField;

    @FXML
    private TextArea resultadoTextArea;

    @FXML
    private Label maximaLabel;

    @FXML
    private Label mediaLabel;

    @FXML
    private Label minimaLabel;

    @FXML
    private Button janelaStatusButton;

    @FXML
    private ComboBox<String> portasComboBox;

    @FXML
    private Button botaoConecta;

    @FXML
    private Label kwhLabel;

    @FXML
    private Button simuladorButton;

    @FXML
    private Button botaoUtilizarOtimizacao;

    @FXML
    private Button botaoSalvarSolucao;

    @FXML
    private Button botaoVisualizarSolucoes;

    @FXML
    private Button botaoResultadosSimulador;


    // Componentes não FXML
    private ArrayList<Equipamento> equipamentos;

    private File file;

    private ArrayList<Equipamento> listaEquipamentosSelecionados;

    private ArrayList<Integer> portas;

    private double max, med, min;

    private Arduino arduino;

    private String localPasta;

    private ArrayList<String> resultadoOtimizacoes;

    private int indexOtimizacoes;

    private String genes;

    private double[] simulacaoresultadoA;

    private double[] simulacaoresultadoB;


    /*
            Inicializador da janela, nele as tudo que é necessário sera inicializado, incluindo as portas seriais.

     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.equipamentos = new ArrayList<>();
        this.file = null;
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listView2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listaEquipamentosSelecionados = new ArrayList<>();
//        this.addListaEquipamentosDEBUG();
        this.addToList();
        this.otimizaButton.setDisable(true);
        SerialPort[] portas = SerialPort.getCommPorts();
        for (SerialPort porta : portas) {
            this.portasComboBox.getItems().add(porta.getSystemPortName());
        }
        this.localPasta = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\HouseMon";
        this.resultadoOtimizacoes = new ArrayList<>();
        this.indexOtimizacoes = 0;
        janelaStatusButton.setDisable(true);
        this.portas = new ArrayList<>();
        this.simuladorButton.setDisable(true);
        this.botaoUtilizarOtimizacao.setDisable(true);
        this.botaoSalvarSolucao.setDisable(true);
        this.botaoVisualizarSolucoes.setDisable(true);
        this.botaoResultadosSimulador.setDisable(true);


    }


    @FXML
    private void botaoConectaClicked() {
        if (equipamentos.size() > 0) {
            String porta = this.portasComboBox.getSelectionModel().getSelectedItem();
            this.botaoConecta.setDisable(true);
            this.portasComboBox.setDisable(true);
            conectaArduino(porta);
        }
    }

    @FXML
    private void botaoArquivoClicked() {
        listaEquipamentosSelecionados = new ArrayList<>();
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Abrir Arquivo");
            chooser.setInitialDirectory(new File(this.localPasta));
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
                    chooser.setInitialDirectory(new File(this.localPasta));
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
            File file = new File(this.localPasta + "\\EquipamentosCadastrados.txt");
            //System.out.println(file.toString());
            this.equipamentos = Loader.load(file);
        } catch (Exception e) {
//            //System.out.println(e);
        } finally {
            this.addToList();
            this.refreshListClicked();
            this.clearList2();
        }
        this.DEBUG_EQUIPAMENTOS.setDisable(true);
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
            e.printStackTrace();
        }

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
        if (this.listView2.getSelectionModel().getSelectedIndex() > -1) {
            listaEquipamentosSelecionados.remove(listaEquipamentosSelecionados.get(listView2.getSelectionModel().getSelectedIndex()));
            this.listView2.getItems().remove(this.listView2.getSelectionModel().getSelectedIndex());
        }
        chamaCalculadora();
    }

    private void clearList2() {
        try {
            this.listView2.getItems().remove(0, this.listView2.getItems().size());
        } catch (Exception e) {
            e.printStackTrace();
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
            try {
                //System.out.println("Chamando janela");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaConfigEquipamento.fxml"));
                Parent root = (Parent) loader.load();
                JanelaConfigEquipamentos newWindowController = loader.getController();
                newWindowController.inicializaJanela(this.listaEquipamentosSelecionados);
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
        if (objetivoField.getText().equals("")) {
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

                    Task<Void> longRunningTask = new Task<Void>() {

                        @Override
                        protected Void call() throws Exception {
                            OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(listaEquipamentosSelecionados, objetivo, Double.parseDouble(textFieldTarifa.getText()));
                            otimizacaoGenetica.otimiza();
                            if (otimizacaoGenetica.isEncontrado()) {
                                resultadoOtimizacoes.add("Solucao " + (indexOtimizacoes + 1) + "\n" + otimizacaoGenetica.getResultado());
                                indexOtimizacoes = resultadoOtimizacoes.size();
                                botaoUtilizarOtimizacao.setDisable(false);
                                botaoUtilizarOtimizacao.setDisable(false);
                                botaoSalvarSolucao.setDisable(false);
                                botaoVisualizarSolucoes.setDisable(false);
                                Platform.runLater(() -> resultadoTextArea.setText(resultadoOtimizacoes.get(resultadoOtimizacoes.size() - 1)));
                            } else {
                                Platform.runLater(() -> resultadoTextArea.setText("Resultado não encontrado.\nModifique o valor e tente novamente."));
                            }
                            return null;
                        }
                    };
                    new Thread(longRunningTask).start();
                } else {
                    resultadoTextArea.setText("O objetivo precisa ser maior ou igual ao MINIMO");
                }
            } else {
                resultadoTextArea.setText("O objetivo precisa ser menor ou igual ao MAXIMO");
            }
        }
    }

    @FXML
    private void buttonJanelaStatusButtonClicked() {
        try {
            if (portas.size() == 0) {
                for (int i = 0; i < listaEquipamentosSelecionados.size(); i++) {
                    this.portas.add(i + 1);
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaStatus.fxml"));
            Parent root = (Parent) loader.load();
            JanelaStatusController newWindowController = loader.getController();
            newWindowController.inicializaJanela(this.listaEquipamentosSelecionados, this.portas, Double.parseDouble(this.textFieldTarifa.getText()), arduino);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Monitor de equipamentos");
//                stage.setOnHidden(e -> {newWindowController.shutdown(); this.janelaStatusAberta = false;});
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chamaCalculadora() {
        double tarifa = Double.parseDouble(textFieldTarifa.getText());
        Calculadora calculadora = new Calculadora(listaEquipamentosSelecionados, tarifa);
        calculadora.calculaGastosTotais();
        max = calculadora.getResults()[0];
        med = calculadora.getResults()[1];
        min = calculadora.getResults()[2];
        this.kwhLabel.setText(Double.toString(calculadora.calculakwh()));
        atualizaLabels();
    }

    private void atualizaLabels() {
        if (listaEquipamentosSelecionados.size() == 0) {
            minimaLabel.setText("0.00");
            maximaLabel.setText("0.00");
            mediaLabel.setText("0.00");
        } else if (listaEquipamentosSelecionados.size() > 4) {
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            maximaLabel.setText(df.format(max));
            mediaLabel.setText(df.format(med));
            minimaLabel.setText(df.format(min));
            this.otimizaButton.setDisable(false);
        }
    }

    @FXML
    private void printEquips() {
        for (Equipamento e : listaEquipamentosSelecionados) {
            System.out.println(e);
        }
    }

    @FXML
    private void botaoLimparClicked() {
        listView2.getItems().remove(0, listView2.getItems().size());
        listaEquipamentosSelecionados = new ArrayList<>();
        chamaCalculadora();
    }

    public String formatDbl(double dbl) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);
        return df.format(dbl);

    }


    private void conectaArduino(String porta) {

        arduino = new Arduino(porta, this.listaEquipamentosSelecionados, Double.parseDouble(textFieldTarifa.getText()));
        arduino.conecta();
        Task<Void> longRunningTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (arduino.isConectado()) {
                    Thread.sleep(100);
                    janelaStatusButton.setDisable(false);
                    double valor = arduino.getGastoAtual();
                }
                botaoConecta.setDisable(false);
                portasComboBox.setDisable(false);
                janelaStatusButton.setDisable(false);
                return null;
            }
        };
        new Thread(longRunningTask).start();
    }

    @FXML
    private void botaoSalvarSolucaoClicked() {
        Escritor escritor = new Escritor(new File(this.localPasta));
        escritor.salvaOtimizacao("\\Solucao.txt", this.resultadoTextArea.getText());
    }

    @FXML
    private void botaoUtilizarOtimizacaoClicked() {
        this.simuladorButton.setDisable(false);
        this.botaoResultadosSimulador.setDisable(true);
        this.genes = resultadoTextArea.getText();
        Task<Void> longRunningTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                genes = genes.split("\n")[2];
                int inicio = genes.indexOf('{');
                int fim = genes.indexOf('}');
                genes = genes.substring(inicio, fim);
                genes = genes.replace('{', ' ');
                genes = genes.replace(',', ' ');
                String[] nums = genes.split(" ");
                int[] valores = new int[listaEquipamentosSelecionados.size()];
                int pos = 0;
                for (String num : nums) {
                    try {
                        if (!num.equals(" ")) {
                            if (!num.equals("")){
//                                System.out.println(Integer.parseInt(num));
                                valores[pos] = Integer.parseInt(num);
                                pos++;
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                pos = 0;
                for (Equipamento e : listaEquipamentosSelecionados) {
                    e.setOtimizado(true);
                    e.setTempoOtimizado(valores[pos]);
                    pos++;
                }
                return null;
            }
        };
        new Thread(longRunningTask).start();
    }


    @FXML
    private void simladorButtonClicked() {
        this.botaoUtilizarOtimizacaoClicked();
        Simulador simulador = new Simulador(listaEquipamentosSelecionados, Double.parseDouble(this.objetivoField.getText()),
                Double.parseDouble(this.textFieldTarifa.getText()));
        Task<Void> longRunningTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                simulador.simula();
                simulacaoresultadoA = simulador.getResultadoNotimizado();
                simulacaoresultadoB = simulador.getResultadoOtimizado();
//                simulador.setOtimizar(true);
//                simulador.simula();

                Platform.runLater(() -> botaoResultadosSimulador.setDisable(false));
                return null;
            }
        };
        new Thread(longRunningTask).start();
    }

    @FXML
    private void janelaResultadoSimulacao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaSimulador.fxml"));
            Parent root = (Parent) loader.load();
            JanelaSimuladorController newWindowController = loader.getController();
            newWindowController.inicializaJanela(Double.parseDouble(this.textFieldTarifa.getText()),
                    this.simulacaoresultadoA, this.simulacaoresultadoB, Double.parseDouble(this.objetivoField.getText()));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleção de Simulações");
            stage.show();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void visualizarSolucoesClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaSelecaoDeSolucao.fxml"));
            Parent root = (Parent) loader.load();
            JanelaSelecaoDeSolucaoController newWindowController = loader.getController();
            newWindowController.inicializaJanela(this.resultadoOtimizacoes, this.resultadoTextArea);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Seleção de Soluções");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        System.out.println("shutdown");
        this.arduino.closeThread();
//        Platform.exit();

    }

//    private void deepCopy(){
//        ArrayList<Equipamento> equipamentoList = new ArrayList<>();
//        for (Equipamento e: listaEquipamentosSelecionados) {
//            Equipamento aux = new Equipamento()
//            equipamentoList.add()
//        }
//    }

}