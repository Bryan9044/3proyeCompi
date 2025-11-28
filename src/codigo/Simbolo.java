/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

/**
 *
 * @author dylan
 */
public class Simbolo {
    private String simbolo;
    private String tipo;
    
    public Simbolo(String pSimbolo, String pTipo){
        this.simbolo = pSimbolo;
        this.tipo = pTipo;
    }
    
    public String getSimbolo(){
        return simbolo;
    }
    
    public String getTipo(){
        return tipo;
    }
    
@Override
   public String toString(){
       return "Simbolo: " + simbolo + 
           "\tTipo: " + tipo ;
           
   }
}

