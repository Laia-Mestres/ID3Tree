package tree;

import model.Passenger;

import java.util.List;

public class Entropia {

    // Calculo del Logaritmo
    private static double log2(double x) {
        return Math.log(x) / Math.log(2);
    }

    // Entropía (vivos/muertos)
    public static double entropia(int vivos, int fallecidos) {
        int total = vivos + fallecidos;

        // Grupo vacío o todos iguales (sin mezcla)
        if (total == 0 || vivos == 0 || fallecidos == 0) {
            return 0.0;
        }

        double p = (double) vivos / total;       // proporción de vivos
        double q = (double) fallecidos / total;  // proporción de fallecidos
        return -p * log2(p) - q * log2(q);
    }

    // Entropía de una lista de pasajeros (los cuenta)
    public static double entropia(List<Passenger> data) {
        int vivos = 0;
        for (Passenger p : data) {
            vivos += p.survived;
        }
        int fallecidos = data.size() - vivos;
        return entropia(vivos, fallecidos);
    }

    public static double informationGain(List<Passenger> data,
                                         List<Passenger> left,
                                         List<Passenger> right) {
        double n = data.size();

        // Mezcla media de los dos grupos nuevos, ponderada por su tamaño
        double after = (left.size() / n) * entropia(left)
                + (right.size() / n) * entropia(right);

        return entropia(data) - after;
    }
}
