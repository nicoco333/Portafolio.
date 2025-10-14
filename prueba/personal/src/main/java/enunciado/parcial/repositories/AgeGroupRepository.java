package enunciado.parcial.repositories;

import enunciado.parcial.entities.AgeGroup;


public class AgeGroupRepository extends Repository <AgeGroup, Integer> {
     public AgeGroupRepository() {
        super();
    }
    @Override
    protected Class<AgeGroup> getEntityClass(){
        return AgeGroup.class;
    }
}