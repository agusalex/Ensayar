package negocio;

import Data.Client;
import Data.Offer;
import Data.Schedule;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Max on 10/27/2016.
 */
public class Main {


//
public static void main (String args[] ){
    Instancia ins = new Instancia();
    ArrayList<Offer> offers = generateRandomOffers(4);
    Random r = new Random();
    for(Offer of : offers){

        ins.agregarObjeto(of);
    }

    System.out.println(ins.cloneOffers());
    System.out.println("**************************");
    System.out.println("");
    System.out.println("Fin de ofertas:"); 
    Solver solver = new SolverGoloso(SolverGoloso.Criterios.COCIENTE);
    Subconjunto sub = solver.resolver(ins);
    System.out.println("Soluciones:");
    System.out.println("");
    System.out.println(sub);


}
    private static ArrayList<Offer> generateRandomOffers(int n){
        Random r = new Random();
        Offer offer;
        Client client;
        ArrayList<Offer> ret = new ArrayList<Offer>();
        Schedule schedule;
        ArrayList<Client> clients =  generateRandomClients();
        ArrayList<Offer.Instruments> offerInstruments = new ArrayList<Offer.Instruments>();
        int Low = 1;
        int High = 10;
        int randomClient;
        int randomHour, randomMin,randomSpan;
        int cantInst;
        int[] min = {0,30};

        for(int i = 0; i< n; i++){
            cantInst = r.nextInt(defaultInstruments().size()-1)+1;
            offerInstruments = chosenRandomInstruments(cantInst);
         
            	 
           
            randomHour = r.nextInt(23)+1;
            
            randomSpan = r.nextInt(24-randomHour)+1;
            randomMin = min[r.nextInt(2)];
            schedule = new Schedule(randomHour, randomMin,randomHour+randomSpan, min[r.nextInt(2)]);

            randomClient = r.nextInt(clients.size()-1);
            client = clients.get(randomClient);

            offer = new Offer(offerInstruments,schedule,client);
            ret.add(offer);
        }

        return ret;
    }

    private static  ArrayList<Offer.Instruments> chosenRandomInstruments(int n){
    	ArrayList<Offer.Instruments> instruments = defaultInstruments();
    	ArrayList<Offer.Instruments> ret = new ArrayList<Offer.Instruments>();
        Random rand = new Random();
        int randomIndex;
        ArrayList<Integer> index=new ArrayList<Integer>();
        for(int i=0; i<n;i++){
            randomIndex = rand.nextInt(instruments.size());
            ret.add(instruments.get(randomIndex));
            instruments.remove(randomIndex);
            
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
            mobile = "11"+Integer.toString(r.nextInt(99999999));
            client.setMobile(mobile);
            clients.add(client);
        }

        return clients;


    }
}
