package prjgrafos;

public interface iCM {

    public boolean isConnected();

    public boolean isCompleted();

    public boolean hasLoop();

    public boolean isNull();

    public void toComplete();

    public void notDirected();

    public boolean isEulerian();

    public int inputDegree(Vertice v);

    public int exitDegree(Vertice v);
}
