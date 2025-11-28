/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

/**
 *
 * @author dylan
 */
public class Result {
    public String valor;
    public String tipo;
    public int linea;
    public int columna;
    
    public Result(String pValor, String pTipo, int pLinea, int pColumna){
        this.valor = pValor;
        this.tipo = pTipo;
        this.linea = pLinea;
        this.columna = pColumna;
    }
    
    @Override
    public String toString() {
        return String.format("Result{valor='%s', tipo='%s', linea=%d, columna=%d}", 
                             valor, tipo, linea, columna);
    }
}
