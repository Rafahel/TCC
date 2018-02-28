package classes;


import java.util.ArrayList;

public class Simulator {

    ArrayList<Equipamento> equipamentos;
    private double tarifa;
    private double totalMax;
    private double totalMin;
    private double totalMed;

    public Simulator(ArrayList<Equipamento> equipamentos, double tarifa) {
        this.equipamentos = equipamentos;
        this.tarifa = tarifa;
        this.totalMax = 0;
        this.totalMin = 0;
        this.totalMed = 0;
    }

//    public static void main(String[] args){
//
//        int dia = 0;
//        int hora = 0;
//        int minuto = 0;
//        for (int i = 1; i <= 30 ; i++) {
//            ++ dia;
//            for (minuto = 0; minuto < 1440; minuto++){
//                System.out.println("Dia atual: " + dia + " Minuto do dia atual: " + minuto);
//                if(minuto % 60 == 0){
//                    hora++;
//                }
//            }
//            System.out.println("Hora final: " + hora);
//            hora = 0;
//            System.out.println("-------- Fim do Dia --------");
//        }
//    }
//
//    public void simula(){
//        int dia = 0;
//        int hora = 0;
//        int minuto = 0;
//        for (int i = 1; i <= 30 ; i++) {
//            ++ dia;
//            for (minuto = 0; minuto < 1440; minuto++){
//                System.out.println("Dia atual: " + dia + " Minuto do dia atual: " + minuto);
//                if(minuto % 60 == 0){
//                    hora++;
//                }
//            }
//            System.out.println("Hora final: " + hora);
//            hora = 0;
//            System.out.println("-------- Fim do Dia --------");
//        }
//    }

    public void calculaGastosTotais(){
        double totalMinimo = 0, totalMax = 0, totalMed = 0;
        System.out.println("Total equipamentos selecionados: " + equipamentos.size());
        for (Equipamento e: this.equipamentos) {
            totalMax = (e.getKwhMin() * e.getMaxUtilzacaoDiaria() * 30 ) * this.tarifa;
            totalMed = (e.getKwhMin() * 30 * (e.getMaxUtilzacaoDiaria()/2)) * this.tarifa;
            totalMinimo = (e.getKwhMin() * 30 * e.getMinUtilzacaoDiaria()) * this.tarifa;
            this.totalMax += totalMax;
            this.totalMed += totalMed;
            this.totalMin += totalMinimo;
        }
        System.out.println("Máxima: " + this.totalMax);
        System.out.println("Média: " + this.totalMed);
        System.out.println("Minima: " + this.totalMin);
    }

    public double getTotalMax() {
        return totalMax;
    }

    public double getTotalMin() {
        return totalMin;
    }

    public double getTotalMed() {
        return totalMed;
    }
}
