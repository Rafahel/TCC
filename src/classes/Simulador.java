package classes;

import java.util.ArrayList;
import java.util.Random;

public class Simulador {
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Equipamento> equipamentosOtimizado;
    private double objetivo;
    private double tarifa;
    private double offset;
    private String caminho;
    private int dias;
    boolean otimizar;
    private double[] resultadoNotimizado;
    private double[] resultadoOtimizado;

    public Simulador(ArrayList<Equipamento> equipamentos, double objetivo, double tarifa, Escritor escritor) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.tarifa = tarifa;
        this.caminho = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\HouseMon" + "\\logSimulador.txt";
        this.dias = 30;
        this.otimizar = false;
        this.offset = 0.4;
        this.equipamentosOtimizado = new ArrayList<>();
        this.deepCpy();
        this.resultadoNotimizado = new double[equipamentos.size()];
        this.resultadoOtimizado = new double[equipamentos.size()];
        this.resultadoNotimizado = new double[30];
        this.resultadoOtimizado = new double[30];
    }

    public void setOtimizar(boolean otimizar) {
        this.otimizar = otimizar;
    }

    public void simula(){

        int valorRnd = 0;
        int valorRndOt = 0;
        double custo = 0;
        double kwMensal = 0;
        double kwDiario = 0;
        double gastado = 0;
        double novoObj = this.objetivo;
        double gastoDiario = 0;
        double custoOt = 0;
        double kwMensalOt = 0;
        double kwDiarioOt = 0;
        double gastoDiarioOt = 0;
        double gastadoOt = 0;
        for (int i = 0; i < 30; i++) {
//            System.out.println("Dia " + (i + 1));
            kwDiario = 0;
            kwDiarioOt = 0;
            for (int j = 0; j < equipamentos.size(); j++) {
                if (Math.random() < offset){
                    if(equipamentos.get(j).getMinUtilzacaoDiaria() != 1440){
                        double atrasoGerado = ((new Random().nextInt(2) + 1)* Math.random() + 1);
                        valorRnd = (int) (equipamentos.get(j).getTempoOtimizado() * atrasoGerado );
                        valorRndOt = (int) (equipamentosOtimizado.get(j).getTempoOtimizado() * atrasoGerado);
                        if (valorRnd > 1440){
                            valorRnd = 1440;
                        }
                        if (valorRndOt > 1440){
                            valorRndOt = 1440;
                        }
                    }else {
                        valorRnd = equipamentos.get(j).getTempoOtimizado();
                        valorRndOt = equipamentosOtimizado.get(j).getTempoOtimizado();
                    }
//                    System.out.println("(Uso para mais) Tempo de uso de " + e.getNome() + " : " + valorRnd);
                    custo += valorRnd * equipamentos.get(j).getKwhMin() * this.tarifa;
                    kwMensal += valorRnd * equipamentos.get(j).getKwhMin();
                    kwDiario += valorRnd * equipamentos.get(j).getKwhMin();
                    gastoDiario += valorRnd * equipamentos.get(j).getKwhMin() * this.tarifa;

                    custoOt += valorRndOt * equipamentosOtimizado.get(j).getKwhMin() * this.tarifa;
                    kwMensalOt += valorRndOt * equipamentosOtimizado.get(j).getKwhMin();
                    kwDiarioOt += valorRndOt * equipamentosOtimizado.get(j).getKwhMin();
                    gastoDiarioOt += valorRndOt * equipamentosOtimizado.get(j).getKwhMin() * this.tarifa;
                }
                else {
                    custo += equipamentos.get(j).getTempoOtimizado() * equipamentos.get(j).getKwhMin() * this.tarifa;
                    kwMensal += equipamentos.get(j).getTempoOtimizado() * equipamentos.get(j).getKwhMin();
                    kwDiario += equipamentos.get(j).getTempoOtimizado() * equipamentos.get(j).getKwhMin();
                    gastoDiario += equipamentos.get(j).getTempoOtimizado() * equipamentos.get(j).getKwhMin() * this.tarifa;

                    custoOt += equipamentosOtimizado.get(j).getTempoOtimizado() * equipamentosOtimizado.get(j).getKwhMin() * this.tarifa;
                    kwMensalOt += equipamentosOtimizado.get(j).getTempoOtimizado() * equipamentosOtimizado.get(j).getKwhMin();
                    kwDiarioOt += equipamentosOtimizado.get(j).getTempoOtimizado() * equipamentosOtimizado.get(j).getKwhMin();
                    gastoDiarioOt += equipamentosOtimizado.get(j).getTempoOtimizado() * equipamentosOtimizado.get(j).getKwhMin() * this.tarifa;
                }
            }
//            for (Equipamento e: this.equipamentos) {
//                if (Math.random() < offset){
//                    if(e.getMinUtilzacaoDiaria() != 1440){
//                        valorRnd = (int) (e.getTempoOtimizado() * ((new Random().nextInt(2) + 1)* Math.random() + 1) );
//                        if (valorRnd > 1440){
//                            valorRnd = 1440;
//                        }
//                    }else {
//                        valorRnd = e.getMinUtilzacaoDiaria();
//                    }
////                    System.out.println("(Uso para mais) Tempo de uso de " + e.getNome() + " : " + valorRnd);
//                    custo += valorRnd * e.getKwhMin() * this.tarifa;
//                    kwMensal += valorRnd * e.getKwhMin();
//                    kwDiario += valorRnd * e.getKwhMin();
//                    gastoDiario += valorRnd * e.getKwhMin() * this.tarifa;
//                }
//                else {
//                    custo += e.getTempoOtimizado() * e.getKwhMin() * this.tarifa;
//                    kwMensal += e.getTempoOtimizado() * e.getKwhMin();
//                    kwDiario += e.getTempoOtimizado() * e.getKwhMin();
//                    gastoDiario += e.getTempoOtimizado() * e.getKwhMin() * this.tarifa;
//                }
//            }
//            System.out.println(">>> " + ((kwDiario * dias * this.tarifa) + gastado));
            gastado += kwDiario * this.tarifa;
            gastadoOt += kwDiarioOt * this.tarifa;
//            System.out.println(">>> GASTO DIARIO  " + kwDiario * this.tarifa);
//            System.out.println(">>> GASTO DIARIO OTIMIZADO : " + gastoDiario());
//            System.out.println("REDUZIR >>> " + kwDiario * this.tarifa);
//            System.out.println("TOTAL GASTO ATE AGORA: " + (kwMensal * this.tarifa));

//            System.out.println("Dia" + (i + 1 ) + "    " + kwDiario + " : " + kwDiarioOt);
            double reducao = (kwDiarioOt * this.tarifa) - gastoDiario();
            this.resultadoOtimizado[i] = kwDiarioOt;
            this.resultadoNotimizado[i] = kwDiario;

//            System.out.println("Dia" + (i + 1 ) + "    " + gastado + " : " + gastadoOt);

            novoObj -= (kwDiarioOt * this.tarifa);
//            System.out.println("novo obj: " + novoObj);
            if(reducao > 0){
//                System.out.println("Nova otimizacao sendo feita, o valor passou do objetivo. Dias Restantes " + dias);
//                novoObj -= reducao;
//                System.out.println("Novo Obj: " + novoObj);
                OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(this.equipamentosOtimizado, novoObj, dias );
                otimizacaoGenetica.otimiza();
                this.utilizaOtimizacao(otimizacaoGenetica.getGenes());

            }
            dias --;
        }
//        System.out.println("Custo total mensal: " + custo);
//        System.out.println("kwMensal: " + kwMensal);
//        System.out.println("custo do kwMensal: " + (kwMensal * this.tarifa));
////        System.out.println("resultado: \n" + resultado);
//
//        System.out.println("Custo total mensal OT: " + custoOt);
//        System.out.println("kwMensal OT: " + kwMensalOt);
//        System.out.println("custo do kwMensal OT: " + (kwMensalOt * this.tarifa));
////        System.out.println("resultado: \n" + resultado);
    }


    private void utilizaOtimizacao (int[] genes) {
//        System.out.println("Otimizacao sendo utilizada");
        int pos = 0;
        double kw = 0;
        for (Equipamento e: equipamentosOtimizado) {
//            System.out.println(genes[pos]);
            e.setTempoOtimizado(genes[pos]);
            kw = (e.getKwhMin() * e.getTempoOtimizado()) * this.tarifa;
            pos++;
//            System.out.println("Equipamento " + e.getNome() + " tempo novo: " + e.getTempoOtimizado());
        }
    }

    private double gastoDiario(){
        double gastoDiario = 0 ;
        for (Equipamento e: this.equipamentosOtimizado) {
            gastoDiario += e.getTempoOtimizado() * e.getKwhMin();
        }

        gastoDiario = gastoDiario * this.tarifa;
//        System.out.println(">>> GASTO DIARIO OTIMIZADO : " + gastoDiario);
        return gastoDiario;
    }

    private void deepCpy(){
        for (Equipamento e: this.equipamentos) {
            Equipamento aux = new Equipamento(e.getNome(), e.getWatts(), e.getMinUtilzacaoDiaria(), e.getMaxUtilzacaoDiaria());
            aux.setTempoOtimizado(e.getTempoOtimizado());
            this.equipamentosOtimizado.add(aux);
        }
        System.out.println("Sizes: " + equipamentosOtimizado.size() + " :: " + equipamentos.size());
    }

    public double[] getResultadoNotimizado() {
        return resultadoNotimizado;
    }

    public double[] getResultadoOtimizado() {
        return resultadoOtimizado;
    }
}