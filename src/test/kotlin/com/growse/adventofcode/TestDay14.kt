package com.growse.adventofcode

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import java.util.stream.Stream.of

class TestDay14 {
    companion object {
        @JvmStatic
        fun oreTestCases(): Stream<Arguments> =
            of(

                Arguments.of(
                    """
                    9 ORE => 2 A
                    8 ORE => 3 B
                    7 ORE => 5 C
                    3 A, 4 B => 1 AB
                    5 B, 7 C => 1 BC
                    4 C, 1 A => 1 CA
                    2 AB, 3 BC, 4 CA => 1 FUEL
                """.trimIndent(), 165, 6323776715L
                ),
                Arguments.of(
                    """
                    157 ORE => 5 NZVS
                    165 ORE => 6 DCFZ
                    44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
                    12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
                    179 ORE => 7 PSHF
                    177 ORE => 5 HKGWZ
                    7 DCFZ, 7 PSHF => 2 XJWVT
                    165 ORE => 2 GPVTF
                    3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT
                """.trimIndent(), 13312, 82892753L
                ),
                Arguments.of(
                    """
                    2 BC, 7 AD, 2 BB, 11 D => 1 BCADBBD
                    17 B, 3 C => 8 BC
                    53 BCADBBD, 6 D, 46 A, 81 DAD2ADBCBB, 68 BB, 25 ADBCBB => 1 FUEL
                    22 A, 37 D => 5 AD
                    139 ORE => 4 B
                    144 ORE => 7 C
                    5 D, 7 AD2, 2 AD, 2 BC, 19 BB => 3 DAD2ADBCBB
                    5 A, 7 D, 9 BC, 37 BB => 6 ADBCBB
                    145 ORE => 6 D
                    1 B => 8 BB
                    1 A, 6 D => 4 AD2
                    176 ORE => 6 A
                """.trimIndent(), 180697, 5586022L
                ),
                Arguments.of(
                    """
                    171 ORE => 8 CNZTR
                    7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
                    114 ORE => 4 BHXH
                    14 VRPVC => 6 BMBT
                    6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
                    6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
                    15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
                    13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
                    5 BMBT => 4 WPTQ
                    189 ORE => 9 KTJDG
                    1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
                    12 VRPVC, 27 CNZTR => 2 XDBXC
                    15 KTJDG, 12 BHXH => 5 XCVML
                    3 BHXH, 2 VRPVC => 7 MZWV
                    121 ORE => 7 VRPVC
                    7 XCVML => 6 RJRHP
                    5 BHXH, 4 VRPVC => 5 LTCX
                """.trimIndent(), 2210736, 460664L
                )
            )
    }

    @ParameterizedTest
    @MethodSource("oreTestCases")
    fun oreRequiredForFuelIsCorrect(recipe: String, expectedOreRequiredForOneFuel: Int, ignored: Long) {
        assertEquals(expectedOreRequiredForOneFuel.toLong(), Day14().calculateOreRequiredForFuel(recipe, 1L))
    }


    @ParameterizedTest
    @MethodSource("oreTestCases")
    fun correctFuelWithTrillionOre(recipe: String, ignored: Int, fuelCanBeMadeWithATrillionOre: Long) {
        assertEquals(fuelCanBeMadeWithATrillionOre, Day14().calculateFuelProducedWithOre(recipe, 1000000000000))
    }


    @Test
    fun parseRecipe() {
        val recipe = """
            10 ORE => 10 A
            1 ORE => 1 B
            7 A, 1 B => 1 C
            7 A, 1 C => 1 D
            7 A, 1 D => 1 E
            7 A, 1 E => 1 FUEL
        """.trimIndent()
        assertEquals(6, Day14().parseRecipe(recipe).size)
    }

    @Test
    fun ingredientIsPrerequisiteOf() {
        val recipe = """
            10 ORE => 10 A
            1 ORE => 1 B
            7 A, 1 B => 1 C
            7 A, 1 C => 1 D
            7 A, 1 D => 1 E
            7 A, 1 E => 1 FUEL
        """.trimIndent()
        val formulas = Day14().parseRecipe(recipe)
        assertTrue(Material("C").canBeMadeFrom(Material("A"), formulas))
        assertTrue(Material("E").canBeMadeFrom(Material("A"), formulas))
        assertTrue(Material("C").canBeMadeFrom(Material("B"), formulas))
        assertFalse(Material("B").canBeMadeFrom(Material("A"), formulas))
    }

    @Test
    fun ingredientIsPrerequisiteOfMoreComplex() {
        val recipe = """
            171 ORE => 8 CNZTR
            7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
            114 ORE => 4 BHXH
            14 VRPVC => 6 BMBT
            6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
            6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
            15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
            13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
            5 BMBT => 4 WPTQ
            189 ORE => 9 KTJDG
            1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
            12 VRPVC, 27 CNZTR => 2 XDBXC
            15 KTJDG, 12 BHXH => 5 XCVML
            3 BHXH, 2 VRPVC => 7 MZWV
            121 ORE => 7 VRPVC
            7 XCVML => 6 RJRHP
            5 BHXH, 4 VRPVC => 5 LTCX
        """.trimIndent()
        val formulas = Day14().parseRecipe(recipe)
        assertTrue(Material("RJRHP").canBeMadeFrom(Material("XCVML"), formulas))
        assertTrue(Material("XDBXC").canBeMadeFrom(Material("CNZTR"), formulas))
        assertTrue(Material("ZLQW").canBeMadeFrom(Material("CNZTR"), formulas))
        assertTrue(Material("ZDVW").canBeMadeFrom(Material("CNZTR"), formulas))
    }

    @Test
    fun selectFirstIngredientsToRefine() {
        val recipe = """
            10 ORE => 10 A
            1 ORE => 1 B
            7 A, 1 B => 1 C
            7 A, 1 C => 1 D
            7 A, 1 D => 1 E
            7 A, 1 E => 1 FUEL
        """.trimIndent()
        val ingredientToRefine = Day14().selectIngredientToUnrefine(
            mapOf(
                Material("A") to 1L,
                Material("B") to 2L
            ), Day14().parseRecipe(recipe)
        )
        assertEquals(Material("A"), ingredientToRefine)
    }

    @Test
    fun selectFirstNonPrerequisiteIngredientToRefine() {
        val recipe = """
                    2 BC, 7 AD, 2 BB, 11 D => 1 BCADBBD
                    17 B, 3 C => 8 BC
                    53 BCADBBD, 6 D, 46 A, 81 DAD2ADBCBB, 68 BB, 25 ADBCBB => 1 FUEL
                    22 A, 37 D => 5 AD
                    139 ORE => 4 B
                    144 ORE => 7 C
                    5 D, 7 AD2, 2 AD, 2 BC, 19 BB => 3 DAD2ADBCBB
                    5 A, 7 D, 9 BC, 37 BB => 6 ADBCBB
                    145 ORE => 6 D
                    1 B => 8 BB
                    1 A, 6 D => 4 AD2
                    176 ORE => 6 A
                """.trimIndent()
        val ingredientToRefine = Day14().selectIngredientToUnrefine(
            mapOf(
                Material("BCADBBD") to 53L,
                Material("D") to 6L,
                Material("A") to 46L,
                Material("DAD2ADBCBB") to 81L,
                Material("BB") to 68L,
                Material("ADBCBB") to 25L
            ), Day14().parseRecipe(recipe)
        )
        assertEquals(Material("BCADBBD"), ingredientToRefine)
    }

}