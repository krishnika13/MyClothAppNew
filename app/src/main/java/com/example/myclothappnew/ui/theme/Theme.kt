import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme

@Composable
fun ThemeColors(content: @Composable () -> Unit) {
    // Define the color schemes for light and dark modes
    val lightColors = lightColorScheme(
        primary = Color(0xFFFFE7E2),
        onPrimary = Color.White,
        background = Color(0xFFFFB4A6),
        surface = Color(0xFFFFDAD6),
    )

    val darkColors = darkColorScheme(
        primary = Color(0xFF410002),
        onPrimary = Color.Black,
        background = Color(0xFF690005),
        surface = Color(0xFF8C0009),
    )


    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColors else lightColors,
        content = content
    )
}

// This function returns the button color based on the theme
@Composable
fun buttonColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color(0xFFa31505) // Dark mode color
    } else {
        Color(0xFFCC7B6D) // Light mode color
    }
}


@Composable
fun appBackgroundColor(): Color {
    // Return black for dark mode and white for light mode
    return if (isSystemInDarkTheme()) {
        Color(0xFF1A1110) // Dark mode color
    } else {
        Color(0xFFFFF9F8)// Light mode color
    }
}


@Composable
fun appTextColor(): Color {
    // Return black for dark mode and white for light mode
    return if (isSystemInDarkTheme()) {
        Color(0xFFFFF9F8) // Dark mode color
    } else {
        Color(0xFF000000)// Light mode color
    }
}

