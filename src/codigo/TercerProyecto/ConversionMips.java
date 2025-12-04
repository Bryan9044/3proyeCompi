/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo.TercerProyecto;
import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.ArrayList;
import java.util.Scanner;            
import java.util.regex.*;
/**
 *
 * @author dylan
 */
public class ConversionMips {
    public static ListaTemporales temporalesTresDirecciones = new ListaTemporales();
    public static int consecutivoTemporalEnteroMips = 0;
    public static int consecutivoTemporalFlotanteMips = 0;
    public static TablaDeVariablesMips tablaDeVariablesMips = new TablaDeVariablesMips("");
    public static boolean global = true;
    public static int posicionActualPila = 0; //Me indica en cual posición agregaría algo, comienza de cero y después de usarlo lo aumento
    public static int bytesPila = 0; //Me indica cuanta cantidad de bytes debo reservar al principio de la función y luego sacarlos de la pila
    public static String stringTemporal = "";
    
    public static void traducirArchivoCodigoTresDirecciones(String rutaC3D){
        File archivo = new File(rutaC3D); //Aqui tengo el archivo
        String accion;
        String linea;
        try (Scanner lector = new Scanner(archivo)){
            while (lector.hasNextLine()) {
                linea = lector.nextLine(); //En linea tengo almacenada la actual
                
                //Llamada a función externa que me retorna una acción para realizar en el switch
                accion = ConversionMips.accionLinea(linea);
                
                //Switch para definir que hago con esa línea
                switch(accion){
                    case "Asignacion multiple":
                        ConversionMips.asignacionMultiple(linea);
                        break;
                    case "Asignacion simple":
                        ConversionMips.asignacionSimple(linea);
                        break;
                    case "Creacion variable":
                        ConversionMips.creacionVariable(linea);
                        break;
                    default:
                        System.out.println("No se detectó acción");
                }
                
                //System.out.println(linea);
            }
            System.out.println("Resultado: " + stringTemporal);
        }catch (FileNotFoundException e) {
            System.out.println("Ocurrió un error leyendo el archivo.");
            e.printStackTrace();
         }
    }
    
    /* En esta función se pretenden colocar todas las posibles acciones que se pueden llevar a cabo con las líneas del código tres direcciones
        El propósito es por ejemplo, si tengo t1 = a *2; indicar que tipo de acción es en el retorno para esto utilizarlo en el switch de la función traducirArchivoCodigoTresDirecciones
        Esta función va a puro if para poder hacer casos diversos
    */
    public static String accionLinea(String linea){
        //Verificar si es una asignación
        if(linea.contains(" = ")){
            //Verificar si es una asignación simple ( t1 = 5) o una múltiple (t1 = 5*a)
                String[] partes = linea.split("=", 2);  //Para que separe con el primer = por aquelo del ==
                String parteDerecha = partes[1].trim();  
                
                //Verificar si contiene operador
                boolean contiene = linea.matches(".*(//|\\+|-|\\*|/|%|<|>|==|!=|<=|>=|@|~).*"); //Me falta ver la negación y disyunción
                if(contiene){
                    return "Asignacion multiple"; //Como contiene un operador entonces es una asignación múltiple
                }else{
                    return "Asignacion simple"; //Ya que no contiene un operador es una asignación simple
                }
        }
        //Verificar si no es la carga de un dato
        if(linea.startsWith("data_")){
            return "Creacion variable";
        }
        
        
        return "";
    }
    
    /*
        Maneja los casos de asignación múltiple como lo son t1 = a * 2;
        Se tienen los casos de:
            Asignación a temporal
            Asignación a variable
            Uso de temporal en operación
    */
    public static void asignacionMultiple(String linea){
        String[] partes = linea.split("=", 2);  //Para que separe con el primer = por aquelo del ==
        String parteIzquierda = partes[0].trim();
        String parteDerecha = partes[1].trim(); 
        String resultado = "";
        String operandoIzquierdo;
        String operandoDerecho;
        //Primero resuelvo los valores de la expresión
       
        //Obtener el operador
        String operador = ConversionMips.obtenerOperador(parteDerecha);
        
        //Ver si es una asignación a un temporal flotante o entero
        if(ConversionMips.esTemporalT(parteIzquierda) ){
             String[] operandos = ConversionMips.obtenerOperandos(linea); //Obtengo en una lista los dos operandos
            operandoIzquierdo = operandos[0];
            operandoDerecho = operandos[1];
            
            //System.out.println("Parte izquierda: " + parteIzquierda);
            
            //Realizar la operación con enteros
            int numeroTemporal = ConversionMips.extraerNumero(parteIzquierda);
            int consecutivoActualEntero = consecutivoTemporalEnteroMips;
            int numeroTemporalOperandoIzquierdo = consecutivoActualEntero - (numeroTemporal - ConversionMips.extraerNumero(operandoIzquierdo));
            int numeroTemporalOperandoDerecho = consecutivoActualEntero- (numeroTemporal - ConversionMips.extraerNumero(operandoDerecho));
            operandoIzquierdo = ConversionMips.obtenerRegistroOperadorEntero(numeroTemporalOperandoIzquierdo);
            operandoDerecho = ConversionMips.obtenerRegistroOperadorEntero(numeroTemporalOperandoDerecho);
            System.out.println("Consecutivo actual entero: " + consecutivoActualEntero);
            System.out.println("Numero temporal izquierdo : " + numeroTemporalOperandoIzquierdo);
            System.out.println("Numero temporal derecho: " + numeroTemporalOperandoDerecho);
            System.out.println("Operando izquierdo: " + operandoIzquierdo);
            System.out.println("Operando derecho: " + operandoDerecho);
            resultado = ConversionMips.realizarOperacionConEnteros(operandoIzquierdo, operandoDerecho, operador);
            consecutivoTemporalEnteroMips++;
            //Agregarlo a la función
            stringTemporal += resultado + "\n";
            return;
        }if(ConversionMips.esTemporalF(parteIzquierda)){
            //Realizar la operación con flotantes
            int numeroTemporal = ConversionMips.extraerNumero(parteIzquierda);
            int consecutivoActualEntero = consecutivoTemporalEnteroMips-1;
            int numeroTemporalOperandoIzquierdo = consecutivoActualEntero - ConversionMips.extraerNumero(operandoIzquierdo);
            int numeroTemporalOperandoDerecho = consecutivoActualEntero - ConversionMips.extraerNumero(operandoDerecho);
            operandoIzquierdo = ConversionMips.obtenerRegistroOperadorEntero(numeroTemporalOperandoIzquierdo);
            operandoDerecho = ConversionMips.obtenerRegistroOperadorEntero(numeroTemporalOperandoDerecho);
            resultado = ConversionMips.realizarOperacionConFlotantes(operandoIzquierdo, operandoDerecho, operador);
            //Agregarlo a la función
            stringTemporal += resultado + "\n";
            return;
        }
        //Ver si la parte izquierda es un identificador
        
        
        
    }
    
    /*
        Retorna los operandos de una entrada por ejemplo 5 * 2
    */
    private static String[] obtenerOperandos(String linea) {
   
    String[] partes = linea.split("=");

    String derecha = partes[1].replace(";", "").trim();

    return derecha.split("\\s*(//|\\+|-|\\*|/|%|<|>|==|!=|<=|>=|@)\\s*");
    }
    
    //Verificar si una línea es temporal t
    private static boolean esTemporalT(String linea){
        return linea.charAt(0) == 't' && Character.isDigit(linea.charAt(1));
    }
    
    private static boolean esTemporalF(String linea){
        return linea.charAt(0) == 'f' && Character.isDigit(linea.charAt(1));
    }
    
    private static String obtenerOperador(String linea){
        Pattern p = Pattern.compile("(//|\\+|-|\\*|/|%|<|>|==|!=|<=|>=|@)");
        Matcher m = p.matcher(linea);   
        if (m.find()) {
            String operador = m.group(1);
            return operador;
        }
        return "";
    }
    
    /*
        Realiza en el código MIPS la operación indicada con enteros.
        Los operandos son registros
    */
    private static String realizarOperacionConEnteros(String operandoIzquierdo, String operandoDerecho, String operador){
        String retorno = "";
        switch(operador){
            case "+":
                retorno = "add " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + operandoIzquierdo + ", " + operandoDerecho;
                //Aquí tendría que hacer la operación en la tabla de temporales del tres direcciones para poder saber en caso de que fuera para un arreglo el tamaño
                //retorno += "\nmove " + "$t" + consecutivoTemporalEnteroMips + ", $t0";
                ///consecutivoTemporalEnteroMips = 1;//Devuelvo el consecutivo a 1 para
                return retorno;
            case "-":
                retorno = "sub " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + operandoIzquierdo + ", " + operandoDerecho;
                //retorno += "\nmove " + "$t" + consecutivoTemporalEnteroMips + ", $t0";
                //consecutivoTemporalEnteroMips = 1;
                return retorno;
            case "//":
                retorno = "div " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + operandoIzquierdo + ", " + operandoDerecho;
                //retorno += "\nmove " + "$t" + consecutivoTemporalEnteroMips + ", $t0";
                //consecutivoTemporalEnteroMips = 1;
                return retorno;
            case "*":
                retorno = "mul " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + operandoIzquierdo + ", " + operandoDerecho;
                //retorno += "\nmove " + "$t" + consecutivoTemporalEnteroMips + ", $t0";
                //consecutivoTemporalEnteroMips = 1;
                return retorno;
            default:
                return "";
        }
        
    }
    
    private static String realizarOperacionConFlotantes(String operandoIzquierdo, String operandoDerecho, String operador){
        String retorno = "";
        switch(operador){
            case "+":
                retorno = "add.s $t" + consecutivoTemporalFlotanteMips + ", $" + operandoIzquierdo + ", $" + operandoDerecho;
                consecutivoTemporalFlotanteMips++;
                return retorno;
            case "-":
                retorno = "sub.s $t" + consecutivoTemporalFlotanteMips + ", $" + operandoIzquierdo + ", $" + operandoDerecho;
                consecutivoTemporalFlotanteMips++;
                return retorno;
            case "//":
                retorno = "div.s $t" + consecutivoTemporalFlotanteMips + ", $" + operandoIzquierdo + ", $" + operandoDerecho;
                consecutivoTemporalFlotanteMips++;
                return retorno;
            default:
                return "";
        }
        
    }
    
    private static void creacionVariable(String linea){
        String nombre;
        if(linea.startsWith("data_int")){
            nombre = linea.replace("data_int", "").replace(":", "").trim(); //Obtengo el nombre de la variables
            //Creo una variable de tipo entero. Debo de ver si es global o no
            
            tablaDeVariablesMips.globales.add(new VariableMips("int", "", nombre, posicionActualPila)); //Guardo
                
            //Ahora voy sumando a la cantidad de la pila
            posicionActualPila += 4;
                
            //Sumo a la cantidad que debo reservar
            bytesPila += 4;
            return;
                
        }if(linea.startsWith("data_float")){
            //Aquí debo de ver si al final los pongo en el .data
            nombre = linea.replace("data_float", "").replace(":", "").trim();
            tablaDeVariablesMips.globales.add(new VariableMips("float", "", nombre, posicionActualPila)); //Guardo
                
            //Ahora voy sumando a la cantidad de la pila
            posicionActualPila += 4;
                
            //Sumo a la cantidad que debo reservar
            bytesPila += 4;
            return;
        }if(linea.startsWith("data_bool")){
            nombre = linea.replace("data_bool", "").replace(":", "").trim();
            tablaDeVariablesMips.globales.add(new VariableMips("bool", "", nombre, posicionActualPila)); //Guardo
                
            //Ahora voy sumando a la cantidad de la pila
            posicionActualPila += 4;
                
            //Sumo a la cantidad que debo reservar
            bytesPila += 4;
            return;
        }
        
        //Falta ver string, char y los array
        
    }
    
    /*
        Asignación de por ejemplo a = t1. Falta ver otros casos como el del string, char, bool,
    */
    private static void asignacionSimple(String linea){
        String[] partes = linea.split("=");
        String parteIzquierda = partes[0].trim();   
        String parteDerecha   = partes[1].trim(); 
        parteDerecha = parteDerecha.replaceAll(";$", "");  // quita ;
        String resultado;
        
        //Caso donde asigno algo a un temporal entero, necesito verificar que es.
        if(ConversionMips.esTemporalT(parteIzquierda)){
            //Caso donde la parte derecha es un entero literal 
            if(parteDerecha.matches("[0-9]+")){
                resultado = "li " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + parteDerecha;
                consecutivoTemporalEnteroMips++;
                stringTemporal += resultado + "\n";
                temporalesTresDirecciones.temporales.add(new Temporal("int", parteDerecha, parteIzquierda)); //Para saber el valor de los temporales por las listas
                return;
            }
            //Caso donde la parte derecha es un booleano
            if(parteDerecha.equals("True") | parteDerecha.equals("False")){
                int valor = parteDerecha.equals("True") ? 1 : 0;
                //Cargo un 1 al temporal o un 0 si es falso
                resultado = "li " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + valor;
                consecutivoTemporalEnteroMips++;
                stringTemporal += resultado + "\n";
                temporalesTresDirecciones.temporales.add(new Temporal("bool", String.valueOf(parteDerecha), parteIzquierda)); //Para saber el valor de los temporales por las listas
                return;
            }
            //Caso donde la parte derecha es un identificador
            if(parteDerecha.matches("^_?[a-zA-Z][a-zA-Z0-9_]*$")){
                //Hay que hacer la descarga al temporal de la pila
                VariableMips variable = tablaDeVariablesMips.obtenerVariable(parteDerecha);
                resultado = "lw " + ConversionMips.obtenerRegistroOperadorEntero(consecutivoTemporalEnteroMips) + ", " + variable.posicionEnLaPila + "($sp)";
                consecutivoTemporalEnteroMips++;
                stringTemporal += resultado + "\n";
                return;
            }
            
        }
        //Caso donde la parte izquierda es una variable
        if(parteIzquierda.matches("^_?[a-zA-Z][a-zA-Z0-9_]*$")){
            //Caso donde la parte derecha es un temporal
            if(ConversionMips.esTemporalT(parteDerecha)){
                //Hay que hacer la carga del valor
                VariableMips variable = tablaDeVariablesMips.obtenerVariable(parteIzquierda);
                resultado = "sw " + ConversionMips.obtenerRegistroOperadorEntero((consecutivoTemporalEnteroMips-1)) + ", " + variable.posicionEnLaPila + "($sp)";

                stringTemporal += resultado + "\n";
                return;
                
            }
        }
    }
    
    /*
        Para tener más registros para las operaciones se van a usar primero los t y luego los s. Dependiendo del número retorno el que correspoda. Del 0 al 9 van los t y del 10 al 16 van los $s
    */
    private static String obtenerRegistroOperadorEntero(int numero){
        if(numero >= 0 && numero <10){
            return "$t" + numero;
        }
        return "$s" + (numero-10);
    }
    
    /*
        De por ejemplo t100 me retorna solo el número como entero
    */
    private static int extraerNumero(String registro) {
        return Integer.parseInt(registro.substring(1));
    }
    
    
}

