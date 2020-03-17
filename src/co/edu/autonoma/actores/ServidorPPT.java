/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.actores;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author nikof
 */
public class ServidorPPT {
    
    public static void main(String[] args){
        int serverPort = 9090;

        try
        {
            System.out.println("SERVIDOR: binding port");

            ServerSocket listenSocket = new ServerSocket(serverPort);

            while(true) 
            {
                System.out.println("SERVIDOR: esperando a una sesion de juego");

                Socket socketJ1 = listenSocket.accept();
                System.out.println("SERVIDOR: jugador 1 recibido " + socketJ1.getInetAddress().getHostName());
                
                Socket socketJ2 = listenSocket.accept();
                System.out.println("SERVIDOR: jugador 2 recibido " + socketJ2.getInetAddress().getHostName());

                SesionJuego sesion = new SesionJuego(socketJ1, socketJ2);

                sesion.start();
            }
        } catch(IOException e){
                System.out.println("Error connecting a client: " + e.getMessage());
        }
    
    }
    
}
