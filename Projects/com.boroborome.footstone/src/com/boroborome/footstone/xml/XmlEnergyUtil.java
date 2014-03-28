/*
 * <P>Title:      [产品名称和版本号]</P>
 * <P>Description:[描述功能、作用、用法和注意事项]</P>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-1
 * @see           [相关类，可选，也可多条]
 * @since         [产品/模块版本，表示从哪个版本开始有]
 * @!deprocated   [表示不建议使用]
 * @modify        [修改记录，可多条，每次修改后增加说明。只包括重要修改，修改人、时间、单号、版本、内容]
 */
package com.boroborome.footstone.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.boroborome.footstone.dataenergy.DataEnergyException;
import com.boroborome.footstone.dataenergy.DefaultObjectMapTable;
import com.boroborome.footstone.dataenergy.IObjectMapTable;
import com.boroborome.footstone.dataenergy.ObjectDataEnergy;
import com.boroborome.footstone.dataenergy.ObjectDataMachine;
import com.boroborome.footstone.util.CommonRes;

/**
 * <DT><B>Title:</B></DT>
 *    <DD>XML引擎常量</DD>
 * <DT><B>Description:</B></DT>
 *    <DD>定义所有XML引擎使用的常量，包括数据类型标记字符串，默认属性名称等。</DD>
 * <P>Copyright:  Copyright (c) 2008</P>
 * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
 * @author        BoRoBoRoMe
 * @version       1.0 2010-3-1
 * @see           {@link XmlDataEnergy}
 */
public final class XmlEnergyUtil
{
    /**
     * 用于保存对象属性的名称在XML文档中的属性名称。<br>
     * 
     * 如下XML文件表示:清华学校的校长是一个叫BoRoBoRoMe的人。
     * 学校和这个人之间的关系是通过属性_AN_连接起来的。这个字符串是AttributeName的简写,简写是因为这个属性应用会很多，如果较长会导致文件很大。
     * <PRE>
     * {@code
     * <School name="QingHua">
     *      <People _AN_="schoolManager" name="BoRoBoRoMe">
     *      </People>
     * </School>
     * }
     * </PRE>
     */
    public static final String AttributeName = "_AN_"; //$NON-NLS-1$

    /**
     * 名字空间的开头，以这个 开头的属性都只是用于保持文档结构准确的，不需要理会
     */
    public static final String NameSpaceHead = "xmlns:"; //$NON-NLS-1$

    /**
     * <DT><B>Title:</B></DT>
     *    <DD>数据类型标志</DD>
     * <DT><B>Description:</B></DT>
     *    <DD>XML节点中简单数据类型的类型标志</DD>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2010-3-1
     */
    @SuppressWarnings("nls")
	public static final class Type
    {
        /**
         * 字符串
         */
        public static final String StrT = "s";
        
        /**
         * 数字
         */
        public static final String IntT = "i";
        
        /**
         * 长整形
         */
        public static final String LongT = "l";
        
        /**
         * 浮点
         */
        public static final String FloatT = "f";
        
        /**
         * 双精
         */
        public static final String DoubleT = "d";
        
        /**
         * 类型
         */
        public static final String ClassT = "c";
        
        /**
         * 日期时间
         */
        public static final String DateT = "dt";
        
        /**
         * 布尔类型前缀
         */
        public static final String BooleanT = "b";
    }
    
    //放在下面是为了让上面的常量优先加载
    /**
     * 保存所有简单类型的标示字符串和简单类型<br>
     * 用于通过标示或者类型检测类型是否为简单类型。
     */
    private static Map<Object, Object> mapSimpleType;
    
    /**
     * 负责类型转换
     */
    private static Map<String, TypeItem> mapType;
    
    static
    {
        mapSimpleType = new HashMap<Object, Object>();
        mapType = new HashMap<String, TypeItem>();
        
        reg(new TypeItem(Type.StrT, String.class)
        {
            @Override
            public Object textToValue(final String text)
            {
                return text;
            }
        });
        
        reg(new TypeItem(Type.IntT, Integer.class)
        {
            @Override
            public Object textToValue(String text)
            {
                return Integer.valueOf(text);
            }
        });
        reg(new TypeItem(Type.LongT, Long.class)
        {
            @Override
            public Object textToValue(String text)
            {
                return Long.valueOf(text);
            }
        });
        reg(new TypeItem(Type.FloatT, Float.class)
        {
            @Override
            public Object textToValue(String text)
            {
                return Float.valueOf(text);
            }
        });
        
        reg(new TypeItem(Type.DoubleT, Double.class)
        {
            @Override
            public Object textToValue(String text)
            {
                return Double.valueOf(text);
            }
        });
        
        reg(new TypeItem(Type.ClassT, Class.class)
        {
            @SuppressWarnings("unchecked")
            @Override
            public Object textToValue(String text) throws DataEnergyException
            {
                Class c = null;
                try
                {
                    c = Class.forName(text);
                }
                catch (ClassNotFoundException e)
                {
                    throw new DataEnergyException(CommonRes.ResFileName,
                            CommonRes.DataEnergy_NoClass, new Object[]{text}, e);
                }
                return c;
            }
            @SuppressWarnings("unchecked")
            @Override
            public String valueToText(Object value)
            {
                return ((Class) value).getName();
            }
        });
        
        reg(new TypeItem(Type.DateT, Date.class)
        {
            @Override
            public Object textToValue(String text)
            {
                //暂时不支持时间类型
                return null;
            }
        });
        
        reg(new TypeItem(Type.BooleanT, Boolean.class)
        {
            @Override
            public Object textToValue(String text) throws DataEnergyException
            {
                return Boolean.valueOf(text);
            }
        });
    }
    
    private static void reg(final TypeItem item)
    {
        mapSimpleType.put(item.type, item.clazz);
        mapSimpleType.put(item.clazz, item.type);
        mapType.put(item.type, item);
    }
    
    /**
     * 检测给定的类型是否为简单类型
     * @param typeName 类型名称
     * @return 是否简单类型
     */
    public static boolean isSimpleType(final String typeName)
    {
        return mapSimpleType.get(typeName) != null;
    }
    
    /**
     * 值转换为文本
     * @param value 值
     * @return 值对应的文本，不带有类型标记
     */
    public static String valueToText(final Object value)
    {
        TypeItem item = findTypeItem(value);
        if (item != null)
        {
            return item.valueToText(value);
        }
        return null;
    }

    /**
     * 值转换为对应的字符串，带有类型前缀
     * @param value 值
     * @return 带有类型前缀的文本
     */
    public static String valueToTypeText(final Object value)
    {
        TypeItem item = findTypeItem(value);
        if (item != null)
        {
            return item.type + ':' + item.valueToText(value);
        }
        return null;
    }
    
    /**
     * 返回值对应的字符串
     * @param value 值
     * @return 值类型字符串
     */
    public static String valueType(final Object value)
    {
        TypeItem item = findTypeItem(value);
        if (item != null)
        {
            return item.type;
        }
        return null;
    }

    /**
     * 查找简单类型数据对应的类型信息。
     * @param value 简单值
     * @return
     */
    private static TypeItem findTypeItem(final Object value)
    {
        for (TypeItem item : mapType.values())
        {
            if (item.clazz.isInstance(value))
            {
                return item;
            }
        }
        return null;
    }
    
    /**
     * 文本转换为值
     * @param type 类型
     * @param text 值对应的字符串，不带有类型标记
     * @return 值
     * @throws DataEnergyException 提供的字符串无法转换为相应数据时会有异常
     */
    public static Object textToValue(final String type, final String text) throws DataEnergyException
    {
        Object value = null;
        if (type != null)
        {
            TypeItem item = mapType.get(type);
            if (item != null && text != null)
            {
                value = item.textToValue(text);
            }
        }
        return value;
    }
    
    /**
     * 将文本转换为对应的值
     * @param text 带有类型标记的字符串
     * @return 值
     * @throws DataEnergyException 如果字符串无法转换为值，则会跑出异常
     */
    public static Object textToValue(final String text) throws DataEnergyException
    {
        if (text == null)
        {
            return null;
        }
        
        int index = text.indexOf(':');
        if (index < 0)
        {
            return null;
        }
        return textToValue(text.substring(0, index), text.substring(index + 1));
    }
    
    /**
     * <DT><B>Title:</B></DT>
     *    <DD>简单类型信息</DD>
     * <DT><B>Description:</B></DT>
     *    <DD>负责提供类型名称和类型之间的对应关系，以及本类型数据如何与字符串进行转换。</DD>
     * <P>Copyright:  Copyright (c) 2008</P>
     * <P>Company:    BoRoBoRoMe Co. Ltd.</P>
     * @author        BoRoBoRoMe
     * @version       1.0 2010-4-20
     */
    private static abstract class TypeItem
    {
        public final String type;
        public final Class<?> clazz;
        
        /**
         * 构造函数
         * @param type 类型字符串
         * @param clazz 类型
         */
        public TypeItem(final String type, final Class<?> clazz)
        {
            super();
            this.type = type;
            this.clazz = clazz;
        }
        
        public String valueToText(final Object value)
        {
            return value.toString();
        }
        public abstract Object textToValue(final String text) throws DataEnergyException;
    }
    
    /**
     * 检测指定的类型是否为简单类型
     * @param clazz 需要检测的类型
     * @return 检测结果
     */
    public static boolean isSimpleType(final Class<?> clazz)
    {
        return mapSimpleType.get(clazz) != null;
    }
    
    /**
     * 使用XML引擎和对象引擎复制一个对象
     * @param obj 需要复制的对象
     * @return 对象副本
     * @throws DataEnergyException 数据转换异常
     */
    public static Object clone(Object obj) throws DataEnergyException
    {
        DefaultObjectMapTable mapTable = new DefaultObjectMapTable();
        mapTable.setSelfStudy(true);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        saveObject(mapTable, obj, output);
        return loadObject(mapTable, new ByteArrayInputStream(output.toByteArray()));
    }
    
    /**
     * 将对象信息保存到指定的输出流中
     * @param mapTable 保存信息时使用的对象映射表
     * @param source 需要保存的信息
     * @param output 输出流
     * @throws DataEnergyException 转换异常
     */
    public static void saveObject(IObjectMapTable mapTable, Object source, OutputStream output) throws DataEnergyException
    {
        ObjectDataEnergy energy = new ObjectDataEnergy(mapTable);
        XmlDataMachine machine = new XmlDataMachine(output);
        energy.setMachine(machine);
        energy.translateData();
    }
    
    /**
     * 从输入流中获取信息，转换为对象
     * @param mapTable 对象映射表
     * @param input 数据流
     * @return 结果
     * @throws DataEnergyException 转换异常
     */
    public static Object loadObject(IObjectMapTable mapTable, InputStream input) throws DataEnergyException
    {
        XmlDataEnergy energy = new XmlDataEnergy(input);
        ObjectDataMachine machine = new ObjectDataMachine(mapTable);
        energy.setMachine(machine);
        energy.translateData();
        
        return machine.getResult();
    }
    
    /**
     * 从输入流中获取信息，转换为对象
     * @param mapTableStream 对象映射表流
     * @param input 数据流
     * @return 结果
     * @throws DataEnergyException 转换异常
     */
    public static Object loadObject(InputStream mapTableStream, InputStream input) throws DataEnergyException
    {
        DefaultObjectMapTable mapTable = DefaultObjectMapTable.getInstance();
        mapTable.appendMapTable(mapTableStream);
        return loadObject(mapTable, input);
    }
}
