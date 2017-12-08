package com.happy3w.memoryassistant.repository;

import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MAInformationRepository extends JpaRepository<MAInformation, Long> {
    @Query(value = "select ti.* from tblInformation ti join tblInfoKeyRelation tr on ti.tid=tr.infoid " +
            "where tr.wordid in (?) group by ti.tid,ti.createdTime,ti.modifytime,ti.content,ti.keywords having count(*)>=?",
        nativeQuery = true)
//    @Query(value = "select info from MAInformation info inner join info.lstKeyword where info.lstKeyword in ?1")
    List<MAInformation> findAllByLstKeywordFullMatch(Collection<Long> keywords, int size);
}
