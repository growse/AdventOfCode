import com.growse.adventofcode.Day4
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class TestDay4 {
    @ParameterizedTest
    @ValueSource(ints = [111231, 348522, 2039488, 382281,1])
    fun inputHasAdjacentDigits(input: Int) {
        assertTrue(Day4().numberHasAdjacentDigits(input))
    }

    @ParameterizedTest
    @ValueSource(ints = [123456, 135699, 456789])
    fun inputHasIncreasingDigits(input: Int) {
        assertTrue(Day4().numberHasAlwaysIncreasingDigits(input))
    }
}