package UI;

import Data.Client;
import Data.DataBase;
import Data.Offer;
import Data.Schedule;
import negocio.Instancia;
import negocio.Solver;
import negocio.Subconjunto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Data.DataBase.getDb;


@SuppressWarnings("unused")
class Manager {
    private static final Manager manager = new Manager();

    private static ArrayList<Offer> recentOffers;
    private static ArrayList<Offer> currentAssignedOffers;
    private static ArrayList<Offer> notCurrentAssignedOffers;
    private static LocalDate currentDate;





     static LocalDate getCurrentDate() {
        return currentDate;
    }

     static void setCurrentDate(LocalDate currentDate) {
        Manager.currentDate = currentDate;
    }




    private static Offer temporaryOffer;


    private Manager() {
        currentDate=LocalDate.now();
        loadDB();
    }


    static void Sort(Comparator<Offer> comparador) {
        Collections.sort(recentOffers, comparador);
        Collections.sort(currentAssignedOffers, comparador);

    }


     static ArrayList<Offer> getNotCurrentAssignedOffers() {
        return notCurrentAssignedOffers;
    }

    static void resetDB() {
        DataBase.getOffers().clear();
        for (Offer offer : recentOffers)
            DataBase.getOffers().add(offer);

        for (Offer offer : currentAssignedOffers)
            DataBase.getOffers().add(offer);


        for (Offer offer : notCurrentAssignedOffers)
            DataBase.getOffers().add(offer);

        getDb().save();
    }


    static void calculateOffersBy(Solver solver) {
        Instancia instance = new Instancia();
        getCurrentAssignedOffers().forEach(instance::agregarObjeto);
        getRecentOffers().forEach(instance::agregarObjeto);

        Subconjunto solution = solver.resolver(instance);

        Manager.getCurrentAssignedOffers().clear();

        for (Offer of : solution.getOffers()) {
            of.setAvailable(currentDate);
            Manager.getCurrentAssignedOffers().add(of);
            Manager.getRecentOffers().remove(of);
        }


        Manager.resetDB();
        Manager.loadDB();

    }

//

    public void upDateCurrentAssigned(LocalDate date){

        //TODO IMPLEMENTAR

    }


    static void loadDB() {
        getDb().load();
        recentOffers = new ArrayList<>();
        currentAssignedOffers = new ArrayList<>();

        notCurrentAssignedOffers =(ArrayList<Offer>)DataBase.getOffers().clone();


        for (Offer offer : DataBase.getOffers()) {

            if (offer.getDateAvailable() == null) {
                recentOffers.add(offer);
                notCurrentAssignedOffers.remove(offer);
            }

            else {

                if(offer.getDateAvailable().equals(currentDate)) {
                    currentAssignedOffers.add(offer);
                    notCurrentAssignedOffers.remove(offer);
                }


        }


        }
    }

    static boolean importDB(String filePath) {

        boolean isSuccess = DataBase.Import(filePath);

        if (isSuccess) {
            Manager.loadDB();

        }

        return isSuccess;
    }


    static boolean mergeDB(String filePath) {

        boolean isSuccess = DataBase.Import(filePath);

        if (isSuccess) {
            for (Offer offer : DataBase.getOffers()) {
                boolean alreadyExists = notCurrentAssignedOffers.contains(offer) ||recentOffers.contains(offer) || currentAssignedOffers.contains(offer);
                if (!alreadyExists) {
                        recentOffers.add(offer);
                    }
                }
            resetDB();
            Manager.loadDB();

            }

        return isSuccess;
    }


    static boolean exportDB(String filePath) {
        DataBase.getDb();

        return DataBase.Export(filePath);
    }


    static ArrayList<Offer> getRecentOffers() {
        return recentOffers;
    }

    static ArrayList<Offer> getCurrentAssignedOffers() {
        return currentAssignedOffers;
    }

    static void emptyTemporaryOffer() {
        Manager.temporaryOffer = null;
    }

    static void setOffer(ArrayList<Offer.Instruments> instruments, Schedule schedule, Client client) {
        Manager.temporaryOffer = new Offer(instruments, schedule, client);
    }

    static Offer getTemporaryOffer() {
        return Manager.temporaryOffer;
    }


}

