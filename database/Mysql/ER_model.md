# Concept ER model 
![](https://i.imgur.com/C5k7vxS.png)   

## Entity   

### Strong Entity  

A strong entity set is an entity set that contains sufficient attributes to uniquely identify all its entities.  

For example  
> movie  

### Weak Entity   
You need more further information (other Entity) to identify this Entity   

In ER diagram, weak entity set is always present in total participation with the identifying relationship set.   
![](https://i.imgur.com/p0eumRz.png)   


For example   
![](https://i.imgur.com/fg38HPG.png)   
1. First, building number is required to identify the particular building.  
2. Secondly, door number of the apartment is required to uniquely identify the apartment.  


### Simple Attribute   
an Atomic values, which cannot be divided further.  
![](https://i.imgur.com/fST4NZy.png)  

For example
```vim   
Released  
Title  
MovieId  
```

### Composition Attribute   
  
`Attribute X = Attribute Y + Attribute Z`

```vim
Address  = Post Code + Sate  
```

### Key Attribute
Attribute with the uniqueness   
![](https://i.imgur.com/9OTTIpT.png)   

```vim
MoveId
```  
  
### Multi-Valued Attribute  
Multi valued attributes are those attributes which can take more than one value for a given entity from an entity set.   

For example   
> Genres   

### Derived Attribute  

Derived attributes are those attributes which can be derived from other attribute(s).   
For example    
> Age can be derived from Genres    

## Relationship

> Symbol of Related with   
> ![](https://i.imgur.com/wJwhMs8.png)   


So ...   
```diff
Student <-related with-> Course  
Teacher <-related with-> Course  
```
They can be presented as with the illustrator as the following   
![](https://i.imgur.com/tDvne0E.png)    


## Schema 

The Description For the Structure of a table   
![](https://i.imgur.com/VhumQxn.png)   


## Instance

Tuple/Instance/Record Inside A Schema   

## Build A ER Model

To Define the following ONE BY ONE   
1. **Create** Entities
2. The **Relationship** of Each Entity
3. The **Attributes** of Each Entity
4. The **(Primary)Key** of Each Entity
5. **Cardinality** BTW Each Entity


## Convert ER-Model into Table   

Tip : F.K (Duplicates) $\rightarrow$ P.K (Unique)

#### One To One   
![](https://i.imgur.com/PLLPf8z.png)  
![](https://i.imgur.com/TDl8EtI.png)  
 
#### One To Many  
![](https://i.imgur.com/xBUX8ge.png)  
![](https://i.imgur.com/3PcAfQS.png)  
 
#### Many To Many   
![](https://i.imgur.com/XLUsWvk.png)   
![](https://i.imgur.com/hKzGqvg.png)  
