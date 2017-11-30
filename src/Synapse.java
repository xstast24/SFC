import java.util.Random;
import com.sun.istack.internal.NotNull;

public class Synapse {
    static int synapseCount = 0;

    private final int id;
    private double weight;
    private final Neuron neuron1;
    private final Neuron neuron2;

    public Synapse(@NotNull Neuron n1, @NotNull Neuron n2){
        id = synapseCount++;

        Random rand = new Random();
        weight = rand.nextDouble();

        neuron1 = n1;
        neuron2 = n2;
    }

    public Synapse(){
        id = synapseCount++;

        Random rand = new Random();
        weight = rand.nextDouble();

        neuron1 = null;
        neuron2 = null;
    }

    public double getWeightedValue(){
        //in case of bias, there is no neuron1, return weight*1.0
        if (neuron1 == null){
            return weight;
        } else {
            return neuron1.getOutput() * weight;
        }

    }

    public Neuron getNeuron1(){
        return neuron1;
    }

    public Neuron getNeuron2(){
        return neuron2;
    }
}
