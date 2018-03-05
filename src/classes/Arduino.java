package classes;

import com.fazecast.jSerialComm.SerialPort;

import java.util.ArrayList;
import java.util.Scanner;

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

    public Arduino(String porta, ArrayList<Equipamento> equipamentos, ArrayList<Integer> portas, double tarifa) {
        this.equipamentos = equipamentos;
        this.portas = portas;
        this.conectado = false;
        this.gastoAtual = 0;
        this.tarifa = tarifa;
        this.closeThread = false;
        this.wattsEquips = new int[equipamentos.size()];
        for (int i = 0; i < wattsEquips.length ; i++) {
            this.wattsEquips[i] = 0;
        }
//        SerialPort[] portNames = SerialPort.getCommPorts();
//        for (int i = 0; i < portNames.length; i++)
//            System.out.println(portNames[i].getSystemPortName());
        chosenPort = SerialPort.getCommPort(porta);
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
    }

    public boolean isConectado() {
        return conectado;
    }

    public void conecta() {

        if (chosenPort.openPort()) {
            this.conectado = true;
            Thread thread = new Thread() {
                @Override
                public void run() {
                    Scanner scanner = new Scanner(chosenPort.getInputStream());
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
                            closeThread = false;
                            chosenPort.closePort();
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

    public void equipamentoIsConnected() {
        try {
//            System.out.println("len de status = "  + this.status.length());
            for (int i = 0; i < this.status.length(); i++) {
                if (status.charAt(i) == '1') {
//                    System.out.println("Equipamento " + i + " ligado.");
                    System.out.println(equipamentos.get(i).getNome() + " ::: LIGADO na porta: " + portas.get(i) + " utilizando " + equipamentos.get(i).getWatts() + " watts");
                    this.gastoAtual += ((equipamentos.get(i).getKwhMin() * 30) * tarifa) / 60;
                    this.wattsEquips[i] = this.equipamentos.get(i).getWatts();
                    System.out.println("Gasto Atual:" + this.gastoAtual);
                }else {
                    this.wattsEquips[i] = 0;
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

    public void setCloseThread(boolean closeThread) {
        this.closeThread = closeThread;
    }

    public int getTotalWatts(){
        int total = 0;
        for (int wattsEquip : wattsEquips) {
            total += wattsEquip;
        }
        return total;
    }
}
