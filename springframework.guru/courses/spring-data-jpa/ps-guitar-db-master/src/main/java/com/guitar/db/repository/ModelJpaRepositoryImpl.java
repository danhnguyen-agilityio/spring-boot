package com.guitar.db.repository;

public class ModelJpaRepositoryImpl implements ModelJpaRepositoryCustom {
    @Override
    public void customMethod() {
        System.out.println("This is custom method in custom repository");
    }
}
