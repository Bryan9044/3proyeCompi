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
public class ListaFunciones {
    public ArrayList<Funcion> funciones = new ArrayList<>();
    public ListaFunciones(){}
    
    public Funcion obtenerFuncion(String nombre){
        for(Funcion funcion : funciones){
            if (funcion.nombre.equals(nombre)){
                return funcion;
            }
        }
        return null;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ListaFunciones:\n");

        for (Funcion f : funciones) {
            sb.append("  - ").append(f.toString()).append("\n");
        }

        return sb.toString();
    }
}
