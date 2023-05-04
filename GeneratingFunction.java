
public class GeneratingFunction {
    private static GeneratingFunction SINGLE_INSTANCE = null;

    private static int[] coefficients;
    private static int numTerms;

    private GeneratingFunction(int[] coefficients) {
        this.coefficients = coefficients;
        numTerms =  1;
    }

    public static synchronized GeneratingFunction getInstance(int[] coefficients) {
        if(SINGLE_INSTANCE == null) {
            SINGLE_INSTANCE = new GeneratingFunction(coefficients);
        }
        return SINGLE_INSTANCE;
    }

    public static GeneratingFunction getInstance() {
        if(SINGLE_INSTANCE == null) {
            return null;
        }
        return SINGLE_INSTANCE;
    }

    public int[] getSequence() {
        int[] terms = new int[numTerms];        
        for(int i=0; i<numTerms; i++) {
            int result = 0;
            for(int j=0; j<coefficients.length; j++) {
                result += coefficients[j]*(int)Math.pow(i+1, j);
            }
            terms[i] = result;
        }
        return terms;
    }

    public int getTerms() {
        return numTerms;
    }

    public void increaseTerms() {
        numTerms+= 1;
    }
}