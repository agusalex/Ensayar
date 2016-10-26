package Data;

import java.util.Calendar;

/**
 * Created by Agus on 23/10/2016.
 */
public class Schedule {
    private Calendar start, end;
    private int starth, endH, startMins, endMins;


    public Schedule(int startH,int startM,int endH,int endM){

        if(startH == endH)
            throw new IllegalArgumentException("No se puede elegir el mismo horario como inicio y final. " +
                    "hora de inicio: "+ startH+" hora finalizacion"+ endH);
        if(endH < startH)
            throw new IllegalArgumentException("No se puede elegir como hora inicial una posterior a la final. " +
                    "Hora inicial: " + startH+" hora finalizacion"+ endH);
        if(startM < 0 || endM < 0 || startM > 60 || endM > 60)
            throw new IllegalArgumentException("No se puede elegir como minuto menor a 0 o mayor a 60." +
                    "minuto de inicio :"+ startM+ " minuto de finalizacion: "+ endM);
        if(startM < 0 || endM < 0 || startM > 24 || endM > 24)
            throw new IllegalArgumentException("No se puede elegir como horario de inicio fuera de las 24 hs del dia actual." +
                    "minuto de inicio :"+ startM+ " minuto de finalizacion: "+ endM);

        start = Calendar.getInstance();
        end = Calendar.getInstance();
        start = Calendar.getInstance();
        end = Calendar.getInstance();

        start.set(Calendar.HOUR_OF_DAY,startH);
        start.set(Calendar.MINUTE,startM);
        end.set(Calendar.HOUR_OF_DAY,endH);
        end.set(Calendar.MINUTE,endM);

        this.starth = startH;
        this.endH = endH;
        this.startMins = startM;
        this.endMins = endM;
    }

    public boolean conflictsWith(Schedule that){

        if(this.start.after(that.end)|| this.end.before(that.start)){
            return false;
        }

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
    public String toString(){
        return "Hora de inicio: "+ this.starth+" hs "+this.startMins+" minutos\n"+
                "Hora de finalizacion "+ this.endH+" hs "+ this.endMins+" minutos.";
    }
}
