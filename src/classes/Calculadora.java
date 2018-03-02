package classes;


import java.util.ArrayList;

public class Calculadora {

    ArrayList<Equipamento> equipamentos;
    private double tarifa;
    private double totalMax;
    private double totalMin;


    public Calculadora(ArrayList<Equipamento> equipamentos, double tarifa) {
        this.equipamentos = equipamentos;
        this.tarifa = tarifa;
        this.totalMax = 0;
        this.totalMin = 0;
    }

    public void calculaGastosTotais(){
        double totalMinimo = 0, totalMax = 0, totalMed = 0;
        System.out.println("Total equipamentos selecionados: " + equipamentos.size());
        for (Equipamento e: this.equipamentos) {
            totalMax = (e.getKwhMin() * e.getMaxUtilzacaoDiaria() * 30 ) * this.tarifa;
            totalMinimo = (e.getKwhMin() * 30 * e.getMinUtilzacaoDiaria()) * this.tarifa;
            this.totalMax += totalMax;
            this.totalMin += totalMinimo;
        }

        System.out.println("Máxima: " + this.totalMax);
        System.out.println("Minima: " + this.totalMin);
        System.out.println("Media:" + getTotalMed());
    }

    public double getTotalMax() {
        return totalMax;
    }

    public double getTotalMin() {
        return totalMin;
    }

    public double getTotalMed() {
        return (totalMax + totalMin) / 2;
    }
}
