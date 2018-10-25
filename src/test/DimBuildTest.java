/**
 * @file    DimBuildTest.java
 * @brief
 *
 *  读取特定格式的维度信息来建立索引的工具
 *
 * @author wuqiu
 * @version 1.0
 * @date 2012年12月20日-下午4:25
 *
 * @see
 *
 * @par 版本记录：
 * <table border=1>
 *  <tr> <th> 版本	<th>日期			<th>作者    	<th>备注 </tr>
 *  <tr> <td> 1.0	<td>12-12-20	<td>wuqiu  <td>创建 </tr>
 * </table>
 */
package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.iflytek.itm.api.ITMBuilder;

import com.iflytek.itm.api.ITM;

import com.iflytek.itm.api.ITMFactory;

public class DimBuildTest{
    // main
    public static void main(String[] args)
    {
        System.out.println("测试功能");

        DimBuildTest inst = new DimBuildTest();
        // build
        inst.doBuild();
       // inst.testSelf();
    }

    // build
    public void doBuild()
    {
        long start = System.currentTimeMillis();
        String indexPath = "e:\\test_home\\index\\itm\\";
        String docsPath = "e:\\test_home\\resource\\dim";
        String params = "sub_index_dir=dim";
        ITM inst = ITMFactory.create();
        ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
        int ret = inst.build(indexPath, params, builder);
        if (ret != 0)
        {
            System.out.println("Error: errcode=" + ret);
        }
        System.out.println("read | total doc="+(builder.idoc));
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
        System.out.println("ret:"+ret);
    }
    // test self
    public void testSelf()
    {
        String docsPath = "e:\\test_home\\resource\\dim";
        ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
        ITMBuilder.ITMDocument itmDocument = null;
        while ((itmDocument = builder.read()) != null)
        {
            //
        }
    }

    // build的回调实现类
    class ITMBuilderImpl implements ITMBuilder
    {
        private File[] files = null;
        private int now = 0;
        private int iline = 0;  // 记录着现在读取到第几个文件的第几行了
        private BufferedReader bufferedReader = null; // 每个dim文件只打开一次
        private String strLine = null;
        public int idoc = 0; // 记录总共建立了多少条语音

        // 构造函数
        public ITMBuilderImpl(String docsPath)
        {
            do
            {
                final File docDir = new File(docsPath);
                if (!docDir.exists() || !docDir.canRead())
                {
                    System.out.println("Document directory '" + docDir.getAbsolutePath() +
                        "' does not exist or is not readable, please check the path");
                    break;
                }
                if (docDir.isDirectory())
                {
                    files = docDir.listFiles();
                }
            } while (false);
        }

        // ITM引擎会调用的函数
        @Override
        public void event(String id, int evt, String msg)
        {
            System.out.println("Event called: id=" + id + ", event = " + evt + ", msg=" + msg);
        }

        @Override
        public ITMDocument read()
        {
            ITMDocument document = null;
            if (files == null || now == files.length)
            {
                return null;
            }
            // 先看看是否有必要解析新的文件，每个文件是几十万个document的集合
            DimLine dimLine = readDimLine(files[now]);
            if (dimLine == null)
            {
                return null;
            }
            System.out.println("read | adding..." + dimLine.mypath);

            // 组装document
            document = new ITMDocument();
            // id
            String id = String.valueOf(idoc);
            ITMField idField = new ITMField("id", "int", id, null, true);
            document.add(idField);
            // mypath
            String mypath = dimLine.mypath; // 文件路径+行号做id
            ITMField mypathField = new ITMField("mypath", "string", mypath, null, false);
            document.add(mypathField);
            // content
            String content = dimLine.content;
            ITMField contentField = new ITMField("content", "string", content,"org.wltea.analyzer.lucene.IKAnalyzer", false);
            document.add(contentField);
         /*   // contact_id
            String contact_id = dimLine.contact_id;
            ITMField contact_idField = new ITMField("contact_id", "string", contact_id, null, false);
            document.add(contact_idField);
            // call_phone
            String call_phone = dimLine.call_phone;
            ITMField call_phoneField = new ITMField("call_phone", "string", call_phone, null, false);
            document.add(call_phoneField);
            // kf_id
            String kf_id = dimLine.kf_id;
            ITMField kf_idField = new ITMField("kf_id", "string", kf_id, null, false);
            document.add(kf_idField);
            // accept_phone
            String accept_phone = dimLine.accept_phone;
            ITMField accept_phoneField = new ITMField("accept_phone", "string", accept_phone, null, false);
            document.add(accept_phoneField);
            // begin_date
            String begin_date = dimLine.begin_date;
            ITMField begin_dateField = new ITMField("begin_date", "long", begin_date, null, false);
            document.add(begin_dateField);
            // file_uri
            String file_uri = dimLine.file_uri;
            ITMField file_uriField = new ITMField("file_uri", "string", file_uri, null, false);
            document.add(file_uriField);
            // call_type
            String call_type = dimLine.call_type;
            ITMField call_typeField = new ITMField("call_type", "string", call_type, null, false);
            document.add(call_typeField);
            // call_time
            String call_time = dimLine.call_time;
            ITMField call_timeField = new ITMField("call_time", "int", call_time, null, false);
            document.add(call_timeField);
            // contact_time
            String contact_time = dimLine.contact_time;
            ITMField contact_timeField = new ITMField("contact_time", "int", contact_time, null, false);
            document.add(contact_timeField);
            // caller_reason
            String caller_reason = dimLine.caller_reason;
            ITMField caller_reasonField = new ITMField("caller_reason", "string", caller_reason, null, false);
            document.add(caller_reasonField);
            // user_brand
            String user_brand = dimLine.user_brand;
            ITMField user_brandField = new ITMField("user_brand", "string", user_brand, null, false);
            document.add(user_brandField);
            // user_level
            String user_level = dimLine.user_level;
            ITMField user_levelField = new ITMField("user_level", "string", user_level, null, false);
            document.add(user_levelField);
            // user_level
            String satisfaction = dimLine.satisfaction;
            ITMField satisfactionField = new ITMField("satisfaction", "string", satisfaction, null, false);
            document.add(satisfactionField);
            // region
            String region = dimLine.region;
            ITMField regionField = new ITMField("region", "string", region, null, false);
            document.add(regionField);*/

            ++idoc;
            return document;
        }

        // 解析维度信息，每次读取一行，一行对应itm的一个document
        private DimLine readDimLine(File file)
        {
            DimLine dimLine = null;
            try
            {
                if (iline == 0) // 只在读取第一行的时候打开，所有文件只打开一次
                {
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    bufferedReader.readLine(); // 跳过第一行
                    ++iline;
                }

                strLine = bufferedReader.readLine();
                if (strLine == null) // 到这个文件的末尾了
                {
                    bufferedReader.close();
                    ++now; // 开始读取下一个文件
                    iline = 0;
                    if (now == files.length)
                    {
                        return null;
                    }
                    return readDimLine(files[now]);
                }

                // 解析这一行
                dimLine = new DimLine(files[now], iline, strLine);
                ++iline;
            }
            catch (Exception e)
            {
                System.out.printf("readDimLine | Error: file=%s, line=%d, msg=%s\n",
                    files[now].getPath(), iline, e.getMessage());
            }
            return dimLine;
        }

    } // class ITMBuilderImpl end

    // 维度信息，一行对应itm的一个document
    class DimLine
    {
        String mypath;
        String content;

      /*  String contact_id;      // 电话流水号,string
        String call_phone;      // 呼叫号码,string
        String kf_id;           // 客服工号,string
        String accept_phone;   // 受理号码,string
        String begin_date;     // 通话时间,long
        String file_uri;       // 语音文件存储路径,string
        String call_type;      // 呼叫类型,string
        String call_time;      // 通话时长,int
        String contact_time;   // 接触时长,int
        String caller_reason;  // 来电原因,string
        String user_brand;     // 用户品牌,string
        String user_level;     // 客户等级,string
        String satisfaction;   // 满意度,string
        String region;          // 所属区域,string
*/        public DimLine(File file, int i, String str)
        {
            this.mypath = file.getPath()+"."+i;
            this.content = str;
            parse();
        }
        private int parse()
        {
            int ret = 0;
            String[] arrs = content.split("\\|");
         /*   contact_id = arrs[0].trim();
            call_phone = arrs[1].trim();
            kf_id = arrs[2].trim();
            accept_phone = arrs[3].trim();
            begin_date = String.valueOf(toLongDate(arrs[4].trim()));
            file_uri = arrs[5].trim();
            call_type = arrs[6].trim();
            call_time = arrs[7].trim();
            contact_time = arrs[8].trim();
            caller_reason = arrs[9].trim();
            user_brand = arrs[10].trim();
            user_level = arrs[11].trim();
            satisfaction = arrs[12].trim();
            region = arrs.length <= 13 ? "" : arrs[13].trim();*/

            return ret;
        }
        private long toLongDate(String str)
        {
            long dt = 0;
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                dt = sdf.parse(str).getTime();
            }
            catch (ParseException e)
            {
                System.out.println("toDate | error: parse failed, str=" + str);
                dt = 0;
            }

            return dt;
        }
    }
} // class DimBuildTest end

