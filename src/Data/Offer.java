package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Agus on 23/10/2016.
 */
public class Offer implements Serializable{

    private Float price;
    private Client client;
    private Calendar dateAvailable = Calendar.getInstance();
    private Schedule schedule;
    private ArrayList<Instruments> instruments;
    private long duration;
    private int durationInMin;

    public Offer(ArrayList<Instruments> instruments, Schedule schedule, Client client ){
        this.instruments = instruments;
        this.price = Instruments.comboValue(instruments);
        this.setSchedule(schedule);
        this.client = client;
    }

    public enum Instruments {
        BATERIA,GUITARRA,TECLADO,BAJO,MICROFONO;

        public static float instrumentValue(Instruments inst){
             switch(inst){
                 case BATERIA   : return 70.0F;
                 case GUITARRA  : return 50.0F;
                 case TECLADO   : return 40.0F;
                 case BAJO      : return 50.0F;
                 case MICROFONO : return 30.0F;
                 default        : return 0.0F;
             }
        }

        public static float comboValue(ArrayList<Instruments> combo){
            if(combo == null || combo.size() == 0)
                throw new RuntimeException("No existe ningun combo de instrumentos");

            float ret = 0.0F;
            for(int i = 0; i< combo.size(); i++)
                ret += instrumentValue(combo.get(i));
            return ret;
        }
    }


    public float getPrice() {return price;}

    public Client getClient() {return client;}

    public void setClient(Client client) {this.client = client;}

    public Calendar getDateAvailable() {return dateAvailable;}

    public void setDateAvailable(Calendar dateAvailable) {this.dateAvailable = dateAvailable;}

    public boolean conflictsWith(Offer that){
        return this.getSchedule().conflictsWith(that.getSchedule());
    }

    public long getDuration() {return duration;}

    public int getDurationInMin() {return durationInMin;}

    public ArrayList<Instruments> getInstruments() {return instruments;}

    public void setInstruments(ArrayList<Instruments> instruments) {this.instruments = instruments;}

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        this.duration = schedule.getEnd() - schedule.getStart();
        if(schedule.getStartMins() > schedule.getEndMins())
            this.duration--;
        this.durationInMin = Math.abs(schedule.getEndMins() - schedule.getStartMins());
    }

    public Schedule getSchedule() {return schedule;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (duration != offer.duration) return false;
        if (price != null ? !price.equals(offer.price) : offer.price != null) return false;
        if (client != null ? !client.equals(offer.client) : offer.client != null) return false;
        if (dateAvailable != null ? !dateAvailable.equals(offer.dateAvailable) : offer.dateAvailable != null)
            return false;
        if (schedule != null ? !schedule.equals(offer.schedule) : offer.schedule != null) return false;
        return instruments != null ? instruments.equals(offer.instruments) : offer.instruments == null;
    }

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
    public String toString(){
        return "Oferta:\n" +
                "Instrumentos: "+this.instruments.toString()+"\n"+
                "Horario: "+ this.schedule.toString()+"\n"+
                "Duracion: "+ this.duration +"hs "+ this.durationInMin+" minutos\n"+
                "Cliente: "+ this.client.toString()+"\n"+
                "Precio: "+ this.price+"\n";

    }

}
