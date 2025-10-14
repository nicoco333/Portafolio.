package enunciado.parcial.repositories;

import enunciado.parcial.entities.LegoSet;

public class LegoSetRepository extends Repository <LegoSet, Integer>{
    public LegoSetRepository() {
        super();
    }

    @Override
    protected Class<LegoSet> getEntityClass(){
        return LegoSet.class;
    }
}