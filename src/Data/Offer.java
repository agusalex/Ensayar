package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Agus on 23/10/2016.
 */
public class Offer implements Serializable{
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

    public enum instruments{
        BATERIA,GUITARRA,TECLADO,BAJO,MICROFONO
    }

    private Float price;
    private Client client;
    private Date dateAvailable;
    private Schedule schedule;
    private ArrayList<instruments> instruments;
    private long duration;

    public void random(Random r){
        price= (float)r.nextInt(1000)+1;
        if(price<0){
            price*=-1;
        }
        int inicio=r.nextInt(24);
        int fin=r.nextInt(24);

        if(inicio<0)
            inicio*=-1;
        if(fin<0)
            fin*=-1;

        while(inicio>fin){
            inicio=r.nextInt(24);

        }
        if(inicio<0)
            inicio*=-1;
        if(fin<0)
            fin*=-1;
        setSchedule(new Schedule(inicio,0,fin,0));

        client=new Client(Integer.toString(this.hashCode()));
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateAvailable() {
        return dateAvailable;
    }

    public void setDateAvailable(Date dateAvailable) {
        this.dateAvailable = dateAvailable;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        this.duration=schedule.end.getHours()-schedule.start.getHours();
    }

    public long getDuration() {
        return duration;
    }

    public ArrayList<Offer.instruments> getInstruments() {
        return instruments;

    }

    public void setInstruments(ArrayList<Offer.instruments> instruments) {
        this.instruments = instruments;
    }


}
