// Massage queue comparator, I put players on massage queue, fir

import java.util.Comparator;

public class MassageQueueComparator implements Comparator<Player>{

    @Override
    public int compare(Player o1, Player o2) {
        if(o1.getSkillLevel()>o2.getSkillLevel()){
            return -1;
        }
        else if(o1.getSkillLevel()<o2.getSkillLevel()){
            return 1;
        }
        else{
        	if(Double.compare(o1.getMassageQueueEnterTime(), o2.getMassageQueueEnterTime())<0) {
        		return -1;
        	}
        	else if(Double.compare(o1.getMassageQueueEnterTime(), o2.getMassageQueueEnterTime())>0) {
        		return 1;
        	}
        	else {
        		if(o1.getID()<o2.getID()){
                    return -1;
                }
                else{
                    return 1;
                }
        	}
            
        }
    }
    
}
