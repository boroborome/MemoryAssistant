/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2011-6-24
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprecated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.happy3w.footstone.model;

import java.util.Iterator;

/**
 * @author BoRoBoRoMe
 */
public abstract class AbstractBufferIterator<E> implements IBufferIterator<E> {
    protected E curItem;

    @Override
    public E current() {
        return curItem;
    }

    /**
     * 将it转换为一个AbstractBufferIterator
     *
     * @param it
     * @return
     */
    public static <T> AbstractBufferIterator<T> from(Iterator<T> it) {
        return new IteratorAdapter<T>(it);
    }

    public static <T> AbstractBufferIterator<T> from(T item) {
        return new SingleIteratorAdapter<T>(item);
    }

    private static class SingleIteratorAdapter<T> extends AbstractBufferIterator<T> {
        private T item;

        /**
         * @param item
         */
        public SingleIteratorAdapter(T item) {
            this.item = item;
        }

        @Override
        public boolean hasNext() {
            return item != null;
        }

        @Override
        public T next() {
            T t = item;
            item = null;
            return t;
        }

        @Override
        public void remove() {
            item = null;
        }
    }

    private static class IteratorAdapter<T> extends AbstractBufferIterator<T> {
        private Iterator<T> it;

        /**
         * @param it
         */
        public IteratorAdapter(Iterator<T> it) {
            this.it = it;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            curItem = it.next();
            return curItem;
        }

        @Override
        public void remove() {
            it.remove();
        }
    }
}
