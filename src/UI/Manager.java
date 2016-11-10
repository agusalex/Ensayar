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


@SuppressWarnings("unused")
class Manager {
    private static final Manager manager = new Manager();

    private static ArrayList<Offer> recentOffers;
    private static ArrayList<Offer> assignedOffers;

    private static Offer temporaryOffer;


    private Manager() {
        loadDB();
    }


    static void Sort(Comparator<Offer> comparador) {
        Collections.sort(recentOffers, comparador);
        Collections.sort(assignedOffers, comparador);

    }


    static void resetDB() {
        DataBase.getOffers().clear();
        for (Offer offer : recentOffers)
            DataBase.getOffers().add(offer);

        for (Offer offer : assignedOffers)
            DataBase.getOffers().add(offer);

        getDb().save();
    }


    static void calculateOffersBy(Solver solver) {
        Instancia instance = new Instancia();
        DataBase.getOffers().forEach(instance::agregarObjeto);

        Subconjunto solution = solver.resolver(instance);

        Manager.getAssignedOffers().clear();
        Manager.getRecentOffers().clear();

        for (Offer of : solution.getOffers()) {
            of.setAvailableTomorrow();
            Manager.getAssignedOffers().add(of);
        }

        DataBase.getOffers().stream().filter(solution::contiene).forEach(of -> Manager.getRecentOffers().add(of));
        Manager.resetDB();

    }

//

    static void loadDB() {
        ArrayList<Offer> dataBaseOffers;
        recentOffers = new ArrayList<>();
        assignedOffers = new ArrayList<>();
        getDb().load();

        dataBaseOffers = DataBase.getOffers();

        for (Offer offer : dataBaseOffers) {

            if (offer.getDateAvailable() == null)
                recentOffers.add(offer);
            else {
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

    static boolean importDB(String filePath) {

        boolean isSuccess = DataBase.Import(filePath);

        if (isSuccess) {
            recentOffers = new ArrayList<>();
            assignedOffers = new ArrayList<>();
            for (Offer offer : DataBase.getOffers()) {

                if (offer.getDateAvailable() == null)
                    recentOffers.add(offer);
                else {
                    assignedOffers.add(offer);
                }
            }
        }
        resetDB();

        return isSuccess;
    }


    static boolean mergeDB(String filePath) {

        boolean isSuccess = DataBase.Import(filePath);

        if (isSuccess) {
            for (Offer offer : DataBase.getOffers()) {
                boolean alreadyExists = recentOffers.contains(offer) || assignedOffers.contains(offer);
                if (!alreadyExists) {
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


    static boolean exportDB(String filePath) {
        DataBase.getDb();

        return DataBase.Export(filePath);
    }


    static ArrayList<Offer> getRecentOffers() {
        return recentOffers;
    }

    static ArrayList<Offer> getAssignedOffers() {
        return assignedOffers;
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

