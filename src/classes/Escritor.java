package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Escritor {

    private ArrayList<Equipamento> equipamentos;
    private File caminho;

    public Escritor(ArrayList<Equipamento> equipamentos, File caminho) {
        this.equipamentos = equipamentos;
        this.caminho = caminho;
    }

    public Escritor(File local){
        this.caminho = local;
    }

    public void geraArquivo() {
        try {
            FileWriter fw = new FileWriter(caminho);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Equipamento e : equipamentos) {
                System.out.println(e.toFileFormat());
                bw.append(e.toFileFormat());
                bw.newLine();
            }
            bw.append("END");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void geradorLogs(int index, String horario, String gastoAtual){
        try {
            FileWriter fw = new FileWriter(caminho, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if (equipamentos.get(index).isLigado()){
                bw.append(equipamentos.get(index).getNome()).append(" LIGADO ").append(horario).append(" Tempo Restante: ").append(Integer.toString(equipamentos.get(index).getTempoRestante()))
                        .append(" Gasto total: ").append("R$").append(gastoAtual);
                bw.newLine();
            }else {
                bw.append(equipamentos.get(index).getNome()).append(" DESLIGADO ").append(horario).append(" Tempo Restante: ").append(Integer.toString(equipamentos.get(index).getTempoRestante()))
                        .append(" Gasto total: ").append("R$").append(gastoAtual);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void geradorLogSimulador(String caminho, int dia, ArrayList<Equipamento> equipamentos){
        try {
            FileWriter fw = new FileWriter(caminho, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append("\t\t\t\t\tDIA").append(Integer.toString(dia));
            bw.newLine();
            for (Equipamento e: equipamentos) {
                bw.append(e.getNome()).append(" TEMPO OTIMIZADO ").append(Integer.toString(e.getTempoOtimizado())).append(" TEMPO UTILIZADO: ")
                        .append(Integer.toString(e.getTempoUtilizado())).append(" TEMPO EXCEDIDO: ").append(Integer.toString(e.getTempoExcedido()));;
                bw.newLine();
            }
            bw.append("----------------------------------------------------------------------------------");
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void salvaOtimizacao(String nome, String otimizacao){
        try {
            FileWriter fw = new FileWriter(caminho + nome );
            BufferedWriter bw = new BufferedWriter(fw);
            String[] o = otimizacao.split("\n");
            for (String s: o) {
                bw.append(s);
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
