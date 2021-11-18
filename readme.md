### 无尽贪婪
* 前置mod的引入
    ```java
    //引入仓库
    repositories {
       maven {
         url "https://www.cursemaven.com"
       }
       maven {
         //引入maven仓库
         url = "https://jei.net/maven"
       }
    }
    dependencies {
        minecraft 'net.minecraftforge:forge:1.16.4-35.1.4'
        //    compile "codechicken:CodeChickenLib:${config.mc_version}-${config.ccl_version}:deobf"  //引入mod API
        runtimeOnly fg.deobf("curse.maven:jei-238222:3245003") //引入mod
    }```
* 1.0.0开发中....
  1. 无尽工作台基本完成。
  2. 合成配方基本添加。
  3. 无尽套效果基本完成。
  4. 中子态素收集器基本完成，优化gui基本文本显示。
  5. 中子态素压缩机基本完成
  6. 压缩机配方基本完成
  7. jei合成查看插件添加完成
  8. 无尽工具效果基本完成，物质团
  * 无尽剑：秒杀生物
  * 无尽镐：第一形态：对镐类方块极高破坏速度加成，第二形态（锤）：范围
  破坏，使用物质团打包；潜行右键切换形态
  * 无尽斧：潜行右键范围破坏，连锁破坏树木
  * 无尽铲：第一形态：对铲类方块极高破坏速度加成，第二形态（毁灭者）：范围
    破坏，使用物质团打包；9*9转换草径
  * 无尽锄：9*9耕地，并清除上方方块；潜行右键收获周围作物，并催熟未成熟作物
  * 啄颅剑：击杀小白必定掉落凋零骷髅头
