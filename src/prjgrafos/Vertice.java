package prjgrafos;

import java.awt.Color;

public class Vertice {

    private int m_ID;
    private Color m_Color;

    public Vertice(int p_ID) {
        m_ID = p_ID;
    }

    public int getID() {
        return m_ID;
    }

    public Color getColor() {
        return m_Color;
    }

    @Override
    public String toString() {
        return "ID: " + m_ID;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.m_ID;
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
        final Vertice other = (Vertice) obj;
        if (this.m_ID != other.m_ID) {
            return false;
        }
        return true;
    }
}
