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
