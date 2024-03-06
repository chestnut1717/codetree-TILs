import java.util.*;
import java.io.*;

public class Main {
    static int N, M;
    static TreeSet<Integer> s = new TreeSet<>();
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // N개의 숫자 입력
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            s.add(Integer.parseInt(st.nextToken()));
        }

        // M개의 숫자 입력
        for(int i = 0; i < M; i++) {
            Integer result = s.ceiling(Integer.parseInt(br.readLine()));
            if(result != null) {
                sb.append(result).append('\n');
            } else {
                sb.append(-1).append('\n');
            }
        }

        bw.write(sb.toString());
        bw.close();
    }
}