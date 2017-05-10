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
Assertion utilities to check input parameters and throw IllegalArgumentException in case arguments are invalid  

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

## JSON utils
Wrapper for Jackson to simplify affairs
* Transform object from JSON and back using Jackson

## JUnit probe
* Checks if code runs inside a JUnit test or not

## KeyGenerator
* Generates random keys of given length
 
## Maps
* Merge
* Sort
* Compare

## Resources
* Loading of resource files
* Reading streams into strings 

## Strings
Various handy string utilities
* compare if null or empty
* trim to null
* NPE safe equals 
* word extraction 
* trimming text to length
* joining list of items into a single string
* ...
 
