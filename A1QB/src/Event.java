public class Event {

    // instance variables
    private String startTime;
    private String name;
    private int priority;

    // constructor
    public Event(String name, String startTime, int priority) {
        this.name = name;
        this.startTime = startTime;
        this.priority = priority;
    }

    public String toString() {
        String result = startTime + ": " + name + " (priority " + priority + ")";
        return result;
    }

    public String printHighPriorityOnly() {
        String result = name;
        return result;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
