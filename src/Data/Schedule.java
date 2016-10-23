package Data;

import java.util.Date;

/**
 * Created by Agus on 23/10/2016.
 */
public class Schedule {
    Date start;
    Date end;


    public Schedule(int startH,int startM,int endH,int endM){
           if(false){
               //TODO manejar excepciones de tiempo
           }
            start=new Date();
            end=new Date();
           start.setHours(startH);
            start.setMinutes(startM);
            end.setHours(endH);
            end.setMinutes(endM);
            //TODO tener en cuenta minutos


    }


}
