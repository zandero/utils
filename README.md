# utils
Collection of commonly used utilities 

## Setup
```xml
<dependency>      
     <groupId>com.zandero</groupId>      
     <artifactId>utils</artifactId>      
     <version>1.1</version>      
</dependency>
```

## Assert
Assertion utilities to check input parameters and throw IllegalArgumentException in case arguments are invalid  

`Assert.isTrue(text.length >= 15, "Text is to short, must be at least 15 characters long!");`

## Date time
Time conversion between long, Date and String
* Date time formatting, 
* Timezone support
* Some handy functions to get the start/end of a period 

## Dir 
* Listing files in directories
* Get filename extensions ... 

## Encoding
* Various encoding / decoding utilities
* Some encrypt / decrypt stuff
 
## Equality utils
* NPE safe checks to compare two objects of same type: Long, Integer ... 

## JSON utils
Wrapper for Jackson to simplify affairs
* Transform object from JSON and back using Jackson

## JUnit probe
* Checks if code runs inside a JUnit test or not

## KeyGenerator
* Generates random keys of given length
 
## Keywords
* Extracts keywords from text 
* Will exclude common english words
* Will exclude any given set of words

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

## Urls
Building and parsing of URLs
* URL composition
* get domain
* get scheme
* get query parameters 
* ...

 
