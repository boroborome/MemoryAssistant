/**
 * 
 */
package com.boroborome.ma.logic.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.boroborme.ma.model.MAKeyword;
import com.boroborme.ma.model.MAKeywordCondition;
import com.boroborme.ma.model.svc.IMAKeywordSvc;
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
public class MAKeywordSvcImpl implements IMAKeywordSvc
{
	private static Logger log = Logger.getLogger(MAKeywordSvcImpl.class);
	
	private IDatabaseMgrSvc dbMgrSvc;
	private EventContainer<IDataChangeListener<MAKeyword>> eventContainer = new EventContainer<IDataChangeListener<MAKeyword>>(IDataChangeListener.class);
    
	public MAKeywordSvcImpl(IDatabaseMgrSvc dbMgrSvc)
	{
		super();
		this.dbMgrSvc = dbMgrSvc;
	}

	@Override
	public void create(IBufferIterator<MAKeyword> it) throws MessageException
	{
		dbMgrSvc.executeSql("insert into tblKeyWord(wordid,keyword) values(?,?)", it,
	            new IFillSql<MAKeyword>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAKeyword value) throws SQLException
	                {
	                    statement.setLong(1, value.getWordid());
	                    statement.setString(2, value.getKeyword());
	                }
	                
	                @Override
	                public void onSuccess(MAKeyword value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_CREATED, value);
	                }
	            });
	}

	@Override
	public void modify(IBufferIterator<MAKeyword> it) throws MessageException
	{
		throw new UnsupportedOperationException("The keyword does not support modify.");
	}

	@Override
	public void delete(IBufferIterator<MAKeyword> it) throws MessageException
	{
		dbMgrSvc.executeSql("delete tblKeyWord where wordid=?", it,
	            new IFillSql<MAKeyword>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAKeyword value) throws SQLException
	                {
	                    statement.setLong(1, value.getWordid());
	                }
	                
	                @Override
	                public void onSuccess(MAKeyword value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_DELETED, value);
	                }
	            });
	}

	@Override
	public IBufferIterator<MAKeyword> query(IDataCondition<MAKeyword> condition) throws MessageException
	{
		IBufferIterator<MAKeyword> result = null;
		SimpleSqlBuilder builder = new SimpleSqlBuilder("select * from tblKeyWord");
        MAKeywordCondition c = (MAKeywordCondition) condition;
        if (c.getKeywordLike() != null && c.getKeywordLike().isEmpty())
        {
        	builder.appendCondition(" where keyword like ?", c.getKeywordLike());
        }
        
    	PreparedStatement statement = builder.createStatement(dbMgrSvc);
    	ResultSet rs = null;
		try
		{
			rs = statement.executeQuery();
			if (rs != null && !rs.isClosed())
			{
				result = new MAKeywordDBIterator(rs);
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
	public EventContainer<IDataChangeListener<MAKeyword>> getEventContainer()
	{
		return this.eventContainer;
	}
}
