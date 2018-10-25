package test;


import com.iflytek.itm.api.ITM;
import com.iflytek.itm.api.ITMFactory;

public class WordAssociation {
	
	 // main
    public static void main(String[] args)
    {
        System.out.println("测试mining接口");

        WordAssociation inst = new WordAssociation();
        // mining
        for (int i = 0; i < 1; ++i)
        {
        	 inst.doMining();
        }
    }
    // mining
    public void doMining()
    {
        // 1.词语联想
        doWordAssociation();
        
    }
    
    public void doWordAssociation()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\itm";
        String subDir = "dim";
        ITM inst = ITMFactory.create();
        StringBuffer buffer = new StringBuffer();
        StringBuffer tmpIdList = new StringBuffer(1024);
        for (int i = 0; i < 100; ++i)
        {
            tmpIdList.append(subDir+":"+i+";");
        }
        int ret = inst.mining(indexPath, "word_association",
            "sub_index_dir_list=dim \n" +
                "word_association_word=三十元 \n" +
                "sample_rate=500 \n" +
                "word_association_level_top_n=2:10;5 \n" +
                "mining_field=content",
            buffer);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }
        // 显示文本分析的结果
        System.out.println("mining result="+buffer.toString());
        String allContent = buffer.toString();
   	 	String[] bufferString = allContent.split(";");
   	 	int cont = bufferString.length;
   	 	if(cont > 1){
		for(int i=0;i<cont;i++)
			{
			String strTemp = bufferString[i];
			//得到本体
         	
         	
         	System.out.println(strTemp);
			}
   	 	
   	 	
	 }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }
	

}
