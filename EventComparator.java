// Comparator for main event queue 
// First compare using time, then if times are equal check the ID, pick the lower ID 

import java.util.Comparator;

public class EventComparator implements Comparator<Event>{

    @Override
    public int compare(Event o1, Event o2) {
        if(Double.compare(o1.getTime(), o2.getTime())<0){
            return -1;
        }
        else if(Double.compare(o1.getTime(), o2.getTime())>0){
            return 1;
        }
        else{
            if(o1.getPlayer().getID()>o2.getPlayer().getID()){
                return 1;
            }
            else{
                return -1;
            }
        }
    }
    
    
}
