package com.growse.adventofcode

import kotlin.math.ceil

fun main() {
    val recipe = Day14().loadRecipeFromResource("/day14.input.txt")
    println(Day14().calculateOreRequiredForFuel(recipe, 1L))
    println(Day14().calculateFuelProducedWithOre(recipe, 1000000000000))
}

data class Material(val name: String) {
    fun canBeMadeFrom(material: Material, formulas: Map<MaterialAndAmount, List<MaterialAndAmount>>): Boolean =
        if (material == this) true else
            formulas
                .filter { formula -> formula.key.material == this } // Get all formulas that make the ingredient
                .any {
                    it.value.any { i -> i.material.canBeMadeFrom(material, formulas) }
                    // Recurse looking at any of the constituents for each formula
                }

}

data class MaterialAndAmount(val material: Material, val amount: Int) {
    companion object {
        fun fromString(input: String): MaterialAndAmount {
            return MaterialAndAmount(
                Material(input.trim().split(" ", limit = 2)[1]),
                input.trim().split(" ")[0].toInt()
            )
        }
    }

    override fun toString(): String {
        return "$amount${material.name}"
    }
}


class Day14 {
    fun calculateOreRequiredForFuel(recipe: String, fuelRequired: Long): Long {
        val formulas: Map<MaterialAndAmount, List<MaterialAndAmount>> = parseRecipe(recipe)
        val ingredients = mutableMapOf(Material("FUEL") to fuelRequired)
        val surplus = mutableMapOf<Material, Long>()
        unRefineIngredients(
            ingredients, formulas, surplus
        )
        return ingredients[Material("ORE")] ?: 0
    }

    fun calculateFuelProducedWithOre(recipe: String, oreRequired: Long): Long {
        val oreForFirstFuel = calculateOreRequiredForFuel(recipe, 1L)
        val lowerFuelBound = ceil((oreRequired / oreForFirstFuel).toDouble()).toLong()
        val initialUpperBound = 2 * lowerFuelBound
        val initialStep = 1000L

        val lowerBoundFinder = { r: String, l: Long, u: Long, step: Long ->
            val idx = LongRange(l, u).step(step).toList()
                .binarySearchBy(oreRequired) {
                    val p = calculateOreRequiredForFuel(r, it)
                    p
                }
            (step * ((idx + 2) * -1)) + l
        }

        val firstPass = lowerBoundFinder(recipe, lowerFuelBound, initialUpperBound, initialStep)
        return lowerBoundFinder(recipe, firstPass, firstPass + 1000, 1)

    }

    private fun unRefineIngredients(
        ingredients: MutableMap<Material, Long>,
        formulas: Map<MaterialAndAmount, List<MaterialAndAmount>>,
        surplusIngredients: MutableMap<Material, Long>
    ) {
        if (formulas.keys.map { it.material }.intersect(ingredients.keys).isEmpty()) {
            return
        }
        val ingredientToUnrefine =
            selectIngredientToUnrefine(ingredients, formulas)

        val formula =
            formulas.filter { it.key.material == ingredientToUnrefine }
                .keys.first()

        val formulaMultiplier = ceil(ingredients[ingredientToUnrefine]!! / formula.amount.toDouble()).toLong()
        val surplus = (formula.amount * formulaMultiplier) - ingredients[ingredientToUnrefine]!!
        if (surplus > 0) surplusIngredients.merge(ingredientToUnrefine, surplus) { existing, new -> existing + new }
        ingredients.remove(ingredientToUnrefine)
//        usedFormulae.merge(formula, formulaMultiplier) { existing, new -> existing + new }

        formulas[formula]!!.forEach {
            ingredients.merge(it.material, formulaMultiplier * it.amount) { existing, new -> existing + new }
        }
        unRefineIngredients(ingredients, formulas, surplusIngredients)
    }

    fun selectIngredientToUnrefine(
        ingredients: Map<Material, Long>,
        formulas: Map<MaterialAndAmount, List<MaterialAndAmount>>
    ): Material = ingredients.keys.first { ingredient ->
        !ingredients.filter { ingredient != it.key }.any { it.key.canBeMadeFrom(ingredient, formulas) }
    }


    fun parseRecipe(recipe: String): Map<MaterialAndAmount, List<MaterialAndAmount>> = recipe
        .lines()
        .filter { it.contains("=>") }
        .groupBy { MaterialAndAmount.fromString(it.split("=>", limit = 2)[1]) }
        .also { assert(it.all { entry -> entry.value.size == 1 }) }
        .mapValues {
            it.value
                .first()
                .split("=>")[0]
                .split(",")
                .map { ingredient ->
                    MaterialAndAmount.fromString(ingredient)
                }
        }


    fun loadRecipeFromResource(resourceName: String): String =
        this::class.java
            .getResourceAsStream(resourceName)
            .bufferedReader()
            .use { it.readText() }


}
