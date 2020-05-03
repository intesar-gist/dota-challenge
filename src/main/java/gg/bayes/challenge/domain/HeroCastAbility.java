package gg.bayes.challenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="hero_cast_ability")
public class HeroCastAbility implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Hero hero;

   @ManyToOne
   private DotaMatch match;

   @ManyToOne
   private Ability ability;

   private String abilityLevel;

}
