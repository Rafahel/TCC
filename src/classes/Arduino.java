package classes;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Arduino {
    private SerialPort chosenPort;
    private String status;
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Integer> portas;
    private boolean conectado;
    private double gastoAtual;
    private double tarifa;
    boolean closeThread;
    private int[] wattsEquips;
    private int totalWatts;
    private Escritor escritor;
    private String diaAtual;
    private double kwTotal;

    public Arduino(String porta, ArrayList<Equipamento> equipamentos, double tarifa) {
        this.equipamentos = equipamentos;
        this.conectado = false;
        this.gastoAtual = 0;
        this.tarifa = tarifa;
        this.closeThread = false;
        this.wattsEquips = new int[equipamentos.size()];
        for (int i = 0; i < wattsEquips.length ; i++) {
            this.wattsEquips[i] = 0;
        }
        this.chosenPort = SerialPort.getCommPort(porta);
        this.chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        this.escritor = new Escritor(equipamentos, new File("C:\\Users\\" +
                System.getProperty("user.name") + "\\Documents\\HouseMon\\Logs_dia_" +
                getData().replace('/', '-') + ".txt"));
        this.diaAtual = getData();
        this.kwTotal = 0.00;
        this.reduz();
    }

    public boolean isConectado() {
        return this.conectado;
    }


    public void conecta() {

        if (this.chosenPort.openPort()) {
            this.conectado = true;
            Thread thread = new Thread() {
                @Override
                public void run() {

                    Scanner scanner = new Scanner(chosenPort.getInputStream());
                    OutputStream saida = chosenPort.getOutputStream();
                    while (scanner.hasNextLine()) {
                        try {

                            String line = scanner.nextLine();
//                            int number = Integer.parseInt(line);
                            if (line.length() == 9) {
//                                System.out.println(line);
                                status = line;
                                equipamentoIsConnected();
                            }

                        } catch (Exception e) {
                        }
                        if (closeThread){
                            chosenPort.closePort();
                            System.out.println("Desconectando arduino e fechando thread.");
                            break;
                        }

                    }
                    scanner.close();
                }
            };
            thread.start();

        } else {
            // disconnect from the serial port
            System.out.println("Desconectado");
            chosenPort.closePort();
            this.conectado = false;
        }

    }

    private void equipamentoIsConnected() {

        try {
//            System.out.println("len de status = "  + this.status.length());
            for (int i = 0; i < this.status.length(); i++) {
                if (status.charAt(i) == '1') {
//                    System.out.println("Equipamento " + i + " ligado.");
                    System.out.println(equipamentos.get(i).getNome() + " ::: LIGADO");
//                    this.escritor.geradorLogs(getDataHorario());
                    if (!this.equipamentos.get(i).isLigado()){
                        if (!diaAtual.equals(getData())){
                            this.mudaDataArquivo();
                            this.equipamentos.get(i).resetaTempoLigado();
                        }
                        equipamentos.get(i).setLigado(true);
                        System.out.println(getDataHorario());
                        escritor.geradorLogs(i, this.getDataHorario(), new DecimalFormat("#.##").format(this.gastoAtual));

                    }
                    this.gastoAtual += Calculadora.calculaGastoSegundo(equipamentos.get(i), tarifa);
                    kwTotal += (equipamentos.get(i).getKwhMin() / 60);
                    System.out.println("Total de KW gastos: " +  kwTotal);
                    this.wattsEquips[i] = this.equipamentos.get(i).getWatts();
                    System.out.println("Gasto Atual:" + this.gastoAtual);
                }else {
                    this.wattsEquips[i] = 0;
                    if(this.equipamentos.get(i).isLigado()){
                        if (!diaAtual.equals(getData())){
                            this.mudaDataArquivo();
                            this.equipamentos.get(i).resetaTempoLigado();
                        }
                        System.out.println(equipamentos.get(i).getNome() + " ::: DESLIGADO");
                        this.equipamentos.get(i).setLigado(false);
                        System.out.println(getDataHorario());
                        escritor.geradorLogs(i, this.getDataHorario(), new DecimalFormat("#.##").format(this.gastoAtual));
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    public double getGastoAtual() {
        return gastoAtual;
    }

    public String getStatus() {
        return status;
    }

    public void closeThread() {
        this.closeThread = true;
    }

    public int getTotalWatts(){
        int total = 0;
        for (int wattsEquip : this.wattsEquips) {
            total += wattsEquip;
        }
        return total;
    }

    private String getHorario(){
        Date date = new Date();
        return new SimpleDateFormat("hh:mm:ss a").format(date);
    }

    private String getDataHorario(){
        Date date = new Date();
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").format(date);
    }
    private String getData(){
        Date date = new Date();
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    private void mudaDataArquivo(){
        this.escritor = new Escritor(this.equipamentos,
                new File("C:\\Users\\" + System.getProperty("user.name") +
                        "\\Documents\\HouseMon\\Logs_dia_" +
                        getData().replace('/', '-') + ".txt"));
    }

    private void reduz(){
        Thread thread = new Thread() {

            @Override
            public void run() {
               while (true){
                   if (closeThread)
                       break;
                   try {
                       sleep(1000);
                       for (Equipamento e: equipamentos) {
                           if(e.isLigado()){
                               e.reduzTempoRestanteSegundo();
                               e.checkMinRestante();
                           }
                       }
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
            }
        };
        thread.start();
    }




}
