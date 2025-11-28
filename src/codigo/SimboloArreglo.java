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
public class SimboloArreglo extends Simbolo{
    private ArrayList<Simbolo> elementosArreglo = new ArrayList<>();;
    private String tamanio;
    public SimboloArreglo(String pSimbolo, String pTipo, String pTamanio, ArrayList<Simbolo> pElementosArreglo){
        super(pSimbolo, pTipo); //Creo el objeto padre
        this.tamanio = pTamanio;
        this.elementosArreglo = pElementosArreglo;
    }
    
    //Agrega un símbolo al final del arreglo
    public void agregarSimbolo(String pSimbolo, String pTipo){
        this.elementosArreglo.add(new Simbolo(pSimbolo, pTipo));
    }
}
