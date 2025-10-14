package enunciado.parcial.menu;

@FunctionalInterface
public interface OpcionMenu<T> {
    void invocar(T context);
}