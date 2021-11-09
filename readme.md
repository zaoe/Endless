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
