/**
 *
 */
package com.happy3w.memoryassistant.view.query;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

/**
 * @author boroborome
 *
 */
@Slf4j
public class QueryAssistant<CondtionType, DataType> {
    private CondtionType condition;
    private volatile boolean conditionChanged = false;
    private Object conditionLock = new Object();

    private IQueryLogic<CondtionType, DataType> logic;

    //TODO [optimize] can implements with a thread pool
    private QueryThread queryThread;
    private String threadName;

    public QueryAssistant(String threadName, IQueryLogic<CondtionType, DataType> logic) {
        if (logic == null) {
            throw new IllegalArgumentException("Argument service can't be null.");
        }
        this.logic = logic;
        this.threadName = threadName;
    }


    /**
     * @return the prefix
     */
    public CondtionType getCondtion() {
        return condition;
    }


    /**
     * @param condition the prefix to set
     */
    public void setCondition(CondtionType condition) {
        synchronized (conditionLock) {
            if (this.condition == condition) {
                return;
            }
            if (condition == null || !condition.equals(this.condition)) {
                this.condition = condition;
                conditionChanged = true;
                if (queryThread == null) {
                    queryThread = new QueryThread();
                    queryThread.setName(threadName);
                    queryThread.start();
                }
            }
        }
    }


    private class QueryThread extends Thread {
        @Override
        public void run() {
            try {
                do {
                    conditionChanged = false;
                    //sleep
                    Thread.sleep(100);
                    if (conditionChanged) {
                        continue;
                    }

                    //clear table
                    logic.clearView();
                    if (conditionChanged) {
                        continue;
                    }

                    //query
                    Iterator<DataType> it = logic.query(condition);
                    if (conditionChanged) {
                        continue;
                    }

                    //show result
                    logic.showData(it);
                    synchronized (conditionLock) {
                        if (conditionChanged) {
                            continue;
                        }
                        queryThread = null;
                        break;
                    }
                }
                while (true);

            } catch (Exception e) {
                log.error("Failed in querying keyword.", e);
            } finally {
                queryThread = null;
            }
        }
    }
}
