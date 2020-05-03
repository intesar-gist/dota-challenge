package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.DotaMatch;
import gg.bayes.challenge.domain.Hero;
import gg.bayes.challenge.domain.HeroPurchasedItem;
import gg.bayes.challenge.domain.Item;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class HeroPurchasedItemDao extends BaseDao<HeroPurchasedItem> {

   public HeroPurchasedItem insert(Hero hero, Item item, DotaMatch dotaMatch, Long createdOn) {
      HeroPurchasedItem heroPurchasedItem = new HeroPurchasedItem();
      heroPurchasedItem.setCreatedOn(createdOn);
      heroPurchasedItem.setHero(hero);
      heroPurchasedItem.setItem(item);
      heroPurchasedItem.setMatch(dotaMatch);

      create(heroPurchasedItem);
      return heroPurchasedItem;
   }

}
