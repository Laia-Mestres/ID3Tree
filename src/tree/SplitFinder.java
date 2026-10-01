package tree;

import model.Passenger;
import model.Split;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SplitFinder {

    // ¿Este pasajero responde SÍ a la pregunta?
    public static boolean goesLeft(Passenger p, Split s) {
        if (Config.NUMERICA[s.campo_pregunta]) {
            // Pregunta sobre un número: valor <= umbral
            return Double.parseDouble(p.campos_pasajero[s.campo_pregunta]) <= s.umbral;
        }
        // Pregunta sobre una categoría: valor == categoría
        return p.campos_pasajero[s.campo_pregunta].equals(s.categoria);
    }

    public static Split bestSplit(List<Passenger> data) {
        Split best = null;

        // Recorremos cada dato (Pclass, Sex, Age...)
        for (int f = 0; f < Config.CAMPOS.length; f++) {

            // Preparamos todas las preguntas posibles sobre este dato
            List<Split> candidates = new ArrayList<>();

            if (Config.NUMERICA[f]) {
                // Valores distintos, ordenados de menor a mayor
                TreeSet<Double> values = new TreeSet<>();
                for (Passenger p : data) {
                    values.add(Double.parseDouble(p.campos_pasajero[f]));
                }
                // Una pregunta en el punto medio de cada par de valores consecutivos
                Double previous = null;
                for (double v : values) {
                    if (previous != null) {
                        Split s = new Split();
                        s.campo_pregunta = f;
                        s.umbral = (previous + v) / 2.0;
                        candidates.add(s);
                    }
                    previous = v;
                }
            } else {
                // Valores distintos de la categoría
                Set<String> values = new TreeSet<>();
                for (Passenger p : data) {
                    values.add(p.campos_pasajero[f]);
                }
                // Una pregunta por cada valor
                for (String v : values) {
                    Split s = new Split();
                    s.campo_pregunta = f;
                    s.categoria = v;
                    candidates.add(s);
                }
            }

            // Calculamos la ganancia de cada pregunta y nos quedamos con la mejor
            for (Split s : candidates) {
                List<Passenger> yes = new ArrayList<>();
                List<Passenger> no = new ArrayList<>();
                for (Passenger p : data) {
                    if (goesLeft(p, s)) yes.add(p);
                    else no.add(p);
                }

                // Si un grupo queda vacío, la pregunta no separa nada: se descarta
                if (yes.isEmpty() || no.isEmpty()) continue;

                s.gain = Entropia.informationGain(data, yes, no);
                if (best == null || s.gain > best.gain) {
                    best = s;
                }
            }
        }
        return best;
    }
}