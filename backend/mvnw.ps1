# Maven Wrapper for Windows PowerShell
param(
    [Parameter(ValueFromRemainingArguments = $true)]
    [string[]]$MavenArgs
)

$AppHome = $PSScriptRoot
$WrapperJar = "$AppHome\.mvn\wrapper\maven-wrapper.jar"

if ($env:JAVA_HOME) {
    $JavaCmd = "$env:JAVA_HOME\bin\java.exe"
} else {
    $JavaCmd = "java.exe"
}

$M2Config = "$env:USERPROFILE\.m2"

$JavaArgs = @(
    "-Dmaven.multiModuleProjectDirectory=$AppHome"
    "-classpath", $WrapperJar
    "org.apache.maven.wrapper.MavenWrapperMain"
) + $MavenArgs

& $JavaCmd $JavaArgs
exit $LASTEXITCODE
