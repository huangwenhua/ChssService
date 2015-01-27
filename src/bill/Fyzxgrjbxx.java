package bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import sys.CommonPara;
import coon.JdbcPool;
import Interface.Bill;

public class Fyzxgrjbxx implements Bill{
	
	/**
	 * 	7.7	妇幼专项个人基本信息业务数据对账接口 
	 *  7.7.2	请求交易号：Z00.00.00.07
		7.7.3	请求消息
		标准标识符	数据元名称	类型	格式	必填	允许值及综合考虑
		ORG_CODE	医疗机构代码	S3	AN..10	P	医疗机构代码基础字典库 NBD02.00.002
		SMONTHDAY	对账日期	D	D8	P	
		MCHS_COUNT	妇幼专项个人基本信息记录数	N	N..10	Y	查询时间内客户端妇幼专项个人基本信息记录数
		根据业务接口B04.00.00.00中的SETUP_TIME字段
	 */

	private String tbName;
	
	public Fyzxgrjbxx(){
		tbName = "DA_GR_HXDA";
	}
	
	@Override
	public String createxml(String upload_time) {
		Connection con = JdbcPool.getConnection();
		String succ = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='" + tbName + "'" ;
		int  succret = 0;
		String error = "SELECT count from T_ERROR WHERE date='" + upload_time + "' AND tbName='" + tbName + "'" ;
		int errorret = 0;
		int count = 0;
		
		String succCszm = "SELECT count from T_SUCC WHERE date='" + upload_time + "' AND tbName='T_CSZM'" ;
		int countCszm = 0;
		
		PreparedStatement pspm = null;
		ResultSet rs = null;
		String xml = null;
		try {
			pspm = con.prepareStatement(succ);
			rs = pspm.executeQuery();
			while (rs.next()) {
				succret = rs.getInt("count");
				succret = succret + 1;
			}
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			
			pspm = con.prepareStatement(error);
			rs = pspm.executeQuery();
			while(rs.next()){
				errorret = rs.getInt("count");
			}
			
//			count = succret + errorret;
			count = succret;
			
			pspm = con.prepareStatement(succCszm);
			rs = pspm.executeQuery();
			while (rs.next()) {
				countCszm = rs.getInt("count");
				countCszm = countCszm + 1;
			}
			countCszm = countCszm;
		} catch (SQLException e) {
			logger.error("fail to connect db：" + e.getMessage());
		}finally{
			try {
				if (rs != null) {
					rs.close();
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (pspm != null) {
						pspm.close();
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (con != null) {
							con.close();
						}
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

				}

			}		
		}
		String MCHS_COUNT = String.valueOf(count);
		String BIRTH_COUNT= String.valueOf(countCszm);
		
		Document document = DocumentHelper.createDocument();	
		Element root = document.addElement("body"); 
		
		Element head = root.addElement("head"); 
		Element userid = head.addElement("userid");
		userid.addText("nb_fybj");
		Element password = head.addElement("password");
		password.addText("123");
		Element trans_no = head.addElement("trans_no");
		trans_no.addText("Z00.00.00.07");
		
		Element request = root.addElement("resquest");
//		Element trans_noChild = request.addElement("Z00.00.00.07");
		Element eORG_CODE = request.addElement("ORG_CODE");
		eORG_CODE.setText(CommonPara.ORG_CODE); 
		Element eSMONTHDAY = request.addElement("SMONTHDAY");
		eSMONTHDAY.setText(upload_time);
		Element eMCHS_COUNT = request.addElement("MCHS_COUNT");
		eMCHS_COUNT.setText(MCHS_COUNT);
		Element eBIRTH_COUNT = request.addElement("BIRTH_COUNT");
		eBIRTH_COUNT.setText(BIRTH_COUNT);
		
		xml = document.getRootElement().asXML();
		return xml;
	}
	
	private static Log logger = LogFactory.getLog(Fyzxgrjbxx.class);

}
