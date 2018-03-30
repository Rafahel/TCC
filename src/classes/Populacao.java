package classes;

import java.util.ArrayList;
import java.util.Random;

public class Populacao {

    private Individuo[] individuals;
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private int diasRestantes;
    private double tarifa;

    public Populacao(int populationSize, ArrayList<Equipamento> equipamentos, double objetivo, int diasRestantes, double tarifa) {
        this.individuals = new Individuo[populationSize];
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.diasRestantes = diasRestantes;
        this.tarifa = tarifa;
        initialize();

    }

    public void initialize() {
        for (int i = 0; i < individuals.length; i++) {
            Individuo individual = new Individuo(equipamentos, this.objetivo, this.diasRestantes, this.tarifa);
            individual.generateIndividual();
            saveIndividual(i, individual);

        }
    }

    public Individuo getIndividual(int index) {
        return this.individuals[index];
    }


    public Individuo getFitestIndividual() {
        this.sortIndividuals();
        double fitness = 0;
        Individuo fittest = individuals[0];
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].getFitness() > fitness && individuals[i].getFitness() <= 100) {
                fittest = individuals[i];
                fitness = fittest.getFitness();
            }

        }
        return fittest;
    }

    private void sortIndividuals(){
        int n = individuals.length;
        Individuo temp;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (individuals[j - 1].getFitness() > individuals[j].getFitness()) {
                    temp = individuals[j - 1];
                    individuals[j - 1] = individuals[j];
                    individuals[j] = temp;
                }
            }
        }
    }

    public int size() {
        return this.individuals.length;
    }

    public void saveIndividual(int index, Individuo individual) {
        this.individuals[index] = individual;
    }
}
