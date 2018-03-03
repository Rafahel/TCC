package classes;

public class Equipamento {

    private String nome;
    private int watts;
    private double kwh;
    private double kwhMin;
    private int minUtilzacaoDiaria;
    private int maxUtilzacaoDiaria;
    private boolean ligado;

    public Equipamento(String nome, int watts, int minUtilzacaoDiaria, int maxUtilzacaoDiaria) {
        this.nome = nome;
        this.watts = watts;
        this.minUtilzacaoDiaria = minUtilzacaoDiaria;
        this.maxUtilzacaoDiaria = maxUtilzacaoDiaria;
//        this.horasParaMinutos();
        this.ligado = false;
        this.transformaWatts();
    }

//    public void horasParaMinutos(){
//        this.minUtilzacaoDiaria = this.minUtilzacaoDiariaHoras * 60;
//        this.maxUtilzacaoDiaria = this.maxUtilzacaoDiariaHoras * 60;
//    }

    public void transformaWatts() {
        this.kwh = this.watts / 1000.0;
        this.kwhMin = this.kwh / 60.0;
    }

    public String getNome() {
        return nome;
    }

    public int getWatts() {
        return watts;
    }

    public double getKwh() {
        return kwh;
    }

    public double getKwhMin() {
        return kwhMin;
    }

    public int getMinUtilzacaoDiaria() {
        return minUtilzacaoDiaria;
    }

    public int getMaxUtilzacaoDiaria() {
        return maxUtilzacaoDiaria;
    }

    public boolean isLigado() {
        return ligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    public String toFileFormat() {
        String nome = this.nome.replace(' ', '$');
        return nome + " " + this.watts + " " + this.minUtilzacaoDiaria + " "
                + this.maxUtilzacaoDiaria;
    }

    @Override
    public String toString() {
        return "Equipamento{" +
                "nome='" + nome + '\'' +
                ", kwh=" + kwh +
                ", minUtilzacaoDiaria=" + minUtilzacaoDiaria +
                ", maxUtilzacaoDiaria=" + maxUtilzacaoDiaria +
                ", ligado=" + ligado +
                '}';
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setWatts(int watts) {
        this.watts = watts;
    }

    public void setMinUtilzacaoDiaria(int minUtilzacaoDiaria) {
        this.minUtilzacaoDiaria = minUtilzacaoDiaria;
    }

    public void setMaxUtilzacaoDiaria(int maxUtilzacaoDiaria) {
        this.maxUtilzacaoDiaria = maxUtilzacaoDiaria;
    }
}
