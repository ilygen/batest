set JAVA_HOME=C:\Java\jdevj2eebase1013\jdk
set ANT_HOME=D:\software\Tools\Eclipse 3.2\eclipse\plugins\org.apache.ant_1.6.5
set STAR_SDK=C:\Program Files\Borland\StarTeam SDK 2005 R2
set STARTEAM=C:\Program Files\Borland\StarTeam 2005 R2
set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%STAR_SDK%\lib;%STARTEAM%
set CLASSPATH=%JAVA_HOME%\lib\commons-net-2.0.jar;%JAVA_HOME%\lib\jakarta-oro-2.0.8.jar;%STAR_SDK%\lib\starteam80.jar;
ant ear

pause