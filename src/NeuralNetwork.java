import java.util.ArrayList;
import java.util.Vector;

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

    public void train(ArrayList<Vector<Double>> inputs, ArrayList<Vector<Double>> outputs){
        for (int i=0; i < inputs.size(); i++){
            //load inputs
            setInput(inputs.get(i));

            //calculate output
            calculateOutput();

            //propagate back - update weights
        }
    }

    private void setInput(Vector<Double> input){
        for (int i=0; i < inputNeuronsCount; i++){
            // assign input value to each neuron in input layer
            inputLayer.get(i).setOutput(input.get(i));
        }
    }

    private void calculateOutput(){
        for (Neuron neuron : hiddenLayer){
            neuron.calculateOutput();
        }
        // TODO
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

        for (Neuron n2 : hiddenLayer){
            for (Neuron n3: outputLayer){
                Synapse syn = new Synapse(n2, n3);
                synapsesL2toL3.add(syn);
                n3.addSynapse(syn);
            }
        }
    }
}
