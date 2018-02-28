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

    public Arduino(ArrayList<Equipamento> equipamentos, ArrayList<Integer> portas) {
        this.equipamentos = equipamentos;
        this.portas = portas;
        this.conectado = false;
        SerialPort[] portNames = SerialPort.getCommPorts();
        for(int i = 0; i < portNames.length; i++)
            System.out.println(portNames[i].getSystemPortName());
        chosenPort = SerialPort.getCommPort("COM5");
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
    }

    public boolean isConectado() {
        return conectado;
    }

    public void conecta(){

        if(chosenPort.openPort()) {
            this.conectado = true;
            Thread thread = new Thread(){
                @Override public void run() {
                    Scanner scanner = new Scanner(chosenPort.getInputStream());
                    while(scanner.hasNextLine()) {
                        try {
                            String line = scanner.nextLine();
//                            int number = Integer.parseInt(line);
                            if (line.length() == 9){
//                                System.out.println(line);
                                status = line;
                            }
                        } catch(Exception e) {}
                    }
                    scanner.close();
                }
            };
            thread.start();

        } else {
            // disconnect from the serial port
            chosenPort.closePort();
            this.conectado = false;
        }

    }

    public void equipamentoIsConnected(){
        try {
            for (int i = 0; i < this.status.length() ; i++) {
                if (status.charAt(i) == '1'){
//                    System.out.println("Equipamento " + i + " ligado.");
                    System.out.println(equipamentos.get(i).getNome() + " ::: LIGADO na porta: " + portas.get(i));
                }else {

                }
            }
        }catch (Exception e){

        }
    }
}
