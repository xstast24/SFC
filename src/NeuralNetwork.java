import java.util.ArrayList;

public class NeuralNetwork {
    protected final int inputNeuronsCount;
    protected final int hiddenNeuronsCount;
    protected final int outputNeuronsCount;
    protected final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
    protected final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
    protected final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
    protected final ArrayList<Synapse> synapsesL1toL2 = new ArrayList<Synapse>();
    protected final ArrayList<Synapse> synapsesL2toL3 = new ArrayList<Synapse>();

    public NeuralNetwork(int inputNeurons, int hiddenNeurons, int outputNeurons){
        inputNeuronsCount = inputNeurons;
        hiddenNeuronsCount = hiddenNeurons;
        outputNeuronsCount = outputNeurons;
        initialize(inputNeurons, hiddenNeurons, outputNeurons);
    }

    public void run(){
        System.out.println("Hello, I am Lindsay Lohan.");
    }

    public void initialize(int inputNeurons, int hiddenNeurons, int outputNeurons){
        // Create neurons
        while(inputNeurons > 0){
            inputLayer.add(new Neuron());
            inputNeurons--;
        }

        while(hiddenNeurons > 0){
            hiddenLayer.add(new Neuron());
            hiddenNeurons--;
        }

        while(outputNeurons > 0){
            outputLayer.add(new Neuron());
            outputNeurons--;
        }

        // Create synapses
        for (Neuron n1 : inputLayer){
            for (Neuron n2: hiddenLayer){
                Synapse syn = new Synapse(n1, n2);
                synapsesL1toL2.add(syn);
                n2.addSynapse(syn);
            }
        }

        for (Neuron n1 : hiddenLayer){
            for (Neuron n2: outputLayer){
                Synapse syn = new Synapse(n1, n2);
                synapsesL2toL3.add(syn);
                n2.addSynapse(syn);
            }
        }
    }
}
