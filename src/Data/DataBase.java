package Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Agus on 23/10/2016.
 */
public class DataBase {
    private final String filename = "Offers.json";;
    private static DataBase db;
    private ArrayList<Offer> offers;
    private boolean archivoCorrupto = false;

    private File directory = new File (".");
    private String dir = directory.getAbsolutePath();

    private DataBase(){}

    public ArrayList<Offer> getOffers() {return offers;}

    public void setOffers(ArrayList<Offer> offers) {this.offers = offers;}

    public static DataBase getDb() {
        if(db == null){
            db = new DataBase();
        }
        return db;
    }

    public void save(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String coordenadas = gson.toJson(this.offers);  //guardado de coordenada

        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.dir));
            fileWriter.write(coordenadas);
            fileWriter.close();
        }
        catch (Exception e){
            System.out.println("Falla en la escritura de archivos");
        }


    }
    public ArrayList<Offer> load() {
        Gson gson = new Gson();
        File f = new File(this.dir);
        try {
            if (f.exists()) {
                ArrayList<Offer> offers = new ArrayList<Offer>();
                try {
                    offers = gson.fromJson(new FileReader(this.dir), offers.getClass());
                } catch (Exception e) {
                    System.out.println("Archivo corrupto, generando instancia vacia");
                    archivoCorrupto = true;
                    return offers;

                }
                if (offers == null)
                    System.out.println("Archivo vacio, creando uno nuevo...");

            } else {
                //ESTO ES PARA QUE, SI EL ARCHIVO NO EXISTE, SE CREA UNO VACIO, QUE DESPUES SE MODIFICA SEGUN LO QUE GUARDE EL USUARIO
                BufferedWriter br = new BufferedWriter(new FileWriter(this.dir));
                br.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se encuentra el archivo especificado");
        }
        return null;
    }


    public void Export(String FilePath){
    }
    public void Import(String FilePath){

    }



}
