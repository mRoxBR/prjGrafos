package prjgrafos;

import java.awt.Color;
import java.util.Objects;

public class Edge {

    private int m_ID;
    private Color m_Color;
    private double m_Weight = 1;
    private Vertice m_VerticeSource;
    private Vertice m_VerticeTarget;
    private int m_IndicatorIMR;

    public Edge(int p_ID, Vertice p_VerticeSource, Vertice p_VerticeTarget) {
        m_ID = p_ID;
        m_VerticeSource = p_VerticeSource;
        m_VerticeTarget = p_VerticeTarget;
    }

    public Edge(int p_ID, Vertice p_VerticeSource, Vertice p_VerticeTarget, double p_Weight) {
        this(p_ID, p_VerticeSource, p_VerticeTarget);
        m_Weight = p_Weight;
    }

    public Edge(int p_ID, Vertice p_VerticeSource, Vertice p_VerticeTarget, double p_Weight, int p_IndicatorIMR) {
        this(p_ID, p_VerticeSource, p_VerticeTarget, p_Weight);
        m_IndicatorIMR = p_IndicatorIMR;
    }

    public int getID() {
        return m_ID;
    }

    public Color getColor() {
        return m_Color;
    }

    public double getWeight() {
        return m_Weight;
    }

    public Vertice getSource() {
        return m_VerticeSource;
    }

    public Vertice getTarget() {
        return m_VerticeTarget;
    }

    public int getIndicatorIMR() {
        return m_IndicatorIMR;
    }

    public void setWeight(double m_Weight) {
        this.m_Weight = m_Weight;
    }

    @Override
    public String toString() {
        return "ID: " + m_ID;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.m_VerticeSource);
        hash = 23 * hash + Objects.hashCode(this.m_VerticeTarget);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.m_VerticeSource, other.m_VerticeSource)) {
            return false;
        }
        if (!Objects.equals(this.m_VerticeTarget, other.m_VerticeTarget)) {
            return false;
        }
        return true;
    }
}
