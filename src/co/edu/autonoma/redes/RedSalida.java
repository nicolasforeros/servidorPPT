/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.redes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nikof
 */
public class RedSalida {
    
    private DataOutputStream outJ1;
    private DataOutputStream outJ2;

    public RedSalida(DataOutputStream outJ1, DataOutputStream outJ2) {
        this.outJ1 = outJ1;
        this.outJ2 = outJ2;
    }
    
    public void enviarMensaje(String mensajeEnviar){
        
        try {
            outJ1.writeUTF(mensajeEnviar);
            outJ2.writeUTF(mensajeEnviar);
            
            outJ1.flush();
            outJ1.flush();
        } catch (IOException ex) {
            System.out.println("RED SALIDA => Error enviando mensaje: " + ex.getMessage());
        }
    }
    
}
