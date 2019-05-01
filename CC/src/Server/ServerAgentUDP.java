/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Common.PDU;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author joanacruz
 */
public class ServerAgentUDP extends Thread {
 
    private DatagramSocket socket;
    private boolean running;
    private byte[] receiveBuf;
    private byte[] sendBuf;

 
    public ServerAgentUDP() throws SocketException {
        this.socket = new DatagramSocket(7777);
        this.receiveBuf = new byte[256];
        this.sendBuf  = new byte[256];
    }
 
    public void run() {
        running = true;
        try {
            while (running) {
                DatagramPacket packetReceveid = new DatagramPacket(this.receiveBuf, this.receiveBuf.length);
                socket.receive(packetReceveid);
                
                PDU pdu = new PDU();
                
                pdu.byteToPDU(this.receiveBuf);
                System.out.println(pdu.getFlag());
                System.out.println(pdu.getSeqNumber());
                
                InetAddress address = packetReceveid.getAddress();
                int port = packetReceveid.getPort();
                
                String received = new String(packetReceveid.getData(), 0, packetReceveid.getLength());
                //System.out.println(received);
                
                if(received.matches("download [A-Za-z.]+")){
                    this.sendBuf = received.toUpperCase().getBytes();
                    pdu.setSeqNumber(pdu.intfromByte(this.sendBuf));
                    System.out.println("Enviei número de sequência" + pdu.getSeqNumber());
                    DatagramPacket packetSent = new DatagramPacket(this.sendBuf, this.sendBuf.length, address, port);
                    socket.send(packetSent);
                }
                if(received.matches("upload [A-Za-z.]+")){
                    
                }
                if(received.equals("exit")){
                    running = false;
                    socket.close();
                }
            }
        } catch (IOException ex) {
        }
    }
}