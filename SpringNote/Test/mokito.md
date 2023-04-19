# Mockito

- [Mockito](#mockito)
  - [Junit 5](#junit-5)

## Junit 5

```java
@DisplayName("_logs Of name_")
public class MyFirstTestCaseTest {

    @BeforeAll
    public static void init() {
        System.out.println("@BeforeAll");
    }

    @AfterAll
    public static void cleanup() {
        System.out.println("@AfterAll");
    }

    @BeforeEach
    public void tearUp() {
        System.out.println("@BeforeEach @Test method");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("@AfterEach @Test method");
    }

    @DisplayName("Test First Test")
    @Test
    void testFirstTest() {
        //...
    }

    @DisplayName("Test Second Test")
    @Test
    void testSecondTest() {
        //...
    }
}```