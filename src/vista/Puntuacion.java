package vista;
public class Puntuacion {
        private String nombre;
        private int puntuacion;

        public Puntuacion(String name, int score) {
            this.nombre = name;
            this.puntuacion = score;
        }

        public int obtPuntuacion() {
            return this.puntuacion;
        }

        public String obtNombre() {
            return this.nombre;
        }
    }


