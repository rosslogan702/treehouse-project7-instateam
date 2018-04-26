package com.rosslogan.instateam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z -]+", message = "Invalid character(s) entered")
    private String name;

    @OneToMany(mappedBy = "role")
    private List<Collaborator> collaborators;

    @ManyToMany(mappedBy = "rolesNeeded")
    private List<Project> projects;

    public Role() {
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (!id.equals(role.id)) return false;
        return name.equals(role.name);
    }
}
