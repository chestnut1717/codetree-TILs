import java.util.*;
import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = null;

        int T = Integer.parseInt(br.readLine()); // 테스트 데이터 개수

        // 각 테스트마다 연산 시행
        for(int t = 0; t < T; t++) {
            // 큐 정의 => 삭제, 삽입 시 최댓값을 O(logn)으로 알 수 있도록!
            TreeSet<Integer> q = new TreeSet<>();
            int N = Integer.parseInt(br.readLine());
            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                // I : 큐에 삽입
                String cmd = st.nextToken();
                int num;
                if(cmd.equals("I")) {
                    num = Integer.parseInt(st.nextToken());
                    q.add(num);
                }
                // D : 삭제 연산
                else if(cmd.equals("D")) {
                    num = Integer.parseInt(st.nextToken());
                    // 우선 큐가 비어있으면 => 무시
                    if(q.isEmpty()) continue;
                    else if(num == 1) q.remove(q.last());
                    else if(num == -1) q.remove(q.first());
                }

                
            }

            // 각 테스트가 끝나면 큐 상태 점검
            if(q.isEmpty()) {
                sb.append("EMPTY").append('\n');
            } else {
                sb.append(q.last()).append(' ').append(q.first()).append('\n');;
            }




        }
        
        bw.write(sb.toString());
        bw.close();
    }
}