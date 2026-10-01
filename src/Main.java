import data.DataLoader;
import data.Preprocesado;
import eval.Evaluator;
import model.Node;
import model.Passenger;
import model.Split;
import tree.*;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("=== Árbol de decisión ID3 - Titanic ===");

        // ller datos
        List<Passenger> all = DataLoader.readLoad();
        // Train/Test + llenar vacios
        Collections.shuffle(all, new Random(42)); // mezclar filas
        int cut = (int) (all.size() * 0.8);
        List<Passenger> train = new ArrayList<>(all.subList(0, cut));
        List<Passenger> test = new ArrayList<>(all.subList(cut, all.size()));
        Preprocesado.llenarVacios(train, test);
        System.out.println("Train: " + train.size() + " | Test: " + test.size());

        // Montamos el arbol con los datos train
        DecisionTree tree = new DecisionTree(4);
        tree.fit(train);

        // Imprimir el arbol resultante
        System.out.println("\n=== Árbol resultante ===");
        TreePrinter.print(tree.getRoot());

        // Evaluación + métricas
        System.out.println("\n=== Evaluación con el test (" + test.size() + " pasajeros) ===");
        Evaluator.evaluate(tree, test);

        System.out.printf("%nAccuracy en train: %.4f%n", Evaluator.accuracy(tree, train));
        System.out.printf("Accuracy en test : %.4f%n", Evaluator.accuracy(tree, test));

        System.out.println("\n=== Efecto de la profundidad ===");
        System.out.println("Profundidad | Acc train | Acc test");
        int[] depths = {1, 2, 3, 4, 5, 6, 8, 10};
        for (int d : depths) {
            DecisionTree t = new DecisionTree(d);
            t.fit(train);
            System.out.printf("%11d | %9.4f | %8.4f%n",
                    d, Evaluator.accuracy(t, train), Evaluator.accuracy(t, test));
        }

    }

}