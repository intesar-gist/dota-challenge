package gg.bayes.challenge.service;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

public interface MatchService {
    Long ingestMatch(String payload) throws Exception;

    List<HeroKills> getHeroKills(Long matchId) throws Exception;

    List<HeroItems> getHeroItems(String heroName, Long matchId) throws Exception;

   List<HeroSpells> getHeroSpells(String heroName, Long matchId) throws Exception;

   List<HeroDamage> getHeroDamages(String heroName, Long matchId) throws Exception;
}
