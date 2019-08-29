# Fork/Join解析

## 功能

从JDK1.7开始，Java提供Fork/Join框架用于并行执行任务，它的思想就是讲一个大任务分割成若干小任务，最终汇总每个小任务的结果得到这个大任务的结果。

这种思想和MapReduce很像（input --> split --> map --> reduce --> output）

主要有两步：

- 第一、任务切分；
- 第二、结果合并

它的模型大致是这样的：线程池中的每个线程都有自己的工作队列（PS：这一点和ThreadPoolExecutor不同，ThreadPoolExecutor是所有线程公用一个工作队列，所有线程都从这个工作队列中取任务），当自己队列中的任务都完成以后，会从其它线程的工作队列中偷一个任务执行，这样可以充分利用资源。

## ForkJoinTask

ForkJoinTask代表运行在ForkJoinPool中的任务。

主要方法：

- fork()    在当前线程运行的线程池中安排一个异步执行。简单的理解就是再创建一个子任务。
- join()    当任务完成的时候返回计算结果。
- invoke()    开始执行任务，如果必要，等待计算完成。

子类：

- RecursiveAction    一个递归无结果的ForkJoinTask（没有返回值）
- RecursiveTask    一个递归有结果的ForkJoinTask（有返回值）