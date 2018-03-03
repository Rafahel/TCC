package classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    public void geraArquivo() {
        try {
            FileWriter fw = new FileWriter(caminho);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Equipamento e : equipamentos) {
                bw.append(e.toFileFormat());
                bw.newLine();
            }
            bw.append("END");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
