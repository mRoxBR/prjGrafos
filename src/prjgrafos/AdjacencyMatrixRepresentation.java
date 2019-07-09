package prjgrafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdjacencyMatrixRepresentation implements iGraphRepresentation {

    private Edge m_Representation[][];
    private int VerticesNumber;
    private final File m_File;

    public AdjacencyMatrixRepresentation(File p_File) {
        this.m_File = p_File;
        this.loadFromFile();
    }

    public void addEdge(Edge p_Edge) {
        Vertice v_Source = p_Edge.getSource();
        Vertice v_Target = p_Edge.getTarget();
        this.m_Representation[v_Source.getID()][v_Target.getID()] = p_Edge;
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
            m_Representation = new Edge[VerticesNumber][VerticesNumber];

            while (br.ready()) {
                aux = br.readLine().split(" ");
                v_Source = new Vertice(Integer.parseInt(aux[0]) - 1);
                v_Target = new Vertice(Integer.parseInt(aux[1]) - 1);
                v_Weight = Double.parseDouble(aux[2]);
                Edge e;
                e = new Edge(indicator, v_Source, v_Target, v_Weight);
                this.addEdge(e);
                indicator++;
            }

            br.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AdjacencyMatrixRepresentation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AdjacencyMatrixRepresentation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printRepresentation() {
        System.out.println("Matriz de Adjacência: ");
        for (int i = 0; i < VerticesNumber; i++) {
            for (int j = 0; j < VerticesNumber; j++) {
                if (this.m_Representation[i][j] != null) {
                    System.out.print(this.m_Representation[i][j].getWeight() + "\t");
                } else {
                    System.out.print(0.0 + "\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
