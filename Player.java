public class Player {
    private int ID;
    private int skillLevel;

    private double massageQueueWaitedTime = 0;
    private double physiotherapyQueueWaitedTime = 0;
    private double trainingQueueWaitedTime = 0;

    private double playerCurrentTime;
    private Physiotherapist physiotherapist;
    private double totalMassageTime = 0;
    private double totalTherapyTime = 0;
    private double totalTrainingTime = 0;

    private double numberOfMassagesDone = 0;

    private double lastTrainingTime = 0;

    private double tempTrainingTime;
    private double tempMassageTime;

    private double trainingQueueEnterTime;
    private double massageQueueEnterTime;
    private double physiotherapistQueueEnterTime;

    private double totalTurnaroudTime = 0;
    private boolean playerPosition = true;


    public Player(int iD, int skillLevel) {
        ID = iD;
        this.skillLevel = skillLevel;
    }
    public int getID() {
        return ID;
    }

    public int getSkillLevel() {
        return skillLevel;
    }
    public double getMassageQueueWaitedTime() {
        return massageQueueWaitedTime;
    }
    public void increaseMassageQueueWaitedTime(double massageQueueWaitedTime) {
        this.massageQueueWaitedTime += massageQueueWaitedTime;
    }
    public double getPhysiotherapyQueueWaitedTime() {
        return physiotherapyQueueWaitedTime;
    }
    public void increasePhysiotherapyQueueWaitedTime(double physiotherapyQueueWaitedTime) {
        this.physiotherapyQueueWaitedTime += physiotherapyQueueWaitedTime;
    }
    public double getTrainingQueueWaitedTime() {
        return trainingQueueWaitedTime;
    }
    public void increaseTrainingQueueWaitedTime(double trainingQueueWaitedTime) {
        this.trainingQueueWaitedTime += trainingQueueWaitedTime;
    }
    public double getTotalMassageTime() {
        return totalMassageTime;
    }
    public void increaseTotalMassageTime(double totalMassageTime) {
        this.totalMassageTime += totalMassageTime;
    }
    public double getTotalTherapyTime() {
        return totalTherapyTime;
    }
    public void increaseTotalTherapyTime(double totalTherapyTime) {
        this.totalTherapyTime += totalTherapyTime;
    }
    public double getTotalTrainingTime() {
        return totalTrainingTime;
    }
    public void increaseTotalTrainingTime(double totalTrainingTime) {
        this.totalTrainingTime += totalTrainingTime;
    }
    public double getNumberOfMassagesDone() {
        return numberOfMassagesDone;
    }
    public void increaseNumberOfMassagesDone() {
        this.numberOfMassagesDone += 1;
    }
    
    public boolean canTakeMassage(){
        if(this.numberOfMassagesDone<3){
            return true;
        }
        return false;
    }
    public double getLastTrainingTime() {
        return lastTrainingTime;
    }
    public void setLastTrainingTime(double lastTrainingTime) {
        this.lastTrainingTime = lastTrainingTime;
    }
    
    public void trainProcess(double trainingTime){
        setLastTrainingTime(trainingTime);
        increaseTotalTrainingTime(trainingTime);
    }

    public void massageProcess(double massageTime){
        increaseTotalMassageTime(massageTime);
        //increaseNumberOfMassagesDone();
    }

    public void therapyProcess(double therapyTime){
        increaseTotalTherapyTime(therapyTime);
    }
    public double getTempTrainingTime() {
        return tempTrainingTime;
    }
    public void setTempTrainingTime(double tempTrainingTime) {
        this.tempTrainingTime = tempTrainingTime;
    }

    public double getTrainingQueueEnterTime() {
        return trainingQueueEnterTime;
    }
    public void setTrainingQueueEnterTime(double trainingQueueEnterTime) {
        this.trainingQueueEnterTime = trainingQueueEnterTime;
    }
    public double getMassageQueueEnterTime() {
        return massageQueueEnterTime;
    }
    public void setMassageQueueEnterTime(double massageQueueEnterTime) {
        this.massageQueueEnterTime = massageQueueEnterTime;
    }
    public double getPhysiotherapistQueueEnterTime() {
        return physiotherapistQueueEnterTime;
    }
    public void setPhysiotherapistQueueEnterTime(double physiotherapistQueueEnterTime) {
        this.physiotherapistQueueEnterTime = physiotherapistQueueEnterTime;
    }
    public double getTotalTurnaroudTime() {
        return totalTurnaroudTime;
    }
    public void increaseTotalTurnaroudTime(double totalTurnaroudTime) {
        this.totalTurnaroudTime += totalTurnaroudTime;
    }
    public double getTempMassageTime() {
        return tempMassageTime;
    }
    public void setTempMassageTime(double tempMassageTime) {
        this.tempMassageTime = tempMassageTime;
    }
    public boolean getPlayerPosition() {
        return playerPosition;
    }
    public void setPlayerPosition(Boolean playerPosition) {
        this.playerPosition = playerPosition;
    }
    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }
    public void setPhysiotherapist(Physiotherapist physiotherapist) {
        this.physiotherapist = physiotherapist;
    }
	public double getPlayerCurrentTime() {
		return playerCurrentTime;
	}
	public void setPlayerCurrentTime(double playerCurrentTime) {
		this.playerCurrentTime = playerCurrentTime;
	}
    
    
}
