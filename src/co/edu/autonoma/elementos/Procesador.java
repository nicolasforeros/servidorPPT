/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.autonoma.elementos;

import java.io.IOException;
import java.io.StringWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author nikof
 */
public class Procesador {
    
    private Juego juego;
    
    private JSONParser parser;
    
    public Procesador(Juego juego){
        this.juego = juego;
    }
    
    public String procesar(){
        
        String respuesta = null;
        
        String[] estados = this.juego.getEstados(); //aca se espera a que se obtenga un estado primero
        
        String jugador1 = estados[0];
        String jugador2 = estados[1];
        
        JSONObject objJ1;
        JSONObject objJ2;
        
        try {
            objJ1 = (JSONObject)parser.parse(jugador1);
            objJ2 = (JSONObject)parser.parse(jugador2);
        } catch (ParseException ex) {
            System.out.println("Error procesando los mensajes. " + ex.getMessage() );
            return null;
        }
        
        int jugada1 =(int) (long)objJ1.get("jugada");
        int jugada2 =(int) (long)objJ2.get("jugada");
        
        if(jugada1==Juego.TERMINAR || jugada2==Juego.TERMINAR){
            respuesta = "cerrar";
        }else{
            if(jugada1==Juego.NUEVA_PARTIDA && jugada2==Juego.NUEVA_PARTIDA ){
                respuesta = this.realizarEstadoPreparado(objJ1,objJ2);
            }else{
                if( (jugada1==Juego.NUEVA_PARTIDA && jugada2==Juego.INICIO) || 
                        (jugada1==Juego.INICIO && jugada2==Juego.NUEVA_PARTIDA) ){
                    respuesta = this.realizarEstadoNoPreparado(objJ1,objJ2);
                }else{
                    if( (jugada1!=Juego.NUEVA_PARTIDA) && (jugada2==Juego.NUEVA_PARTIDA)){
                        respuesta = this.realizarEstadoJ1OK_J2NO(objJ1,objJ2);
                    }else{
                        if( (jugada1==Juego.NUEVA_PARTIDA) && (jugada2!=Juego.NUEVA_PARTIDA)){
                            respuesta = this.realizarEstadoJ1NO_J2OK(objJ1,objJ2);
                        }else{
                            if( (jugada1!=Juego.NUEVA_PARTIDA) && (jugada2!=Juego.NUEVA_PARTIDA)){
                                respuesta = this.jugar(objJ1,objJ2); //en esta se mandan las jugadas para saber resultados
                            }
                        }
                    }
                }
            }
        
        }
            
        return respuesta;
    }

    private String realizarEstadoPreparado(JSONObject objJ1, JSONObject objJ2) {
        
        JSONObject obj = new JSONObject();
        
        String jugador1 = (String)objJ1.get("jugador");
        String jugador2 = (String)objJ2.get("jugador");
        
        obj.put("jugador1", jugador1);
        obj.put("jugador2", jugador2);
        obj.put("estado", Juego.ESTADO_PREPARADO);
        
        /////////////////////////////////////////////////
        
        try {
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            
            String jsonText = out.toString();
            return jsonText;
        } catch (IOException ex) {
            System.out.println("PROCESADOR => Error realizando el mensaje de estado preparado: " + ex.getMessage());
        }
        
        return null;
    }

    private String realizarEstadoNoPreparado(JSONObject objJ1, JSONObject objJ2) {
    
        JSONObject obj = new JSONObject();
        
        String jugador1 = (String)objJ1.get("jugador");
        String jugador2 = (String)objJ2.get("jugador");
        
        obj.put("jugador1", jugador1);
        obj.put("jugador2", jugador2);
        obj.put("estado", Juego.ESTADO_NO_PREPARADO);
        
        /////////////////////////////////////////////////
        
        try {
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            
            String jsonText = out.toString();
            return jsonText;
        } catch (IOException ex) {
            System.out.println("PROCESADOR => Error realizando el mensaje de estado NO preparado: " + ex.getMessage());
        }
        
        return null;
    }

    private String realizarEstadoJ1OK_J2NO(JSONObject objJ1, JSONObject objJ2) {

        JSONObject obj = new JSONObject();
        
        String jugador1 = (String)objJ1.get("jugador");
        String jugador2 = (String)objJ2.get("jugador");
        
        obj.put("jugador1", jugador1);
        obj.put("jugador2", jugador2);
        obj.put("estado", Juego.ESTADO_J1OK_J2NO);
        
        /////////////////////////////////////////////////
        
        try {
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            
            String jsonText = out.toString();
            return jsonText;
        } catch (IOException ex) {
            System.out.println("PROCESADOR => Error realizando el mensaje de estado J1 OK J2 NO: " + ex.getMessage());
        }
        
        return null;
    }

    private String realizarEstadoJ1NO_J2OK(JSONObject objJ1, JSONObject objJ2) {

        JSONObject obj = new JSONObject();
        
        String jugador1 = (String)objJ1.get("jugador");
        String jugador2 = (String)objJ2.get("jugador");
        
        obj.put("jugador1", jugador1);
        obj.put("jugador2", jugador2);
        obj.put("estado", Juego.ESTADO_J1NO_J2OK);
        
        /////////////////////////////////////////////////
        
        try {
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            
            String jsonText = out.toString();
            return jsonText;
        } catch (IOException ex) {
            System.out.println("PROCESADOR => Error realizando el mensaje de estado J1 NO J2 OK: " + ex.getMessage());
        }
        
        return null;
    }

    private String jugar(JSONObject objJ1, JSONObject objJ2) {
        
        JSONObject obj = new JSONObject();
        
        String jugador1 = (String)objJ1.get("jugador");
        String jugador2 = (String)objJ2.get("jugador");
        
        int jugada1 =(int) (long)objJ1.get("jugada");
        int jugada2 =(int) (long)objJ2.get("jugada");
        
        String ganador = "empate";
        
        if (jugada1!=jugada2) {
            if (jugada1==Juego.PIEDRA && jugada2 == Juego.PAPEL) ganador = jugador2;
            
            if (jugada1==Juego.PIEDRA && jugada2 == Juego.TIJERA) ganador = jugador1;
            
            if (jugada1==Juego.PAPEL && jugada2 == Juego.PIEDRA) ganador = jugador1;
            
            if (jugada1==Juego.PAPEL && jugada2 == Juego.TIJERA) ganador = jugador2;
            
            if (jugada1==Juego.TIJERA && jugada2 == Juego.PIEDRA) ganador = jugador2;
            
            if (jugada1==Juego.TIJERA && jugada2 == Juego.PAPEL) ganador = jugador1;   
        }
        
        obj.put("jugador1", jugador1);
        obj.put("jugador2", jugador2);
        obj.put("jugada1", jugada1);
        obj.put("jugada2", jugada2);
        obj.put("estado", Juego.ESTADO_J1OK_J2OK);
        obj.put("ganador", ganador);

        /////////////////////////////////////////////////
        
        try {
            StringWriter out = new StringWriter();
            obj.writeJSONString(out);
            
            String jsonText = out.toString();
            return jsonText;
        } catch (IOException ex) {
            System.out.println("PROCESADOR => Error realizando el mensaje de estado J1 NO J2 OK: " + ex.getMessage());
        }
        
        return null;
        
    }
}
