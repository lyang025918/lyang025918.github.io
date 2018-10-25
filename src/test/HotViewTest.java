package test;

import com.iflytek.itm.api.ITM;
import com.iflytek.itm.api.ITMFactory;

public class HotViewTest {
	
	 // main
    public static void main(String[] args)
    {
        System.out.println("测试mining接口");

        HotViewTest inst = new HotViewTest();
        // mining
        for (int i = 0; i < 1; ++i)
        {
            inst.doHotView();
        }
    }
	// hot_view 热点分析
    public void doHotView()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\201801";
        String subDir = "test;dim;dim1";
        ITM inst = ITMFactory.create();
        StringBuffer buffer = new StringBuffer();
        StringBuffer tmpIdList = new StringBuffer(1024);
      for(int i=10001;i<10501;i++){
    	  tmpIdList.append(subDir+":"+i+";");
      }
   String params="sub_index_dir_list="+subDir+" \n"+
               
                    "id_list="+tmpIdList.toString()+"\n" +    
                 "id_field=id \n" +
                  //  "rule=1:(两城#一家) \n" +
                //"rule=1:(流量|套餐) \n" +
                	"sample_rate=500 \n" +
                //   "hot_view_mode=1 \n" +
                    "hot_view_feature_words_top_n=5 \n" +
                    "hot_view_word_top_n=5 \n" +
                "mining_field=content";
        int ret = inst.mining(indexPath,"trade",params,buffer);
   
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }
        // 显示文本分析的结果
        System.out.println("mining result="+buffer.toString());
        
        System.out.println("length="+buffer.length());
        String allContent = buffer.toString();
   	 	String[] bufferString = allContent.split(";");
   	 	int cont = bufferString.length;
   	 	if(cont > 1){
		for(int i=0;i<cont;i++)
			{
			String strTemp = bufferString[i];
			//得到本体
         	
         	String bufferStr=strTemp.replace( "(",":" );
         	String bufferStr1=bufferStr.replace( ")","" );
         	String all = bufferStr1.toString();
       	 	String[] bufferAll = all.split(":");
       	 	//System.out.println("111"+bufferAll);
       	 	int cont1 = bufferAll.length;
    	 	if(cont1 > 1){
    	 		for(int j=0;j<cont1;j++){
 			String str1 = bufferAll[j];
 			
 			System.out.println(str1);
    	 		}
    	 	}
         	System.out.println(bufferStr1);
			}
   	 	
   	 	
	 }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

}
