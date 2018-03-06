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

    public void geradorLogs(int index, String horario){
        try {
            FileWriter fw = new FileWriter(caminho, true);
            BufferedWriter bw = new BufferedWriter(fw);
//            FileReader fr = new FileReader(caminho);
//            BufferedReader br = new BufferedReader(fr);
//            String line;
//            String text = "";
//            while ((line = br.readLine()) != null)
//                text += line;
            if (equipamentos.get(index).isLigado()){
                bw.append(equipamentos.get(index).getNome()).append(" LIGADO ").append(horario);
                bw.newLine();
            }else {
                bw.append(equipamentos.get(index).getNome()).append(" DESLIGADO ").append(horario);
                bw.newLine();
            }
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
