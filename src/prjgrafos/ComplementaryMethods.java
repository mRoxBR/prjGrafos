/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prjgrafos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mateus
 */
public class ComplementaryMethods extends AdjacencyListRepresentation implements iCM {

    public ComplementaryMethods(File p_File) {
        super(p_File);
    }

    @Override
    public boolean isConnected() {
        ArrayList<Edge> al = new ArrayList<>();
        int index = 1, count = 0;
        for (Vertice v_Source : super.m_Representation.keySet()) {
            for (Vertice v_Target : super.m_Representation.get(v_Source).keySet()) {
                Edge e = new Edge(index, v_Source, v_Target);
                Edge e2 = new Edge(1, v_Target, v_Source);
                if (!al.contains(e2)) {
                    count++;
                }
                al.add(e);
            }
        }
        return super.VerticesNumber - 1 <= count;
    }

    @Override
    public boolean isCompleted() {
        return (super.VerticesNumber * (super.VerticesNumber - 1)) == super.EdgesNumber;
    }

    @Override
    public boolean hasLoop() {
        for (Vertice v_Source : super.m_Representation.keySet()) {
            for (Vertice v_Target : super.m_Representation.get(v_Source).keySet()) {
                if (v_Source.getID() == v_Target.getID()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isNull() {
        return super.EdgesNumber == 0;
    }

    @Override
    public void toComplete() {

        try {
            FileWriter fw = new FileWriter(super.m_File);
            BufferedWriter bw = new BufferedWriter(fw);
            int countNE = 0;

            for (int i = 1; i <= super.VerticesNumber; i++) {
                Vertice v_Source = new Vertice(i);
                if (super.m_Representation.containsKey(v_Source)) {
                    for (int j = 1; j <= super.VerticesNumber; j++) {
                        Vertice v_Target = new Vertice(j);
                        if (super.m_Representation.get(v_Source).get(v_Target) == null && i != j) {
                            super.m_Representation.get(v_Source).put(v_Target, 1.0);
                            countNE++;
                        }
                    }
                } else {
                    super.m_Representation.put(v_Source, new HashMap<>());
                    for (int c = 1; c <= super.VerticesNumber; c++) {
                        Vertice v_Target = new Vertice(c);
                        if (i != c) {
                            super.m_Representation.get(v_Source).put(v_Target, 1.0);
                            countNE++;
                        }
                    }
                }
            }

            bw.write(Integer.toString(super.VerticesNumber) + " " + Integer.toString(super.EdgesNumber + countNE));
            bw.newLine();
            for (Vertice v_Source : super.m_Representation.keySet()) {
                for (Vertice v_Target : super.m_Representation.get(v_Source).keySet()) {
                    int v_Weight = super.m_Representation.get(v_Source).get(v_Target).intValue();
                    bw.write(Integer.toString(v_Source.getID()) + " " + Integer.toString(v_Target.getID()) + " " + Integer.toString(v_Weight));
                    bw.newLine();
                }
            }
            bw.close();
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(ComplementaryMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void notDirected() {
        HashMap<Vertice, HashMap<Vertice, Double>> aux = new HashMap<>();
        for (Vertice v_Source : super.m_Representation.keySet()) {
            for (Vertice v_Target : super.m_Representation.get(v_Source).keySet()) {
                aux.putIfAbsent(v_Target, new HashMap<>());
                double v_Weight = super.m_Representation.get(v_Source).get(v_Target);
                aux.get(v_Target).put(v_Source, v_Weight);
            }
        }

        for (Vertice v_Source : aux.keySet()) {
            super.m_Representation.putIfAbsent(v_Source, new HashMap<>());
            for (Vertice v_Target : aux.get(v_Source).keySet()) {
                double v_Weight = aux.get(v_Source).get(v_Target);
                super.m_Representation.get(v_Source).put(v_Target, v_Weight);
            }
        }
    }

    @Override
    public boolean isEulerian() {
        for (Vertice v_Source : super.m_Representation.keySet()) {
            if (super.m_Representation.get(v_Source).size() % 2 != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int inputDegree(Vertice v) {
        int count = 0;

        for (Vertice v_Source : super.m_Representation.keySet()) {
            for (Vertice v_Target : super.m_Representation.get(v_Source).keySet()) {
                if (v.getID() == v_Target.getID()) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int exitDegree(Vertice v) {
        int count = 0;

        if (super.m_Representation.containsKey(v)) {
            for (Vertice aux : super.m_Representation.get(v).keySet()) {
                count++;
            }
        }

        return count;
    }

    public ArrayList<Vertice> widthSearch(Vertice v) {
        ArrayList<Vertice> queue = new ArrayList<>();
        ArrayList<Vertice> ordered = new ArrayList<>();
        int visited[] = new int[super.VerticesNumber];

        for (int i = 0; i < super.VerticesNumber; i++) {
            visited[i] = 0;
        }

        visited[v.getID() - 1] = 1;
        ordered.add(v);
        queue.add(v);

        while (!queue.isEmpty()) {
            Vertice u = queue.get(0);
            queue.remove(0);

            if (super.m_Representation.containsKey(u)) {
                for (int i = 1; i <= super.VerticesNumber; i++) {
                    Vertice aux = new Vertice(i);
                    if (super.m_Representation.get(u).containsKey(aux)) {
                        if (visited[aux.getID() - 1] == 0) {
                            visited[aux.getID() - 1] = 1;
                            ordered.add(aux);
                            queue.add(aux);
                        }
                    }
                }
            }
        }

        for (Vertice aux2 : ordered) {
            System.out.print(aux2.getID() + " ");
        }
        System.out.println();

        return ordered;
    }

    public ArrayList<Vertice> depthSearch(Vertice v) {
        ArrayList<Vertice> stack = new ArrayList<>();
        ArrayList<Vertice> ordered = new ArrayList<>();
        int visited[] = new int[super.VerticesNumber];

        for (int i = 0; i < super.VerticesNumber; i++) {
            visited[i] = 0;
        }
        visited[v.getID() - 1] = 1;
        ordered.add(v);
        stack.add(v);

        while (!stack.isEmpty()) {
            Vertice u = stack.get(stack.size() - 1);
            int indicator = 0;
            if (super.m_Representation.containsKey(u)) {
                for (int i = 1; i <= super.VerticesNumber; i++) {
                    Vertice aux = new Vertice(i);
                    if (super.m_Representation.get(u).containsKey(aux) && visited[i - 1] == 0) {
                        visited[i - 1] = 1;
                        indicator = 1;
                        ordered.add(aux);
                        stack.add(aux);
                        break;
                    }
                }
            }
            if (indicator == 0) {
                stack.remove(u);
            }
        }

        for (Vertice aux2 : ordered) {
            System.out.print(aux2.getID() + " ");
        }
        System.out.println();

        return ordered;
    }

    public void relatedAux(Vertice u, int mark, int visited[]) {
        visited[u.getID() - 1] = mark;

        if (super.m_Representation.containsKey(u)) {
            for (int i = 1; i <= super.VerticesNumber; i++) {
                Vertice aux = new Vertice(i);
                if (super.m_Representation.get(u).containsKey(aux)) {
                    if (visited[aux.getID() - 1] == 0) {
                        relatedAux(aux, mark, visited);
                    }
                }
            }
        }
    }

    public int relatedComponents() {
        int visited[] = new int[super.VerticesNumber];
        int count = 0;

        for (int i = 0; i < super.VerticesNumber; i++) {
            visited[i] = 0;
        }

        for (int i = 0; i < super.VerticesNumber; i++) {
            if (visited[i] == 0) {
                count++;
                relatedAux(new Vertice(i + 1), count, visited);
            }
        }
        return count;
    }

    public void depthOrder(Vertice u, int visited[], ArrayList<Vertice> TO_List) {
        visited[u.getID() - 1] = 1;
        if (super.m_Representation.containsKey(u)) {
            for (int i = 1; i <= super.VerticesNumber; i++) {
                Vertice aux = new Vertice(i);
                if (super.m_Representation.get(u).containsKey(aux)) {
                    if (visited[aux.getID() - 1] == 0) {
                        this.depthOrder(aux, visited, TO_List);
                    }
                }
            }
        }
        TO_List.add(0, u);
    }

    public ArrayList<Vertice> topologicOrder(Vertice v) {
        int visited[] = new int[super.VerticesNumber];
        ArrayList<Vertice> TO_List = new ArrayList<>();

        for (int i = 0; i < super.VerticesNumber; i++) {
            visited[i] = 0;
        }

        for (int i = 0; i < super.VerticesNumber; i++) {
            if (visited[i] == 0) {
                this.depthOrder(new Vertice(i + 1), visited, TO_List);
            }
        }

        for (Vertice aux : TO_List) {
            System.out.print(aux.getID() + " ");
        }
        System.out.println();
        return TO_List;
    }

    public Vertice minDist(ArrayList<Vertice> list, double dist[]) {
        Vertice minV = list.get(0);
        double minD = dist[minV.getID() - 1];

        for (int i = 1; i < list.size(); i++) {
            if (dist[list.get(i).getID() - 1] < minD) {
                minV = list.get(i);
                minD = dist[minV.getID() - 1];
            }
        }

        return minV;
    }

    public void Dijkstra(Vertice v) {
        ArrayList<Vertice> list = new ArrayList<>();
        ArrayList<Vertice> ordered = new ArrayList<>();
        double dist[] = new double[super.VerticesNumber];
        Vertice pred[] = new Vertice[super.VerticesNumber];

        for (int i = 0; i < super.VerticesNumber; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = null;
        }

        dist[v.getID() - 1] = 0;
        list = super.VerticesList;

        while (!list.isEmpty()) {
            Vertice u = minDist(list, dist);
            ordered.add(u);
            list.remove(u);
            if (super.m_Representation.containsKey(u)) {
                for (Vertice aux : super.m_Representation.get(u).keySet()) {
                    if (dist[aux.getID() - 1] > dist[u.getID() - 1] + super.m_Representation.get(u).get(aux)) {
                        dist[aux.getID() - 1] = (dist[u.getID() - 1] + super.m_Representation.get(u).get(aux));
                        pred[aux.getID() - 1] = u;
                    }
                }
            }
        }

        System.out.print("Vértice(v) \t");
        for (int i = 1; i <= super.VerticesNumber; i++) {
            System.out.print(i + " \t");
        }
        System.out.println();

        System.out.print("dist[v] \t");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(dist[i] + " \t");
        }
        System.out.println();

        System.out.print("pred[v] \t");
        for (int i = 0; i < pred.length; i++) {
            if (pred[i] == null) {
                System.out.print("- \t");
            } else {
                System.out.print(pred[i].getID() + " \t");
            }
        }
        System.out.println("\n");
    }
}
