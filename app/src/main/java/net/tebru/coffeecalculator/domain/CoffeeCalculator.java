package net.tebru.coffeecalculator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class Coffee Calculator
 *
 * Calculates amounts of coffee/water and bloom
 *
 * We can use the formula yield = [water poured] - [water absorbed] + [grounds extracted]
 * to calculate the amount of coffee and water we need if:
 *     yield: user input
 *     water absorbed: multiplier * mass of grounds
 *     grounds extracted: expected extraction percentage * mass of grounds
 *     water poured: x
 *     mass of grounds: y
 *
 * The formula becomes: yield = x - (multiplier * y) + (extraction * y)
 * and we solve for x and y knowing that x / y = the target ratio
 *
 * Example:
 *     If the expected yield is 13 ounces, the expected absorption is 2 times the amount
 *     of coffee, the expected extraction is 19%, and the target ratio is 17.42:1
 *
 *     13 = x - 2y + 0.19y
 *     17.42 = x/y
 *
 *     We can set up equations like:
 *
 *     x = 13 + y(-2 + 0.19)
 *     x = 17.42y
 *
 *     y = (13 - x) / (-2 + 0.19)
 *     y = x / 17.42
 *
 *     To solve fo coffee or water
 */
@Singleton public class CoffeeCalculator {
    /**
     * The ratio of water to grounds
     */
    final static private double RATIO = 17.42;

    /**
     * Used to convert fluid ounces to grams
     */
    final static private double FLUID_OUNCE_TO_GRAM = 29.5735;

    /**
     * The expected extraction percentage. The amount of coffee grounds that will
     * be dissolved into the coffee.
     */
    final static private double EXPECTED_EXTRACTION = 0.19;

    /**
     * How much water will be absorbed in grounds (grounds * multiplier)
     */
    final static private int WATER_ABSORBED_PERCENTAGE = 2;

    /**
     * How much water should be used to bloom
     */
    final static private double BLOOM_PERCENTAGE = 0.1;

    /**
     * How many decimal places we should display results with
     */
    final static private int DECIMAL_PLACES = 2;

    /**
     * The amount of coffee we should make in ounces
     */
    private double targetYield;

    /**
     * Constructor
     */
    @Inject public CoffeeCalculator() {}

    /**
     * Set the amount of coffee we should make in ounces
     *
     * @param targetYield The amount of coffee in fluid ounces
     */
    public void setTargetYield(double targetYield) {
        this.targetYield = targetYield * FLUID_OUNCE_TO_GRAM;
    }

    /**
     * Get the amount of coffee we should use
     *
     * this.targetYield = waterAmount + -(WATER_ABSORBED_PERCENTAGE * coffeeAmount) + (EXPECTED_EXTRACTION * coffeeAmount)
     * this.targetYield = waterAmount + coffeeAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     *
     * waterAmount = this.targetYield - coffeeAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * waterAmount = RATIO * coffeeAmount
     *
     * this.targetYield - coffeeAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION) = RATIO * coffeeAmount
     * this.targetYield = RATIO * coffeeAmount + coffeeAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * this.targetYield = coffeeAmount(RATIO + (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION))
     *
     * coffeeAmount = this.targetYield / (RATIO + (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION))
     *
     * @return The amount of coffee to use in grams
     */
    public BigDecimal getCoffeeAmount() {

        double coffeeAmount = this.targetYield / (RATIO + (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION));

        return this.fixDecimals(coffeeAmount);
    }

    /**
     * Get how much water we should use to bloom
     *
     * @return The amount to bloom
     */
    public BigDecimal getBloomAmount() {

        double waterAmount = this.getWaterAmount().doubleValue();
        double bloomAmount = waterAmount * BLOOM_PERCENTAGE;

        return this.fixDecimals(bloomAmount);
    }

    /**
     * Get the amount of water we should use
     *
     * this.targetYield = waterAmount + -(WATER_ABSORBED_PERCENTAGE * coffeeAmount) + (EXPECTED_EXTRACTION * coffeeAmount)
     * this.targetYield = waterAmount + coffeeAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * this.targetYield - waterAmount = coffeeAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     *
     * coffeeAmount = (this.targetYield - waterAmount) / (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * coffeeAmount = waterAmount / RATIO
     *
     * waterAmount / RATIO = (this.targetYield - waterAmount) / (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * waterAmount = RATIO(this.targetYield - waterAmount) / (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * waterAmount = (RATIO * this.targetYield - RATIO(waterAmount)) / (-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION)
     * waterAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION) = (RATIO * this.targetYield - RATIO(waterAmount))
     * waterAmount(-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION) + RATIO(waterAmount) = RATIO * this.targetYield
     * waterAmount((-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION) + RATIO) = RATIO * this.targetYield
     *
     * waterAmount = RATIO * this.targetYield / ((-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION) + RATIO)
     *
     * @return BigDecimal
     */
    public BigDecimal getWaterAmount() {
        double waterAmount = RATIO * this.targetYield / ((-WATER_ABSORBED_PERCENTAGE + EXPECTED_EXTRACTION) + RATIO);

        return this.fixDecimals(waterAmount);
    }

    /**
     * Convert double to BigDecimal and set decimal places to two
     *
     * @param amount The double value
     * @return The BigDecimal value
     */
    private BigDecimal fixDecimals(double amount) {
        return new BigDecimal(amount).setScale(DECIMAL_PLACES, RoundingMode.HALF_UP);
    }
}
