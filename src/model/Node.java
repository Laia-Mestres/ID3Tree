package model;

public class Node {

    public boolean hoja;        // respuesta final
    public int prediction;      // qué responde si es hoja
    public int survived, died;  // cuántos vivos y muertos hay
    public Split split;         // la pregunta (si no es hoja)
    public Node left, right;    // left = dice SÍ, right = dice NO
}
