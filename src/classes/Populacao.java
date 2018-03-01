package classes;

import java.util.ArrayList;
import java.util.Random;

public class Populacao {

    private Individuo[] individuals;
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;

    public Populacao(int populationSize, ArrayList<Equipamento> equipamentos, double objetivo) {
        this.individuals = new Individuo[populationSize];
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        initialize();

    }

    public void initialize(){
        for (int i = 0; i < individuals.length; i++) {
            Individuo individual = new Individuo(equipamentos, this.objetivo);
            individual.generateIndividual();
            saveIndividual(i, individual);

        }
    }

    public Individuo getIndividual(int index){
        return this.individuals[index];
    }


    //TODO RESOLVER PROBLEMA DO FITNESS
    public Individuo getFitestIndividual(){
        Individuo fitest = individuals[0];
        for (int i = 0; i < individuals.length ; i++) {
            // Caso fitness do primeiro individuo esteja maior que 100
            if (fitest.getFitness() > 100){
                if (getIndividual(i).getFitness() < 100)
                    fitest = getIndividual(i);
            }
            else {
                if (getIndividual(i).getFitness() < 100 && getIndividual(i).getFitness() > fitest.getFitness())
                    fitest = getIndividual(i);
            }
//            if (getIndividual(i).getFitness() < 1.0){
//                if (getIndividual(i).getFitness() > fittestPorcent && getIndividual(i).getFitness() < 1){
//                    fitest = getIndividual(i);
//                }
//            }
        }
        return fitest;
    }

    public int size(){
        return this.individuals.length;
    }

    public void saveIndividual(int index, Individuo individual){
        this.individuals[index] = individual;
    }
}
