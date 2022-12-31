# Java Base64

[encoding-as-base64-in-java](https://stackoverflow.com/questions/13109588/encoding-as-base64-in-java)

Since Java 8
```java 
import java.util.Base64;
```

The basic encoder keeps things simple and encodes the input as is, without any line separation.

**The output is mapped to a set of characters in (`ABCDEFG...Z`, `abcdefg...z`, `012345..89`, `+`,`/`) character set, and the decoder rejects any character outside of this set.**


Encode into base64 via `getEncoder()`
```java 
String originalInput = "test input";
String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
```

To decode an encoded base64 string  via `getDecoder()`
```java
byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
// convert byte[] to string 
String decodedString = new String(decodedBytes);
```

## Java 8 Base64 Encoding with Padding
[More Details](https://stackoverflow.com/questions/4080988/why-does-base64-encoding-require-padding-if-the-input-length-is-not-divisible-by/18518605#18518605)

**Padding characters help satisfy length requirements and carry no meaning.**
1. Padding allows us to decode base64 encoding with the promise of no lost bits. 
2. Without padding there is no longer the explicit acknowledgement of measuring in three byte bundles. 
3. Without padding you may not be able to guarantee exact reproduction of original encoding without additional information usually from somewhere else in your stack, like TCP, checksums, or other methods.

If we need to skip the padding of the output — perhaps because the resulting String will never be decoded back 
we can simply choose to encode without padding:
```java 
String encodedString = 
  Base64.getEncoder().withoutPadding().encodeToString(originalInput.getBytes());
```
## Java 8 URL Encoding

**It uses the URL and Filename Safe Base64 alphabet (no `/`, `-`, `+`, `_`) and does not add any line separation:**
```java
String originalUrl = "https://www.google.co.nz/?gfe_rd=cr&ei=dzbFV&gws_rd=ssl#q=java";
String encodedUrl = Base64.getUrlEncoder().encodeToString(originalURL.getBytes());
```

Decoding happens in much the same way. 
but with `getUrlDecoder()` instead of `getDecoder()`
```java 
byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedUrl);
String decodedUrl = new String(decodedBytes);
```

## Java 8 MIME Encoding

Generate MIME via `UUID.randomUUID().toString()`
```java 
private static StringBuilder getMimeBuffer() {
    StringBuilder buffer = new StringBuilder();
    for (int count = 0; count < 10; ++count) {
        buffer.append(UUID.randomUUID().toString());
    }
    return buffer;
}
```
- The MIME encoder generates a Base64-encoded output using the basic alphabet but in a MIME-friendly format.

Each line of the output is no longer than 76 characters and ends with a carriage return followed by a linefeed `(\r\n)`:
```java
StringBuilder buffer = getMimeBuffer();
byte[] encodedAsBytes = buffer.toString().getBytes();
String encodedMime = Base64.getMimeEncoder().encodeToString(encodedAsBytes);
```
- The `getMimeDecoder()` utility method returns a `java.util.Base64.Decoder` that is then used in the decoding process:
```java
byte[] decodedBytes = Base64.getMimeDecoder().decode(encodedMime);

String decodedMime = new String(decodedBytes);
```

## Encoding/Decoding Using Apache Commons Code

First, we need to define the commons-codec dependency in the pom.xml:
```http
<dependency>
    <groupId>commons-codec</groupId>
    <artifactId>commons-codec</artifactId>
    <version> TO_SELECT_VERSION_YOU_WANT </version>
</dependency>
```

The main API is the org.apache.commons.codec.binary.Base64 class, which can be parameterized with various constructors:
```java
Base64(boolean urlSafe) //creates the Base64 API by controlling the URL-safe mode — on or off.
Base64(int lineLength) //creates the Base64 API in an URL-unsafe mode and controls the length of the line (default is 76).
Base64(int lineLength, byte[] lineSeparator) //creates the Base64 API by accepting an extra line separator, which by default is CRLF (“\r\n”).
```

Once the Base64 API is created, both encoding and decoding are quite simple:
```java
String originalInput = "test input";
Base64 base64 = new Base64();
String encodedString = new String(base64.encode(originalInput.getBytes()));
```

The decode() method of Base64 class returns the decoded string:
```java
String decodedString = new String(base64.decode(encodedString.getBytes()));
```


Another simple option is using the static API of Base64 instead of creating an instance:
```java
String originalInput = "test input";
String encodedString = new String(Base64.encodeBase64(originalInput.getBytes()));
String decodedString = new String(Base64.decodeBase64(encodedString.getBytes()));
```

## Converting a String to a byte Array
Sometimes, we need to convert a String to a `byte[]`. 
```java
String originalInput = "test input";
byte[] result = originalInput.getBytes();

assertEquals(originalInput.length(), result.length);
```

It's better to provide encoding as well and not depend on default encoding, as it's system dependent:
```java
String originalInput = "test input";
byte[] result = originalInput.getBytes(StandardCharsets.UTF_16);

assertTrue(originalInput.length() < result.length);
```

If our String is Base64 encoded, we can use the Base64 decoder:
```java
// base64 encoded in string form
String originalInput = "dGVzdCBpbnB1dA==";
byte[] result = Base64.getDecoder().decode(originalInput);
assertEquals("test input", new String(result));
```

### DatatypeConverter

We can also use DatatypeConverter `parseBase64Binary()` method:
```java
String originalInput = "dGVzdCBpbnB1dA==";
byte[] result = DatatypeConverter.parseBase64Binary(originalInput);
assertEquals("test input", new String(result));
```

convert a hexadecimal String to a `byte[]` using DatatypeConverter method:
```java
String originalInput = "7465737420696E707574";
byte[] result = DatatypeConverter.parseHexBinary(originalInput);
assertEquals("test input", new String(result));
```


