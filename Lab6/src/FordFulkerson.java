import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Ford-Fulkerson 
 * Given a flow function and its corresponding residual graph (a maximum-flow
 * problem), select a path from the source to the sink along which the flow can
 * be increased and increase the flow. Repeat until there are no such paths.
 * 
 * @author liushaohui
 * @version 1.0.0 from Oct 7, 2009
 * 
 */

public class FordFulkerson {
	
		public static void main(String[] args) {
			Scanner scan = null;
			try {
				scan = new Scanner(new File("rail.txt"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			int n = scan.nextInt();
			FordFulkerson f = new FordFulkerson(n);
			scan.nextLine();

			for (int i = 0; i < n; i++) {
				scan.nextLine();
			}
			n = scan.nextInt();
			for (int i = 0; i < n; i++) {
				int a = scan.nextInt();
				int b = scan.nextInt();
				int c = scan.nextInt();
				if (c < 0) { // infinite flow if capacity is -1
					c = Integer.MAX_VALUE;
				}
				f.addEdge(a, b, c);
				scan.nextLine();
			}
			
			System.out.println(f.getMaxflow(0,54));
		}
        private int n;
        private int[][] cap;

        public FordFulkerson(int n) {
                this.n = n;
                this.cap = new int[n][n];
        }

        public void addEdge(int a, int b, int v) {
                cap[a][b] += v;
        }

        public int getMaxflow(int s, int t) {
                int flow = 0;
                while (true) {
                        int path = dfs(s, t);
                        if (path <= 0) {
                                break;
                        } else {
                                flow += path;
                        }
                }
                return flow;
        }

        /**
         * DFS增广寻找最大流
         * 
         * @param s
         * @param t
         * @return
         */
        public int getMaxflowDFS(int s, int t) {
                int flow = 0;
                while (true) {
                        int path = dfs(s, t);
                        if (path <= 0) {
                                break;
                        } else {
                                flow += path;
                        }
                }
                return flow;
        }

        /**
         * BFS增广寻找最大流
         * 
         * @param s
         * @param t
         * @return
         */
        public int getMaxflowBFS(int s, int t) {
                int flow = 0;
                while (true) {
                        int path = bfs(s, t);
                        if (path <= 0) {
                                break;
                        } else {
                                flow += path;
                        }
                }
                return flow;
        }

        /**
         * PFS增广寻找最大流
         * 
         * @param s
         * @param t
         * @return
         */
        public int getMaxflowPFS(int s, int t) {
                int flow = 0;
                while (true) {
                        int path = pfs(s, t);
                        if (path <= 0) {
                                break;
                        } else {
                                flow += path;
                        }
                }
                return flow;
        }

        /**
         * 
         * 
         * @param source
         * @param sink
         * @return
         */
        private int dfs(int source, int sink) {
                boolean[] flag = new boolean[n];
                Arrays.fill(flag, true);
                return dfsFind(source, sink, Integer.MAX_VALUE, flag);
        }

        /**
         * 递归寻找增广路径
         * 
         * @param source
         * @param sink
         * @param flow
         * @param flag
         * @return
         */
        private int dfsFind(int source, int sink, int flow, boolean[] flag) {
                if (source == sink) {
                        return flow;
                }
                flag[source] = false;
                for (int i = 0; i < n; i++) {
                        if (flag[i] && cap[source][i] > 0) {
                                int t = dfsFind(i, sink, Math.min(flow, cap[source][i]), flag);
                                if (t > 0) {
                                        cap[source][i] -= t;
                                        cap[i][source] += t;
                                        return t;
                                }
                        }
                }
                return 0;
        }

        /**
         * 
         * @param source
         * @param sink
         * @return
         */
        private int bfs(int source, int sink) {
                Queue<Integer> queue = new LinkedBlockingQueue<Integer>();

                int[] prev = new int[n];
                Arrays.fill(prev, -1);

                boolean[] visit = new boolean[n];
                Arrays.fill(visit, false);

                queue.add(source);
                visit[source] = true;

                boolean find = false;

                while (!queue.isEmpty()) {
                        int from = queue.poll();
                        for (int i = 0; i < n; i++) {
                                if (visit[i] == false && cap[from][i] > 0) {
                                        queue.add(i);
                                        visit[i] = true;
                                        prev[i] = from;

                                        if (i == sink) {
                                                find = true;
                                                break;
                                        }
                                }
                        }
                        if (find) {
                                break;
                        }
                }
                // 如果没有找到增广路径
                if (!find) {
                        return 0;
                }

                int to = sink;
                int flow = Integer.MAX_VALUE;
                while (prev[to] != -1) {
                        flow = Math.min(flow, cap[prev[to]][to]);
                        to = prev[to];
                }
                to = sink;
                while (prev[to] != -1) {
                        cap[prev[to]][to] -= flow;
                        cap[to][prev[to]] += flow;
                        to = prev[to];
                }
                return flow;
        }

        /**
         * 
         * @param source
         * @param sink
         * @return
         */
        private int pfs(int source, int sink) {
                Queue<PNode> pq = new PriorityQueue<PNode>();
                pq.add(new PNode(source, Integer.MAX_VALUE, -1));

                int[] prev = new int[n];
                Arrays.fill(prev, -1);

                boolean[] visit = new boolean[n];
                Arrays.fill(visit, false);

                int flow = 0;

                while (!pq.isEmpty()) {
                        PNode top = pq.poll();
                        int from = top.from;

                        if (visit[from]) {
                                continue;
                        }
                        prev[from] = top.from;

                        if (from == sink) {
                                flow = top.flow;
                                break;
                        }
                        visit[from] = true;

                        for (int i = 0; i < n; i++) {
                                if (cap[from][i] > 0) {
                                        int newFlow = Math.min(cap[from][i], top.flow);
                                        pq.add(new PNode(i, newFlow, from));
                                }
                        }
                }
                if (flow == 0) {
                        return 0;
                }
                int to = sink;
                while (prev[to] != -1) {
                        cap[prev[to]][to] -= flow;
                        cap[to][prev[to]] += flow;
                        to = prev[to];
                }
                return flow;

        }

        class PNode implements Comparable<PNode> {
                int from;
                int flow;
                int prev;

                public PNode(int from, int flow, int prev) {
                        this.from = from;
                        this.flow = flow;
                        this.prev = prev;
                }

                @Override
                public int compareTo(PNode that) {
                        return this.flow >= that.flow ? -1 : 1;
                }
        }
}