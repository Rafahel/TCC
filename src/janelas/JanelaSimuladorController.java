package janelas;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

    @FXML private Label precisaoLabel;

    private boolean gastoKw;

    private double[] resultadoA;

    private double[] resultadoB;

    private XYChart.Series<String, Double> seriesNotimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesNotimizadoGasto = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoGasto = new XYChart.Series<>();
    
    private double tarifa;

    private double objetivo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void inicializaJanela(double tarifa, double[] resultadoA, double[] resultadoB, double objetivo){
        this.tarifa = tarifa;
        this.objetivo = objetivo;
        this.resultadoA = resultadoA;
        this.resultadoB = resultadoB;
        this.lineChart.getData().clear();
        this.lineChart.setCreateSymbols(false);
        
        this.seriesNotimizadoKw.setName("Não Otimizado");
        this.seriesOtimizadoKw.setName("Otimizado");
        this.seriesNotimizadoGasto.setName("Não Otimizado");
        this.seriesOtimizadoGasto.setName("Otimizado");
        double a = 0;
        double b = 0;
        for (int i = 0; i < resultadoA.length ; i++) {
            this.textAreaResultadoOtimizado.appendText("" + resultadoB[i] + "\n");
            this.textAreaResultadoNaoOtimizado.appendText("" + resultadoA[i] + "\n");
            a += resultadoA[i];
            b += resultadoB[i];
            this.seriesNotimizadoKw.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1),resultadoA[i]));
            this.seriesNotimizadoGasto.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1), (a * this.tarifa)));
            this.seriesOtimizadoKw.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1),resultadoB[i]));
            this.seriesOtimizadoGasto.getData().add(new XYChart.Data<String, Double>(Integer.toString(i+1), (b * this.tarifa)));
        }
        this.totalNaoOtimizadoLabelRs.setText("R$ " + new DecimalFormat("#.##").format(a * this.tarifa));
        this.totalOtimizadoLabelRs.setText("R$ " + new DecimalFormat("#.##").format(b * this.tarifa));
        this.totalNaoOtimizadoLabelKw.setText(new DecimalFormat("#.##").format(a) + " Kw");
        this.totalOtimizadoLabelKw.setText(new DecimalFormat("#.##").format(b) + " Kw");
        this.precisaoLabel.setText(new DecimalFormat("#.##").format(((b * this.tarifa) * 100)/this.objetivo) + " %");
        this.lineChart.getData().add(this.seriesNotimizadoKw);
        this.lineChart.getData().add(this.seriesOtimizadoKw);
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
            this.lineChart.getData().add(this.seriesNotimizadoKw);
            this.lineChart.getData().add(this.seriesOtimizadoKw);
        }
    }
}
