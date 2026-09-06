# Programa de Distancias Minima y Maxima (Serial + Paralelo)

Programa Java de terminal que genera un dataset aleatorio, procesa las distancias
entre todos los pares de puntos por dos vias (serial y paralela con H hilos) y
consolida las metricas comparativas (speedup, eficiencia y reduccion de tiempo).

## Requisitos

- JDK 17 o superior (javac y java accesibles desde la terminal, o usar la ruta
  completa de cada ejecutable).

## Compilacion

Desde la raiz del proyecto compilar todos los modulos:

```text
javac -encoding UTF-8 -d out src/entrada/*.java src/generador/*.java src/serial/*.java src/paralelo/*.java src/consolidador/*.java src/Main.java
```

Los archivos `.class` se generan en el directorio `out/`.

## Ejecucion

Con el programa compilado, ejecutar el flujo completo:

```text
java -cp out Main
```

El programa pedira por terminal los siguientes parametros:

- `N` — cantidad de observaciones (mayor que 1).
- `n` — cantidad de dimensiones (mayor o igual que 1).
- `A` — limite inferior aleatorio (entero).
- `B` — limite superior aleatorio (entero, estrictamente mayor que A).
- `H` — cantidad de hilos concurrentes del procesamiento paralelo (mayor o igual que 1).

Una vez ingresados, el programa genera `dataset.dat`, lo procesa por la via serial
y por la via paralela, y muestra el reporte de consolidacion con la tabla de
tiempos y extremos y la telemetria (speedup, eficiencia, reduccion de tiempo).