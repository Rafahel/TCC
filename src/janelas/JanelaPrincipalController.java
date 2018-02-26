package janelas;


import classes.Equipamento;
import classes.Escritor;
import classes.Loader;
import classes.Simulator;
import javafx.collections.ObservableList;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


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

    @FXML
    private void botaoArquivoClicked(){
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Abrir Arquivo");
            chooser.setInitialDirectory(new File("C:\\Users\\"));
            this.file = chooser.showOpenDialog(new Stage());
            System.out.println(file.toString());
            this.equipamentos = Loader.load(file);
        }catch (Exception e){
            System.out.println(e);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JanelaCadastroEquipamento.fxml"));
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
            Simulator simulator = new Simulator(this.equipamentos, tarifa);
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
            System.out.println(this.listView.getSelectionModel().getSelectedItems());
            System.out.println(lista.get(0));
            this.listView2.getItems().add(lista.get(0));
        }
    }

    @FXML
    private void clickLista2(){
        if(this.listView.getItems().size() > 0 && this.listView2.getSelectionModel().getSelectedIndex() >= 0) {
            System.out.println("Removendo no index: " + this.listView2.getSelectionModel().getSelectedIndex());
            try {
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

}
