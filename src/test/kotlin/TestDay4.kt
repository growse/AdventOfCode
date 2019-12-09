import com.growse.adventofcode.Day4
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TestDay4 {
    @ParameterizedTest
    @ValueSource(ints = [111231, 348522, 2039488, 382281, 1])
    fun inputHasAdjacentDigits(input: Int) {
        assertTrue(Day4().numberHasAdjacentDigits(input))
    }

    @ParameterizedTest
    @ValueSource(ints = [123456, 135699, 456789])
    fun inputHasIncreasingDigits(input: Int) {
        assertTrue(Day4().numberHasAlwaysIncreasingDigits(input))
    }


    @ParameterizedTest
    @ValueSource(ints = [114231, 1227722277, 2039488, 382281, 11, 111122])
    fun inputHasOnePairOfAdjacentNumbers(input: Int) {
        assertTrue(Day4().numberHasAtLeastOnePairOfAdjacentDigits(input))
    }

    @ParameterizedTest
    @ValueSource(ints = [111231, 345552, 222228889, 111, 2, 1110111])
    fun inputHasNoPairsfAdjacantDigits(input: Int) {
        assertFalse(Day4().numberHasAtLeastOnePairOfAdjacentDigits(input))
    }
}