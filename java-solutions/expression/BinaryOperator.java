package expression;

import java.math.BigDecimal;

public interface BinaryOperator {
    BigDecimal apply(BigDecimal left, BigDecimal right);
}