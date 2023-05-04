import java.util.Arrays;
import java.util.Random;

public class GeneticAlgorithm {
    private static GeneratingFunction m;
    private static int numAgents = 1024;
    private static int maxSeriesLength = 10;
    private static int numTries = 500;
    public static void main(String args[]) {
        Random generator = new Random(100);
        Agent[] agents = new Agent[numAgents];
        Agent[] newAgents = new Agent[numAgents]; 
        int[] function = {1,-1,1,-1};
        m = GeneratingFunction.getInstance(function);
        for(int i=0; i< numAgents; i++){
            agents[i] = new Agent(m.getTerms(), m.getSequence());
        }
        for(int i=0; i<maxSeriesLength; i++) {
            for(int n=0; n<numTries; n++) {
                Arrays.sort(agents);
                if(!agents[0].isCorrect()) {
                    for(int j=numAgents/2; j<numAgents; j++) {
                        int[] newGenes = getNewGenes(agents, generator);
                        newAgents[j] = new Agent(newGenes, m.getSequence());
                    }
                    agents = newAgents;
                }
                else {
                    n = numTries;
                }
            }
            if(!agents[0].isCorrect()) {
                //TRIES AGAIN
                for(int j=0; j< numAgents; j++){
                    agents[j] = new Agent(m.getTerms(), m.getSequence());
                }
                i--;
            }
            else {
                printCorrect(agents[0]);
                m.increaseTerms();
                for(int j=0; j<numAgents; j++) {
                    agents[j] = new Agent(m.getTerms(), m.getSequence());
                }
                System.out.println(Arrays.toString(m.getSequence()));
            }
        }
    }

    public static int[] getNewGenes(Agent[] agents, Random generator) {
        int[] parentGenes = agents[(int)(agents.length/10*generator.nextDouble())].getGenetics();
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
