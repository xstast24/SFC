import java.util.ArrayList;

public class Neuron {
    static private int id_counter = 0;
    public final int id;
    private final ArrayList<Synapse> synapses = new ArrayList<Synapse>();
    private double output;

    public Neuron(){
        id = id_counter++;
        output = 0;

        // add input bias (synapse with value 1)
        Synapse bias = new Synapse();
        synapses.add(bias);
    }

    protected void addSynapse(Synapse syn){
        synapses.add(syn);
    }
}
