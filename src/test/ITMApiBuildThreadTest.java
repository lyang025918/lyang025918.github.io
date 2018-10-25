/** 
 * @file    ITMApiBuildThreadTest.java
 * @brief  
 *
 *  多线程的测试工具
 *
 * @author	wuqiu
 * @version	1.0
 * @date	2013年04月08日-下午7:59
 *
 * @see		
 *
 * @par 版本记录：
 * <table border=1>
 *  <tr> <th> 版本	<th>日期			<th>作者    	<th>备注 </tr>
 *  <tr> <td> 1.0	<td>13-4-8	    <td>wuqiu  <td>创建 </tr>
 * </table>
 */
package test;

public class ITMApiBuildThreadTest extends Thread
{
    private String indexPath;
    private String docsPath;
    private String params;

    ITMApiBuildThreadTest(String indexPath, String docsPath, String params)
    {
        this.indexPath = indexPath;
        this.docsPath = docsPath;
        this.params = params;
    }

    public void run()
    {
        //ITMApiBuildTest.doBuild(indexPath, docsPath, params);
    }

    public static void main(String[] args)
    {
        String indexPath = "e:\\test_home\\index\\itm";
        String docsPath = "e:\\test_home\\resource\\dim";
        String params = "sub_index_dir=test";

        // create thread
        int Thread_Len = 10;
        ITMApiBuildThreadTest[] trs = new ITMApiBuildThreadTest[Thread_Len];
        for (int i = 0; i < trs.length; ++i)
        {
            trs[i] = new ITMApiBuildThreadTest(indexPath, docsPath+i, params);
        }
        // start
        for (int i = 0; i < trs.length; ++i)
        {
            trs[i].start();
        }
    }
} // class ITMApiBuildThreadTest end
