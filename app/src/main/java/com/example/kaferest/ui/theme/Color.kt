package com.example.kaferest.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Light Theme Colors
// Primary: Warm coffee brown
val CoffeePrimary = Color(0xFF8B5E3C)  // Warm coffee brown
val CoffeeOnPrimary = Color(0xFFFFFFFF)
val CoffeePrimaryContainer = Color(0xFFFFDCC2)  // Light creamy brown
val CoffeeOnPrimaryContainer = Color(0xFF2E1500)  // Deep brown

// Secondary: Mocha tones
val CoffeeSecondary = Color(0xFF755847)  // Mocha brown
val CoffeeOnSecondary = Color(0xFFFFFFFF)
val CoffeeSecondaryContainer = Color(0xFFFFDCC7)  // Soft mocha
val CoffeeOnSecondaryContainer = Color(0xFF2B1708)  // Dark mocha

// Tertiary: Caramel accent
val CoffeeTertiary = Color(0xFF996B3D)  // Caramel
val CoffeeOnTertiary = Color(0xFFFFFFFF)
val CoffeeTertiaryContainer = Color(0xFFFFDDB7)  // Light caramel
val CoffeeOnTertiaryContainer = Color(0xFF351E00)  // Dark caramel

// Error colors
val CoffeeError = Color(0xFFBA1A1A)
val CoffeeOnError = Color(0xFFFFFFFF)
val CoffeeErrorContainer = Color(0xFFFFDAD6)
val CoffeeOnErrorContainer = Color(0xFF410002)

// Light theme backgrounds - Warm, creamy coffee tones
val CoffeeBackground = Color(0xFFF5E6D3)       // Soft cream coffee
val CoffeeOnBackground = Color(0xFF2C1810)     // Dark coffee text
val CoffeeSurface = Color(0xFFF8EDE3)         // Creamy surface
val CoffeeOnSurface = Color(0xFF2C1810)       // Dark coffee text

val CoffeeSurfaceVariant = Color(0xFFF2D5BA)  // Latte foam
val CoffeeOnSurfaceVariant = Color(0xFF52443C) // Roasted coffee
val CoffeeOutline = Color(0xFF85746B)         // Coffee outline




// Dark Theme Colors
// Rich espresso and dark roast tones
val CoffeePrimaryDark = Color(0xFFD4A373)      // Warm roasted coffee
val CoffeeOnPrimaryDark = Color(0xFF2E1500)    // Deep espresso
val CoffeePrimaryContainerDark = Color(0xFF8B5E3C) // Rich coffee brown
val CoffeeOnPrimaryContainerDark = Color(0xFFFFDCC2) // Creamy foam

// Mocha tones for secondary
val CoffeeSecondaryDark = Color(0xFFC4A69D)    // Soft mocha
val CoffeeOnSecondaryDark = Color(0xFF342620)  // Dark mocha
val CoffeeSecondaryContainerDark = Color(0xFF7D5B52) // Deep mocha
val CoffeeOnSecondaryContainerDark = Color(0xFFFFEDE8) // Light cream

// Caramel accents
val CoffeeTertiaryDark = Color(0xFFDEB887)     // Caramel gold
val CoffeeOnTertiaryDark = Color(0xFF3E2E04)   // Dark caramel
val CoffeeTertiaryContainerDark = Color(0xFF8B6B43) // Rich caramel
val CoffeeOnTertiaryContainerDark = Color(0xFFFFE5C5) // Light caramel

// Error states
val CoffeeErrorDark = Color(0xFFFFB4AB)
val CoffeeOnErrorDark = Color(0xFF690005)
val CoffeeErrorContainerDark = Color(0xFF93000A)
val CoffeeOnErrorContainerDark = Color(0xFFFFDAD6)

// Dark theme backgrounds - Rich coffee tones
val CoffeeBackgroundDark = Color(0xFF362F2D)    // Deep roasted coffee
val CoffeeOnBackgroundDark = Color(0xFFF3E6D8)  // Warm cream
val CoffeeSurfaceDark = Color(0xFF453832)      // Medium roast
val CoffeeOnSurfaceDark = Color(0xFFF3E6D8)    // Warm cream

// Surface variants
val CoffeeSurfaceVariantDark = Color(0xFF574B44) // Roasted coffee bean
val CoffeeOnSurfaceVariantDark = Color(0xFFE7D6CA) // Coffee with cream
val CoffeeOutlineDark = Color(0xFFB39F93)      // Soft coffee outline

// Gradients for dark theme
val CoffeeGradientDark = Brush.linearGradient(
    colors = listOf(
        Color(0xFF453832),  // Medium roast
        Color(0xFF362F2D)   // Deep roast
    ),
    start = Offset(Float.POSITIVE_INFINITY, 0f),
    end = Offset(0f, Float.POSITIVE_INFINITY)
)