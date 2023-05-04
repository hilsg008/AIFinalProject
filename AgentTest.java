import java.util.Arrays;

public class AgentTest {

    public static void main(String args[]) {
        correctCompare();
        correctSequence();
    }
    
    /*
     * Proving that with a shorter series, even though x^3 is our generating function we find 6+11x+6x^2 as the "correct" answer.
     */
    public static void correctCompare() {
        int[] series = new int[3];
        for(int i=0; i<3; i++) {
            series[i] = (int)(Math.pow(i+1,3));
            System.out.println(series[i]);
        }
        int[] genes = {0,0,1,0};
        Agent incorrect = new Agent(genes, series);
        genes[0] = 1;
        Agent incorrect2 = new Agent(genes, series);
        int[] correctGenes = {0,0,0,1};
        Agent correct = new Agent(correctGenes,series);
        int[] shorterCorrectGenes = {6, -11, 6, 0};
        Agent correct2 = new Agent(shorterCorrectGenes,series); 
        System.out.println(
        incorrect.compareTo(incorrect2) >= 0
        && incorrect.compareTo(correct) < 0
        && correct.compareTo(incorrect) > 0
        && correct.compareTo(correct2) < 0
        && correct2.compareTo(incorrect2) > 0);
    }

    public static void correctSequence() {
        int[] series = {1,8,27};
        int[] genes = {0,0,0,1};
        int[] wronggenes = {0,0,1,0};
        Agent correct = new Agent(genes, series);
        Agent incorrect = new Agent(wronggenes, series);
        System.out.println(correct.isCorrect()
        && !incorrect.isCorrect());
    }
}
