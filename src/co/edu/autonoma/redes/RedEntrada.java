/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.redes;

import co.edu.autonoma.elementos.Juego;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikof
 */
public class RedEntrada extends Thread {
    
    public static int JUGADOR_1 = 1;
    public static int JUGADOR_2 = 2;
    
    private Juego juego;
    private DataInputStream in;
    private int jugador;
    
    public RedEntrada( Juego juego, DataInputStream in, int jugador){
        
        this.juego = juego;
        this.in = in;
        this.jugador = jugador;
        
    }
    
    @Override
    public void start(){
        
        String mensajeRecibido;
        
        while(true){
            
            
            System.out.println("RED ENTRADA => Esperando informacion del jugador " + this.jugador);
            
            try {
                mensajeRecibido = in.readUTF();
            } catch (IOException ex) {
                System.out.println("RED ENTRADA => Error de IO, en lectura jugador " + this.jugador +": " + ex.getMessage());
                mensajeRecibido="cerrar";
            }
            
            if(this.jugador==RedEntrada.JUGADOR_1){
                juego.setEstadoJ1(mensajeRecibido);
            }else{
                if(this.jugador==RedEntrada.JUGADOR_2){
                    juego.setEstadoJ2(mensajeRecibido);
                }
            }
        }
    
    }
    
}
