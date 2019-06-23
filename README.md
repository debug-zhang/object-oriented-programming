[TOC]



# 多项式函数求导



# 一、三次作业简单介绍

**第一次作业：** 简单多项式导函数的求解，仅包含常数与幂函数。

**第二次作业：** 包含简单幂函数和简单正余弦函数的导函数的求解，在第一次作业的基础上，增加了sin(x)、cos(x)和导数的四则运算。

**第三次作业：** 包含简单幂函数和简单正余弦函数的导函数的求解，在第二次作业的基础上，增加了复合函数的求导。

 

# 二、三次作业的类图与度量分析

## 第一次作业：

### 1、架构构思：

　　第一次作业是我第一次接触面向对象的程序设计，没有经验，再加上这次作业并不是非常需要“面向对象”，我的设计也比较偏向面向过程。这次的难点就在于读入字符串的合法性审查和多项式分割存储，我专门设计了一个 ***StringChecker*** 类用于完成这项功能。由于所有的项都可以写成 **axb** 的形式，为了简化程序，我仅仅设计了一个 **Poly** 类，并将读入的字符串将 +- 变为 +-<space>，用<space>进行存储。最后是一个主类用于完成多项式的主体求导和输出功能。

### 2、项目分析：

#### 类图

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324215121288-862780243.jpg)

#### 度量分析

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324215151229-303972841.jpg)

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324215201727-233631802.jpg)

### 3、自我总结：

　　可以非常明显的发现，主类的***Cyclomatic Complexity***非常非常高，重点就在于 ***output*** 方法的设计问题，导致其***Cyclomatic Complexity*** 和 ***Design Complexity*** 很高。

　　一方面，我应该将主类设计的越简单越好，将 ***parsePoly***、***computePoly*** 和 ***output*** 方法的实现扔出去新建类；另一方面，我应该重写 ***Poly*** 类的 ***toString*** 方法，简化程序的输出，降低复杂度。

　　除此之外，我的***StringChecker*** 类设计的是有些丑陋的，其中包含了很多格式特判，这主要是因为我对正则表达式的使用熟练度不足。

### 4、程序bug分析：

　　非常幸运的是，我的程序在强测环节和互测环节并没有被查出bug，但在历次提交和我的自我测试中，我在不断修复的时候明显感觉到了自己的设计问题。主要问题件就是前文提到的***output*** 方法和***StringChecker*** 类的编写。

　　***StringChecker*** 类中的合法性检查方法，我的正则表达式都是一个个小正则，这样的好处是避免了爆栈问题。但是由于我在初次设计时没有考虑完善，所以是在一个不断修补的状态中的，并不是非常流畅。

　　在 ***output*** 方法的实现过程中，因为我为了缩短输出长度，它的循环复杂度很高，我在输出时有很多错误，debug的时候很困难，这也是自己的设计问题，应该结合***Poly*** 类的 ***toString*** 方法，这样的好处不仅仅在于降低复杂度，还简化了debug的难度。

### 5、性能分析：

　　我在这次作业中使用的是 ***ArrayList*** 存储多项式，使得我在合并同类项时还需要进一步的处理，使得程序复杂。同时，我没有考虑将 + 提到最前的问题，使得很多题性能分没有拿满。

　　应该使用 ***HashMap*** 存储多项式，自动合并同类项，在输出时按照系数排序，由大到小输出。

### 6、互测分析：

　　第一次作业的互测屋气氛非常友好，没有狼人。

　　由于本次作业并不复杂，我的策略就是专盯大家的合法性检查和输出这两部分。大正则容易爆栈或者出错，小正则容易漏情况，这都是经常出现bug的地方。同时，在输出时由于性能分的存在，x、-x，0等情况也都容易出错。

　　我按照每个人程序的代码设计结构分别构造被测样例，进行提交，有效性不错。同时我也学习到了很多正则的写法、输出的优化等等，收获很多。

　　另外，本次互测大家状态透明，其实是比较容易发现bug的，每次状态更新后简单分析即可得到每个人大致的bug情况，进而有针对性的检查。

 

## 第二次作业：

### 1、架构构思：

　　第二次作业相比第一次，面向对象的特性有所增强。我在课上刚公布题目的时候，就想到了每一项都能写成  ***kxasinb(x)cosc(x)*** 这种形式，写成这种形式的好处是几乎与第一次作业在架构方面相差不大，便于处理。坏处就是我们都猜到了第三次作业肯定是复合函数，然而这种形式可扩展性几乎为零，无法适应后面的任务。

　　所以我在设计之初，构造了 **Factor** 抽象类，并通过继承得到 ***Cons***、***Power***、***Sin***、***Cos*** 类，增强可扩展性。然而，想的总是比做得好，由于自身水平问题，我在求导上卡了非常长的时间，最终无奈放弃，转回最初的设计方法。

　　有了第一次作业的教训，我将 ***(a,b,c)*** 设计成 ***HashMap*** 的 **key** ，将 ***k*** 设计成 **HashMap** 的 ***value***。重写***hashCode*** 和 **equals** 方法，完成 ***Term*** 的设计。并且听取了研讨课上大佬们的意见，将 **Error** 写成静态方法，不断调用。拆分 ***PolyChecker*** 类的检查方法，分而治之。主类大大简化，只负责类的创建与方法的调用。余下的事情就非常简单了。

### 2、项目分析：

#### 类图

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324230832559-1393648472.jpg)

#### 度量分析

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324230846311-975517476.jpg)

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324232332428-1954018270.jpg)

### 3、自我总结：

　　可能是因为还是不熟悉面向对象，加上中途换道的影响，这次的分析结果非常差劲。大量方法的***Essentail Complexity***、***Cyclomatic Complexity***  和 ***Design Complexity*** 都有很多问题。**Term** 类和 ***Poly*** 类也由于构造和合并同类型的原因循环复杂度极高。

　　虽然这次我采取“能扔就扔”的策略，让每个类不那么臃肿，但是部分方法的循环问题却是我之前没有想过的。大量的循环导致了方法的复杂，同时也大大增加了出错的可能和debug的难度。

　　非常羞耻的是，这次的输出我考虑到了 ***toString*** 方法的重要性，但在具体实现时为了偷懒使用了打表的方法，使得 ***print*** 方法非常丑陋。

### 4、程序bug分析：

　　同样非常幸运，我的程序在强测环节和互测环节并没有被查出bug。

　　在历次提交和我的自我测试中，这次作业的出错总体来说还是比较少的。原因主要在于这次的 ***PolyChecker*** 结合了上次互测屋中其他同学的优秀设计经验，分治效果非常明显。另外，虽然 ***print*** 方法很丑陋，但是丑的好处是考虑全面，避免了输出的bug。

　　再加上我的化简方法非常捡漏，使得化简的bug非常容易显露，便于将bug扼杀在摇篮里。

### 5、性能分析：

　　这次作业除了使用 ***HashMap*** ，正项提前外，化简方法只考虑了非常简单的 **sin2(x)+cos2(x)=1** 这一最简单的形式，使得我的性能分几乎全部损失。

　　我在讨论区中也看到了很多大佬思路的分享，但在当时感到很难实现，加上自己容易写成bug，最终放弃。

### 6、互测分析：

　　第二次作业的互测屋气氛仍然非常友好，没有狼人，加上对hack情况的隐匿，群起攻之的情况也消失了。

　　这次作业大家的设计复杂度剧增，加上第一次互测的经验，很多简单的测试比如爆栈、空输入等没有成效。于是我在同学的帮助指导下采用了自动评测的方法进行测试，然而由于我的测试样例生成程序是用正则表达式自动生成的，导致我仍然需要手动构造错误样例，最终的结果就是初尝自动评测非常失败，最后仅仅找到一个bug。并没有结合被测程序的代码设计结构来设计测试用例。

　　于是在后期我已经转为学习他人代码架构，为第三次作业做准备。我发现大家更多也是采用与我类此的方法，其他更精妙的设计还是少见，更多学习的还是小的细节，有点遗憾。

 

## 第三次作业：

### 1、架构构思：

　　构思过程简单来说，就是四个字——一脸懵逼。第二次作业的构思过程已经让我品尝过一次失败，这次依然有着非常多的困难去让我克服。

　　这次我仍然选择了构造 **Factor** 抽象类，并通过继承得到 ***Cons***、***Power***、***Sin***、***Cos*** 类，有了前两次的经验，我在这些因子的内部就重写了 ***toString*** 方法和 ***clone*** 方法，降低输出的复杂度；按照统一的结构，给每个因子设计专有的 ***diff*** 方法，为之后的求导做准备。同时 ***PolyChecker*** 类也延续了第二次的模式，进行改进。

　　然后，就没有然后了，我一直卡壳。

　　幸运的是，我的朋友一直非常无私的帮助着我，为我解答疑惑。我按照讨论区提到的构造树状结构求导，回顾了大一下的数据结构内容，采用中缀转后缀进而构造树结构，完成了 ***Poly*** 的设计。

　　接下来，求导和输出就是一个通过树不断递归的过程。在输出中，我感受到前两次作业中化简的一些不便，这次并没有采用直接输出的方法，而是利用 ***StringBuilder*** 做一个缓冲，减轻输出判断的负担。

 

### 2、项目分析：

#### 类图

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324235525708-1442409840.jpg)

#### 度量分析

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324235540680-286434890.jpg)

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324235656225-1489591099.jpg)

### 3、自我总结：

　　这次设计我划分的很细致，3个包，14个类，使得类图比较庞大。

　　由于字符串的解析、中缀转后缀和树的构建中的的循环分支判断（***while***、***switch***），求导和输出的递归，使得这次设计相关类的循环复杂度依然非常高。

　　但是因为这次作业的难度很高，我自己已经是拼命完成的，所以我对复杂度的优化感觉并不是非常有头绪，还是希望能够看到更多大佬的设计和建议。

### 4、程序bug分析：

　　这次就没那么幸运了，我在强测中爆了3个点，互测中被hack了7次。

　　在中测中，由于前文提到的复杂度过高，我的debug可以说是非常非常困难。需要不断的尝试、尝试、再尝试，过程非常的痛苦，最终才能找到bug，而bug果然也是在循环复杂度最高的中缀转后缀和树的构建这两部分内容中。这种复杂度过高的情况是我之后必须要避免的，无论是bug出现的可能性还是debug的难度都是非常高的，很影响代码质量。

　　强测和互测这一共10个样例错误，最终发现是一个问题：我在中缀转后缀的过程中，由于是按运算符判断，对于 ***+\\d** 和 ****-\\d*** 这两种情况就会存在误判，解决办法是在中缀转后缀前，将 ******* 替换为 ****#*** ，避免这种误判。

### 5、性能分析：

　　这次作业能活下来就已经很艰难了，自动放弃性能分。

### 6、互测分析：

　　第三次互测屋可以说是非常不友好了，厮杀极其惨烈，狼人非常多，还有狼王。

　　这次互测我在之前自动评测的基础上，采用了 ***Nemo*** 大佬在Github上提供的利用递归下降编写的生成样例程序。互测效果非常显著，同时在自动评测发现bug后，结合被测程序的出错点，构造样例测试确定bug类型，尽量避免同质bug。

 

# 三、Applying Creational Pattern

## 1、架构构思：

　　我在三次架构设计中采用的方法是，先思考所需的类和方法，创建它们并且不实现具体属性，不断尝试组合与构造，不断修改，最终得到一个合理的框架结构，然后再进行分析设计合理性，最后开始着手程序的实现。

## 2、面向对象思想：

　　三次作业带来的最主要的就是思想的转变，从面向过程到面向对象，这个转变很艰难，到现在也只是摸到点头绪，面向对象是一种思维方法。类、方法的构建与层次关系都是要不断思考不断改进的。

## 3、重构：

### （1）抽象类的使用：

　　相对于接口我还是更喜欢抽象类，它避免了对属性的重复定义和部分方法的重复实现，同时同一了各个因子的方法参数，并且在使用时不需要区分具体是哪一个子类。

```
 1 public abstract class Factor {
 2     private BigInteger index;
 3 
 4     Factor() {
 5         this.index = BigInteger.ZERO;
 6     }
 7 
 8     Factor(BigInteger index) {
 9         this.index = index;
10     }
11 
12     public BigInteger getIndex() {
13         return index;
14     }
15 
16     public abstract Factor clone();
17 
18     public abstract String toString();
19 
20     public abstract Factor[] diff();
21 
22 }
```

### （2）方法的重写：

　　***toString*** 、 ***clone*** 和 **equals** 这三个方法非常的实用，针对不同子类的重写可以帮助我们简化代码。同时，在需要 **HashMap** 处理 ***key*** 时，***hashCode*** 方法的重写也是很必要的。

### （3）架构改进：

 　　我这次的树结构其实是有些复杂的，在浏览其他同学的优秀代码时发现很多非常精妙的设计，值得学习，比如划分成加组合项、乘组合项和嵌套组合项，这个思路老师课上有提到过，但是当时并不太会实现，在尝试过树结构和学习他人代码后，有了一些思路，这种方法更为简便，能极大降低复杂度，减少bug的出现概率和debug难度。

### （4）工厂方法：

　　这其实是我之前的一个盲区，工厂方法能更加灵活地创建对象，帮助梳理结构，减少层次，这是需要我仔细学习的地方。

 

# 四、总结

　　第一次写博客，可能存在很多问题，请大家见谅。

　　面向对象的第一单元已经结束了，收获很多，压力也非常的大，尤其是最后一次作业。我在架构设计、代码风格和程序拓展性方面都有了很多的学习与思考，发现了自己的很多问题，仍然需要不断努力。

 





# 多线程电梯



# 一、三次作业简单介绍

### **第一次作业：**

单部多线程傻瓜调度（FAFS）电梯的模拟

### **第二次作业：**

单部多线程可捎带调度（ALS）电梯的模拟

### **第三次作业：**

多部多线程智能（SS）调度电梯的模拟

### **关键点 1** **—— ALS(可捎带电梯)规则：**

可捎带电梯调度器将会新增主请求和被捎带请求两个概念。
主请求选择规则：
　　如果电梯中没有乘客，将请求队列中到达时间最早的请求作为主请求
　　如果电梯中有乘客，将其中到达时间最早的乘客请求作为主请求
被捎带请求选择规则：

电梯的主请求存在，即主请求到该请求进入电梯时尚未完成
该请求到达请求队列的时间**小于等于**电梯到达该请求出发楼层关门的截止时间 电梯的运行方向和该请求的目标方向一致。即电梯主请求的目标楼层和被捎带请求的目标楼层，两者在当前楼层的同一侧。

其他： 

标准ALS电梯不会连续开关门。即开门关门一次之后，如果请求队列中还有请求，不能立即再执行开关门操作，会先执行请求。

### **关键点 2** **—— 多线程编程：**

　　这是我第一次接触多线程编程。多线程就是多个线程一起去协同完成一个任务，通过充分去共享资源来达到提升效率的一种编程思想。

　　在本单元的作业中，线程安全是一个贯穿全程的问题，这部分内容处理的还算好，但对我困扰极大的是CPU时间超时问题，我对wait()、notify()机制的使用还是有很大的不熟练。

### **关键点 3 —— 输入输出接口：**

　　本单元作业的输入输出使用的都是课程组提供的统一接口，避免了对输入输出做大量处理的麻烦。这在第一单元的作业中是让我们非常痛苦的一个地方，是bug高发地带也是优化难题之一。

 

#  二、SOLID设计原则

### SOLID 原则基本概念：

　　程序设计领域， SOLID (单一功能、开闭原则、里氏替换、接口隔离以及依赖反转)是由罗伯特·C·马丁在21世纪早期 引入的记忆术首字母缩略字，指代了面向对象编程和面向对象设计的五个基本原则。当这些原则被一起应用时，它们使得一个程序员开发一个容易进行软件维护和扩展的系统变得更加可能SOLID被典型的应用在测试驱动开发上，并且是敏捷开发以及自适应软件开发的基本原则的重要组成部分。

### [S] Single Responsibility Principle (单一功能原则)

- 每个类或方法都只有一个明确的指责。
- 类职责：使用多个方法，从多个方面来综合维护对象所管理的数据。
- 方法职责：从某个特定方面来维护对象的状态（更新、查询）。

### [O] Open Close Principle （开闭原则）

- 软件实体应该是可扩展，而不可修改的。也就是说，对扩展是开放的，而对修改是封闭的。
- 对扩展开放，意味着有新的需求或变化时，可以对现有代码进行扩展，以适应新的情况。
- 对修改封闭，意味着类一旦设计完成，就可以独立完成其工作，而不要对类进行任何修改。

### [L] Liskov Substitution Principle（里氏替换原则）

- 任何父类出现的地方都可以使用子类来替代，并不会导致使用相应类的程序出现错误。
- 子类虽然继承了父类的属性和方法，但往往也会增加一些属性和方法，可能会破坏父类的相关约束。

### [I] Interface Segregation Principle（接口隔离原则）

- 当一个类实现一个接口类的时候，必须要实现接口类中的所有接口，否则就是抽象类，不能实例化出对象。
- 软件设计经常需要设计接口类，来规范一些行为，避免往一个接口类中放过多的接口。

### [D] Dependency Inversion Principle（依赖反转原则）

- 代码应当取决于抽象概念，而不是具体实现。
- 高层模块不应该依赖于低层模块，二者都应该依赖于抽象。
- 抽象不应该依赖于细节，细节应该依赖于抽象。
- 类可能依赖于其他类来执行其工作。但是，它们不应当依赖于该类的特定具体实现，而应当是它的抽象。

 

# 三、三次作业的类图与度量分析

**度量分析标识：**

- - ev(G)　　本质复杂度
  - iv(G)　　 设计复杂度
  - v(G)　　  循环复杂度
  - OCavg　 平均方法复杂度
  - OSavg　 平均方法语句数（规模）
  - WMC　　加权方法复杂度
  - v(G)avg　平均循环复杂度
  - v(G)tot　总循环复杂度

 

## 第五次作业：

### 1、架构构思：

　　总体架构我是按照指导书的建议走的

- 主线程进行输入的管理，使用ElevatorInput，负责接收请求并存入队列
- 开一个线程，用于模拟电梯的活动，负责从队列中取出请求并进行执行，执行完后继续取继续执行
- 构建一个队列，用于管理请求，且需要保证队列是线程安全的

　　*Main*类中的main方法负责创建电梯线程，同时作为输入线程，管理输入，接受请求并存入队列，线程结束标志是读到null。

　　*ElevatorThread*类是电梯线程，按照FIFO策略的傻瓜电梯，每次搭载一名乘客，完成任务后再搭载另一名乘客，线程结束标志是读到null同时队列为空。

　　*Request*类包含三个属性id、from和to，在输入接口的三个get方法（getFromFloor()、getToFloor()、getPersonId()）的帮助下新建对象。同时利用IDEA自动生成三个属性的get方法（这步在完成之后才发现其实有点多余，可以直接使用输入接口里的*PersonRequest*）。

　　*RequestQueue*类作为请求队列，用于管理请求，以ArrayList为基础，保证线程安全，并配上所需的方法。

 

### 2、项目分析：

UML类图：

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423202156207-1152329080.jpg)

度量分析：

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222952523-208569164.png)

 

 ![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222800882-2009757395.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222718131-1230994859.png)

 

### 3、自我总结：

　　Main类main方法的ev(G)、iv(G)和v(G)都很高，原因在于main方法中包含了输入处理，这一部分我基本是按照指导书输入接口的Demo设计的，暂时还没有想到很好的解决办法。

```
 1 ElevatorInput elevatorInput = new ElevatorInput(System.in);
 2 　　while (true) {
 3    　　PersonRequest personRequest = elevatorInput.nextPersonRequest();
 4        　　if (personRequest == null) {
 5            　　elevatorThread.setEnd(true);
 6           　　 break;
 7　　　　　　　} else {
 8               Request request = new Request(personRequest.getPersonId(),
 9            　　personRequest.getFromFloor(),
10            　　personRequest.getToFloor());
11            　　queue.addRequest(request);
12            }
13    　　}
14 　　 elevatorInput.close();
```

　　ElevatorThread类run方法的iv(G)和v(G)很高，也是由于一个长期为真的while循环所导致的。

```
 1     public void run() {
 2         while (!isEnd()) {
 3             if (!queue.isEmpty()) {
 4                 try {
 5                     elevator.work(queue.getRequest());
 6                     queue.removeRequest();
 7                 } catch (InterruptedException e) {
 8                     e.printStackTrace();
 9                 }
10             }
11         }
12     }
```

 

### 4、程序bug分析：

　　这次作业比较简单，我中测只提交了两次就通过了，强测和互测也是全部安全通过。

　　第一次提交中测，我出现的问题在于，在读到null时就结束了所有线程，并没有考虑到请求队列中是否还有未完成的请求。

　　于是我将主线程结束标志设为读到null，*ElevatorThread*结束标志设为主线程读到null且请求队列为空。

　　*ElevatorThread*原结束标志：

```
1 private synchronized boolean isEnd() {
2 　　return end;
3}
```

 　　　　*ElevatorThread现*结束标志：

```
1private synchronized boolean isEnd() {
2 　　return end & queue.isEmpty();
3}
```

 

### 5、性能分析：

　　这次作业我采用的调度策略是完全按照FAFS调度策略走的，按照请求进入系统的顺序，依次按顺序逐个执行运送任务，不过这次作业不存在性能分，虽然效率很低，但更多是让我们熟悉熟悉多线程，为后面的作业做准备。

 

### 6、互测分析： 

 　　第一次作业的互测其实是非常困扰我的，很大的原因在于输入的顶点投放和正确性比对。最后我所采用的策略是非常傻瓜的自编数据，同时将输入时间戳全部设置为[0.0]，在本地投放后复制粘贴。

　　由于这次作业并不复杂，大家的程序设计的也都很周期，本次互测并没有测出bug。

 

## 第六次作业：

### 1、架构构思：

　　这次的总体架构我仍然是按照指导书去写的，不过还是有一些变动：

- 主线程进行输入的管理，使用ElevatorInput，负责接收请求并存入队列
- 构建一个调度器（本次的调度器可以和队列是一体的）
- 用于管理请求
- 和电梯进行交互并指派任务给电梯
- 且需要保证调度器是线程安全的
- 构建一个电梯线程，负责和调度器进行交互（比如每到一层进行一个交互）接收任务，并按照一定的局部策略进行执行。
- 设计的重点依然在于最大限度降低耦合，每个对象只应该管自己该管的事。

　　*Main*类、*ElevatorThread*类和*Request*类几乎保持不变，变化的主要是*Elevator*类和*RequestQueue*类。

　　*RequestQueue*类作为请求队列与调度器的统一体，包含了排序、选择捎带对象、分配任务等功能。我在上完理论课后，将原来的数据结构 ArrayList<> 换为了 Vector<>。Vector<> 相比与 ArrayList 的优势是它是线程安全的，对于同一个vector对象多个线程调用get、remove、size等方法时，它们是串行执行的。

　　我看了源码后发现，确实很多方法都有同步关键字synchronized，从而保证所有的对外接口都会以 Vector对象为锁，即，在vector内部，所有的方法都不会被多线程访问。

　　但是，单个方法的原子性（注：原子性，程序的原子性即不会被线程调度机制打断），并不能保证复合操作也具有原子性。

　　所以，虽然源代码注释里面说这个是线程安全的，因为确实很多方法都加上了同步关键字synchronized，但是对于符合操作而言，只是同步方法并没有解决线程安全的问题，还需要人为的去增加锁。

　　然而，如果是这样的话，那么用 Vector 和 ArrayList 就没有区别了，而且 Vector 的执行效率要低于 ArrayList，这一条是我在第七次作业中才发现的，在这种情况下，用 Vector 还不如用 ArrayList。

 

### 2、项目分析：

UML类图：

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423202140342-1121566130.jpg)

度量分析：

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423223251593-533983788.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423223141699-1125713520.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423223048635-106501191.png)

 

### 3、自我总结：

　　这次作业的重灾区在于RequestQueue类和Elevator类。

　　RequestQueue类主要在于调度器与队列一体，我的排序方法addAndSort()和寻找捎带方法getPiggy()有很多的for循环，导致复杂度升高。

　　而对于Elevator类，我非常后悔的是我的每次进入、输出和开门关门都是手动进行，没有把它们包装为方法，导致错误几率和复杂度增加，在第七次作业中我就第一时间将它们作出了改变。

```
 1 public class Elevator {
 2 
 3     private void arriveFloor(int floor) throws InterruptedException {
 4         Thread.sleep(runTime);
 5         TimableOutput.println("ARRIVE-" + floor + "-" + name);
 6     }
 7 
 8     private void openDoor(int floor) throws InterruptedException {
 9         TimableOutput.println("OPEN-" + floor + "-" + name);
10         Thread.sleep(doorTime);
11     }
12 
13     private void closeDoor(int floor) throws InterruptedException {
14         Thread.sleep(doorTime);
15         TimableOutput.println("CLOSE-" + floor + "-" + name);
16     }
17 }
```

 

```
 1 public class Request {
 2 
 3     public void inElevator(char name) {
 4         TimableOutput.println("IN-" + id + "-" + from + "-" + name);
 5     }
 6 
 7     public void outElevator(char name) {
 8         TimableOutput.println("OUT-" + id + "-" + to + "-" + name);
 9     }
10 }
```

　　另一方面，由于在电梯运行过程中，不断的取最高（最低）楼层，利用for循环遍历捎带队列，导致循环复杂度很高，使得我的v(G)高居不下。Elevator类的WMC也达到了吓人的44。

 

### 4、程序bug分析：

　　这次作业，我中测一直卡在最后一个点，提交了很多次才通过，而强测和互测全部安全通过。

　　前两次的中测提交修复了除最后一个点外的所有样例发现的bug，都是一些小细节错误，然而最后一个点始终测不出来，我翻阅了讨论区和水群，最后发现了跟我有相似经历的同学，他们给我提供了一些非常有价值的样例，比如

```
1-FROM-7-TO-10
2-FROM-7-TO-14
3-FROM-10-TO-5
1-FROM-1-TO-5
2-FROM-2-TO-6
3-FROM-3-TO-7
4-FROM-4-TO-8
5-FROM-5-TO-9
```

　　除此之外，因为这一单元的输入输出接口只有微小的不同，我发现我们可以选择用上一次作业的bug修复来寻找这一次作业的bug。

　　在这二者的帮助下，我最终发现我通不过最后一个点的原因在于，电梯在完成全部捎带任务后，自己所在楼层的更新有误。

 

### 5、性能分析：

　　本次强测性能分占强测总分的20%，我在强测中出现了两次80分，其他的性能分也并不高。

　　这次我的调动策略仍然非常保守，我的选择是将捎带队列按目标楼层的高低排序，在到达每一层加入捎带请求时，只考虑目标运动方向与现有运动方向是否一致，同时更新整体最终目标楼层。

```
 1 int updateTo = to;
 2 
 3 ……
 4 
 5 if (pigQueue.getLast().getTo() > updateTo) {
 6     updateTo = pigQueue.getLast().getTo();
 7 }
 8 
 9 ……
10 
11 if (pigQueue.getFirst().getTo() < updateTo) {
12     updateTo = pigQueue.getFirst().getTo();
13 }
```

 

### 6、互测分析：

　　第二次作业的互测，我首先是汲取了我在中测debug时的经验，将我的代码交到第一次作业的bug修复上，获取上次强测的数据，进行提交。

　　同时，在讨论区大佬和github的帮助下，在本地构建简易评测机，辅助测试。

　　这次作业大家把细节也都考虑到了，本次互测也并没有测出bug。

 

## 第七次作业：

### 1、架构构思：

　　终于来到了本单元最后一次作业，这次每个电梯的架构我是直接照搬的第六次作业，然而偷懒一时爽，强测火葬场，上一次作业的不规范让我在之后付出了很大的代价。

　　指导书的建议：

- 主线程进行输入的管理，使用ElevatorInput，负责接收请求并存入队列
- 构建一个调度器（本次的调度器可以和队列是一体的）
- 用于管理请求
- 和电梯进行交互并指派任务给电梯，并可能需要处理请求的先后顺序依赖关系
- 且需要保证调度器是线程安全的
- 对于性能优化党，调度器还需要对调度的优劣性有一定的判断，并以任务分配的方式给出较优的统筹规划结果
- 如果感觉一层调度分配器有点不够用或者已经越发臃肿的话，可以考虑构建多层分配器，将请求分配逻辑进一步拆分+细化，甚至使用多个线程构建为类似流水线的分配模式。
- 构建三个电梯线程
- 三部电梯统一建模。
- 各自进行一定的配置（容量，速度，楼层），并维护状态。
- 各自负责和同一个调度器进行交互（比如每到一层进行一个交互）接收任务。
- 各自基于接收到的任务按照一定的局部策略进行执行。
- 设计的重点依然在于最大限度降低耦合，每个对象只应该管自己该管的事。

　　我的做法是，将输入线程从主线程中拿了出来，主线程在创建完各个对象和各个线程后就自动结束。剩下由 InputThread 和 SchedulerThread 交互，接收请求并存入队列，SchedulerThread 和三个 ElevatorThread 交互，指派任务给电梯，完成换成工作，从而达到数据共享。

 

### 2、项目分析：

UML类图：

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423202113387-135343409.jpg)

度量分析：

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222351189-1756099186.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222457807-267496299.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222552771-381841044.png)

 

### 3、自我总结：

　　在汲取了第二次作业的教训后，我第三次作业中将之前写到的开关门、到达和进入离开都写成方法，增加程序的可读性，同时降低复杂度。

　　这次作业的重灾区在于Elevator类和SchedulerThread类。

　　Elevator类中的addPigQueue方法，是用来管理捎带队列的，所以需要不断遍历请求队列，寻找可以捎带的对象，导致复杂度很高。

　　SchedulerThread类中的run()方法则是因为我在不断遍历请求队列中的每一个元素，提取出来原楼层和目标楼层后再让它和三个电梯的可达楼层作循环比对，还要判断电梯是否超载，导致恐怖的方法复杂度。

```
 1 public void run() {
 2         while (!queue.isEmpty() && (queueA.size() < 5 || queueB.size() < 7 || queueC.size() < 6)) {
 3             r = queue.getFirst();
 4             from = r.getFrom();
 5             to = r.getTo();
 6             if (contains(floorA, from, to)) {
 7                 if (queueA.size() < 5) {
 8                     queueA.addAndSort(r);
 9                     queue.removeRequest();
10                 }
11             } else if (contains(floorB, from, to)) {
12                 if (queueB.size() < 7) {
13                     queueB.addAndSort(r);
14                     queue.removeRequest();
15                 }
16             } else if (contains(floorC, from, to)) {
17                 if (queueC.size() < 6) {
18                     queueC.addAndSort(r);
19                     queue.removeRequest();
20                 }
21             } else if (contains(floorA, from)) {
22                 if (queueA.size() < 5) {
23                     setTemp(r);
24                     queueA.addAndSort(r);
25                     queue.removeRequest();
26                 }
27             } else if (contains(floorB, from)) {
28                 if (queueB.size() < 7) {
29                     setTemp(r);
30                     queueB.addAndSort(r);
31                     queue.removeRequest();
32                 }
33             } else if (contains(floorC, from)) {
34                 if (queueC.size() < 6) {
35                     setTemp(r);
36                     queueC.addAndSort(r);
37                     queue.removeRequest();
38                 }
39             } else {
40                 queue.addRequest(r);
41                 queue.removeRequest();
42             }
43         }
44     }
```

###  

### 4、程序bug分析：

　　如果说前两次作业都比较顺利的话，这次作业就是惊险刺激+大型翻车现场。

　　这次作业，我的正确性没有问题，问题集中在CPU时间上。我的中测一直在出现 CPU_TIME_LIMIT_EXCEED ，花费了非常多的时间去修复，同时强测和互测也出现了很多的 CPU_TIME_LIMIT_EXCEED，可以说我这次作业中线程之间的通讯与轮转存在着很大的问题。

　　我在强测公布后寻找了几位大佬学习架构，这次的超时问题主要还是架构的锅，所以我也是从上周就开始重构代码，想完完全全将整个电梯写好。

 

### 5、性能分析：

　　本次强测性能分占强测总分的15%。

　　我的单个电梯的调度策略仍与上次一致。而整体分发保证均衡性，避免出现任一电梯过载而其他电梯空闲的状态。对于换乘，我分析得知，从一楼层去往另一楼层，最多只需要1次换乘，所以完成所有的电梯至多需要进入两次请求队列。

　　我在*Request*类中定义了两个to属性：

```
1 private int to;
2 private int tempTo;
3 
4 public void setTempTo(int temp) {
5     this.tempTo = to;
6     this.to = temp;
7 }
```

　　在完成换乘时更新to：

```
1 public void setFromAndTo() {
2     this.from = to;
3     this.to = tempTo;
4 }
```

　　　　如果发现to和tempTo相同，证明是直达，直接移除电梯，否则再次加入请求队列：

```
1 private synchronized int leaveAndTransfer(int i, Request r) {
2     r.outElevator(name);
3     if (r.isTransfer()) {
4          r.setFromAndTo();
5          baseQueue.addRequest(r);
6     }
7     pigQueue.removeRequest(r);
8     return i - 1;
9 }
```

 

### 6、互测分析：

　　前两次互测的春风拂面消失不见，取而代之的是一场腥风血雨。

　　我的互测策略仍然是 上次作业的强测数据 + 简易评测机，可以说第二次作业的强测数据的命中率还是很高的，收获不小，但是比起被刀来说还是入不敷出，最后还是努力告诉自己不要当狼人，别拼命提交样例。

 

 

# 四、心得体会

### 1、线程安全：

　　在多线程编程的学习中，我入门的方法是图书馆借来的《Java多线程编程核心技术》，里面详细的讲授了线程交互的策略与线程安全的设计。

　　在设计架构时，我们需要分析出共享对象和在其上的共享线程们，这是我们进行线程安全设计的基础。

　　在编码过程中，我们要保障共享对象中的方法的安全性和共享线程之间的协同问题。

　　在保证所有在线程安全的同时，保障类自身功能的高内聚低耦合，这二者直接需要很多的权衡，我在本单元的作业中这一点做的并不好，度量分析出现了大量的飘红。
　　在线程轮询上，避免暴力轮询，多用wait和notify来实现逻辑上的同步。
　　锁是很重要很有力的工具，但一定一定不能滥用锁，同时要避免死锁。

 

### 2、设计模式：

　　设计模式方面，我所依靠的一方面是老师上课的教授，另一方面则是《图解Java大学城设计模式》。

　　为什么我们需要学习设计模式呢？

　　这就跟我们看别人的代码来学习一样，是为了学习里面的精髓。每一本设计模式的书都会告诉你，这些都是在讲究如何对修改封闭，对扩展开放的事情。

　　我们学东西，重要的是学idea，次要的是学technique。

　　翻译成编程的语言就是，我们学设计模式，是为了学习如何合理的组织我们的代码，如何解耦，如何真正的达到对修改封闭对扩展开放的效果，而不是去背诵那些类的继承模式，然后自己记不住，回过头来就骂设计模式把你的代码搞复杂了，要反设计模式。

　　设计模式归根结底就是因为使用的程序语言的抽象能力不足才发明出来的。

　　这个单元的学习过程中，第七次作业给了我很大的教训，让我明白运用好设计模式去设计架构的重要性，不然就会出现大量的崩盘。

　　在设计实现的时候，要尽可能的留出空间，按照Open Close Principle （开闭原则）和Liskov Substitution Principle（里氏替换原则），充分体现程序的可扩展性设计，在程序中能用父类就用父类，能用接口就用接口，能预留扩展空间就预留扩展空间。这样也能让我们在单元式的学习中不需要一次次的重构代码。

　　遵循良好的设计原则，有利于我们平常在开发中写出更可维护的代码，便于团队协作也有利于后来者。

 

 





# JML



# 一、三次作业简单介绍

### 第九次作业：

实现两个容器类 **Path** 和 **PathContainer。**

本次作业最终需要实现一个路径管理系统。可以通过各类输入指令来进行数据的增删查改等交互。

实现指令：

| **添加路径**                         | **PATH_ADD 结点序列**              |
| ------------------------------------ | ---------------------------------- |
| **删除路径**                         | **PATH_REMOVE 结点序列**           |
| **根据路径id删除路径**               | **PATH_REMOVE_BY_ID 路径id**       |
| **查询路径id**                       | **PATH_GET_ID 结点序列**           |
| **根据路径id获得路径**               | **PATH_GET_BY_ID 路径id**          |
| **获得容器内总路径数**               | **PATH_COUNT**                     |
| **根据路径id获得其大小**             | **PATH_SIZE 路径id**               |
| **根据结点序列判断容器是否包含路径** | **CONTAINS_PATH 结点序列**         |
| **根据路径id判断容器是否包含路径**   | **CONTAINS_PATH_ID 路径id**        |
| **容器内不同结点个数**               | **DISTINCT_NODE_COUNT**            |
| **根据字典序比较两个路径的大小关系** | **COMPARE_PATHS 路径id 路径id**    |
| **路径中是否包含某个结点**           | **PATH_CONTAINS_NODE 路径id 结点** |

### 第十次作业：

实现容器类 **Path** 和数据结构类 **Graph 。**

本次作业最终需要实现一个无向图系统。

实现指令：

| **容器中是否存在某个结点** | **CONTAINS_NODE 结点id**                       |
| -------------------------- | ---------------------------------------------- |
| **容器中是否存在某一条边** | **CONTAINS_EDGE 起点结点id 终点结点id**        |
| **两个结点是否连通**       | **IS_NODE_CONNECTED 起点结点id 终点结点id**    |
| **两个结点之间的最短路径** | **SHORTEST_PATH_LENGTH 起点结点id 终点结点id** |

### 第十一次作业：

实现容器类 **Path** 和地铁系统类 **RailwaySystem。**

本次作业最终需要实现一个简单地铁系统。

实现指令：

| **整个图中的连通块数量**       | **CONNECTED_BLOCK_COUNT**                        |
| ------------------------------ | ------------------------------------------------ |
| **两个结点之间的最低票价**     | **LEAST_TICKET_PRICE 起点结点id 终点结点id**     |
| **两个结点之间的最少换乘次数** | **LEAST_TRANSFER_COUNT 起点结点id 终点结点id**   |
| **两个结点之间的最少不满意度** | **LEAST_UNPLEASANT_VALUE 起点结点id 终点结点id** |

### 关键点 1 —— JML 规格：

准确理解 **JML** 规格，然后使用 **Java** 来实现相应的接口，并保证代码实现严格符合对应的 **JML** 规格。

### 关键点 2 —— 架构设计：

　　本单元作业容器类的设计贯穿全程，且后一次作业需要使用前一次作业的容器类，同时还要继承前一次作业的容器类，需要仔细规划架构。

### 关键点 3 —— Junit 单元测试：

　　通过编写单元测试类和方法，来实现对类和方法实现正确性的快速检查和测试。还可以查看测试覆盖率以及具体覆盖范围（精确到语句级别），全面无死角的进行程序功能测试。

 

# 二、JML 语言

## 语言的理论基础

　　JML(Java Modeling Language)是用于对Java程序进行规格化设计的一种表示语言。JML是一种行为接口规格语言 （Behavior Interface Speciﬁcation Language，BISL），基于Larch方法构建。BISL提供了对方法和类型的规格定义手段。

　　一般而言，JML有两种主要的用法：

1. 开展规格化设计。这样交给代码实现人员的将不是可能带有内在模糊性的自然语言描述，而是逻辑严格的规格。
2. 针对已有的代码实现，书写其对应的规格，从而提高代码的可维护性。这在遗留代码的维护方面具有特别重要的意义。

### 注释结构：

　　JML以javadoc注释的方式来表示规格，每行都以@起头。有两种注释方式，行注释和块注释。其中行注释的表示方式 为 //@annotation ，块注释的方式为 /* @ annotation @*/ 。按照Javadoc习惯，JML注释一般放在被注释成分的近邻上部。

1. 1. requires子句定义该方法的前置条件(precondition)， elements.length>=1 ，即IntHeap中管理着至少一个元 素；
   2. 副作用范围限定，assignable列出这个方法能够修改的类成员属性，\nothing是个关键词，表示这个方法不对 任何成员属性进行修改，所以是一个pure方法。
   3. ensures子句定义了后置条件，即largest方法的返回结果等于elements中存储的所有整数中的最大的那个 (\max也是一个关键词)。

### JML表达式：

**原子表达式：**

1. 1. \result表达式：表示一个非 void 类型的方法执行所获得的结果，即方法执行后的返回值。\result表达式的类型就是方法声明中定义的返回值类型。
   2. \old( expr )表达式：用来表示一个表达式 expr 在相应方法执行前的取值。该表达式涉及到评估 expr 中的对象是否发生变化，遵从Java的引用规则，即针对一个对象引用而言，只能判断引用本身是否发生变化，而不能判断引用所指向的对象实体内容是否发生变化。
   3. \not_assigned(x,y,...)表达式：用来表示括号中的变量是否在方法执行过程中被赋值。如果没有被赋值，返回为 true ，否则返回 false 。实际上，该表达式主要用于后置条件的约束表示上，即限制一个方法的实现不能对列表中的变量进行赋值。
   4. \not_modiﬁed(x,y,...)表达式：与上面的\not_assigned表达式类似，该表达式限制括号中的变量在方法执行期间的取值未发生变化。
   5. \nonnullelements( container )表达式：表示 container 对象中存储的对象不会有 null 。
   6. \type(type)表达式：返回类型type对应的类型(Class)，如type( boolean )为Boolean.TYPE。TYPE是JML采用的缩略表示，等同于Java中的 java.lang.Class 。
   7. \typeof(expr)表达式：该表达式返回expr对应的准确类型。如\typeof( false )为Boolean.TYPE。

**量化表达式：**

1. 1. \forall表达式：全称量词修饰的表达式，表示对于给定范围内的元素，每个元素都满足相应的约束。 
   2. \exists表达式：存在量词修饰的表达式，表示对于给定范围内的元素，存在某个元素满足相应的约束。 
   3. \sum表达式：返回给定范围内的表达式的和。 
   4. \product表达式：返回给定范围内的表达式的连乘结果。
   5. \max表达式：返回给定范围内的表达式的最大值。
   6. \min表达式：返回给定范围内的表达式的最小值。
   7. \num_of表达式：返回指定变量中满足相应条件的取值个数。

**集合表达式：**

　　集合构造表达式：可以在JML规格中构造一个局部的集合（容器），明确集合中可以包含的元素。

**操作符：** 

1. 1. 子类型关系操作符： E1<:E2 ，如果类型E1是类型E2的子类型(sub type)，则该表达式的结果为真，否则为假。如果E1和E2是相同的类型，该表达式的结果也为真。
   2. 等价关系操作符： b_expr1<==>b_expr2 或者 b_expr1<=!=>b_expr2 ，其中b_expr1和b_expr2都是布尔表达 式，这两个表达式的意思是 b_expr1==b_expr2 或者 b_expr1!=b_expr2 。可以看出，这两个操作符和Java中的 == 和 != 具有相同的效果，按照JML语言定义， <==> 比 == 的优先级要低，同样 <=!=> 比 != 的优先级低。
   3. 推理操作符： b_expr1==>b_expr2 或者 b_expr2<==b_expr1 。对于表达式 b_expr1==>b_expr2 而言，当 b_expr1==false ，或者 b_expr1==true 且 b_expr2==true 时，整个表达式的值为 true 。
   4. 变量引用操作符：除了可以直接引用Java代码或者JML规格中定义的变量外，JML还提供了几个概括性的关键词来 引用相关的变量。\nothing指示一个空集；\everything指示一个全集，即包括当前作用域下能够访问到的所有变量。变量引用操作符经常在assignable句子中使用，如 assignable \nothing 表示当前作用域下每个变量都不可以在方法执行过程中被赋值。

### 方法规格：

- - 前置条件(pre-condition)：通过requires子句来表示： requires P;。其中requires是JML关键词，表达的意思是“要求调用者确保P为 真”。注意，方法规格中可以有多个requires子句，是并列关系，即调用者必须同时满足所有的并列子句要求。
  - 后置条件(post-condition) ：通过ensures子句来表示： ensures P;。其中ensures是JML关键词，表达的意思是“方法实现者确保方法执 返回结果一定满足谓词P的要求，即确保P为真”。同样，方法规格中可以有多个ensures子句，是并列关系，即方法实现者必须同时满足有所并列ensures子句的要求。
  - 副作用范围限定(side-eﬀects) ：副作用指方法在执行过程中会修改对象的属性数据或者类的静态成员数据，从而给后续方法的执行带来影响。从方法规格的角度，必须要明确给出副作用范围。JML提供了副作用约束子句，使用关键词 assignable 或者 modifiable 。
  - signals子句 ：signals子句的结构为 signals (Exception e) b_expr ，意思是当 b_expr 为 true 时，方法会抛出括号中给出的相应异常e。

## 应用工具链

　　**OpenJML**：一款提供 **JML** 语言检查的开源编译器，可以检查 **JML** 语法、根据 **JML** 对代码实现进行静态检查。

　　**JML** 编译器( **jmlc )**，是对 **Java** 编译器将带有 **JML** 规范注释的 **Java** 程序编译成 **Java** 字节码。编译的字节码包括检查的运行时断言检查指令。

　　文件产生器( **jmldoc** )，生成包含 **Javadoc** 注释和任何 **JML** 规范的 **HTML**。 这便于将 **JML** 规范公布在网上。

　　**JMLUnitNG**：一款根据 **JML** 自动构造样例的测试生成工具，用于进行单元化测试。由实践知生成的样例偏向于边界条件的测试。

 

# 三、JMLUnitNG/JMLUnit

　　首先按照讨论群伦佬的帖子测试简单的demo：

```
 1 // demo/Demo.java
 2 package demo;
 3 
 4 public class Demo {
 5     /*@ public normal_behaviour
 6       @ ensures \result == lhs - rhs;
 7     */
 8     public static int compare(int lhs, int rhs) {
 9         return lhs - rhs;
10     }
11 
12     public static void main(String[] args) {
13         compare(114514,1919810);
14     }
15 }
```

　　利用命令行操作：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522195141568-460078422.jpg)

　　可以看到 **JMLUnitNG** 自动测试选择了很多在临界值，并且检查一些空的测试。

　　根据尝试发现，**JMLUnitNG** 的确能够帮助我们提高程序的准确性，它选取很多容易出错的数据。

　　然而，个人觉得 **JMLUnitNG** 自动测试针对代码和规格的要求还是非常严格的，这反而提高了测试的复杂度。

 

# 四、三次作业的类图与度量分析

**度量分析标识：**

- - ev(G)　　本质复杂度
  - iv(G)　　 设计复杂度
  - v(G)　　 循环复杂度
  - OCavg　 平均方法复杂度
  - OSavg　 平均方法语句数（规模）
  - WMC　　加权方法复杂度
  - v(G)avg　平均循环复杂度
  - v(G)tot　总循环复杂度

 

## 第九次作业：

### 1、架构构思：

　　第一次作业主要就是通过继承 **Path** 、 **PathContainer** 两个官方提供的接口，来实现自己的 **Path** 、 **PathContainer** 容器。

　　这部分按照官方给定接口的 **JML** 规格来实现即可。

　　需要注意的是 **Path** 中采用两个HashMap：

```
private HashMap<Integer, Integer> nodeList = new HashMap<>();
private HashMap<Integer, Integer> distinctList = new HashMap<>();
```

　　distinctList能帮助我们完成 **containsNode()** 和 **PathContainer** 的 **getDistinctNodeCount()。**

　　另外要注意 **equals()** 可以先判断 **hash** 值是否相等，相等再进行比较。

　　同样的，**PathContainer** 采用三个HashMap：

```
private HashMap<Integer, MyPath> plist;
private HashMap<MyPath, Integer> pidlist;
private HashMap<Integer, Integer> distinctNode;
```

　　**plist** 和 **pidlist** 双向存储，同时满足我们查找 **ID** 和 **Path** 的需求。

 

### 2、项目分析：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163217246-964858752.jpg)

度量分析：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163333311-2111496509.png)

 

 ![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163359937-1819421451.png)

 

### 3、自我总结：

　　这次作业的复杂度问题主要就是 **MyPath.compareTo()** 和 **Mypath.equals()**，这两个方法的 **ev(G)** 都在 **Metrics** 中出现了飘红。

　　然而这个问题也是没有办法的，**equals()** 中我们可以先检查 **hash** 是否相等，如果相等再进行循环检查。

　　而 **compareTo()** 则只能一股脑的检查下去，判断大小。

　　所以循环是没有办法避免的。

　　而 **MyPathContainer.addPath()** 的 **v(G)** 虽然没有飘红，但是复杂度最高的原因在于，我将 **getDistinctNodeCount()** 分摊到了每一次 **add** 和 **remove** ：

```
1 for (Integer pathId : path) {
2     if (distinctNode.containsKey(pathId)) {
3         distinctNode.put(pathId, distinctNode.get(pathId) + 1);
4     } else {
5         distinctNode.put(pathId, 1);
6     }
7 }        
```

　　其他方法的复杂度都得到了有效的控制。

 

### 4、程序bug分析：

　　　　这次作业比较简单，我的中测提交主要是不断简化程序，降低算法复杂度。我是严格按照 **JML** 规格编写的，所以并没有出现 **bug**。

 

### 5、性能分析：

　　一方面我将 **getDistinctNodeCount()** 这一耗时利器分摊到了每一次 **add** 和 **remove** ，减少时间消耗。

　　另一方面我从讨论区中张万聪大佬的帖子中受教，根据id和路径双向索引，使用 **HashMap** 双容器，一个是 **HashMap<Integer, Path>** ，一个是 **HashMap<Path, Integer>**，增删时同时考虑双方，查找就可以根据不同索引进行选择，保证了性能。

 

### 6、互测分析：

　　互测方面我主要检查了大家的 **compareTo()** 和 **equals()**，然后采用了大数据集测试程序的性能，虽然发现有写的比较复杂的，但本次作业的 **CPU** 时间给的很宽松，所以并没有查出 **bug**。

 

## 第十次作业：

### 1、架构构思：

　　本次作业的架构概况起来就是“大胆继承，小心重构”。

　　因为 **Graph** 接口是继承了 **PathContainer** 接口的，所以我的 **MyGraph** 直接继承了上一次作业的 **MyPathContainer** 。

　　从指令上来看，上一次作业主要是涉及点结构，而第二次作业的重点在边结构。在上一次作业中，我将 **getDistinctNodeCount()** 这一耗时利器分摊到了每一次 **add** 和 **remove** ，那么针对这一次作业，我需要重写 **addPath()** 、**removePath()** 和 **removePathById()** ，继续将每一次的增加和删除操作分摊下去。

　　同时设置 **isModify** 变量作为图结构的更新的标志。

　　而针对新的指令扩展，因为“本次由路径构成的图结构，在任何时候，总节点个数（不同的节点个数，即 **DISTINCT_NODE_COUNT** 的结果），均不得超过250”，所以我采用静态数组，将节点离散化，把它们映射到[1,250]。

　　针对最短路径，使用 **Bfs** 算法，采用 **cache** 机制，将每次计算目标结点之间距离时经过的中间结点的距离都保存下来，如果 **isModify** 被置 **true**，则清空 **cache**，重新计算。

 

### 2、项目分析：

UML类图：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522162521545-855819656.jpg)

度量分析：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522162711220-385987765.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522162800678-1718918262.png)

 

### 3、自我总结：

　　这次作业中的飘红主要是 **graph.MyPath.compareTo()、graph.MyPath.equals()、graph.MyGraph.bfs()** 和 **graph.MyGraph.cache()**，其中 **graph.MyPath.compareTo()** 和 **graph.MyPath.equals()** 在上次作业中已经分析过了，并没有办法降低 **ev(G)。**

　　**bfs()** 属于标准算法，而 **cache()** 中牵扯到一个图的重构，这两个方法我在第十一次作业中进行了再一次重构，将复杂度降低了下去。在本次作业中的 **cache()** ，我是这样写的：

```
 1 for (int i = 0; i < super.getLength(); i++) {
 2         for (int j = 0; j < super.getLength(); j++) {
 3             if (i == j) {
 4                 renewGraph[i][j] = 0;
 5         } else if (graph[i][j] == 0) {
 6                 renewGraph[i][j] = inf;
 7         } else {
 8                 renewGraph[i][j] = 1;
 9         }
10     }
11 }        
```

然而实际上只需要修改一下 **bfs()** ，在需要的时候判断一下，就能省去这个 **O(n2)** 的方法。当时可能是脑子轴了，没有想到。

 

### 4、程序bug分析：

这次作业我的强测爆的极其惨烈，原因在意“小心重构”的“小心”二字我没有做到。

**bug** 出现在 **MyPathContainer.removePath()** 和 **MyPathContainer.removePathById()。**

为了将图的更新均摊到每一次 **remove** 操作，最初我是这样写的：

```
 1 if (distinctNode.get(pathId) == 1) {
 2     distinctNode.remove(pathId);
 3     removeList.add(removeId);
 4     mapping.remove(pathId);
 5 
 6     for (int i = 0; i < length; i++) {
 7         if (graph[removeId][i] != 0) {
 8             graph[removeId][i] = 0;
 9             graph[i][removeId] = 0;
10         }
11     }
12 } else {
13     distinctNode.put(pathId, distinctNode.get(pathId) - 1);
14         if (graph[removeId][prevId] != 0 && removeId != prevId) {
15             graph[removeId][prevId] = 0;
16             graph[prevId][removeId] = 0;
17     }
18 }
```

　　每一次删除操作，我都将这个 **Path** 中的节点的所有边全部删除，如此错误的写法我当时居然没发现，实属不应该。

　　因为这个头昏的写法，我的强测炸的妈都找不着了。

 

### 5、性能分析：

　　性能方面，**MyGraph** 继承了上一次作业的 **MyPathContainer** ，相关方法的复杂度得到有效控制。

　　而本次作业相关的图操作，除了我上文提到的 **cache()** 的简化问题，**bfs** 最多跑 20×n 次，每次复杂度O(V + E)，所以复杂度完全可以接受。

 

### 6、互测分析：

　　本次互测我采用的仍然是大数据集压力测试，检查同屋的复杂度问题和正确性问题，虽然我本地查出来了很多 **bug**，然而让我非常苦恼的是，我的数据一直交不上去，也不知道到底是哪里出问题了，非常难受，所幸助教大大在下一次互测中加入了互测样例错误提示。

 

## 第十一次作业：

### 1、架构构思：

　　终于来到了本单元最后一次作业，我首先看了指导书的提示：

本系列作业中

- 每一次的功能是单调递增的，而且具备一定的继承性。
- 所以建议大家采用继承的方式，将每一次的逻辑进行独立封装，以提高程序的工程性、可维护性。

本次作业中

- 请求数看似很多，实际上写指令很少，图结构变更指令更少。
- 这毫无疑问意味着需要充分优化各类读指令。
- 此外，由于图结构相对静态，所以可以考虑以下的策略
  - 将时间复杂度分散到本就无法降低复杂度的写指令以及线性复杂度指令中。
  - 将之前计算出来的部分中间结果保存下来，以减少后续的计算复杂度。
  - 一言以蔽之，应当使用类似缓存的思想，将大量的计算任务按照一定的策略进行均摊，以减少重复劳动。
- 此外，由于本次涉及到大量图相关的计算，并且很多逻辑实际上很类似
  - 所以建议将图相关的计算进行单独的开类封装，并进行单独维护。
  - 同理，上文所说的缓存系统，也最好和图一样，进行单独维护（或者就以图的形态存在，不过还是建议尽可能降低耦合）。

　　所以针对本次作业的 **MyRailwaySystem**， 采取的仍然是继承的思想，继承上一次作业的 **MyGraph**，同时重构 **MyPathContainer** 和 **MyGraph** 中的部分方法，以适宜新的需求。

　　另外，我构架了针对不同需求的 **graph**：**ShortGraph**、**TransGraph**、**PriceGraph** 和 **UnpleasantGraph**，它们的架构都有很多相似的地方，所以都继承自同一个父类 **RailGraph**。虽然这样做显得有些臃肿和复杂，但我在设计时对我自身而言比较清楚，而且易于查找 **bug**。

　　对于图的构建，我主要采用了讨论区中拆点的方法，赋予不同功能的图不同的权值，以达到计算的需求。

```
public static final int trans = 121;
public static final int price = 2;
public static final int Us = 32;
```

 

### 2、项目分析：

UML类图：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163915668-1796502032.jpg)

度量分析：

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163626658-1622564159.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163715145-1480417070.png)

 

### 3、自我总结：

　　这次作业中的飘红主要是 **graph.RailGraph.dijkstra()、graph.RailGraph.getValue()、graph.ShortGraph.getBlockCount()、graph.ShortGraph.getLength()、railwaysystem.MyPath.compareTo()** 和 **railwaysystem.MyPath.equals()。**

　　除去老生常谈的 **compareTo()** 和 **equals()**，其他的方法也可以进行一个分类：

　　**graph.ShortGraph.getLength()** 是上次作业的 **graph.MyGraph.bfs()** 的一个修改，核心还是 **bfs** 算法。

　　**graph.ShortGraph.getBlockCount()** 是采用并查集的思想，利用 **bfs** 算法进行连通块的计算。

　　**graph.RailGraph.getValue()** 则是调用 **graph.RailGraph.dijkstra()** 进行满足各种需求的不同权值的带权路径的计算。

 

### 4、程序bug分析：

　　本次的强测依然炸的非常惨烈，**bug** 全都是 CTLE，所以我在性能分析中具体阐述。

 

### 5、性能分析：

　　在 **bug** 修复中我仔细分析了我的程序，发现了如下若干性能问题：

　　对于 **src/graph/ShortGraph.java** ，我简化了 **rebuild** 方法，即将

```
 1 for (int i = 0; i < size; i++) {
 2     for (int j = 0; j < size; j++) {
 3         if (i == j) {
 4             graph[i][j] = 0;
 5         } else if (undigraph.getEdge(i, j) == 0) {
 6             graph[i][j] = inf;
 7         } else {
 8             graph[i][j] = 1;
 9         }
10     }
11 }
```

　　舍弃，直接

```
graph = undigraph.getGraph();
```

　　简化程序，节约时间。同时，我将原来使用的 **Floyd** 算法更改为了 **bfs** 算法。

　　对于 **src/graph/RailGraph.java**，我主要是更换了数据结构。

　　即将 **int[][] graph，**更换为 **ArrayList<arraylist<pair<integer, integer>>> edgeList。**

　　同时增加缓存机制 **HashMap<integer, hashmap<integer,integer>> resultMap**。

　　另外与 **src/graph/ShortGraph.java** 相似，我将我将原来使用的 **Floyd** 算法更改为了 **dijkstra** 算法。

 

### 6、互测分析：

　　又是一场腥风血雨。

　　我的互测策略就是简易评测机对拍 + 自己构造超大数据集。

　　同屋的 **bug** 还是很多的，重点集中在 **LEAST_TRANSFER_COUNT** 和 CTLE。

 

# 五、心得体会

　　规格能够避免自然语言的二义性，它更加严谨，功能描述更加确切。它既帮助自己实现要求的功能，同时帮助他人理解该段代码的作用。

　　可以说，规格是为了团队协作、大型软件开发、严格功能定义项目所设计的。规格的重要性并不在于如何达到最好的设计效果，最好的性能要求，而是在于最正确的写法，最明确的功能定义。在开发时，可以用来确保不会因为不同的团队对于模块的功能和需求理解错误，导致开发过程出现问题。

　　在实际使用和撰写规格后，我在感受到规格的优势时，也发现了撰写规格的难度。规格的撰写实际上是比编写代码更加艰难的过程。一方面在于撰写者需要确切理解需求，覆盖所有情况；另一方面当功能非常复杂时，规格也会非常复杂，这使得规格反而难以理解，这时候反而应该以自然语言为主，规格为辅

　　长远看来，规格化编程是一项非常重要的能力，它在现今程序越来越复杂、参与人员越来越多、各种开源项目层出不穷的时代，它的需求会越来越大，所以我们必须提高自己对规格的理解程度。

### 

# UML



# 一、三次作业简单介绍

### 第十三次作业：

实现一个 **UML** 类图解析器 **UmlInteraction**

本次作业最终需要实现一个UML类图解析器，可以通过输入各种指令来进行类图有关信息的查询。

实现指令：

| **模型中一共有多少个类**   | **CLASS_COUNT**                                     |
| -------------------------- | --------------------------------------------------- |
| **类中的操作有多少个**     | **CLASS_OPERATION_COUNT classname mode**            |
| **类中的属性有多少个**     | **CLASS_ATTR_COUNT classname mode**                 |
| **类有几个关联**           | **CLASS_ASSO_COUNT classname**                      |
| **类的关联的对端是哪些类** | **CLASS_ASSO_CLASS_LIST classname**                 |
| **类的操作可见性**         | **CLASS_OPERATION_VISIBILITY classname methodname** |
| **类的属性可见性**         | **CLASS_ATTR_VISIBILITY classname attrname**        |
| **类的顶级父类**           | **CLASS_TOP_BASE classname**                        |
| **类实现的全部接口**       | **CLASS_IMPLEMENT_INTERFACE_LIST classname**        |
| **类是否违背信息隐藏原则** | **CLASS_INFO_HIDDEN classname**                     |

### 第十四次作业：

在上次作业基础上，扩展解析器，使得能够支持对 **UML** 顺序图和 **UML** 状态图的解析，并能够支持几个基本规则的验证。

实现指令：

| **给定状态机模型中一共有多少个状态**                         | **STATE_COUNT statemachine_name**                        |
| ------------------------------------------------------------ | -------------------------------------------------------- |
| **给定状态机模型中一共有多少个迁移**                         | **TRANSITION_COUNT statemachine_name**                   |
| **给定状态机模型和其中的一个状态，有多少个不同的后继状态**   | **SUBSEQUENT_STATE_COUNT statemachine_name statename**   |
| **给定UML顺序图，一共有多少个参与对象**                      | **PTCP_OBJ_COUNT umlinteraction_name**                   |
| **给定UML顺序图，一共有多少个交互消息**                      | **MESSAGE_COUNT umlinteraction_name**                    |
| **给定UML顺序图和参与对象，有多少个incoming消息**            | **INCOMING_MSG_COUNT umlinteraction_name lifeline_name** |
| **R001：针对下面给定的模型元素容器，不能含有重名的成员(UML002)** |                                                          |
| **R002：不能有循环继承(UML008)**                             |                                                          |
| **R003：任何一个类或接口不能重复继承另外一个接口(UML009)**   |                                                          |

### 关键点 1 —— UML 规则：

准确理解 **UML** 规则，然后使用 **Java** 来实现相应的接口。

### 关键点 2 —— 架构设计：

　　第一次作业和第二次作业紧密相关，要考虑好可扩展性。



# 二、两次次作业的类图与度量分析

**度量分析标识：**

- - ev(G)　　本质复杂度
  - iv(G)　　 设计复杂度
  - v(G)　　 循环复杂度
  - OCavg　 平均方法复杂度
  - OSavg　 平均方法语句数（规模）
  - WMC　　加权方法复杂度
  - v(G)avg　平均循环复杂度
  - v(G)tot　总循环复杂度

 

## 第十三次作业：

### 1、架构构思：
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623145226205-1283509523.jpg)



　　第一次作业主要是继承 **UmlInteraction** 接口，实现相应的方法。

　　我的设计是首先在 **MyUmlInteraction** 进行构造，读取所有的元素，然后在 **Find** 里用 **HashMap** 进行存储， **MyAssociation** 、 **MyInterfaceRealition** 和 **MyGeneralization** 等直接查找好相应的类和接口，直接保存，方便查找。

　　同时在 **Cahce** 里设置缓存，记录重复信息和每次查询后结果的存储，保证不做重复查找，提高效率。
**Realiton** 负责继承、接口实现、关联这些关系的存储。

　　然后 **MyUmlInteraction** 中的每条指令都归类到相应的结构中，互不影响，例如：

| **getClassOperationCount**      | Find.getClassByName(className).getOperationCount(queryType)** |
| ------------------------------- | ------------------------------------------------------------ |
| **getClassAttributeCount**      | **Find.getClassByName(className).getAttributeSize()**        |
| **getClassOperationVisibility** | **Find.getClassByName(className).getOperationVisibility(         operationName)** |
| **getTopParentClass**           | **Find.getClassByName(className).getTopFatherName()**        |

　　其中 **Find.getClassByName(className)** 得到是 **MyClass** ，在这个类中包含相应的属性：

```java
private UmlClass umlClass;
private HashMap<String, UmlAttribute> attributeNameMap;   
private HashMap<String, MyOperation> operationIdMap;    
private HashMap<OperationQueryType, Integer> operaCountMap;
private HashMap<String, HashMap<Visibility, Integer>> operaVisiMap;
private LinkedList<MyClass> fatherList;
private LinkedList<AttributeClassInformation> attributeInformationList;
private HashSet<String> interfaceIdSet;
private int assoicationCount;
private HashSet<String> assoicationIdSet;
```

			类似的， **MyInterface** 和 **MyOperation** 也是一样。

### 2、项目分析：

类图分析：
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623145644462-2104590107.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623150345998-194133097.jpg)


度量分析：

![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623145614767-95729154.png)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623150431055-1343824520.png)



### 3、自我总结：

　　从 **Metrics** 的分析来看，这次作业的复杂度飘红是在 **MyUmlInteraction.buildMap** 和 **MyAssociation.add** 。
　　**MyUmlInteraction.buildMap** 是构造函数的一部分，我用了一个 **for** 和 **switch** 结构进行分类，提高了复杂度。


```java
for (UmlElement uml : elements) {
    Find.addId(uml);
    switch (uml.getElementType()) {
        case UML_CLASS:
            Find.addClass(uml);
            break;
        case UML_INTERFACE:
            Find.addInterfaceId(uml);
            break;
        case UML_GENERALIZATION:
            generalizationList.add((UmlGeneralization) uml);
            break;
        case UML_INTERFACE_REALIZATION:
            Relation.addInterfaceRealition(uml);
            break;
        case UML_OPERATION:
            operationMap.put(uml.getId(), new MyOperation(uml));
            break;
        case UML_ATTRIBUTE:
            attributeList.add((UmlAttribute) uml);
            break;
        case UML_PARAMETER:
            parameterList.add((UmlParameter) uml);
            break;
        case UML_ASSOCIATION:
            associationList.add((UmlAssociation) uml);
            break;
        default:
            break;
    }
}
```

　　而 **MyAssociation.add** 是因为我分别判断了两个 **End** 相同和不同的情况，每个情况又去添加相应的元素。


```java
if (!contains(id1)) {
            associationEnd.put(id1, new HashSet<>());
        }
        associationEnd.get(id1).add(Find.getById(a.getEnd2()));
        if (!contains(id2)) {
            associationEnd.put(id2, new HashSet<>());
        }
        associationEnd.get(id2).add(Find.getById(a.getEnd1()));
        if (id1.equals(id2)) {
            if (Find.containsClassId(id1)) {
                if (!containsClass(id1)) {
                    associationClass.put(id1, new HashSet<>());
                }
                associationClass.get(id1).add(id1);
            }
        } else {
            if (Find.containsClassId(id1) && Find.containsClassId(id2)) {
                if (!containsClass(id1)) {
                    associationClass.put(id1, new HashSet<>());
                }
                associationClass.get(id1).add(id2);
                if (!containsClass(id2)) {
                    associationClass.put(id2, new HashSet<>());
                }
                associationClass.get(id2).add(id1);
            }
        }
```


　　其他方法的复杂度都得到了有效的控制。

 

### 4、程序 **bug** 分析：

　　这次出现了一个 **bug**，是因为我在存储 **class** 的时候，直接将同名的 **class** 覆盖了，导致当查询子类继承父类的属性时，查找到的那个类不是它真正的父类，后来我改为用 **id** 查找就消除了这个 **bug**。




## 第十四次作业：

### 1、架构构思：
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623150721447-1619784900.jpg)



　　本次作业是在上次作业的基础上进行扩展，**UmlGeneralInteraction** 接口继承了四个接口，分别是 **UmlClassModelInteraction, UmlStandardPreCheck, UmlCollaborationInteraction, UmlStateChartInteraction**。我将它们分别继承，实现各种的功能后再进行整合，其中：
　　**UmlClassModelInteraction** 放入 **classmodel**包中，该包即上次作业的结构。
　　**UmlStandardPreCheck**也放入 **classmodel**，并为它设置相应的 **function**，即 **UmlRule**。
　　**UmlCollaborationInteraction** 和 **UmlStateChartInteraction** 分别放入新包 **collaboration** 和 **statechart** 中，并与上次作业相同，将方法归到各种的结构中，直接调用相应结构的方法。

 

### 2、项目分析：

类图分析：
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151247400-1879971382.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151256379-1840459385.jpg)



度量分析：
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151358467-561628753.png)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151430011-65506831.png)

 

### 3、自我总结：

　　这次作业中的飘红主要是 **UmlRule.checkClass**、**UmlRule.checkCycle**、**MyUmlGeneralInteraction.classify**，其中 **MyUmlGeneralInteraction.classify** 就是上次的 **for** 和 **switch** 结构。
　　**UmlRule.checkClass** 是被 **UmlRule.checkCycle** 调用的，他们复制的是 **R002：不能有循环继承(UML008)**：


```java
public static void checkCycle(boolean isFirst, String fatherId, String id) {
    if (!isFirst) {
        if (fatherId.equals(id)) {
            addUml008(fatherId);
            return;
        } else {
            visitedSet.add(id);
        }
    }

    if (Find.containsClassId(id) && Relation.hasGeneralization(id)) {
        String nextId = Relation.getClassFather(id).getId();
        if (!visitedSet.contains(nextId)) {
            checkCycle(false, fatherId, nextId);
        }
    }
    if (Find.containsInterfaceId(id) && Relation.hasGeneralization(id)) {
        for (String nextId : Relation.getInterfaceFather(id)) {
            if (!visitedSet.contains(nextId)) {
                checkCycle(false, fatherId, nextId);
            }
        }
    }
}
```


```java
private static boolean checkClass(String id, HashSet<String> idSet) {
        if (idSet.contains(id) || containsUml009Id(id)) {
            return true;
        } else {
            idSet.add(id);
        }
        if (Relation.hasGeneralization(id)) {
            for (String tempId : Relation.getInterfaceFather(id)) {
                if (idSet.contains(tempId) || containsUml009Id(id)) {
                    return true;
                } else {
                    idSet.add(tempId);
                }
            }
        }
        return false;
    }
```



### 4、程序 bug 分析：
　　这次的 **bug** 我非常难受，我在实现 **R001：针对下面给定的模型元素容器，不能含有重名的成员(UML002)** 时，在检查关联是，忘记了首先判断该类是否有关联，即：

```java
if (Relation.hasAssoication(getId())) {
    HashSet<UmlElement> set = Relation.getAssociationEndById(getId());
    for (UmlElement e : set) {
        if (attributeNameMap.containsKey(e.getName())) {
            UmlRule.addUml002(e.getName(), getName());
        }
    }
}
```
　　这个方法我之前忘加了前面的 **if**，导致强测非常惨烈，实在难受。




# 三、架构设计及OO方法理解的演进  
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154218621-1774751753.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154237975-1803744591.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154244116-453316002.jpg)

　　第一单元是我第一次接触面向对象程序设计，也是这个学期的起步，我各方面的认识都很不到尾，还是有着很深的面向过程编程的影子。
　　这其中经历了一次讨论课，一次优秀代码的展示后，我接触到了很多大佬的思维方式，开始深刻理解静态方法的使用，继承和接口实现的使用，对建类和方法设计的思考，慢慢摸索到一些方向。
　　所以在第三次作业中将这些技巧都尝试了一下，包括 **Error** 类的静态方法归类， **factor**包中抽象类的实现和继承，虽然架构设计的问题还是有很多，但相比前两次作业已经好了不少。


![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154250360-27301278.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154257061-477590277.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154305721-2086920893.jpg)

　　第二单元是我这个学期面临困难最大的一个单元，即对多线程的理解。
　　这个单元的前两次作业都还算平缓，但第三次作业直接让我的架构血崩，前两次作业的可扩展性极差，让我的第三次作业不断修修补补，在屎山上不断补坑，极其难受。
　　这个单元让我认识到，我对架构设计的理解还有非常大的问题，程序的可扩展性是一个非常重要的问题，我们需要在这一次作业的设计时为未来的变化留下足够便利的扩展空间，同时这也是架构本身的问题，一个优秀的架构应该没有极强的耦合，让我们可以方便的修改。


![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154311052-884369429.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154321606-2098451150.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154331067-382570228.jpg)

　　第三单元是我这个学期失分最严重的一个单一，第二次作业仅仅得了10分。
　　这个单元我吸取了前两个单元的教训，做好了可扩展性，下一次作业都继承了上一次作业的类。
　　但是我却没做好测试，导致一些隐蔽的 **bug** 没有被查出来，强测惨不忍睹。
　　无论哪次作业，测试一定是重中之重，否则只能自己哭泣。

![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154338764-1574958685.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623154344719-2069580870.jpg)

　　第四单元是我这个学期的最后一个单元，我自我认为我的架构设计相比开学已经有了很大的进步，虽然还是存在很多问题。
　　这个单元的每个类、每个方法都做到了精简与专一，保证每个类、每个方法只做自己该做的事情。同时在第二次作业的扩展时，不需要对第一次的需求做出改变，只需要不断添加新方法，保证了扩展的便利性。
　　然而，测试依然不够全面，如同我在前面说的，因为一个 **if** 的缺失，让我的强测再次血崩。测试的充分性不仅仅是数据的量和复杂程度，更要考虑所有的情况。


# 四、四个单元中测试理解与实践的演进
　　第一单元，因为程序本身没有太多技巧，前两次作业主要靠肉眼找 **bug**，瞄准事故多发地段正则表达式猛烈攻击。第三次作业使用自动评测机，不断暴力生成。这个单元前两次作业我没有出现 **bug**，第三次作业因为程序比较庞大，一些地方没有顾忌到，导致强测出现了问题。
　　第二单元，因为引入多线程，调试难度加大，加上数据不好生成，我几乎没有做额外测试。我选择的方法是用这一次的程序提交到上一次作业的 **bug** 修复，不断回滚。这样依然有很多问题，比如第三次作业仍然需要肉眼判断，同时难以找出 **bug**。这个单元我没有出现 **bug**，但是我第三次作业爆了一半的 **CTLE**，这是架构不合理导致的无法轻易修复的问题，只能重构。
　　第三单元，是我本学期 **bug** 最多的一个单元。这个单元的完成其实不难，但是也有可能是因为这样，我忽视了大面积的测试，导致一些隐蔽的 **bug** 没有发现。
　　第四单元，我在强测前做了很多测试，却只重视了量，没有重视质，忽略了一些边界条件，导致同样出现了一些小 **bug**，然而这些小 **bug** 却造成了非常大的影响，一行代码影响全局，非常难受。

　　四个单元以来，从肉眼 **debug**，到搭建自动评测机评测，再到 **JUnit**、自己手动构造边界条件，这些都让我认识到测试的重要性。测试不全面，亲人两行泪。80%的bug隐藏在20%的代码中；80%的时间用在测试计划、测试设计、测试实现上，20%的时间用于测试执行上；80%的bug通过静态测试发现，20%的bug通过动态测试发现；80%的bug通过人工测试发现，20%的bug通过自动化测试发现。这些二八定律真实的反应了具体的情况。


# 五、课程收获
　　总的来说，这学期非常的累，但是收获也很大。
　　一方面的收获是成长，作为一名大学才接触编程的学生，我需要学习的地方太多太多。从毫无 **java** 编程基础的小白、无法理解面向对象程序设计的新手，到基本能完成大部分要求，整个过程非常困难，回顾头看看又很有感触。
　　另一方面的收获是教训，无数次 **bug** 的出现和 **bug** 的修复让我深刻认识到：编程不仔细，亲人两行泪。架构不合理，亲人两行泪。测试不充分，亲人两行泪。偶然间的一个 **bug** 毁所有，一行代码毁人生的感觉，非常糟糕，非常难忘。
　　这个学期，我收获了如下几点：
　　基本熟练掌握了 **java**语言的语法和特性；
　　初步学习了多线程编程，虽然这块我的理解还不到位，但我会继续深入学习的；
　　学习了面向对象程序设计的思维逻辑；
　　认识到架构设计的重要性和一些技巧方法；
　　了解了 **JML** 规格化编程；
　　了解了 **UML** 建模设计；
　　深刻理解了测试的重要性，学习到很多测试的方法。


# 六、给课程提三个具体改进建议
　　首先，本学期课程组对 **OO** 课程的改进是巨大的，课程体验要好于往年，在此真诚地感谢各位助教。
　　我的建议如下：
　　1、提升中测难度和分数占比或构造测试集池、提供运行时间测试：
　　这学期的中测更像是弱测，测试点都比较简单。我明白课程组可能有些想让我们自己做更充分测试的意思，但是一方面我们才接触面向对象的设计，个人能力有限，另一方面很多作业的测试不那么好构造，我们自己构造的难度比较大，还有就是关于 **CTLE** 的测试本地和评测机差距比较大。希望明年课程中能提高中测难度和分数占比或构造测试集池、提供运行时间测试。让我们能更好地找出自己的 **bug**，不因为一些小问题而程序爆炸。
　　2、修改 **Project** 的难度递进，保证指导书质量：
　　部分 **Project** 的难度递进不太合理，第一次、第二次、第三次的跳跃较大。建议要么缩小差距，提升第一次作业的难度，降低第三次作业的难度。同时合理规划项目时间，尽量避免指导书的修改，可以在本次作业的互测阶段提前开发下一次作业的指导书，让我们有充足的时间去分析指导书的问题，避免指导书的反复修改和我们的理解不清，同时能够减轻因难度增加带来的时间紧张问题。
　　3、每单元作业结束后及时开放标程和优秀作业：
　　即便通过了三次作业，还是存在很多的问题，还需要大量的学习。从标程和大佬的作业中，能找到很多值得我们学习的地方。有个激进的建议是每次作业结束后发布标程，让我们不断学习，但这样也会造成一些问题，希望课程中能够思考的更加全面吧。
　　4、理论课：
　　希望理论课内容能够更加具体一些，有些地方比较空泛，学完仍然没有什么感觉，同时希望给出更多的例子，帮助学习，单独的讲解感触不是特别深。
　　5、实验课：
　　还是希望实验课能够在双周，同时希望能够减小实验课分数占比，让我们有更多的机会去学习更多的东西，增加我们的试错机会。