package classes;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Simulador {
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private double tarifa;
    private double offset = 0.2;
    private String resultado = "";
    private String caminho;
    private int dias;
    boolean otimizar;

    public Simulador(ArrayList<Equipamento> equipamentos, double objetivo, double tarifa, Escritor escritor) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.tarifa = tarifa;
        this.offset = 0.2;
        this.caminho = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\HouseMon" + "\\logSimulador.txt";
        this.dias = 30;
        this.otimizar = false;

    }

    public void setOtimizar(boolean otimizar) {
        this.otimizar = otimizar;
    }

    public void simula(){

        int valorRnd = 0;
        double custo = 0;
        double kwMensal = 0;
        double kwDiario = 0;
        double gastado = 0;
        double novoObj = this.objetivo;
        this.resultado = "";

        double gastoDiario = 0;


        for (int i = 0; i < 30; i++) {
//            System.out.println("Dia " + (i + 1));
            kwDiario = 0;
            for (Equipamento e: this.equipamentos) {

                if (Math.random() < offset){
                    if(e.getMinUtilzacaoDiaria() == 1440)
                        continue;
                    valorRnd = (int) (e.getTempoOtimizado() * ((new Random().nextInt(3) + 1)* Math.random() + 1) );
//                    System.out.println("(Uso para mais) Tempo de uso de " + e.getNome() + " : " + valorRnd);
                    custo += valorRnd * e.getKwhMin() * this.tarifa;
                    kwMensal += valorRnd * e.getKwhMin();
                    kwDiario += valorRnd * e.getKwhMin();
                    gastoDiario += valorRnd * e.getKwhMin() * this.tarifa;
                }
                else {
                    custo += e.getTempoOtimizado() * e.getKwhMin() * this.tarifa;
                    kwMensal += e.getTempoOtimizado() * e.getKwhMin();
                    kwDiario += e.getTempoOtimizado() * e.getKwhMin();
                    gastoDiario += e.getTempoOtimizado() * e.getKwhMin() * this.tarifa;
                }

            }

//            System.out.println(">>> " + ((kwDiario * dias * this.tarifa) + gastado));
            gastado += kwDiario * this.tarifa;
//            System.out.println(">>> GASTO DIARIO  " + kwDiario * this.tarifa);
//            System.out.println(">>> GASTO DIARIO OTIMIZADO : " + gastoDiario());
//            System.out.println("REDUZIR >>> " + kwDiario * this.tarifa);
//            System.out.println("TOTAL GASTO ATE AGORA: " + (kwMensal * this.tarifa));

            double reducao = (kwDiario * this.tarifa) - gastoDiario();
            this.resultado += kwDiario + "\n";

            novoObj -= (kwDiario * this.tarifa);
            if(reducao > 0 && otimizar){
//                System.out.println("Nova otimizacao sendo feita, o valor passou do objetivo. Dias Restantes " + dias);
//                novoObj -= reducao;
//                System.out.println("Novo Obj: " + novoObj);
                OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(this.equipamentos, novoObj, dias + 1);
                otimizacaoGenetica.otimiza();
                this.utilizaOtimizacao(otimizacaoGenetica.getGenes());

            }
            reducao = 0;
            dias --;
        }
        System.out.println("Custo total mensal: " + custo);
        System.out.println("kwMensal: " + kwMensal);
        System.out.println("custo do kwMensal: " + (kwMensal * this.tarifa));
        System.out.println("resultado: \n" + resultado);
        this.dias = 30;


    }


    private void utilizaOtimizacao (int[] genes) {
        System.out.println("Otimizacao sendo utilizada");
        int pos = 0;
        double kw = 0;
        for (Equipamento e: equipamentos) {
//            System.out.println(genes[pos]);
            e.setTempoOtimizado(genes[pos]);
            kw = (e.getKwhMin() * e.getTempoOtimizado()) * this.tarifa;
            pos++;
//            System.out.println("Equipamento " + e.getNome() + " tempo novo: " + e.getTempoOtimizado());
        }
    }


    private double gastoDiario(){
        double gastoDiario = 0 ;
        for (Equipamento e: this.equipamentos) {
            gastoDiario += e.getTempoOtimizado() * e.getKwhMin();
        }

        gastoDiario = gastoDiario * this.tarifa;
//        System.out.println(">>> GASTO DIARIO OTIMIZADO : " + gastoDiario);
        return gastoDiario;
    }

    public double[] getResultado() {
        double[] valores = new double[resultado.split("\n").length];
        String[] str = resultado.split("\n");
        int pos = 0;
        for (String s: str) {
            try {
                valores[pos] = Double.parseDouble(s);
            }catch (NumberFormatException e){

            }
            pos++;
        }
        return valores;
    }

    private String time(){
        Date date = new Date();
        return new SimpleDateFormat("ddMMyyyyhh:mm:ss").format(date);
    }
}
