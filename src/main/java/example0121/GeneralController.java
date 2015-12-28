package example0121;

import com.spring.event.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import org.iq80.leveldb.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
//import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class GeneralController extends AbstractController {
  
  
	@Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    //��ȡ������Ϣ�͵�½ʱ��
    String username = request.getParameter("username");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	String time=df.format(new Date());
	int count=0;
    DB db=Listener.db;
    Charset charset = Charset.forName("utf-8");
	
	    //������Ӧ�Ĳ�����ͨ��ModelAndView����
	Map<String ,String> model=new HashMap<String,String>();
		
	
    //write
    if(username !=null && !username.equals("")){
    	db.put(username.getBytes(charset),time.getBytes(charset));
    }
    else{
		//�������Ϊ�յĻ����׳�һ������
    	model.put("error", "Try again and input something!");
	 	return new ModelAndView("index",model);
    }


    //��ȡ��ǰsnapshot�����գ���ȡ�ڼ����ݵı�������ᷴӦ����
    Snapshot snapshot = db.getSnapshot();
    //��ѡ��
    ReadOptions readOptions = new ReadOptions();
    readOptions.fillCache(false);//������swap���������ݣ���Ӧ�ñ�����memtable�С�
    readOptions.snapshot(snapshot);//Ĭ��snapshotΪ��ǰ��
    DBIterator iterator = db.iterator(readOptions);
    while (iterator.hasNext()) {
        Map.Entry<byte[],byte[]> item = iterator.next();
        String key = new String(item.getKey(),charset);
        String value = new String(item.getValue(),charset);//null,check.
      	model.put(key, value);
        count++;
    }
    //System.out.println(count);
    iterator.close();
    
    List <Map.Entry<String,String>> infoIds=new ArrayList<Map.Entry<String,String>>(model.entrySet());
    Collections.sort(infoIds,new Comparator<Map.Entry<String,String>>(){
    	public int compare(Map.Entry<String, String> o1,Map.Entry<String,String> o2){
    		 return (o1.getValue()).toString().compareTo(o2.getValue());

    	}
    });
  //sort by time,put inf into model2
    Map<String ,String> model2=new LinkedHashMap<String,String>();
    for (int i = 0; i < infoIds.size()-1; i++) {
        model2. put( infoIds.get(i).getKey(),infoIds.get(i).getValue());
    //   System.out.println(infoIds.get(i).getKey()+":"+infoIds.get(i).getValue());
   }
	ModelAndView mav=new ModelAndView();
	mav.addObject("model2",model2);
	mav.addObject("username",username);
	mav.addObject("count",count);
	mav.setViewName("userInf");

    return mav;
	
  
	}
		
  }	

