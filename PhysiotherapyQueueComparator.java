import java.util.Comparator;

public class PhysiotherapyQueueComparator implements Comparator<Player>{

    @Override
    public int compare(Player o1, Player o2) {
        if(Double.compare(o1.getLastTrainingTime(), o2.getLastTrainingTime())>0){
            return -1;
        }
        else if(Double.compare(o1.getLastTrainingTime(), o2.getLastTrainingTime())<0){
            return 1;
        }
        else{
        	if(Double.compare(o1.getPhysiotherapistQueueEnterTime(), o2.getPhysiotherapistQueueEnterTime())<0) {
        		return -1;
        	}
        	else if(Double.compare(o1.getPhysiotherapistQueueEnterTime(), o2.getPhysiotherapistQueueEnterTime())>0) {
        		return 1;
        	}
        	else {if(o1.getID()<o2.getID()){
                return -1;
            }
            else{
                return 1;
            }}
            
        }
    }
    
}
