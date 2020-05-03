package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.DotaMatch;
import gg.bayes.challenge.domain.Hero;
import gg.bayes.challenge.domain.HeroKill;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class HeroKillDao extends BaseDao<HeroKill> {

   public Long insert(Hero killer, Hero killed, DotaMatch dotaMatch, Long createdOn) {
      HeroKill heroKill = new HeroKill();
      heroKill.setCreatedOn(createdOn);
      heroKill.setMatch(dotaMatch);
      heroKill.setKilled(killed);
      heroKill.setKiller(killer);

      create(heroKill);
      return heroKill.getId();
   }
}
