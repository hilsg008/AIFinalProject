import java.util.Arrays;
import java.util.Random;
import java.time.Instant;
import java.time.Duration;

public class GeneticAlgorithm {
    private static GeneratingFunction m;
    private static int maxSeriesLength = 10;
    private static int numTries = 250;
    public static void main(String args[]) {
        Random generator = new Random(100);
        Agent[] agents = new Agent[0];
        Agent[] newAgents = new Agent[0]; 
        int[] function = {-4,-1,4,2};
        m = GeneratingFunction.getInstance(function);
        Instant time = Instant.now();
        for(int i=0; i<maxSeriesLength; i++) {
            int numAgents = (int)Math.pow(16, i+1);
            agents = new Agent[numAgents];
            newAgents = new Agent[numAgents];
            for(int j=0; j<numAgents; j++) {
                agents[j] = new Agent(m.getTerms(), m.getSequence());
            }
            for(int n=0; n<numTries; n++) {
                Arrays.sort(agents);
                if(!agents[0].isCorrect()) {
                    for(int j=numAgents/2; j<numAgents; j++) {
                        int[] newGenes = getNewGenes(agents, generator);
                        newAgents[j] = new Agent(newGenes, m.getSequence());
                    }
                    for(int j=0; j<numAgents/2; j++) {
                        newAgents[j] = agents[j];
                    }
                    agents = newAgents;
                }
                else {
                    n = numTries;
                }
            }
            if(!agents[0].isCorrect()) {
                //TRIES AGAIN
                System.out.println(Arrays.toString(agents[0].accuracy));
                i--;
            }
            else {
                System.out.println(Duration.between(time,Instant.now()));
                time = Instant.now();
                printCorrect(agents[0]);
                m.increaseTerms();
                System.out.println(Arrays.toString(m.getSequence()));
            }
        }
    }

    public static int[] getNewGenes(Agent[] agents, Random generator) {
        int[] parentGenes = agents[(int)(agents.length/5*generator.nextDouble())].getGenetics();
        parentGenes[(int)(parentGenes.length*generator.nextDouble())] += (int)(generator.nextDouble()*3 - 1);
        return parentGenes;
    }

    public static void printCorrect(Agent correctAgent) {
        System.out.println(Arrays.toString(m.getSequence()));
        System.out.println(correctAgent.isCorrect());
        int[] correctGenetics = correctAgent.getGenetics();
        for(int j=0; j<correctGenetics.length; j++) {
            System.out.print(correctGenetics[j] + "n^" + j + ", ");
        }
        System.out.println();
    }
}
