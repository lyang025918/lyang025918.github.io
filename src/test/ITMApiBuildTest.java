/**
 * @file ItmUtils.java
 * @brief
 *
 *  测试ITM的build接口
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
 *  <tr> <td> 1.0	<td>12-12-20	    <td>wuqiu  <td>创建 </tr>
 * </table>
 */
package test;

import com.iflytek.itm.api.ITM;
import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.api.ITMFactory;
import mylib.file.DelDir;
import mylib.file.FileInfo;


import java.io.File;


public class ITMApiBuildTest
{
    // main
    public static void main(String[] args)
    {
        System.out.println("测试功能");

        ITMApiBuildTest inst = new ITMApiBuildTest();
        // build
        String indexPath = "e:\\test_home\\index\\201801";
        String docsPath = "e:\\test_home\\resource\\test";
        String params = "sub_index_dir=dim1 \n max_buffered_docs=6000";
        inst.doBuild(indexPath, docsPath, params);
      //inst.doBuildDoc(indexPath, params);
        //inst.doUpdate();
    }

    // build
    public void doBuildDoc(String indexPath, String params)
    {
        long start = System.currentTimeMillis();
        ITM inst = ITMFactory.create();
        ITMBuilder.ITMDocument document = new ITMBuilder.ITMDocument();
        ITMBuilder.ITMField idField = new ITMBuilder.ITMField("text", "string", "你喜欢&什么&女", "org.wltea.analyzer.lucene.IKAnalyzer", false);
        document.add(idField);
        // todo：尽量用小数点
        ITMBuilder.ITMField contentField = new ITMBuilder.ITMField("silences", "string", "0000001000|0-2000 0100020000|2000-8000",
            "com.iflytek.itm.search.sil.SilAnalyzer", false);
        document.add(contentField);
        ITMBuilder.ITMField lsField = new ITMBuilder.ITMField("sil", "int", "5", null, false);
        document.add(lsField);

        int ret = inst.build(indexPath, params, document);
        if (ret != 0)
        {
            System.out.println("Error: errcode=" + ret);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // build
    public void doBuild(String indexPath, String docsPath, String params)
    {
        long start = System.currentTimeMillis();
        // 为了便于调试，需要将原来的索引目录删除
//        String subDirPath = indexPath + "/usr/test";
//        File idxDir = new File(subDirPath);
//        if (idxDir.exists())
//        {
//            // do delete idx
//            boolean beret = DelDir.doDel(subDirPath);
//            if (beret)
//            {
//                System.out.printf("DelDir.doDel | success, subDirPath=[%s]\n", subDirPath);
//            }
//            else
//            {
//                System.out.printf("DelDir.doDel | fail, subDirPath=[%s]\n", subDirPath);
//            }
//        }
        ITM inst = ITMFactory.create();
        ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
        int ret = inst.build(indexPath, params, builder);
        if (ret != 0)
        {
            System.out.println("Error: errcode=" + ret);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // update
    public void doUpdate()
    {
        long start = System.currentTimeMillis();
        // update
        String indexPath = "e:\\test_home\\index\\itm";
        ITM inst = ITMFactory.create();
        ITMUpdateBuilder builder = new ITMUpdateBuilder();
        int ret = inst.build(indexPath, "sub_index_dir=2018-01 \n" + "is_update_document=true \n",
            builder);
        if (ret != 0)
        {
            System.out.println("Error: errcode=" + ret);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }
    class ITMUpdateBuilder implements ITMBuilder
    {
        private int cnt = 0;
        // ITM引擎会调用的函数
        @Override
        public void event(String id, int evt, String msg)
        {
            System.out.println("Event called: id=" + id + ", event = " + evt + ", msg=" + msg);
        }

        @Override
        public ITMDocument read()
        {
            if (cnt == 1)
            {
                return null;
            }
            ITMDocument document = new ITMDocument();
            ITMField idField = new ITMField("id", "int","1", null, true);
            document.add(idField);
            ITMField phoneField = new ITMField("phone", "string", "13810439507", null, false);
            document.add(phoneField);
            ++cnt;
            return document;
        }
    }

    // delete
    public void doDelete()
    {
        long start = System.currentTimeMillis();
        // delete
        String indexPath = "e:\\test_home\\index\\itm";
        String docsPath = "e:\\test_home\\resource\\dim";
        ITM inst = ITMFactory.create();
        ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
        int ret = inst.build(indexPath, "sub_index_dir=20180109\n" + "is_delete_document=true", builder);
        if (ret != 0)
        {
            System.out.println("Error: errcode=" + ret);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start + " total milliseconds");
    }

    // build的回调实现类
    class ITMBuilderImpl implements ITMBuilder
    {
        private File[] files = null;
        private int now = 0;

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
            // 组装document
            document = new ITMDocument();
            // 1.id, 如果不是要排序或者按范围来检索的话，id用string即可
            //String id = Integer.toString(now);
            String id = FileInfo.getNameNoExt(files[now]); // 用数字的文件名作为id
            ITMField idField = new ITMField("id", "int", id, null, true);
            document.add(idField);
            // 1.path
            String path = files[now].getPath();
            ITMField pathField = new ITMField("path", "string", path, null, false);
            document.add(pathField);
//
//            // 2.time
//            String time = Long.toString(20121220 + now);
//            ITMField timeField = new ITMField("time", "long", time, null, false);
//            document.add(timeField);

            // 3.content
            String content = FileInfo.getContent(files[now], "gbk");
            ITMField contentField = new ITMField("content", "string", content,
                "org.wltea.analyzer.lucene.IKAnalyzer", false);
            document.add(contentField);

//            // 4.phone
//            String mobilePhone = MobilePhone.randomGenerate();
//            ITMField phoneField = new ITMField("phone", "string", mobilePhone, null, false);
//            document.add(phoneField);
//
//            // 5.onebest
//            String onebest = "你好这个只是 测试不同的field 用不同的 分词器只是测试哦!";
//            ITMField onebestField = new ITMField("onebest", "string", onebest,
//                "org.apache.lucene.analysis.core.WhitespaceAnalyzer", false);
//            document.add(onebestField);
//
//            // 6.rand
//            Random ran = new Random(System.currentTimeMillis());
//            String randStr = Integer.toString(ran.nextInt(12));
//            ITMField randField = new ITMField("rand", "string", randStr, null, false);
//            document.add(randField);

            // 打印
            // if (now % 1000 == 0)
            {
                int mb = 1024 * 1024;
                System.out.println("adding doc...now=" + now + ", id=" + id + ", path=" + path +
                    ", free=" + Runtime.getRuntime().freeMemory() / mb + ", " +
                    "total=" + Runtime.getRuntime().totalMemory() / mb + ", " +
                    "max=" + Runtime.getRuntime().maxMemory() / mb);
            }
            // 下一个文档
            ++now;
            return document;
        }
    }

} // class ItmUtils end

