import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
	    //load program configuration
        Config cfg = new Config();
        String trainPath = cfg.getProperty("trainingSetsPath");
        String testPath = cfg.getProperty("testInputPath") ;

        //load training sets from directory
        ArrayList<Vector<Double>> trainInputs = new ArrayList<>();
        ArrayList<Vector<Double>> trainOutputs = new ArrayList<>();
        for (String path : getFilepathsFromDir(trainPath)){
            //check each file - if it is input or output, and load data accordingly
            File file = new File(path);
            if (file.getName().startsWith("in")){
                //load input data and add it to input array
                Vector<Double> data = loadDataFromFile(path);
                trainInputs.add(data);
            }
            else if (file.getName().startsWith("out")){
                //load input data and add it to output array
                Vector<Double> data = loadDataFromFile(path);
                trainOutputs.add(data);
            }
        }
        // number of inputs must match number of outputs
        if(trainInputs.size() != trainOutputs.size()){
            exitWithMsgAndHelp("Training sets do not match - input/output count is different.");
        }

        //create neural network
        int inputNeurons = trainInputs.get(0).size();
        int hiddenNeurons = cfg.getPropertyAsInt("hiddenNeuronsCount");
        int outputNeurons = trainOutputs.get(0).size();
	    NeuralNetwork network = new NeuralNetwork(inputNeurons, hiddenNeurons, outputNeurons);

        //train network
        int trainCycles = cfg.getPropertyAsInt("maxTrainCycles");
        double minError = cfg.getPropertyAsDouble("minTrainError");
        double trainCoef = cfg.getPropertyAsDouble("learningCoeficient");
        network.train(trainInputs, trainOutputs, trainCycles, trainCoef, minError);


        //load test input
        Vector<Double> testInput = loadDataFromFile(testPath);

        //run test input through network
        network.run(testInput);
    }

    /*
    * Read file and load all integers from it (separated by whitespaces).
    * */
    static Vector<Double> loadDataFromFile(String path){
        Vector<Double> data = new Vector<>();

        try {
            Scanner input = new Scanner(new File(path));
            while(input.hasNextLine())
            {
                Scanner lineReader = new Scanner(input.nextLine());
                while(lineReader.hasNextInt())
                {
                    data.add((double)lineReader.nextInt());
                }
            }
        } catch (java.io.FileNotFoundException e){
            exitWithMsgAndHelp("Error loading data from file: " + path);
        }

        return data;
    }

    /*
    * Find all files (not directories) in given directory.
    * Return alphabetically sorted list of their absolute paths.
    * */
    static ArrayList<String> getFilepathsFromDir(String path) {
        ArrayList<String> results = new ArrayList<>();
        File directory = new File(path);

        File[] files = directory.listFiles();
        if (files == null){
            exitWithMsgAndHelp("Error loading training set files, no files found in given directory.");
        } else {
            Arrays.sort(files);
            for (File item : files) {
                if (item.isFile()) {
                    String itemPath = item.getAbsolutePath();
                    results.add(itemPath);
                }
            }
        }

        return results;
    }

    /*
    * Exit program with error message. Exit with code 1.
    * */
    static void exitWithMsgAndHelp(String msg){
        if (msg != null){
            System.out.print(msg + System.lineSeparator());
        }
        System.exit(1);
    }
}
