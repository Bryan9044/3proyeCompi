/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo.TercerProyecto;

/**
 * Esta clase es para simular que tengo variables en mips. Permitirá que por ejemplo si en mi tres direcciones tengo a = 3 simular esto aquí con valores en la pila para saber 
 * en que posición de dicha estructura está almacenado el valor.
 * La posición en la pila es tomando en cuenta que dentro del manejo del mips cuando se ejecute una función se reserva nueva memoria, es decir, queda al principio de la pila.
 * @author dylan
 */
public class VariableMips {
    public String tipo;
    public String valor;
    public String nombre;
    public int posicionEnLaPila; //Es absoluta, indica el corrimiento. Por ejemplo si es 4, eso quiere decir que el corrimiento es 4 y no que se encuentra en esa posición como si fuera una lista
    
    //Constructor
    public VariableMips(String pTipo, String pValor, String pNombre, int pPosicionEnLaPila){
        this.tipo = pTipo;
        this.valor = pValor;
        this.nombre = pNombre;
        this.posicionEnLaPila = pPosicionEnLaPila;
    }
}
