package negocio;

import Data.Client;
import Data.Schedule;
import Data.Offer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Max on 10/27/2016.
 */
public class Main {



public static void main (String args[] ){
    Instancia ins = new Instancia();
    Instancia inst = new Instancia();
    ArrayList<Offer> offers = generateRandomOffers(8);
    ArrayList<Offer> offers2 = generateRandomOffers(6);

    
    for(Offer of : offers)
        ins.agregarObjeto(of);

    for(Offer of: offers2)
        inst.agregarObjeto(of);


    System.out.println(offers);
    System.out.println("fin de ofertas");
    Solver solver = new SolverExacto();
    Subconjunto sub = solver.resolver(ins);
    System.out.println("soluciones");
    System.out.println(sub);


}
    private static ArrayList<Offer> generateRandomOffers(int n){
        Random r = new Random();
        Offer offer;
        Client client;
        ArrayList<Offer> ret = new ArrayList<Offer>();
        Schedule schedule;
        ArrayList<Client> clients =  generateRandomClients();
        ArrayList<Offer.Instruments> defaultInst = defaultInstruments();
        ArrayList<Offer.Instruments> offerInstruments = new ArrayList<Offer.Instruments>();
        int Low = 1;
        int High = 10;
        int randomClient;
        int randomHour, randomMin;
        int cantInst;
        int[] min = {0,30};

        for(int i = 0; i< n; i++){
            cantInst = r.nextInt(defaultInst.size()-1)+1;
            offerInstruments = chosenRandomInstruments(cantInst,defaultInst);

            randomHour = r.nextInt(High-Low)+Low;
            randomMin = min[r.nextInt(2)];
            schedule = new Schedule(randomHour, randomMin, randomHour+High, min[r.nextInt(2)]);

            randomClient = r.nextInt(clients.size()-1);
            client = clients.get(randomClient);

            offer = new Offer(offerInstruments,schedule,client);
            ret.add(offer);
        }

        return ret;
    }

    private static  ArrayList<Offer.Instruments> chosenRandomInstruments(int n, ArrayList<Offer.Instruments> defaultInst){
        ArrayList<Offer.Instruments> ret = new ArrayList<Offer.Instruments>();
        Random rand = new Random();
        int randomIndex;
        for (int i =0; i< n; i++){
            randomIndex = rand.nextInt(n);
            ret.add(defaultInst.get(randomIndex));
        }
        return ret;
    }

    private static  ArrayList<Offer.Instruments> defaultInstruments(){
        ArrayList<Offer.Instruments> inst = new ArrayList<Offer.Instruments>();
        inst.add(Offer.Instruments.BATERIA);
        inst.add(Offer.Instruments.GUITARRA);
        inst.add(Offer.Instruments.TECLADO);
        inst.add(Offer.Instruments.BAJO);
        inst.add(Offer.Instruments.MICROFONO);
        return inst;
    }

    private static ArrayList <Client> generateRandomClients(){
        String [] names = {"Gerardo", "Agustin", "Emmanuel","Fede", "Roberto", "Juan", "Pustilnik","Maxi", "Roberto"
                ,"Maria", "Josefina", "Claudia", "Alberto", "Esteban"};
        String [] IDs = {"1", "2", "3","4", "5", "6", "7","8", "9"
                ,"10", "11", "12", "13", "14"};
        ArrayList<Client> clients = new ArrayList<Client>();

        Random r = new Random();
        Client client = null;
        String mobile = "";
        int Low = 0;
        int High = names.length-1;
        int randomIndex ;

        for(int i = 0; i< High; i++){
            client = new Client("");
            randomIndex = r.nextInt(High-Low) + Low;
            client.setName(names[randomIndex]);
            client.setID(IDs[randomIndex]);
            mobile = Integer.toString(r.nextInt(1599999999-1500000000)+1500000000);
            client.setMobile(mobile);
            clients.add(client);
        }

        return clients;


    }
}
