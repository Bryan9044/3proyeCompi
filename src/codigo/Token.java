/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

/**
 *
 * @author dylan
 */

public class Token {
   public String nombre;
   public String lexema;
   public int linea;
   public int columna;
   
   
   public Token(String pNombre, String pLexema, int pLinea, int pColumna){
       this.nombre = pNombre;
       this.lexema = pLexema;
       this.linea = pLinea;
       this.columna = pColumna;
   }
@Override
   public String toString(){
       return "Token: " + nombre + 
           "\tLexema: " + lexema + 
           "\tLínea: " + linea + 
           "\tColumna: " + columna;
   }
}
