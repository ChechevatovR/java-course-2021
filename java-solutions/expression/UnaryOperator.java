package expression;

import java.math.BigDecimal;

public interface UnaryOperator {
    BigDecimal apply(BigDecimal operand);
}