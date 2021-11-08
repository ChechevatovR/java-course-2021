import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.function.Predicate;

import static java.lang.Math.max;

public class E {
    static ArrayList<ArrayList<Integer>> connections;
    static int[] parent;
    static int[] distanceToRoot;
    static boolean[] isTeamInTheCity;
    static int[] teamOrigins;

    private static int findFurthestTeam(int curVertex) {
        int res = isTeamInTheCity[curVertex] ? curVertex : -1;

        for (int nextVertex : connections.get(curVertex)) {
            if (nextVertex != parent[curVertex]) {
                parent[nextVertex] = curVertex;
                distanceToRoot[nextVertex] = distanceToRoot[curVertex] + 1;
                int childRes = findFurthestTeam(nextVertex);
                if (res == -1 || (childRes != -1 && distanceToRoot[childRes] > distanceToRoot[res])) {
                    res = childRes;
                }
            }
        }
        return res;
    }

    private static int findKthParent(int vertex, int k) {
        if (k == 0) {
            return vertex;
        } else if (parent[vertex] == -1) {
            return -1;
        } else {
            return findKthParent(parent[vertex], k - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        connections = new ArrayList<ArrayList<Integer>>(n + 1);
        isTeamInTheCity = new boolean[n + 1];
        teamOrigins = new int[m];
        parent = new int[n + 1];
        distanceToRoot = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            connections.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < n - 1; i++) {
            int v = in.nextInt();
            int u = in.nextInt();
            connections.get(v).add(u);
            connections.get(u).add(v);
        }
        for (int i = 0; i < m; i++) {
            teamOrigins[i] = in.nextInt();
            isTeamInTheCity[teamOrigins[i]] = true;
        }
        in.close();
        parent[teamOrigins[0]] = -1;
        int furthestTeam = findFurthestTeam(teamOrigins[0]);
        int ansCandidateDistance = distanceToRoot[furthestTeam] / 2;

        int ansCandidate = findKthParent(furthestTeam, ansCandidateDistance);
        parent = new int[n + 1];
        distanceToRoot = new int[n + 1];
        parent[ansCandidate] = -1;
        findFurthestTeam(ansCandidate); // Build 'distanceToRoot' array

        for (int cityWithTeam : teamOrigins) {
            if (distanceToRoot[cityWithTeam] != ansCandidateDistance) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
        System.out.println(ansCandidate);
    }
}

// ================[ Scanner ]================

class isNotWhitespace implements Predicate<Character> {
    @Override
    public boolean test(Character c) {
        return !Character.isWhitespace(c);
    }
}

class Scanner {
    private final int BUFFER_SIZE = 512;

    private final Reader reader;
    private final CharBuffer buf = CharBuffer.allocate(this.BUFFER_SIZE).limit(0);

    private final Predicate<Character> isNotWhitespace = new isNotWhitespace();

    // =================================[ CONSTRUCTORS ]=================================

    public Scanner(InputStream is) {
        this.reader = new BufferedReader(new InputStreamReader(is));
    }

    // WORKAROUND FOR JAVA < 15

    private static boolean isEmpty(CharBuffer buf) {
        return buf.position() == buf.limit();
    }

    // ==========================[ BASIC READER INTERACTIONS ]===========================

    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            System.err.println("There was an IOException when closing scanner");
        }
    }

    private void read() throws IOException {
        // Reads into the buffer with destroying data already stored
        this.buf.clear();
        this.buf.limit(max(0, this.reader.read(this.buf)));
        this.buf.rewind();
    }

    // ===============================[ TOKEN SPLITTING ]================================

    private String nextToken(Predicate<Character> isTokenChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        outer: while (true) {
            while (!isEmpty(this.buf)) {
                char cur = this.buf.get();
                if (isTokenChar.test(cur)) {
                    found = true;
                    sb.append(cur);
                } else if (found) {
                    this.buf.position(this.buf.position() - 1);
                    break outer;
                }
            }
            this.read();
            if (isEmpty(this.buf)) {
                break;
            }
        }
        return sb.toString();
    }

    public int nextInt() throws InputMismatchException, IOException {
        try {
            return Integer.parseInt(this.nextToken(this.isNotWhitespace));
        } catch (NumberFormatException e) {
            throw new InputMismatchException();
        }
    }
}
