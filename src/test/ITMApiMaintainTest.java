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

public class ITMApiMaintainTest
{
    // main
    public static void main(String[] args)
    {
        System.out.println("测试功能");

        ITMApiMaintainTest inst = new ITMApiMaintainTest();
        // maintain
        inst.doMaintain();
    }

    // maintain
    public void doMaintain()
    {
        // 1.删除子目录
        long start = System.currentTimeMillis();
        // maintain
        String indexPath = "e:\\test_home\\index\\itm";
        ITM inst = ITMFactory.create();
        int ret = inst.maintain(indexPath, "delete", "sub_index_dir=test");
        if (ret != 0)
        {
            System.out.println("Error: errcode="+ret);
        }

        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

} // class ItmUtils end
