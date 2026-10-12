package com.calc.model;

import java.math.BigDecimal;

/**
 * Java 21 sealed interface와 record를 활용한 연산 결과 모델
 */
public sealed interface CalculationResult
        permits CalculationResult.Success, CalculationResult.Error {

    /**
     * 정상 계산 결과
     *
     * @param value          정밀 계산된 BigDecimal 값
     * @param formattedValue 화면 표시용 포맷팅 문자열 (천 단위 콤마 포함)
     * @param expression     계산 수식 문자열
     */
    record Success(BigDecimal value, String formattedValue, String expression) implements CalculationResult {
    }

    /**
     * 연산 오류 결과 (예: 0으로 나누기, 음수의 제곱근 등)
     *
     * @param message    사용자에게 표시할 오류 메시지
     * @param expression 오류가 발생한 수식 문자열
     */
    record Error(String message, String expression) implements CalculationResult {
    }
}
