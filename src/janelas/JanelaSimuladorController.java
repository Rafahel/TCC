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
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class JanelaSimuladorController implements Initializable {

    @FXML private TextArea textAreaResultadoOtimizado;

    @FXML private TextArea textAreaResultadoNaoOtimizado;

    @FXML private LineChart<String, Double> lineChart;

    @FXML private Button mudaModoChartButton;

    @FXML private Label yLabel;

    @FXML private Label totalNaoOtimizadoLabelKw;

    @FXML private Label totalOtimizadoLabelKw;

    @FXML private Label totalNaoOtimizadoLabelRs;

    @FXML private Label totalOtimizadoLabelRs;

    private boolean gastoKw;

    private double[] resultadoA;

    private double[] resultadoB;

    private XYChart.Series<String, Double> seriesNotimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesNotimizadoGasto = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoGasto = new XYChart.Series<>();
    
    private double tarifa;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void inicializaJanela(double tarifa, double[] resultadoA, double[] resultadoB){
        this.tarifa = tarifa;
        this.resultadoA = resultadoA;
        this.resultadoB = resultadoB;
        this.lineChart.getData().clear();
        this.lineChart.setCreateSymbols(false);
        
        seriesNotimizadoKw.setName("Não Otimizado");
        seriesOtimizadoKw.setName("Otimizado");
        seriesNotimizadoGasto.setName("Não Otimizado");
        seriesOtimizadoGasto.setName("Otimizado");
        double a = 0;
        double b = 0;
        for (int i = 0; i < resultadoA.length ; i++) {
            textAreaResultadoOtimizado.appendText("" + resultadoB[i] + "\n");
            textAreaResultadoNaoOtimizado.appendText("" + resultadoA[i] + "\n");
            a += resultadoA[i];
            b += resultadoB[i];
            seriesNotimizadoKw.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1),resultadoA[i]));
            seriesNotimizadoGasto.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1), (a * this.tarifa)));
            seriesOtimizadoKw.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1),resultadoB[i]));
            seriesOtimizadoGasto.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1), (b * this.tarifa)));
        }
        totalNaoOtimizadoLabelRs.setText("R$ " + new DecimalFormat("#.##").format(a * this.tarifa));
        totalOtimizadoLabelRs.setText("R$ " + new DecimalFormat("#.##").format(b * this.tarifa));
        totalNaoOtimizadoLabelKw.setText(new DecimalFormat("#.##").format(a) + " Kw");
        totalOtimizadoLabelKw.setText(new DecimalFormat("#.##").format(b) + " Kw");
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
