package com.happy3w.memoryassistant.service;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.model.AbstractBufferIterator;
import com.happy3w.footstone.model.EventContainer;
import com.happy3w.footstone.model.IBufferIterator;
import com.happy3w.footstone.svc.IAutoIDDataSvc;
import com.happy3w.footstone.svc.IDataChangeListener;
import com.happy3w.footstone.svc.IDataCondition;
import com.happy3w.footstone.svc.IIDGeneratorSvc;
import com.happy3w.memoryassistant.model.MAInfoKey;
import com.happy3w.memoryassistant.model.MAInformation;
import com.happy3w.memoryassistant.model.MAInformationCondition;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.repository.MAInfoKeyRepository;
import com.happy3w.memoryassistant.repository.MAInformationRepository;
import com.happy3w.toolkits.iterator.EasyIterator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MAInformationSvc implements IAutoIDDataSvc<MAInformation> {
    private final MAKeywordSvc keywordSvc;
    private final MAInformationRepository maInformationRepository;
    private final MAInfoKeyRepository maInfoKeyRepository;
    private final IIDGeneratorSvc iidGeneratorSvc;

    private EventContainer<IDataChangeListener<MAInformation>> eventContainer = new EventContainer<IDataChangeListener<MAInformation>>(IDataChangeListener.class);

    public MAInformationSvc(IIDGeneratorSvc iidGeneratorSvc,
                            MAKeywordSvc keywordSvc,
                            MAInformationRepository maInformationRepository,
                            MAInfoKeyRepository maInfoKeyRepository,
                            JdbcTemplate jdbcTemplate) {
        super();
        this.iidGeneratorSvc = iidGeneratorSvc;
        this.keywordSvc = keywordSvc;
        this.maInformationRepository = maInformationRepository;
        this.maInfoKeyRepository = maInfoKeyRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initService() {
        iidGeneratorSvc.init(MAInformation.class, this);
    }

    @Override
    @Transactional
    public void create(Iterator<MAInformation> it) throws MessageException {
        saveOrModify(EasyIterator.fromIterator(it).peek(info ->
                        info.setId(iidGeneratorSvc.nextIndex(MAInformation.class))),
                IDataChangeListener.EVENT_CREATED);
    }


    @Override
    @Transactional
    public void modify(Iterator<MAInformation> it) throws MessageException {
        saveOrModify(it, IDataChangeListener.EVENT_MODIFIED);
    }

    private void saveOrModify(Iterator<MAInformation> it, String eventType) throws MessageException {
        it.forEachRemaining(info -> {
            keywordSvc.saveAndUpdate(info.getLstKeyword());
            maInformationRepository.saveAndFlush(info);
            maInfoKeyRepository.deleteAllByInfoid(info.getId());
            maInfoKeyRepository.saveAll(info.getLstKeyword().stream()
                    .map(key -> new MAInfoKey(info.getId(), key.getId()))
                    .collect(Collectors.toList())
            );
            eventContainer.fireEvents(eventType, info);
        });
    }

    @Override
    @Transactional
    public void delete(Iterator<MAInformation> it) throws MessageException {
        it.forEachRemaining(info -> {
            maInfoKeyRepository.deleteAllByInfoid(info.getId());
            maInformationRepository.deleteById(info.getId());
            eventContainer.fireEvents(IDataChangeListener.EVENT_DELETED, info);
        });
    }

    private final JdbcTemplate jdbcTemplate;

    public List<MAInformation> findAllByLstKeywordFullMatch(List<Long> keyIDs) {
        StringBuilder buff = new StringBuilder("select ti.* from tblInformation ti join tblInfoKeyRelation tr " +
                "on ti.tid=tr.infoid where tr.wordid in (");
        for (Long l : keyIDs) {
            buff.append("?,");
        }
        buff.setLength(buff.length() - 1);
        buff.append(") group by ti.tid,ti.createdTime,ti.modifytime,ti.content,ti.keywords having count(*)>=?");

        List<Long> parameters = new ArrayList<>(keyIDs);
        parameters.add((long) keyIDs.size());
        return jdbcTemplate.query(buff.toString(),
                new BeanPropertyRowMapper<MAInformation>(MAInformation.class) {
                    @Override
                    public MAInformation mapRow(ResultSet rs, int rowNum) throws SQLException {
                        MAInformation info = super.mapRow(rs, rowNum);
                        info.setId(rs.getLong("tid"));
                        info.setLstKeyword(MAKeyword.string2List(rs.getString("keywords")));
                        return info;
                    }
                },
                parameters.toArray()
        );
    }

    //TODO:[optimize] implement a sql function to summery all keyword of a information.
    //URL:http://db.apache.org/derby/docs/10.9/devguide/index.html
    //Title:Derby server-side programming
    @Override
    public IBufferIterator<MAInformation> query(IDataCondition<MAInformation> condition) throws MessageException {
        IBufferIterator<MAInformation> result = null;
        MAInformationCondition c = (MAInformationCondition) condition;

        List<MAInformation> queryResult = null;
        if (c.getLstKeyword() == null || c.getLstKeyword().isEmpty()) {
            queryResult = maInformationRepository.findAll();
        } else {
            Iterable<MAKeyword> itNoID = keywordSvc.filterNoIdKeywords(c.getLstKeyword());
            if (itNoID != null) {
                return null;
            }

            List<Long> lstID = new ArrayList<Long>();
            for (MAKeyword key : c.getLstKeyword()) {
                lstID.add(Long.valueOf(key.getId()));
            }
//			queryResult = jdbcTemplate.query("");
            queryResult = findAllByLstKeywordFullMatch(lstID);
        }

        //This statement and rs will be used by MAKeywordDBIterator.
        //So they can't be closed here
        return AbstractBufferIterator.from(queryResult.iterator());
    }

    @Override
    public EventContainer<IDataChangeListener<MAInformation>> getEventContainer() {
        return this.eventContainer;
    }

    @Override
    public long getMaxID() throws MessageException {
        return jdbcTemplate.queryForObject("select max(tid) as maxID from tblInformation", Long.class);
    }
}
