package lang.ast;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FunWithIndex extends Node {
	private ID functionName;
	private List<Param> arguments;
	private Param index;

	public FunWithIndex(int line, int column, ID functionName, List<Param> arguments, Param index) {
		super(line, column);
		this.functionName = functionName;
		this.arguments = arguments;
		this.index = index;
	}

	public ID getFunctionName() {
		return functionName;
	}

	public List<Param> getArguments() {
		return arguments;
	}

	public Param getIndex() {
		return index;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(functionName.toString()).append("(");

		if (arguments != null && !arguments.isEmpty()) {
			sb.append(arguments.stream().map(Param::toString).collect(Collectors.joining(", ")));
		}

		sb.append(")[").append(index.toString()).append("]");
		return sb.toString();
	}

	@Override
	public int interpret(HashMap<String, Integer> context) {
		return 1; // Tem que mudar
	}
}
