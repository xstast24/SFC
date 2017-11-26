import java.util.Random;
import com.sun.istack.internal.NotNull;

public class Synapse {
    static int synapseCount = 0;

    private final int id;
    private float weight;
    private final Neuron neuron1;
    private final Neuron neuron2;

    public Synapse(@NotNull Neuron n1, @NotNull Neuron n2){
        id = synapseCount++;
        System.out.println("New Synapse " + id);

        neuron1 = n1;
        neuron2 = n2;

        Random rand = new Random();
        weight = rand.nextFloat(); //interval <0, 1> TODO random nebo 0-1 stable - prednaska?
    }
}
