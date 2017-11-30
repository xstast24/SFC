import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
	    int inputNeurons;
	    int hiddenNeurons;
	    int outputNeurons;

	    //TODO parse arguments
        String trainPath = "resources/set1";


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
        inputNeurons = trainInputs.get(0).size();
        hiddenNeurons = 2; //TODO get z parametru?
        outputNeurons = trainOutputs.get(0).size();
	    NeuralNetwork network = new NeuralNetwork(inputNeurons, hiddenNeurons, outputNeurons);

        //train network
        network.train(trainInputs, trainOutputs);
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
    * Exit program with error message and print help. Exit with code 1.
    * */
    static void exitWithMsgAndHelp(String msg){
        String help = "Neural network / backpropagation. For more info see documentation." + System.lineSeparator() +
                "-help : print help and exit" + System.lineSeparator() +
                "-train <path> : path to folder with training sets" + System.lineSeparator() +
                "-test <path> : path to folder with tested inputs" + System.lineSeparator();

        if (msg != null){
            System.out.print(msg + System.lineSeparator());
        }
        System.out.print(help);
        System.exit(1);
    }
}
