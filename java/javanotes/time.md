# Java Time

[Java Time](https://stackoverflow.com/questions/32437550/whats-the-difference-between-instant-and-localdatetime)  
[Old and New Time API in Java](https://programminghints.com/2017/05/still-using-java-util-date-dont/)   
[Api_examples](https://kucw.github.io/blog/2020/6/java-date/)    

![image](https://user-images.githubusercontent.com/68631186/131538484-b93fb226-97c2-4570-99bb-d76a96bf897e.png)   


New time API core Ideal    
- Immutable-value classes. (THREAD SAFE)
- Domain-driven design. The new API models its domain very precisely with classes that represent different use cases for Date and Time closely. 
- Separation of chronologies. The new API allows people to work with different “non-ISO-8601” calendaring systems, like one used in Japan or Thailand.

New Time APIs   
- `Clock` provides access to the current instant, date and time using a time-zone.
- `LocaleDate` holds only the date part without a time-zone in the ISO-8601 calendar system.
- `LocaleTime` holds only the time part without time-zone in the ISO-8601 calendar system.
- The `LocalDateTime` combines together LocaleDate and LocalTime and holds a date with time but without a time-zone in the ISO-8601 calendar system.
- `ZonedDateTime` holds a date with time and with a time-zone in the ISO-8601 calendar system.
- `Duration` class represents an amount of time in terms of seconds and nanoseconds. It makes very easy to compute the different between two time values. Period, on the other hand, performs a date based comparison between two dates.

![image](https://user-images.githubusercontent.com/68631186/131539344-1200efe7-1113-4755-8543-60ec01d70fa4.png)  

