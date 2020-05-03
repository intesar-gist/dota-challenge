package gg.bayes.challenge.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Entity
@Table(name="hero_damage")
public class HeroDamage implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Hero heroHit;

   @ManyToOne
   private Hero heroDamaged;

   @ManyToOne
   private DotaMatch match;

   @ManyToOne
   private Ability ability;

   private Long damageDone;

   @Column(name = "created_on")
   private Long createdOn;

}
