package TSLG.model;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int tamano = 5; // Tamaño del grafo
        Grafo grafo = new Grafo(tamano, false);

        Nodo nodoA = new Nodo("A");
        Nodo nodoB = new Nodo("B");
        Nodo nodoC = new Nodo("C");
        Nodo nodoD = new Nodo("D");
        Nodo nodoE = new Nodo("E");

        grafo.agregarNodo(nodoA, 0);
        grafo.agregarNodo(nodoB, 1);
        grafo.agregarNodo(nodoC, 2);
        grafo.agregarNodo(nodoD, 3);
        grafo.agregarNodo(nodoE, 4);

        grafo.agregarArista(nodoA, nodoB, 12); // A a B
        grafo.agregarArista(nodoA, nodoC, 43); // A a C
        grafo.agregarArista(nodoA, nodoD, 99); // A a D
        grafo.agregarArista(nodoA, nodoE, 57); // A a E
        grafo.agregarArista(nodoB, nodoC, 10); // B a C
        grafo.agregarArista(nodoB, nodoD, 80); // B a D
        grafo.agregarArista(nodoB, nodoE,93); // B a E
        grafo.agregarArista(nodoC, nodoD, 60); // C a D
        grafo.agregarArista(nodoC, nodoE, 43); // C a E
        grafo.agregarArista(nodoD, nodoE, 50); // D a E

        grafo.imprimirMatrizAdyacencia();

        System.out.println("Lista");
        grafo.encontrarRutaViajante(nodoB);

        grafo.encontrarRutaViajanteNodoCercano(nodoB);

    }
}
