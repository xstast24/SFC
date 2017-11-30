import java.util.ArrayList;
import java.lang.Math;

class Neuron {
    protected final ArrayList<Synapse> synapses = new ArrayList<Synapse>();
    private double output;

    Neuron(){
        output = 0;

        // add input bias (synapse with value 1)
        Synapse bias = new Synapse();
        synapses.add(bias);
    }

    void addSynapse(Synapse syn){
        synapses.add(syn);
    }

    Synapse getSynapse(int index){
        return synapses.get(index);
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
