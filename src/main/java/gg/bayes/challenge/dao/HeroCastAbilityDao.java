package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.Ability;
import gg.bayes.challenge.domain.DotaMatch;
import gg.bayes.challenge.domain.Hero;
import gg.bayes.challenge.domain.HeroCastAbility;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class HeroCastAbilityDao extends BaseDao<HeroCastAbility> {

   public Long insert(Hero hero, Ability ability, String abilityLevel, DotaMatch dotaMatch) {
      HeroCastAbility heroCastAbility = new HeroCastAbility();
      heroCastAbility.setHero(hero);
      heroCastAbility.setAbility(ability);
      heroCastAbility.setAbilityLevel(abilityLevel);
      heroCastAbility.setMatch(dotaMatch);

      create(heroCastAbility);
      return heroCastAbility.getId();
   }

}
