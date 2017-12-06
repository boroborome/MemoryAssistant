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
    @Query(value = "select count(*),ti.* from tblInformation ti join tblInfoKeyRelation tr on ti.createTime=tr.infoid " +
            "where tr.wordid in (?) group by ti.createtime,ti.modifytime,ti.content,ti.keywords having count(*)>=?1.size()",
        nativeQuery = true)
    List<MAInformation> findAllByLstKeywordFullMatch(Collection<Long> keywords);
}
