import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class Scheduler {

    private Assignment[] assignmentArray;
    private Integer[] C;
    private Double[] max;
    private ArrayList<Assignment> solutionDynamic;
    private ArrayList<Assignment> solutionGreedy;


    /**
     * @throws IllegalArgumentException when the given array is empty
     */
    public Scheduler(Assignment[] assignmentArray) throws IllegalArgumentException {
        if (assignmentArray.length == 0) {
            throw new IllegalArgumentException();
        }
        this.C = new Integer[assignmentArray.length];
        this.assignmentArray = assignmentArray;
        for (Assignment as:
             assignmentArray) {
            System.out.println(as);
        }
        this.solutionDynamic = new ArrayList<>();
        this.solutionGreedy = new ArrayList<>();
        max = new Double[assignmentArray.length];
        calculateC();
        for (int i = 0; i < assignmentArray.length; i++) {
            max[i] = calculateMax(i);
        }

        for (int i: C) {
            System.out.println("c" + i);
        }


        // Should be instantiated with an Assignment array
        // All the properties of this class should be initialized here
    }

    /**
     * @param index of the {@link Assignment}
     * @return Returns the index of the last compatible {@link Assignment},
     * returns -1 if there are no compatible assignments
     */
    private int binarySearch(int index) {
        int hour = LocalTime.parse(assignmentArray[index].getStartTime()).getHour();
        System.out.println("hour " + hour);
        int minute = LocalTime.parse(assignmentArray[index].getStartTime()).getMinute();
        System.out.println("minute " + minute);
        int lo = 0, hi = index - 1;

        if (index == 0) {
            return - 1;
        }
        else {
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                int midHour = LocalTime.parse(assignmentArray[mid].getFinishTime()).getHour();
                System.out.println("mid hour " + midHour);
                int midMin = LocalTime.parse(assignmentArray[mid].getFinishTime()).getMinute();
                System.out.println("mid min " + midMin);
                if (midHour < hour || (midHour == hour && midMin <= minute)) {
                    int midPlusHour = LocalTime.parse(assignmentArray[mid + 1].getFinishTime()).getHour();
                    System.out.println("mid plus hour " + midPlusHour);
                    int midPlusMin = LocalTime.parse(assignmentArray[mid + 1].getFinishTime()).getMinute();
                    System.out.println("mid plus min " + midPlusMin);
                    if (midPlusHour < hour || (midPlusHour == hour && midPlusMin <= minute)) {
                        lo = mid + 1;
                    } else return mid;
                } else {
                    hi = mid - 1;
                }
            }
            return -1;
        }
    }


    /**
     * {@link #C} must be filled after calling this method
     */
    private void calculateC() {
        for(int i = 0; i < assignmentArray.length; i++) {
            C[i] = binarySearch(i);
        }
    }

    /**
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleDynamic() {
        findSolutionDynamic(assignmentArray.length - 1);
        return solutionDynamic;
    }

    /**
     * {@link #solutionDynamic} must be filled after calling this method
     */
    private void findSolutionDynamic(int i) {
        if (i == 0) {
            return;
        }
        else if (C[i] == -1) {
            C[i] = 0;
        }
        System.out.printf("findSolutionDynamic(%d)\n", i);
        if (assignmentArray[i].getWeight() + max[C[i]] >= max[i - 1]) {
            solutionDynamic.add(assignmentArray[i]);
            System.out.printf("Adding assignment%s to the dynamic schedule\n", assignmentArray[i]);
            findSolutionDynamic(C[i]);
        }
        else findSolutionDynamic(i - 1);
    }

    /**
     * {@link #max} must be filled after calling this method
     */
    private Double calculateMax(int i) {
        if (i == -1) {
            return (double) 0;
        }
        else {
            // getting max value to provide max efficiency when the next elements smaller than previous ones. (We pick bigger value but lower index in a nutshell)
            return Math.max(assignmentArray[i].getWeight()+calculateMax(C[i]), calculateMax(i-1));
        }
    }

    /**
     * {@link #solutionGreedy} must be filled after calling this method
     * Uses {@link #assignmentArray} property
     *
     * @return Returns a list of scheduled assignments
     */
    public ArrayList<Assignment> scheduleGreedy() {
        for (Assignment a: assignmentArray) {
            if (solutionGreedy.isEmpty()) {
                solutionGreedy.add(a);
                System.out.printf("Adding assignment%s to the greedy schedule\n", a);
            }
            else if ((LocalTime.parse(a.getStartTime()).getHour() >= LocalTime.parse(solutionGreedy.get(solutionGreedy.size() - 1).getFinishTime()).getHour())) {
                solutionGreedy.add(a);
                System.out.printf("Adding assignment%s to the greedy schedule\n", a);
            }
        }
        Collections.reverse(solutionGreedy);
        return solutionGreedy;
    }
}
