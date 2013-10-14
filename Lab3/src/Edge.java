public class Edge implements Comparable<Edge> {
	public String source;
	public String target;
	public int weight;

	public Edge(String source, String target, int weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge o) {
		return weight - o.weight;
	}

	@Override
	public String toString() {
		return source + "-" + weight + "-" + target;
	}
}