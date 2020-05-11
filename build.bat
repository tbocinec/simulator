@echo off 
    setlocal enableextensions disabledelayedexpansion

    :: possible locations under HKLM\SOFTWARE of JavaSoft registry data
    set "javaNativeVersion="
    set "java32ON64=Wow6432Node\"

    :: for variables
    ::    %%k = HKLM\SOFTWARE subkeys where to search for JavaSoft key
    ::    %%j = full path of "Java Runtime Environment" key under %%k
    ::    %%v = current java version
    ::    %%e = path to java

    set "javaDir="
    set "javaVersion="
    for %%k in ( "%javaNativeVersion%" "%java32ON64%") do if not defined javaDir (
        for %%j in (
            "HKLM\SOFTWARE\%%~kJavaSoft\Java Runtime Environment"
        ) do for /f "tokens=3" %%v in (
            'reg query "%%~j" /v "CurrentVersion" 2^>nul ^| find /i "CurrentVersion"'
        ) do for /f "tokens=2,*" %%d in (
            'reg query "%%~j\%%v" /v "JavaHome"   2^>nul ^| find /i "JavaHome"'
        ) do ( set "javaDir=%%~e" & set "javaVersion=%%v" )
    )

    if not defined javaDir (
        echo Java not found
        exit
    ) else (
        echo JAVA_HOME="%javaDir%"
        echo JAVA_VERSION="%javaVersion%"
        mvn clean package

    )

 
    endlocal
    pause