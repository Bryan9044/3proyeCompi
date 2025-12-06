/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo.TercerProyecto;

import java.util.ArrayList;

/**
 *
 * @author dylan
 */
public class Funcion {
    public int cantidadMemoriaRequeridaPila;
    public String nombre;
    public int cantidadMemoriaParametros;
    public ArrayList<String> tiposParametros = new ArrayList<>();
    
    public Funcion(String pNombre){
        this.nombre = pNombre;
        
        this.cantidadMemoriaRequeridaPila = 0;
        this.cantidadMemoriaParametros = 0;
    }
    
    
    
    @Override
    public String toString() {
        return "Nombre: " + nombre
                + ", Memoria requerida en pila: " + cantidadMemoriaRequeridaPila
                + ", Memoria de parámetros: " + cantidadMemoriaParametros;
    }

}
