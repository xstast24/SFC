import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
	    int inputNeurons = 2;
	    int hiddenNeurons = 2;
	    int outputNeurons = 1;

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


        Vector<Double> input = new Vector<>();
		input.add(1.0);
		input.add(0.0);

		Vector<Double> output = new Vector<>();

	    NeuralNetwork network = new NeuralNetwork(inputNeurons, hiddenNeurons, outputNeurons);
	    network.run();
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
    * Find all files (not directories) in given directory. Return list of their absolute paths.
    * */
    static ArrayList<String> getFilepathsFromDir(String path) {
        ArrayList<String> results = new ArrayList<>();
        File directory = new File(path);

        File[] files = directory.listFiles();
        if (files == null){
            exitWithMsgAndHelp("Error loading training set files, no files found in given directory.");
        } else {
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
