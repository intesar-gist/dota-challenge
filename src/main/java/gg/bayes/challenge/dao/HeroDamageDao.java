package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.Ability;
import gg.bayes.challenge.domain.DotaMatch;
import gg.bayes.challenge.domain.Hero;
import gg.bayes.challenge.domain.HeroDamage;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class HeroDamageDao extends BaseDao<HeroDamage> {

   public Long insert(Hero heroHit, Hero heroDamaged, DotaMatch dotaMatch, Long damageDone, Ability ability, Long createdOn) {
      HeroDamage heroDamage = new HeroDamage();
      heroDamage.setCreatedOn(createdOn);
      heroDamage.setAbility(ability);
      heroDamage.setDamageDone(damageDone);
      heroDamage.setHeroDamaged(heroDamaged);
      heroDamage.setHeroHit(heroHit);
      heroDamage.setMatch(dotaMatch);

      create(heroDamage);
      return heroDamage.getId();
   }

}
