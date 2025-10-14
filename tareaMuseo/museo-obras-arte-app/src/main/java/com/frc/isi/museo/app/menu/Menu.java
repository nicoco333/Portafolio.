package com.frc.isi.museo.app.menu;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import lombok.Setter;

public class Menu {
    @Setter
    public String titulo;
    public final List<ItemMenu> opciones = new ArrayList<>();

    private int safeInt(Scanner in) {
        while (!in.hasNextInt()) {
            in.next();
            System.out.print("> Opcion: ");        
        }
        int n = in.nextInt();
        in.nextLine();
        return n;
    }

    public void addOpcion(ItemMenu opcion) {
        this.opciones.add(opcion);
    }

    public void ejecutar(ApplicationContext contexto) {
        var lector = new Scanner(System.in);
        contexto.setOrThrow("lector", lector);
        try {
            while (true) {       
                System.out.println(titulo);
                System.out.println("============================");
                this.opciones.forEach(o -> System.err.printf("%2d) --- %s%n", o.indice(), o.mesaje()));
                System.out.println("0 - Salir");
                System.out.println("Ingrese su opcion: ");
                int opcion = safeInt(lector);

                if (opcion == 0) {
                    System.out.println("Saliendo...");
                    break;
                }

                this.opciones.stream()
                    .filter(o -> o.indice() == opcion)
                    .findFirst()
                    .ifPresentOrElse(
                        o -> o.accion().invocar(contexto),
                        () -> System.out.println("Opcion invalida..."));
            }

        } catch (IllegalArgumentException | NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        } finally {
            lector.close();
        }
        System.out.println("Fin del programa.");
    }
}
