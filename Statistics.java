import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Statistics {
    
    double totalTrainingWaitedTime = 0;     double totalMassageWaitedTime = 0;  double totalPhysiotherapistWaitedTime = 0;
    double totalMassageTime = 0;            double totalTrainingTime = 0;       double totalTherapyTime = 0;
    double totalTurnaroudTime = 0;
    double maxTimeSpentOnPhysiotherapyQueue = -1;   int maxTimeSpentByThis = -1;
    int IDOfPlayerThreeMassage = Integer.MAX_VALUE;
    double minWaitingTimeAmongThreeMassages = Double.POSITIVE_INFINITY;
    int validTrainingNumber;
    int validMassageNumber;
    int maxLengthOfTrainingQueue;
    int maxLengthOfPhysiotherapyQueue;
    int maxLengthOfMassageQueue;
    int numberOfInvalidAttempts;
    int numberOfCancelledAttempts;
    double totalTimePassedOnSimulation;
    HashMap<Integer,Player> playersHashMap;

    public Statistics(HashMap<Integer, Player> playersHashMap,int validTrainingNumber,int validMassageNumber,int maxLengthOfTrainingQueue,
    		int maxLengthOfPhysiotherapyQueue,int maxLengthOfMassageQueue,int numberOfInvalidAttempts,int numberOfCancelledAttempts,
    		double totalTimePassedOnSimulation) {
        this.playersHashMap = playersHashMap;
        this.validTrainingNumber = validTrainingNumber;
        this.validMassageNumber = validMassageNumber;
        this.maxLengthOfTrainingQueue = maxLengthOfTrainingQueue;
        this.maxLengthOfPhysiotherapyQueue = maxLengthOfPhysiotherapyQueue;
        this.maxLengthOfMassageQueue = maxLengthOfMassageQueue;
        this.numberOfInvalidAttempts = numberOfInvalidAttempts;
        this.numberOfCancelledAttempts = numberOfCancelledAttempts;
        this.totalTimePassedOnSimulation = totalTimePassedOnSimulation;

    }
    
    ArrayList<String> statistics = new ArrayList<String>();

    public ArrayList<String> makeStats(){
        for (Map.Entry<Integer, Player> entry : playersHashMap.entrySet()) {
            Integer playerID = entry.getKey();
            Player player = entry.getValue();
            totalTrainingWaitedTime += player.getTrainingQueueWaitedTime();
            totalMassageWaitedTime += player.getMassageQueueWaitedTime();
            totalPhysiotherapistWaitedTime += player.getPhysiotherapyQueueWaitedTime();
            totalTherapyTime += player.getTotalTherapyTime();
            totalMassageTime += player.getTotalMassageTime();
            totalTrainingTime += player.getTotalTrainingTime();
            totalTurnaroudTime += player.getTotalTurnaroudTime();
            if(maxTimeSpentOnPhysiotherapyQueue<player.getPhysiotherapyQueueWaitedTime()){
                maxTimeSpentByThis = playerID;
                maxTimeSpentOnPhysiotherapyQueue = player.getPhysiotherapyQueueWaitedTime();
            }
            if(player.getNumberOfMassagesDone()==3){
                if(minWaitingTimeAmongThreeMassages>player.getMassageQueueWaitedTime()){
                    minWaitingTimeAmongThreeMassages = player.getMassageQueueWaitedTime();
                    IDOfPlayerThreeMassage = playerID;
                }
                else if(minWaitingTimeAmongThreeMassages==player.getMassageQueueWaitedTime()){
                    if(IDOfPlayerThreeMassage>playerID){
                        minWaitingTimeAmongThreeMassages = player.getMassageQueueWaitedTime();
                        IDOfPlayerThreeMassage = playerID;
                    }
                }
            }


            
        }
        Locale.setDefault(Locale.US);
        statistics.add(String.valueOf(maxLengthOfTrainingQueue));
        statistics.add(String.valueOf(maxLengthOfPhysiotherapyQueue));
        statistics.add(String.valueOf(maxLengthOfMassageQueue));
        if(validTrainingNumber!=0){
            statistics.add(String.format("%.3f",totalTrainingWaitedTime/validTrainingNumber));
            statistics.add(String.format("%.3f", totalPhysiotherapistWaitedTime/validTrainingNumber));
        }
        else if(validTrainingNumber==0){
            statistics.add(String.valueOf(0));
            statistics.add(String.valueOf(0));
        }
        if(validMassageNumber!=0){
            statistics.add(String.format("%.3f", totalMassageWaitedTime/validMassageNumber));
        }
        else if(validMassageNumber==0){
            statistics.add(String.valueOf(0));
        }
        if(validTrainingNumber!=0){
            statistics.add(String.format("%.3f", totalTrainingTime/validTrainingNumber));
            statistics.add(String.format("%.3f", totalTherapyTime/validTrainingNumber));
        }
        else if(validTrainingNumber==0){
            statistics.add(String.valueOf(0));
            statistics.add(String.valueOf(0));
        }
        if(validMassageNumber!=0){
            statistics.add(String.format("%.3f", totalMassageTime/validMassageNumber));
        }
        else if(validMassageNumber==0){
            statistics.add(String.valueOf(0));
        }
        if(validTrainingNumber!=0){
            statistics.add(String.format("%.3f", totalTurnaroudTime/validTrainingNumber));
        }
        else if(validTrainingNumber==0){
            statistics.add(String.valueOf(0));
        }
        String line1 = String.valueOf(maxTimeSpentByThis) + " " + String.format("%.3f", maxTimeSpentOnPhysiotherapyQueue);
        statistics.add(line1);
        if(IDOfPlayerThreeMassage==Integer.MAX_VALUE||minWaitingTimeAmongThreeMassages==Double.POSITIVE_INFINITY){
            String line2aString = -1 + " " + -1;
            statistics.add(line2aString);
        }
        else{
            String line2 = String.valueOf(IDOfPlayerThreeMassage) + " " + String.format("%.3f", minWaitingTimeAmongThreeMassages);
            statistics.add(line2);
        }

        statistics.add(String.valueOf(numberOfInvalidAttempts));
        statistics.add(String.valueOf(numberOfCancelledAttempts));
        statistics.add(String.format("%.3f", totalTimePassedOnSimulation));
        return statistics;

    }
    
}
