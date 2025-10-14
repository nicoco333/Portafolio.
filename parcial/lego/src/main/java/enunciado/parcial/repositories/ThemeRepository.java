package enunciado.parcial.repositories;

import enunciado.parcial.entities.Theme;

public class ThemeRepository extends Repository<Theme, Integer> {
    public ThemeRepository() { super(); }

    @Override
    protected Class<Theme> getEntityClass() { return Theme.class; }
}
