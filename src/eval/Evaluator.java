package eval;

import model.Passenger;
import tree.DecisionTree;

import java.util.List;

public class Evaluator {

    // Porcentaje de pasajeros que el árbol acierta (entre 0 y 1)
    public static double accuracy(DecisionTree tree, List<Passenger> data) {
        int correct = 0;
        for (Passenger p : data) {
            if (tree.predict(p) == p.survived) correct++;
        }
        return (double) correct / data.size();
    }

    // Muestra la matriz de confusión y las métricas completas
    public static void evaluate(DecisionTree tree, List<Passenger> data) {
        int tp = 0, tn = 0, fp = 0, fn = 0;

        for (Passenger p : data) {
            int predicted = tree.predict(p);
            if (predicted == 1 && p.survived == 1) tp++;       // dijo vive, vivió
            else if (predicted == 0 && p.survived == 0) tn++;  // dijo muere, murió
            else if (predicted == 1) fp++;                     // dijo vive, murió
            else fn++;                                         // dijo muere, vivió
        }

        // Si el denominador es 0, devolvemos 0 para no dividir entre cero
        double precision = (tp + fp == 0) ? 0 : (double) tp / (tp + fp);
        double recall = (tp + fn == 0) ? 0 : (double) tp / (tp + fn);

        System.out.println("Matriz de confusión:");
        System.out.println("                      Predice 0   Predice 1");
        System.out.printf("Realmente murió (0)   %9d   %9d%n", tn, fp);
        System.out.printf("Realmente vivió (1)   %9d   %9d%n", fn, tp);
        System.out.printf("%nAccuracy : %.4f%n", accuracy(tree, data));
        System.out.printf("Precision: %.4f%n", precision);
        System.out.printf("Recall   : %.4f%n", recall);
    }
}