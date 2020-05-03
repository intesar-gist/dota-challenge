package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.Hero;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class HeroDao extends BaseDao<Hero> {

   public Hero insert(String heroName) {
      Hero hero = new Hero();
      hero.setHeroName(heroName);

      create(hero);
      return hero;
   }

   public List<HeroKills> getHeroKills(Long matchId) {
      List<HeroKills> heroKills = entityManager.createNamedQuery("hero.findHeroKills", HeroKills.class)
            .setParameter("matchId", matchId)
            .getResultList();

      return heroKills;

   }

   public List<HeroItems> getItemPurchasedByHeroAndMatch(String heroName, Long matchId) {

      List<HeroItems> heroItems = entityManager.createNamedQuery("hero.findHeroBoughtItemsInAMatch", HeroItems.class)
            .setParameter("matchId", matchId)
            .setParameter("heroName", heroName) //using heroId is better, but using heroName just to save development time
            .getResultList();

      return heroItems;

   }

   public List<HeroSpells> getSpellCastedByHeroAndMatch(String heroName, Long matchId) {

      List<HeroSpells> heroSpells = entityManager.createNamedQuery("hero.findHeroSpellFrequencyInAMatch", HeroSpells.class)
            .setParameter("matchId", matchId)
            .setParameter("heroName", heroName) //using heroId is better, but using heroName just to save development time
            .getResultList();

      return heroSpells;
   }

   public List<HeroDamage> getDamagesByHeroAndMatch(String heroName, Long matchId) {

      List<HeroDamage> heroDamages = entityManager.createNamedQuery("hero.findHeroDamagesInAMatch", HeroDamage.class)
            .setParameter("matchId", matchId)
            .setParameter("heroName", heroName) //using heroId is better, but using heroName just to save development time
            .getResultList();

      return heroDamages;
   }
}
