package classes;

import java.util.ArrayList;
import java.util.Random;

public class OtimizacaoGenetica {
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    public OtimizacaoGenetica(ArrayList<Equipamento> equipamentos, double objetivo) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
    }


    public OtimizacaoGenetica() {
//        Double teste = Math.round(99.634534 * 100.0) / 100.0;
//
//
//        System.out.println("TESTE >>> " + teste);

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        this.equipamentos = new ArrayList<Equipamento>();

        /*
                        Microondas 1200 5 3
                        Lampada 9 300 120
                        Geladeira 130 1440 1440
                        Cafeteira 1000 10 6
                        Geladeira$2 200 1440 1440
         */
//        equipamentos.add(new Equipamento("Microondas" ,1200, 3, 5));
//        equipamentos.add(new Equipamento("Geladeira" ,130, 1440, 1440));
//        equipamentos.add(new Equipamento("Cafeteira" ,1000, 6, 10));
//        equipamentos.add(new Equipamento("Lampada" ,9, 120, 300));
    }

    public void otimiza(){
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(equipamentos, objetivo);
        Populacao populacao = new Populacao(50, equipamentos, this.objetivo);
        populacao.initialize();

        int generationCounter = 0;
        boolean encontrado = false;

        while (true){
            generationCounter++;
            Individuo melhor = populacao.getFitestIndividual();




//            System.out.println("\t\tRELATORIO DA POPULACAO");
//            for (int i = 0; i < populacao.size() ; i++) {
//                System.out.println(i + 1 + " - " + populacao.getIndividual(i));
//            }
//            System.out.println("");
//            System.out.println("Geracao: " + generationCounter + " - maior fitness : "
//                    + melhor.getFitness() + "%");
//            System.out.println(melhor);
//            System.out.println("Resultado: " + melhor.getResultado() + "\nObjetivo: " + this.objetivo + "\n");
//            System.out.println(melhor.getCalculo() + "\n");
            if (melhor.getFitness() >= Constantes.MAXIMUM_FITNESS && melhor.getFitness() <= 100 ){
                encontrado = true;
                break;
            }

            populacao = algoritmoGenetico.evolvePopulacao(populacao);

            if (generationCounter >= 90000 * 5){
                encontrado = false;
                break;
            }



        }

        if (encontrado){
            System.out.println("\n\nSolution Found!");
            System.out.println("Generation: " + generationCounter + " - fitness: " + populacao.getFitestIndividual().getFitness()
                    + "% Resultado: " + populacao.getFitestIndividual().getResultado());
            System.out.println(populacao.getFitestIndividual().getFitness() + "%");
            System.out.println(populacao.getFitestIndividual() + "\n" + "Objetivo: " + this.objetivo);
        }
        else {
            System.out.println("Não encontrado.");
        }
    }
}
