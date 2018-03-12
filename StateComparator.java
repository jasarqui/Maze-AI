import java.util.Comparator;

// create a comparator class for integer
public class StateComparator implements Comparator<State> {
    @Override
    // override compare to handle integers
    public int compare(State stateA, State stateB) {
        // return lesser index
        if (stateA.getOverallCost() < stateB.getOverallCost()) {
            return 1;
        }
        // return higher index
        if (stateA.getOverallCost() > stateB.getOverallCost()) {
            return -1;
        }
        // return same index
        return 0;
    }
}