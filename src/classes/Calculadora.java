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
        this.df.setRoundingMode(RoundingMode.CEILING);
    }

    public void calculaGastosTotais() {
        double totalMinimo = 0, totalMax = 0, totalMed = 0;
//        System.out.println("Total equipamentos selecionados: " + equipamentos.size());
        for (Equipamento e : this.equipamentos) {
//            System.out.println(e.getNome());
            totalMax = (e.getKwhMin() * e.getMaxUtilzacaoDiaria() * 30) * this.tarifa;
            totalMinimo = (e.getKwhMin() * 30 * e.getMinUtilzacaoDiaria()) * this.tarifa;
            this.totalMax += totalMax;
            this.totalMin += totalMinimo;
        }
        this.totalMax = Double.parseDouble(df.format(this.totalMax));
        this.totalMin = Double.parseDouble(df.format(this.totalMin));
//        System.out.println("Máxima: " + this.totalMax);
//        System.out.println("Minima: " + (this.totalMin + 0.06));
//        System.out.println("Media:" + this.getTotalMed());
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
}
