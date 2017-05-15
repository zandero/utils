# utils
Collection of commonly used utilities (pure Java - without external dependencies)

## Setup
```xml
<dependency>      
     <groupId>com.zandero</groupId>      
     <artifactId>utils</artifactId>      
     <version>1.2</version>      
</dependency>
```

## Assert
Assertion utilities to check input parameters and throw IllegalArgumentException in case arguments are invalid.  

### true / false test
```java
    Assert.isTrue(text.length >= 15, "Text is to short, must be at least 15 characters long!");

    Assert.isFalse(text.length < 15, "Text is to long, must be less than 15 characters long!");
```
    
### null / not null test
```java
    Assert.isNull(value, "Value was given, but null was expected!");
    
    Assert.notNull(value, "Value expected, but null was given!");
```

### String null / empty tests
```java
    Assert.notNullOrEmptyTrimmed(value, "Expected value but was not given!");
```

### Array, set, list tests
```java
    Assert.notNullOrEmpty(array, "Expected array but null or empty provided!");
    Assert.notNullOrEmpty(list, "Expected list but null or empty provided!");
    Assert.notNullOrEmpty(set, "Expected set but null or empty provided!");
    Assert.notNullOrEmpty(map, "Expected map but null or empty provided!");
    
    Assert.isNullOrEmpty(array, "Expected empty array!");
    Assert.isNullOrEmpty(list, "Expected empty list!");
    Assert.isNullOrEmpty(set, "Expected empty set!");
    Assert.isNullOrEmpty(map, "Expected empty map!");
```


## Date time
Time conversion between long, Date and String

* Date time formatting, 
* Timezone support
* Some handy functions to get the start/end of a period 

### Utils
```java
    // return UTC initialized instance of Calendar
    DateTimeUtils.getCalendar(); 
    
    DateTimeUtils.getCalendar(long time); // returns UTC initialized instance of Calendar with set time provided in milliseconds 
```

### Date time formatting
```java
    // formats time to yyyy-MM-dd HH:mm:ss Z
    DateTimeUtils.formatDateTime(long time); 
    
    // formats time to yyyy-MM-dd HH:mm:ss
    DateTimeUtils.formatDateTimeShort(long time); 
    
    // formats time to yyyy-MM-dd
    DateTimeUtils.formatDate(long time); 
    
    // format time with custom format or to yyyy-MM-dd HH:mm:ss Z if format not provided
    DateTimeUtils.format(long time, SimpleDateFormat format); 
```

### Timezone helpers
```java
    // Returns time for given time zone (-12 / +12)
    DateTimeUtils.getTimezoneTime(long time, int timezone);

    // Converts local hour back to UTC hour
    DateTimeUtils.getUtcHour(int hour, int timezone);
    
    // Converts UTC hour to local hour
    DateTimeUtils.getTimezoneHour(int hour, int timezone);
```

### Conversions
```java
    // Converts timestamp into OffsetDatetime
    DateTimeUtils.toOffsetDateTime(long timestamp)
    
    // Converts offset date time to long timestamp
    DateTimeUtils.fromOffsetDateTime(OffsetDateTime timestamp)
```
   
```java
    // Gets first millisecond of first day in month for given time    
    DateTimeUtils.getMonthStart(long time);  
    
    // Returns last millisecond of last day in month 
    DateTimeUtils.getMonthEnd(long time);      
    
    // Returns day in month, where first day of month == 1
    DateTimeUtils.getDayInMonth(long time);  
    
    // Gets first millisecond of first day in week for given time
   	DateTimeUtils.getWeekStart(long time);
   	
   	// Returns last millisecond of last day in week for given time
   	DateTimeUtils.getWeekEnd(long time);  
   	
    // Returns number of day in week, where monday is the first day of the week (monday = 1, tuesday = 2 ..., sunday = 7)
   	DateTimeUtils.getDayInWeek(long time);  
   	
   	// Check if it is a weekend day (Saturday or Sunday)
   	DateTimeUtils.isWeekend(long time);  
   	
   	// Removes time component from date time
   	DateTimeUtils.getDateOnly(long time);  
   	
   	// Convert any given timestamp string to long trying a list of provided formats
   	DateTimeUtils.getTimestamp(String value, SimpleDateFormat[] formats);
```

## Dir 
Directory, path and file utilities
 
```java
    // Gets file extension: someFile.zip -> zip
    DirUtils.getFileExtension(String file);

    // Lists all files in given directory recursive (with option to filter by extension)
    DirUtils.getAllFilesRecursive(File rootDir, String fileExtension);
    DirUtils.getAllFilesRecursive(File rootDir, String[] fileExtensions);
    
    // Lists all files in single directory, (with option to filter by extension)
    DirUtils.getAllFilesInDir(File rootDir, String fileExtension)
    DirUtils.getAllFilesInDir(File rootDir, String[] fileExtensions);
    
    // Calculates size of all files in given directory and it's sub-directories.
    DirUtils.dirSize(File directory);
```

## Equality utils
* NPE safe checks to compare two objects of same type: Long, Integer ... 

```java
    // compare integers
    Integer one = null;
    Integer two = 3;

    EqualsUtils.equals(one, two);
    
    // compare longs
    Integer longOne = null;
    Integer longTwo = 3;
    
    EqualsUtils.equals(longOne, longTwo);
    
    // compare strings (case sensitive)
    EqualsUtils.equals("Some string", null);
    
    // Compares if two maps hold the same set of keys and same values
    // comparison of values utilizes the equals call, so make sure equals is implemented 
    // position of key is not considered (like in LinkedHashMap)
    Map<String, T> one;
    Map<String, T> two;
    EqualsUtils.equals(mapOne, mapTwo);
```

## JUnit probe
Checks if code runs inside a JUnit test or not

```java
    // Checks if code is run inside a unit test
    JUnitProbe.isUnitTest();
```

## KeyGenerator
Generates random keys of given length.

```java
    // Generates random string from A-Z, a-z, 0-9 set of chars and numbers
    // with lenght from 1-100
    KeyGenerator.generateString(int length);  
    
    // Generates non negative long key of maximum length 18
    KeyGenerator.generateLong(int length);
```

## Maps
Map helpers and manipulation utils

### Merging two maps
```java
    Map outputMap = MapUtils.mergeMaps(firatMap, mergeFunction, otherMap);
    
    // Example
    Map<String, Long> m1 = new HashMap<>();
    m1.put("A", 10L);
    m1.put("B", 20L);
    
    Map<String, Long> m2 = new HashMap<>();
    m2.put("C", 2L);
    m2.put("D", 3L);
    m2.put("E", 1L);
   
    // sum those two maps together
    Map<String, Long> result = MapUtils.mergeMaps(Stream.of(m1, m2), Long::sum, HashMap::new);
```

### Sorting 
```java
    // sort map with given comparator ... 
    LinkedHashMap sorted = sort(map, comparator);

    // example
    LinkedHashMap sortedMap = MapUtils.sort(map, Comparator.comparing(Map.Entry::getValue));
```

### Compare maps
```java
    // compare maps by keys
    MapUtils.equals(mapOne, mapTwo);
	
	// compare maps by keys and values 
    MapUtils.equals(mapOne, mapTwo, true);
```

## Resources

### Loading of resource files

```java
    // Loads resource into string
    String output = ResourceUtils.getResourceAsString(String resourceFile, Class clazz);

     // Loads resource as a set of Strings, where each word is added to the set
     Set<String> output = ResourceUtils.getResourceWords(file, clazz);
     
     // Get resource absolute path
     String output = ResourceUtils.getResourceAbsolutePath(String resource, Class clazz);
```

### Reading streams and files into strings
 ```java
     // Loads resource to String 
     String output = ResourceUtils.readFileToString(file);
     
     // Get resource last modified time stamp
     Long time = ResourceUtils.getLastModifiedTime(file);
     
     // Load input stream into string
     String output = ResourceUtils.getString(inputStream) {
     
     // Load input stream into string
     String output = ResourceUtils.getString(inputStream, encoding);
     
     // Load input stream into byte array
     byte[] output = ResourceUtils.getBytes(InputStream is);
 ```

## Strings
Various handy string utilities.

### NPE safe String comparison 

```java
    // NPE safe compare of two strings (case sensitive)
    StringUtils.equals(original, compare);
    
	// NPE safe compare of two strings - case sensitive or insensitive
	StringUtils.equals(original, compare, ignoreCase);
```

```java
    // NPE safe compare same as String.compare()
    int result = StringUtils.compare(one, two);
```

### Null and empty checks

```java
    // Checks if string is null or empty == ""
    StringUtils.isNullOrEmpty(value);
    
    // Checks if string is null, empty or contains only spaces
	StringUtils.isNullOrEmptyTrimmed(value);
```

### String trimming

```java
    // NPE safe String.trim()
    StringUtils.trim(String value);
    
    // Trim with inner double space trim
    StringUtils.trimDoubleSpaces(text);
    
    // Complete trim of all spaces
    StringUtils.trimInner(text);
    
    // Trims only end of text
    StringUtils.trimEnd(text);
    
    // Trims only start of text
    StringUtils.trimStart(text);
    
    // Trims specific text only
    StringUtils.trimAll(text, toBeTrimmed);
    
    // Trims to null if empty
    StringUtils.trimToNull(text);
```

### String transformation
```java
    // Capitalizes first found character in given string
    // "123 abc" -> "123 Abc"
    String TEXT = StringUtils.capitalize(text);

    // Removes punctuation from text
    // "test!" -> "test"
	StringUtils.removePunctuation(text);
	
	// Removes all multiple-consecutive whitespace characters (space, tab, newline) and replaces them with single space.
    // Also removes leading and trailing spaces.
    Stirng out = StringUtils.sanitizeWhitespace(text)
    byte[] out = sanitizeWhitespace(byte[] input);
    
    // Reduces text to max given size preserving words
    StringUtils.trimTextDown(text, maxWidth);
    
    // Reduces text size to a given size preserving words with appendix
    StringUtils.trimTextDown(text, widthMinusAppend, append)
    
    // Simple enumeration: first, second, third ... 5th, 6th .. etc for given number
    StringUtils.enumerate(number);
    
    // Removes double quotes if any are present (in begging and end)
    StringUtils.unQuote(text);
```

### String joining
```java
    // Joins list of string items to a single string, where items are separated
   	// with a defined separator. Limiting number of included items. 
   	StringUtils.join(List<?> list, separator);
   	StringUtils.join(List<?> list, separator, includeMaxLimit);
    
   	// Joins array of objects to a single string, where items are separated
   	// with a defined separator. Limiting number of included items.
   	StringUtils.join(Object[] args, separator);
   	StringUtils.join(Object[] args, separator, includeMaxLimit);
   
   	// Joins set of objects to a single string, where items are separated
   	// with a defined separator.
   	StringUtils.join(Set<?> items, separator);
   	StringUtils.join(Set<?> items, separator, includeMaxLimit);
   
   
   	// Joins map of items to a single string, where items are separated with a defined separator.
   	StringUtils.join(Map<String, String> map, separator);
```

### Word extraction

```java
    // Extracts words from text removing non alpha characters
    List<String> words = StringUtils.getWords(text);

    // Converts text to list of characters
	List<String> chars = asListOfChars(text);
	
	// Checks if given string is a single word (doesn't accepts words with "-" as a single word!)
    isWord(word);
```

### Search by relevance
```java
    // Calculates matching relevance between given string and search expression
	// -1 not relevant, 0..N - where lower values represents more relevant results
    relevance(text, search); 
```