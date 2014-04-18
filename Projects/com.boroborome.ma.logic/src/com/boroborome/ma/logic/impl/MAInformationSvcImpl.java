/**
 * 
 */
package com.boroborome.ma.logic.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.boroborme.ma.model.MAInformation;
import com.boroborme.ma.model.MAInformationCondition;
import com.boroborme.ma.model.MAKeyword;
import com.boroborme.ma.model.svc.IMAInformationSvc;
import com.boroborme.ma.model.svc.IMAKeywordSvc;
import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.model.EventContainer;
import com.boroborome.footstone.model.IBufferIterator;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;
import com.boroborome.footstone.sql.IFillSql;
import com.boroborome.footstone.svc.IDataChangeListener;
import com.boroborome.footstone.svc.IDataCondition;
import com.boroborome.ma.logic.res.ResConst;

/**
 * @author boroborome
 *
 */
public class MAInformationSvcImpl implements IMAInformationSvc
{
private static Logger logger = Logger.getLogger(MAInformationSvcImpl.class);
	
	private IDatabaseMgrSvc dbMgrSvc;
	private EventContainer<IDataChangeListener<MAInformation>> eventContainer = new EventContainer<IDataChangeListener<MAInformation>>(IDataChangeListener.class);
	private IMAKeywordSvc keywordSvc;
    
	public MAInformationSvcImpl(IDatabaseMgrSvc dbMgrSvc, IMAKeywordSvc keywordSvc)
	{
		super();
		this.dbMgrSvc = dbMgrSvc;
		this.keywordSvc = keywordSvc;
	}

	@Override
	public void create(Iterator<MAInformation> it) throws MessageException
	{
		dbMgrSvc.executeSql("insert into tblInformation(createTime,modifyTime,content,keywords) values(?,?,?,?)", it,
	            new IFillSql<MAInformation>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAInformation value) throws SQLException
	                {
	                	keywordSvc.saveAndUpdate(value.getLstKeyword());
	                	
	                    statement.setLong(1, value.getCreateTime());
	                    statement.setLong(2, value.getModifyTime());
	                    statement.setString(3, value.getContent());
	                    statement.setString(4, MAKeyword.list2String(value.getLstKeyword()));
	                }
	                
	                @Override
	                public void onSuccess(MAInformation value)
	                {
	                	updateReleation(value);
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_CREATED, value);
	                }
	            });
	}
	
	public void updateReleation(final MAInformation information)
	{
		if (information == null)
		{
			return;
		}
		
		dbMgrSvc.executeUpdateSql("delete from tblInfoKeyRelation where infoid=?", 
    			Long.valueOf(information.getCreateTime()));
		
		dbMgrSvc.executeSql("insert into tblInfoKeyRelation(wordid,infoid) values(?,?)", information.getLstKeyword().iterator(),
	            new IFillSql<MAKeyword>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAKeyword value) throws SQLException
	                {
	                    statement.setLong(1, value.getWordid());
	                    statement.setLong(2, information.getCreateTime());
	                }
	            });
	}
	
	@Override
	public void modify(Iterator<MAInformation> it) throws MessageException
	{
		dbMgrSvc.executeSql("update tblInformation set modifyTime=?,content=?,keywords=? where createTime=?", it,
	            new IFillSql<MAInformation>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAInformation value) throws SQLException
	                {
	                	keywordSvc.saveAndUpdate(value.getLstKeyword());
	                	
	                    statement.setLong(1, value.getModifyTime());
	                    statement.setString(2, value.getContent());
	                    statement.setString(3, MAKeyword.list2String(value.getLstKeyword()));
	                    statement.setLong(4, value.getCreateTime());
	                }
	                
	                @Override
	                public void onSuccess(MAInformation value)
	                {
	                	updateReleation(value);
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_MODIFIED, value);
	                }
	            });
	}

	@Override
	public void delete(Iterator<MAInformation> it) throws MessageException
	{
		dbMgrSvc.executeSql("delete from tblInformation where createTime=?", it,
	            new IFillSql<MAInformation>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAInformation value) throws SQLException
	                {
	                	dbMgrSvc.executeUpdateSql("delete from tblInfoKeyRelation where infoid=?", 
	                			Long.valueOf(value.getCreateTime()));
	                	
	                    statement.setLong(1, value.getCreateTime());
	                }
	                
	                @Override
	                public void onSuccess(MAInformation value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_DELETED, value);
	                }
	            });
	}

	//TODO:[optimize] implement a sql function to summery all keyword of a information.
	//URL:http://db.apache.org/derby/docs/10.9/devguide/index.html
	//Title:Derby server-side programming
	@Override
	public IBufferIterator<MAInformation> query(IDataCondition<MAInformation> condition) throws MessageException
	{
		IBufferIterator<MAInformation> result = null;
		MAInformationCondition c = (MAInformationCondition) condition;
		
		ResultSet queryResult = null;
        if (c.getLstKeyword() == null || c.getLstKeyword().isEmpty())
        {
        	queryResult = dbMgrSvc.executeQuery("select * from tblInformation");
        }
        else
        {
        	Iterable<MAKeyword> itNoID = keywordSvc.updateID(c.getLstKeyword());
        	if (itNoID != null)
        	{
        		return null;
        	}
        	
        	List<Long> lstID = new ArrayList<Long>();
        	StringBuilder sqlBuf = new StringBuilder("select count(*),ti.* from tblInformation ti join tblInfoKeyRelation tr on ti.createTime=tr.infoid where tr.wordid in (");
        	for (MAKeyword key : c.getLstKeyword())
        	{
        		if (!lstID.isEmpty())
        		{
        			sqlBuf.append(',');
        		}
        		sqlBuf.append('?');
        		lstID.add(Long.valueOf(key.getWordid()));
        	}
        	sqlBuf.append(") group by ti.createtime,ti.modifytime,ti.content,ti.keywords having count(*)>=?");
        	lstID.add(Long.valueOf(lstID.size()));
//        	"select count(*),ti.* from tblInformation ti join tblInfoKeyRelation tr on ti.createTime=tr.infoid where tr.wordid in (1,2) group by ti.createtime,ti.modifytime,ti.content having count(*)>=2"
        	queryResult = dbMgrSvc.executeQuery(sqlBuf.toString(), lstID.toArray());
        }
        
		try
		{
			if (queryResult != null && !queryResult.isClosed())
			{
				result = new MAInformationDBIterator(queryResult);
			}
		}
		catch (SQLException e)
		{
			try
			{
				queryResult.close();
			}
			catch (SQLException e1)
			{
				logger.error("close rs failed.", e1);
			}
				
			throw new MessageException(ResConst.ResKey, ResConst.FailedInExeSql);
		}
		
		//This statement and rs will be used by MAKeywordDBIterator.
		//So they can't be closed here
        return result;
	}

	@Override
	public EventContainer<IDataChangeListener<MAInformation>> getEventContainer()
	{
		return this.eventContainer;
	}
}
