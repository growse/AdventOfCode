import com.growse.adventofcode.com.growse.adventofcode.Day6
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import java.util.stream.Stream.of

class TestDay6 {
    companion object {
        @JvmStatic
        fun orbitTestCases(): Stream<Arguments> =
            of(
                Arguments.of(listOf("COM)B"), 1),
                Arguments.of(listOf("COM)B", "B)C"), 3),
                Arguments.of(
                    listOf(
                        "COM)B",
                        "B)C",
                        "C)D",
                        "D)E",
                        "E)F",
                        "B)G",
                        "G)H",
                        "D)I",
                        "E)J",
                        "J)K",
                        "K)L"
                    ), 42
                )
            )
    }

    @ParameterizedTest
    @MethodSource("orbitTestCases")
    fun orbitChecksumIsCorrect(input: List<String>, expectedOutput: Int) {
        assertEquals(expectedOutput, Day6().calculateOrbitChecksum(input))
    }

    @Test
    fun orbitTransferIsCorrect() {
        val input = listOf(
            "COM)B",
            "B)C",
            "C)D",
            "D)E",
            "E)F",
            "B)G",
            "G)H",
            "D)I",
            "E)J",
            "J)K",
            "K)L",
            "K)YOU",
            "I)SAN"
        )
        val expectedOutput = 4
        assertEquals(expectedOutput, Day6().calculateOrbitTransfer(input, "YOU", "SAN"))
    }
}