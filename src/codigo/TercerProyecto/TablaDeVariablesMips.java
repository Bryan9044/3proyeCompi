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
public class TablaDeVariablesMips {
    public String nombre; //Es para el nombre de la función o scope
    public ArrayList<VariableMips> variables = new ArrayList<>();
    public TablaDeVariablesMips anterior; //Tabla anterior en la jerarquía
    
    public TablaDeVariablesMips(String pNombre){
        this.nombre = pNombre;
        this.anterior = null;
    }
    
    //Agrego la variable a la lista
    public void agregarVariable(VariableMips pVariable){
        this.variables.add(pVariable);
    }
    
    public VariableMips obtenerVariable(String pNombre){
        for(int i = 0; i < variables.size(); i++){
            if(variables.get(i).nombre.equals(pNombre) ){
                return variables.get(i); //Retorno la variable
            }
        }
        return null; //Retorno null
    }
}
