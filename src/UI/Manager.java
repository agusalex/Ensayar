package UI;

import Data.Client;
import Data.DataBase;
import Data.Offer;
import Data.Schedule;
import negocio.Instancia;
import negocio.Solver;
import negocio.Subconjunto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Data.DataBase.getDb;

/**
 * Created by Max on 11/4/2016.
 */
public class Manager {
    private static Manager manager = new Manager();

    private static ArrayList<Offer> recentOffers;
    private static ArrayList<Offer> assignedOffers;

    private static Offer temporaryOffer ;


    private Manager() {
    loadDB();
    }


    public static Manager getManager() {return manager;}

    public static void Sort(Comparator<Offer> comparador){
        Collections.sort(recentOffers,comparador);
        Collections.sort(assignedOffers,comparador);

    }


    public static void resetDB(){
        getDb().getOffers().clear();
        for (Offer offer:recentOffers)
            getDb().getOffers().add(offer);

        for(Offer offer :assignedOffers)
            getDb().getOffers().add(offer);

        getDb().save();
    }



    public static void calculateOffersBy(Solver solver){
        Instancia instance = new Instancia();
        for(Offer of : getDb().getOffers())
            instance.agregarObjeto(of);

        Subconjunto solution = solver.resolver(instance);

        Manager.getAssignedOffers().clear();
        Manager.getRecentOffers().clear();

        for(Offer of : solution.getOffers()){
            of.setAvailableTomorrow();
            Manager.getAssignedOffers().add(of);
        }

        for(Offer of : getDb().getOffers()){
            if(!solution.contiene(of))
                Manager.getRecentOffers().add(of);
        }
        Manager.resetDB();

    }

//

    public static void loadDB(){
        ArrayList<Offer> dataBaseOffers = new ArrayList<Offer>();
        recentOffers = new ArrayList<Offer>();
        assignedOffers = new ArrayList<Offer>();
        getDb().load();

        dataBaseOffers = DataBase.getOffers();

        for (Offer offer : dataBaseOffers) {

            if (offer.getDateAvailable() == null)
                recentOffers.add(offer);
            else{
                assignedOffers.add(offer);
            }
            /*else if(offer.getDateAvailable().after(Calendar.getInstance())){
                assignedOffers.add(offer);
            }
            else if (offer.getDateAvailable().equals(Calendar.getInstance())) {
                assignedOffers.add(offer);
            }
            else if((offer.getDateAvailable().before(Calendar.getInstance()))){
                //NO LA AGREGO SI ES VIEJA

            }*/

        }
    }

    public static boolean importDB(String filePath){

        recentOffers = new ArrayList<Offer>();
        assignedOffers = new ArrayList<Offer>();

        boolean isSuccess=DataBase.Import(filePath);

        if(isSuccess){
            for (Offer offer : DataBase.getOffers()) {

                if (offer.getDateAvailable() == null)
                    recentOffers.add(offer);
                else{
                    assignedOffers.add(offer);
            }
            }
        }
        resetDB();

        return isSuccess;
    }



    public static boolean mergeDB(String filePath){

        boolean isSuccess=DataBase.Import(filePath);

        if(isSuccess){
            for (Offer offer : DataBase.getOffers()) {
                boolean alreadyExists=recentOffers.contains(offer)||assignedOffers.contains(offer);
                if(!alreadyExists) {
                    if (offer.getDateAvailable() == null)
                        recentOffers.add(offer);
                    else {
                        assignedOffers.add(offer);
                    }
                }
            }
        }
        resetDB();

        return isSuccess;
    }




    public static boolean exportDB(String filePath){
              DataBase.getDb();

        return DataBase.Export(filePath);
    }


    public static ArrayList<Offer> getRecentOffers() {
        return recentOffers;
    }

    public static void setRecentOffers(ArrayList<Offer> recentOffers) {
        Manager.recentOffers = recentOffers;
    }

    public static ArrayList<Offer> getAssignedOffers() {
        return assignedOffers;
    }

    public static void setAssignedOffers(ArrayList<Offer> assignedOffers) {
        Manager.assignedOffers = assignedOffers;
    }

    public static void emptyTemporaryOffer(){ Manager.temporaryOffer = null;}

    public static void setOffer(ArrayList<Offer.Instruments> instruments, Schedule schedule, Client client){
        Manager.temporaryOffer = new Offer (instruments,schedule,client);
    }

    public static Offer getTemporaryOffer(){
        return Manager.temporaryOffer;
    }



}

