package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.Ability;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class AbilityDao extends BaseDao<Ability> {

   public Ability insert(String abilityName) {
      Ability ability = new Ability();
      ability.setAbilityName(abilityName);
      create(ability);
      return ability;
   }

}
