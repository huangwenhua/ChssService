package impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import util.DateUtil;
import Interface.BuildingXml;
import coon.JdbcPool;
import dictall.DictMap;

/**
 * <p>标题: Eb_bj_zsjkxj.java</p>
 * <p>业务描述:周岁健康小结 生成xml</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2014</p>
 * @author 王军
 * @date 2014年4月17日
 * @version V1.0 
 */

public class Eb_bj_zsjkxj implements BuildingXml{
	private static Log logger = LogFactory.getLog(Eb_bj_zsjkxj.class);

	@Override
	public Map<String, String> createxml(String startTime,String endTime) {
		Connection con = JdbcPool.getConnection();
		String sql = "select  GRBJH,PGRQ,SZNL,TJYGY,TJMFYF,TJYRYF,TJSCYF,TJDYF,DMRYF,PJMTSMSJ,SMCN,SMDH,HWHD,FXCS,FYCS,SGCS,GLB,PX,BJDFSQTJB,JBMC,JX,TZ,ZZ,SG,ZG,TW,TWZJ,QX1,QX2,YCS,YYZK,GRSH,JXDZ,YY,DDZ,ZDYJDM,ZDYJMC,PJYSBM,PJYS,JLZT,DJRQ,DJRY,DJRYBM,DJJGDM,DJJGMC,XGRQ,XGRY,XGRYBM,XGJGDM,XGJGMC from EB_BJ_ZSJKXJ  where XGRQ>? and XGRQ<=?";
		PreparedStatement pspm = null;
		ResultSet rs = null;
		Map<String, String> xmlMap = new HashMap<String, String>();
		String xml = null;
		try {
			pspm = con.prepareStatement(sql);
			pspm.setString(1, startTime);
			pspm.setString(2, endTime);
			rs = pspm.executeQuery();
			while (rs.next()) {
				// 个人保健号
				String GRBJH = rs.getString("GRBJH");
				//评估日期
				String PGRQ = rs.getString("PGRQ");
				PGRQ = convertDateToD8(PGRQ);
				//添加鱼肝油
				String TJYGY	 = rs.getString("TJYGY");
				//添加米粉月份
				String TJMFYF = rs.getString("TJMFYF");
				//添加鱼肉月份
				String TJYRYF = rs.getString("TJYRYF");
				//添加蔬菜月份
				String TJSCYF = rs.getString("TJSCYF");
				//添加蛋月份
				String TJDYF = rs.getString("TJDYF");
				//断母乳月份
				String DMRYF = rs.getString("DMRYF");
				//平均每天睡眠时间(小时)
				String PJMTSMSJ = rs.getString("PJMTSMSJ");
				//睡眠吵闹或夜惊
				String SMCN = rs.getString("SMCN");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				SMCN = getSleep(SMCN);
						
				//睡眠多汗
				String SMDH= rs.getString("SMDH");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				SMDH = getSleep(SMDH);
				
				
				//户外活动
				String HWHD = rs.getString("HWHD");
				//本阶段腹泻次数
				String FXCS = rs.getString("FXCS");
				//本阶段肺炎次数
				String FYCS = rs.getString("FYCS");
				//本阶段伤感次数
				String SGCS = rs.getString("SGCS");
				//佝偻病
				String GLB = rs.getString("GLB");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				GLB = getSleep(GLB);
				
				//本阶段发生疾病说明
				String BJDFSQTJB = rs.getString("BJDFSQTJB");
				//畸形
				String JX = rs.getString("JX");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				JX = getSleep(JX);
				
				//增重(kg)
				String ZZ = rs.getString("ZZ");
				//体重(kg)
				String TZ = rs.getString("TZ");
				//身高(cm)
				String SG = rs.getString("SG");
				//增高(cm)
				String ZG = rs.getString("ZG");
				//头围(cm)
				String TW = rs.getString("TW");
				//头围增加
				String TWZJ = rs.getString("TWZJ");
				//前囟1
				String QX1 = rs.getString("QX1");
				//前囟2
				String QX2 = rs.getString("QX2");
				//牙齿数
				String YCS = rs.getString("YCS");
				//营养状况
				String YYZK = rs.getString("YYZK");
				//DDST筛查(个人社会)
				String GRSH = rs.getString("GRSH");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				GRSH = getDdst(GRSH);
				
				//DDST筛查(精细动作)
				String JXDZ = rs.getString("JXDZ");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				JXDZ = getDdst(JXDZ);
				
				//DDST筛查(语言)
				String YY = rs.getString("YY");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				YY = getDdst(YY);
				
				//DDST筛查(大动作)
				String DDZ = rs.getString("DDZ");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				DDZ = getDdst(DDZ);
				
				//指导意见代码
				String ZDYJDM = rs.getString("ZDYJDM");
				//评价医师编码
				String PJYSBM = rs.getString("PJYSBM");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				PJYSBM = DictMap.getCzry(PJYSBM);
				
				//登记人员编码
				String DJRYBM = rs.getString("DJRYBM");
				//
				String PX = rs.getString("PX");
				
				/**
				 * 2014-4-29    未做字典处理                   李研edit
				 */
				PX = getSleep(PX);
				
				//修改机构名称
				String XGJGMC = rs.getString("XGJGMC");
				
				
				Document document = DocumentHelper.createDocument();	
				Element root = document.addElement("body"); 
				
				Element head = root.addElement("head"); 
				Element userid = head.addElement("userid");
				userid.addText("nb_fybj");
				Element password = head.addElement("password");
				password.addText("123");
				Element trans_no = head.addElement("trans_no");
				trans_no.addText("B04.01.01.05");
				
				Element request = root.addElement("resquest");//error   2014-4-28 李研改上
				Element trans_noChild = request.addElement("B04.01.01.05");//error   2014-4-28 李研改
				Element eRPTNO = trans_noChild.addElement("RPTNO");
				eRPTNO.setText( getIsNull(GRBJH));
				Element eHEALTH_ID  = trans_noChild.addElement("HEALTH_ID");
				eHEALTH_ID.setText("");
				Element EVAL_DATE  = trans_noChild.addElement("EVAL_DATE");
				EVAL_DATE.setText( getIsNull(PGRQ));
				Element LIVER_OIL  = trans_noChild.addElement("LIVER_OIL");
				LIVER_OIL.setText( getIsNull(TJYGY));
				Element RICE_NOODLES  = trans_noChild.addElement("RICE_NOODLES");
				RICE_NOODLES.setText( getIsNull(TJMFYF));
				Element FISH  = trans_noChild.addElement("FISH");
				FISH.setText( getIsNull(TJYRYF));
				Element VEGETABLES  = trans_noChild.addElement("VEGETABLES");
				VEGETABLES.setText( getIsNull(TJSCYF));
				Element EGG  = trans_noChild.addElement("EGG");
				EGG.setText( getIsNull(TJDYF));
				Element BREAST  = trans_noChild.addElement("BREAST");
				BREAST.setText( getIsNull(DMRYF));
				
				Element AVERAGE_SLEEP  = trans_noChild.addElement("AVERAGE_SLEEP");
				AVERAGE_SLEEP.setText( getIsNull(PJMTSMSJ));
				
				Element SLEEP_NOISY   = trans_noChild.addElement("SLEEP_NOISY");
				SLEEP_NOISY.setText( getIsNull(SMCN) );
				Element SLEEP_HYPERHIDROSIS = trans_noChild.addElement("SLEEP_HYPERHIDROSIS");
				SLEEP_HYPERHIDROSIS.setText( getIsNull(SMDH));
				Element OUTDOOR_ACTIVITIES   = trans_noChild.addElement("OUTDOOR_ACTIVITIES");
				OUTDOOR_ACTIVITIES.setText( getIsNull(HWHD));
				Element DIARRHEA_TIMES  = trans_noChild.addElement("DIARRHEA_TIMES");
				DIARRHEA_TIMES.setText( getIsNull(FXCS));
				Element PNEUMONIA_TIMES  = trans_noChild.addElement("PNEUMONIA_TIMES");
				PNEUMONIA_TIMES.setText( getIsNull(FYCS));
				Element COLDS_TIMES   = trans_noChild.addElement("COLDS_TIMES");
				COLDS_TIMES.setText( getIsNull(SGCS));
				
				Element RICKETS   = trans_noChild.addElement("RICKETS");
				RICKETS.setText( getIsNull(GLB));
				Element ANEMIA = trans_noChild.addElement("ANEMIA");
				ANEMIA.setText( getIsNull(PX));
				Element DISEASE_OCCURS_OTHER  = trans_noChild.addElement("DISEASE_OCCURS_OTHER");
				DISEASE_OCCURS_OTHER.setText( getIsNull(BJDFSQTJB));
				Element DEFORMITY  = trans_noChild.addElement("DEFORMITY");
				DEFORMITY.setText( getIsNull(JX));
				Element WEIGHT   = trans_noChild.addElement("WEIGHT");
				WEIGHT.setText( getIsNull(TZ));

				Element ADD_WEIGHT   = trans_noChild.addElement("ADD_WEIGHT");
				ADD_WEIGHT.setText( getIsNull(ZZ));
				Element HEIGHT = trans_noChild.addElement("HEIGHT");
				HEIGHT.setText( getIsNull(SG));
				Element ADD_HEIGHT   = trans_noChild.addElement("ADD_HEIGHT");
				ADD_HEIGHT.setText( getIsNull(ZG));
				Element HC  = trans_noChild.addElement("HC");
				HC.setText( getIsNull(TW));
				Element ADD_HC  = trans_noChild.addElement("ADD_HC");
				ADD_HC.setText( getIsNull(TWZJ));
				Element BREGMA1   = trans_noChild.addElement("BREGMA1");
				BREGMA1.setText( getIsNull(QX1));
				Element eBREGMA2   = trans_noChild.addElement("BREGMA2");
				eBREGMA2.setText( getIsNull(QX2));

				Element TEETH   = trans_noChild.addElement("TEETH");
				TEETH.setText( getIsNull(YCS));
				Element NUTRITION = trans_noChild.addElement("NUTRITION");
				NUTRITION.setText("");
				Element DDST_SOCIAL = trans_noChild.addElement("DDST_SOCIAL");
				DDST_SOCIAL.setText( getIsNull(GRSH));
				Element DDST_FINE_MOTOR   = trans_noChild.addElement("DDST_FINE_MOTOR");
				DDST_FINE_MOTOR.setText( getIsNull(JXDZ));
				Element DDST_LANGUAGE  = trans_noChild.addElement("DDST_LANGUAGE");
				DDST_LANGUAGE.setText( getIsNull(YY));
				Element DDST_BIG_MOVEMENTS  = trans_noChild.addElement("DDST_BIG_MOVEMENTS");
				DDST_BIG_MOVEMENTS.setText( getIsNull(DDZ));
				Element GUIDANCE   = trans_noChild.addElement("GUIDANCE");
				GUIDANCE.setText( getIsNull(ZDYJDM));

				Element EVAL_EMP_ID  = trans_noChild.addElement("EVAL_EMP_ID");
				EVAL_EMP_ID.setText( getIsNull(PJYSBM));
				Element EVAL_ORG_CODE  = trans_noChild.addElement("EVAL_ORG_CODE");
				EVAL_ORG_CODE.setText(DictMap.getZcjg(rs.getString("DJJGDM")));
				Element CREATE_EMP_NAME   = trans_noChild.addElement("CREATE_EMP_NAME");
				CREATE_EMP_NAME.setText( getIsNull(DJRYBM));
				Element CREATE_ORG_NAME  = trans_noChild.addElement("CREATE_ORG_NAME");
				CREATE_ORG_NAME.setText( getIsNull(XGJGMC));
				
				Element eUPLOAD_TIME = trans_noChild.addElement("UPLOAD_TIME");
				eUPLOAD_TIME.setText(DateUtil.nowDT15());

				xml = document.getRootElement().asXML();
				xmlMap.put(GRBJH, xml);
			}
		}catch (SQLException e) {
			logger.error("fail to connect db");
		}finally{
			//2014-4-28   李研    添加链接关闭代码
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					pspm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				
			}
		}
		
		return xmlMap;
	}

	public static void main(String[] args) throws IOException {
		Eb_bj_zsjkxj childCare = new Eb_bj_zsjkxj();
		Map<String, String> result = childCare.createxml(
				"2012-04-27 00:00:00.000", "2012-04-29 00:00:00.000");
		@SuppressWarnings("rawtypes")
		Iterator it = result.entrySet().iterator();
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}

	}
	
	/**
	 * 
	* 方法名:          convertDateToD8
	* 方法功能描述:    将字符串日期转换为D8格式的日期
	* @author 李研
	 */
	public String convertDateToD8(String strDate){
		
		if(strDate == null || "".equals(strDate)){
			return "";
		}
		
		String newDate = "";
		try {
			
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
			newDate = new SimpleDateFormat("yyyyMMdd").format(date);
		} catch (ParseException e) {
			logger.error("-----日期转换出错-------");
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * 周岁健康小结     睡眠吵闹或夜惊    匹配
	 * 周岁健康小结     睡眠多汗    匹配
	 * 周岁健康小结     佝偻病    匹配
	 * 周岁健康小结     贫    血    匹配
	 * 周岁健康小结     本阶段发生疾病    匹配
	 * 周岁健康小结     畸    形    匹配
	 * @author 李研
	 * 业务表   1   有   2  无
	 * 中心表   0   无   1  有   9  不详
	 */
	public String getSleep(String sleep){

		if("2".equals(sleep)){
			sleep="0";
		}
		
		return sleep;
	}
	
	/**
	 * 周岁健康小结     DDST筛查(个人社会)   匹配
	 * 周岁健康小结     DST筛查(精细动作)  匹配
	 * 周岁健康小结   DDST筛查(语言)   匹配
	 * 周岁健康小结     DDST筛查(大动作)    匹配
	 * @author 李研
	 * 业务表   0   正常   1  异常           2其他
	 * 中心表   1  正常      2  异常   3  不详
	 */
	public String getDdst(String ddst){
		
		if("0".equals(ddst)){
			ddst="1";
		}
		if("1".equals(ddst)){
			ddst="2";
		}
		if("2".equals(ddst)){
			ddst="3";
		}
		
		return ddst;
	}
	
	public String getIsNull(String str){
		if(str == null){
			return "";
		}
		return str;
	}
}