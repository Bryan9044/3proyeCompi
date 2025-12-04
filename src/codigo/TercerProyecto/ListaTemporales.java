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
            if(temporales.get(i).nombre.equals(pNombre) ){
                return temporales.get(i); //Retorno el temporal
            }
        }
        return null; //Retorno null
    }
}
