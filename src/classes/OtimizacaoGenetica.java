package classes;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Random;

public class OtimizacaoGenetica {
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private String resultado;
    private TextArea textArea;
    private Boolean cancela;
    public OtimizacaoGenetica(ArrayList<Equipamento> equipamentos, double objetivo, TextArea textArea, Boolean cancela) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.resultado = "";
        this.textArea = textArea;
        this.cancela = false;
    }

    public void otimiza(){
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(equipamentos, objetivo);
        Populacao populacao = new Populacao(100, equipamentos, this.objetivo);
        populacao.initialize();

        int generationCounter = 0;
        boolean encontrado = false;
        double maxFitness = 100;

        while (generationCounter <= 5000 && !this.cancela){
            generationCounter++;
            Individuo melhor = populacao.getFitestIndividual();
            if (melhor.getFitness() >= maxFitness && melhor.getFitness() <= 100 ){
                encontrado = true;
                break;
            }
            if (generationCounter % 1000 == 0 && maxFitness >= 95){
                System.out.print("Solução não encontrada, ja se passaram " + generationCounter + " geracoes, mudando fitness de " + maxFitness);
                maxFitness -= 0.1;
                System.out.println(" para " + maxFitness);
            }
            populacao = algoritmoGenetico.evolvePopulacao(populacao);
        }

        if (encontrado){
            Individuo fittest = populacao.getFitestIndividual();
            this.resultado = "Solução encontrada!\n" + "Geração: " + generationCounter + " - fitness: " +
                    fittest.getFitnessStr() + "% Resultado: " + fittest.getResultado() + "\n" +
                    fittest + "\n" + "Objetivo: " + this.objetivo + "\n";
            for (int i = 0; i < equipamentos.size() ; i++) {
                this.resultado +=("Utilizar o equipamento " + equipamentos.get(i).getNome() + " por " + fittest.getGene(i) + " minutos diarios.\n");
            }
            this.textArea.setText(this.resultado);
        }
        else {
            this.textArea.setText("Solução não encontrada");
        }
    }
}
