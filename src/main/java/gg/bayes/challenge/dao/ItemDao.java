package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.Item;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ItemDao extends BaseDao<Item> {

   public Item insert(String itemName) {
      Item item = new Item();
      item.setItemName(itemName);

      create(item);
      return item;
   }

}
