public class UnionFind {
    private int[] arrN;
    private int[] size;
    private int[] largest;
    
    public UnionFind(int N) {
        arrN = new int[N];
        size = new int[N];
        largest = new int[N];
        for (int i = 0; i < N; i += 1) {
            arrN[i] = i;
            size[i] = 1;
            largest[i]=i;
        }
    }
    
    private int root(int i) {
        while (i != arrN[i]) {
            arrN[i] = arrN[arrN[i]];   //path compression
            i = arrN[i];
        }
        return i;
    }
    
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }
    
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        if (pRoot == qRoot) {
            return;
        }
        if (size[pRoot] < size[qRoot]) {
            arrN[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        } else {
            arrN[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }
        if (largest[pRoot]<largest[qRoot]){
            largest[pRoot] = largest[qRoot];
        }else {
            largest[qRoot] = largest[pRoot];
        }
    }
    
    /**
     *returns the largest element in the connected component containing i.
     */
    public int find(int i){
        int iRoot = root(i);
        if (size[iRoot] == 1){
            return i;
        }else {
            return largest[iRoot];
        }
        
    }
    
    public static void main(String[] args) {
        UnionFind UF = new UnionFind(10);
        UF.union(8,9);
        System.out.println(UF.find(8));
        System.out.println(UF.find(7));
    }
}
