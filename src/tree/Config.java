package tree;

public class Config {
    // Datos del pasajero a usar
    public static final String[] CAMPOS = {"Pclass", "Sex", "Age", "SibSp", "Parch", "Fare", "Embarked"};

    // true = número, false = categoría
    public static final boolean[] NUMERICA = {true, false, true, true, true, true, false};

    // Reglas para parar
    public static final int MIN_SAMPLES = 10;     // mínimo de pasajeros que restan para seguir preguntando
    public static final double MIN_GAIN = 0.001;  // mejora mínima para continuar con el árbol
}
