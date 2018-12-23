 
超图supermap模块说明

执行超图代码(SupermapTest)的前提条件：
一、环境变量配置：先把当前Bin目录复制到某一盘符，如“D:\Bin”，
        然后设置到环境变量Path中，上移到第一行
二、Native library location设置：该项目右击build path，
   Native library location指定第一步的目录
三、jar包引入：该项目右击build path，Add External JARs添加Bin目录的所有jar文件
注意：如果执行时报找不到WrapjGeo.dll文件
    1.查看bin目录中是否存在；
    2.环境变量是否在Path中第一行
    3.重启ide