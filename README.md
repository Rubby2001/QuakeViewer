# QuakeViewer 360 Quake API 图形化客户端

## 使用方法

Release中的jar包请在jdk8环境运行：

```
java -jar QuakeViewer.jar
```

查询效果如图所示：

![image-20231214162439986](README.assets/image-20231214162439986.png)

可以右键复制URL、ip等内容，可以导出选中的数据、全部查询数据：

![image-20231216005847289](README.assets/image-20231216005847289.png)

双击可以实现跳转：

![image-20231214162656467](README.assets/image-20231214162656467.png)

## 配置API-KEY

第一次运行会弹出提示配置apikey等信息：

![image-20231214162959349](README.assets/image-20231214162959349.png)

配置完成后，会在同文件夹下生成配置文件api.config：

![image-20231214163105814](README.assets/image-20231214163105814.png)

每次重新配置后，请重启应用加载配置。