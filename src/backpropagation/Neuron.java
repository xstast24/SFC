package backpropagation;

import java.util.ArrayList;

public class Neuron {
    static private int id_counter = 0;
    public final int id;
    private final ArrayList<Synapse> synapses = new ArrayList<Synapse>();
    private float output;

    public Neuron(){
        id = id_counter++;
        output = 0;
        System.out.println("New Neuron " + id);
    }

    protected void addSynapse(Synapse syn){
        synapses.add(syn);
    }
}
