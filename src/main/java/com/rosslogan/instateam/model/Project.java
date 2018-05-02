package com.rosslogan.instateam.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9 -]+", message = "Invalid character(s) entered for project name")
    private String name;
    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9 -]+", message = "Invalid character(s) entered for project description")
    private String description;
    private String status;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "PROJECT_ROLE",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private List<Role> rolesNeeded;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "PROJECT_COLLAB",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "collaborator_id") }
    )
    private List<Collaborator> collaborators;

    public Project(){
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }
}
