package com.happy3w.memoryassistant.service;

import com.happy3w.memoryassistant.CommonAppTest;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.repository.MAInformationRepository;
import com.happy3w.memoryassistant.repository.MAKeywordRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class MAInformationSvcTest extends CommonAppTest {

    @Autowired
    private MAInformationRepository maInformationRepository;

    @Autowired
    private MAKeywordRepository maKeywordRepository;

    @Test
    public void should_read_infor_with_keyword_when_query_info() {

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

        maKeywordRepository.save(key1);
        maKeywordRepository.save(key2);

        Assert.assertTrue(key1.getId() > 0);
        Assert.assertTrue(key2.getId() > 0);

        maInformationRepository.save(info);
        Assert.assertTrue(info.getId() > 0);


        MAInformation newInfo = maInformationRepository.findOne(info.getId());
        Assert.assertNotNull(newInfo);
        Assert.assertFalse(newInfo.getLstKeyword().isEmpty());
    }
}
