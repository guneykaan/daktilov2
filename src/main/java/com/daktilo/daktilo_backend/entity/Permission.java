package com.daktilo.daktilo_backend.entity;

import java.util.HashSet;
import java.util.Set;

import com.daktilo.daktilo_backend.entity.constants.EPermission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="PERMISSIONS")
public class Permission {

    @Id @GeneratedValue(strategy=GenerationType.AUTO) @Column(name="PERMISSION_ID")
    private int permission_id;

    @Enumerated(EnumType.STRING)
    @Column(length=20)
    private EPermission name;

    @ManyToMany(fetch=FetchType.LAZY, mappedBy="permissions")
    private Set<Role> roles=new HashSet<Role>();

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }

    public EPermission getName() {
        return name;
    }

    public void setName(EPermission name) {
        this.name = name;
    }


}