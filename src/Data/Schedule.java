package Data;

import java.util.Calendar;

/**
 * Created by Agus on 23/10/2016.
 */
public class Schedule {
    private Calendar startHour, finishHour, startMin, finishMin;
    private int starth,finishH, startMins, finishMins;


    public Schedule(int startH,int startM,int endH,int endM){

        if(false){
            if(startH == endH)
                throw new IllegalArgumentException("No se puede elegir el mismo horario como inicio y final");
            if(endH < startH)
                throw new IllegalArgumentException("No se puede elegir como hora inicial una posterior a la final");
        }

        startHour = Calendar.getInstance();
        finishHour = Calendar.getInstance();
        startMin = Calendar.getInstance();
        finishMin = Calendar.getInstance();

        startHour.set(Calendar.HOUR_OF_DAY,startH);
        startMin.set(Calendar.MINUTE,startM);
        finishHour.set(Calendar.HOUR_OF_DAY,endH);
        finishMin.set(Calendar.MINUTE,endM);

        this.starth = startH;
        this.finishH = endH;
        this.startMins = startM;
        this.finishMins = endM;

        if (startHour.before(finishHour)) {
            System.out.println("The beginning comes before the end");
            System.out.println("");
        }
        //TODO tener en cuenta minutos


    }

    public int getStartHour() {
        return starth;
    }

    public int getFinishHour() {
        return finishH;
    }

    public int getStartMins() {
        return startMins;
    }

    public int getFinishMins() {
        return finishMins;
    }
}
