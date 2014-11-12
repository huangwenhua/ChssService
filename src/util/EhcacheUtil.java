package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;

import coon.JdbcPool;

/**
 * <p>标题: EhcacheUtil.java</p>
 * <p>业务描述:ehcache缓存工具类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author <a href ='zhangfei_xa@dhcc.com.cn'>张飞</a>
 * @date 2014年4月24日
 * @version V1.0 
 */
public class EhcacheUtil {
	
	private static Logger logger = Logger.getLogger(EhcacheUtil.class);
	
	/**
	 * 缓存中存放的最多元素个数
	 */
	private static final int MAXITEMS = 100000;
	/**
	 * 如果内存中数据超过内存限制，是否要缓存到磁盘上
	 */
	private static final boolean OVERFLOWTODISK = true;
	/**
	 * 设定缓存的elements是否永远不过期
	 */
	private static final boolean ETERNAL = true;

	private static EhcacheUtil ehcache;
	
	private static CacheManager manager = CacheManager.create();
	
	private EhcacheUtil() {

	}

	public static EhcacheUtil getInstance() {
		if (ehcache == null) {
			ehcache = new EhcacheUtil();
		}
		return ehcache;
	}
	
	public void initCache() {
		initWPH_DZXX();
		initSYS_ZCJG();
		initSYS_CZRY();
		initCSZM();
	}
	
	// 缓存表WPH_DZXX
	private Cache initWPH_DZXX() {
		Cache cache = new Cache("WPH_DZXX", MAXITEMS, OVERFLOWTODISK, ETERNAL,
				0, 0);
		manager.addCache(cache);
		Element element = null;
		Connection con = JdbcPool.getConnection();
		String sql = "select ZXBM,DZBM from WPH_DZXX where DZBM is not null";
		PreparedStatement pspm;
		try {
			pspm = con.prepareStatement(sql);
			ResultSet rs = pspm.executeQuery();
			while (rs.next()) {
				element = new Element(rs.getString("DZBM"),
						rs.getString("ZXBM"));
				cache.put(element);
			}
			rs.close();
			pspm.close();
			con.close();
			
		} catch (SQLException e) {
			logger.error("query or close error :" + e.getMessage());
			e.printStackTrace();
		}
		return cache;
	}
	
	// 缓存表SYS_ZCJG
	private Cache initSYS_ZCJG() {
		Cache cache = new Cache("SYS_ZCJG", MAXITEMS, OVERFLOWTODISK, ETERNAL,
				0, 0);
		manager.addCache(cache);
		Element element = null;
		Connection con = JdbcPool.getConnection();
		String sql = "select ZXBM,JGBM from SYS_ZCJG where JGBM is not null";
		PreparedStatement pspm;
		try {
			pspm = con.prepareStatement(sql);
			ResultSet rs = pspm.executeQuery();
			while (rs.next()) {
				element = new Element(rs.getString("JGBM"),
						rs.getString("ZXBM"));
				cache.put(element);
			}
			rs.close();
			pspm.close();
			con.close();
		} catch (SQLException e) {
			logger.error("query or close error :" + e.getMessage());
			e.printStackTrace();
		}
		return cache;
	}
	
	// 缓存表SYS_CZRY
	private Cache initSYS_CZRY() {
		Cache cache = new Cache("SYS_CZRY", MAXITEMS, OVERFLOWTODISK, ETERNAL,
				0, 0);
		manager.addCache(cache);
		Element element = null;
		Connection con = JdbcPool.getConnection();
		String sql = "select ZXBM,RYBM from SYS_CZRY where RYBM is not null";
		PreparedStatement pspm;
		try {
			pspm = con.prepareStatement(sql);
			ResultSet rs = pspm.executeQuery();
			while (rs.next()) {
				element = new Element(rs.getString("RYBM"),
						rs.getString("ZXBM"));
				cache.put(element);
			}
			rs.close();
			pspm.close();
			con.close();
		} catch (SQLException e) {
			logger.error("query or close error :" + e.getMessage());
			e.printStackTrace();
		}
		return cache;
	}
	
	// 缓存表cszm
	private Cache initCSZM() {
		Cache cache = new Cache("T_CSZM_ZDBA", MAXITEMS, OVERFLOWTODISK, ETERNAL,
				0, 0);
		manager.addCache(cache);
		Element element = null;
		Connection con = JdbcPool.getConnection();
		String sql = "select lzcode,jtcode from T_CSZM_ZDBA";
		PreparedStatement pspm;
		try {
			pspm = con.prepareStatement(sql);
			ResultSet rs = pspm.executeQuery();
			while (rs.next()) {
				element = new Element(rs.getString("lzcode"),
						rs.getString("jtcode"));
				cache.put(element);
			}
			rs.close();
			pspm.close();
			con.close();
		} catch (SQLException e) {
			logger.error("query or close error :" + e.getMessage());
			e.printStackTrace();
		}
		return cache;
	}
	
	/**
	* 方法名:  findValue
	* 方法功能描述: 找到映射的值
	* @param tbName 表名
	* @param code 代码
	* @return String 映射的值
	* @Author:  张飞
	* @Create Date:  2014年4月24日 下午4:56:15
	 */
	public static String findValue(String tbName, String code) {
		if (null == code || "".equals(code))
			return "";
		Cache cache = manager.getCache(tbName);
		Element temp = cache.get(code);
		if (null != temp && null != temp.getObjectValue()) {
			return cache.get(code).getObjectValue().toString();
		}
		return "";
	}
	
	public static void main(String args[]) {
		EhcacheUtil.getInstance().initCache();
	}
}
