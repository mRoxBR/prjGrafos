package prjgrafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IncidencyMatrixRepresentation implements iGraphRepresentation {

    private final ArrayList<ArrayList<Edge>> m_Representation;
    private final File m_File;
    private int VerticesNumber;

    public IncidencyMatrixRepresentation(File p_File) {
        m_Representation = new ArrayList<>();
        this.m_File = p_File;
        this.loadFromFile();
    }

    public void addEdge(Edge p_Edge) {
        Vertice v_Source = p_Edge.getSource();
        Vertice v_Target = p_Edge.getTarget();

        for (int i = 1; i <= VerticesNumber; i++) {
            if (v_Source.getID() == i) {
                m_Representation.get(i - 1).add(p_Edge);
            } else {
                m_Representation.get(i - 1).add(null);
            }
        }
    }

    @Override
    public final void loadFromFile() {
        Vertice v_Source, v_Target;
        int indicator = 0;
        double v_Weight;
        FileReader fr;
        BufferedReader br;

        try {
            fr = new FileReader(m_File);
            br = new BufferedReader(fr);
            String aux[];

            aux = br.readLine().split(" ");
            VerticesNumber = Integer.parseInt(aux[0]);

            for (int i = 0; i < this.VerticesNumber; i++) {
                m_Representation.add(new ArrayList<>());
            }

            while (br.ready()) {
                aux = br.readLine().split(" ");
                v_Source = new Vertice(Integer.parseInt(aux[0]));
                v_Target = new Vertice(Integer.parseInt(aux[1]));
                v_Weight = Double.parseDouble(aux[2]);

                Edge e;
                e = new Edge(indicator, v_Source, v_Target, v_Weight, 1);
                this.addEdge(e);
                indicator++;
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(IncidencyMatrixRepresentation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IncidencyMatrixRepresentation.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void printRepresentation() {
        System.out.println("Matriz de Incidência: ");
        for (int i = 0; i < VerticesNumber; i++) {
            for (Edge e : m_Representation.get(i)) {
                if (e != null) {
                    int v_S = e.getSource().getID();
                    int v_T = e.getTarget().getID();

                    System.out.print(v_S + "->" + v_T + "\t");
                }
            }
        }
        System.out.println();

        for (int i = 0; i < VerticesNumber; i++) {
            for (Edge e : m_Representation.get(i)) {

                if (e != null) {
                    System.out.print(e.getIndicatorIMR() + " \t");
                } else {
                    System.out.print("0 \t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
