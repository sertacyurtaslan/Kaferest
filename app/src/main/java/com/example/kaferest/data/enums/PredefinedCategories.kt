package com.example.kaferest.data.enums

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.kaferest.R
import com.example.kaferest.domain.model.Category

enum class PredefinedCategories(
    @StringRes val titleRes: Int,
    val emoji: String
) {
    COFFEE(R.string.category_coffee, "☕"),
    HOT_BEVERAGES(R.string.category_hot_beverages, "🔥"),
    COLD_BEVERAGES(R.string.category_cold_beverages, "❄️"),
    DESSERTS(R.string.category_desserts, "🍰"),
    CAKES_PASTRIES(R.string.category_cakes_pastries, "🎂"),
    COOKIES_SNACKS(R.string.category_cookies_snacks, "🍪"),
    BREAKFAST(R.string.category_breakfast, "🍳"),
    TOASTS_SANDWICHES(R.string.category_toasts_sandwiches, "🥪"),
    SALADS(R.string.category_salads, "🥗"),
    MAIN_DISHES(R.string.category_main_dishes, "🍛"),
    BURGERS(R.string.category_burgers, "🍔"),
    PASTA(R.string.category_pasta, "🍝"),
    SMOOTHIES_DETOX(R.string.category_smoothies_detox, "🍓"),
    FRESH_JUICES(R.string.category_fresh_juices, "🍹"),
    MILKSHAKES(R.string.category_milkshakes, "🥤"),
    HERBAL_TEAS(R.string.category_herbal_teas, "🍵"),
    SPECIAL_BLENDS(R.string.category_special_blends, "🧉"),
    VEGAN_GLUTEN_FREE(R.string.category_vegan_gluten_free, "🌱"),
    KIDS_MENU(R.string.category_kids_menu, "🧒"),
    CHEF_SPECIALS(R.string.category_chef_specials, "🌟");

    companion object {
        fun getEntries() = entries.toList()
    }
} 