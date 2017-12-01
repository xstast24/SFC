import java.util.ArrayList;
import java.util.Vector;

public class NeuralNetwork {
    private final int inputNeuronsCount;
    private final int hiddenNeuronsCount;
    private final int outputNeuronsCount;
    private final ArrayList<Neuron> inputLayer = new ArrayList<Neuron>();
    private final ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
    private final ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();

    public NeuralNetwork(int inputNeurons, int hiddenNeurons, int outputNeurons){
        inputNeuronsCount = inputNeurons;
        hiddenNeuronsCount = hiddenNeurons;
        outputNeuronsCount = outputNeurons;
        initialize(inputNeurons, hiddenNeurons, outputNeurons);
    }

    public void train(ArrayList<Vector<Double>> inputs, ArrayList<Vector<Double>> outputs,
                      int trainCycles, double coef, double minError){
        double error = 1;
        //run training iterations while not reached max number or minimal error
        for (int cycleCount = 0; (cycleCount < trainCycles) && (error > minError); cycleCount++){
            for (int i=0; i < inputs.size(); i++){
                //load inputs
                setInput(inputs.get(i));

                //calculate output
                calculateOutput();

                //calculate total error
                error = getTotalError(outputs.get(i));

                //propagate back - update weights
                backpropagation(outputs.get(i), coef);
            }
        }

    }

    private void backpropagation(Vector<Double> desiredOut, double trainCoef){
        double weight;

        //update weights for neurons in ouput layer
        double actualOut;
        for (int i = 0; i < outputNeuronsCount; i++){
            //update weights for all synapses of neuron
            actualOut = outputLayer.get(i).getOutput();
            double desOut = desiredOut.get(i);

            for (Synapse syn : outputLayer.get(i).synapses){
                //in case of bias - there is no neuron1
                Neuron tmpNeuron = syn.getNeuron1();
                double outInput;
                if (tmpNeuron == null){
                    outInput = 1;
                } else {
                    outInput = tmpNeuron.getOutput();
                }

                //lambda can be omitted according to SFC lectures
                weight = syn.getWeight() + trainCoef * outInput * actualOut * (desOut - actualOut) * (1 - actualOut);
                syn.setWeight(weight);
            }
        }

        //update weights for neurons in hidden layer
        int x = 0;
        for (Neuron nH : hiddenLayer){
            //update all synapses for the neuron
            double outHidden = nH.getOutput();
            for (Synapse syn : nH.synapses){
                double tmpWeight;
                double sum = 0;
                //in case of bias - there is no neuron1
                Neuron inputNeuron = syn.getNeuron1();
                double outInput;
                if (inputNeuron == null){
                    outInput = 1;
                } else {
                    outInput = inputNeuron.getOutput();
                }

                //for all output neurons and their weight differences
                int y = 0;
                for (Neuron nO : outputLayer){
                    double weightO = nO.synapses.get(x).getWeight();
                    double outOutput = nO.getOutput();
                    double outDesired = desiredOut.get(y);
                    sum += ((outOutput - outDesired)) * outOutput * (1-outOutput) * weightO;
                    y++;
                }

                weight = syn.getWeight() - trainCoef * (sum * outHidden * outInput * (1-outHidden));
                syn.setWeight(weight);
            }

            x++;
        }
    }

    /*
    * Calculate total error of current training run.
    * */
    public double getTotalError(Vector<Double> desiredOutput){
        double result = 0;
        for (int i=0; i < outputNeuronsCount; i++){
            result += Math.pow(outputLayer.get(i).getOutput() - desiredOutput.get(i), 2);
        }

        return 0.5 * result;
    }

    public void run(Vector<Double> input){
        setInput(input);
        calculateOutput();

        //print results
        for (Neuron n : outputLayer){
            System.out.print(n.getOutput() + "  ");
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

        for (Neuron neuron : outputLayer){
            neuron.calculateOutput();
        }
    }

    private void initialize(int inputNeurons, int hiddenNeurons, int outputNeurons){
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
                n2.addSynapse(syn);
            }
        }

        for (Neuron n2 : hiddenLayer){
            for (Neuron n3: outputLayer){
                Synapse syn = new Synapse(n2, n3);
                n3.addSynapse(syn);
            }
        }
    }
}
