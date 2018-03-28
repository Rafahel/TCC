package classes;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class OtimizacaoGenetica {
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private String resultado;
    private Boolean cancela;
    private boolean encontrado;
    private int diasRestantes;
    private Individuo individuoFinal;

    public OtimizacaoGenetica(ArrayList<Equipamento> equipamentos, double objetivo) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.resultado = "";
        this.cancela = false;
        this.diasRestantes = 30;

    }

    public OtimizacaoGenetica(ArrayList<Equipamento> equipamentos, double objetivo, int diasRestantes) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.resultado = "";
        this.cancela = false;
        this.diasRestantes = diasRestantes;

    }

    public void otimiza() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(equipamentos, objetivo, diasRestantes);
        Populacao populacao = new Populacao(100, equipamentos, this.objetivo, this.diasRestantes);
        populacao.initialize();

        int generationCounter = 0;
        this.encontrado = false;
        double maxFitness = 100;

        while (generationCounter <= 5000 && !this.cancela) {
            generationCounter++;
            Individuo melhor = populacao.getFitestIndividual();
            if (melhor.getFitness() >= maxFitness && melhor.getFitness() <= 100) {
                encontrado = true;
                break;
            }
            if (generationCounter % 1000 == 0 && maxFitness >= 90) {
//                System.out.print("Solução não encontrada, ja se passaram " + generationCounter + " geracoes, mudando fitness de " + maxFitness);
                maxFitness -= 2;
//                System.out.println(" para " + maxFitness);
            }
            populacao = algoritmoGenetico.evolvePopulacao(populacao);
        }

        if (encontrado) {
            Individuo fittest = populacao.getFitestIndividual();
            this.individuoFinal = fittest;
            this.resultado = "Geração: " + generationCounter + " - fitness: " +
                    fittest.getFitnessStr() + "% Resultado: " + new DecimalFormat("#.##").format(fittest.getResultado()) + "\n" +
                    fittest + "\n" + "Objetivo: " + this.objetivo + "\n" + "-------------------------\n";

            for (int i = 0; i < equipamentos.size(); i++) {
                this.resultado += (equipamentos.get(i).getNome() + " " + fittest.getGene(i) + " minutos diarios.\n");
            }
        }
    }

    public int[] getGenes(){
        int[] genes = new int[equipamentos.size()];
        for (int i = 0; i < equipamentos.size(); i++) {
            genes[i] = this.individuoFinal.getGene(i);
        }
        return genes;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

    public String getResultado() {
        return resultado;
    }
}
