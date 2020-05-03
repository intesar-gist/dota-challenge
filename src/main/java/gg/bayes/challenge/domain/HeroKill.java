package gg.bayes.challenge.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Data
@Entity
@Table(name="hero_kill")
public class HeroKill implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   private Hero killer;

   @ManyToOne
   private Hero killed;

   @Column(name = "created_on")
   private Long createdOn;

   @ManyToOne
   private DotaMatch match;

}
