#，linux，windows下的区别，多个jar包windows下用分号隔开，如下：
java -Xmx3684m -cp "itm.jar;itm-test.jar" com.iflytek.itm.test.ITMScpTest "bug.scp"
而linux下用冒号隔开
java -Xmx3684m -cp "itm.jar:itm-test.jar" com.iflytek.itm.test.ITMScpTest "bug.scp"


TODO:
1，添加初始化和逆初始化，将log和分词表的加载放入这里面
2，
