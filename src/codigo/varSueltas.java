/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

/**
 *
 * @author bryan
 */
public class varSueltas {
    public String nombre;
    public String tipo;
    public String temp;
    public String tempData;
    public int cantidadUsos;
    
    public varSueltas(String nombre,String tipo){
        this.nombre = nombre;
        this.tipo = tipo;
        this.temp = null;
        this.tempData = null;
        this.cantidadUsos = 0;
    }
}
