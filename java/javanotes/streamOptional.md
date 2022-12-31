# Optional & Stream

- [How to convert an Optional<T> into a Stream<T>?](https://stackoverflow.com/questions/33942614/how-to-convert-an-optionalt-into-a-streamt)

## Reference
- [Optional 與 Stream 的 flatMap](https://openhome.cc/Gossip/Java/FlatMap.html)
- [Using Optional with Streams in Java](https://stackoverflow.com/questions/24254209/using-optional-with-streams-in-java)

## Optional to Stream

```java
// JAVA 8
Optional<String> optional = Optional.of("Hello");
Stream<String> texts = optional.map(Stream::of).orElseGet(Stream::Empty);

// JAVA 9
List<String> filteredList = opList.stream().flatMap(Optional::stream)
                                           .collect(Collectors.toList());
```

## Collection to Optional to Stream

```java
final List<String> flintstones = new ArrayList<String>(){{
    add("Fred");
    add("Wilma");
    add("Pebbles");
}};

final List<String> another = Optional.ofNullable(flintstones)
        .map(Stream::of)
        .orElseGet(Stream::empty)
        .toList();
```


## flatMap vs Map
```java
public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent())
        return empty();
    else {
        return Objects.requireNonNull(mapper.apply(value));
    }
}

String addr = "n.a.";
Optional<Customer> customer = order.getCustomer();
if(customer.isPresent()) {
    Optional<String> address = customer.get().getAddress();
    if(address.isPresent()) {
        addr = address.get();
    }
return addr;
// same as 
return order.getCustomer()
            .flatMap(Customer::getAddress)
            .flatMap(Address::getCity)
            .orElse("n.a.");


List<String> itemNames = new ArrayList<>();
for(Order order : orders) {
    for(LineItem lineItem : order.getLineItems()) {
        itemNames.add(lineItem.getName());
    }
}
// same as 
List<String> itemNames = orders.stream()
                .flatMap(order -> order.getLineItems().stream())
                .map(LineItem::getName)
                .collect(toList());
```

Unlike flatMap method map returns `offNullable` type;
```java
public<U> Optional<U> map(Function<? super T, ? extends U> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent())
        return empty();
    else {
        return Optional.ofNullable(mapper.apply(value));
    }
}

return Optional.ofNullable(order)
               .map(Order::getCustomer) // might return null
               .map(Customer::getAddress) // might return null
               .map(Address::getCity) // might return null
               .orElse("n.a.");
```

