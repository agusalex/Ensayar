package Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Agus on 23/10/2016.
 */
public class DataBase {
    private static String filename = "Offers.json";
    private static DataBase db;
    private static ArrayList<Offer> offers;
    private boolean archivoCorrupto = false;
    private static File file = new File (filename);
    private static String dir = file.getAbsolutePath();

    private DataBase(){}

    public static DataBase getDb() {
        if(db == null){
            db = new DataBase();
        }
        return db;
    }

    public static ArrayList<Offer> getOffers() {return offers;}

    void setOffers(ArrayList<Offer> offers) {
        DataBase.offers = offers;}



    public void save(){
        Type listType = new TypeToken<List<Offer>>() {}.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String offers = gson.toJson(DataBase.offers,listType);

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

                this.offers = gson.fromJson(new FileReader(this.dir), listType);

                if(this.offers==null)
                    throw new FileNotFoundException("Archivo existente pero corrupto");

            }
            else{
                throw new FileNotFoundException("Archivo inexistente");
            }



        } catch (Exception e) {
            try{
                BufferedWriter br = new BufferedWriter(new FileWriter(this.dir));
                offers= new ArrayList<Offer>();
                save();
                load();
            } catch (Exception b){
                System.out.println(b.getMessage()+" Error de Acceso al directorio");
            }


        }

    }



    public static boolean Export(String FilePath){
       boolean isSuccess=true;
        Type listType = new TypeToken<List<Offer>>() {}.getType();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String offers = gson.toJson(getDb().getOffers(),listType);

        try{
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(FilePath));
            fileWriter.write(offers);
            if(!new File(FilePath).exists()){
                isSuccess=false;
            }
            fileWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Falla en la escritura de archivos");
            isSuccess=false;
        }

        return isSuccess;
    }
    public static boolean Import(String FilePath){
        boolean isSuccess=true;
        Gson gson = new Gson();
        File f = new File(FilePath);
        Type listType = new TypeToken<List<Offer>>() {}.getType();
        ArrayList<Offer> offrs=new ArrayList<Offer>();
        try {
            if (f.exists()) {
                try {
                    offrs = gson.fromJson(new FileReader(FilePath), listType);
                } catch (Exception e){
                    isSuccess=false;}
                if (offrs == null)
                    isSuccess=false;
            }
            else {
                //ESTO ES PARA QUE, SI EL ARCHIVO NO EXISTE, SE CREA UNO VACIO, QUE DESPUES SE MODIFICA SEGUN LO QUE GUARDE EL USUARIO
                BufferedWriter br = new BufferedWriter(new FileWriter(dir));
                br.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se encuentra el archivo especificado");
            isSuccess=false;
        }
        if(isSuccess)
            getDb().setOffers(offrs);

        return isSuccess;
    }



}
