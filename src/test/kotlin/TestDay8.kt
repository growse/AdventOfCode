import com.growse.adventofcode.Day8
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TestDay8 {
    private val imageData = "123456789012"
    private val width = 3
    private val height = 2
    @Test
    fun doesImageContainCorrectNumberOfLayers() {
        val image = Day8()
            .decodeImage(imageData.toCharArray().map { it.toInt() }, width, height)
        assertEquals(2, image.layers.size)
    }

    @Test
    fun doesImageDecodeToLayersWithRightData() {
        val image = Day8()
            .decodeImage(imageData.map { it.toString().toInt() }.toList(), width, height)
        assertTrue(image.layers.all { it.width == width })
        assertTrue(image.layers.all { it.height == height })
        assertEquals(listOf(1, 2, 3), image.layers.first().rows().first())
    }
}