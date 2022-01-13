package de.navimatix.hero_backend.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "HERO")
public class Hero {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hero_gen")
    @SequenceGenerator(name = "hero_gen", sequenceName = "hero_seq")
    int id;
    @Column
    String name;
}
