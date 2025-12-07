/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo.TercerProyecto;

/**
 *
 * @author dylan
 */
public class VariableMipsString extends VariableMips{
    public String etiquetaData; //Se almacena en el .data, entonces me indica cual es el nombre de la etiqueta que almacena el valor
    public int version = 0; //Por defecto la versión 0. La aumento cuando asigno un nuevo valor
    
    public VariableMipsString(String pTipo, String pValor, String pNombre, int pPosicionEnLaPila, String pEtiquetaData){
        super(pTipo, pValor, pNombre, pPosicionEnLaPila);
        this.etiquetaData = pEtiquetaData;
    }
}
