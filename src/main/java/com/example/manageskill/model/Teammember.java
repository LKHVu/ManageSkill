package com.example.manageskill.model;

import javax.persistence.*;

@Entity
@Table(
    name = "Teammember",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_teammember_team_user",
            columnNames = {"team_id", "username"}
        )
    },
    indexes = {
        @Index(name = "idx_teammember_team", columnList = "team_id"),
        @Index(name = "idx_teammember_username", columnList = "username")
    }
)
public class Teammember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "team_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_teammember_team")
    )
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "username",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_teammember_user")
    )
    private User user;

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
