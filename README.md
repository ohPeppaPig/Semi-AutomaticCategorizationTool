## 业务文档辅助归档在线工具--后端代码实现

#### 使用框架及算法包

- 后端框架 ：SpringBoot + Mybatis + Redis + MySQL
- 算法包：Hanlp + IR工具（实验室相似度计算代码：支持VSM，LSI）+ 百度OCR
- 工具包：jedis（Redis连接工具）+ Lombok（代码简化工具）



#### 环境配置

- IDEA + MySQL + Redis + JDK1.8 + MAVEN
- IDEA 导入代码后，MEVEN自动加载服务包，无需其他操作
- 按照Lombok插件  ：https://jingyan.baidu.com/article/0a52e3f4e53ca1bf63ed725c.html  



#### 代码包结构

...

- Config   ：配置包
  - aconfig  :  解决前后端跨域问题
  - RedisConfig ：Redis 常用配置项
- controller ：控制层
  - AdminController ：工作台控制层
  - CommentController：评论控制层
  - DocumentController：文档控制层
  - PretreatmentController：预处理控制层
  - TitleController：标题控制层
  - UserController：用户控制层  （待完善） 
- dao ：持久层
  - ClassifictionDao ：分类相关持久
  - DocumentDao：文档相关持久
  - pretreatmentDao：预处理相关持久
  - TitleClusterDao：标题相关持久
  - UserDao：用户持久（待完善）
- data ：放置Hanlp 中分类算法的训练集模型
- dataMini：搜狗实验室的文档集 ：https://www.sogou.com/labs/resource/list_pingce.php
- entity ：对象pojo  
  - 包括：工作台 展示对象，文档描述对象等
  - 不再展开描述
- HanLpTools ： hanlp相关的组件
  - 不再描述：名称介绍
- IRTools ：实验室的相似度计算工具
- OCRTools ： 百度OCR 识别工具
  - OCRService ：OCR相关服务接口
- Service  ：服务层
  - 对应着controller层的相关逻辑实现
  - 名称可容易得知服务类的作用
- simu_data
  - 思目一级分类的文档集
- subClusterData
  - 一级分类下的二级聚类结果
- Utils
  - 一些常用的工具类
- VO
  - 传递给前端的对象