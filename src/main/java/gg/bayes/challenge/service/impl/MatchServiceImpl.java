package gg.bayes.challenge.service.impl;

import gg.bayes.challenge.dao.*;
import gg.bayes.challenge.domain.Ability;
import gg.bayes.challenge.domain.DotaMatch;
import gg.bayes.challenge.domain.Hero;
import gg.bayes.challenge.domain.Item;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItems;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import gg.bayes.challenge.service.MatchService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.StringReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MatchServiceImpl implements MatchService {

    public static final String HERO_PREFIX = "npc_dota_hero_";
    public static final String DOTA_UNKNOWN = "dota_unknown";
    private DotaMatchDao dotaMatchDao;
    private HeroDao heroDao;
    private ItemDao itemDao;
    private HeroPurchasedItemDao heroPurchasedItemDao;
    private HeroKillDao heroKillDao;
    private AbilityDao abilityDao;
    private HeroCastAbilityDao heroCastAbilityDao;
    private HeroDamageDao heroDamageDao;

    //quick caching just to avoid multiple calls/queries to load saved objects
    HashMap<String, Hero> savedHeros = new HashMap<>();
    HashMap<String, Ability> savedAbilities = new HashMap<>();
    HashMap<String, Item> savedItems = new HashMap<>();

    @Autowired
    public MatchServiceImpl(final DotaMatchDao dotaMatchDao, final HeroDao heroDao,
          final ItemDao itemDao, final HeroPurchasedItemDao heroPurchasedItemDao,
          final HeroKillDao heroKillDao, final AbilityDao abilityDao,
          final HeroCastAbilityDao heroCastAbilityDao, final HeroDamageDao heroDamageDao) {
        this.dotaMatchDao = dotaMatchDao;
        this.heroDao = heroDao;
        this.itemDao = itemDao;
        this.heroPurchasedItemDao = heroPurchasedItemDao;
        this.heroKillDao = heroKillDao;
        this.abilityDao = abilityDao;
        this.heroCastAbilityDao = heroCastAbilityDao;
        this.heroDamageDao = heroDamageDao;
    }

    /**
     * Read, manipulate and store the payload in the database
     * @param payload
     * @return
     * @throws Exception
     */
    @Override
    public Long ingestMatch(String payload) throws Exception {
        try {

            DotaMatch dotaMatch = dotaMatchDao.insert(new RandomString(4).nextString());

            new BufferedReader(new StringReader(payload))
                  .lines().forEach(s -> parseLine(s, dotaMatch));

            return dotaMatch.getId();

        } catch (Exception ex){
            log.debug("Service Exception in MatchService.ingestMatch", ex);
            throw new Exception("Service Exception in MatchService.ingestMatch");
        }
    }

    private void parseLine(String line, DotaMatch dotaMatch) {

        if(line != null) {
            String [] lineSplit = line.split(" ");

            // first word is always hero
            if(lineSplit.length>1 && lineSplit[1].contains(HERO_PREFIX)) {

                Long createdOn = getTime(lineSplit[0]); //time stamp

                // considering the line will always be in lowerCases()
                if(line.contains("buys")) {
                    heroPurchasedItemDao.insert(
                          getHero(lineSplit[1]), //hero name
                          getItem(lineSplit[4]), // item bought
                          dotaMatch,
                          createdOn);
                } else if (line.contains("killed") && lineSplit[5].contains(HERO_PREFIX)) {
                    heroKillDao.insert(
                          getHero(lineSplit[5]), //Killer hero
                          getHero(lineSplit[1]), //Killed hero
                          dotaMatch,
                          createdOn);
                } else if (line.contains("casts")) {
                    heroCastAbilityDao.insert(
                          getHero(lineSplit[1]), //hero casted spell
                          getAbility(lineSplit[4], getHero(lineSplit[1])),
                          lineSplit[5],
                          dotaMatch);
                } else if (line.contains("hits") && lineSplit[3].contains(HERO_PREFIX)) {
                    heroDamageDao.insert(
                          getHero(lineSplit[1]), //hero who HIT
                          getHero(lineSplit[3]),  //hero who was damaged
                          dotaMatch,
                          Long.valueOf(lineSplit[7]), //Number of damage done
                          getAbility(lineSplit[5], getHero(lineSplit[1])),
                          createdOn);
                }
            }
        }
    }

    private Hero getHero(String heroName) {
        heroName = heroName.substring(14);
        Hero hero;
        if(savedHeros.containsKey(heroName)) { //hero name
            hero = savedHeros.get(heroName);
        } else {
            hero = heroDao.insert(heroName);
            savedHeros.put(heroName, hero);
        }
        return hero;
    }

    private Item getItem(String itemName) {
        itemName = itemName.substring(5);
        Item item = null;
        if(savedItems.containsKey(itemName)) { //item name
            item = savedItems.get(itemName);
        } else {
            item = itemDao.insert(itemName);
            savedItems.put(itemName, item);
        }
        return item;
    }

    private Ability getAbility(String abilityName, Hero hero) {

        if(!DOTA_UNKNOWN.equalsIgnoreCase(abilityName) && abilityName.contains(hero.getHeroName())) {
            abilityName = abilityName.substring(hero.getHeroName().length()+1);
        }

        Ability ability = null;
        if(savedAbilities.containsKey(abilityName)) { //ability name
            ability = savedAbilities.get(abilityName);
        } else {
            ability = abilityDao.insert(abilityName);
            savedAbilities.put(abilityName, ability);
        }
        return ability;
    }

    private Long getTime(String eventTime) {
        String time = eventTime.substring(1, eventTime.length()-1);

        Long timestamp = Duration.between(
              LocalTime.MIN ,
              LocalTime.parse( time )
        ).toMillis();

        return timestamp;
    }

    /**
     * Returns the HEROES and their KILLING FREQUENCY in a given MATCH
     * Note: returns only the HEROES who have killed AT-LEAST ONCE
     * @param matchId
     * @return
     * @throws Exception
     */
    @Override public List<HeroKills> getHeroKills(Long matchId) throws Exception {
        try {
            return heroDao.getHeroKills(matchId);
        } catch (Exception ex){
            log.debug("Service Exception in MatchService.getHeroKills", ex);
            throw new Exception("Service Exception in MatchService.getHeroKills");
        }
    }

    /**
     * Returns Items along with the timestamp bought by the specified HERO (in a given match)
     * @param heroName
     * @param matchId
     * @return
     * @throws Exception
     */
    @Override public List<HeroItems> getHeroItems(String heroName, Long matchId) throws Exception {
        try {
            return heroDao.getItemPurchasedByHeroAndMatch(heroName, matchId);
        } catch (Exception ex){
            log.debug("Service Exception in MatchService.getHeroItems", ex);
            throw new Exception("Service Exception in MatchService.getHeroItems");
        }
    }

    /**
     * Returns all the abilities along with their frequencies the specified hero has casted (in a given match)
     * @param heroName
     * @param matchId
     * @return
     * @throws Exception
     */
    @Override public List<HeroSpells> getHeroSpells(String heroName, Long matchId) throws Exception {
        try {
            return heroDao.getSpellCastedByHeroAndMatch(heroName, matchId);
        } catch (Exception ex){
            log.debug("Service Exception in MatchService.getHeroSpells", ex);
            throw new Exception("Service Exception in MatchService.getHeroSpells");
        }
    }

    /**
     * Returns all the heroes and number of times they were killed by the specified hero, in a match
     * @param heroName
     * @param matchId
     * @return
     * @throws Exception
     */
    @Override public List<HeroDamage> getHeroDamages(String heroName, Long matchId) throws Exception {
        try {
            return heroDao.getDamagesByHeroAndMatch(heroName, matchId);
        } catch (Exception ex){
            log.debug("Service Exception in MatchService.getHeroDamages", ex);
            throw new Exception("Service Exception in MatchService.getHeroDamages");
        }
    }
}
