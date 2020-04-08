# 1 多项式函数求导

## 1.1 三次作业简单介绍

**第一次作业：** 简单多项式导函数的求解，仅包含常数与幂函数。

**第二次作业：** 包含简单幂函数和简单正余弦函数的导函数的求解，在第一次作业的基础上，增加了sin(x)、cos(x)和导数的四则运算。

**第三次作业：** 包含简单幂函数和简单正余弦函数的导函数的求解，在第二次作业的基础上，增加了复合函数的求导。

## 1.2 三次作业的类图与度量分析

### 1.2.1 第一次作业

#### 1.2.1.1 架构构思

第一次作业是我第一次接触面向对象的程序设计，没有经验，再加上这次作业并不是非常需要“面向对象”，我的设计也比较偏向面向过程。

这次的难点就在于读入字符串的合法性审查和多项式分割存储，我专门设计了一个 ***StringChecker*** 类用于完成这项功能。由于所有的项都可以写成 **axb** 的形式，为了简化程序，我仅仅设计了一个 **Poly** 类，并将读入的字符串将 +- 变为 +-<space>，用<space>进行存储。最后是一个主类用于完成多项式的主体求导和输出功能。

#### 1.2.1.2 项目分析

##### 类图

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324215121288-862780243.jpg)

##### 度量分析

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324215151229-303972841.jpg)

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324215201727-233631802.jpg)

#### 1.2.1.3 自我总结

可以非常明显的发现，主类的***Cyclomatic Complexity***非常非常高，重点就在于 ***output*** 方法的设计问题，导致其***Cyclomatic Complexity*** 和 ***Design Complexity*** 很高。

一方面，我应该将主类设计的越简单越好，将 ***parsePoly***、***computePoly*** 和 ***output*** 方法的实现扔出去新建类；另一方面，我应该重写 ***Poly*** 类的 ***toString*** 方法，简化程序的输出，降低复杂度。

除此之外，我的***StringChecker*** 类设计的是有些丑陋的，其中包含了很多格式特判，这主要是因为我对正则表达式的使用熟练度不足。

#### 1.2.1.4 程序bug分析

非常幸运的是，我的程序在强测环节和互测环节并没有被查出bug，但在历次提交和我的自我测试中，我在不断修复的时候明显感觉到了自己的设计问题。主要问题件就是前文提到的***output*** 方法和***StringChecker*** 类的编写。

***StringChecker*** 类中的合法性检查方法，我的正则表达式都是一个个小正则，这样的好处是避免了爆栈问题。但是由于我在初次设计时没有考虑完善，所以是在一个不断修补的状态中的，并不是非常流畅。

在 ***output*** 方法的实现过程中，因为我为了缩短输出长度，它的循环复杂度很高，我在输出时有很多错误，debug的时候很困难，这也是自己的设计问题，应该结合***Poly*** 类的 ***toString*** 方法，这样的好处不仅仅在于降低复杂度，还简化了debug的难度。

#### 1.2.1.5 性能分析

我在这次作业中使用的是 ***ArrayList*** 存储多项式，使得我在合并同类项时还需要进一步的处理，使得程序复杂。同时，我没有考虑将 + 提到最前的问题，使得很多题性能分没有拿满。

应该使用 ***HashMap*** 存储多项式，自动合并同类项，在输出时按照系数排序，由大到小输出。

### 1.2.2 第二次作业

#### 1.2.2.1 架构构思

第二次作业相比第一次，面向对象的特性有所增强。我在课上刚公布题目的时候，就想到了每一项都能写成  ***kxasinb(x)cosc(x)*** 这种形式，写成这种形式的好处是几乎与第一次作业在架构方面相差不大，便于处理。坏处就是我们都猜到了第三次作业肯定是复合函数，然而这种形式可扩展性几乎为零，无法适应后面的任务。

所以我在设计之初，构造了 **Factor** 抽象类，并通过继承得到 ***Cons***、***Power***、***Sin***、***Cos*** 类，增强可扩展性。然而，想的总是比做得好，由于自身水平问题，我在求导上卡了非常长的时间，最终无奈放弃，转回最初的设计方法。

有了第一次作业的教训，我将 ***(a,b,c)*** 设计成 ***HashMap*** 的 **key** ，将 ***k*** 设计成 **HashMap** 的 ***value***。重写***hashCode*** 和 **equals** 方法，完成 ***Term*** 的设计。并且听取了研讨课上大佬们的意见，将 **Error** 写成静态方法，不断调用。拆分 ***PolyChecker*** 类的检查方法，分而治之。主类大大简化，只负责类的创建与方法的调用。余下的事情就非常简单了。

#### 1.2.2.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324230832559-1393648472.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324230846311-975517476.jpg)

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324232332428-1954018270.jpg)

#### 1.2.2.3 自我总结

可能是因为还是不熟悉面向对象，加上中途换道的影响，这次的分析结果非常差劲。大量方法的***Essentail Complexity***、***Cyclomatic Complexity***  和 ***Design Complexity*** 都有很多问题。**Term** 类和 ***Poly*** 类也由于构造和合并同类型的原因循环复杂度极高。

虽然这次我采取“能扔就扔”的策略，让每个类不那么臃肿，但是部分方法的循环问题却是我之前没有想过的。大量的循环导致了方法的复杂，同时也大大增加了出错的可能和debug的难度。

非常羞耻的是，这次的输出我考虑到了 ***toString*** 方法的重要性，但在具体实现时为了偷懒使用了打表的方法，使得 ***print*** 方法非常丑陋。

#### 1.2.2.4 程序bug分析

同样非常幸运，我的程序在强测环节和互测环节并没有被查出bug。

在历次提交和我的自我测试中，这次作业的出错总体来说还是比较少的。原因主要在于这次的 ***PolyChecker*** 结合了上次互测屋中其他同学的优秀设计经验，分治效果非常明显。另外，虽然 ***print*** 方法很丑陋，但是丑的好处是考虑全面，避免了输出的bug。

再加上我的化简方法非常捡漏，使得化简的bug非常容易显露，便于将bug扼杀在摇篮里。

#### 1.2.2.5 性能分析

这次作业除了使用 ***HashMap*** ，正项提前外，化简方法只考虑了非常简单的 **sin2(x)+cos2(x)=1** 这一最简单的形式，使得我的性能分几乎全部损失。

我在讨论区中也看到了很多大佬思路的分享，但在当时感到很难实现，加上自己容易写成bug，最终放弃。

### 1.2.3 第三次作业

#### 1.2.3.1 架构构思

构思过程简单来说，就是四个字——一脸懵逼。第二次作业的构思过程已经让我品尝过一次失败，这次依然有着非常多的困难去让我克服。

这次我仍然选择了构造 **Factor** 抽象类，并通过继承得到 ***Cons***、***Power***、***Sin***、***Cos*** 类，有了前两次的经验，我在这些因子的内部就重写了 ***toString*** 方法和 ***clone*** 方法，降低输出的复杂度；按照统一的结构，给每个因子设计专有的 ***diff*** 方法，为之后的求导做准备。同时 ***PolyChecker*** 类也延续了第二次的模式，进行改进。

然后，就没有然后了，我一直卡壳。

幸运的是，我的朋友一直非常无私的帮助着我，为我解答疑惑。我按照讨论区提到的构造树状结构求导，回顾了大一下的数据结构内容，采用中缀转后缀进而构造树结构，完成了 ***Poly*** 的设计。

接下来，求导和输出就是一个通过树不断递归的过程。在输出中，我感受到前两次作业中化简的一些不便，这次并没有采用直接输出的方法，而是利用 ***StringBuilder*** 做一个缓冲，减轻输出判断的负担。

#### 1.2.3.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324235525708-1442409840.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324235540680-286434890.jpg)

![img](https://img2018.cnblogs.com/blog/1425766/201903/1425766-20190324235656225-1489591099.jpg)

#### 1.2.3.3 自我总结

这次设计我划分的很细致，3个包，14个类，使得类图比较庞大。

由于字符串的解析、中缀转后缀和树的构建中的的循环分支判断（***while***、***switch***），求导和输出的递归，使得这次设计相关类的循环复杂度依然非常高。

但是因为这次作业的难度很高，我自己已经是拼命完成的，所以我对复杂度的优化感觉并不是非常有头绪，还是希望能够看到更多大佬的设计和建议。

#### 1.2.3.4 程序bug分析

这次就没那么幸运了，我在强测中爆了3个点，互测中被hack了7次。

在中测中，由于前文提到的复杂度过高，我的debug可以说是非常非常困难。需要不断的尝试、尝试、再尝试，过程非常的痛苦，最终才能找到bug，而bug果然也是在循环复杂度最高的中缀转后缀和树的构建这两部分内容中。这种复杂度过高的情况是我之后必须要避免的，无论是bug出现的可能性还是debug的难度都是非常高的，很影响代码质量。

强测和互测这一共10个样例错误，最终发现是一个问题：我在中缀转后缀的过程中，由于是按运算符判断，对于 ***+\\d** 和 ****-\\d*** 这两种情况就会存在误判，解决办法是在中缀转后缀前，将 ******* 替换为 ****#*** ，避免这种误判。

#### 1.2.3.5 性能分析

这次作业能活下来就已经很艰难了，自动放弃性能分。

# 2 多线程电梯

## 2.1 三次作业简单介绍

**第一次作业：**

单部多线程傻瓜调度（FAFS）电梯的模拟

**第二次作业：**

单部多线程可捎带调度（ALS）电梯的模拟

**第三次作业：**

多部多线程智能（SS）调度电梯的模拟

**关键点 1** **—— ALS(可捎带电梯)规则：**

可捎带电梯调度器将会新增主请求和被捎带请求两个概念。
主请求选择规则：

- 如果电梯中没有乘客，将请求队列中到达时间最早的请求作为主请求
- 如果电梯中有乘客，将其中到达时间最早的乘客请求作为主请求

被捎带请求选择规则：

电梯的主请求存在，即主请求到该请求进入电梯时尚未完成
该请求到达请求队列的时间**小于等于**电梯到达该请求出发楼层关门的截止时间 电梯的运行方向和该请求的目标方向一致。即电梯主请求的目标楼层和被捎带请求的目标楼层，两者在当前楼层的同一侧。

其他： 

标准ALS电梯不会连续开关门。即开门关门一次之后，如果请求队列中还有请求，不能立即再执行开关门操作，会先执行请求。

**关键点 2** **—— 多线程编程：**

这是我第一次接触多线程编程。多线程就是多个线程一起去协同完成一个任务，通过充分去共享资源来达到提升效率的一种编程思想。

在本单元的作业中，线程安全是一个贯穿全程的问题，这部分内容处理的还算好，但对我困扰极大的是CPU时间超时问题，我对wait()、notify()机制的使用还是有很大的不熟练。

**关键点 3 —— 输入输出接口：**

本单元作业的输入输出使用的都是课程组提供的统一接口，避免了对输入输出做大量处理的麻烦。这在第一单元的作业中是让我们非常痛苦的一个地方，是bug高发地带也是优化难题之一。

## 2.2 三次作业的类图与度量分析

**度量分析标识：**

- ev(G)　　本质复杂度
- iv(G)　　 设计复杂度
- v(G)　　  循环复杂度
- OCavg　 平均方法复杂度
- OSavg　 平均方法语句数（规模）
- WMC　　加权方法复杂度
- v(G)avg　平均循环复杂度
- v(G)tot　总循环复杂度

### 2.2.1 第五次作业

#### 2.2.1.1 架构构思

总体架构我是按照指导书的建议走的

- 主线程进行输入的管理，使用ElevatorInput，负责接收请求并存入队列
- 开一个线程，用于模拟电梯的活动，负责从队列中取出请求并进行执行，执行完后继续取继续执行
- 构建一个队列，用于管理请求，且需要保证队列是线程安全的

*Main*类中的main方法负责创建电梯线程，同时作为输入线程，管理输入，接受请求并存入队列，线程结束标志是读到null。

*ElevatorThread*类是电梯线程，按照FIFO策略的傻瓜电梯，每次搭载一名乘客，完成任务后再搭载另一名乘客，线程结束标志是读到null同时队列为空。

*Request*类包含三个属性id、from和to，在输入接口的三个get方法（getFromFloor()、getToFloor()、getPersonId()）的帮助下新建对象。同时利用IDEA自动生成三个属性的get方法（这步在完成之后才发现其实有点多余，可以直接使用输入接口里的*PersonRequest*）。

*RequestQueue*类作为请求队列，用于管理请求，以ArrayList为基础，保证线程安全，并配上所需的方法。

#### 2.2.1.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423202156207-1152329080.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222952523-208569164.png)

 

 ![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222800882-2009757395.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222718131-1230994859.png)

 

#### 2.2.1.3 自我总结

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

#### 2.2.1.4 程序bug分析

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

#### 2.2.1.5 性能分析

这次作业我采用的调度策略是完全按照FAFS调度策略走的，按照请求进入系统的顺序，依次按顺序逐个执行运送任务，不过这次作业不存在性能分，虽然效率很低，但更多是让我们熟悉熟悉多线程，为后面的作业做准备。

### 2.2.2 第六次作业

#### 2.2.2.1 架构构思

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

#### 2.2.2.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423202140342-1121566130.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423223251593-533983788.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423223141699-1125713520.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423223048635-106501191.png)

 

#### 2.2.2.3 自我总结

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

### 2.2.2.4 程序bug分析

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

### 2.2.2.5 性能分析

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

### 2.2.3 第七次作业

#### 2.2.3.1 架构构思

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

#### 2.2.3.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423202113387-135343409.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222351189-1756099186.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222457807-267496299.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201904/1425766-20190423222552771-381841044.png)

#### 2.2.3.3 自我总结

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

#### 2.2.3.4 程序bug分析

如果说前两次作业都比较顺利的话，这次作业就是惊险刺激+大型翻车现场。

这次作业，我的正确性没有问题，问题集中在CPU时间上。我的中测一直在出现 CPU_TIME_LIMIT_EXCEED ，花费了非常多的时间去修复，同时强测和互测也出现了很多的 CPU_TIME_LIMIT_EXCEED，可以说我这次作业中线程之间的通讯与轮转存在着很大的问题。

我在强测公布后寻找了几位大佬学习架构，这次的超时问题主要还是架构的锅，所以我也是从上周就开始重构代码，想完完全全将整个电梯写好。

### 2.2.3.5 性能分析

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

# 3 JML

## 3.1 三次作业简单介绍

**第九次作业：**

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

**第十次作业：**

实现容器类 **Path** 和数据结构类 **Graph 。**

本次作业最终需要实现一个无向图系统。

实现指令：

| **容器中是否存在某个结点** | **CONTAINS_NODE 结点id**                       |
| -------------------------- | ---------------------------------------------- |
| **容器中是否存在某一条边** | **CONTAINS_EDGE 起点结点id 终点结点id**        |
| **两个结点是否连通**       | **IS_NODE_CONNECTED 起点结点id 终点结点id**    |
| **两个结点之间的最短路径** | **SHORTEST_PATH_LENGTH 起点结点id 终点结点id** |

**第十一次作业：**

实现容器类 **Path** 和地铁系统类 **RailwaySystem。**

本次作业最终需要实现一个简单地铁系统。

实现指令：

| **整个图中的连通块数量**       | **CONNECTED_BLOCK_COUNT**                        |
| ------------------------------ | ------------------------------------------------ |
| **两个结点之间的最低票价**     | **LEAST_TICKET_PRICE 起点结点id 终点结点id**     |
| **两个结点之间的最少换乘次数** | **LEAST_TRANSFER_COUNT 起点结点id 终点结点id**   |
| **两个结点之间的最少不满意度** | **LEAST_UNPLEASANT_VALUE 起点结点id 终点结点id** |

**关键点 1 —— JML 规格：**

准确理解 **JML** 规格，然后使用 **Java** 来实现相应的接口，并保证代码实现严格符合对应的 **JML** 规格。

**关键点 2 —— 架构设计：**

本单元作业容器类的设计贯穿全程，且后一次作业需要使用前一次作业的容器类，同时还要继承前一次作业的容器类，需要仔细规划架构。

**关键点 3 —— Junit 单元测试：**

通过编写单元测试类和方法，来实现对类和方法实现正确性的快速检查和测试。还可以查看测试覆盖率以及具体覆盖范围（精确到语句级别），全面无死角的进行程序功能测试。

## 3.2 应用工具链

**OpenJML**：一款提供 **JML** 语言检查的开源编译器，可以检查 **JML** 语法、根据 **JML** 对代码实现进行静态检查。

**JML** 编译器( **jmlc )**，是对 **Java** 编译器将带有 **JML** 规范注释的 **Java** 程序编译成 **Java** 字节码。编译的字节码包括检查的运行时断言检查指令。

文件产生器( **jmldoc** )，生成包含 **Javadoc** 注释和任何 **JML** 规范的 **HTML**。 这便于将 **JML** 规范公布在网上。

**JMLUnitNG**：一款根据 **JML** 自动构造样例的测试生成工具，用于进行单元化测试。由实践知生成的样例偏向于边界条件的测试。

## 3.3 三次作业的类图与度量分析

**度量分析标识：**

- ev(G)　　本质复杂度
- iv(G)　　 设计复杂度
- v(G)　　 循环复杂度
- OCavg　 平均方法复杂度
- OSavg　 平均方法语句数（规模）
- WMC　　加权方法复杂度
- v(G)avg　平均循环复杂度
- v(G)tot　总循环复杂度

## 3.3.1 第九次作业

#### 3.3.1.1 架构构思

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

#### 3.3.1.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163217246-964858752.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163333311-2111496509.png)

 

 ![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163359937-1819421451.png)

#### 3.3.1.3 自我总结

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

#### 3.3.1.4 程序bug分析

这次作业比较简单，我的中测提交主要是不断简化程序，降低算法复杂度。我是严格按照 **JML** 规格编写的，所以并没有出现 **bug**。

#### 3.3.1.5 性能分析

一方面我将 **getDistinctNodeCount()** 这一耗时利器分摊到了每一次 **add** 和 **remove** ，减少时间消耗。

另一方面我从讨论区中张万聪大佬的帖子中受教，根据id和路径双向索引，使用 **HashMap** 双容器，一个是 **HashMap<Integer, Path>** ，一个是 **HashMap<Path, Integer>**，增删时同时考虑双方，查找就可以根据不同索引进行选择，保证了性能。

### 3.3.2 第十次作业

#### 3.3.2.1 架构构思

本次作业的架构概况起来就是“大胆继承，小心重构”。

因为 **Graph** 接口是继承了 **PathContainer** 接口的，所以我的 **MyGraph** 直接继承了上一次作业的 **MyPathContainer** 。

从指令上来看，上一次作业主要是涉及点结构，而第二次作业的重点在边结构。在上一次作业中，我将 **getDistinctNodeCount()** 这一耗时利器分摊到了每一次 **add** 和 **remove** ，那么针对这一次作业，我需要重写 **addPath()** 、**removePath()** 和 **removePathById()** ，继续将每一次的增加和删除操作分摊下去。

同时设置 **isModify** 变量作为图结构的更新的标志。

而针对新的指令扩展，因为“本次由路径构成的图结构，在任何时候，总节点个数（不同的节点个数，即 **DISTINCT_NODE_COUNT** 的结果），均不得超过250”，所以我采用静态数组，将节点离散化，把它们映射到[1,250]。

针对最短路径，使用 **Bfs** 算法，采用 **cache** 机制，将每次计算目标结点之间距离时经过的中间结点的距离都保存下来，如果 **isModify** 被置 **true**，则清空 **cache**，重新计算。

#### 3.3.2.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522162521545-855819656.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522162711220-385987765.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522162800678-1718918262.png)

### 3.3.2.3 自我总结

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

#### 3.3.2.4 程序bug分析

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

#### 3.3.2.5 性能分析

性能方面，**MyGraph** 继承了上一次作业的 **MyPathContainer** ，相关方法的复杂度得到有效控制。

而本次作业相关的图操作，除了我上文提到的 **cache()** 的简化问题，**bfs** 最多跑 20×n 次，每次复杂度O(V + E)，所以复杂度完全可以接受。

### 3.3.3 第十一次作业

#### 3.3.3.1 架构构思

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

### 3.3.3.2 项目分析

**类图分析**

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163915668-1796502032.jpg)

**度量分析**

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163626658-1622564159.png)

 

![img](https://img2018.cnblogs.com/blog/1425766/201905/1425766-20190522163715145-1480417070.png)

#### 3.3.3.3 自我总结

这次作业中的飘红主要是 **graph.RailGraph.dijkstra()、graph.RailGraph.getValue()、graph.ShortGraph.getBlockCount()、graph.ShortGraph.getLength()、railwaysystem.MyPath.compareTo()** 和 **railwaysystem.MyPath.equals()。**

除去老生常谈的 **compareTo()** 和 **equals()**，其他的方法也可以进行一个分类：

**graph.ShortGraph.getLength()** 是上次作业的 **graph.MyGraph.bfs()** 的一个修改，核心还是 **bfs** 算法。

**graph.ShortGraph.getBlockCount()** 是采用并查集的思想，利用 **bfs** 算法进行连通块的计算。

**graph.RailGraph.getValue()** 则是调用 **graph.RailGraph.dijkstra()** 进行满足各种需求的不同权值的带权路径的计算。

#### 3.3.3.4 程序bug分析

本次的强测依然炸的非常惨烈，**bug** 全都是 CTLE，所以我在性能分析中具体阐述。

#### 3.3.3.5 性能分析

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

# 4 UML

# 4.1 三次作业简单介绍

**第十三次作业：**

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

**第十四次作业：**

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

**关键点 1 —— UML 规则：**

准确理解 **UML** 规则，然后使用 **Java** 来实现相应的接口。

**关键点 2 —— 架构设计：**

第一次作业和第二次作业紧密相关，要考虑好可扩展性。

# 4.2 两次次作业的类图与度量分析

**度量分析标识：**

- ev(G)　　本质复杂度
- iv(G)　　 设计复杂度
- v(G)　　 循环复杂度
- OCavg　 平均方法复杂度
- OSavg　 平均方法语句数（规模）
- WMC　　加权方法复杂度
- v(G)avg　平均循环复杂度
- v(G)tot　总循环复杂度

### 4.2.1 第十三次作业

#### 4.2.1.1 架构构思

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

#### 4.2.1.2 项目分析

**类图分析**
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623145644462-2104590107.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623150345998-194133097.jpg)

**度量分析**

![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623145614767-95729154.png)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623150431055-1343824520.png)

#### 4.2.1.3 自我总结：

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

#### 4.2.1.4 程序 **bug** 分析

这次出现了一个 **bug**，是因为我在存储 **class** 的时候，直接将同名的 **class** 覆盖了，导致当查询子类继承父类的属性时，查找到的那个类不是它真正的父类，后来我改为用 **id** 查找就消除了这个 **bug**。

### 4.2.2 第十四次作业

#### 4.2.2.1 架构构思

![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623150721447-1619784900.jpg)

本次作业是在上次作业的基础上进行扩展，**UmlGeneralInteraction** 接口继承了四个接口，分别是 **UmlClassModelInteraction, UmlStandardPreCheck, UmlCollaborationInteraction, UmlStateChartInteraction**。我将它们分别继承，实现各种的功能后再进行整合，其中：

**UmlClassModelInteraction** 放入 **classmodel**包中，该包即上次作业的结构。

**UmlStandardPreCheck**也放入 **classmodel**，并为它设置相应的 **function**，即 **UmlRule**。

**UmlCollaborationInteraction** 和 **UmlStateChartInteraction** 分别放入新包 **collaboration** 和 **statechart** 中，并与上次作业相同，将方法归到各种的结构中，直接调用相应结构的方法。

#### 4.2.2.2 项目分析

**类图分析**
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151247400-1879971382.jpg)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151256379-1840459385.jpg)



**度量分析**
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151358467-561628753.png)
![](https://img2018.cnblogs.com/blog/1425766/201906/1425766-20190623151430011-65506831.png)

#### 4.2.2.3 自我总结

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

#### 4.2.2.4 程序 bug 分析

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
