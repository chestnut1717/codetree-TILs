import java.io.*;
import java.util.*;

public class Main {
    static final int[] dy = {0, 1, 0}; // 동, 남, 서
    static final int[] dx = {1, 0, -1}; 
    static final int[] ry = {0, 1, 0, -1}; // BFS탐색할 때 쓰임
    static final int[] rx = {1, 0, -1, 0};
    

    static int R, C, K; // 행, 열, 정령의 수
    static int[][] forest; // 숲 / 위치 X = 0, 위치 = 1, 위치 + 출구 = 2
    static int[][] grid; // 같은 골렘인지 영역표시

    static int result = 0; // 행 더하기

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 정보 입력
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        forest = new int[R+3][C+1]; // 
        grid = new int[R+3][C+1];

        // 각 정령의 개수만큼 골렘 진행하기
        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());
            int startCol = Integer.parseInt(st.nextToken()); // 출발하는 열
            int exitDir = Integer.parseInt(st.nextToken()); // 출구 방향 정보 (0, 1, 2, 3 : 북, 동, 남, 서)

            // 골렘 생성
            Golem golem = new Golem(startCol, exitDir, i);

            // 골렘 이동
            moveGolem(golem);
            
        }
        System.out.println(result);
        



    }
    
    // 골렘을 계속 이동시켜 가능한 곳까지 탐색
    static void moveGolem(Golem golem) {
        boolean isEnd = false;
        // 끝날때까지 진행
        while(!isEnd) {

            // 1. 남쪽으로 이동 
            if(canMoveDown(golem)) {
                golem = moveDown(golem);
                // System.out.println("남쪽으로 이동");
                
            }
            // 2. 서쪽으로 이동
            else if (canMoveLeft(golem)) {
                golem = moveLeft(golem);
                // System.out.println("서쪽으로 이동");

            }
            // 3. 동쪽으로 이동
            else if (canMoveRight(golem)) {
                golem = moveRight(golem);
                // System.out.println("동쪽으로 이동");
            }

            else {
                isEnd = true;
            }

        }

        // 반복문이 다 끝났으면, 골렘의 위치를 박제시킨다.
        // 박제시키기 전 해당 골렘의 몸이 모두 정상 범위에 있는지 체크 => 빠져나왔다면 모두 clear 후 종료
        if (golemOutMap(golem)) {
            // forest 초기화
            forest = new int[R+3][C+1];
            grid = new int[R+3][C+1];
        }
        else {
            // 현재 골렘 위치를 확정지음
            setGolemPosition(golem);

            // BFS탐색으로 가장 아래쪽에 있는 row 구하기
            int maxRow = findMaxRow(golem) - 2;
            
            result += maxRow;
        }

        

    }

    // BFS탐색할 때 valid점검
    static boolean isValid(int y, int x) {
        return 3 <= y && y <= R+2 && 1 <= x && x <= C;
    }

    static int findMaxRow(Golem golem) {

        boolean[][] visited = new boolean[R+3][C+1];
        int golemNum = golem.num;
        int y = golem.center[0];
        int x = golem.center[1]; // center에 있으므로
        int maxRow = y;
        Queue<int[]> q = new ArrayDeque<>();

        // 우선 방문처리 해주고 
        visited[y][x] = true;
        q.add(new int[] {y, x, golemNum});

        while(!q.isEmpty()) {
            int[] tmp = q.poll();
            int prevY = tmp[0];
            int prevX = tmp[1];
            int nowNum = tmp[2];

            // 4방탐색 => 위로 가서 탐색할 수도 있음
            for(int i = 0; i < 4; i++) {
                int ny = prevY + ry[i];
                int nx = prevX + rx[i];

                // 유효하지 않다면
                if (!isValid(ny, nx)) continue;
                // 이미 방문했으면
                if(visited[ny][nx]) continue;
                // 움직일 골렘이 없으면
                if(forest[ny][nx] == 0) continue;
                
                // 1. 지금 위치한 골렘과 동일한 위치이면
                if(grid[ny][nx] == nowNum) {
                    visited[ny][nx] = true;
                    q.offer(new int[] {ny, nx, nowNum});
                    maxRow = Math.max(maxRow, ny);
                }
                // 2. 다른 골렘이라도 이전 위치에 출구가 있으면
                else if (grid[ny][nx] != nowNum && forest[prevY][prevX] == 2) {
                    visited[ny][nx] = true;
                    nowNum = grid[ny][nx];
                    q.offer(new int[] {ny, nx, nowNum});
                    maxRow = Math.max(maxRow, ny);

                    // 골렘 갱신
                    
                }

            }

        }

        return maxRow;


    }

    // 골렘의 몸이 맵 밖으로 빠져나왔나 체크
    static boolean golemOutMap(Golem golem) {
        
        if(golem.up[0] < 3) {
            return true;
        }
        return false;
    }

    static void setGolemPosition(Golem golem) {
        forest[golem.up[0]][golem.up[1]] = (golem.exitDir == 0) ? 2 : 1;
        forest[golem.right[0]][golem.right[1]] = (golem.exitDir == 1) ? 2 : 1;
        forest[golem.down[0]][golem.down[1]] = (golem.exitDir == 2) ? 2 : 1;
        forest[golem.left[0]][golem.left[1]] = (golem.exitDir == 3) ? 2 : 1;
        forest[golem.center[0]][golem.center[1]] = 1;

        // 영역표시
        grid[golem.up[0]][golem.up[1]] = golem.num;
        grid[golem.right[0]][golem.right[1]] = golem.num;
        grid[golem.down[0]][golem.down[1]] = golem.num;
        grid[golem.left[0]][golem.left[1]] = golem.num;
        grid[golem.center[0]][golem.center[1]] = golem.num;

        return;
        
    }
    // forest에 골렘이 있나 체크
    static boolean canMoveDown(Golem golem) {
        // 동
        int rightY = golem.right[0] + dy[1];
        // 남
        int downY = golem.down[0] + dy[1];
        // 서
        int leftY = golem.left[0] + dy[1];

        // 더 이상 갈수 없는 경우
        if (downY > R+2) return false;

        // 남쪽으로 가지만, 해당 위치에 이미 골렘이 있나 확인해야 함
        // 골렘이 있나 체크
        if(forest[downY][golem.down[1]] > 0) return false;
        if(forest[rightY][golem.right[1]] > 0) return false;
        if(forest[leftY][golem.left[1]] > 0) return false;

        return true;
    }

    // 서쪽, left => dx[2]
    static boolean canMoveLeft(Golem golem) {
        // 동
        int rightX = golem.right[1] + dx[2];
        // 남
        int downX = golem.down[1] + dx[2];
        // 서
        int leftX = golem.left[1] + dx[2];

        // 우선 범위에 벗어나지않는지 확인 => 범위 벗어나면 더 이상 불가
        if (leftX < 1) return false;

        // 동쪽(오른쪽으로 가지만으로 가지만, 해당 위치에 이미 골렘이 있나 확인해야 함
        // 골렘이 있나 체크
        if(forest[golem.down[0]][downX] > 0) return false;
        if(forest[golem.right[0]][rightX] > 0) return false;
        if(forest[golem.left[0]][leftX] > 0) return false;

        // 그리고 왼쪽으로 갈 수 있다면 왼쪽 아래쪽으로도 갈 수 있는지 탐색해야 한다.
        // 동
        int rightY = golem.right[0] + dy[1];
        // 남
        int downY = golem.down[0] + dy[1];
        // 서
        int leftY = golem.left[0] + dy[1];

        // 더 이상 갈수 없는 경우
        if (downY > R+2) return false;

        if(forest[downY][downX] > 0) return false;
        if(forest[rightY][rightX] > 0) return false;
        if(forest[leftY][leftX] > 0) return false;


        return true;
    }
    // 동쪽, rightt => dx[0]
    static boolean canMoveRight(Golem golem) {
        // 동
        int rightX = golem.right[1] + dx[0];
        // 남
        int downX = golem.down[1] + dx[0];
        // 서
        int leftX = golem.left[1] + dx[0];

        // 우선 범위에 벗어나지않는지 확인 => 범위 벗어나면 더 이상 불가
        if (rightX > C) return false;

        // 동쪽(오른쪽으로 가지만으로 가지만, 해당 위치에 이미 골렘이 있나 확인해야 함
        // 골렘이 있나 체크
        if(forest[golem.down[0]][downX] > 0) return false;
        if(forest[golem.right[0]][rightX] > 0) return false;
        if(forest[golem.left[0]][leftX] > 0) return false;

        // 그리고 왼쪽으로 갈 수 있다면 왼쪽 아래쪽으로도 갈 수 있는지 탐색해야 한다.
        // 동
        int rightY = golem.right[0] + dy[1];
        // 남
        int downY = golem.down[0] + dy[1];
        // 서
        int leftY = golem.left[0] + dy[1];

        // 더 이상 갈수 없는 경우
        if (downY > R+2) return false;

        if(forest[downY][downX] > 0) return false;
        if(forest[rightY][rightX] > 0) return false;
        if(forest[leftY][leftX] > 0) return false;


        return true;
    }

    static Golem moveDown(Golem golem) {
        // 움직임 이동(아래쪽)
        golem.down[0] += dy[1];
        golem.right[0] += dy[1];
        golem.left[0] += dy[1];
        golem.center[0] += dy[1];
        golem.up[0] += dy[1];
        
        return golem;
    }

    static Golem moveLeft(Golem golem) {
        // 움직임 이동(아래쪽)
        golem.down[0] += dy[1];
        golem.right[0] += dy[1];
        golem.left[0] += dy[1];
        golem.center[0] += dy[1];
        golem.up[0] += dy[1];
        
        // 움직임 이동(왼쪽)
        golem.down[1] += dx[2];
        golem.right[1] += dx[2];
        golem.left[1] += dx[2];
        golem.center[1] += dx[2];
        golem.up[1] += dx[2];

        // rotation 진행해야 함
        // System.out.println(golem.exitDir);
        golem.exitDir = ((golem.exitDir + 3) % 4);
        // System.out.println(golem.exitDir);
        return golem;
    }

    static Golem moveRight(Golem golem) {
        // 움직임 이동(아래쪽)
        golem.down[0] += dy[1];
        golem.right[0] += dy[1];
        golem.left[0] += dy[1];
        golem.center[0] += dy[1];
        golem.up[0] += dy[1];
        
        // 움직임 이동(오른쪽)
        golem.down[1] += dx[0];
        golem.right[1] += dx[0];
        golem.left[1] += dx[0];
        golem.center[1] += dx[0];
        golem.up[1] += dx[0];

        // rotation 진행해야 함 => 반시계방향
        // System.out.println(golem.exitDir);
        golem.exitDir = ((golem.exitDir + 1) % 4);
        // System.out.println(golem.exitDir);

        return golem;
    }

    // 골렘 구성 요소
    static class Golem {
        // 골렘을 구성하고 있는 것들
        int num; // 골렘 식별자
        int[] up;
        int[] right;
        int[] down;
        int[] left;
        int[] center;
        int exitDir;

        public Golem(int col, int exitDir, int num) {
            this.num = num;
            this.up = new int[] {0, col};
            this.right = new int[] {1, col+1};
            this.down = new int[] {2, col};
            this.left = new int[] {1, col-1};
            this.center = new int[] {1, col};
            this.exitDir = exitDir;
        }


    }

}