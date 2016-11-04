package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Agus on 23/10/2016.
 */
public class Offer implements Serializable{

    private Integer price;
    private static final Integer pricePerHour = 30;
    private static final Integer priceHalfHour = pricePerHour / 2;
    private Client client;
    private Calendar dateAvailable ;
    private Schedule schedule;
    private ArrayList<Instruments> instruments;
    private int duration;
    private int durationInMin;
    private int nonHalfHour;

    public Offer(ArrayList<Instruments> instruments, Schedule schedule, Client client ){
        this.instruments = instruments;
        this.price = Instruments.comboValue(instruments);
        this.setSchedule(schedule);
        this.price += pricePerHour*getDuration() + priceHalfHour*nonHalfHour;
        this.client = client;
    }

    public enum Instruments {
        BATERIA,GUITARRA,TECLADO,BAJO,MICROFONO;

        public static int instrumentValue(Instruments inst){
             switch(inst){
                 case BATERIA   : return 70;
                 case GUITARRA  : return 50;
                 case TECLADO   : return 40;
                 case BAJO      : return 50;
                 case MICROFONO : return 30;
                 default        : return 0;
             }
        }

        public static int comboValue(ArrayList<Instruments> combo){
            if(combo == null || combo.size() == 0)
                throw new RuntimeException("No existe ningun combo de instrumentos");

            int ret = 0;
            for(int i = 0; i< combo.size(); i++)
                ret += instrumentValue(combo.get(i));
            return ret;
        }
    }

    public void setAvailableTomorrow(){
        dateAvailable= Calendar.getInstance();
        dateAvailable.add(Calendar.DATE,1);
    }

    public int getPrice() {return price;}
    public  void  subastar( int cantidad){ this.price += cantidad;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public Calendar getDateAvailable() {return dateAvailable;}

    public void setDateAvailable(Calendar dateAvailable) {this.dateAvailable = dateAvailable;}

    public boolean conflictsWith(Offer that){
        return this.getSchedule().conflictsWith(that.getSchedule());
    }

    public int getDuration() {return duration;}

    public int getDurationInMin() {return durationInMin;}

    public ArrayList<Instruments> getInstruments() {return instruments;}

    public void setSchedule(Schedule schedule) {

        this.schedule = schedule;
        Long initMilis=schedule.getStartC().getTimeInMillis();
        Long endMilis=schedule.getEndC().getTimeInMillis();
        Long deltaH= TimeUnit.MILLISECONDS.toHours(endMilis-initMilis);
        Long deltaM= TimeUnit.MILLISECONDS.toMinutes(endMilis-initMilis);

        this.duration = (int)Math.abs(deltaH);
        this.durationInMin =((int)Math.abs(deltaM)-((int)Math.abs(deltaH))*60);

    }

    public Schedule getSchedule() {return schedule;}



    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (dateAvailable != null ? dateAvailable.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (instruments != null ? instruments.hashCode() : 0);
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (duration != offer.duration) return false;
        if (durationInMin != offer.durationInMin) return false;
        if (nonHalfHour != offer.nonHalfHour) return false;
        if (price != null ? !price.equals(offer.price) : offer.price != null) return false;
        if (client != null ? !client.equals(offer.client) : offer.client != null) return false;
        if (dateAvailable != null ? !dateAvailable.equals(offer.dateAvailable) : offer.dateAvailable != null)
            return false;
        if (schedule != null ? !schedule.equals(offer.schedule) : offer.schedule != null) return false;
        return instruments != null ? instruments.equals(offer.instruments) : offer.instruments == null;

    }

    @Override
    public String toString(){
        return "\nOferta:\n" +
                "Instrumentos: "+this.instruments.toString()+"\n"+
                "Horario: "+ this.schedule.toString()+"\n"+
                "Duracion: "+ this.duration +"hs "+ this.durationInMin+" minutos\n"+
                "Cliente: "+ this.client.toString()+"\n"+
                "Precio: "+ this.price+"\n\n*********\n";

    }

}
