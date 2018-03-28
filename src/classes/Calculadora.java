package classes;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculadora {

    ArrayList<Equipamento> equipamentos;
    private double tarifa;
    private double totalMax;
    private double totalMin;
    private DecimalFormat df;

    public Calculadora(ArrayList<Equipamento> equipamentos, double tarifa) {
        this.equipamentos = equipamentos;
        this.tarifa = tarifa;
        this.totalMax = 0;
        this.totalMin = 0;
        this.df = new DecimalFormat("#.##");
        this.df.setRoundingMode(RoundingMode.DOWN);
    }

    public void calculaGastosTotais() {
        double totalMinimo = 0, totalMax = 0;
        for (Equipamento e : this.equipamentos) {
            totalMax = (e.getKwhMin() * e.getMaxUtilzacaoDiaria() * 30) * this.tarifa;
            totalMinimo = (e.getKwhMin() * 30 * e.getMinUtilzacaoDiaria()) * this.tarifa;
            this.totalMax += totalMax;
            this.totalMin += totalMinimo;
        }
        this.totalMax = Double.parseDouble(df.format(this.totalMax));
        this.totalMin = Double.parseDouble(df.format(this.totalMin));
    }

    public double getTotalMax() {
        return totalMax;
    }

    public double getTotalMin() {
        return totalMin;
    }

    private double getTotalMed() {
        return Double.parseDouble(df.format((this.totalMax + this.totalMin) / 2));
    }

    public double[] getResults() {
        return new double[]{getTotalMax(), getTotalMed(), getTotalMin()};
    }

    public static double calculaGastoSegundo(Equipamento equipamento, double tarifa){
        return ((equipamento.getKwhMin()/60) * tarifa);
    }

    public double calculakwh(){
        double kWh = 0;
        for (Equipamento e: this.equipamentos) {
            kWh+= ((e.getWatts()) * e.getMaxUtilzacaoDiaria());
            kWh = (kWh/1000) / 2;
        }
        return Double.parseDouble(df.format(kWh));
    }
}
