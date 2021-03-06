package com.example.rest_demo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
public class AbstractNamedEntity extends AbstractBaseEntity{

    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name", nullable = false)
    protected String name;

    protected AbstractNamedEntity() {
    }

    protected AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
