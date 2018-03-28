package janelas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class JanelaSimuladorController implements Initializable {

    @FXML private TextArea textAreaResultadoOtimizado;

    @FXML private TextArea textAreaResultadoNaoOtimizado;

    @FXML private LineChart<String, Double> lineChart;

    @FXML private Button mudaModoChartButton;

    @FXML private Label yLabel;
            
    private boolean gastoKw;

    private double[] resultadoA;

    private double[] resultadoB;

    private XYChart.Series<String, Double> seriesNotimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesNotimizadoGasto = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoGasto = new XYChart.Series<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void inicializaJanela(double[] resultadoA, double[] resultadoB){
        this.resultadoA = resultadoA;
        this.resultadoB = resultadoB;
        this.lineChart.getData().clear();
        this.lineChart.setCreateSymbols(false);
        
        seriesNotimizadoKw.setName("Não Otimizado");
        seriesOtimizadoKw.setName("Otimizado");
        seriesNotimizadoGasto.setName("Não Otimizado");
        seriesOtimizadoGasto.setName("Otimizado");
        for (int i = 0; i < resultadoA.length ; i++) {
            textAreaResultadoOtimizado.appendText("" + resultadoB[i] + "\n");
            textAreaResultadoNaoOtimizado.appendText("" + resultadoA[i] + "\n");
        }
        double a = 0;
        double b = 0;
        for (int i = 0; i < resultadoA.length ; i++) {
            a += resultadoA[i];
            seriesNotimizadoKw.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1),resultadoA[i]));
            seriesNotimizadoGasto.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1), (a * 0.69118)));
            System.out.println(a );
        }

        System.out.println("-------");
        for (int i = 0; i < resultadoB.length ; i++) {
            b += resultadoB[i];
            seriesOtimizadoKw.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1),resultadoB[i]));
            seriesOtimizadoGasto.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1), (b * 0.69118)));
            System.out.println(b);
        }


        lineChart.getData().add(seriesNotimizadoKw);
        lineChart.getData().add(seriesOtimizadoKw);
        this.gastoKw = true;

    }

    @FXML
    private void toogleGastoDiarioClicked() {
        if (this.mudaModoChartButton.getText().equals("Gasto diario kw")){
            this.yLabel.setText("R$");
            this.mudaModoChartButton.setText("Gasto diario");
            this.lineChart.getData().clear();
            this.lineChart.getData().add(this.seriesNotimizadoGasto);
            this.lineChart.getData().add(this.seriesOtimizadoGasto);
        }
        else {
            this.mudaModoChartButton.setText("Gasto diario kw");
            this.yLabel.setText("kw");
            this.lineChart.getData().clear();
            lineChart.getData().add(seriesNotimizadoKw);
            lineChart.getData().add(seriesOtimizadoKw);
        }
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
