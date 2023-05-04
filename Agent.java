import java.util.Arrays;

/*
 * ONLY EXIST TO FIND THE BEST POLYNOMIAL FOR A GIVEN SERIES
 * SEQUENCE IS THE ONE WE GENERATE vs SERIES IS THE SERIES WE'RE TRYING TO FIND A POLYNOMIAL FOR
 * GENES ARE THE COEFFICIENTS OF EACH PART OF THE SERIES: [7,3,1,0] = x^2 + 3x + 7 
 */
public class Agent implements Comparable<Agent>{
    public int[] accuracy;
    private int[] genes;
    private int[] sequence;
    private int[] series;
    private int maxTerms;
    private static int maxCoefficient = 200;

    public Agent(int[] genetics, int[] series) {
        genes = genetics;
        this.series = series;
        maxTerms = genes.length;
        sequence = generateSequence(genetics);
        accuracy = generateAccuracy();
    }

    public Agent(int numCoefficients, int[] series) {
        maxTerms = numCoefficients;
        int[] newGenes = new int[maxTerms];
        for(int i=0; i<maxTerms; i++) {
            newGenes[i] = (int)(Math.random() * maxCoefficient) - maxCoefficient/2;
        }
        genes = newGenes;
        this.series = series;
        sequence = generateSequence(genes);
        accuracy = generateAccuracy();
    }

    /*
     * Sequence = f(0), f(1), etc.. f(n) = genes[0] + genes[1]n + genes[2]n^2, etc..
     */
    private int[] generateSequence(int[] genes) {
        int[] terms = new int[series.length];        
        for(int i=0; i<series.length; i++) {
            int result = 0;
            for(int j=0; j<genes.length; j++) {
                result += genes[j]*(int)Math.pow(i+1, j);
            }
            terms[i] = result;
        }
        return terms;
    }

    private int[] generateAccuracy() {
        int[] score = new int[series.length];
        for(int i=0; i<series.length; i++) {
            score[i] = -Math.abs(series[i] - sequence[i]);
        }
        return score;
    }

    public boolean isCorrect() {
        return Arrays.equals(sequence, series);
    }

    /*
     * Returns who is correct, then who has the lowest coefficients (starting from the top)
     */
    @Override
    public int compareTo(Agent o) {
        if(o.accuracy.length != accuracy.length) {
            System.out.println("HELP WTF");
        }
        for(int i=o.accuracy.length-1; i>=0; i--) {
            if(accuracy[i] != o.accuracy[i]) {
                return o.accuracy[i] - accuracy[i];
            }
        }
        int[] otherAgentsGenes = o.getGenetics();
            for(int i=genes.length-1; i>=0; i--) {
                if(genes[i] != otherAgentsGenes[i]) {
                    return otherAgentsGenes[i] - genes[i];
                }
            }
            return 0;
        }

    public int[] getGenetics() {
        return genes;
    }

}
