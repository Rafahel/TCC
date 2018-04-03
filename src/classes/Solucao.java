package classes;

public class Solucao {
    private String solucao;
    private int[] tempos;
    private double objetivo;

    public Solucao(String solucao, int[] tempos, double objetivo) {
        this.solucao = solucao;
        this.tempos = tempos;
        this.objetivo = objetivo;
    }

    public void addIndexSolucao(int index){
        this.solucao = "Solução " + index + "\n" + this.solucao;
    }

    public String getSolucao() {
        return solucao;
    }

    public int[] getTempos() {
        return tempos;
    }

    public double getObjetivo() {
        return objetivo;
    }
}
