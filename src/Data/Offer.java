package Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
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

        public static ArrayList<Instruments> defaultInstruments() {
            ArrayList<Offer.Instruments> inst = new ArrayList<>();
            inst.add(Offer.Instruments.BATERIA);
            inst.add(Offer.Instruments.GUITARRA);
            inst.add(Offer.Instruments.TECLADO);
            inst.add(Offer.Instruments.BAJO);
            inst.add(Offer.Instruments.MICROFONO);
            return inst;
        }



        public static ArrayList<Instruments> chosenRandomInstruments(int n, ArrayList<Offer.Instruments> defaultInst) {
            ArrayList<Instruments> ret = new ArrayList<>();
            Random rand = new Random();
            int randomIndex;
            for (int i = 0; i < n; i++) {
                randomIndex = rand.nextInt(n);
                ret.add(defaultInst.get(randomIndex));
            }
            return ret;
        }


        public static int comboValue(ArrayList<Instruments> combo) {
            if (combo == null)
                throw new RuntimeException("No existe ningun combo de instrumentos");

            int ret = 0;
            for (Instruments aCombo : combo) ret += instrumentValue(aCombo);
            return ret;
        }
    }


    public static ArrayList<Offer> generateRandomOffers() {
        Random r = new Random();
        Offer offer;
        Client client;
        ArrayList<Offer> ret = new ArrayList<>();
        Schedule schedule;
        ArrayList<Client> clients = Client.generateRandomClients();
        ArrayList<Offer.Instruments> defaultInst = Offer.Instruments.defaultInstruments();
        ArrayList<Offer.Instruments> offerInstruments;
        int Low = 1;
        int High = 5;
        int randomClient;
        int randomHour, randomMin;
        int cantInst;
        int[] min = {0, 30};

        for (int i = 0; i < 4; i++) {
            cantInst = r.nextInt(defaultInst.size() - 1) + 1;
            offerInstruments = Offer.Instruments.chosenRandomInstruments(cantInst, defaultInst);

            randomHour = r.nextInt(High - Low) + Low;
            randomMin = min[r.nextInt(2)];
            schedule = new Schedule(randomHour, randomMin, randomHour + High, min[r.nextInt(2)]);

            randomClient = r.nextInt(clients.size() - 1);
            client = clients.get(randomClient);

            offer = new Offer(offerInstruments, schedule, client);
            ret.add(offer);
        }

        return ret;
    }

}
