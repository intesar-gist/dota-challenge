package gg.bayes.challenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="hero")
@NamedQueries({
      @NamedQuery(name = "hero.findByName",
            query = "SELECT h FROM Hero h where h.heroName = :heroName"),
      @NamedQuery(name = "hero.findHeroKills",
            query = "SELECT new gg.bayes.challenge.rest.model.HeroKills(h.heroName, count(h.id)) "
                  + "FROM Hero h LEFT JOIN h.killedOthers hk WHERE hk.match.id = :matchId group by h.id"),
      @NamedQuery(name = "hero.findHeroBoughtItemsInAMatch",
            query = "SELECT new gg.bayes.challenge.rest.model.HeroItems(i.itemName, hpi.createdOn) "
                  + "FROM Hero h "
                  + "LEFT JOIN h.heroPurchasedItems hpi "
                  + "LEFT JOIN hpi.item i "
                  + "WHERE hpi.match.id = :matchId AND h.heroName = :heroName"),
      @NamedQuery(name = "hero.findHeroSpellFrequencyInAMatch",
            query = "SELECT new gg.bayes.challenge.rest.model.HeroSpells(a.abilityName, count(a.abilityName)) "
                  + "FROM Hero h "
                  + "LEFT JOIN h.heroCastAbilities hca "
                  + "LEFT JOIN hca.ability a "
                  + "WHERE hca.match.id = :matchId AND h.heroName = :heroName "
                  + "group by a.abilityName"),
      @NamedQuery(name = "hero.findHeroDamagesInAMatch",
            query = "SELECT new gg.bayes.challenge.rest.model.HeroDamage(hh.heroName, count(hd.heroDamaged.id), sum(hd.damageDone)) "
                  + "FROM Hero h "
                  + "LEFT JOIN h.damagedOthers hd "
                  + "LEFT JOIN hd.heroDamaged hh "
                  + "WHERE hd.match.id = :matchId AND h.heroName = :heroName "
                  + "group by hd.heroDamaged.id")
})
public class Hero implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name="hero_name", unique = true)
   private String heroName;

   @OneToMany(mappedBy = "hero", fetch = FetchType.LAZY)
   private List<HeroPurchasedItem> heroPurchasedItems;

   @OneToMany(mappedBy = "hero", fetch = FetchType.LAZY)
   private List<HeroCastAbility> heroCastAbilities;

   @OneToMany(mappedBy = "killer", fetch = FetchType.LAZY)
   private List<HeroKill> killedOthers;

   @OneToMany(mappedBy = "heroHit", fetch = FetchType.LAZY)
   private List<HeroDamage> damagedOthers;
}
