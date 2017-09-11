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
 * 带有缓存的迭代器<br>
 * 当执行next后的值可以通过current方法重新获得。几点注意事项<br>
 * <ol>
 * <li>当没有执行next方法时current方法会返回null，或者其他表示无效的值</li>
 * <li>当hasNext返回false的时候，current中仍然保存最后的元素，读取的时候不要遗漏</li>
 * </ol>
 * @author BoRoBoRoMe
 *
 */
public interface IBufferIterator<E> extends Iterator<E>
{
    /**
     * 执行next后保存next的结果
     * @return
     */
    E current();
}
