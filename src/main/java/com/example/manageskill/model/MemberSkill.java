package com.example.manageskill.model;

import javax.persistence.*;

@Entity
@Table(name = "MemberSkill")
public class MemberSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberSkillId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Teammember member;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private String proficiencyLevel;

    public Long getMemberSkillId() {
        return memberSkillId;
    }

    public void setMemberSkillId(Long memberSkillId) {
        this.memberSkillId = memberSkillId;
    }

    public Teammember getMember() {
        return member;
    }

    public void setMember(Teammember member) {
        this.member = member;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
// Getters and setters
}
