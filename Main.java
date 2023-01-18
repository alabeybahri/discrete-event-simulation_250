import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class project2main {
    public static void main(String[] args) throws Exception {
        String inputFileName = args[0];         String outputFileName = args[1];
        File input = new File(inputFileName);   File output = new File(outputFileName);
        Scanner scanner = new Scanner(input);   FileWriter writer = new FileWriter(output);
        HashMap<Integer,Player> playersHashMap = new HashMap<Integer,Player>();
        PriorityQueue<Event> eventsQueue = new PriorityQueue<Event>(new EventComparator());
        PriorityQueue<Physiotherapist> availablePhysiotherapistsQueue = new PriorityQueue<Physiotherapist>(new PhysiotherapistComparator());
        PriorityQueue<Player> massageQueue = new PriorityQueue<>(new MassageQueueComparator());
        PriorityQueue<Player> physiotherapyQueue = new PriorityQueue<Player>(new PhysiotherapyQueueComparator());
        Queue<Player> trainingQueue = new LinkedList<Player>();

        int maxLengthOfTrainingQueue = 0;   int maxLengthOfPhysiotherapyQueue = 0;   int maxLengthOfMassageQueue = 0;
        int numberOfInvalidAttempts = 0;    int numberOfCancelledAttempts = 0;		 double totalTimePassedOnSimulation = 0;
        int validTrainingNumber = 0;        int validMassageNumber = 0; 			 
        
        int numberOfPlayer = Integer.parseInt(scanner.nextLine());
        for(int i=0;i<numberOfPlayer;i++){
            int playerID = scanner.nextInt();
            int playerSkillLevel = scanner.nextInt();
            Player player = new Player(playerID, playerSkillLevel);
            playersHashMap.put(playerID, player);
        }
        int numberOfArrivals = scanner.nextInt();
        for(int i=0;i<numberOfArrivals;i++){
            String eventType = scanner.next();
            int playerIndex = scanner.nextInt();
            double startTime = Double.parseDouble(scanner.next());
            double processTime = Double.parseDouble(scanner.next());
            Event event = new Event(playersHashMap.get(playerIndex) ,startTime, processTime, eventType);
            eventsQueue.add(event);
        }
        int numberOfPhysiotherapists = Integer.parseInt(scanner.next());
        for(int i=0;i<numberOfPhysiotherapists;i++){
            double serviceTime = Double.parseDouble(scanner.next());
            Physiotherapist physiotherapist = new Physiotherapist(serviceTime,i);
            availablePhysiotherapistsQueue.add(physiotherapist);
        }
        int numberOfTrainers = scanner.nextInt();
        int numberOfMasseurs = scanner.nextInt();
        while(!(eventsQueue.isEmpty())){
            Event eventToProcess = eventsQueue.poll();
            if(eventsQueue.isEmpty()){
                totalTimePassedOnSimulation = eventToProcess.getTime();}
            switch(eventToProcess.getEventType()){
                case "t": // Train
                    Player Tplayer = eventToProcess.getPlayer();
                    double TcurrentTime = eventToProcess.getTime();
                    double TprocessTime = eventToProcess.getProcessTime();
                    Tplayer.setPlayerCurrentTime(TcurrentTime);
                    if(!Tplayer.getPlayerPosition()){
                        numberOfCancelledAttempts++;
                        break;}
                    validTrainingNumber++;
                    Tplayer.setPlayerPosition(false);
                    if(trainingQueue.isEmpty()&&numberOfTrainers>0){
                        Tplayer.trainProcess(TprocessTime);
                        Tplayer.setTrainingQueueEnterTime(TcurrentTime);
                        Tplayer.setLastTrainingTime(TprocessTime);
                        Event tailEvent = new Event(Tplayer,TcurrentTime+TprocessTime , "PT");
                        eventsQueue.add(tailEvent);
                        numberOfTrainers--;}
                    else{
                        trainingQueue.add(Tplayer);
                        maxLengthOfTrainingQueue = Math.max(maxLengthOfTrainingQueue, trainingQueue.size());
                        Tplayer.setTrainingQueueEnterTime(TcurrentTime);
                        Tplayer.setTempTrainingTime(TprocessTime);
                    }
                    break;
                case "PT": // Physiotherapy
                    numberOfTrainers++;
                    Player PTplayer = eventToProcess.getPlayer();
                    double PTcurrentTime = eventToProcess.getTime();
                    PTplayer.setPlayerCurrentTime(PTcurrentTime);
                    if(physiotherapyQueue.isEmpty()&&!availablePhysiotherapistsQueue.isEmpty()){
                        Physiotherapist physiotherapist = availablePhysiotherapistsQueue.poll();
                        PTplayer.setPhysiotherapist(physiotherapist);
                        double PTprocessTime = physiotherapist.getServiceTime();
                        PTplayer.therapyProcess(PTprocessTime);
                        Event tailEvent = new Event(PTplayer, PTcurrentTime+PTprocessTime, "LPT");
                        eventsQueue.add(tailEvent);
                    }
                    else{
                        physiotherapyQueue.add(PTplayer);
                        maxLengthOfPhysiotherapyQueue = Math.max(maxLengthOfPhysiotherapyQueue, physiotherapyQueue.size());
                        eventToProcess.getPlayer().setPhysiotherapistQueueEnterTime(eventToProcess.getTime());

                    }
                    if(!trainingQueue.isEmpty()&&numberOfTrainers>0){
                        numberOfTrainers--;
                        Player TplayerFromQueue = trainingQueue.poll();
                        TplayerFromQueue.setPlayerCurrentTime(PTcurrentTime);
                        double TprocessTimePT = TplayerFromQueue.getTempTrainingTime();
                        TplayerFromQueue.trainProcess(TprocessTimePT);
                        double TqueueEnterTime = TplayerFromQueue.getTrainingQueueEnterTime();
                        TplayerFromQueue.increaseTrainingQueueWaitedTime(PTcurrentTime-TqueueEnterTime);
                        TplayerFromQueue.setLastTrainingTime(TprocessTimePT);
                        Event tailEvent = new Event(TplayerFromQueue,PTcurrentTime+TprocessTimePT, "PT");
                        eventsQueue.add(tailEvent);
                    }

                break;
                case "LPT": // Left-Physiotherapy
                    Player LPTplayer = eventToProcess.getPlayer();
                    double LPTcurrentTime = eventToProcess.getTime();
                    double TQenterTime = LPTplayer.getTrainingQueueEnterTime();
                    LPTplayer.setPlayerCurrentTime(LPTcurrentTime);
                    availablePhysiotherapistsQueue.add(LPTplayer.getPhysiotherapist());
                    LPTplayer.setPlayerPosition(true);
                    LPTplayer.increaseTotalTurnaroudTime(LPTcurrentTime-TQenterTime);
                    if(!physiotherapyQueue.isEmpty()&&!availablePhysiotherapistsQueue.isEmpty()){            
                        Player PTplayerFromQueue = physiotherapyQueue.poll();
                        PTplayerFromQueue.setPlayerCurrentTime(LPTcurrentTime);
                        Physiotherapist physiotherapist = availablePhysiotherapistsQueue.poll();
                        PTplayerFromQueue.setPhysiotherapist(physiotherapist);
                        double PTprocessTimeLPT = physiotherapist.getServiceTime();
                        double PTqueueEnterTime = PTplayerFromQueue.getPhysiotherapistQueueEnterTime();
                        PTplayerFromQueue.increasePhysiotherapyQueueWaitedTime(LPTcurrentTime-PTqueueEnterTime);
                        PTplayerFromQueue.therapyProcess(PTprocessTimeLPT);
                        Event tailEvent = new Event(PTplayerFromQueue,LPTcurrentTime+PTprocessTimeLPT, "LPT");
                        eventsQueue.add(tailEvent);
                    }
                    break;

                case "m": // Massage
                    double McurrentTime = eventToProcess.getTime();
                    double MprocessTime = eventToProcess.getProcessTime();
                    Player Mplayer = eventToProcess.getPlayer();
                	Mplayer.setPlayerCurrentTime(McurrentTime);
                    if(!Mplayer.canTakeMassage()){
                        numberOfInvalidAttempts++;
                        break;}
                        else{
                            if(!Mplayer.getPlayerPosition()){
                                numberOfCancelledAttempts++;
                                break;}
                        }
                    Mplayer.setPlayerPosition(false);
                    validMassageNumber++;
                    if(massageQueue.isEmpty()&&numberOfMasseurs>0){
                        Mplayer.increaseNumberOfMassagesDone();
                        numberOfMasseurs--;
                        Mplayer.massageProcess(MprocessTime);
                        Event tailEvent = new Event(Mplayer,McurrentTime+MprocessTime, "LM");
                        eventsQueue.add(tailEvent);
                        Mplayer.setMassageQueueEnterTime(McurrentTime);
                    }
                    else{
                        Mplayer.increaseNumberOfMassagesDone();
                        Mplayer.setMassageQueueEnterTime(McurrentTime);
                        massageQueue.add(Mplayer);
                        maxLengthOfMassageQueue = Math.max(maxLengthOfMassageQueue, massageQueue.size());
                        Mplayer.setTempMassageTime(MprocessTime);
                        }
                    break;
                case "LM": // Left-Massage
                    double LMcurrentTime = eventToProcess.getTime();
                    Player LMplayer = eventToProcess.getPlayer();
                    LMplayer.setPlayerCurrentTime(LMcurrentTime);
                    numberOfMasseurs++;
                    LMplayer.setPlayerPosition(true);
                    if(!(massageQueue.isEmpty())&&numberOfMasseurs>0){
                        numberOfMasseurs--;
                        Player MplayerFromQueue = massageQueue.poll();
                        MplayerFromQueue.setPlayerCurrentTime(LMcurrentTime);
                        double MprocessTimeLM = MplayerFromQueue.getTempMassageTime();
                        double MQenterTime = MplayerFromQueue.getMassageQueueEnterTime();
                        MplayerFromQueue.massageProcess(MprocessTimeLM);
                        MplayerFromQueue.increaseMassageQueueWaitedTime(LMcurrentTime-MQenterTime);
                        Event tailEvent = new Event(MplayerFromQueue,LMcurrentTime+MprocessTimeLM, "LM");
                        eventsQueue.add(tailEvent);
                    }
                    break;
            }

        }
        Statistics statistics = new Statistics(playersHashMap,validTrainingNumber,validMassageNumber,
        maxLengthOfTrainingQueue,maxLengthOfPhysiotherapyQueue,maxLengthOfMassageQueue,
        numberOfInvalidAttempts,numberOfCancelledAttempts,totalTimePassedOnSimulation);
        ArrayList<String> stats = statistics.makeStats();
        for(int i=0;i<stats.size();i++) {
        	if(i==stats.size()-1) {
            	writer.write(stats.get(i));
        	}
        	else {
        		writer.write(stats.get(i));
        		writer.write("\n");     		
        	}
        }

        scanner.close();
        writer.close();
    }
}