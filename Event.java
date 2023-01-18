public class Event {
    private Player player;
    private double time;
    private double processTime;
    private String eventType;

    

    @Override
    public String toString() {
        return "Event [eventType=" + eventType + ", player=" + player.getID() + ", processTime=" + processTime+ "]";
    }
    public Event(Player player, double time, double processTime, String eventType) {
        this.player = player;
        this.time = time;
        this.processTime = processTime;
        this.eventType = eventType;
    }
    public Event(Player player, double time, String eventType){
        this.player = player;
        this.time = time;
        this.eventType = eventType;
    }

    public double getTime() {
        return time;
    }

    public String getEventType() {
        return eventType;
    }

    public Player getPlayer() {
        return player;
    }

    public double getProcessTime() {
        return processTime;
    }

    
}
