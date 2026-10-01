package tree;

import model.Node;
import model.Passenger;
import model.Split;

import java.util.ArrayList;
import java.util.List;

public class DecisionTree {

    private final int maxDepth;   // máximo de niveles
    private Node root;            // primer nodo del árbol

    public DecisionTree(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    // Construye el árbol a partir de los pasajeros de entrenamiento
    public void fit(List<Passenger> train) {
        root = build(train, 0);
    }

    public Node getRoot() {
        return root;
    }

    public int predict(Passenger p) {
        Node node = root;
        while (!node.hoja) {
            node = SplitFinder.goesLeft(p, node.split) ? node.left : node.right;
        }
        return node.prediction;
    }

    // Construye un nodo (y, recursivamente, lo que cuelga de él)
    private Node build(List<Passenger> data, int depth) {
        Node node = new Node();

        // 1. Contar vivos y muertos, y apuntar lo que respondería la mayoría
        for (Passenger p : data) {
            if (p.survived == 1) node.survived++;
            else node.died++;
        }
        node.prediction = node.survived > node.died ? 1 : 0;

        // 2. ¿Hay que parar? Entonces este nodo es una hoja
        boolean pure = node.survived == 0 || node.died == 0;
        if (pure || depth >= maxDepth || data.size() < Config.MIN_SAMPLES) {
            node.hoja = true;
            return node;
        }

        // 3. Buscar la mejor pregunta
        Split best = SplitFinder.bestSplit(data);
        if (best == null || best.gain < Config.MIN_GAIN) {
            node.hoja = true;
            return node;
        }

        // 4. Dividir el grupo en dos según la pregunta
        List<Passenger> yes = new ArrayList<>();
        List<Passenger> no = new ArrayList<>();
        for (Passenger p : data) {
            if (SplitFinder.goesLeft(p, best)) yes.add(p);
            else no.add(p);
        }

        // 5. Repetir el proceso en cada grupo, un nivel más abajo
        node.split = best;
        node.left = build(yes, depth + 1);
        node.right = build(no, depth + 1);
        return node;
    }
}