/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Common.PDU;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author joanacruz
 */
public class ClientMain {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        
        ClientAgentUDP client = new ClientAgentUDP(5000);
        client.start();
        String input;
        
        try {
            PDU connection = new PDU();
            String conn = "Connection establish";
            connection.setDataPacket(conn.getBytes());
            connection.setSeqNumber(3);
            connection.setFlag(0);
            client.sendPDU(connection);
            do {
                System.out.print("JOE > ");
                input = scanner.nextLine();
                //client.sendMessage(input);
                scanner.nextLine();          
            } while (!input.equals("exit"));
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
