package Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agus on 23/10/2016.
 */
public class DataBase {
    private final String filename = "Offers.json";;
    private static DataBase db;
    private ArrayList<Offer> offers;
    private boolean archivoCorrupto = false;

    private File directory = new File (filename);
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
        Type listType = new TypeToken<List<Offer>>() {}.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String offers = gson.toJson(this.offers,listType);

        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(this.filename));
            fileWriter.write(offers);
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Falla en la escritura de archivos");
        }
    }

    public void  load() {
        Gson gson = new Gson();
        File f = new File(this.dir);
        Type listType = new TypeToken<List<Offer>>() {}.getType();

        try {
            if (f.exists()) {
                try {
                    this.offers = gson.fromJson(new FileReader(this.dir), listType);
                } catch (Exception e) {
                    System.out.println("Archivo corrupto, generando instancia vacia");
                    archivoCorrupto = true;
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

    }


    public void Export(String FilePath){
    }
    public void Import(String FilePath){

    }



}
