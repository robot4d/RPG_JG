package jg.rpg.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class DBHelper {
	
	public static <T> T GetFirst(DataSource ds , String sql , ResultSetHandler<T> rsh ,Object... params) throws SQLException{
		QueryRunner qr = new QueryRunner(ds);
		return qr.query(sql, rsh,params);
	}

	public static <T> List<T> GetAll(DataSource ds , String sql , final ResultSetHandler<T> rsh ,  Object... params) throws SQLException{
		
		ResultSetHandler<List<T>> listRS = new ResultSetHandler<List<T>>(){
			@Override
			public List<T> handle(ResultSet rs) throws SQLException {
				 List<T> rses = null;
				 T t = null;
				 while(( t = rsh.handle(rs)) != null){
					 if( rses == null ){
							 rses = new ArrayList<T>();
					 }
					 rses.add(t);
				 }
				return rses;
			}
		};
		
		QueryRunner qr = new QueryRunner(ds);
		List<T> rst = null;
		rst = qr.query(sql, listRS, params);
		return rst;
	}
	
	public  static <T> T insert(DataSource ds , String sql , ResultSetHandler<T> rsh ,Object... params) throws SQLException{
		QueryRunner qr = new QueryRunner(ds);
		return qr.insert(sql, rsh,params);
	}
	
	public static int update(DataSource ds , String sql ,Object... params) throws SQLException{
		QueryRunner qr = new QueryRunner(ds);
		return qr.update(sql, params);
	}
}
