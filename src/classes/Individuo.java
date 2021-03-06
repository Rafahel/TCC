package classes;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * O indivíduo é a representação de uma possível solução, nele temos a variavel genes que é onde se encontram os tempos
 * de uso de cada um dos equipamentos selecionados pelo usuário.
 */

public class Individuo {
    private int[] genes;
    private double fitness;
    private Random randomGenerator;
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private Double resultado;
    private int diasRestantes;
    private double tarifa;

    public Individuo(ArrayList<Equipamento> equipamentos, double objetivo, int diasRestantes, double tarifa) {
//        System.out.println("Individuo quantidade equip: " + equipamentos.size());
        this.genes = new int[equipamentos.size()];
        this.randomGenerator = new Random();
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.fitness = 0.00;
        this.resultado = 0.000;
        this.diasRestantes = diasRestantes;
        this.tarifa = tarifa;

    }

    public Individuo(double fitness, ArrayList<Equipamento> equipamentos, double objetivo) {
        this.fitness = fitness;
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
    }

    public void generateIndividual() {
        for (int i = 0; i < equipamentos.size(); i++) {
            int min = equipamentos.get(i).getMinUtilzacaoDiaria();
            int max = equipamentos.get(i).getMaxUtilzacaoDiaria();
//            int gene = minimum + randomGenerator.nextInt((maximum - minimum) + 1);
            int gene = randomGenerator.nextInt((max - min) + 1) + min;
            genes[i] = gene;

        }
    }

    public Double getFitness() {
        if (fitness == 0) {
            for (int i = 0; i < genes.length; i++) {
                this.resultado += (equipamentos.get(i).getKwhMin() * getGene(i) * this.diasRestantes) * this.tarifa;
            }
        }
        this.fitness = (resultado * 100) / this.objetivo;
//        System.out.println("FLAG " +  resultado + " * 100 / " + this.objetivo + " >> Porcentagem : " + fitness);
//        if (fitness > Constantes.MAXIMUM_FITNESS && fitness < 1)
//            System.out.println("FLAG " +  resultado + " * 100 / " + this.objetivo + " >> Porcentagem : " + fitness);
        return fitness;
    }

    public int getGene(int i) {
        return this.genes[i];
    }

    public void setGenes(int i, int value) {
        this.genes[i] = value;
        this.fitness = 0;
    }

    @Override
    public String toString() {
        String s = "Genes: {";

        for (int i = 0; i < equipamentos.size(); i++) {
            s += getGene(i);
            if (i < equipamentos.size() - 1)
                s += ", ";
        }
        return s + "}\nSolucao: " + new DecimalFormat("#.###").format(this.resultado) + " Fitness: " + new DecimalFormat("#.###").format(fitness) + " %";
    }

    public double getResultado() {
        return resultado;
    }

    public String getFitnessStr() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(fitness);
    }

    public String getCalculo() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.UNNECESSARY);
        return "" + resultado + " * 100 / " + this.objetivo + " = " + df.format(fitness);
    }

    public int[] getGenes(){
        return this.genes;
    }

}
