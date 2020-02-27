public class Successor {
    
    private int[] arrN;
    private int[] successor;
    private int largest;
    
    public Successor(int N) {
        arrN = new int[N];
        successor = new int[N];
        largest = N;
        for (int i = 0; i < N; i += 1) {
            arrN[i] = i;
            successor[i] = i;
        }
    }
    
    
    public int successor(int x) {
        if (x == largest) {
            largest -= 1;
            return -1;
        }
        arrN[x] = -1;
        successor[x] = successor[x + 1];
        return successor[x];
    }
    
    public static void main(String[] args) {
        Successor S = new Successor(10);
        System.out.println(S.successor(8));
        System.out.println(S.successor(7));
    }
}
