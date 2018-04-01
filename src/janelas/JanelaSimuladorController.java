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
import java.util.Formatter;
import java.util.ResourceBundle;

public class JanelaSimuladorController implements Initializable {

    @FXML
    private TextArea textAreaResultadoOtimizado;

    @FXML
    private TextArea textAreaResultadoNaoOtimizado;

    @FXML
    private TextArea gastoPorDiaTextArea;

    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private Button mudaModoChartButton;

    @FXML
    private Label yLabel;

    @FXML
    private Label totalNaoOtimizadoLabelKw;

    @FXML
    private Label totalOtimizadoLabelKw;

    @FXML
    private Label totalNaoOtimizadoLabelRs;

    @FXML
    private Label totalOtimizadoLabelRs;

    @FXML
    private Label precisaoLabel;

    @FXML
    private Label objetivoLabel;

    private double[] resultadoNaoOtimizado;

    private double[] somatorioOtimizado;

    private XYChart.Series<String, Double> seriesNotimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesNotimizadoGasto = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoKw = new XYChart.Series<>();

    private XYChart.Series<String, Double> seriesOtimizadoGasto = new XYChart.Series<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void inicializaJanela(double tarifa, double[] resultadoA, double[] resultadoB, double objetivo) {
        this.resultadoNaoOtimizado = resultadoA;
        this.somatorioOtimizado = resultadoB;
        this.lineChart.getData().clear();
        this.lineChart.setCreateSymbols(false);
        this.seriesNotimizadoKw.setName("Não Otimizado");
        this.seriesOtimizadoKw.setName("Otimizado");
        this.seriesNotimizadoGasto.setName("Não Otimizado");
        this.seriesOtimizadoGasto.setName("Otimizado");
        DecimalFormat formatador = new DecimalFormat("#.##");
        formatador.setMinimumFractionDigits(2);
        this.objetivoLabel.setText("R$ " + formatador.format(objetivo));
        double somatorioNaoOtimizado = 0;
        double somatorioOtimizado = 0;
        for (int i = 0; i < resultadoA.length; i++) {
            this.textAreaResultadoOtimizado.appendText("" + formatador.format(this.somatorioOtimizado[i]) + "\n");
            this.textAreaResultadoNaoOtimizado.appendText("" + formatador.format(this.resultadoNaoOtimizado[i]) + "\n");
            somatorioNaoOtimizado += this.resultadoNaoOtimizado[i];
            somatorioOtimizado += this.somatorioOtimizado[i];
            this.seriesNotimizadoKw.getData().add(new XYChart.Data<>(Integer.toString(i + 1), this.resultadoNaoOtimizado[i]));
            this.seriesNotimizadoGasto.getData().add(new XYChart.Data<>(Integer.toString(i + 1), (somatorioNaoOtimizado * tarifa)));
            this.seriesOtimizadoKw.getData().add(new XYChart.Data<>(Integer.toString(i + 1), this.somatorioOtimizado[i]));
            this.seriesOtimizadoGasto.getData().add(new XYChart.Data<>(Integer.toString(i + 1), (somatorioOtimizado * tarifa)));
            this.gastoPorDiaTextArea.appendText("Dia " + (i + 1) + ": Otimizado: R$ " +
                    formatador.format(resultadoB[i] * tarifa) +
                    "\t\tNão Otimizado: R$ " + formatador.format(resultadoA[i] * tarifa) + "\n");
        }
        this.gastoPorDiaTextArea.appendText("\n----------------------------------------------------------------\n");
        this.gastoPorDiaTextArea.appendText("Total Otimizado: R$ " + formatador.format(somatorioOtimizado * tarifa) + "\t\tNão otimizado: R$" + formatador.format(somatorioNaoOtimizado * tarifa));
        this.totalNaoOtimizadoLabelRs.setText("R$ " + formatador.format(somatorioNaoOtimizado * tarifa));
        this.totalOtimizadoLabelRs.setText("R$ " + formatador.format(somatorioOtimizado * tarifa));
        this.totalNaoOtimizadoLabelKw.setText(formatador.format(somatorioNaoOtimizado) + " Kw");
        this.totalOtimizadoLabelKw.setText(formatador.format(somatorioOtimizado) + " Kw");
        this.precisaoLabel.setText(formatador.format(((somatorioOtimizado * tarifa) * 100) / objetivo) + " %");
        this.lineChart.getData().add(this.seriesNotimizadoKw);
        this.lineChart.getData().add(this.seriesOtimizadoKw);
    }

    @FXML
    private void toogleGastoDiarioClicked() {
        if (this.mudaModoChartButton.getText().equals("Gasto diario kw")) {
            this.yLabel.setText("R$");
            this.mudaModoChartButton.setText("Gasto diario");
            this.lineChart.getData().clear();
            this.lineChart.getData().add(this.seriesNotimizadoGasto);
            this.lineChart.getData().add(this.seriesOtimizadoGasto);
        } else {
            this.mudaModoChartButton.setText("Gasto diario kw");
            this.yLabel.setText("kw");
            this.lineChart.getData().clear();
            this.lineChart.getData().add(this.seriesNotimizadoKw);
            this.lineChart.getData().add(this.seriesOtimizadoKw);
        }
    }
}
