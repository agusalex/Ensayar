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
    ArrayList<Offer> offers = generateRandomOffers(10);
    Random r = new Random();
    for(Offer of : offers){

        ins.agregarObjeto(of);
    }


   // System.out.println(offers);
    Solver solverBeneficio = new SolverGoloso(SolverGoloso.Criterios.PRECIO);
    Subconjunto sub3 = solverBeneficio.resolver(ins);
    System.out.println("Solver beneficio, GUITA:"+ sub3.beneficio());
    System.out.println(sub3+"\n\n");

    Solver solverCociente = new SolverGoloso(SolverGoloso.Criterios.COCIENTE);
    Subconjunto sub2 = solverCociente.resolver(ins);
    System.out.println("Solver Cociente, GUITA:"+ sub2.beneficio());
    System.out.println(sub2+"\n\n");

    SolverExacto solver = new SolverExacto();
    Subconjunto sub = solver.resolver(ins);
    System.out.println("Solver Exacto tiempo"+solver.getTiempo());
   System.out.println("Soluciones EXACTO Beneficio="+sub.beneficio());
    System.out.println("");
    System.out.println(sub);



}
    public static ArrayList<Offer> generateRandomOffers(int n){
        Random r = new Random();
        Offer offer;
        Client client;
        ArrayList<Offer> ret = new ArrayList<Offer>();
        Schedule schedule;
        ArrayList<Client> clients =  generateRandomClients();
        ArrayList<Offer.Instruments> offerInstruments = new ArrayList<Offer.Instruments>();

        int randomClient;
        int randomHour, randomMin;
        int cantInst;
        int[] min = {0,30};

        for(int i = 0; i< n; i++){
            cantInst = r.nextInt(defaultInstruments().size()-1)+1;
            offerInstruments = chosenRandomInstruments(cantInst);
         
            	 

            randomHour = r.nextInt(24);//min 0 max 23


            int randomSpan =1;
            int maxSpan=23-randomHour;
            randomMin = min[r.nextInt(2)];

            if(maxSpan>0)
                randomSpan = r.nextInt(maxSpan)+1;

            int endH=randomHour+randomSpan;

            if(endH==24)
                randomMin=0;

            schedule = new Schedule(randomHour, min[r.nextInt(2)],endH,randomMin );

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
