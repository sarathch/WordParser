# WordParser

## Implementation
* Used MVP design pattern to make this app scalable considering future enhancements to this idea. Packages are explained below.
	* ##### parse : 
		* operations to lookup words from dictionary
		* reads broken input from user and fetches valid words in english
	* ##### dict :
		* Reads english dictionary file and loads into a TRIE data structure for constant time lookup for prefixes. One time load.
		* Implements custom dictionary based on trie data structure.
	* ##### di :
		* Implements dependency injection dagger2 logic required for application.
    
    
* Used Dagger2 dependency injection to make this app testable.
* Used Mockito, JUnit, RoboElectric and Hamcrest frameworks for unit testing.
* Used Espresso for instrumentation testing.

## Libraries
* [Dagger2](https://google.github.io/dagger/)   - For dependency injection
* [Espresso](https://github.com/googlesamples/android-testing/tree/master/ui/espresso)  - For instrumentaion testing
* [JUnit](https://mvnrepository.com/artifact/junit/junit), [Mockito](http://site.mockito.org/), [Hamcrest](http://hamcrest.org/JavaHamcrest/), [Guava](https://github.com/google/guava), RoboElectric - For Unit testing
* ButterKnife - To bind views without redundant code

## TODO
* Detailed unit tests need to be added.
* Code clean up

## Author
Sarath
