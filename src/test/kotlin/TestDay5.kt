import com.growse.adventofcode.IntCodeComputer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class TestDay5 {
    @Test
    fun intComputerRunsSimplePrintProgram() {
        val inputProgram = listOf(3,0,4,0,99)
        val byteArrayInputStream = ByteArrayInputStream("4".toByteArray())
        val byteArrayOutputStream = ByteArrayOutputStream()
        val printStream = PrintStream(byteArrayOutputStream)
        System.setIn(byteArrayInputStream)
        System.setOut(printStream)

        IntCodeComputer().executeProgram(inputProgram = inputProgram)

        assertEquals("4",byteArrayOutputStream.toString().trim())
    }
}