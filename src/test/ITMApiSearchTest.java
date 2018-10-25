/** 
 * @file    ITMApiSearchTest.java
 * @brief  
 *
 *  测试ITM的接口
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

import com.iflytek.itm.api.*;

import java.util.List;

public class ITMApiSearchTest
{
    // main
    public static void main(String[] args)
    {
        System.out.println("测试功能");

        ITMApiSearchTest inst = new ITMApiSearchTest();

        for (int i = 0; i < 1; ++i)
        {
            // search
            inst.doSearch();
            // group
            inst.doGroup();
        }


        // listDocs
        inst.listDocs();
    }

    /* search
    一些要注意的检索语法，如果索引中有一万doc
    content:你好      --》 total hit docs=[3552]
    content:(你好)      --》 total hit docs=[3552]
    content:(+你好)      --》 total hit docs=[3552]  属于语法说明书上的“field grouping”
    content:+你好     --》语法错误，总结：如果在field后还有符号，则必须要group一下
    以上四个，在content前都可以加+号
    但是-号不能跟一个词, The NOT operator cannot used with just one term，这是没有结果的，如
    -content:你好             --> total hit docs=[0]
    *:* -content:你好         --> total hit docs=[6448]  在有“非”的时候，要用*:*来保证结果
    *
    * 邻近
    * +content:"上网 套餐"~10
    * 这个调用的是PhraseQuery，可以找到这个类，然后在里面的scorer打个断点，就会过来了
    * pos和offset的区别，可以把某个doc的content取到，然后用AnalyzeUtils来得到其pos和offset
    *
    * 正则检索
    * 这个时候要想知道语法该如何写，可以用query的toString来看看到底该如何写
    *
    * SpanNearQuery, PhraseQuery, RegexpQuery有什么区别
    * SpanNearQuery: 物理上的距离，人主观感觉的距离，是中间的字数比如“你好感谢来电”中“你好”和“来电”中间有2个字，距离为2
    * PhraseQuery：slop是编辑距离
    * RegexpQuery：是对term而言，即索引倒排中有“你好”和“来电”，但是并不能用正则来检索这个“你好 来电”，而只能检索“你.好”，词内，而不能跨词，所以正则一般要求不分词
    * 最后邻近用这种方式解决了，建索用单字，检索用特殊处理，类似：+_itm_rule_content:手机#5#业务
    *
    * 官方提示：在测试搜索的速率的时候，要忽略第一次检索的时间
    * 还有：分词器，IK分词器在第一次执行的时候要加载一个27w多的字典，你说耗时么，所以第一次的不可信
    *
    * 检索100w的效率：open占30ms，pageQuery占250ms
    *
    */
    public void doSearch()
    {
        // 1.怎么取数据？
        // 2.怎么分页？
        // 3.怎么筛选？按int范围，按前缀
        // 4.多目录检索
        // 5.在结果中搜索
        long start = System.currentTimeMillis();
        // search
        String indexPath = "E:\\test_home\\index\\201801";
        ITM inst = ITMFactory.create();
        ITMSearchContext searchContext = new ITMSearchContext();
        int ret = 0;
        //ret = inst.search(indexPath, "onebest:用不同的", "", "sub_index_dir_list=20121222", searchContext);
        ret = inst.search(indexPath, "+id:1", "sub_index_dir_list=test \n page_size=10 \n current_page=1", searchContext);
        //ret = inst.search(indexPath, "+satisfaction:未处理 +contact_time:[100 TO *] -kf_id:1888 +user_brand:神州行 +user_level:1 +region:07", "sub_index_dir_list=dim \n page_size=10 \n current_page=1", searchContext);
        //ret = inst.search(indexPath, "content:你好 AND CALLER_PHONE:135* AND ACCEPT_PHONE:135* AND REGION_CODE:1 AND USERBRAND:全球通 AND CALLTYPE:1 AND id:[1 TO 8000000] AND id_csv:[266 TO 90846] AND CALLTIME:[20 TO 300]", "sub_index_dir_list=200w \n page_size=10 \n current_page=1", searchContext);
        //ret = inst.search(indexPath, "+(*:* -content:手机) +content:上#网", "mining_field=content \n rule=1:(gprs#套餐) \n sub_index_dir_list=1w \n page_size=10 \n current_page=1", searchContext);
        //ret = inst.search(indexPath, "+silences:[0 TO *]", "sub_index_dir_list=test \n page_size=10 \n current_page=1", searchContext);
        //ret = inst.search(indexPath, "+_itm_rule_content:手机#5#业务", "sub_index_dir_list=1w \n  page_size=10 \n current_page=1", searchContext);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
            return;
        }
        // 显示检索的结果
        // 搜索结果总数量,不论search里面传入的是多少
        int totalHits = searchContext.itmSearchResult.getTotalHits();
        System.out.printf("total hit docs=[%d]\n", totalHits);
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
        ITMBuilder.ITMDocument[] docs = searchContext.itmSearchResult.docs();
        for(int i=0; i< docs.length; ++i)
        {
            System.out.printf("i=%d, ", i);
            ITMBuilder.ITMDocument doc = docs[i];
            List<ITMBuilder.ITMField> fields = doc.getFields();
            for (int j = 0; j < fields.size(); ++j)
            {
                ITMBuilder.ITMField field = fields.get(j);
                String value = doc.get(field.name).value;
                System.out.printf("%s=%s, ", field.name, value);
            }
            System.out.printf("\n");
        }
        searchContext.itmSearchResult.close();

        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start + " total milliseconds");
    }

    // group
    public void doGroup()
    {
        long start = System.currentTimeMillis();
        // search
        String indexPath = "e:\\test_home\\index\\itm";
        ITM inst = ITMFactory.create();
        ITMSearchContext searchContext = new ITMSearchContext();
        int ret = 0;
        ret = inst.search(indexPath, "", "sub_index_dir_list=dim \n group_field=id \n page_size=10 \n current_page=1", searchContext);

        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
            return;
        }
        // 显示检索的结果
        // 搜索结果总数量,不论search里面传入的是多少
        int totalHits = searchContext.itmSearchResult.getGroupTotalHits();
        System.out.printf("total hit docs=[%d], total hit groups=[%d]\n", searchContext.itmSearchResult.getTotalHits(), totalHits);
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
        ITMSearchContext.ITMGroup[] groups = searchContext.itmSearchResult.groups();
        for(int i=0; i< groups.length; ++i)
        {
            System.out.printf("i=%d, ", i);
            ITMSearchContext.ITMGroup group = groups[i];
            System.out.println("group value="+group.value+", group doc cnt="+group.docCount);
        }
        searchContext.itmSearchResult.close();

        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start + " total milliseconds");
    }

    // listDocs
    public void listDocs()
    {
        long start = System.currentTimeMillis();
        // search
        String indexPath = "e:\\test_home\\index\\itm";
        ITM inst = ITMFactory.create();
        ITMSearchContext searchContext = new ITMSearchContext();
        int ret = 0;
        ret = inst.search(indexPath, "*:*", "sub_index_dir_list=dim", searchContext);
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
            return;
        }
        // 显示检索的结果
        // 搜索结果总数量,不论search里面传入的是多少
        int totalHits = searchContext.itmSearchResult.getTotalHits();
        System.out.printf("total hit docs=[%d]\n", totalHits);
        ITMBuilder.ITMDocument[] docs = searchContext.itmSearchResult.docs();
        for(int i=0; i< docs.length; ++i)
        {
            System.out.printf("i=%d, ", i);
            ITMBuilder.ITMDocument doc = docs[i];
            List<ITMBuilder.ITMField> fields = doc.getFields();
            for (int j = 0; j < fields.size(); ++j)
            {
                ITMBuilder.ITMField field = fields.get(j);
                String value = doc.get(field.name).value;
                System.out.printf("%s=%s, ", field.name, value);
            }
            System.out.printf("\n");
        }
        searchContext.itmSearchResult.close();

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

} // class ItmUtils end
