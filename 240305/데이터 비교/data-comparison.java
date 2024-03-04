import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception{
        // 여기에 코드를 작성해주세요.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        // 우선 arr1과 arr2를 입력받은 후 모두 각자의 set에 넣는다.
        int n = Integer.parseInt(br.readLine());
        int[] arr1 = new int[n];
        HashSet<Integer> s1 = new HashSet<>();

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr1[i] = Integer.parseInt(st.nextToken());
            s1.add(arr1[i]);
        }

        int m = Integer.parseInt(br.readLine());
        int[] arr2 = new int[m];
        HashSet<Integer> s2 = new HashSet<>();
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < m; i++) {
            arr2[i] = Integer.parseInt(st.nextToken());
            s2.add(arr2[i]);
        }

        // 그리고 arr2기준으로 비교한다.
        for(int i = 0; i < m; i++) {
            int num = arr2[i];
            if(s1.contains(num)) {
                System.out.printf("%d ", 1);
            }
            else {
                System.out.printf("%d ", 0);
            }
        }

        // 시간복잡도 : O(N1 + N2)
        // 만약 그냥 한다면? => O(N1*N2);
        
    }
}