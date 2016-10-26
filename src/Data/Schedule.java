package Data;

import java.util.Calendar;

/**
 * Created by Agus on 23/10/2016.
 */
public class Schedule {
    private Calendar start, end;
    private int starth,finishH, startMins, endMins;


    public Schedule(int startH,int startM,int endH,int endM){

        if(startH == endH)
            throw new IllegalArgumentException("No se puede elegir el mismo horario como inicio y final");
        if(endH < startH)
            throw new IllegalArgumentException("No se puede elegir como hora inicial una posterior a la final");
        if(startM < 0 || endM < 0 || startM > 60 || endM > 60)
            throw new IllegalArgumentException("No se puede elegir como minuto menor a 0 o mayor a 60");

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
        this.endMins = endM;

        if (start.before(end)) {
            System.out.println("The beginning comes before the end");
            System.out.println("");
        }

    }

    public boolean conflictsWith(Schedule that){

        if(this.start.after(that.end)||this.end.before(that.start)){
            return false;

        }

        return true;

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

    public int getEndMins() {
        return endMins;
    }
}
