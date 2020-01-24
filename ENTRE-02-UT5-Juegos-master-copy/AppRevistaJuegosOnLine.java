/**
 * Punto de entrada a la aplicación
 * 
 * @author - Fermin Lanzas
 */
public class AppRevistaJuegosOnLine 
{
	public static void main(String[] args)
	{
		if (args.length < 2 || args.length > 2){
            System.out.println("Error en argumentos\nSintaxis: java AppRevistaJuegosOnLine <nombre> <n>");
        }else{  
            RevistaOnLineJuegos revista = new RevistaOnLineJuegos(args[0], Integer.parseInt(args[1]));
            revista.leerDeFichero();
            System.out.println(revista.toString());
        }	 
	}
}
