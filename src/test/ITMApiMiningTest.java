/** 
 * @file    ITMApiSearchTest.java
 * @brief  
 *
 *  测试ITM的mining接口
 *
 * @author	wuqiu
 * @version	1.0
 * @date	2012年12月20日-下午4:25
 *
 * @see		
 *
 * @par 版本记录：
 * <table border=1>
 *  <tr> <th> 版本	<th>日期			<th>作者    	<th>备注 </tr>
 *  <tr> <td> 1.0	<td>12-12-20	    <td>wuqiu  <td>创建 </tr>
 * </table>
 */
package test;
import com.iflytek.itm.api.ITM;
import com.iflytek.itm.api.ITMFactory;

public class ITMApiMiningTest
{
    // main
    public static void main(String[] args)
    {
        System.out.println("测试mining接口");

        ITMApiMiningTest inst = new ITMApiMiningTest();
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
        // 2.热点分析
    	//doHotView();
        // 3.规则分析
        //doRule();
        // 4.趋势分析
        //doTrade();
        // 5. 做推送
        //doAssist();
    }

    // word association, 词语联想
    public void doWordAssociation()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\itm";
        String subDir = "10w";
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

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // rule 规则分析
    public void doRule()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\itm";
        String subDir = "1w";
        ITM inst = ITMFactory.create();
        StringBuffer buffer = new StringBuffer();
        StringBuffer tmpIdList = new StringBuffer(1024);
        for (int i = 0; i < 100; ++i)
        {
            tmpIdList.append(subDir+":"+i+";");
        }
        int ret = inst.mining(indexPath, "rule",
            "sub_index_dir_list=dim \n" +
                "id_field=id \n" +
                // (来电|感谢)#(来电|感谢|你好)#你好
                // (来电|感谢)#(来电|感谢|你好)
            //"rule=1:((来电|感谢)+(sil>3)+我是+(sil<9)+中国) \n" +
                // (感谢#来电)&(来电+我是)
                // todo：先支持+号，后要vie配合支持sil5，再支持#前后是query
                "rule=1:(来电|感谢) \n" +
                "mining_field=content ",
            buffer);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }
        // 显示文本分析的结果
        System.out.println("mining result="+buffer.toString());

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // hot_view 热点分析
    public void doHotView()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\201801";
        String subDir = "test";
        ITM inst = ITMFactory.create();
        StringBuffer buffer = new StringBuffer();
        StringBuffer tmpIdList = new StringBuffer(1024);
        for (int i = 101; i < 501; ++i)
        {
            tmpIdList.append(subDir+":"+i+";");
        }
   
        int ret = inst.mining(indexPath,"hot_view",
                "sub_index_dir_list=test\n"+
               
                    "id_list="+tmpIdList.toString()+" \n" +    
                 "id_field=id \n" +
                //    "rule=1:(两城#一家) \n" +
                	"sample_rate=400 \n" +
                    "hot_view_mode=1 \n" +
                    "hot_view_feature_words_top_n=50 \n" +
                    "hot_view_word_top_n=15 \n" +
                "mining_field=content",
            buffer);
     System.out.println(ret);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }
        // 显示文本分析的结果
        System.out.println("mining result="+buffer.toString());

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // trade 趋势分析
    public void doTrade()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\201801";
        String subDir = "test";
        ITM inst = ITMFactory.create();
        StringBuffer buffer = new StringBuffer();
        String params = "sub_index_dir_list="+subDir+" \n" +
                "trade_top_n=10 \n" +
                "sample_rate=500 \n" +
                "trade_result_type=tf-idf \n" +
                "mining_field=content";
        int ret = inst.mining(indexPath,"trade",params, buffer);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }
        // 显示文本分析的结果
        System.out.println("mining result="+buffer.toString());

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // assist 坐席辅助，推送
    public void doAssist()
    {
        long start = System.currentTimeMillis();
        // mining
        String indexPath = "e:\\test_home\\index\\itm";
        ITM inst = ITMFactory.create();
        StringBuffer buffer = new StringBuffer();
        String params = "sub_index_dir_list=dim \n" +
            "assist_top_n=10 \n" +
            "assist_text=好像有一个叫商旅一百多五十八的套餐吧 \n";
        int ret = inst.mining(indexPath, "assist", params, buffer);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }
        // 显示文本分析的结果
        System.out.println("mining result="+buffer.toString());

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }
} // class ITMApiMiningTest end
