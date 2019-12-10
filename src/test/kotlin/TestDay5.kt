
import com.growse.adventofcode.IntCodeComputer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream


class TestDay5 {
    @Test
    fun intComputerRunsSimplePrintProgram() {
        val inputProgram = listOf(3, 0, 4, 0, 99)
        val byteArrayInputStream = ByteArrayInputStream("4".toByteArray())
        val byteArrayOutputStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteArrayOutputStream)
        System.setIn(byteArrayInputStream)
        System.setOut(printStream)

        IntCodeComputer().executeProgram(inputProgram = inputProgram)

        assertEquals("4", byteArrayOutputStream.toString().trim())
    }

    @ParameterizedTest
    @CsvSource("7,999", "8,1000", "9,1001")
    fun intComputerCanDoJumps(input: String, expectedOutput: String) {
        val inputProgram = listOf(
            3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31,
            1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104,
            999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99
        )

        val byteArrayInputStream = ByteArrayInputStream(input.toByteArray())
        val byteArrayOutputStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteArrayOutputStream)
        System.setIn(byteArrayInputStream)
        System.setOut(printStream)

        IntCodeComputer().executeProgram(inputProgram)
        assertEquals(expectedOutput, byteArrayOutputStream.toString().trim())
    }

}