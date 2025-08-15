# ===============================
# Script: run-tests.ps1
# Descripción: Configura JDK 17 y ejecuta mvnw test
# ===============================

# Ruta al JDK 17 (ajustar si cambia)
$jdkHome = "C:\Users\User12\.jdks\corretto-17.0.14"

Write-Host "Configurando JAVA_HOME -> $jdkHome" -ForegroundColor Cyan
$env:JAVA_HOME = $jdkHome

# Limpiar PATH de posibles rutas a Java 8
$parts = $env:Path -split ';' |
    Where-Object { $_ -and ($_ -notmatch 'Common Files\\Oracle\\Java\\javapath') -and ($_ -notmatch 'jre1\.8') -and ($_ -notmatch 'jdk1\.8') }

$env:Path = "$jdkHome\bin;" + ($parts -join ';')

# Verificar versión
Write-Host "`nVersion de Java actual:" -ForegroundColor Yellow
java -version
Write-Host "`nVersion de Maven actual:" -ForegroundColor Yellow
./mvnw -v

# Ejecutar pruebas
Write-Host "`nEjecutando pruebas unitarias..." -ForegroundColor Green
./mvnw test

Write-Host "`nPruebas finalizadas." -ForegroundColor Green
