package de.navimatix.hero_backend.repository;

import de.navimatix.hero_backend.entity.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepo extends CrudRepository<Hero, Integer> {

    List<Hero> findHeroByName(String name);

}
