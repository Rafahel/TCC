package classes;

import java.io.*;
import java.util.ArrayList;

public class Loader {
    public static ArrayList<Equipamento> load(File caminho) {
        ArrayList<Equipamento> equipamentos = new ArrayList<>();
        try {
            File arquivo = caminho;
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                if (linha.equals("END"))
                    break;
                String nome = linha.split("\\s+")[0].replace('$', ' ');
                int watts = Integer.parseInt(linha.split("\\s+")[1]);
                int tempoMin = Integer.parseInt(linha.split("\\s+")[3]);
                int tempMax = Integer.parseInt(linha.split("\\s+")[2]);
                equipamentos.add(new Equipamento(nome, watts, tempoMin, tempMax));
            }
            br.close();
            return equipamentos;
        } catch (FileNotFoundException e) {
            System.out.println(e);

        } catch (IOException e) {
            System.out.println(e);
        }
        return equipamentos;
    }
}
