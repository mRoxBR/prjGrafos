package prjgrafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdjacencyListRepresentation implements iGraphRepresentation {

    protected final HashMap<Vertice, HashMap<Vertice, Double>> m_Representation;
    protected ArrayList<Vertice> VerticesList;
    protected int VerticesNumber, EdgesNumber;
    protected final File m_File;

    public AdjacencyListRepresentation(File p_File) {
        this.m_Representation = new HashMap<>();
        this.VerticesNumber = 0;
        this.EdgesNumber = 0;
        this.m_File = p_File;
        this.VerticesList = new ArrayList<>();
        this.loadFromFile();
    }

    public void addVerticeSource(Vertice p_Vertice) {
        m_Representation.putIfAbsent(p_Vertice, new HashMap<>());
    }

    public void addVertice(Edge p_Edge) {
        Vertice v_Source = p_Edge.getSource();
        Vertice v_Target = p_Edge.getTarget();
        double v_Weight = p_Edge.getWeight();

        m_Representation.get(v_Source).put(v_Target, v_Weight);
    }

    public void addVerticeList(Vertice p_Vertice) {
        int v_ID = p_Vertice.getID();
        if (!VerticesList.contains(p_Vertice)) {
            VerticesList.add(p_Vertice);
        }
    }

    @Override
    public void loadFromFile() {
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
            this.VerticesNumber = Integer.parseInt(aux[0]);
            this.EdgesNumber = Integer.parseInt(aux[1]);

            while (br.ready()) {
                aux = br.readLine().split(" ");
                v_Source = new Vertice(Integer.parseInt(aux[0]));
                v_Target = new Vertice(Integer.parseInt(aux[1]));
                v_Weight = Double.parseDouble(aux[2]);

                this.addVerticeSource(v_Source);
                this.addVerticeList(v_Source);

                Edge e;
                e = new Edge(indicator, v_Source, v_Target, v_Weight);
                this.addVertice(e);

                indicator++;
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdjacencyListRepresentation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdjacencyListRepresentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printRepresentation() {
        System.out.println("Lista de Adjacência: ");
        for (int i = 1; i <= this.VerticesNumber; i++) {
            Vertice v_Source = new Vertice(i);
            if (this.m_Representation.containsKey(v_Source)) {
                System.out.print(v_Source.getID());
                for (int j = 1; j <= this.VerticesNumber; j++) {
                    Vertice v_Target = new Vertice(j);
                    if (this.m_Representation.get(v_Source).containsKey(v_Target)) {
                        Double v_Weight = m_Representation.get(v_Source).get(v_Target);
                        System.out.print(" -(" + v_Weight + ")-> " + v_Target.getID());
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
