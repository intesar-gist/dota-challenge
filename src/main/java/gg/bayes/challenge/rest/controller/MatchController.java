package gg.bayes.challenge.rest.controller;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping(consumes = "text/plain")
    public ResponseEntity<Long> ingestMatch(@RequestBody @NotNull @NotBlank String payload) {
        final Long matchId;
        try {
            matchId = matchService.ingestMatch(payload);
            return ResponseEntity.ok(matchId);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{matchId}")
    public ResponseEntity<List<HeroKills>> getMatch(@PathVariable("matchId") Long matchId) {
        try {
            List<HeroKills> heroKills = matchService.getHeroKills(matchId);
            return new ResponseEntity<>(heroKills, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{matchId}/{heroName}/items")
    public ResponseEntity<List<HeroItems>> getItems(@PathVariable("matchId") Long matchId,
                                                    @PathVariable("heroName") String heroName) {
        try {
            List<HeroItems> heroItems = matchService.getHeroItems(heroName, matchId);
            return new ResponseEntity<>(heroItems, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{matchId}/{heroName}/spells")
    public ResponseEntity<List<HeroSpells>> getSpells(@PathVariable("matchId") Long matchId,
                                                      @PathVariable("heroName") String heroName) {
        try {
            List<HeroSpells> heroSpells = matchService.getHeroSpells(heroName, matchId);
            return new ResponseEntity<>(heroSpells, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{matchId}/{heroName}/damage")
    public ResponseEntity<List<HeroDamage>> getDamage(@PathVariable("matchId") Long matchId,
                                                      @PathVariable("heroName") String heroName) {
        try {
            List<HeroDamage> heroDamages = matchService.getHeroDamages(heroName, matchId);
            return new ResponseEntity<>(heroDamages, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
