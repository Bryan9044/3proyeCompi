/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;
import java.io.*;
import java.util.HashMap;

/**
 *
 * @author bryan
 */



public class TraduccionMips {

    // Este método recibe la ruta del archivo C3D y lo traduce
    
    public static HashMap<String, varSueltas> almacenaVars = new HashMap<>();

    public static void traducirArchivoC3D(String rutaC3D) throws Exception {
        File archivo = new File(rutaC3D);

        // Primera pasada: contar usos
        BufferedReader br1 = new BufferedReader(new FileReader(archivo));
        String linea;
        agregarData("data: ");

        while ((linea = br1.readLine()) != null) {
            String pasadaData = LecturaLinea(linea);
        }
        br1.close(); // Cerrar el primer reader

        // Segunda pasada: traducir y restar usos
        BufferedReader br2 = new BufferedReader(new FileReader(archivo));
        while((linea = br2.readLine()) != null){
            System.out.println("C3D: " + linea);
            String prueba = traducirLinea(linea);
            System.out.println("MIPSP: " + prueba);
        }
        br2.close();

        // Mostrar tabla final
        System.out.println("Tabla de variables almacenadas:");
        for (String nombre : almacenaVars.keySet()) {
            varSueltas info = almacenaVars.get(nombre);
            System.out.println(" - " + nombre +
                " | tipo=" + info.tipo +
                " | temp=" + info.temp +
                " | tempData=" + info.tempData +
                " | cantidadUsos=" + info.cantidadUsos);
        }
        System.out.println("Final finalito");
        System.out.println(dataMips.toString());
        System.out.println(salidaMips.toString());
    }
    
public static StringBuilder salidaMips = new StringBuilder();
public static StringBuilder dataMips = new StringBuilder();    
public static int contadorF = 1;

public static void agregar(String linea) {
    salidaMips.append(linea).append("\n");
}

public static void agregarData(String linea){
    dataMips.append(linea).append("\n");
    
}

public static boolean tieneOperador(String c3d) {
    String operaciones = ".*(==|!=|<=|>=|>|<|\\+|\\-|\\*|/|%).*";


    if(c3d.matches(operaciones)){
        return true;
    }
    else {
        return false;
    }
}


public static boolean esRegistroT(String registro) {
    String[] registrosT = {"t1","t2","t3","t4","t5","t6","t7","t8","t9","t10"};
    for (String regis : registrosT){
        if (registro.equals(regis))
            return true;
    }
    return false;
}

public static boolean esRegistroF(String registro) {
    String[] registrosF = {"f1","f2","f3","f4","f5","f6","f7","f8","f9","f10"};
    for (String regis : registrosF){
        if (registro.equals(regis))
            return true;

    }
    return false;
}

public static String asignacionSencilla(String c3d){
    String[] separacion = c3d.split("=");
    String partIzquierda = separacion[0].trim();
    String partDerecha = separacion[1].replace(";", "").trim();
String FloatZero = "0\\.0";

String FloatPositiveDecimal = "0\\.[0-9]*[1-9]";

String FloatNegativeDecimal = "-" + FloatPositiveDecimal;

String FloatWithInteger = "[1-9][0-9]*\\.[0-9]*[1-9]";

String FloatWithIntegerZeroDecimal = "[1-9][0-9]*\\.0";

String FloatNegativeWithInteger = "-" + FloatWithInteger;

String FloatNegativeWithIntegerZero = "-" + FloatWithIntegerZeroDecimal;

String Digit = "[0-9]";
String Letter = "[a-zA-Z]";
String Underscore = "_";

String Identifier = "(" + Letter + "|" + Underscore + ")" +
                    "(" + Letter + "|" + Digit + "|" + Underscore + ")*";

String FloatLiteral =
    "(" + FloatZero + "|" + FloatPositiveDecimal + "|" + FloatNegativeDecimal + "|" +
        FloatWithInteger + "|" + FloatWithIntegerZeroDecimal + "|" +
        FloatNegativeWithInteger + "|" + FloatNegativeWithIntegerZero +
    ")";

    
    System.out.println("derecja" + partDerecha);
    
   // System.out.println(partIzquierda);
    if (esRegistroT(partIzquierda) && partDerecha.matches("[0-9]+")){
        agregar("li " + "$"+ partIzquierda + ", " + partDerecha);
            return "";
    }
    else if (esRegistroF(partIzquierda) && partDerecha.matches(FloatLiteral)) {

        String registroData = "val" + contadorF;
        agregarData(registroData + ": .float " + partDerecha);
        contadorF++;


        agregar("l.s $" + partIzquierda + ", " + registroData);

        return "";
    }


    else if(esRegistroT(partIzquierda) && esRegistroT(partDerecha)){
        agregar("move " + "$" + partIzquierda + ", $" + partDerecha);
        return "";
    }
    else if (esRegistroF(partIzquierda) && esRegistroF(partDerecha)){
        agregar("mov.s " + "$" + partIzquierda + ", $" + partDerecha);
        return "";
    } 
    else if (partIzquierda.matches(Identifier) && esRegistroT(partDerecha)){
        agregar("sw $" + partDerecha + ", " + partIzquierda);
        if (almacenaVars.containsKey(partIzquierda)) {
            almacenaVars.get(partIzquierda).cantidadUsos -= 2;
        }
        return"";
    }
    else if (partIzquierda.matches(Identifier) && esRegistroF(partDerecha)){
        System.out.println("PRUEBA"+ partIzquierda);
        System.out.println("PRUEBA2"+ partDerecha);
        return"";
    }
    else 
        return "Esto es una prueba";


}

public static String asignacionMultiple(String c3d){ 
    String[] separacion = c3d.split("=", 2); // Se puso el 2 para que en el split solo me deje 2 elementos
    // Porque en operaciones con == al primer = que detecta lo separa, ver si todo funciona normal
    // Pero tener cuidado por un posible error en el futuro 
    String partIzquierda = separacion[0].trim();
    String partDerecha = separacion[1].trim();
    
    String op = "";
    String operador1 = "";
    String operador2 = "";
    
    if (partDerecha.contains("==")) {
        op = "==";
        String[] partes = partDerecha.split("==");
        operador1 = partes[0].trim();
        operador2 = partes[1].trim();
    }
    else if (partDerecha.contains("!=")) {
        op = "!=";
        String[] partes = partDerecha.split("!=");
        operador1 = partes[0].trim();
        operador2 = partes[1].trim();
    }
    else if (partDerecha.contains(">=")) {
        op = ">=";
        String[] partes = partDerecha.split(">=");
        operador1 = partes[0].trim();
        operador2 = partes[1].trim();
    }
    else if (partDerecha.contains("<=")) {
        op = "<=";
        String[] partes = partDerecha.split("<=");
        operador1 = partes[0].trim();
        operador2 = partes[1].trim();
    }
    else if (partDerecha.contains(">")) {
        op = ">";
        String[] partes = partDerecha.split(">");
        operador1 = partes[0].trim();
        operador2 = partes[1].trim();
    }
    else if (partDerecha.contains("<")) {
        op = "<";
        String[] partes = partDerecha.split("<");
        operador1 = partes[0].trim();
        operador2 = partes[1].trim();
    }
    else {
    String[] partesOperadores = partDerecha.split("\\s*(//|\\+|-|\\*|/|%)\\s*");
    
    operador1 = partesOperadores[0].trim(); //Obtengo mi operador de la izquierda             
    operador2 = partesOperadores[1].trim(); //Obtengo mi operador de la derecha
    
    if(partDerecha.contains("//")){ op = "//";}
    else if (partDerecha.contains("/")){ op = "/";}
    else if (partDerecha.contains("+")){ op = "+";}
    else if (partDerecha.contains("-")){ op = "-";}
    else if (partDerecha.contains("*")){ op = "*";}
    else if (partDerecha.contains("%")){ op = "%";}
    
    
    
 
    }
    String temp1;
    String temp2;
   
// operador de mi izquierda
if (esRegistroF(operador1)) {
    varSueltas info = almacenaVars.get(operador1);
    if (info != null && info.tempData != null) {
        agregar("l.s $" + operador1 + ", " + info.tempData);
    }
}

if (esRegistroT(operador1) || esRegistroF(operador1)) {
    temp1 = operador1;
} else if (almacenaVars.get(operador1).temp != null) {
    temp1 = almacenaVars.get(operador1).temp;
    almacenaVars.get(operador1).cantidadUsos -= 1;
} else {
    temp1 = almacenaVars.get(operador1).tempData;
     almacenaVars.get(operador1).cantidadUsos -= 1;
}


// Operador de la derecha 
if (esRegistroF(operador2)) {
    varSueltas info2 = almacenaVars.get(operador2);
    if (info2 != null && info2.tempData != null) {
        agregar("l.s $" + operador2 + ", " + info2.tempData);
    }
}

if (esRegistroT(operador2) || esRegistroF(operador2)) {
    temp2 = operador2;
} else if (almacenaVars.get(operador2).temp != null) {
    temp2 = almacenaVars.get(operador2).temp;
     almacenaVars.get(operador2).cantidadUsos -= 1;
} else {
    temp2 = almacenaVars.get(operador2).tempData;
     almacenaVars.get(operador2).cantidadUsos -= 1;
}
    
    
     if (!esRegistroT(partIzquierda) && !esRegistroF(partIzquierda) && almacenaVars.containsKey(partIzquierda)) {
        almacenaVars.get(partIzquierda).cantidadUsos -= 1;
    }
       
    if(esRegistroT(partIzquierda)){
        if(op.equals("+")){
            agregar("add $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("-")){
            agregar("sub $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("*")){
            agregar("mul $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("//")){
            agregar("div $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("%")){
            agregar("rem $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals(">")){
            agregar("slt $" + partIzquierda + ", $" + temp2 + ", $" + temp1);
        }
        else if(op.equals("<")){
            agregar("slt $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("==")){
            agregar("beq $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("!=")){
            agregar("bne $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        
        
    } 
    if (esRegistroF(partIzquierda)){
        if(op.equals("+")){
            agregar("add.s $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("-")){
            agregar("sub.s $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("*")){
            agregar("mul.s $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        else if(op.equals("/")){
            agregar("div.s $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
        }
        // Consultar con Dylan porque este no devuelve nada, y quitarle la partIzquierda
        
        else if(op.equals(">")){
            agregar("c.le.s" + partIzquierda + ", $" + temp2 + ", $" + temp1);
        }
      //  else if(op.contains("%")){
      //      agregar("rem $" + partIzquierda + ", $" + temp1 + ", $" + temp2);
      //  } De momento comentado porque no existe jasjsjsj
        
        
    }
    return "toy probando";
}




public static String revisaDato(String linea){
        if(linea.startsWith("data_int")){
            String nombre = linea.replace("data_int", "").replace(":", "").trim();
            System.out.println("cola"+ nombre);
            almacenaVars.put(nombre, new varSueltas(nombre, "int"));
        }
        if(linea.startsWith("data_float")){
            String nombre = linea.replace("data_float", "").replace(":", "").trim(); // Dejo solo la variable
            almacenaVars.put(nombre, new varSueltas(nombre, "float")); // agrego la variable y el tipo de dato
        }
        if (linea.startsWith("data_paramInt")) {
            String nombre = linea.replace("data_paramInt", "").replace(":", "").trim();
            almacenaVars.put(nombre, new varSueltas(nombre, "int"));
        }

        if (linea.startsWith("data_paramFloat")) {
            String nombre = linea.replace("data_paramFloat", "").replace(":", "").trim();
            almacenaVars.put(nombre, new varSueltas(nombre, "float"));
        }
        if(linea.startsWith("data_bool")){
            String nombre = linea.replace("data_bool", "").replace(":", "").trim();
            almacenaVars.put(nombre, new varSueltas(nombre, "bool"));
        }
        if(linea.startsWith("data_char")){
            String nombre = linea.replace("data_char", "").replace(":", "").trim();
            almacenaVars.put(nombre, new varSueltas(nombre, "char"));
        }
        if(linea.startsWith("data_string")){
            String nombre = linea.replace("data_string", "").replace(":", "").trim();
            almacenaVars.put(nombre, new varSueltas(nombre, "string"));
        }
    return "";
}

    
    public static String traducirLinea(String c3d) {
        String titulo = "^inicio(_funcion(Int|Float|Void|Char|Bool|String)\\d+|\\s+principal):?";

        
        c3d = c3d.trim().replace(";", "");

        if(c3d.matches(titulo)) { 
            System.out.println("Soy izquierda: " + c3d); 
            agregar(c3d);
        }  
        
        if(!tieneOperador(c3d) && c3d.contains("=")) {
            return asignacionSencilla(c3d);
        }
        if(tieneOperador(c3d) && c3d.contains("=")){
            return asignacionMultiple(c3d);
        }
        
        return "Nuse";
    }
    

    
    public static String LecturaLinea(String linea){

        linea = linea.trim().replace(";", "");

        // Busco las que empiezan con data_
        if (linea.startsWith("data_")) {
            return revisaDato(linea);
        }

        // Buscamos que tengan = o asignación 
        if (!linea.contains("=")) {
            return "noASIGNO";
        }

        // Si tiene alguno de los simbolos quiere decir que es multiple 
        boolean esMultiple = linea.matches(".*(\\+|-|\\*|/|%|<|>|==|!=|<=|>=).*");

        if (esMultiple) {
            revisarMultiples(linea);
        } else {
            revisaVariable(linea);
        }

        return "ok";
    }





    public static String revisarMultiples(String linea){

        String[] partes = linea.split("=", 2); 
        String parteIzquierda = partes[0].trim();
        String parteDerecha = partes[1].trim();  

        // Solo sumo si no es ningún tipo de registro y si ya fue registada 
        // En ese caso aumento mis usos o el de esa var
        if (!esRegistroT(parteIzquierda) && !esRegistroF(parteIzquierda) && almacenaVars.containsKey(parteIzquierda)) {
            almacenaVars.get(parteIzquierda).cantidadUsos += 1;
        }

        //Aqui queda la parte multiple volvemos a hacer un split según el operador que encuentre
        String[] operandos = parteDerecha.split("\\s*(\\+|-|\\*|/|%|<|>|==|!=|<=|>=)\\s*");

        for (String op : operandos) {
            op = op.trim();

            // Si hay un numero se ignora
            if (op.matches("[0-9]+")){ 
                continue;
            }

            // Si hay registros ignoramos 
            if (esRegistroT(op) || esRegistroF(op)){ 
                continue;
            }
            
            // Si es una variable entonces aumentamos la cantidad de usos
            if (almacenaVars.containsKey(op)) {
                almacenaVars.get(op).cantidadUsos += 1;
            }
        }

        return "";
    }




    public static String revisaVariable(String linea){

        String[] separacion = linea.split("=", 2);
        String partIzquierda = separacion[0].trim();
        String partDerecha   = separacion[1].trim();

        // Si es algun registro no hacemos nada
        if (esRegistroT(partIzquierda) || esRegistroF(partIzquierda)) {
            return "";
        }

        // Si es una variable y no existe la metemos a la tabla
        if (!almacenaVars.containsKey(partIzquierda)) {
            almacenaVars.put(partIzquierda, new varSueltas(partIzquierda, "desconocido"));
            
        }

        // agarramos la key del hasmap
        varSueltas info = almacenaVars.get(partIzquierda);

        // aumentamos 2 porque ya en este punto paso una parte que aqui no se toma en cuenta
        info.cantidadUsos += 2;

        // agrgamos el temp
        if (esRegistroT(partDerecha) || esRegistroF(partDerecha)) {
            info.temp = partDerecha;
        }
        agregarData(partIzquierda + ": .word 0");

        return "";
    }

}
