package enunciado.parcial.menu;

public class ItemMenu<T> {
    private final String text;
    private final OpcionMenu<T> action;

    public ItemMenu(String text, OpcionMenu<T> accion) {
        this.text = text;
        this.action = accion;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public void ejecutar(T context) {
        if (this.action != null) {
            this.action.invocar(context); // delega la acción
        }
    }
}