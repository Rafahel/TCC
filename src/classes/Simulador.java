package classes;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Simulador {
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private double tarifa;
    private double gastoTotal;
    private double kWh;
    private Escritor escritor;
    private int dia;
    private int hora;
    private int minuto;
    private int segundo;
    private String caminho;

    public Simulador(ArrayList<Equipamento> equipamentos, double objetivo, double tarifa, Escritor escritor) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.tarifa = tarifa;
        this.gastoTotal = 0.0;
        this.escritor = escritor;
        this.dia = 1;
        this.hora = 0;
        this.minuto = 0;
        this.segundo = 0;
        this.caminho = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\HouseMon" + "\\logSimulador.txt";


    }

//    public void simula() {
//
//
//        String horario = hora + ":" + minuto + ":" + segundo;
//
//        for (dia = 1; dia <= 30; dia++) {
////            System.out.println("DIA: " + ( dia + 1));
//            for (Equipamento equipamento : equipamentos) {
//                equipamento.setTempoRestante();
//                equipamento.setLigado(true);
//                horario = hora + ":" + minuto + ":" + segundo;
//                Escritor.geradorLogSimulador(caminho, horario, equipamento);
//            }
//            for (hora = 0; hora < 24; hora++) {
//
//                for (minuto = 0; minuto < 60; minuto++) {
//
//                    for (segundo = 0; segundo < 60; segundo++) {
////                        System.out.println("Dia: " + (dia + 1) + "horario: " + horas + ":" + minutos + ":" + segundos);
//                        for (Equipamento e : equipamentos) {
//                            if (!e.isLigado() && e.getTempoRestante() > 0) {
////                            System.out.println("Ligando equipamento " + e.getNome());
//                                e.setLigado(true);
//                                horario = hora + ":" + minuto + ":" + segundo;
//                                Escritor.geradorLogSimulador(caminho, horario, e);
//
//                            }
//
//                            e.reduzTempoRestanteSegundo();
//                            e.checkMinRestante();
//                            if (e.isLigado()) {
//                                this.gastoTotal += Calculadora.calculaGastoSegundo(e, this.tarifa);
//                            }
//                            if (e.getTempoRestante() <= 0 && e.isLigado()) {
////                                System.out.println("Resultado probabilidade: " + prob);
//                                if (Math.random() <= .25) {
////                                System.out.println(e.getNome() + "Desligado " + e.getNome() + " Tempo restante: " + e.getTempoRestante());
//                                    e.addTempoRestante(120);
//                                    continue;
//                                }
//                                e.setLigado(false);
//                                horario = hora + ":" + minuto + ":" + segundo;
//                                Escritor.geradorLogSimulador(caminho, horario, e);
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println("GASTO TOTAL: " + this.gastoTotal);
//    }


    public void simula(){
        double offset = 0.2;
        int valorRnd = 0;
        double custo = 0;
        double kwMensal = 0;
        for (int i = 0; i < 30; i++) {
            System.out.println("Dia " + (i + 1));
            for (Equipamento e: this.equipamentos) {
                if (Math.random() < offset){
                    if (Math.random() < 0.5){
                        valorRnd = (int) (e.getTempoOtimizado() * (offset + 1));
                        System.out.println("(Uso para mais) Tempo de uso de " + e.getNome() + " : " + valorRnd);
                        custo += valorRnd * e.getKwhMin() * 0.69118;
                        kwMensal += valorRnd * e.getKwhMin();
                    }else {
                        valorRnd = (int) (e.getTempoOtimizado() - (e.getTempoOtimizado() * offset));
                        System.out.println("(Uso para menos) Tempo de uso de " + e.getNome() + " : " + valorRnd);
                        custo += valorRnd * e.getKwhMin() * 0.69118;
                        kwMensal += valorRnd * e.getKwhMin();
                    }
                }
                else {
                    custo += e.getTempoOtimizado() * e.getKwhMin() * 0.69118;
                    kwMensal += e.getTempoOtimizado() * e.getKwhMin();
                }
            }

        }
        System.out.println(custo);
        System.out.println("kwMensal: " + kwMensal);
        System.out.println("custo do kwMensal: " + (kwMensal * 0.69118));
    }


    private String time(){
        Date date = new Date();
        return new SimpleDateFormat("ddMMyyyyhh:mm:ss").format(date);
    }
}
