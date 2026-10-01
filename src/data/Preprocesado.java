package data;

import model.Passenger;
import tree.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Preprocesado {

    // Rellena los huecos segun la media y moda
    public static void llenarVacios(List<Passenger> train, List<Passenger> test) {
        for (int f = 0; f < Config.CAMPOS.length; f++) {

            String fill;
            if (Config.NUMERICA[f]) { // llenar campos num con media()
                fill = media(train, f);
            } else { //llenar campos categoria con moda()
                fill = moda(train, f);
            }

            // llenar campos pasajeros
            for (Passenger p : train) {
                if (p.campos_pasajero [f].isEmpty()) p.campos_pasajero [f] = fill;
            }
            for (Passenger p : test) {
                if (p.campos_pasajero [f].isEmpty()) p.campos_pasajero [f] = fill;
            }
        }
    }

    // Calcular media de la columna
    private static String media(List<Passenger> data, int f) {
        List<Double> values = new ArrayList<>();
        for (Passenger p : data) {
            if (!p.campos_pasajero [f].isEmpty()) {
                values.add(Double.parseDouble(p.campos_pasajero [f]));
            }
        }
        Collections.sort(values);
        return String.valueOf(values.get(values.size() / 2));
    }

    // Calcular categoria mas frecuente de la columna
    private static String moda(List<Passenger> data, int f) {
        Map<String, Integer> counts = new HashMap<>();
        for (Passenger p : data) {
            if (!p.campos_pasajero [f].isEmpty()) {
                counts.merge(p.campos_pasajero [f], 1, Integer::sum);
            }
        }
        return Collections.max(counts.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}