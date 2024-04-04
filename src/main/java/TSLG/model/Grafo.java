package TSLG.model;

import java.util.*;

import java.util.*;

import java.util.*;

public class Grafo {
    private Nodo[] nodos;
    private int[][] matrizAdyacencia;
    private boolean esDirigido;
    private List<List<Nodo>> todasLasRutas;
    private int costoRutaMasCorta;

    public Grafo(int tamano, boolean esDirigido) {
        nodos = new Nodo[tamano];
        matrizAdyacencia = new int[tamano][tamano];
        this.esDirigido = esDirigido;
        todasLasRutas = new ArrayList<>();
        costoRutaMasCorta = Integer.MAX_VALUE;
    }

    public void agregarNodo(Nodo nodo, int posicion) {
        nodos[posicion] = nodo;
    }

    public void agregarArista(Nodo origen, Nodo destino, int peso) {
        int indexOrigen = getIndexNodo(origen);
        int indexDestino = getIndexNodo(destino);
        matrizAdyacencia[indexOrigen][indexDestino] = peso;
        if (!esDirigido) {
            // Si no es dirigido, también agregamos la arista inversa
            matrizAdyacencia[indexDestino][indexOrigen] = peso;
        }
    }

    private int getIndexNodo(Nodo nodo) {
        for (int i = 0; i < nodos.length; i++) {
            if (nodos[i].equals(nodo)) {
                return i;
            }
        }
        return -1;
    }

    public void imprimirMatrizAdyacencia() {
        if (esDirigido) {
            // Imprimir la matriz como antes (dirigida)
            for (int i = 0; i < nodos.length; i++) {
                for (int j = 0; j < nodos.length; j++) {
                    System.out.print(matrizAdyacencia[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            // Imprimir una matriz simétrica (no dirigida)
            for (int i = 0; i < nodos.length; i++) {
                for (int j = 0; j < nodos.length; j++) {
                    System.out.print(Math.max(matrizAdyacencia[i][j], matrizAdyacencia[j][i]) + " ");
                }
                System.out.println();
            }
        }
    }

    public int calcularCostoRuta(List<Nodo> ruta) {
        int costo = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {
            Nodo origen = ruta.get(i);
            Nodo destino = ruta.get(i + 1);
            costo += matrizAdyacencia[getIndexNodo(origen)][getIndexNodo(destino)];
        }
        // Sumar el costo de la arista que conecta el último nodo con el primer nodo
        Nodo primerNodo = ruta.get(0);
        Nodo ultimoNodo = ruta.get(ruta.size() - 1);
        costo += matrizAdyacencia[getIndexNodo(ultimoNodo)][getIndexNodo(primerNodo)];
        return costo;
    }


    public void encontrarRutaViajante(Nodo nodoInicio) {
        // Generar todas las permutaciones de nodos
        List<Nodo> nodosSinInicio = new ArrayList<>();
        for (int i = 0; i < nodos.length; i++) {
            if (nodos[i] != null && !nodos[i].equals(nodoInicio)) {
                nodosSinInicio.add(nodos[i]);
            }
        }

        permutarRutas(new ArrayList<>(), nodosSinInicio, nodoInicio);

        // Calcular costos de cada ruta y encontrar la ruta con el menor costo
        for (List<Nodo> ruta : todasLasRutas) {
            ruta.add(0, nodoInicio); // Agregar el nodo de inicio al principio de la ruta
            int costoRuta = calcularCostoRuta(ruta);
            if (costoRuta < costoRutaMasCorta) {
                costoRutaMasCorta = costoRuta;
            }
            imprimirRutaYCosto(ruta, costoRuta);
        }

        // Imprimir la ruta más corta
        System.out.println("\nRuta más corta:");
        for (List<Nodo> ruta : todasLasRutas) {
            if (calcularCostoRuta(ruta) == costoRutaMasCorta) {
                imprimirRuta(ruta);
                break;
            }
        }
        System.out.println("Costo: " + costoRutaMasCorta);
    }

    private void permutarRutas(List<Nodo> rutaActual, List<Nodo> nodosDisponibles, Nodo nodoInicio) {
        if (nodosDisponibles.isEmpty()) {
            todasLasRutas.add(new ArrayList<>(rutaActual));
            return;
        }

        for (int i = 0; i < nodosDisponibles.size(); i++) {
            Nodo nodoActual = nodosDisponibles.get(i);
            rutaActual.add(nodoActual);
            List<Nodo> nodosRestantes = new ArrayList<>(nodosDisponibles);
            nodosRestantes.remove(i);
            permutarRutas(rutaActual, nodosRestantes, nodoInicio);
            rutaActual.remove(rutaActual.size() - 1);
        }
    }

    public void encontrarRutaViajanteNodoCercano(Nodo nodoInicio) {
        List<Nodo> ruta = new ArrayList<>();
        ruta.add(nodoInicio);
        Set<Nodo> nodosRestantes = new HashSet<>(Arrays.asList(nodos));
        nodosRestantes.remove(nodoInicio);

        while (!nodosRestantes.isEmpty()) {
            Nodo nodoActual = ruta.get(ruta.size() - 1);
            Nodo nodoMasCercano = encontrarNodoMasCercano(nodoActual, nodosRestantes);
            ruta.add(nodoMasCercano);
            nodosRestantes.remove(nodoMasCercano);
        }

        ruta.add(nodoInicio); // Regresar al nodo inicial
        int costoRuta = calcularCostoRuta(ruta);

        System.out.println("Ruta más corta por nodo mas cercano:");
        imprimirRutaYCosto(ruta, costoRuta);
    }


    private Nodo encontrarNodoMasCercano(Nodo nodoActual, Set<Nodo> nodosRestantes) {
        int distanciaMinima = Integer.MAX_VALUE;
        Nodo nodoMasCercano = null;

        for (Nodo nodo : nodosRestantes) {
            int distancia = matrizAdyacencia[getIndexNodo(nodoActual)][getIndexNodo(nodo)];
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                nodoMasCercano = nodo;
            }
        }

        return nodoMasCercano;
    }

    private void imprimirRutaYCosto(List<Nodo> ruta, int costo) {
        for (Nodo nodo : ruta) {
            System.out.print(nodo.getNombre() + " -> ");
        }
        System.out.println(ruta.get(0).getNombre() + " : " + costo);
    }

    private void imprimirRuta(List<Nodo> ruta) {
        for (Nodo nodo : ruta) {
            System.out.print(nodo.getNombre() + " -> ");
        }
        System.out.println(ruta.get(0).getNombre());
    }
}

