package prjgrafos;

import java.io.File;

public class Controller {

    public static String g_FilePath1 = "GrafoBuscas.txt";
    public static String g_FilePath2 = "GrafoOrdTop.txt";
    public static String g_FilePath3 = "GrafoDesconexo.txt";
    public static String g_FilePath4 = "GrafoDijkstra.txt";
    public static String g_FilePath5 = "GrafoEuleriano.txt";

    public static void main(String[] args) throws Exception {

        try {
            File v_File1 = new File(g_FilePath1);
            File v_File2 = new File(g_FilePath2);
            File v_File3 = new File(g_FilePath3);
            File v_File4 = new File(g_FilePath4);
            File v_File5 = new File(g_FilePath5);

            Vertice example1 = new Vertice(7);
            Vertice example2 = new Vertice(1);
            Vertice example3 = new Vertice(5);

            AdjacencyListRepresentation alr = new AdjacencyListRepresentation(v_File1);
            AdjacencyMatrixRepresentation amr = new AdjacencyMatrixRepresentation(v_File1);
            IncidencyMatrixRepresentation imr = new IncidencyMatrixRepresentation(v_File1);

            ComplementaryMethods cm1 = new ComplementaryMethods(v_File1);
            ComplementaryMethods cm2 = new ComplementaryMethods(v_File2);
            ComplementaryMethods cm3 = new ComplementaryMethods(v_File3);
            ComplementaryMethods cm4 = new ComplementaryMethods(v_File4);
            ComplementaryMethods cm5 = new ComplementaryMethods(v_File5);

            System.out.println("------------------");
            System.out.println("Grafo para buscas");
            System.out.println("------------------");
            alr.printRepresentation();
            amr.printRepresentation();
            imr.printRepresentation();
            System.out.println("É conexo? " + cm1.isConnected() + "\n");
            System.out.println("É completo? " + cm1.isCompleted() + "\n");
            System.out.println("Possui loop? " + cm1.hasLoop() + "\n");
            System.out.println("É nulo? " + cm1.isNull() + "\n");
            System.out.println("Grau de entrada do vértice " + example1.getID() + ":" + cm1.inputDegree(example1) + "\n");
            System.out.println("Grau de saída do vértice " + example1.getID() + ":" + cm1.exitDegree(example1) + "\n");
            System.out.println("Busca em largura partindo do vértice " + example1.getID() + ":");
            cm1.widthSearch(example1);
            System.out.println();
            System.out.println("Busca em profundidade partindo do vértice " + example1.getID() + ":");
            cm1.depthSearch(example1);
            System.out.println();
            System.out.println("Completar grafo: \n");
            cm1.toComplete();
            AdjacencyListRepresentation alr2 = new AdjacencyListRepresentation(v_File1);
            AdjacencyMatrixRepresentation amr2 = new AdjacencyMatrixRepresentation(v_File1);
            IncidencyMatrixRepresentation imr2 = new IncidencyMatrixRepresentation(v_File1);
            alr2.printRepresentation();
            amr2.printRepresentation();
            imr2.printRepresentation();
            System.out.println("---------------------------------");
            System.out.println(" Grafo para ordenação topológica");
            System.out.println("---------------------------------");
            System.out.println("Ordenação topológica partindo do vértice " + example2.getID() + ":");
            cm2.topologicOrder(example2);
            System.out.println();

            System.out.println("----------------");
            System.out.println("Grafo desconexo");
            System.out.println("----------------");
            cm3.notDirected();
            System.out.println("Número de componentes conexas: " + cm3.relatedComponents() + "\n");

            System.out.println("--------------------------------------");
            System.out.println("Grafo ponderado (p/ aplicar Dijkstra)");
            System.out.println("--------------------------------------");
            System.out.println("Dijkstra: ");
            cm4.Dijkstra(example3);

            System.out.println("----------------");
            System.out.println("Grafo euleriano");
            System.out.println("----------------");
            System.out.println("É euleriano? " + cm5.isEulerian() + "\n");

        } catch (NumberFormatException nfe) {
            System.out.println("O arquivo informado não está no formato DIMACS: " + nfe);
        } catch (Exception e) {
            System.out.println("Foi encontrado um erro: " + e);
        }
    }
}
