/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;
import codigo.TercerProyecto.ConversionMips;
/**
 *
 * @author dylan
 */
public class Principal {
    public static void main(String[] args)throws Exception{
        String basePath = System.getProperty("user.dir");
        String rutaTxt = basePath + "/src/codigo/pruebaParser.txt";
        String rutaC3D = basePath + "/Codigo3Direcciones.txt";
       
        //"/src/codigo/ejemplo código 2.sintactico.base 3.txt"
        
        //JflexCup.generateFiles();
        JflexCup.probarParser(rutaTxt);
        ConversionMips.traducirArchivoCodigoTresDirecciones(rutaC3D);
        //TraduccionMips.traducirLinea(rutaC3D);
    }
    
}
