package gg.bayes.challenge.dao;

import gg.bayes.challenge.domain.DotaMatch;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class DotaMatchDao extends BaseDao<DotaMatch> {

   public DotaMatch insert(String matchName) {
      DotaMatch dotaMatch = new DotaMatch();
      dotaMatch.setName(matchName);

      create(dotaMatch);
      return dotaMatch;
   }

}
