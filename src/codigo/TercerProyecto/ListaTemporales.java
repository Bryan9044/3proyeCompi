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
public class ListaTemporales {
    public ArrayList<Temporal> temporales = new ArrayList<>();
    
    public ListaTemporales(){}
    
    public Temporal buscarTemporal(String pNombre){
        for(int i = 0; i < temporales.size(); i++){
            if(temporal.get(i).nombre.equals(pNombre) ){
                return temporales.get(i); //Retorno la variable
            }
        }
        return null; //Retorno null
    }
}
