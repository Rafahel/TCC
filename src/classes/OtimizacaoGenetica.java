package classes;
import java.text.DecimalFormat;
import java.util.ArrayList;

/***
 * Esta classe realiza a preparação da população e dos indivíduos para o uso do do Algoritmo Genético, assim como o
 * controle da população e o limite de iterações.
 */

public class OtimizacaoGenetica {
    private ArrayList<Equipamento> equipamentos;
    private double objetivo;
    private String resultado;
    private Boolean cancela;
    private boolean encontrado;
    private int diasRestantes;
    private Individuo individuoFinal;
    private double tarifa;
    private Solucao solucao;

    public OtimizacaoGenetica(ArrayList<Equipamento> equipamentos, double objetivo, double tarifa) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.resultado = "";
        this.cancela = false;
        this.diasRestantes = 30;
        this.tarifa = tarifa;
        this.solucao = null;

    }

    public OtimizacaoGenetica(ArrayList<Equipamento> equipamentos, double objetivo, int diasRestantes, double tarifa) {
        this.equipamentos = equipamentos;
        this.objetivo = objetivo;
        this.resultado = "";
        this.cancela = false;
        this.diasRestantes = diasRestantes;
        this.tarifa = tarifa;

    }

    public void otimiza() {
        AlgoritmoGenetico algoritmoGenetico = new AlgoritmoGenetico(this.equipamentos, this.objetivo, this.diasRestantes, this.tarifa);
        Populacao populacao = new Populacao(50, this.equipamentos, this.objetivo, this.diasRestantes, this.tarifa);
        populacao.initialize();

        int generationCounter = 0;
        this.encontrado = false;
        double maxFitness = 99;

        while (generationCounter <= 5000 && !this.cancela) {
            generationCounter++;
            Individuo melhor = populacao.getFitestIndividual();
            if (melhor.getFitness() >= maxFitness && melhor.getFitness() <= 100) {
                this.encontrado = true;
                break;
            }
            if (generationCounter % 1000 == 0 && maxFitness >= 89) {
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

    public Solucao getSolucao(){
        this.solucao = new Solucao(this.resultado, this.individuoFinal.getGenes(), this.objetivo);
        return solucao;
    }

    public boolean isEncontrado() {
        return encontrado;
    }
}
