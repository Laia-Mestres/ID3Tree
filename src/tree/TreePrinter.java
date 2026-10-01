package tree;

import model.Node;
import model.Split;

public class TreePrinter {

    // Imprime el árbol entero empezando por la raíz
    public static void print(Node root) {
        print(root, "");
    }

    // "indent" es la sangría: crece un nivel cada vez que bajamos
    private static void print(Node node, String indent) {
        if (node.hoja) {
            String answer = node.prediction == 1 ? "SOBREVIVE" : "MUERE";
            System.out.println(indent + "-> " + answer
                    + "  (vivos=" + node.survived + ", muertos=" + node.died + ")");
            return;
        }

        System.out.println(indent + "[" + condition(node.split) + "?]"
                + "  IG=" + String.format("%.4f", node.split.gain)
                + "  n=" + (node.survived + node.died));

        System.out.println(indent + "  SÍ:");
        print(node.left, indent + "    ");

        System.out.println(indent + "  NO:");
        print(node.right, indent + "    ");
    }

    // Convierte una pregunta en texto legible
    private static String condition(Split s) {
        String name = Config.CAMPOS[s.campo_pregunta];
        if (Config.NUMERICA[s.campo_pregunta]) {
            return name + " <= " + String.format("%.2f", s.umbral);
        }
        return name + " == " + s.categoria;
    }
}