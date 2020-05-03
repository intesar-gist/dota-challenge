package gg.bayes.challenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="hero_purchased_item")
public class HeroPurchasedItem implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Hero hero;

   @ManyToOne
   private DotaMatch match;

   @ManyToOne
   private Item item;

   @Column(name = "created_on")
   private Long createdOn;

}
