import java.io.*;
import java.util.*;

//77ms, dfs + bfs
public class Softeer_순서대로방문하기_레벨3_한재경 {
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};
    static int n;
    static int m;
    static int messSize = 0;
    static int twoCnt = 0;
    static int[][] grid;
    static List<int[]> goals; //방문 필수 장소 리스트
    static int result = 0;

    //벽1, 방문필요2, 덩어리3, dfs방문4
    //goal하나 방문 시마다 ++하면서 다음 인덱스 찾기
    static void dfs(int x, int y, int depth, int goalIdx) {
        
        if (goalIdx == m) {
            result++;
            return;
        }
        if (depth == messSize) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (0 <= nx && nx < n && 0 <= ny && ny < n && grid[nx][ny] == 3) {
                grid[nx][ny] = 4;
                if (nx == goals.get(goalIdx)[0] && ny == goals.get(goalIdx)[1]) { //다음 지점이면
                    dfs(nx, ny, depth+1, goalIdx + 1);
                } else {
                    dfs(nx, ny, depth+1, goalIdx);
                }
                grid[nx][ny] = 3;
            }
        }
    }

    //모든 방문장소 한 덩어리에 있는지, 해당 덩어리 크기 m 넘는지 확인
    static void bfs(Queue<int[]> q) {
        while(!q.isEmpty()) {
            int[] xy = q.poll();
            int x = xy[0];
            int y = xy[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (0 <= nx && nx < n && 0 <= ny && ny < n && (grid[nx][ny] == 0 || grid[nx][ny] == 2)) {
                    if (grid[nx][ny] == 2) {
                        twoCnt++;
                    }
                    grid[nx][ny] = 3;
                    q.add(new int[]{nx, ny});
                    messSize++;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken()); //n*n배열
        m = Integer.parseInt(st.nextToken()); //m곳 방문
        grid = new int[n][n]; //맵
        goals = new ArrayList<>(); //방문장소
        
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            goals.add(new int[]{x, y});
            grid[x][y] = 2;
        }
        
        Queue<int[]> q = new ArrayDeque<>(); //덩어리 만들기 큐
        q.add(new int[]{goals.get(0)[0], goals.get(0)[1]});
        bfs(q);
        
        //grid에 2남아있거나, messSize < m이면 불가능
        boolean flag = false;
        if (messSize < m || twoCnt != m) {
            flag = true;
        }

        //무조건 첫번째 방문리스트에서 출발해야 함!
        grid[goals.get(0)[0]][goals.get(0)[1]] = 4; //첫 지점 메모
        dfs(goals.get(0)[0], goals.get(0)[1], 1, 1);
        System.out.println(result);
    }
}
