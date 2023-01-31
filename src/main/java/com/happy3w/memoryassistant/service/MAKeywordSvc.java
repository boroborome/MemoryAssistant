package com.happy3w.memoryassistant.service;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.model.AbstractBufferIterator;
import com.happy3w.footstone.model.EventContainer;
import com.happy3w.footstone.model.IBufferIterator;
import com.happy3w.footstone.svc.IAutoIDDataSvc;
import com.happy3w.footstone.svc.IDataChangeListener;
import com.happy3w.footstone.svc.IDataCondition;
import com.happy3w.footstone.svc.IIDGeneratorSvc;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.model.MAKeywordCondition;
import com.happy3w.memoryassistant.repository.MAKeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MAKeywordSvc implements IAutoIDDataSvc<MAKeyword> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MAKeywordRepository maKeywordRepository;

    private final IIDGeneratorSvc iidGeneratorSvc;

    private EventContainer<IDataChangeListener<MAKeyword>> eventContainer = new EventContainer<IDataChangeListener<MAKeyword>>(IDataChangeListener.class);

    public MAKeywordSvc(IIDGeneratorSvc iidGeneratorSvc) {
        super();
        this.iidGeneratorSvc = iidGeneratorSvc;
    }

    @PostConstruct
    public void initService() {
        iidGeneratorSvc.init(MAKeyword.class, this);
    }

    @Override
    public void create(Iterator<MAKeyword> it) throws MessageException {
        it.forEachRemaining(key -> {
            key.setKeyword(key.getKeyword().toLowerCase());
            key.setId(iidGeneratorSvc.nextIndex(MAKeyword.class));
            maKeywordRepository.save(key);
            eventContainer.fireEvents(IDataChangeListener.EVENT_CREATED, key);
        });
    }

    @Override
    public void modify(Iterator<MAKeyword> it) throws MessageException {
        throw new UnsupportedOperationException("The keyword does not support modify.");
    }

    @Override
    public void delete(Iterator<MAKeyword> it) throws MessageException {
        it.forEachRemaining(key -> {
            maKeywordRepository.deleteById(key.getId());
            eventContainer.fireEvents(IDataChangeListener.EVENT_DELETED, key);
        });
    }

    @Override
    public IBufferIterator<MAKeyword> query(IDataCondition<MAKeyword> condition) throws MessageException {
        MAKeywordCondition c = (MAKeywordCondition) condition;
        List<MAKeyword> keywords = (c.getKeywordLike() != null && !c.getKeywordLike().isEmpty())
                ? maKeywordRepository.findAllByKeywordLike(c.getKeywordLike().toLowerCase() + '%')
                : maKeywordRepository.findAll();
        return AbstractBufferIterator.from(keywords.iterator());
    }

    @Override
    public EventContainer<IDataChangeListener<MAKeyword>> getEventContainer() {
        return this.eventContainer;
    }

    public void saveAndUpdate(List<MAKeyword> lstKeyword) throws MessageException {
        Iterable<MAKeyword> itNoId = this.filterNoIdKeywords(lstKeyword);
        if (itNoId == null) {
            return;
        }

        this.create(itNoId.iterator());
    }

    /**
     * 获取最大的ID
     *
     * @return
     * @throws MessageException
     */
    @Override
    public long getMaxID() throws MessageException {
        return jdbcTemplate.queryForObject("select max(tid) as maxID from tblKeyWord", Long.class);
    }

    public Iterable<MAKeyword> filterNoIdKeywords(List<MAKeyword> lstKeyword) {
        if (lstKeyword == null || lstKeyword.isEmpty()) {
            return null;
        }

        //Create a sqlBuilder for query keyword
        Map<String, MAKeyword> mapKey = new HashMap<String, MAKeyword>();
        for (int keyIndex = lstKeyword.size() - 1; keyIndex >= 0; --keyIndex) {
            MAKeyword key = lstKeyword.get(keyIndex);
            if (!mapKey.containsKey(key.getKeyword())) {
                mapKey.put(key.getKeyword(), key);
            } else {
                lstKeyword.remove(keyIndex);
            }
        }

        List<MAKeyword> keywords = maKeywordRepository.findAllByKeywordIn(mapKey.keySet());

        //save keyword's id which is exist
        //exist keyword is in itKey
        for (MAKeyword dbKeyword : keywords) {
            MAKeyword keyword = mapKey.get(dbKeyword.getKeyword());
            keyword.setId(dbKeyword.getId());
            mapKey.remove(dbKeyword.getKeyword());
        }
        return mapKey.isEmpty() ? null : mapKey.values();
    }
}
