/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

import java.util.ArrayList;

/**
 *
 * @author dylan
 */
public class TablaDeFunciones {
    private ArrayList<TablaDeSimbolos> funciones = new ArrayList<>();
    
    public TablaDeFunciones(){
        
    }
    
    //Si la función no existe la agrega. No hay override
    public boolean agregarFuncion(String nombreFuncion, String tipo){
        boolean agregar = true;
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                agregar = false; //No se agrega la función
            }
        }
        
        if(agregar){
            //Se agrega la función
            funciones.add(new TablaDeSimbolos(nombreFuncion, tipo));
            
        }
        return agregar; //Se retorna true si la agregó y false si no para dar el error semántico ya que no hay override
    }
    
    public void agregarSimboloAFuncion(String nombreFuncion, Simbolo pSimbolo){
        
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                funcion.agregarSimbolo(pSimbolo); //Se encontró la función y se le agrega el símbolo
                break;
            }
        }
    }
    
    
    public boolean coincideLaCantidadDeParametros(String nombreFuncion, int cantidadParametros){
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                if(funcion.getCantidadDeSimbolos() == cantidadParametros){
                    return true;
                }else{
                    return false; //No coincide la cantidad de parámtetros
                }
            }
        }
        return false; //Si llega a salir del for significa que la función no existe
    }
    
    
    public boolean coincidenLosTiposDeLosParametros(String nombreFuncion, ArrayList<String> tipos){
        ArrayList<Simbolo> simbolos;
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                //Verificar por cada parámetro
                simbolos = funcion.getSimbolos();
                for(int i = 0; i < simbolos.size(); i++){
                    if(!(simbolos.get(i).getTipo().equals(tipos.get(i)))){
                        //Si no coinciden en alguna posición retorna false
                        return false;
                    }
                }
                return true; //Ya recorrió todos los parámetros de la función y no cayó en el if anterior entonces es true
                
            }
        }
        return false; 
    }
    
    public String obtenerListaDeTiposDeParametros (String nombreFuncion){
        String retorno = "";
        ArrayList<Simbolo> simbolos;
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                //Iterar los parámetros
                simbolos = funcion.getSimbolos();
                for(Simbolo simbolo : simbolos){
                    retorno += "," + simbolo.getTipo();
                }
                
            }
        }
        
        return retorno;
    }
    
    public boolean existeLaFuncion(String nombreFuncion){
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                return true;
                
            }
        }
        
        return false; //Si llega aquí es que no existe la función
    }
    
    public int cantidadDeParametros(String nombreFuncion){
        int retorno = -1;
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                //Iterar los parámetros
                retorno  = funcion.getCantidadDeSimbolos();
                break;
            }
        }
        return retorno;
    }
    
    public String obtenerTipoFuncion(String nombreFuncion){
        String retorno = "null";
        for(TablaDeSimbolos funcion : funciones){
            if (nombreFuncion.equalsIgnoreCase(funcion.getNombre())) {
                //Iterar los parámetros
                retorno  = funcion.tipoFuncion;
                break;
            }
        }
        
        return retorno;
    }
    
    
            
            
            
    
    
}
