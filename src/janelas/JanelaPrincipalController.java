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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;


public class JanelaPrincipalController implements Initializable{

    @FXML
    private Button botaoCadastroEquipamento;

    private ArrayList<Equipamento> equipamentos;

    @FXML
    private Button botaoCalcula;

    @FXML
    private TextField textFieldTarifa;

    @FXML
    private Button DEBUG_EQUIPAMENTOS;

    @FXML
    private ListView<String> listView;

    @FXML
    private ListView<String> listView2;

    @FXML
    private Button botaoArquivo;

    @FXML
    private Button buttonSalvar;

    private File file;

    @FXML
    private MenuBar menuBar;

    private ArrayList<Equipamento> listaEquipamentosSelecionados;

    private ArrayList<Integer> portas;

    @FXML
    private Button botaoArduino;

    @FXML
    private Button otimizaButton;

    @FXML
    private TextField objetivoField;

    @FXML
    private void botaoArquivoClicked(){
        listaEquipamentosSelecionados = new ArrayList<>();
        String caminho = "C:\\Users\\Rafahel\\Desktop" ;
        try {
            FileReader fr = new FileReader("Settings.txt");
            BufferedReader br = new BufferedReader(fr);
            caminho  = br.readLine();
            br.close();
        }catch (NullPointerException | IOException n){
            // não faz nada
        }

        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Abrir Arquivo");
            chooser.setInitialDirectory(new File(caminho));
            this.file = chooser.showOpenDialog(new Stage());
            System.out.println(file.toString());
            this.equipamentos = Loader.load(file);
        }catch (Exception e){
//            System.out.println(e);
        }
        finally {
            this.addToList();
            this.refreshListClicked();
            this.clearList2();
        }
    }

    @FXML
    private void buttonSalvarClicked(){
        try {
            if(!this.file.equals(null)){
                System.out.println("Salvando");
                Escritor escritor = new Escritor(this.equipamentos, this.file);
                escritor.geraArquivo();
            }
        }catch (Exception e){
            if (this.equipamentos.size() > 0){
                try {
                    FileChooser chooser = new FileChooser();
                    chooser.setTitle("Abrir Arquivo");
                    chooser.setInitialDirectory(new File("C:\\Users\\"));
                    this.file = chooser.showOpenDialog(new Stage());
                    System.out.println(file.toString());
                    Escritor escritor = new Escritor(this.equipamentos, this.file);
                    escritor.geraArquivo();
                    this.addToList();
                }catch (Exception equipamentoNaoSelecionado){
                    System.out.println("Arquivo não selecionado.");
                }
            }
        }
    }

    @FXML
    private void debugEquipamentos(){

        System.out.println("Quantidade de equipamentos: " + this.equipamentos.size());
        for (Equipamento e: this.equipamentos) {
            System.out.println(e);
        }
    }

    @FXML
    private void botaoCadastroEquipamento(){ // Chama a janela de cadastro e passa argumentos para ela.

        try {
            System.out.println("Chamando janela");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("janelas/JanelaCadastroEquipamento.fxml"));
            Parent root = (Parent) loader.load();
            JaneleCadastroEquipamentoController newWindowController = loader.getController();
            newWindowController.inicializaJanela(equipamentos);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro de Equipamentos");
            stage.show();


        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void botaoCalculaClicked(){

        try{
            double valores[] = new double[3];
            double tarifa = Double.parseDouble(this.textFieldTarifa.getText());
            Simulator simulator = new Simulator(this.listaEquipamentosSelecionados, tarifa);
            new Thread(){
                public void run(){
                    simulator.calculaGastosTotais();
                }
            }.start();
            System.out.println("Res: " +  simulator.getTotalMax());
        }catch (NumberFormatException e){
            System.out.println("Entrada de tarifa inválida.");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.equipamentos = new ArrayList<>();
        this.file = null;
        this.listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.listView2.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        this.addListaEquipamentosDEBUG();
        this.addToList();

    }

    private void addToList(){
        for (Equipamento e: this.equipamentos) {
            System.out.println(e.getNome() + " adicionado a lista 1" );

            this.listView.getItems().add(e.getNome());
        }
    }

    private void addListaEquipamentosDEBUG(){
        int watts = 1000;
        for (int i = 0; i < 30; i++) {
            equipamentos.add(new Equipamento("Equipamento " + i, 1000, 7, 5));
            watts += 500;
        }
    }

    @FXML
    private void clickLista(){
        if(this.listView.getItems().size() > 0){
            ObservableList<String> lista;
            lista = this.listView.getSelectionModel().getSelectedItems();
//            System.out.println(this.listView.getSelectionModel().getSelectedItems());
//            System.out.println(equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()));
            listaEquipamentosSelecionados.add(equipamentos.get(this.listView.getSelectionModel().getSelectedIndex()));
            this.listView2.getItems().add(lista.get(0));
        }
    }

    @FXML
    private void clickLista2(){
        if(this.listView.getItems().size() > 0 && this.listView2.getSelectionModel().getSelectedIndex() >= 0) {
//            System.out.println("Removendo no index: " + this.listView2.getSelectionModel().getSelectedItem());
            try {

                for (int i = 0; i < listaEquipamentosSelecionados.size() ; i++) {
                    Equipamento e = listaEquipamentosSelecionados.get(i);
                    if (e.getNome().equals(listView2.getSelectionModel().getSelectedItem())){
//                        System.out.println("Item selecionado: " + listView2.getSelectionModel().getSelectedItem());
//                        System.out.println("Item Removido: " + this.listaEquipamentosSelecionados.get(i).getNome());
                        this.listaEquipamentosSelecionados.remove(e);
                    }
                }
                this.listView2.getItems().remove(this.listView2.getSelectionModel().getSelectedIndex());
            }catch (ArrayIndexOutOfBoundsException e){
//            System.out.println(e);
            }
        }
    }

    private void clearList2(){
        try {
            this.listView2.getItems().remove(0, this.listView2.getItems().size());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void refreshListClicked(){
        this.listView.getItems().remove(0, this.listView.getItems().size());
        this.addToList();
    }

    @FXML
    private void configurarEquipamentos(){
        this.portas = new ArrayList<>();
        for (Equipamento e: this.listaEquipamentosSelecionados) {
            System.out.println("Na lista : " + e.getNome());

        }
        try {
            System.out.println("Chamando janela");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("janelas/JanelaConfigEquipamento.fxml"));
            Parent root = (Parent) loader.load();
            JanelaConfigEquipamentos newWindowController = loader.getController();
            newWindowController.inicializaJanela(this.listaEquipamentosSelecionados, this.portas);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Cadastro de Equipamentos");
            stage.show();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void botaoArduinoClicked(){
        System.out.println(portas);
        Arduino arduino = new Arduino(this.listaEquipamentosSelecionados, this.portas);

        if (!arduino.isConectado()){
            Thread thread = new Thread() {
                @Override
                public void run() {
                    arduino.conecta();
                    while (arduino.isConectado()){
                        arduino.equipamentoIsConnected();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };thread.start();
        }

    }

    @FXML
    private void otimizaButtonClicked(){
        double objetivo = Double.parseDouble(this.objetivoField.getText());
//        System.out.println("Numero de equipamentos = " +  this.listaEquipamentosSelecionados.size());
//        OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(listaEquipamentosSelecionados);
        OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(listaEquipamentosSelecionados, objetivo);
        Thread thread = new Thread() {
            @Override
            public void run() {
                otimizacaoGenetica.otimiza();
            }
        };thread.start();

    }

}
