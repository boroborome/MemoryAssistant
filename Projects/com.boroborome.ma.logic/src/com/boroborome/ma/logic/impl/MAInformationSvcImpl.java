/**
 * 
 */
package com.boroborome.ma.logic.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.boroborme.ma.model.MAInformation;
import com.boroborme.ma.model.MAInformationCondition;
import com.boroborme.ma.model.svc.IMAInformationSvc;
import com.boroborome.footstone.exception.MessageException;
import com.boroborome.footstone.model.EventContainer;
import com.boroborome.footstone.model.IBufferIterator;
import com.boroborome.footstone.sql.IDatabaseMgrSvc;
import com.boroborome.footstone.sql.IFillSql;
import com.boroborome.footstone.sql.SimpleSqlBuilder;
import com.boroborome.footstone.svc.IDataChangeListener;
import com.boroborome.footstone.svc.IDataCondition;
import com.boroborome.ma.logic.res.ResConst;

/**
 * @author boroborome
 *
 */
public class MAInformationSvcImpl implements IMAInformationSvc
{
private static Logger log = Logger.getLogger(MAInformationSvcImpl.class);
	
	private IDatabaseMgrSvc dbMgrSvc;
	private EventContainer<IDataChangeListener<MAInformation>> eventContainer = new EventContainer<IDataChangeListener<MAInformation>>(IDataChangeListener.class);
	private MAKeywordSvcImpl keywordSvc;
    
	public MAInformationSvcImpl(IDatabaseMgrSvc dbMgrSvc, MAKeywordSvcImpl keywordSvc)
	{
		super();
		this.dbMgrSvc = dbMgrSvc;
		this.keywordSvc = keywordSvc;
	}

	@Override
	public void create(Iterator<MAInformation> it) throws MessageException
	{
		dbMgrSvc.executeSql("insert into tblInformation(createTime,modifyTime,content) values(?,?,?)", it,
	            new IFillSql<MAInformation>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAInformation value) throws SQLException
	                {
	                	keywordSvc.saveAndUpdate(value.getLstKeyword());
	                	
	                    statement.setLong(1, value.getCreateTime());
	                    statement.setLong(2, value.getModifyTime());
	                    statement.setString(3, value.getContent());
	                }
	                
	                @Override
	                public void onSuccess(MAInformation value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_CREATED, value);
	                }
	            });
	}
	
	@Override
	public void modify(Iterator<MAInformation> it) throws MessageException
	{
		dbMgrSvc.executeSql("update tblInformation set modifyTime=?,content=? where createTime=?", it,
	            new IFillSql<MAInformation>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAInformation value) throws SQLException
	                {
	                	keywordSvc.saveAndUpdate(value.getLstKeyword());
	                	
	                    statement.setLong(1, value.getModifyTime());
	                    statement.setString(2, value.getContent());
	                    statement.setLong(3, value.getCreateTime());
	                }
	                
	                @Override
	                public void onSuccess(MAInformation value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_MODIFIED, value);
	                }
	            });
	}

	@Override
	public void delete(Iterator<MAInformation> it) throws MessageException
	{
		dbMgrSvc.executeSql("delete tblInformation where createTime=?", it,
	            new IFillSql<MAInformation>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAInformation value) throws SQLException
	                {
	                    statement.setLong(1, value.getCreateTime());
	                }
	                
	                @Override
	                public void onSuccess(MAInformation value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_DELETED, value);
	                }
	            });
	}

	@Override
	public IBufferIterator<MAInformation> query(IDataCondition<MAInformation> condition) throws MessageException
	{
		IBufferIterator<MAInformation> result = null;
		SimpleSqlBuilder builder = new SimpleSqlBuilder("select * from tblInformation");
		//(select * from tblInfoKeyRelation tbr where tbr.wordid in ())
		MAInformationCondition c = (MAInformationCondition) condition;
//        if (c.getLstKeyword() != null && c.getLstKeyword().isEmpty())
//        {
//        	builder.appendCondition(" where keyword like ?", c.getKeywordLike());
//        }
        
    	PreparedStatement statement = builder.createStatement(dbMgrSvc);
    	ResultSet rs = null;
		try
		{
			rs = statement.executeQuery();
			if (rs != null && !rs.isClosed())
			{
				result = new MAInformationDBIterator(rs);
			}
		}
		catch (SQLException e)
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e1)
				{
					log.error("close rs failed.", e1);
				}
			}
			try
			{
				statement.close();
			}
			catch (SQLException e1)
			{
				log.error("close statement failed.", e1);
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
