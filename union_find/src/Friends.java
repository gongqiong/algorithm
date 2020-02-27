public class Friends {
    private int[] arrN;
    private int[] size;
    private int num;
    
    public Friends(int N) {
        arrN = new int[N];
        size = new int[N];
        num = N;
        for (int i = 0; i < N; i += 1) {
            arrN[i] = i;
            size[i] = 1;
        }
    }
    
    private int root(int i) {
        while (i != arrN[i]) {
            arrN[i] = arrN[arrN[i]];   //path compression
            i = arrN[i];
        }
        return i;
    }
    
    public boolean isAFriend(int p, int q) {
        return root(p) == root(q);
    }
    
    public void toBeFriends(int p, int q) {
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
    }
    
    public boolean isAllFriends(){
        int root = root(arrN[0]);
        return size[root] == num;
    }
    
    public static void main(String[] args) {
        Friends f = new Friends(10);
        f.toBeFriends(0,1);
        System.out.println(f.isAllFriends());
        for (int i = 2; i<10;i+=1){
            f.toBeFriends(0,i);
        }
        System.out.println(f.isAllFriends());
    }
}
