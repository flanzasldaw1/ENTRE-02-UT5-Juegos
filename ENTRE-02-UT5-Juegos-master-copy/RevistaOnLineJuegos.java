import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
/**
 * La clase representa a una tienda on-line en la
 * que se publican los juegos que se van lanzando al mercado
 * 
 * Un objeto de esta clase guarda en un array los juegos 
 *
 * @author - Fermin Lanzas
 */
public class RevistaOnLineJuegos 
{
    private String nombre;
    private Juego[] juegos;
    private int total;

    /**
     * Constructor  
     * Crea el array de juegos al tama�o m�ximo indicado por la constante
     * e inicializa el resto de atributos
     */
    public RevistaOnLineJuegos(String nombre, int n) {
        this.nombre = nombre;
        total = 0;
        juegos = new Juego[n]; 
    }

    /**
     * Devuelve true si el array est� completo, false en otro caso
     */
    public boolean estaCompleta() {
        return total == juegos.length;
    }

    /**
     *    A�ade un nuevo juego solo si el array no est� completo y no existe otro juego
     *    ya con el mismo nombre.  Si no se puede a�adir se muestra los mensajes adecuados 
     *    (diferentes en cada caso)
     *    
     *    El juego se a�ade de tal forma que queda insertado en orden alfab�tico de t�tulo
     *    (de menor a mayor)
     *     !!OJO!! No hay que ordenar ni utilizar ning�n algoritmo de ordenaci�n
     *    Hay que insertar en orden 
     *    
     */
    public void add(Juego juego) {
        if (!estaCompleta() && existeJuego(juego.getTitulo()) < 0)
        {
            // buscar hueco mientras se desplaza
            int i = total - 1;
            while (i >= 0 && juegos[i].getTitulo().compareTo(juego.getTitulo()) < 0)
            {
                juegos[i + 1] = juegos[i];
                i -- ;
            }
            juegos[i + 1] = juego; // insertar el valor
            total ++; // incrementamos el n� de elementos en la lista
        }
    }

    /**
     * Efect�a una b�squeda en el array del juego cuyo titulo se
     * recibe como par�metro. Es ndiferente may�sculas y min�sculas
     * Si existe el juego devuelve su posici�n, si no existe devuelve -1
     */
    public int existeJuego(String titulo) {
        for (int i = 0; i < total; i++){
            if (juegos[i].getTitulo().equalsIgnoreCase(titulo)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Representaci�n textual de la revista
     * Utiliza StringBuilder como clase de apoyo.
     * Se incluye el nombre de la  revista on-line.
     * (Ver resultados de ejecuci�n)
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\nLos mejores juegos en nuestra revista " + nombre +
            " (" + total + " juegos)\n");
        for (int i = 0; i < total; i++){
            str.append("\n" +juegos[i].getTitulo() + "\n" + "Genero: " +
                juegos[i].getGenero() + "| Lanzamiento: " + juegos[i].getYear() + 
                "\nValoraci�n (" + juegos[i].getVotos() + " votos): " + 
                juegos[i].getValoracionMedia() + "\n--------------------");
        }
        return str.toString();
    }

    /**
     *  Se punt�a el juego de t�tulo indicado con 
     *  la puntuaci�n recibida como par�metro. 
     *  La puntuaci�n es un valor entre 1 y 10 (asumimos esto como correcto)
     *  Si el juego no existe se muestra un mensaje en pantalla
     */
    public void puntuar(String titulo, int puntuacion) {
        int pos = existeJuego(titulo);
        if(pos >= 0 ){
            juegos[pos].puntuar(puntuacion);
        }else{
            System.out.println("Error al puntuar el juego");    
        }
    }

    /**
     * Devuelve un array con los nombres de los juegos 
     * con una valoraci�n media mayor a la indicada  
     * 
     * El array se devuelve todo en may�sculas y ordenado ascendentemente
     */
    public String[] valoracionMayorQue(double valoracion) {
        String[] titulos = new String[total];
        int pos = 0; 
        for (int i = 0; i < total; i++){
            if (juegos[i].getValoracionMedia() > valoracion){
                titulos[pos] = juegos[i].getTitulo();
                pos++;
            }
        }
        return Arrays.copyOf(titulos, pos);
    }

    /**
     * Borrar los juegos del g�nero indicado devolviendo
     * el n� de juegos borradas
     */
    public int borrarDeGenero(Genero genero) {
        int k = 0;
        for (int i = total - 1; i >= 0; i--)
        {
            if (juegos[i].getGenero().equals(genero))
            {
                for (int j = i + 1; j < total; j++)
                {
                    juegos[j - 1] = juegos[j];
                }
                total--;
                k++;
            }
        }
        return k;
    }

    /**
     * Lee de un fichero de texto los datos de los juegos
     * con ayuda de un objeto de la  clase Scanner
     * y los guarda en el array. 
     */
    public void leerDeFichero() {
        Scanner sc = null;
        try {
            sc = new Scanner(new File("juegos.txt"));
            while (sc.hasNextLine()) {
                Juego juego = new Juego(sc.nextLine());
                this.add(juego);

            }

        } catch (IOException e) {
            System.out.println("Error al leer del fichero");
        } finally {
            sc.close();
        }

    }

}
