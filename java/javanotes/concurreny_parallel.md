# Concurrency & Parallel 


## Concurrency

[Difference](https://medium.com/erens-tech-book/%E7%90%86%E8%A7%A3-process-thread-94a40721b492)  
[luminousmen](https://luminousmen.com/post/concurrency-and-parallelism-are-different)


**Concurrency** is the execution of more than one task is being processed **in OVERLAPPING time periods**.  


An important detail is **that tasks are not necessarily performed at the same time(but it's possible)**. **That is based on the notion of Interruptability — tasks can be divided into smaller and alternating subtasks.**  
In this case, they can be executed simultaneously, but this is not necessary.
Subtasks are not connected with each other. Therefore, it does not matter which of them will end earlier, and which later. Thus, concurrency can be realized in many ways — using green threads or processes or asynchronous operations that work on one CPU or something else.

![圖 1](/java/images/afe91940375130cff2de823ca2fd5498d27e8112523fb0ce18da5564ae38d21d.png)  

For example  : **the secretary answers phone calls and sometimes checks for appointments**.  He needs to stop answering the phone to go to the desk and check the appointments, and then start answering and repeating the process before the end of the workday.

As you have noticed, concurrency is more connected with logistics. If it were not, then the secretary would wait until the time of the appointment and do the necessary things and then go to the ringing phone.

## Parallelism

**Parallelism** is literally the **simultaneous execution of tasks**.  

![圖 2](/java/images/de48f921b2f5c32c4f270e4e9dac8cb1c64b498c0298050655056fe38bd3feb0.png)  

Parallelism is one of the ways to implement concurrent execution highlighting abstraction of a thread or process.  
**Also for parallelism to be true, there must be at least two computational resources.**

**Parallelism is a subclass of concurrency** — before performing several concurrent tasks, you must first organize them correctly.