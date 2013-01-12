call %M2_HOME%\bin\mvn -f ../festFaraAdapter/pom.xml compile package install
call %M2_HOME%\bin\mvn compile package
mkdir "./Documentor"
robocopy "./target" "./Documentor" "docGenerator-1.0.0-SNAPSHOT.jar"
robocopy "../festFaraAdapter/target" "./Documentor" "festFara-0.2.0-SNAPSHOT.jar"
cd Documentor
call java -cp "docGenerator-1.0.0-SNAPSHOT.jar;festFara-0.2.0-SNAPSHOT.jar" docGenerator.GeneratorStarter