package womencard;

import impl.Da_gr_hxda;
import impl.Eb_bj_xsefs;
import impl.Eb_tre_glbza;
import impl.Fb_ycf_xsecsqk;
import impl.Fb_ycf_ykjc;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>
 * 标题: JmTest.java
 * </p>
 * <p>
 * 业务描述:测试类
 * </p>
 * <p>
 * 公司:东华软件股份公司
 * </p>
 * <p>
 * 版权:dhcc2013
 * </p>
 * 
 * @author 金铭
 * @date 2014年4月24日
 * @version V1.0
 */
public class JmTest {
	public static void main(String[] args) {
		String startTime = "2012-03-29 00:00:00.000";
		String endTime = "2012-04-10 00:00:00.000";

		// Da_gr_hxda
		Da_gr_hxda da_gr_hxda = new Da_gr_hxda();
		Map<String, String> map = da_gr_hxda.createxml(startTime, endTime);
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}

		// Eb_bj_xsefs
		Eb_bj_xsefs eb_bj_xsefs = new Eb_bj_xsefs();
		Map<String, String> map2 = eb_bj_xsefs.createxml(startTime, endTime);
		Iterator<Entry<String, String>> it2 = map2.entrySet().iterator();
		while (it2.hasNext()) {
			Entry<String, String> entry = it2.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}

		// Eb_tre_glbza
		Eb_tre_glbza eb_tre_glbza = new Eb_tre_glbza();
		Map<String, String> map3 = eb_tre_glbza.createxml(startTime, endTime);
		Iterator<Entry<String, String>> it3 = map3.entrySet().iterator();
		while (it3.hasNext()) {
			Entry<String, String> entry = it3.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}

		// Fb_ycf_xsecsqk
		Fb_ycf_xsecsqk fb_ycf_xsecsqk = new Fb_ycf_xsecsqk();
		Map<String, String> map4 = fb_ycf_xsecsqk.createxml(startTime, endTime);
		Iterator<Entry<String, String>> it4 = map4.entrySet().iterator();
		while (it4.hasNext()) {
			Entry<String, String> entry = it4.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}

		// Fb_ycf_ykjc
		Fb_ycf_ykjc fb_ycf_ykjc = new Fb_ycf_ykjc();
		Map<String, String> map5 = fb_ycf_ykjc.createxml(startTime, endTime);
		Iterator<Entry<String, String>> it5 = map5.entrySet().iterator();
		while (it5.hasNext()) {
			Entry<String, String> entry = it5.next();
			Object key = entry.getKey();
			Object value = entry.getValue();
			System.out.println("key=" + key + " value=" + value);
		}
	}
}
