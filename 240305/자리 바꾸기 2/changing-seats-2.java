import java.io.*;
import java.util.*;

public class Main {
    static int N, K;
    static int[] seat;
    static List<HashSet<Integer>> seatNums = new ArrayList<>(); // i번째 사람이 자리바꿈 일어날 동안 앉게 되는 자리 개수
    static List<List<Integer>> inputList = new ArrayList<>();

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());

        // n, k입력
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        
        seat = new int[N+1]; // 사람 수만큼 입력
        for(int i = 0; i <= N; i++) {
            seat[i] = i; // 사람 초기 배열 채우기 O(N)
            seatNums.add(new HashSet<>());
        }

        // 자리 바꾸는 것 입력받기 O(K)
        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int p1 = Integer.parseInt(st.nextToken());
            int p2 = Integer.parseInt(st.nextToken());
            inputList.add(Arrays.asList(p1, p2));

        }
        // 자기가 기본적으로 앉는 경우 추가 O(N)
        for(int i = 1; i <= N; i++) {
            seatNums.get(i).add(i);
        }

        // 3K동안 자리바꿈 O(K)
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < K; j++) {
                int p1 = inputList.get(j).get(0);
                int p2 = inputList.get(j).get(1);
                swap(p1, p2);
            }
        }
        // 자리바꿈이 끝났으면 각각의 set에 담긴 것들 출력 O(N)
        for(int i = 1; i <= N; i++) {
            sb.append(seatNums.get(i).size()).append('\n');
        }


        bw.write(sb.toString());
        bw.close();
    }

    static void swap(int p1, int p2) {
        int tmp = seat[p1];
        seat[p1] = seat[p2];
        seat[p2] = tmp;

        // 그리고 자기가 앉은 현재 자리를 등록한다.
        seatNums.get(seat[p1]).add(p1);
        seatNums.get(seat[p2]).add(p2);

    }
}