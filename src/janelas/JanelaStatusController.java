package janelas;

import classes.Arduino;
import classes.Equipamento;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class JanelaStatusController implements Initializable {

    private Label[] nomeEquipamentoLabel;

    private Label[] portaEquipamentoLabel;

    private Label[] statusEquipamentoLabel;

    private double tarifa;

    @FXML
    private Label valorGasto;

    @FXML
    private VBox nomesVbox;

    @FXML
    private VBox portasVbox;

    @FXML
    private VBox statusVbox;

    @FXML Label horarioLabel;

    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Integer> portas;

    private Service<Void> backgroundThread;
    private Arduino arduino;
    private String status;
    private DecimalFormat df;
    private Thread thread;
    @FXML private Label potenciaLabel;
    @FXML private AnchorPane insidePane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void inicializaJanela(ArrayList<Equipamento> equipamentos, ArrayList<Integer> portas, double tarifa, Arduino arduino) {
        this.equipamentos = equipamentos;
        this.portas = portas;
        this.tarifa = tarifa;
        this.nomeEquipamentoLabel = new Label[equipamentos.size()];
        this.portaEquipamentoLabel = new Label[equipamentos.size()];
        this.statusEquipamentoLabel = new Label[equipamentos.size()];
        this.df = new DecimalFormat("#.##");
        this.arduino = arduino;
        this.valorGasto.setText("0.00");
        this.populateList();
//        System.out.println(this.insidePane.getPrefHeight());
        if (equipamentos.size() > 22)
            this.insidePane.setPrefHeight(this.insidePane.getPrefHeight() + ((this.equipamentos.size() - 22) * 30));
        conectaArduino();

    }

    private void populateList() {
        nomesVbox.setSpacing(15);
        statusVbox.setSpacing(15);
        portasVbox.setSpacing(15);
        for (int i = 0; i < equipamentos.size(); i++) {
            this.nomeEquipamentoLabel[i] = new Label(equipamentos.get(i).getNome() + ":");
            nomeEquipamentoLabel[i].setTextFill(Color.web("#f6ff00"));
            nomeEquipamentoLabel[i].setAlignment(Pos.BASELINE_LEFT);
            nomeEquipamentoLabel[i].prefHeight(5);
            nomesVbox.getChildren().add(nomeEquipamentoLabel[i]);
            this.statusEquipamentoLabel[i] = new Label("DESLIGADO");
            statusEquipamentoLabel[i].setTextFill(Color.web("#ff0000"));
            statusEquipamentoLabel[i].setAlignment(Pos.BASELINE_LEFT);
            statusEquipamentoLabel[i].prefHeight(5);
            statusVbox.getChildren().add(statusEquipamentoLabel[i]);
            this.portaEquipamentoLabel[i] = new Label(Integer.toString(portas.get(i)));
            portaEquipamentoLabel[i].setTextFill(Color.web("#f6ff00"));
            portaEquipamentoLabel[i].setAlignment(Pos.BASELINE_LEFT);
            portaEquipamentoLabel[i].prefHeight(5);
            portasVbox.getChildren().add(portaEquipamentoLabel[i]);
        }
    }

    private void conectaArduino() {

        Task<Void> longRunningTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (arduino.isConectado()) {
                    Platform.runLater(() -> horarioLabel.setText(time()));
                    Thread.sleep(100);
                    double valor = arduino.getGastoAtual();
                    updateMessage(Double.toString(valor));
                    status = arduino.getStatus().substring(0, equipamentos.size());
                    Platform.runLater(() -> valorGasto.setText(df.format(valor)));
                    for (int i = 0; i < status.length(); i++) {
                        final int pos = i;
                        if (status.charAt(i) == '0'){
                            Platform.runLater(() -> statusEquipamentoLabel[pos].setText("DESLIGADO"));
                            Platform.runLater(() -> statusEquipamentoLabel[pos].setTextFill(Color.web("#ff0000")));
                        }
                        else{
                            Platform.runLater(() -> statusEquipamentoLabel[pos].setText("LIGADO"));
                            Platform.runLater(() -> statusEquipamentoLabel[pos].setTextFill(Color.web("#0fea0b")));
                        }
                        Platform.runLater(() -> potenciaLabel.setText("" + arduino.getTotalWatts()));
                    }

                }


                return null;
            }
        };

        thread = new Thread(longRunningTask);
        thread.start();


    }

    private String time(){
        Date date = new Date();
        return new SimpleDateFormat("hh:mm:ss a").format(date);
    }

    private int getSeconds(String time){
        time = time.replace(':', ' ');
        return Integer.parseInt(time.split(" ")[2]);
    }

//    public void shutdown() {
//        arduino.setCloseThread(true);
//
//    }

}
