# LeQuotes
A java swing player to help learn a second language from reading, hearing and speaking. 
Made with following API
 - H2 database
 - Commons IO
 - Javazoom JL
 - Logback
 
***
Adding resources(txt and mp3 files) to database

`java -jar lequotes s directory_of_resources`

Files has to be in follow format

Text file -> Looking_For_A_Job<br>
MP3 file  -> Looking_For_A_Job.mp3<br>

Text file need the title to be in the first line

In sample folder has two pair of files
***
#### Screenshot
![alt text](LeQuotes.png?raw=true)


#### Note 
Maybe you need to install manually the JARs into your local Maven repository. To do so, run the follow command

`mvn install:install-file -Dfile=extras/jl1.0.1.jar -DgroupId=javazoom -DartifactId=jlayer -Dversion=1.0.1 -Dpackaging=jar`

`mvn install:install-file -Dfile=extras/tritonus_share-0.3.6.jar -DgroupId=org.tritonus -DartifactId=tritonus-share -Dversion=0.3.6 -Dpackaging=jar`

`mvn install:install-file -Dfile=extras/mp3spi1.9.5.jar -DgroupId=javazoom -DartifactId=mp3spi -Dversion=1.9.5 -Dpackaging=jar`
