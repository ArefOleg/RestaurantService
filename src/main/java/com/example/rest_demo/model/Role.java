package com.example.rest_demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role extends AbstractNamedEntity{
    public Role(){}
}
