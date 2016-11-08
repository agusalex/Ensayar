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
        DataBase.getDb().getOffers().clear();
        for (Offer offer:recentOffers)
            DataBase.getDb().getOffers().add(offer);

        for(Offer offer :assignedOffers)
            DataBase.getDb().getOffers().add(offer);

        DataBase.getDb().save();

    }


    public static void calculateOffersBy(Solver solver){
        Instancia instance = new Instancia();
        for(Offer of : DataBase.getDb().getOffers())
            instance.agregarObjeto(of);

        Subconjunto solution = solver.resolver(instance);

        Manager.getAssignedOffers().clear();
        Manager.getRecentOffers().clear();

        for(Offer of : solution.getOffers()){
            of.setAvailableTomorrow();
            Manager.getAssignedOffers().add(of);
        }

        for(Offer of : DataBase.getDb().getOffers()){
            if(!solution.contiene(of))
                Manager.getRecentOffers().add(of);
        }
        Manager.resetDB();

    }



    public static void loadDB(){
        ArrayList<Offer> dataBaseOffers = new ArrayList<Offer>();
        recentOffers = new ArrayList<Offer>();
        assignedOffers = new ArrayList<Offer>();
        DataBase.getDb().load();

        dataBaseOffers = DataBase.getDb().getOffers();

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

