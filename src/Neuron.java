import java.util.ArrayList;
import java.lang.Math;

class Neuron {
    static private int id_counter = 0;
    private final int id;
    private final ArrayList<Synapse> synapses = new ArrayList<Synapse>();
    private double output;

    Neuron(){
        id = id_counter++;
        output = 0;

        // add input bias (synapse with value 1)
        Synapse bias = new Synapse();
        synapses.add(bias);
    }

    void addSynapse(Synapse syn){
        synapses.add(syn);
    }

    void calculateOutput(){
        double output = 0;
        for (Synapse syn : synapses){
            output += syn.getWeightedValue();
        }

        output = sigmoid(output);
        setOutput(output);
    }

    double sigmoid(double value){
        //exp(-1 * lambda * u) for lambda = 1
        return 1.0 / (1.0 + Math.exp(-value));
    }

    double getOutput(){
        return output;
    }

    void setOutput(double value){
        output = value;
    }
}
