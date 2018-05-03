package classes;

public class Equipamento {

    private String nome;
    private int watts;
    private double kwh;
    private double kwhMin;
    private int minUtilzacaoDiaria;
    private int maxUtilzacaoDiaria;
    private boolean ligado;
    private int tempoOtimizado;
    private boolean otimizado;
    private int tempoRestante;
    private long tempoRestanteSegundos;
    private int tempoExcedido;
    private int tempoLigado;
    private int tempoUtilizado;

    public Equipamento(String nome, int watts, int minUtilzacaoDiaria, int maxUtilzacaoDiaria) {
        this.nome = nome;
        this.watts = watts;
        this.minUtilzacaoDiaria = minUtilzacaoDiaria;
        this.maxUtilzacaoDiaria = maxUtilzacaoDiaria;
//        this.horasParaMinutos();
        this.ligado = false;
        this.transformaWatts();
        this.otimizado = false;
        this.tempoOtimizado = 0;
        this.tempoRestante = 0;
        this.tempoExcedido = 0;
        this.tempoRestanteSegundos = 0;
        this.tempoUtilizado = 0;
        this.resetaTempoLigado();
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
        return nome + " " + this.watts + " " + this.maxUtilzacaoDiaria + " "
                + this.minUtilzacaoDiaria;
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
        transformaWatts();
    }

    public void setMinUtilzacaoDiaria(int minUtilzacaoDiaria) {
        this.minUtilzacaoDiaria = minUtilzacaoDiaria;
    }

    public void setMaxUtilzacaoDiaria(int maxUtilzacaoDiaria) {
        this.maxUtilzacaoDiaria = maxUtilzacaoDiaria;
    }

    public int getTempoOtimizado() {
        return tempoOtimizado;
    }

    public void setTempoOtimizado(int tempoOtimizado) {
        this.tempoOtimizado = tempoOtimizado;
        setTempoRestante();
    }

    public boolean isOtimizado() {
        return otimizado;
    }

    public void setOtimizado(boolean otimizado) {
        this.otimizado = otimizado;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante() {
        this.tempoRestante = this.tempoOtimizado;
    }

    public void reduzTempoRestante(){
        this.tempoRestante --;
    }

    public void addTempoRestante(int tempo){
        this.tempoRestanteSegundos += tempo;
    }

    public int getTempoExcedido() {
        return tempoExcedido;
    }

    public void setTempoExcedido(int tempoExcedido) {
        this.tempoExcedido = tempoExcedido;
    }

    public void somaTempoExedido(){
        this.tempoExcedido ++;
    }

    public void setTempoRestanteSegundos() {
        this.tempoRestanteSegundos = this.tempoRestante * 60;
    }

    public void reduzTempoRestanteSegundo(){
        this.tempoRestanteSegundos --;
    }

    public void checkMinRestante(){
        if (this.tempoRestanteSegundos != (this.tempoRestante * 60)){
            if (this.tempoRestanteSegundos % 60 == 0){
                this.reduzTempoRestante();
                this.addTempoLigado();
            }

        }
    }

    public int getTempoLigado() {
        return tempoLigado;
    }

    public void addTempoLigado() {
        this.tempoLigado ++;
    }

    public void resetaTempoLigado(){
        this.tempoLigado = 0;
    }

    public int getTempoUtilizado() {
        return tempoUtilizado;
    }

    public void setTempoUtilizado(int tempoUtilizado) {
        this.tempoUtilizado = tempoUtilizado;
    }
}
