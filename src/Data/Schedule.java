package Data;

import java.util.Calendar;

/**
 * Created by Agus on 23/10/2016.
 */
public class Schedule {
    private Calendar start, end;
    private int starth,finishH, startMins, finishMins;


    public Schedule(int startH,int startM,int endH,int endM){

        if(false){
            if(startH == endH)
                throw new IllegalArgumentException("No se puede elegir el mismo horario como inicio y final");
            if(endH < startH)
                throw new IllegalArgumentException("No se puede elegir como hora inicial una posterior a la final");
        }

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start = Calendar.getInstance();
        end = Calendar.getInstance();

        start.set(Calendar.HOUR_OF_DAY,startH);
        start.set(Calendar.MINUTE,startM);
        end.set(Calendar.HOUR_OF_DAY,endH);
        end.set(Calendar.MINUTE,endM);

        this.starth = startH;
        this.finishH = endH;
        this.startMins = startM;
        this.finishMins = endM;

        if (start.before(end)) {
            System.out.println("The beginning comes before the end");
            System.out.println("");
        }
        //TODO tener en cuenta minutos


    }

    public int getStart() {
        return starth;
    }

    public int getEnd() {
        return finishH;
    }

    public int getStartMins() {
        return startMins;
    }

    public int getFinishMins() {
        return finishMins;
    }
}
