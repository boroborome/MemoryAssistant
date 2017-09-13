/**
 * 
 */
package com.happy3w.memoryassistant.logic;

import com.happy3w.footstone.exception.MessageException;
import com.happy3w.footstone.model.EventContainer;
import com.happy3w.footstone.model.IBufferIterator;
import com.happy3w.footstone.sql.IDatabaseMgrSvc;
import com.happy3w.footstone.sql.IFillSql;
import com.happy3w.footstone.sql.SimpleSqlBuilder;
import com.happy3w.footstone.svc.IAutoIDDataSvc;
import com.happy3w.footstone.svc.IDataChangeListener;
import com.happy3w.footstone.svc.IDataCondition;
import com.happy3w.footstone.svc.IIDGeneratorSvc;
import com.happy3w.memoryassistant.model.MAKeyword;
import com.happy3w.memoryassistant.model.MAKeywordCondition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author boroborome
 *
 */
@Service
public class MAKeywordSvc implements IAutoIDDataSvc<MAKeyword>
{
	private static Logger log = Logger.getLogger(MAKeywordSvc.class);

	@Autowired
	private IIDGeneratorSvc iidGeneratorSvc;

	@Autowired
	private IDatabaseMgrSvc dbMgrSvc;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private EventContainer<IDataChangeListener<MAKeyword>> eventContainer = new EventContainer<IDataChangeListener<MAKeyword>>(IDataChangeListener.class);
    
	public MAKeywordSvc(IDatabaseMgrSvc dbMgrSvc)
	{
		super();
		this.dbMgrSvc = dbMgrSvc;
	}

	@Override
	public void create(Iterator<MAKeyword> it) throws MessageException
	{
		dbMgrSvc.executeSql("insert into tblKeyWord(wordid,keyword) values(?,?)", it,
	            new IFillSql<MAKeyword>()
	            {
	                @Override
	                public void fill(PreparedStatement statement, MAKeyword value) throws SQLException
	                {
	                    statement.setLong(1, value.getWordid());
	                    statement.setString(2, value.getKeyword().toLowerCase());
	                }
	                
	                @Override
	                public void onSuccess(MAKeyword value)
	                {
	                    eventContainer.fireEvents(IDataChangeListener.EVENT_CREATED, value);
	                }
	            });
	}

	@Override
	public void modify(Iterator<MAKeyword> it) throws MessageException
	{
		throw new UnsupportedOperationException("The keyword does not support modify.");
	}

	@Override
	public void delete(Iterator<MAKeyword> it) throws MessageException
	{
		dbMgrSvc.executeSql("delete from tblKeyWord where wordid=?", it,
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
		SimpleSqlBuilder builder = new SimpleSqlBuilder("select * from tblKeyWord");
        MAKeywordCondition c = (MAKeywordCondition) condition;
        if (c.getKeywordLike() != null && !c.getKeywordLike().isEmpty())
        {
        	builder.appendCondition("keyword like ?", c.getKeywordLike().toLowerCase() + '%');
        }
        
    	return queryBySqlBuilder(builder);
	}

	/**
	 * Query a iterator by sqlBuilder
	 * @param sqlBuilder
	 * @return
	 * @throws MessageException
	 */
	public IBufferIterator<MAKeyword> queryBySqlBuilder(SimpleSqlBuilder sqlBuilder) throws MessageException
	{
		if (sqlBuilder == null)
		{
			return null;
		}
		
		IBufferIterator<MAKeyword> result = jdbcTemplate.execute(sqlBuilder.toString(), new PreparedStatementCallback<IBufferIterator<MAKeyword>>() {
			@Override
			public IBufferIterator<MAKeyword> doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				sqlBuilder.fillParameters(ps);
				ResultSet rs = ps.executeQuery();
				if (rs != null && !rs.isClosed())
				{
					return new MAKeywordDBIterator(rs);
				}
				return null;
			}
		});
//		PreparedStatement statement = sqlBuilder.fillParameters(dbMgrSvc);
//    	ResultSet rs = null;
//		try
//		{
//			rs = statement.executeQuery();
//			if (rs != null && !rs.isClosed())
//			{
//				result = new MAKeywordDBIterator(rs);
//			}
//		}
//		catch (SQLException e)
//		{
//			if (rs != null)
//			{
//				try
//				{
//					rs.close();
//				}
//				catch (SQLException e1)
//				{
//					log.error("close rs failed.", e1);
//				}
//			}
//			try
//			{
//				statement.close();
//			}
//			catch (SQLException e1)
//			{
//				log.error("close statement failed.", e1);
//			}
//
//			throw new MessageException(ResConst.ResKey, ResConst.FailedInExeSql);
//		}
		
		//This statement and rs will be used by MAKeywordDBIterator.
		//So they can't be closed here
        return result;
	}

	@Override
	public EventContainer<IDataChangeListener<MAKeyword>> getEventContainer()
	{
		return this.eventContainer;
	}

	public void saveAndUpdate(List<MAKeyword> lstKeyword) throws MessageException
	{
		Iterable<MAKeyword> itNoId = this.updateID(lstKeyword);
    	if (itNoId == null)
    	{
    		return;
    	}
    	//create keyword which is not exist
    	//keyword not exist is in mapKey,now
		for (MAKeyword keyword : itNoId)
		{
			long newID = iidGeneratorSvc.nextIndex(MAKeyword.class);
			keyword.setWordid(newID);
		}
		this.create(itNoId.iterator());
	}
	
	/**
     * 获取最大的ID
     * @return
     * @throws MessageException
     */
    @Override
    public long getMaxID() throws MessageException
    {
		return jdbcTemplate.queryForObject("select max(wordid) as maxID from tblKeyWord", Long.class);
    }

	public Iterable<MAKeyword> updateID(List<MAKeyword> lstKeyword)
	{
    	if (lstKeyword == null || lstKeyword.isEmpty())
		{
			return null;
		}
		
		//Create a sqlBuilder for query keyword
		Map<String, MAKeyword> mapKey = new HashMap<String, MAKeyword>();
		StringBuilder conditionBuf = new StringBuilder("keyword in (");
		for (int keyIndex = lstKeyword.size() -1 ; keyIndex >= 0; --keyIndex)
		{
			MAKeyword key = lstKeyword.get(keyIndex);
			if (!mapKey.containsKey(key.getKeyword()))
			{
				if (!mapKey.isEmpty())
				{
					conditionBuf.append(',');
				}
				conditionBuf.append('?');
				mapKey.put(key.getKeyword(), key);
			}
			else
			{
				lstKeyword.remove(keyIndex);
			}
		}
		conditionBuf.append(')');
		
		SimpleSqlBuilder sqlBuilder = new SimpleSqlBuilder("select * from tblKeyWord");
		sqlBuilder.appendCondition(conditionBuf.toString(), mapKey.keySet().toArray());
		
		IBufferIterator<MAKeyword> itKey = this.queryBySqlBuilder(sqlBuilder);
		
		//save keyword's id which is exist
		//exist keyword is in itKey
    	while (itKey.hasNext())
    	{
    		MAKeyword dbKeyword = itKey.next();
    		MAKeyword keyword = mapKey.get(dbKeyword.getKeyword());
    		keyword.setWordid(dbKeyword.getWordid());
    		mapKey.remove(dbKeyword.getKeyword());
    	}
    	return mapKey.isEmpty() ? null : mapKey.values();
	}
}
