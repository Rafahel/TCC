package classes;

import java.util.ArrayList;
import java.util.Random;

public class AlgoritmoGenetico {

    private Random randomGenerator;
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;

    public AlgoritmoGenetico(ArrayList<Equipamento> equipamentos, double objetivo) {
        this.randomGenerator = new Random();
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
    }

    public Populacao evolvePopulacao(Populacao populacao) {
        Populacao newPopulacao = new Populacao(populacao.size(), equipamentos, this.objetivo);
        for (int i = 0; i < populacao.size(); i++) {
            Individuo firstIndividual = randomSelection(populacao);
            Individuo secondIndividual = randomSelection(populacao);
            Individuo newIndividual = crossover(firstIndividual, secondIndividual);
            newPopulacao.saveIndividual(i, newIndividual);
        }

        for (int i = 0; i < populacao.size(); i++) {
            mutate(newPopulacao.getIndividual(i));
        }
        return newPopulacao;
    }

    private Individuo randomSelection(Populacao Populacao) {
        Populacao newPopulacao = new Populacao(Constantes.TOURNAMENT_SIZE, equipamentos, this.objetivo);
        for (int i = 0; i < Constantes.TOURNAMENT_SIZE; i++) {
            int randomIndex = (int) (Math.random() * Populacao.size());
            newPopulacao.saveIndividual(i, Populacao.getIndividual(randomIndex));

        }

        return newPopulacao.getFitestIndividual();
    }

    private Individuo crossover(Individuo firstIndividual, Individuo secondIndividual) {
        Individuo newSolution = new Individuo(equipamentos, this.objetivo);
        for (int i = 0; i < equipamentos.size(); i++) {
            if (Math.random() <= Constantes.CROSSOVER_RATE) {
                newSolution.setGenes(i, firstIndividual.getGene(i));
            } else {
                newSolution.setGenes(i, secondIndividual.getGene(i));
            }
        }
        return newSolution;
    }

    private void mutate(Individuo individual) {
        for (int i = 0; i < equipamentos.size(); i++) {
            if (Math.random() < Constantes.MUTATION_RATE) {
                int min = equipamentos.get(i).getMinUtilzacaoDiaria();
                int max = equipamentos.get(i).getMaxUtilzacaoDiaria();
                int gene = randomGenerator.nextInt((max - min) + 1) + min;
                individual.setGenes(i, gene);
            }
        }
    }

}
