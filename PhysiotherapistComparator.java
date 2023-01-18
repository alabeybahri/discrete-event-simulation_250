import java.util.Comparator;

public class PhysiotherapistComparator implements Comparator<Physiotherapist>{

    @Override
    public int compare(Physiotherapist o1, Physiotherapist o2) {
        if(o1.getID()<o2.getID()){
            return -1;
        }
        else{
            return 1;
        }
        

        
    }
    
}
