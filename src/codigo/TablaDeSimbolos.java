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
public class TablaDeSimbolos {
    private String nombre;
    private ArrayList<Simbolo> simbolos = new ArrayList<>();
    private TablaDeSimbolos anterior;
    public String tipoFuncion = "";
    
    public TablaDeSimbolos(String pNombre){
        this.nombre = pNombre; 
        this.anterior = null;
    }
    
    //Solo para las funciones
    public TablaDeSimbolos(String pNombre, String pTipo){
        this.nombre = pNombre; 
        this.tipoFuncion = pTipo; 
    }
    
    public void agregarSimbolo(Simbolo pSimbolo){
        //Si el símbolo ya existe no se agrega, se reemplaza
        boolean agregar = true;
        for(int i = 0; i < simbolos.size(); i++){
            if(simbolos.get(i).getSimbolo().equals(pSimbolo.getSimbolo()) ){
                //El símbolo ya existe, se reemplaza
                simbolos.add(i, pSimbolo);
                agregar = false;
                break;
            }
        }
        //Si no se encuentra en la tabla, se agrega al final
        if(agregar){
            simbolos.add(pSimbolo);
        }
        
    }
    
    public void agregarSimbolo(SimboloArreglo pSimbolo){
        //Si el símbolo ya existe no se agrega, se reemplaza
        boolean agregar = true;
        for(int i = 0; i < simbolos.size(); i++){
            if(simbolos.get(i).getSimbolo().equals(pSimbolo.getSimbolo()) ){
                //El símbolo ya existe, se reemplaza
                //System.out.println("Se reemplaza el símbolo " + simbolos.get(i).getSimbolo() + " con el tipo " +  simbolos.get(i).getTipo() + " por el del tipo " + pSimbolo.getTipo());
                simbolos.add(i, pSimbolo);
                agregar = false;
                break;
            }
        }
        //Si no se encuentra en la tabla, se agrega al final
        if(agregar){
            simbolos.add(pSimbolo);
        }
        
    }
    
    public void setTablaAnterior(TablaDeSimbolos pTablaAnterior){
        this.anterior = pTablaAnterior;
    }
        
    public TablaDeSimbolos getTablaAnterior(){
        return this.anterior;
    }
        
    public String getNombre(){
        return this.nombre;
    }
    
    public int getCantidadDeSimbolos(){
        return this.simbolos.size();
    }
    
    public ArrayList<Simbolo> getSimbolos(){
        return this.simbolos;
    }
    
    
    
    //Verifica en la tabla de la instancia y en las anteriores si existe el identificador
    public Simbolo obtenerSimbolo(String pSimbolo){
        //Primero busco en la tabla actual
        Simbolo retorno = null;
        retorno = existeEnLaTabla(this.simbolos, pSimbolo);
        
        if(retorno != null){ return retorno;} //Lo retorno si ya lo encontré
        
        //Si no lo he encontrado busco en las demás tablas
        //Buscar en las tablas anteriores
        TablaDeSimbolos anteriorIteracion = this.anterior;
        while(anteriorIteracion != null){
            retorno = this.existeEnLaTabla(anteriorIteracion.simbolos, pSimbolo);
            if(retorno != null){ return retorno;} //Lo retorno si ya lo encontré
            anteriorIteracion = anteriorIteracion.getTablaAnterior();
        }
        
        return retorno; //Si nunca lo encuentra va a retornar null
    }
    
    //Itera la tabla de símbolos de la entrada y si encuentra el símbolo buscado lo retorna
    private Simbolo existeEnLaTabla(ArrayList<Simbolo> pSimbolos, String pSimbolo){
        for(Simbolo simbolo : pSimbolos){
            if(pSimbolo.equals(simbolo.getSimbolo())){
                return simbolo;
            }
        }
        return null;
    }
    
@Override 
    public String toString(){
        String resultado = "Nombre de la Tabla: " + nombre + "\n";
        for (Simbolo s : simbolos) {
            resultado += s.toString() + "\n";
            }
        return resultado;
    }
    
}
