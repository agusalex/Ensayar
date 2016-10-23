package Data;

/**
 * Created by Agus on 23/10/2016.
 */
public class DataBase {
    String filename;
    private static DataBase db;
    private DataBase(){
        filename="Offers.json";
    }

    public static DataBase getDb() {
        if(db==null){
            db=new DataBase();
        }
        return db;
    }
    public void save(){

    }
    public void load(){}

    public void Export(String FilePath){
    }
    public void Import(String FilePath){

    }



}
