package classes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Simulador {
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Equipamento> equipamentosOtimizado;
    private double objetivo;
    private double tarifa;
    private double offset;
    private int dias;
    private double[] resultadoNotimizado;
    private double[] resultadoOtimizado;
    private Escritor escritor;
    private String local;
    private double progresso;

    public Simulador(ArrayList<Equipamento> equipamentos, double objetivo, double tarifa) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.tarifa = tarifa;
        this.dias = 30;
        this.offset = 0.7;
        this.equipamentosOtimizado = new ArrayList<>();
        this.deepCpy();
        this.resultadoNotimizado = new double[equipamentos.size()];
        this.resultadoOtimizado = new double[equipamentos.size()];
        this.resultadoNotimizado = new double[30];
        this.resultadoOtimizado = new double[30];
        this.local = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\HouseMon\\Simulação.txt";
//        System.out.println(this.local);
        this.progresso = 0;
    }

    public void simula(){

        int valorRnd = 0;
        int valorRndOt = 0;
        double kwDiarioOt = 0;
        double kwDiario = 0;
        double novoObj = this.objetivo;
        for (int i = 0; i < 30; i++) {
//            System.out.println("Dia " + (i + 1));
            kwDiario = 0;
            kwDiarioOt = 0;
            for (int j = 0; j < this.equipamentos.size(); j++) {
                if (Math.random() < offset){
                    if(this.equipamentos.get(j).getMinUtilzacaoDiaria() != 1440){
                        double atrasoGerado = 1 +( 0.1 + (0.6 - 0.1) * new Random().nextDouble());//(1 + Math.random());
//                        System.out.println(atrasoGerado);
                        equipamentos.get(j).setTempoExcedido((int)(this.equipamentos.get(j).getTempoOtimizado() * atrasoGerado ));
                        equipamentosOtimizado.get(j).setTempoExcedido((int)(this.equipamentos.get(j).getTempoOtimizado() * atrasoGerado ));
                        valorRnd = (int) (this.equipamentos.get(j).getTempoOtimizado() * atrasoGerado );
                        valorRndOt = (int) (this.equipamentosOtimizado.get(j).getTempoOtimizado() * atrasoGerado);
                        if (valorRnd > 1440){
                            equipamentos.get(j).setTempoExcedido(1440 - equipamentos.get(i).getTempoOtimizado());
                            valorRnd = 1440;
                        }
                        if (valorRndOt > 1440){
                            equipamentosOtimizado.get(j).setTempoExcedido(1440 - equipamentos.get(i).getTempoOtimizado());
                            valorRndOt = 1440;
                        }
                    }else {
                        equipamentos.get(j).setTempoExcedido(0);
                        equipamentosOtimizado.get(j).setTempoExcedido(0);
                        valorRnd = this.equipamentos.get(j).getTempoOtimizado();
                        valorRndOt = this.equipamentosOtimizado.get(j).getTempoOtimizado();
                    }
//                    System.out.println("(Uso para mais) Tempo de uso de " + e.getNome() + " : " + valorRnd);
                    kwDiario += valorRnd * this.equipamentos.get(j).getKwhMin();
                    kwDiarioOt += valorRndOt * this.equipamentosOtimizado.get(j).getKwhMin();
                }
                else {
                    equipamentos.get(j).setTempoExcedido(0);
                    equipamentosOtimizado.get(j).setTempoExcedido(0);
                    kwDiario += this.equipamentos.get(j).getTempoOtimizado() * this.equipamentos.get(j).getKwhMin();
                    kwDiarioOt += this.equipamentosOtimizado.get(j).getTempoOtimizado() * this.equipamentosOtimizado.get(j).getKwhMin();
                }
            }
//            System.out.println(">>> " + ((kwDiario * dias * this.tarifa) + gastado));

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
//            System.out.println("reducao: " + reducao);
            if(reducao > 0){
//                System.out.println("Nova otimizacao sendo feita, o valor passou do objetivo. Dias Restantes " + dias);
//                novoObj -= reducao;
//                System.out.println("Novo Obj: " + novoObj);
                while (true){
                    OtimizacaoGenetica otimizacaoGenetica = new OtimizacaoGenetica(this.equipamentosOtimizado, novoObj, this.dias, this.tarifa);
                    otimizacaoGenetica.otimiza();
                    if (otimizacaoGenetica.isEncontrado()){
                        this.utilizaOtimizacao(otimizacaoGenetica.getSolucao().getTempos());
                        break;
                    }
                    novoObj += 0.3;
                }
            }
            Escritor.geradorLogSimulador(local, (i+1), equipamentosOtimizado);
            this.progresso = (i * 100) / 29;
            this.dias --;
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
        for (Equipamento e: this.equipamentosOtimizado) {
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
//        System.out.println("Sizes: " + this.equipamentosOtimizado.size() + " :: " + this.equipamentos.size());
    }

    public double[] getResultadoNotimizado() {
        return this.resultadoNotimizado;
    }

    public double[] getResultadoOtimizado() {
        return this.resultadoOtimizado;
    }

    private String time(){
        Date date = new Date();
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public int getDias() {
        return dias;
    }

    public double getProgresso() {
        return progresso;
    }
}