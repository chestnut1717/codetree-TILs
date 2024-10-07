import java.util.*;
import java.io.*;

public class Main {
    static final int[] dy = {-1, 0, 1, 0};
    static final int[] dx = {0, 1, 0, -1};
    static int K, M; // 반복 횟수, 벽면 유물 개수
    static int[][] map = new int[5][5];// 유적지
    static int[] wall;
    static int wallPivot = 0; // 현재까지 어떤 wall까지 진행했는지 확인
    static boolean[][] visited;
    static Stack<int[]> legacyArea;
    static PriorityQueue<Legacy> pq;

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        K = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 유적지 입력 받기
        for(int i = 0; i < 5; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 5; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 벽면에 적힌 M개 입력
        wall = new int[M];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < M; i++) {
            wall[i] = Integer.parseInt(st.nextToken());
        }

        boolean theEnd = false;
        int countK = 0;
        while(countK < K) {

            // 우선 중심을 선택한다(1,1)~(3.3)
            pq = new PriorityQueue<>();
            for(int i = 1; i <= 3; i++) {
                for(int j = 1; j <= 3; j++) {
                    int[][] pivot = new int[5][5];

                    for(int k = 0; k <= 3; k++) {
                        // rotate돌리지 않으면 우선 초기화해준다.
                        if(k == 0) {
                            // i, j 중심을 가지고 기존것을 돌린다.
                            pivot = copyArr(map);
                        }
                        // 그렇지 않으면 돌린다.
                        else {
                            pivot = rotate(pivot, i, j);
                        }
                        // 해당 pivot을 가지고 이제 점수를 계산하기 시작한다.
                        // 점수를 계산할 때는 DFS 탐색으로 진행한다.
                        int result = search(pivot);
                        // System.out.println(result);
                        // 여기서 이제 우선순위를 정한다.
                        // if(i == 1 && j == 2) {
                        //     System.out.printf("cnt: %d, angle: %d y: %d, x: %d\n", result, k, i, j);
                        //     printArr(pivot);
                        //     System.out.println();
                        // }
                        
                        
                        pq.add(new Legacy(result, k, j, i, pivot, legacyArea)); // 가치, 회전각도, 열, 행
                        

                    }

                }
            }
            // 이제 가장 우선순위가 높은 것을 뽑아서
            Legacy legacy = pq.poll();

            // 연쇄 폭발 일어나는지 확인, 유물 제거
            int selectedResult = legacy.cnt;
            int[][] selectedPivot = legacy.pivot;
            Stack<int[]> area = legacy.area;

            // System.out.printf("cnt: %d, angle: %d y: %d, x : %d\n", selectedResult, legacy.angle, legacy.row, legacy.col);

            // 어떠한 경우도 못하면
            if(selectedResult == 0) return;

            while(true) {
                
                // 제거
                for(int[] coor: area) {
                    int y = coor[0];
                    int x = coor[1];
                    selectedPivot[y][x] = 0;
                }
                // System.out.println();
                // printArr(selectedPivot);
                

                // 이제 wall 채울 차례
                // 열번호 작은, 행번호 큰 순으로 채움
                for(int x = 0; x < 5; x++) {
                    for(int y = 4; y >= 0; y--) {
                        if(selectedPivot[y][x] == 0) {
                            selectedPivot[y][x] = wall[wallPivot];
                            wallPivot++;
                        }
                    }
                }

                // System.out.println("wall");
                // printArr(selectedPivot);

                // 이제 연쇄 체크를 해준다.
                int newResult = search(selectedPivot);


                if (newResult <= 0) {
                    map = selectedPivot; //  갱신
                    break;
                }
                else {
                    
                    selectedResult += newResult;
                    area = legacyArea;
                }

            
            
            }
            countK++;
            System.out.printf("%d ", selectedResult);

        }




    }

    static boolean isValid(int y, int x) {
        return 0 <= y && y < 5 && 0 <= x && x < 5;
    }

    static void printArr(int[][] arr) {
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                System.out.printf("%d ", arr[i][j]);
            }
            System.out.println();
        }
       
    }

    static int dfs(int[][] pivot, int cnt, int y, int x) {


        for(int k = 0; k < 4; k++) {
            int ny = y + dy[k];
            int nx = x + dx[k];

            if(!isValid(ny, nx)) continue;
            if(visited[ny][nx]) continue;
            if(pivot[ny][nx] != pivot[y][x]) continue;
            visited[ny][nx] = true;
            legacyArea.push(new int[] {ny, nx});
            cnt = dfs(pivot, cnt+1, ny, nx);
            
            
        }

        return cnt;
    }

    // 조각들이 3개 이상 연결된 경우를 count한다.
    static int search(int[][] pivot) {
        Queue<int[]> q = new ArrayDeque<>();
        visited = new boolean[5][5];
        legacyArea = new Stack<>();
        int totalCnt = 0;
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if(visited[i][j]) continue;
                visited[i][j] = true;
                legacyArea.push(new int[] {i, j});
                int result = dfs(pivot, 1, i, j);
                if (result >= 3) {
                    totalCnt += result;
                } else {
                    for(int z = 0; z < result; z++) {
                        legacyArea.pop();
                    }
                }
            }
        }
        

        return totalCnt;
    }

    static int[][] copyArr(int[][] arrSample) {
        int[][] copy = new int[5][5];
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                copy[i][j] = arrSample[i][j];
            }
        }
        return copy;
    }

    // 회전시키는 함수
    static int[][] rotate(int[][] pivot, int y, int x) {
        // 새로운 5x5 배열을 만들어 pivot의 원본 배열을 복사합니다.
        int[][] result = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                result[i][j] = pivot[i][j];
            }
        }

        // 3x3 영역의 회전
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // 원본 pivot의 값을 회전된 위치에 배치
                result[y - 1 + j][x + 1 - i] = pivot[y - 1 + i][x - 1 + j];
            }
        }

        return result;
    }

    static class Legacy implements Comparable<Legacy>{
        int cnt;
        int angle;
        int col;
        int row;
        int[][] pivot;
        Stack<int[]> area;

        public Legacy(int cnt, int angle, int col, int row, int[][] pivot, Stack<int[] > area ) {
            this.cnt = cnt;
            this.angle = angle;
            this.col = col;
            this.row = row;
            this.pivot = pivot;
            this.area = area;
        }

        @Override
        public int compareTo(Legacy o) {
            if(this.cnt != o.cnt) {
                return Integer.compare(o.cnt, this.cnt);
            }
            if(this.angle != o.angle) {
                return Integer.compare(this.angle, o.angle);
            }
            if(this.col != o.col) {
                return Integer.compare(this.col, o.col);
            }

            return Integer.compare(this.row, o.row);

        }
    }
}