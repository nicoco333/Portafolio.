public class MascotaVirtual {
    private String nombre;
    private int energia;  // 0 - 100
    private int humor;    // 1 - 5
    private boolean duerme;
    private boolean viva;

    private int contadorIngestasConsecutivas;
    private int contadorActividadesConsecutivas;

    public MascotaVirtual(String nombre) {
        this.nombre = nombre;
        this.energia = 100;
        this.humor = 3; // neutral por defecto
        this.duerme = false;
        this.viva = true;
        this.contadorIngestasConsecutivas = 0;
        this.contadorActividadesConsecutivas = 0;
    }

    // ===================
    // Métodos de ingesta
    // ===================
    public boolean comer() {
        if (!puedeResponder()) return false;

        contadorIngestasConsecutivas++;
        contadorActividadesConsecutivas = 0;

        if (contadorIngestasConsecutivas >= 5) {
            viva = false; // muere de empacho
            return false;
        }

        incrementarEnergia((int)(energia * 0.10));

        if (contadorIngestasConsecutivas >= 3) {
            decrementarHumor(1);
        } else {
            incrementarHumor(1);
        }

        return true;
    }

    public boolean beber() {
        if (!puedeResponder()) return false;

        contadorIngestasConsecutivas++;
        contadorActividadesConsecutivas = 0;

        if (contadorIngestasConsecutivas >= 5) {
            viva = false; // muere de empacho
            return false;
        }

        incrementarEnergia((int)(energia * 0.05));

        if (contadorIngestasConsecutivas >= 3) {
            decrementarHumor(1);
        } else {
            incrementarHumor(1);
        }

        return true;
    }

    // ===================
    // Métodos de actividad
    // ===================
    public boolean correr() {
        if (!puedeResponder()) return false;

        contadorActividadesConsecutivas++;
        contadorIngestasConsecutivas = 0;

        decrementarEnergia((int)(energia * 0.35));
        decrementarHumor(2);

        if (contadorActividadesConsecutivas >= 3) {
            dormir(); // se empaca y duerme
        }

        return true;
    }

    public boolean saltar() {
        if (!puedeResponder()) return false;

        contadorActividadesConsecutivas++;
        contadorIngestasConsecutivas = 0;

        decrementarEnergia((int)(energia * 0.15));
        decrementarHumor(2);

        if (contadorActividadesConsecutivas >= 3) {
            dormir();
        }

        return true;
    }

    // ===================
    // Métodos de estado
    // ===================
    public boolean dormir() {
        if (!puedeResponder()) return false;

        duerme = true;
        incrementarEnergia(25);
        incrementarHumor(2);

        contadorIngestasConsecutivas = 0;
        contadorActividadesConsecutivas = 0;

        return true;
    }

    public boolean despertar() {
        if (!viva) return false;
        if (!duerme) return false;

        duerme = false;
        decrementarHumor(1);
        return true;
    }

    // ===================
    // Helpers
    // ===================
    private boolean puedeResponder() {
        return viva && !duerme;
    }

    private void incrementarEnergia(int cantidad) {
        energia = Math.min(energia + cantidad, 100);
    }

    private void decrementarEnergia(int cantidad) {
        energia -= cantidad;
        if (energia <= 0) {
            energia = 0;
            viva = false; // muere de cansancio
        }
    }

    private void incrementarHumor(int cantidad) {
        humor = Math.min(humor + cantidad, 5);
    }

    private void decrementarHumor(int cantidad) {
        humor -= cantidad;
        if (humor <= 0) {
            humor = 1;
            dormir(); // se duerme sola
        }
    }

    @Override
    public String toString() {
        return "MascotaVirtual{" +
                "nombre='" + nombre + '\'' +
                ", energia=" + energia +
                ", humor=" + humor +
                ", duerme=" + duerme +
                ", viva=" + viva +
                '}';
    }
}
