## Usage

### Maven dependency

To use module on Maven-based projects, use following dependency:

Step 1. Add the JitPack repository to your build file
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Step 2. Add the dependency
```xml
<dependency>
    <groupId>com.github.zuisong</groupId>
    <artifactId>jackson-module-gson</artifactId>
    <version>9de392d024</version>
</dependency>
```

(or whatever version is most up-to-date at the moment)

### Registering module

Like all standard Jackson modules (libraries that implement Module interface), registration is done as follows (Jackson 2.x up to 2.9)

```java
ObjectMapper mapper = new ObjectMapper()
    .registerModule(new GsonModule());
```
OR, the new method added in 2.10 (old method will work with 2.x but not 3.x):

```java
ObjectMapper mapper = JsonMapper.builder()
    .addModule(new GsonModule())
    .build();
```

after which functionality is available with all normal Jackson operations, like:

```java
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.gson.GsonModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.junit.Assert;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new GsonModule());

        JsonElement ele = mapper.readValue(
                "[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                JsonElement.class);
        JsonArray array = ele.getAsJsonArray();
        Assert.assertEquals(7, array.size());
        Assert.assertTrue(array.get(0).isJsonNull());
        Assert.assertEquals(13, array.get(1).getAsInt());
        Assert.assertFalse(array.get(2).getAsBoolean());
        Assert.assertEquals(1.25, array.get(3).getAsDouble());
        Assert.assertEquals("abc", array.get(4).getAsString());
        Assert.JsonObject ob = array.get(5).getAsJsonObject();
        Assert.assertEquals(1, ob.size());
        Assert.assertEquals(13, ob.get("a").getAsInt());
        JsonArray array2 = array.get(6).getAsJsonArray();
        Assert.assertEquals(0, array2.size());

    }
}

  
```
