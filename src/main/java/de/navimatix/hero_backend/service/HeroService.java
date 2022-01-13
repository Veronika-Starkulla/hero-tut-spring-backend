package de.navimatix.hero_backend.service;

import de.navimatix.hero_backend.entity.Hero;
import de.navimatix.hero_backend.repository.HeroRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HeroService {
    @Autowired
    HeroRepo repository;

    public Optional<Hero> getHeroById(int id) {
        return repository.findById(id);
    }

    public List<Hero> getAllHeroes(){
        List<Hero> heroes = new ArrayList<Hero>();
        repository.findAll().forEach(hero -> heroes.add(hero));
        return heroes;
    }

    public Hero saveOrUpdate(Hero hero) {
        return repository.save(hero);
    }

    public void deleteHeroById(int id) {
        repository.deleteById(id);
    }

    public List<Hero> searchHeroes(String name) {
        List<Hero> heroes = new ArrayList<Hero>();
        repository.findHeroByName(name).forEach(hero -> heroes.add(hero));
        return heroes;
    }
}
