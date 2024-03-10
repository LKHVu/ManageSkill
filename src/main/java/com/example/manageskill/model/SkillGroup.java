package com.example.manageskill.model;

import javax.persistence.*;

@Entity
@Table(name = "SkillGroup")
public class SkillGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillGroupId;

    private String skillGroupName;

    public Long getSkillGroupId() {
        return skillGroupId;
    }

    public void setSkillGroupId(Long skillGroupId) {
        this.skillGroupId = skillGroupId;
    }

    public String getSkillGroupName() {
        return skillGroupName;
    }

    public void setSkillGroupName(String skillGroupName) {
        this.skillGroupName = skillGroupName;
    }
// Getters and setters
}
