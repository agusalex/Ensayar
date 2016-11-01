package Data;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by Agus on 23/10/2016.
 */
public class Schedule {
    public Calendar getStartC() {
        return this.start;
    }

    public Calendar getEndC() {
        return this.end;
    }

    private Calendar start, end;
    private int starth, endH, startMins, endMins;


    public Schedule(int startH,int startM,int endH,int endM){



        if(endH==24&&endM>0)
            throw new IllegalArgumentException("El horario no puede terminar mas de las 24");

        if(startH == endH)
            throw new IllegalArgumentException("No se puede elegir el mismo horario como inicio y final. " +
                    "hora de inicio: "+ startH+" hora finalizacion"+ endH);
        if(endH < startH)
            throw new IllegalArgumentException("No se puede elegir como hora inicial una posterior a la final. " +
                    "Hora inicial: " + startH+":"+startM+" hora finalizacion"+ endH+":"+startM);
        if(startM < 0 || endM < 0 || startM > 60 || endM > 60)
            throw new IllegalArgumentException("No se puede elegir como minuto menor a 0 o mayor a 60." +
                    "minuto de inicio :"+ startM+ " minuto de finalizacion: "+ endM);
        if(startH < 0 || endH < 0 || startH >= 24 || endH > 24)
            throw new IllegalArgumentException("No se puede elegir como horario de inicio fuera de las 24 hs del dia actual." +
                    "hora de inicio :"+ startM+ " hora de finalizacion: "+ endM);

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start = Calendar.getInstance();
        end = Calendar.getInstance();

        start.set(Calendar.HOUR_OF_DAY,startH);
        start.set(Calendar.MINUTE,startM);
        end.set(Calendar.HOUR_OF_DAY,endH);
        end.set(Calendar.MINUTE,endM);

        Long initMilis=start.getTimeInMillis();
        Long endMilis=end.getTimeInMillis();
        Long delta=TimeUnit.MILLISECONDS.toMinutes(endMilis-initMilis);
        if(Math.abs(delta)<30)
            throw new IllegalArgumentException("Minimo tiempo por reserva es de 30 minutos");

        this.starth = startH;
        this.endH = endH;
        this.startMins = startM;
        this.endMins = endM;
    }

    public boolean conflictsWith(Schedule that){

        if(this.start.after(that.end)|| this.end.before(that.start)){
            return false;
        }

        else if(this.end.equals(that.start) ||this.start.equals(that.end) )
            return false;

        return true;

    }

    public int getStart() {
        return starth;
    }

    public int getEnd() {
        return endH;
    }

    public int getStartMins() {
        return startMins;
    }

    public int getEndMins() {
        return endMins;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (starth != schedule.starth) return false;
        if (endH != schedule.endH) return false;
        if (startMins != schedule.startMins) return false;
        if (endMins != schedule.endMins) return false;
        if (!start.equals(schedule.start)) return false;
        return end.equals(schedule.end);

    }


    @Override
    public String toString(){
        return "Hora de inicio: "+ this.starth+" hs "+this.startMins+" minutos\n"+
                "Hora de finalizacion "+ this.endH+" hs "+ this.endMins+" minutos.";
    }
}
