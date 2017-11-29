import java.util.*;
import java.io.*;
import java.nio.*;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
	    int inputNeurons = 2;
	    int hiddenNeurons = 2;
	    int outputNeurons = 1;

        //TODO parse args
        //load training set from directory, store files to array
        String trainPath = "resources/set1";
        for (String path : getFilepathsFromDir(trainPath)){
            File file = new File(path);
            if (file.getName().startsWith("in")){
                Vector<Double> data = loadDataFromFile(path);
                // add it to input array
            }
            else if (file.getName().startsWith("out")){
                Vector<Double> data = loadDataFromFile(path);
                // add it to output array
            }
        }


        Vector<Double> input = new Vector<>();
		input.add(1.0);
		input.add(0.0);

		Vector<Double> output = new Vector<>();

	    NeuralNetwork network = new NeuralNetwork(inputNeurons, hiddenNeurons, outputNeurons);
	    network.run();
    }

    static Vector<Double> loadDataFromFile(String path){
        Vector<Double> data = new Vector<>();
        File file = new File(path);

        try {
            Scanner input = new Scanner(file);
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

    static void exitWithMsgAndHelp(String msg){
        String help = "Neural network / backpropagation. For more info see documentation." + System.lineSeparator() +
                "-help : print help and exit" + System.lineSeparator() +
                "-train <path> : path to folder with training sets" + System.lineSeparator() +
                "-test <path> : path to folder with tested inputs" + System.lineSeparator();

        System.out.print(msg + System.lineSeparator());
        System.out.print(help);
        System.exit(1);
    }
}
