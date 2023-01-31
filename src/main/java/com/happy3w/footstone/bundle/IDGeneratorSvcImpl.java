/*
 * <P>Title:      任务管理器模型 V1.0</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-10-6
 */
package com.happy3w.footstone.bundle;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.svc.IIDGeneratorSvc;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * <DT><B>Title:</B></DT>
 * <DD>任务管理模型</DD>
 * <DT><B>Description:</B></DT>
 * <DD>生成ID的工具。生成的ID是自增长的</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 *
 * @author BoRoBoRoMe
 * @version 1.0 2011-10-6
 */
@Slf4j
public class IDGeneratorSvcImpl implements IIDGeneratorSvc {

    /**
     * 保存某个类型对应的索引
     */
    private Map<Class<?>, IndexCreator> mapIndex = new HashMap<Class<?>, IndexCreator>();

    @Override
    public long nextIndex(Class<?> type) {
        IndexCreator creator = mapIndex.get(type);
        if (creator == null) {
            synchronized (mapIndex) {
                creator = new IndexCreator(() -> 0L);
                mapIndex.put(type, creator);
            }
        }
        return creator.nextIndex();
    }

    @Override
    public void resetAll() {
        for (IndexCreator creator : mapIndex.values()) {
            creator.reset();
        }
    }

    private static class IndexCreator {
        private long index;

        private final Supplier<Long> curIdGenerator;

        /**
         * 构造方法
         *
         * @param curIdGenerator 起始Index，这个索引不会被nextIndex返回，下一个值才是可用值
         */
        public IndexCreator(Supplier<Long> curIdGenerator) {
            super();
            this.curIdGenerator = curIdGenerator;
            reset();
        }

        public void reset() {
            Long value = curIdGenerator.get();
            this.index = value == null ? 0L : value;
        }

        /**
         * 获取下一个可用Index
         *
         * @return
         */
        public synchronized long nextIndex() {
            index++;
            return index;
        }
    }

    @Override
    public <E> void registerGenerator(Class<E> type, Supplier<Long> curIdGenerator) {
        try {
            mapIndex.put(type, new IndexCreator(curIdGenerator));
        } catch (MessageException e) {
            log.error("init maxid failed.", e);
            //说明系统没有数据库，此时自动创建一次数据库
            //为了安全还是不要自动创建数据库的好
//            try
//            {
//                getService(ITaskMgrSysSvc.class).install();
//            }
//            catch (MessageException e1)
//            {
//                log.error("auto install failed.", e1);
//            }
        }
    }
}
