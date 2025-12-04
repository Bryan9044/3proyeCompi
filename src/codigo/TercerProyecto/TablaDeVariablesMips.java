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
    public ArrayList<VariableMips> globales = new ArrayList<>();
    
    public TablaDeVariablesMips(String pNombre){
        this.nombre = pNombre;
 
    }
    
    //Agrego la variable a la lista
    public void agregarVariable(VariableMips pVariable){
        this.variables.add(pVariable);
    }
    
    public VariableMips obtenerVariable(String pNombre){

    // Buscar primero en variables locales
    for (int i = variables.size() - 1; i >= 0; i--) {
        if (variables.get(i).nombre.equals(pNombre)) {
            return variables.get(i);
        }
    }

    // Buscar en las globales
    for (int i = 0; i < globales.size(); i++) {
        if (globales.get(i).nombre.equals(pNombre)) {
            return globales.get(i);
        }
    }

    return null;
}
}
