###### tags: `Design Pattern`
# Command
[Java version](https://github.com/CyC2018/CS-Notes/blob/master/notes/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%20-%20%E5%91%BD%E4%BB%A4.md)   
[Illuwater](https://github.com/iluwatar/java-design-patterns/tree/master/command)    

- [Command](#command)
  - [`Invoker` combines multiple commands into one](#invoker-combines-multiple-commands-into-one)
  - [Typescript](#typescript)

The `invoker` assigns the `command`s to the `receiver` a certain request to operate a certain task, e.g. Pass a button on a remote(`invoker`) to turn off(`command`) a lamp(`receiver`)
```java
invokerRemote.assignTurnOff(new commandTurnOff());
invokerRemote.executeTheCommand();

receiverLamp.actionTurnOff();
```


![](https://i.imgur.com/j3u2Wol.png)

```cpp
/** 
 * command Interface 
 **/
class command
{
    virtual void execute()=0;
}

/**
 * Concrete Command
 */
class command_1 : public command
{
private:
    receiver r;
public:
    
    void execute(){
        r.action_1();
    }
}
class command_2 : public command
{
private:
    receiver r;
public:
    void execute(){
        r.action_2();
    }
}


/** 
 * Who invokes/gives the COMMANDS
 */
class invoker 
{
private:
    command_1 _1;
    command_2 _2;
public:
    /**
     * Invokeaction_x
     *   Invoke an Action
     *   For Sepecific Command 
     *   for specific Receiver (Production)
     */
     
    void Invokeaction_1(){
        _1.execute();
    }
    void Invokeaction_2(){
        _2.execute();
    }
}

/**
  * Target/Receiver for Commands from Receiver 
  * it receives the commands and do operations
  */
class receiver
{
public:

    /**
     * <p> actions are called by
     *     concret command </p>
     * <p> Image the following methods as 
     *     Function of the Productions </p>
     */ 
     
    action_1(){
        // To do something for specific command ...
    }
    action_2(){
    }
    //.. other actions for the device ...
}
```



## `Invoker` combines multiple commands into one

```cpp
class Invoker
{
private:
    vector<unique_ptr<command>> ONcommands;
    vector<unique_ptr<command>> OFFcommands;
public:
    Invoker(int slots){
        // slots : total num of the buttons
        ONcommands.resize(slots,nullptr);
        OFFcommands.resize(slots,nullptr);
    }
    void setCommand(int slot, 
                    unique_ptr<command>>& ONcommands,
                    unique_ptr<command>>& OFFcommands){
                        onCommands[slot] = Oncommands;
                        offCommands[slot] = OFFcommands;
    }

    void onButtonWasPush(int slot){
        onCommands[slot].execute();
    }
    void offButtonWasPush(int slot){
        OFFCommands[slot].execute();
    }
    
    //.. other operations
}
```



## Typescript

```typescript
/**
 * The Command interface declares a method for executing a command.
 */
interface Command {
    execute(): void;
}

class SimpleCommand implements Command {
    private payload: string;

    constructor(payload: string) {
        this.payload = payload;
    }

    public execute(): void {
        console.log(`SimpleCommand: (${this.payload})`);
    }
}

/**
 * However, some commands can delegate more complex operations to other objects,
 * called "receivers."
 */
class ComplexCommand implements Command {

    constructor(private receiver: ReceiverTV, 
                private a: string, 
                private b: string) {
    }

    /**
     * Commands can delegate to any methods of a receiver.
     */
    public execute(): void {
        console.log('ComplexCommand: Complex stuff should be done by a receiver object.');
        this.receiver.doSomething(this.a);
        this.receiver.doSomethingElse(this.b);
    }
}

/**
 * The Receiver classes contain some important business logic. 
 * They know how to perform all kinds of operations, 
 * associated with carrying out a request. 
 */
class ReceiverTV {
    public doSomething(a: string): void {
        console.log(`Receiver (TV): Working on (${a}.)`);
    }

    public doSomethingElse(b: string): void {
        console.log(`Receiver (TV): Also working on (${b}.)`);
    }
}

/**
 * The Invoker is associated with one or several commands.
 * It sends a request to the command.
 */
class Invoker {
    private _onStart!: Command;
    private _onFinish!: Command;

    setonStart(command: Command) {
        this._onStart = command;
    }

    setonFinish(command: Command) {
        this._onFinish = command;
    }

    
    /**
     * The Invoker does not depend on concrete command or receiver classes. The
     * Invoker passes a request to a receiver indirectly, by executing a
     * command.
     */
    public doSomethingImportant(): void {

        if (this.isCommand(this._onStart)) {
            this._onStart.execute();
        }

        if (this.isCommand(this._onFinish)) {
            this._onFinish.execute();
        }
    }

    private isCommand(object : Command) : object is Command {
        return (object as Command).execute !== undefined;
    }
}

/**
 * The client code can parameterize an invoker with any commands.
 */
const invoker = new Invoker();
invoker.setonStart(new SimpleCommand('turn on'));

const receiver = new ReceiverTV();
invoker.setonFinish(new ComplexCommand(receiver, 'turn the vloume up', 'adjust brightness'));

invoker.doSomethingImportant();
```