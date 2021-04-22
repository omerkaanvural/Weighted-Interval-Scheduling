import java.time.LocalTime;

public class Assignment implements Comparable<Assignment> {
    private String name;
    private String start;
    private int duration;
    private int importance;
    private boolean maellard;

    public Assignment(String name, String start, int duration, int importance, boolean maellard) {
        this.name = name;
        this.start = start;
        this.duration = duration;
        this.importance = importance;
        this.maellard = maellard;
    }
    /*
            Getter methods
         */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isMaellard() {
        return this.maellard;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        // parsing starting time to convert it LocalTime object.
        LocalTime lt = LocalTime.parse(this.start);
        // add duration to start time.
        int ltfH = lt.getHour() + duration;
        int ltfM = lt.getMinute();
        // rebuild time object which represent finish time.
        LocalTime nlt = LocalTime.of(ltfH, ltfM);
        return String.valueOf(nlt);
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        // weight formula is specified in pdf.
        return (double)(importance * (maellard ? 1001:1)) / duration;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param ass Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Assignment ass) {
        return this.getFinishTime().compareTo(ass.getFinishTime());
    }

    /**
     * @return Should return a string in the following form:
     * Assignment{name='Refill vending machines', start='12:00', duration=1, importance=45, maellard=false, finish='13:00', weight=45.0}
     */
    @Override
    public String toString() {
        return "Assignment{name='" + this.name + "', start='" + this.start + "', duration=" + this.duration + ", importance=" + this.importance + ", maellard=" + this.maellard + ", finish='" + this.getFinishTime() + "', weight=" + getWeight() + "}";
    }

}
