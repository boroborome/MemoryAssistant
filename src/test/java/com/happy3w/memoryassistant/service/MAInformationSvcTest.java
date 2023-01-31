package com.happy3w.memoryassistant.service;

import com.happy3w.footstone.model.AbstractBufferIterator;
import com.happy3w.memoryassistant.CommonAppTest;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.repository.MAInformationRepository;
import com.happy3w.memoryassistant.repository.MAKeywordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class MAInformationSvcTest extends CommonAppTest {

    @Autowired
    private MAInformationRepository maInformationRepository;

    @Autowired
    private MAKeywordRepository maKeywordRepository;

    @Autowired
    private MAInformationSvc maInformationSvc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void should_read_info_with_keyword_when_query_info() {

        MAKeyword key1 = MAKeyword.builder()
                .keyword("key1")
                .build();
        MAKeyword key2 = MAKeyword.builder()
                .keyword("key2")
                .build();
        MAInformation info = MAInformation.builder()
                .content("test info")
                .lstKeyword(Arrays.asList(
                        key1, key2
                ))
                .build();

        maInformationSvc.create(AbstractBufferIterator.from(info));

        Assertions.assertTrue(key1.getId() > 0);
        Assertions.assertTrue(key2.getId() > 0);
        Assertions.assertTrue(info.getId() > 0);

        entityManager.flush();
        jdbcTemplate.queryForList("select * from tblInfoKeyRelation");
        jdbcTemplate.queryForList("select * from tblInformation");
        jdbcTemplate.queryForList("select * from tblKeyWord");

        MAInformation newInfo = maInformationRepository.findById(info.getId()).get();
        Assertions.assertNotNull(newInfo);
        Assertions.assertFalse(newInfo.getLstKeyword().isEmpty());

        List<MAInformation> key1List = maInformationSvc.findAllByLstKeywordFullMatch(Arrays.asList(key1.getId()));
        Assertions.assertFalse(key1List.isEmpty());
        List<MAInformation> key2List = maInformationSvc.findAllByLstKeywordFullMatch(Arrays.asList(key2.getId()));
        Assertions.assertFalse(key2List.isEmpty());
        List<MAInformation> key12List = maInformationSvc.findAllByLstKeywordFullMatch(Arrays.asList(key1.getId(), key2.getId()));
        Assertions.assertFalse(key12List.isEmpty());
    }

}
