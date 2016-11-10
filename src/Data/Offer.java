package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class Offer implements Serializable {

    private static final Integer pricePerHour = 30;
    private static final Integer priceHalfHour = pricePerHour / 2;
    private final ArrayList<Instruments> instruments;
    private Integer price;
    private Client client;
    private Calendar dateAvailable;
    private Schedule schedule;
    private int duration;
    private int durationInMin;

    public Offer(ArrayList<Instruments> instruments, Schedule schedule, Client client) {
        this.instruments = instruments;
        this.price = Instruments.comboValue(instruments);
        this.setSchedule(schedule);
        this.price += pricePerHour * getDuration() + priceHalfHour;
        this.client = client;
    }

    public void setAvailableTomorrow() {
        dateAvailable = Calendar.getInstance();
        dateAvailable.add(Calendar.DATE, 1);
    }

    public void setNotAssigned() {
        dateAvailable = null;
    }

    public int getPrice() {
        return price;
    }

    public Client getClient() {
        return client;
    }

    public Calendar getDateAvailable() {
        return dateAvailable;
    }

    public boolean conflictsWith(Offer that) {
        return this.getSchedule().conflictsWith(that.getSchedule());
    }

    public int getDuration() {
        return duration;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public ArrayList<Instruments> getInstruments() {
        return instruments;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    private void setSchedule(Schedule schedule) {

        this.schedule = schedule;
        Long initMilis = schedule.getStartC().getTimeInMillis();
        Long endMilis = schedule.getEndC().getTimeInMillis();
        Long deltaH = TimeUnit.MILLISECONDS.toHours(endMilis - initMilis);
        Long deltaM = TimeUnit.MILLISECONDS.toMinutes(endMilis - initMilis);

        this.duration = (int) Math.abs(deltaH);
        this.durationInMin = ((int) Math.abs(deltaM) - ((int) Math.abs(deltaH)) * 60);

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (dateAvailable != null ? dateAvailable.hashCode() : 0);
        result = 31 * result + (schedule != null ? schedule.hashCode() : 0);
        result = 31 * result + (instruments != null ? instruments.hashCode() : 0);
        result = 31 * result + (duration);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Offer offer = (Offer) o;

        if (duration != offer.duration) return false;
        if (durationInMin != offer.durationInMin) return false;
        if (price != null ? !price.equals(offer.price) : offer.price != null) return false;
        //noinspection SimplifiableIfStatement
        if (client != null ? !client.equals(offer.client) : offer.client != null) return false;
        return dateAvailable != null ? dateAvailable.equals(offer.dateAvailable) : offer.dateAvailable == null && (schedule != null ? schedule.equals(offer.schedule) : offer.schedule == null && (instruments != null ? instruments.equals(offer.instruments) : offer.instruments == null));

    }

    @Override
    public String toString() {
        return "\nOferta:\n" +
                "Instrumentos: " + this.instruments.toString() + "\n" +
                "Horario: " + this.schedule.toString() + "\n" +
                "Duracion: " + this.duration + "hs " + this.durationInMin + " minutos\n" +
                "Cliente: " + this.client.toString() + "\n" +
                "Precio: " + this.price + "\n\n*********\n";

    }

    public enum Instruments {
        BATERIA, GUITARRA, TECLADO, BAJO, MICROFONO;

        public static int instrumentValue(Instruments inst) {
            switch (inst) {
                case BATERIA:
                    return 70;
                case GUITARRA:
                    return 50;
                case TECLADO:
                    return 40;
                case BAJO:
                    return 50;
                case MICROFONO:
                    return 30;
                default:
                    return 0;
            }
        }

        public static int comboValue(ArrayList<Instruments> combo) {
            if (combo == null)
                throw new RuntimeException("No existe ningun combo de instrumentos");

            int ret = 0;
            for (Instruments aCombo : combo) ret += instrumentValue(aCombo);
            return ret;
        }
    }

}
