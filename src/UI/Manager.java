package UI;

import Data.Client;
import Data.DataBase;
import Data.Offer;
import Data.Schedule;

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


    public static Manager getManager() {
        return manager;
    }

    public static void Sort(Comparator<Offer> comparador){
        Collections.sort(recentOffers,comparador);
        Collections.sort(assignedOffers,comparador);

    }

    public static ArrayList<Offer> getRecentOffers() {
        return recentOffers;
    }

    public static void resetDB(){
        DataBase.getDb().getOffers().clear();
        for (Offer offer:recentOffers)
            DataBase.getDb().getOffers().add(offer);

        for(Offer offer :assignedOffers)
            DataBase.getDb().getOffers().add(offer);

        DataBase.getDb().save();

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
            else {
                assignedOffers.add(offer);
            }
        }
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

    public static void emptyOffer(){ Manager.temporaryOffer = null;}

    public static void setOffer(ArrayList<Offer.Instruments> instruments, Schedule schedule, Client client){
        Manager.temporaryOffer = new Offer (instruments,schedule,client);
    }

    public static Offer getOffer(){
        return Manager.temporaryOffer;
    }



}

