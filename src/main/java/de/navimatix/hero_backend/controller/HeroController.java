package de.navimatix.hero_backend.controller;

import de.navimatix.hero_backend.entity.Hero;
import de.navimatix.hero_backend.service.HeroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@Tag(name="Hero")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class HeroController {

    @Autowired
    HeroService heroService;

    Logger logger = LoggerFactory.getLogger(HeroController.class);

    @GetMapping("/hero/{id}")
    @Operation(summary = "Get hero", responses = {
            @ApiResponse(description = "Get hero success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content)
    })
    public ResponseEntity<Hero> getHero(@PathVariable("id") int id) {
        Optional<Hero> hero = heroService.getHeroById(id);

        if (hero.isPresent()) {
            return new ResponseEntity<>(hero.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/heroes")
    @Operation(summary = "Get all heroes", responses = {
            @ApiResponse(description = "Get all hero success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<List<Hero>> getAllHeroes() {

        try {
            List<Hero> heroes = heroService.getAllHeroes();

            if (heroes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(heroes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/hero")
    @Operation(summary = "Create hero", responses = {
            @ApiResponse(description = "Create Hero success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    public ResponseEntity<Hero> addHero(@RequestBody Hero hero) {
        try {
            Optional<Hero> optionalHero = heroService.saveOrUpdate(hero);
            return new ResponseEntity<>(optionalHero.get(), HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/hero/{id}")
    @Operation(summary = "Update hero", responses = {
            @ApiResponse(description = "Update Hero success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "Not found", responseCode = "404", content = @Content),
    })
    public ResponseEntity<Hero> updateHero(@RequestBody Hero hero) {
        Optional<Hero> optionalHero = heroService.getHeroById(hero.getId());

        if (optionalHero.isPresent()) {
            Hero _hero = optionalHero.get();
            _hero.setId(hero.getId());
            _hero.setName(hero.getName());
            return new ResponseEntity<>(heroService.saveOrUpdate(_hero).get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/hero/{id}")
    @Operation(summary = "Delete hero", responses = {
            @ApiResponse(description = "Delete Hero success - No Content", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    public ResponseEntity<HttpStatus> deleteHero(@PathVariable("id") int id) {
        try {
            heroService.deleteHeroById(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/")
    @Operation(summary = "Get suggestions", responses = {
            @ApiResponse(description = "Get suggestions success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Hero.class))),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
    })
    public ResponseEntity<List<Hero>> searchHeroes(@PathParam("name") String name) {
        try {
            List<Hero> heroes = heroService.searchHeroes(name);

            if (heroes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(heroes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
