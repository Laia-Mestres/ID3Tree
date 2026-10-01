package data;

import model.Passenger;
import tree.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    private static final String PATH = "src/data/Titanic-Dataset.csv";

    // Lee el CSV y devuelve la lista de pasajeros
    public static List<Passenger> readLoad() throws IOException {
        List<Passenger> passengerList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {

            // Buscamos la posición de cada columna que nos interesa (Survived)
            List<String> header = parseLine(br.readLine());
            int posSurvived = header.indexOf("Survived");
            int[] pos = new int[Config.CAMPOS.length];
            for (int i = 0; i < Config.CAMPOS.length; i++) {
                pos[i] = header.indexOf(Config.CAMPOS[i]);
            }

            // Añadir a la lista los pasajeros
            String line;
            while ((line = br.readLine()) != null) {
                List<String> cols = parseLine(line);

                Passenger p = new Passenger();
                p.survived = Integer.parseInt(cols.get(posSurvived).trim());
                p.campos_pasajero = new String[Config.CAMPOS.length];
                for (int i = 0; i < Config.CAMPOS.length; i++) {
                    p.campos_pasajero[i] = cols.get(pos[i]).trim();
                }
                passengerList.add(p);
            }
        }
        return passengerList;
    }

    // Parsea las lineas segun las comas
    private static List<String> parseLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                out.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        out.add(current.toString());
        return out;
    }
}